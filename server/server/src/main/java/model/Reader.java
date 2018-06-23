package model;


import orm.Table;

import java.time.LocalDateTime;
import java.util.Date;

public class Reader extends Personage {
   public Reader(String name) {
       super( "Читатель", name, 0.0, 0.0, 0, 158, 0, Mood.NORMAL, LocalDateTime.now(), 0);
    }
}
