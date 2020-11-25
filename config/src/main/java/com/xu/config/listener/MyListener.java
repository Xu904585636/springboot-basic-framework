package com.xu.config.listener;

import com.xu.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Administrator
 */
@Slf4j
public class MyListener implements HttpSessionListener{

    @Autowired
    private UserService userService;

    private AtomicInteger count = new AtomicInteger(0);

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        log.info("新增在线人数,当前在线人数为:{}",count.incrementAndGet());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        log.info("减少在线人数,当前在线人数为:{}",count.decrementAndGet());

    }
}
