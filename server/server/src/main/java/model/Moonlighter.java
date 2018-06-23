package model;

import java.time.LocalDateTime;

public class Moonlighter extends Personage{

    public Moonlighter (String name, double x, double y, int height){
        super("Лунатик", name, x, y, 7, height, 10, Mood.NORMAL, LocalDateTime.now(), 0);
    }
}
