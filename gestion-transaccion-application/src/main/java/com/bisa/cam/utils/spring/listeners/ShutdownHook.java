package com.bisa.cam.utils.spring.listeners;

@FunctionalInterface
public interface ShutdownHook {
    /**
     * Do something when application shutdowns
     */
    void onShutdown();
}
