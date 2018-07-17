package com.order.message;

import com.order.message.scheduled.MessageScheduled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 消息处理定时器
 */
@Component
public class MessageTask {

    private static final Logger LOG = LoggerFactory.getLogger(MessageTask.class);

    @Autowired
    private MessageScheduled messageScheduled;
    @Autowired
    private Environment env;
    
    @PostConstruct
    public void task() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        executorService.submit(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    LOG.info("执行(处理[SENDING]的消息)任务开始");
                    messageScheduled.handleSendingTimeOutMessage();
                    LOG.info("执行(处理[SENDING]的消息)任务结束");

                    try {
                        Thread.sleep(Integer.valueOf(env.getProperty("message.task.sleep.time")) * 1000);    // 休眠60秒
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

}
