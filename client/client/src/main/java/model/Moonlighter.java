package model;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static model.Mood.*;

public class Moonlighter extends Personage{

    class unchException extends RuntimeException{
        unchException(String msg){
            super(msg);
        }
    }

    public Moonlighter (String name, double x, double y, int height){
        super("Лунатик", name, x, y, 7, height, 10, Mood.NORMAL, LocalDateTime.now(), 0);
    }

    /**
     *  Поднять голову
     */
    public void headUp() {
        System.out.println(this.getName() + " поднял голову.");
    }

    static enum ShortiesType{
        NEAR, FAR;
    }

    /**
     * Увидеть коротышку
     */
    public void seeShorties(Shorties k, Shorties... p ){
        /**
         * Локальный класс, который определяет расстояние между коротышками и говорит дадеко ли они от нас находятся
         */
        class Distance{
             private ShortiesType far(Moonlighter k, Shorties p){
                double s = Math.sqrt( (k.getX()-p.getX())*(k.getX()-p.getX()) + (k.getY()-p.getY())*(k.getY()-p.getY()) );
                if (s > 10 && s <= 50){
                    return ShortiesType.FAR;
                }
                else {
                    return ShortiesType.NEAR;
                }
             }
             private int shortiesCounter(ShortiesType type, Moonlighter k, Shorties... p){
                 int counter = 0;
                 for (int i = 0; i < p.length; ++i){
                     if (this.far(k, p[i]) == type) {
                         counter++;
                     }
                 }
                 return counter;
             }
             private int  shortiesCountFar(Moonlighter k, Shorties... p){
                 return shortiesCounter(ShortiesType.FAR, k, p);
             }
             private int shortiesCountNear(Moonlighter k, Shorties... p){
                 return shortiesCounter(ShortiesType.NEAR, k, p);
             }
        }
        Distance d = new Distance();
        int near = d.shortiesCountFar(this, p), far = d.shortiesCountNear(this, p), longAway = p.length - far - near;

        if (far > 0 || near > 0) {
            System.out.println(this.getName() + " увидел " + ((far == 0) ? "вблизи " : "вдалеке ") + p.length + " коротышек.");
        } else {
            System.out.println(this.getName() + " не увидел " + "коротышек.");
        }
        this.feel();
        for (int i = 0; i < p.length; ++i){
            p[i].compare(k);
        }
    }

    /**
     * Метод, который меняет настроение
     * @param newMood - новое настроение персонажа
     */
    public void changeMood(Mood newMood){
        switch (newMood){
            case NORMAL: {
                System.out.println("У " + this.getName() + " снова пришел в норму.");
                break;
            }
            case SAD: {
                System.out.println(this.getName() + " расстроился.");
                break;
            }
            case FURY: {
                System.out.println(this.getName() + " пришел в бешенство.");
                break;
            }
            case HAPPY: {
                System.out.println(this.getName() + " обрадовался.");
                break;
            }
            default: {
                System.out.println("У " + this.getName() + " что-то случилось с настроением.");
            }
        }
    }
}
