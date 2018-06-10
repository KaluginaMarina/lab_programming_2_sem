package client.util;

import model.Personage;

import java.util.concurrent.ConcurrentLinkedDeque;

public class ManageCollection {
    public static int maxPower(ConcurrentLinkedDeque<Personage> heroes) {
        ConcurrentLinkedDeque<Personage> c = new ConcurrentLinkedDeque<Personage>();
        c.addAll(heroes);
        int res = 0;
        while (!c.isEmpty()) {
            int tmp = c.removeFirst().force;
            if (tmp > res) {
                res = tmp;
            }
        }
        return res;
    }

    public static int maxHeight(ConcurrentLinkedDeque<Personage> heroes) {
        ConcurrentLinkedDeque<Personage> c = new ConcurrentLinkedDeque<Personage>();
        c.addAll(heroes);
        int res = 0;
        while (!c.isEmpty()) {
            int tmp = c.removeFirst().height;
            if (tmp > res) {
                res = tmp;
            }
        }
        return res;
    }

    public static double maxX(ConcurrentLinkedDeque<Personage> heroes) {
        ConcurrentLinkedDeque<Personage> c = new ConcurrentLinkedDeque<Personage>();
        c.addAll(heroes);
        if (c.isEmpty()){
            return 0;
        }
        double res = Double.MIN_VALUE;
        while (!c.isEmpty()) {
            double tmp = c.removeFirst().x;
            if (tmp > res) {
                res = tmp;
            }
        }
        return res;
    }

    public static double minX(ConcurrentLinkedDeque<Personage> heroes) {
        ConcurrentLinkedDeque<Personage> c = new ConcurrentLinkedDeque<Personage>();
        c.addAll(heroes);
        if (c.isEmpty()){
            return 0;
        }
        double res = Double.MAX_VALUE;
        while (!c.isEmpty()) {
            double tmp = c.removeFirst().x;
            if (tmp < res) {
                res = tmp;
            }
        }
        return res;
    }

    public static double maxY(ConcurrentLinkedDeque<Personage> heroes) {
        ConcurrentLinkedDeque<Personage> c = new ConcurrentLinkedDeque<Personage>();
        c.addAll(heroes);
        if (c.isEmpty()){
            return 0;
        }
        double res = Double.MIN_VALUE;
        while (!c.isEmpty()) {
            double tmp = c.removeFirst().y;
            if (tmp > res) {
                res = tmp;
            }
        }
        return res;
    }

    public static double minY(ConcurrentLinkedDeque<Personage> heroes){
        ConcurrentLinkedDeque<Personage> c = new ConcurrentLinkedDeque<Personage>();
        c.addAll(heroes);
        if (c.isEmpty()){
            return 0;
        }
        double res = Double.MAX_VALUE;
        while (!c.isEmpty()){
            double tmp = c.removeFirst().y;
            if (tmp < res){
                res = tmp;
            }
        }
        return res;
    }

    public static double minHeight(ConcurrentLinkedDeque<Personage> heroes){
        ConcurrentLinkedDeque<Personage> c = new ConcurrentLinkedDeque<Personage>();
        c.addAll(heroes);
        if (c.isEmpty()){
            return 0;
        }
        double res = Double.MAX_VALUE;
        while (!c.isEmpty()){
            double tmp = c.removeFirst().height;
            if (tmp < res){
                res = tmp;
            }
        }
        return res;
    }
}
