package client;

import model.Moonlighter;
import model.Personage;
import model.Reader;
import model.Shorties;

import java.util.Date;
import java.util.Locale;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.stream.Collectors;

public class CollectionCreater {

    public static ConcurrentLinkedDeque<Personage> collectionCreater(String heroesIn){
        ConcurrentLinkedDeque<Personage> heroes = new ConcurrentLinkedDeque<>();
        Scanner sc = new Scanner(heroesIn);
        sc.useDelimiter("[,\n]");
        sc.useLocale(Locale.ENGLISH);
        try {
            while(sc.hasNext()){
                String type = sc.next();
                switch (type){
                    case "Читатель": {
                        Reader reader = new Reader(sc.next());
                        reader.height = sc.nextInt();
                        reader.force = sc.nextInt();
                        if (!reader.setMood(sc.next())){
                            throw new Exception();
                        }
                        heroes.add(reader);
                        break;
                    }
                    case "Лунатик": {
                        Moonlighter moonlighter = new Moonlighter(sc.next(), sc.nextDouble(), sc.nextDouble(), sc.nextInt());
                        moonlighter.skillSwear = sc.nextInt();
                        moonlighter.force = sc.nextInt();
                        if (!moonlighter.setMood(sc.next())){
                            throw new Exception();
                        }
                        heroes.add(moonlighter);
                        break;
                    }
                    case "Коротышка": {
                        Shorties shorties = new Shorties(sc.next(), sc.nextDouble(), sc.nextDouble(), sc.nextInt());
                        shorties.skillSwear = sc.nextInt();
                        shorties.force = sc.nextInt();
                        if (!shorties.setMood(sc.next())){
                            throw new Exception();
                        }
                        heroes.add(shorties);
                        break;
                    }
                    default: {
                        throw new Exception();
                    }
                }
            }
            heroes = heroes.stream().sorted().collect(Collectors.toCollection(ConcurrentLinkedDeque::new));
            return heroes;
        } catch (Exception e){
            System.out.println("Неправильное представление объекта.");
            return null;
        }
    }
}
