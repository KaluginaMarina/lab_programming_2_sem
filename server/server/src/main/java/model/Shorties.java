package model;

import java.time.LocalDateTime;

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
}
