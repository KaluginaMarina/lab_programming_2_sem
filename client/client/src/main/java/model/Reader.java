package model;


import java.time.LocalDateTime;

public class Reader extends Personage {
   public Reader(String name) {
       super( "Читатель", name, 0.0, 0.0, 0, 158, 0, Mood.NORMAL, LocalDateTime.now(), 0);
    }
}
