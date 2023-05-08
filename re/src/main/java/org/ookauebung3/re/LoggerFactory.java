package org.ookauebung3.re;

import org.ookauebung3.component_modell.Logging;

public class LoggerFactory {
    private static volatile LoggerFactory instance = null;

    public static LoggerFactory getInstance(){
        if(instance == null){
            synchronized (LoggerFactory.class){
                if (instance == null){
                    instance = new LoggerFactory();
                }
            }
        }
        return instance;
    }
    private LoggerFactory(){

    }
    Logging createLogger(){
        return new Logger();
    }
}
