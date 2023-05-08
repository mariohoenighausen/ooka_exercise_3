package org.ookauebung3.app;

import org.ookauebung3.component_modell.Logging;

public class Logger implements Logging {

    @Override
    public void sendLog(String msg) {
        System.out.println(msg);
    }
}
