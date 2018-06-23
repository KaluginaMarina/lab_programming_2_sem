package manage;

import model.*;
import model.Reader;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.stream.Collectors;

import static java.lang.Math.toIntExact;


abstract class CollectionManage {
    public ConcurrentLinkedDeque<Personage> heroes = new ConcurrentLinkedDeque<>();
    Date createDate;
    Date changeDate;

    //private final String fileName = "materials/Heroes.csv";
    //private final String fileNameClosing = "materials/HeroesClosing.csv";
    private final String fileName = System.getenv("FILENAME");
    private final String fileNameClosing = System.getenv("FILENAMECLOSE");

    public ConcurrentLinkedDeque<Personage> getHeroes() {
        return heroes;
    }

    /**
     * Метод для чтения текста из файла fileName
     * @return String heroesJson - строка-содержимое файла
     */
    public String read(String fileName){
        String heroesJson = "";
        try (FileInputStream fis = new FileInputStream(fileName);
             InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
             BufferedReader br = new BufferedReader(isr)
        )
        {
            String line;

            while ((line = br.readLine()) != null) {
                heroesJson += line + "\n";
            }
        } catch (Exception e) {
            System.out.println("Неправильный путь для FILENAME.");
        }
        return heroesJson;
    }

    /**
     * Метод, создающий коллекцию Personage по данным из файла fileName
     * @return true, false
     */
    public boolean collectionCreater(){
        String heroesJson = read(fileName);
        Scanner sc = new Scanner(heroesJson);
        sc.useDelimiter("[,\n]");
        sc.useLocale(Locale.ENGLISH);
        try {
            while(sc.hasNext()){
                String type = sc.next();
                switch (type){
                    case "Читатель": {
                        Reader reader = new Reader(sc.next());
                        reader.setHeight(sc.nextInt());
                        reader.setForce(sc.nextInt());
                        if (!reader.setMood(sc.next())){
                            throw new Exception();
                        }
                        heroes.add(reader);
                        break;
                    }
                    case "Лунатик": {
                        Moonlighter moonlighter = new Moonlighter(sc.next(), sc.nextDouble(), sc.nextDouble(), sc.nextInt());
                        moonlighter.setSkillSwear(sc.nextInt());
                        moonlighter.setForce(sc.nextInt());
                        if (!moonlighter.setMood(sc.next())){
                            throw new Exception();
                        }
                        heroes.add(moonlighter);
                        break;
                    }
                    case "Коротышка": {
                        Shorties shorties = new Shorties(sc.next(), sc.nextDouble(), sc.nextDouble(), sc.nextInt());
                        shorties.setSkillSwear(sc.nextInt());
                        shorties.setForce(sc.nextInt());
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
            changeDate = new Date();
            return true;
        } catch (Exception e){
            System.out.println("Неправильное представление объекта.");
            return false;
        }
    }

    /**
     * Метод, записывающий текущее состояние коллекции в файл fileNameClose
     */
    public void collectionSave(){
        if (!Files.isWritable(Paths.get(fileNameClosing))){
            System.out.println("Что-то с правами");
            return;
        }

        try (FileWriter file = new FileWriter(fileNameClosing, false)){
            String toCsv = toSCV();
            file.write(toCsv);
        } catch (Exception e){
            System.out.println("Неправильный путь для FILENAMECLOSE." + e.getMessage());
        }
    }

    /**
     * Метод возвращает копию объекта ConcurrentLinkedDeque<Personage>
     * (тут вроде нельзя пользоваться clone)
     * @param c - копируемый объект
     * @return ConcurrentLinkedDeque<Personage> clone
     */
    private ConcurrentLinkedDeque<Personage> copy (ConcurrentLinkedDeque c){
        ConcurrentLinkedDeque<Personage> res = new ConcurrentLinkedDeque<>();
        ConcurrentLinkedDeque<Personage> tmp = new ConcurrentLinkedDeque<>();
        while (!c.isEmpty()){
            Personage pers = (Personage) c.pollFirst();
            res.add(pers);
            tmp.add(pers);
        }
        heroes = tmp;
        return res;
    }

    /**
     * Метод, переаодящий строку в строку, формата scv
     * @return String - строка, формата scv
     */
    public String toSCV(){
        return heroes.stream()
                .map(x -> ((x.getType().equals("Читатель")) ? x.getType() + "," + x.getName() + "," + x.getHeight()  + "," + x.getForce() + "," + x.getMood() + "\n" : x.getType() + "," + x.getName() + "," + x.getX() + "," + x.getY() + "," + x.getHeight() + "," + x.getSkillSwear() + "," + x.getForce() + "," + x.getMood() + "\n"))
                .collect(Collectors.joining());
    }

    /**
     * remove_greater {element}: удалить из коллекции все элементы, превышающие заданный
     * Формат задания элемента {element}- json
     * При вводе {element} другого формата или при вводе некорректного представления объекта - бросается исключение
     * @return true - успешное выполнение команды, false - при возникновении ошибки
     */
    abstract public boolean remove_greater(Personage pers);

    /**
     * add_if_max {element}: добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции
     * add_if_min {element}: добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции
     * Формат задания элемента {element}- json
     * При вводе {element} другого формата или при вводе некорректного представления объекта - бросается исключение
     * @param command "add_if_max" или "add_if_min"
     * @return true - успешное выполнение команды, false - при возникновении ошибки
     */
    abstract public boolean addIf(String command, Personage pers);

    /**
     * info: вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, дата изменения, количество элементов)
     */
    abstract public String info();

    /**
     *  load - перечитать коллекцию из файла
     * @return true - успешное выполнение команды, false - при возникновении ошибки
     */
    abstract public boolean load();

    /**
     * add {element} Метод для добавления элемента в коллекцию в интерактивном режиме
     * Формат задания элемента {element}- json
     * При вводе {element} другого формата или при вводе некорректного представления объекта - бросается исключение
     * @return true - успешное выполнение команды, false - при возникновении ошибки
     */
    abstract public boolean add(Personage pers);


    /**
     * remove_last - удаляет последний элемент
     */
    abstract public void removeLast();
}