package model;

import java.time.LocalDateTime;
import java.util.Date;

public class Reader extends Personage {
   public Reader(String name) {
       super("Читатель", name, 0.0, 0.0, 0, 158, Mood.NORMAL, LocalDateTime.now());
   }
}
