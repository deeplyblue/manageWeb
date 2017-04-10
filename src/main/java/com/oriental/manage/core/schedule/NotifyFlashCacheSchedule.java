package com.oriental.manage.core.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentSkipListSet;

/**
 * Created by lupf on 2016/11/24.
 */
@Slf4j
@Component
public class NotifyFlashCacheSchedule implements Runnable {

    @Autowired
    private AmqpTemplate amqpTemplate;
    /**
     * 线程安全的set,存储要刷新缓存的table
     */
    private ConcurrentSkipListSet<String> tables = new ConcurrentSkipListSet<>();

    @Override
    @Scheduled(cron = "0 0/5 * * * ?")
    public void run() {

        log.info("定时通知外部缓存刷新-start");

        if (tables.isEmpty()) {
            log.info("无缓存更新,不发送");
            return;
        } else {

            while (tables.size() > 0) {
                try {
                    String table = tables.pollFirst();
                    this.sendNotify(table);

                    Thread.sleep(10000);
                } catch (Exception e) {
                    log.error("mq通知外部缓存刷新失败", e);
                }
            }
        }
        log.info("定时通知外部缓存刷新-end");
    }

    /**
     * 发送mq消息，通知外部刷新缓存.
     *
     * @param table
     */
    public final void sendNotify(String table) {

        log.info("发送mq通知:{}", table);
        amqpTemplate.convertAndSend(table.toUpperCase());
    }

    /**
     * 添加通知内容,等待发送
     *
     * @param table
     */
    public final void addToNotify(String table) {
        this.tables.add(table);
    }
}
