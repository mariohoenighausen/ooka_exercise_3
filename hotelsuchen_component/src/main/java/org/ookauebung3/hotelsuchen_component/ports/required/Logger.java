package org.ookauebung3.hotelsuchen_component.ports.required;

import org.ookauebung3.component_modell.Logging;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger implements Logging {
    public Logger(){

    }

    @Override
    public void sendLog(String msg) {
        String[] splitMsg = msg.split(",");
        String methodName = splitMsg[0];
        String searchWord = splitMsg[1];
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        System.out.println(now.format(formatter) + ": Zugriff auf Buchungssystem über Methode "+ methodName + ". Suchwort: " + searchWord);
    }
}
