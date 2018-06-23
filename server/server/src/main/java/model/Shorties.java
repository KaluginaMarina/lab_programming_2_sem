package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

public class Shorties extends Personage {

    public Shorties (String name, double x, double y, int height){
        super( "Коротышка", name, x, y, 3, height, 5, Mood.NORMAL, LocalDateTime.now(), 0);
    }

    /**
     * Сравнение коротышек.
     * @param s - второй коротышка
     * @return true - коротышки похожи, false - разные коротышки
     */
    public boolean compare(Shorties s){
        if (equals(s)) {
            System.out.println(this.getType() + " похож на " + this.getName() + ".");
            return true;
        }
        else {
            System.out.println(this.getType() + " не похож на " + this.getName() + ".");
            return false;
        }
    }

    public interface Cometo{
        public double coordinate(Personage a, Personage b);
    }
    public void come(Personage p){

        Cometo xy = new Cometo(){
            @Override
            public double coordinate(Personage who, Personage where){
                if (who.getX() - where.getX() < 0){
                    return where.getX() + Math.random()*10;
                }
                else if (who.getX() - where.getX() > 0){
                    return where.getX() - Math.random()*10;
                }
                else {
                    return where.getX();
                }
            }
        };
        this.setX(xy.coordinate(this, p));
        this.setY(xy.coordinate(this, p));
        System.out.println("Какой-то " + this.getType() + " подошел к " + p.getName() + ".");
    }

    /**
     * Коротышка скрылся за деревом
     */
    public void goForThree(Tree t){
        this.setX(t.x);
        this.setY(t.y);
        System.out.println(this.getType() + " скрылся за " + t.name + ".");
    }
}
