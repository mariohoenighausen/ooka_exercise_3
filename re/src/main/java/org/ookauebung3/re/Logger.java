package org.ookauebung3.re;

import org.ookauebung3.component_modell.Logging;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger implements Logging {

    @Override
    public void sendLog(String msg) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        String timeStamp = now.format(formatter);
        String modifiedLogMsg = "++++ LOG: " +
                msg +
                " ([" +
                timeStamp +
                "])";
        System.out.println(modifiedLogMsg);
    }
}
