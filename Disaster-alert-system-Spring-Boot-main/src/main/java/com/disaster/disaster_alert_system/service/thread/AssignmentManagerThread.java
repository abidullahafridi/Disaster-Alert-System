package com.disaster.disaster_alert_system.service.thread;

import com.disaster.disaster_alert_system.service.DisasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import jakarta.annotation.PreDestroy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
public class AssignmentManagerThread extends Thread {
    
    private final DisasterService disasterService;
    private boolean isRunning = true;
    private final ExecutorService executorService = Executors.newCachedThreadPool();
    
    // Use constructor injection with @Lazy
    @Autowired
public AssignmentManagerThread(DisasterService disasterService) {
        this.disasterService = disasterService;
        this.setName("Assignment-Manager-Thread");
        this.setDaemon(true);
    }
    
    // REMOVE @PostConstruct method
    // @PostConstruct
    // public void init() {
    //     this.start();
    //     System.out.println("Assignment Manager Thread started");
    // }
    
    @PreDestroy
    public void cleanup() {
        isRunning = false;
        this.interrupt();
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
    
    @Override
    public void run() {
        System.out.println("Assignment Manager Thread started"); // Moved here
        
        while (isRunning) {
            try {
                // Check for pending disasters every 5 seconds
                Thread.sleep(5000);
                
                // Process pending disasters
                disasterService.processPendingDisasters();
                
            } catch (InterruptedException e) {
                System.out.println("Assignment Manager Thread interrupted");
                Thread.currentThread().interrupt();
                break;
            } catch (Exception e) {
                System.err.println("Error in Assignment Manager: " + e.getMessage());
            }
        }
        System.out.println("Assignment Manager Thread stopped");
    }
    
    public void submitTask(Runnable task) {
        executorService.submit(task);
    }
    
    // Add a manual start method
    public void startThread() {
        if (!this.isAlive()) {
            this.start();
        }
    }
}