package com.disaster.disaster_alert_system.service.thread;

import com.disaster.disaster_alert_system.model.inheritance.Volunteer;
import java.util.concurrent.locks.ReentrantLock;

public class VolunteerThread extends Thread {
    private final Volunteer volunteer;
    private boolean isActive = true;
    private boolean hasTask = false;
    private final ReentrantLock lock = new ReentrantLock();
    
    public VolunteerThread(Volunteer volunteer) {
        this.volunteer = volunteer;
        this.setName("Thread-" + volunteer.getCategory() + "-" + volunteer.getId());
    }
    
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " started for " + volunteer.getName());
        
        while (isActive) {
            try {
                if (hasTask) {
                    performAssignedTask();
                } else {
                    // Wait for task
                    Thread.sleep(1000); // Check every second
                }
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + " was interrupted");
                Thread.currentThread().interrupt();
                break;
            } catch (Exception e) {
                System.err.println("Error in volunteer thread: " + e.getMessage());
            }
        }
        
        System.out.println(Thread.currentThread().getName() + " stopped");
    }
    
    private void performAssignedTask() throws InterruptedException {
        lock.lock();
        try {
            volunteer.startTask();
            System.out.println(volunteer.getName() + " is performing: " + volunteer.performTask());
            
            // Simulate work duration
            int duration = volunteer.getTaskDuration() * 1000; // Convert to milliseconds
            Thread.sleep(duration);
            
            volunteer.completeTask();
            hasTask = false;
            
            System.out.println(volunteer.getName() + " completed task in " + volunteer.getTaskDuration() + " seconds");
        } finally {
            lock.unlock();
        }
    }
    
    public void assignTask() {
        lock.lock();
        try {
            if (volunteer.getStatus().name().equals("AVAILABLE")) {
                hasTask = true;
                System.out.println("Task assigned to " + volunteer.getName());
            }
        } finally {
            lock.unlock();
        }
    }
    
    public void stopThread() {
        isActive = false;
        this.interrupt();
    }
    
    // Getters
    public Volunteer getVolunteer() {
        return volunteer;
    }
    
    public boolean isBusy() {
        return hasTask;
    }
}