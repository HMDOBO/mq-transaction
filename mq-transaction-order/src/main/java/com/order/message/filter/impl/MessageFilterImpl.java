package com.order.message.filter.impl;

import com.common.enmus.MessageDeadStatusEnum;
import com.common.enmus.MessageSendStatusEnum;
import com.common.enmus.QueueNameEnum;
import com.common.entity.MessageEntity;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.order.mapper.MessageEntityMapper;
import com.order.message.filter.MessageFilter;
import com.order.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class MessageFilterImpl implements MessageFilter {

    private static final Logger LOG = LoggerFactory.getLogger(MessageFilterImpl.class);

    @Autowired
    private Environment env;

    @Autowired
    private MessageEntityMapper messageEntityMapper;
    @Autowired
    private MessageService messageService;

    @Override
    public Map<String, MessageEntity> selectSendingTimeOutMessage(int pageSize, int maxHandlerPageCount) {

        int pageNumber = 1;
        MessageEntity message = new MessageEntity();
        Map<String, MessageEntity> messageMap = new HashMap<>();

        // 分页查询状态为 “发送中” ，但已超时的消息
        Date needTime = getCreateTimeBefore();
        // 条件封装
        message.setQueue_name(QueueNameEnum.LOCAL_BUY_RECORD_QUEUE.getValue()); // 消息队列名称
        message.setCreate_time(needTime);   // 需要查询创建时间小于这个时间的消息
        message.setAreadly_dead(MessageDeadStatusEnum.NODEAD.name());   // 未死亡的消息
        message.setStatus(MessageSendStatusEnum.SENDING.name());    // 消息状态为发送中

        PageHelper.startPage(pageNumber, pageSize);
        List<MessageEntity> messages = messageEntityMapper.selectByParam(message);
        PageInfo pageInfo = new PageInfo(messages);

        if (messages == null || messages.size() <= 0) {
            LOG.info("==> messageList is empty");
            return messageMap;
        }
        for (MessageEntity me : messages) {
            messageMap.put(me.getId().toString(), me);
        }

        int pages = pageInfo.getPages();    // 总页数
        if (pages > maxHandlerPageCount)
            pages = maxHandlerPageCount;

        for (int i = 2; i <= pages; i++) {  // 继续查询
            PageHelper.startPage(i, pageSize);
            messages = messageEntityMapper.selectByParam(message);
            for (MessageEntity me : messages) {
                messageMap.put(me.getId().toString(), me);
            }
        }

        messages = null;
        pageInfo = null;

        LOG.info("==> messageMap size " + messageMap.size());

        return messageMap;
    }


    @Transactional
    @Override
    public void sendEligibleMessage(Map<String, MessageEntity> messageMap) {
        String maxTimes = env.getProperty("message.max.send.times");    // 最多发送次数

        Map<Integer, Integer> notifyParam = getSendTime();

        LOG.info("开始处理[SENDING]状态的消息,总条数[" + messageMap.size() + "]");

        for (Map.Entry<String, MessageEntity> entry : messageMap.entrySet()) {  // 效率要快
            MessageEntity message = entry.getValue();

            try {
                LOG.info("开始处理[SENDING]消息ID为[" + message.getId() + "]的消息");

                Integer message_send_times = message.getMessage_send_times();
                LOG.info("[SENDING]消息ID为[" + message.getId() + "]的消息,已经重新发送的次数[" + message.getMessage_send_times() + "]");
                if (message_send_times >= Integer.valueOf(maxTimes)) {  // 标记为死亡
                    messageService.markedAsDeath(message.getId());
                    continue;
                }

                Integer intervals = notifyParam.get(message_send_times);    // 获取本消息重发间隔时间

                long nowTime = System.currentTimeMillis();
                long needTime = nowTime - intervals * 60 * 1000;    // 需要重新发送时间
                long hasTime = message.getEdit_time().getTime();  // 消息最后一次发送时间
                if (hasTime > needTime) {   // 重发时间间隔未到，无需发送
                    LOG.info("[SENDING]消息ID为[" + message.getId() + "]的消息发送时间间隔未到，需要距离上次发送时间" + intervals + "分钟才能再次发送");
                    continue;
                }

                // 重发消息
                messageService.resendMessage(message);
                LOG.info("结束处理[SENDING]消息ID为[" + message.getId() + "]的消息");
            } catch (Exception e) {
                LOG.error("处理[SENDING]消息ID为[" + message.getId() + "]的消息异常：", e);
            }
        }
    }

    /**
     * 获取需要发送的消息的创建时间
     * @return
     */
    private Date getCreateTimeBefore() {
        String intervalTime = env.getProperty("message.resend.interval.time");
        long interval = Long.valueOf(intervalTime) * 1000;
        long nowTime = System.currentTimeMillis();
        return new Date(nowTime - interval);
    }

    /**
     * 获取消息重发规则
     *
     * 消息发送次数，对应重发间隔时间
     * @return
     */
    private Map<Integer, Integer> getSendTime() {
        Map<Integer, Integer> notifyParam = new HashMap<>();

        notifyParam.put(1, Integer.valueOf(env.getProperty("message.send.1.times")));
        notifyParam.put(2, Integer.valueOf(env.getProperty("message.send.2.times")));
        notifyParam.put(3, Integer.valueOf(env.getProperty("message.send.3.times")));
        notifyParam.put(4, Integer.valueOf(env.getProperty("message.send.4.times")));
        notifyParam.put(5, Integer.valueOf(env.getProperty("message.send.5.times")));

        return notifyParam;
    }

}
