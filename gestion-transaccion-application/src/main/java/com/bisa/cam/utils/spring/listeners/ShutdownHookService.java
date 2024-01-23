package com.bisa.cam.utils.spring.listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ShutdownHookService implements ApplicationListener<ContextRefreshedEvent> {
    private final Logger logger = LogManager.getLogger(getClass());

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        final Map<String, ShutdownHook> shutdowners = event.getApplicationContext().getBeansOfType(ShutdownHook.class);

        logger.info("Attempting to load {} shutdown hooks", shutdowners.size());

        shutdowners.values().stream().forEach(shutdownHook -> {

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                shutdownHook.onShutdown();
            }));

            logger.info("ShutdownHook [{}] prepared successfully", shutdownHook.getClass());
        });

        logger.info("{} shutdown hooks were found", shutdowners.size());
    }
}
