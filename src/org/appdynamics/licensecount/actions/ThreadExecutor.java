/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.appdynamics.licensecount.actions;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author gilbert.solorzano
 * 
 * This is going to be the class that gets
 */
public class ThreadExecutor {
    private ExecutorService executor;
    
    public ThreadExecutor(int size){
        executor = Executors.newFixedThreadPool(size);
    }

    public ExecutorService getExecutor() {
        return executor;
    }

    public void setExecutor(ExecutorService executor) {
        this.executor = executor;
    }
    
    
    
    public void shutdown(){
        executor.shutdown();
        while(!executor.isTerminated()){}
    }
    
}
