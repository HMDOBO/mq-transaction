package com.order.message.filter.impl;

import com.common.enmus.MessageDeadStatusEnum;
import com.common.enmus.MessageSendStatusEnum;
import com.common.enmus.QueueNameEnum;
import com.common.entity.MessageEntity;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.order.mapper.MessageEntityMapper;
import com.order.message.filter.MessageFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MessageFilterImpl implements MessageFilter {

    @Autowired
    private Environment evn;

    @Autowired
    private MessageEntityMapper messageEntityMapper;

    @Override
    public Map<String, MessageEntity> selectSendingTimeOutMessage(int pageSize, int maxHandlerPageCount) {

        int pageNumber = 1;
        MessageEntity message = new MessageEntity();
        Map<String, MessageEntity> messageMap = new HashMap<>();

        // 分页查询状态为 “发送中” ，但已超时的消息

        Date needTime = getCreateTimeBefore();

        message.setQueue_name(QueueNameEnum.LOCAL_BUY_RECORD_QUEUE.name()); // 消息队列名称
        message.setCreate_time(needTime);   // 需要查询创建时间小于这个时间的消息
        message.setAreadly_dead(MessageDeadStatusEnum.NODEAD.name());   // 未死亡的消息
        message.setStatus(MessageSendStatusEnum.SENDING.name());    // 消息状态为发送中

        PageHelper.startPage(pageNumber, pageSize);
        List<MessageEntity> messages = messageEntityMapper.selectByParam(message);
        PageInfo pageInfo = new PageInfo(messages);

        if (messages == null || messages.size() <= 0)
            return messageMap;

        for (MessageEntity me : messages) {
            messageMap.put(me.getId().toString(), me);
        }

        int pages = pageInfo.getPages();    // 总页数
        if (pages > maxHandlerPageCount)
            pages = maxHandlerPageCount;

        for (int i = 2; i <= pages; i++) {
            PageHelper.startPage(i, pageSize);
            messages = messageEntityMapper.selectByParam(message);
            for (MessageEntity me : messages) {
                messageMap.put(me.getId().toString(), me);
            }
        }

        messages = null;
        pageInfo = null;

        return messageMap;
    }

    private Date getCreateTimeBefore() {
        String intervalTime = evn.getProperty("message.resend.interval.time");
        long interval = Long.valueOf(intervalTime) * 1000;
        long nowTime = System.currentTimeMillis();
        return new Date(nowTime - interval);
    }

}
