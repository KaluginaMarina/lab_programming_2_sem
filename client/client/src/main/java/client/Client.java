package client;

import client.gui.ClientGUI;
import client.util.Command;
import client.util.CommandType;
import client.util.FrameErrorConnection;
import model.Personage;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.UnknownHostException;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedDeque;

public class Client implements Runnable{

    private volatile ConcurrentLinkedDeque<Personage> heroes;
    public ClientGUI gui;

    public Client (ConcurrentLinkedDeque<Personage> heroes){
        this.heroes = heroes;
    }

    public ConcurrentLinkedDeque<Personage> getHeroes(){
        return heroes;
    }

    /**
     * Клиет для работы с сервером
     */
    @Override
    public void run() {
        try (Socket socket = new Socket("localhost", 8080);
             //FileInputStream fis = new FileInputStream("materials/script.txt");
             //InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
             BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
             DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
             DataInputStream dis = new DataInputStream(socket.getInputStream());) {
            System.out.println("**Client connected to socket.");
            String clientCommand;
            Command command;
            int i = 0;

            // код для автоматического обновления коллекции
            while (!socket.isOutputShutdown()){
                command = new Command(CommandType.PRINT);
                ObjectOutputStream oos = new ObjectOutputStream(dos);
                oos.writeObject(command);
                oos.flush();
                dos.flush(); ObjectInputStream ois = new ObjectInputStream(dis);
                try {
                    Object ob = ois.readObject();
                    ConcurrentLinkedDeque<Personage> tmp = (ConcurrentLinkedDeque<Personage>) ob;
                    heroes = tmp;
                    //System.out.println(heroes);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                synchronized (this) {wait(5000);}
            }

            // код для работы с коллекцией
            while (!socket.isOutputShutdown()) {
                if (i == 0) {
                    System.out.println("Введите команду.\nhelp/? - открыть справку.");
                    ++i;
                }
                if (br.ready()) {
                    clientCommand = br.readLine();
                    command = Command.readCommand(clientCommand);
                    if (command.commandType == CommandType.HELP) {
                        String man = read("materials/man.txt");
                        System.out.println(man);
                        continue;
                    } else if (command.commandType == CommandType.NOTPERS) {
                        System.out.println("Неверное представление объекта. Объект должен быть в формате json.");
                        continue;
                    }
                    if (command.commandType == CommandType.NOTCOMMAND) {
                        System.out.println("Неизвестная команда.\nhelp / ?: открыть справку");
                        continue;
                    }
                    ObjectOutputStream oos = new ObjectOutputStream(dos);
                    oos.writeObject(command);
                    System.out.println("**Отправлено...");
                    System.out.println(command + "\n");
                    oos.flush();
                    dos.flush();
                    if (clientCommand.equalsIgnoreCase("quit")) {
                        if (dis.read() > -1) {
                            String in = dis.readUTF();
                            System.out.println(in);
                        }
                        break;
                    }
                    System.out.println("**Получено...");
                    ObjectInputStream ois = new ObjectInputStream(dis);
                    try {
                        Object ob = ois.readObject();
                        try {
                            ConcurrentLinkedDeque<Personage> tmp = (ConcurrentLinkedDeque<Personage>) ob;
                            heroes = tmp;
                            System.out.println(heroes);
                        } catch (ClassCastException e) {
                            String tmp = (String) ob;
                            System.out.println(tmp);
                            if (command.commandType == CommandType.INFO) {
                                infoSave(tmp);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    i = 0;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден");
        } catch (ConnectException e) {
            System.out.println("**Ошибка соединения.");
            connecting();
        } catch (SocketException e) {
            System.out.println("**Потеряно соединение с сервером.");
            connecting();
        } catch (NoSuchElementException e){
            System.out.println("**Потеряно соединение с сервером");
            connecting();
        } catch (EOFException e) {
            System.out.println("**Конец передачи");
            connecting();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод дл я чтения с файла
     * @param fileName - путь до файла
     * @return строка - содержимое файла
     */
    private static String read(String fileName) {
        String str = "";
        try (FileInputStream fis = new FileInputStream(fileName);
             InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
             BufferedReader br = new BufferedReader(isr)
        ) {
            String line;

            while ((line = br.readLine()) != null) {
                str += line + "\n";
            }
        } catch (Exception e) {
            System.out.println("Неправильный путь до." + fileName);
        }
        return str;
    }

    /**
     * Клиент для работы без соединения
     */
    public void runWithoutConnection() {
        Scanner input = new Scanner(System.in);
        String line;
        while (true) {
            System.out.println("Введите команду.");
            System.out.println("help/? - открыть справку.");
            line = input.nextLine();
            Command command = Command.readCommand(line);
            switch (command.commandType) {
                case ADD: {
                }
                case LOAD: {
                }
                case PRINT: {
                }
                case ADD_IF_MAX: {
                }
                case ADD_IF_MIN: {
                }
                case REMOVE_LAST: {
                }
                case REMOVE_GREATER: {
                    System.out.println("Недоступно");
                    break;
                }
                case NOTCOMMAND: {
                    System.out.println("Неизвестная команда.");
                    break;
                }
                case NOTPERS: {
                    System.out.println("Неверное представление объекта. Объект должен быть в формате json.");
                    break;
                }
                case CONNECTION_AGAIN: {
                    run();
                    break;
                }
                case HELP: {
                    String man = read("materials/manForWithoutConnection");
                    System.out.println(man);
                    break;
                }
                case INFO: {
                    String info = read("materials/info");
                    System.out.println(info);
                    break;
                }
                case QUIT: {
                    return;
                }
            }
        }
    }

    /**
     * метод, который сохраняет информацию об объекте в определенный файл
     * @param str - строка - инфо
     */
    public void infoSave(String str) {
        final String infoFile = "materials/info";
        if (!Files.isWritable(Paths.get(infoFile))) {
            System.out.println("Что-то с правами");
            return;
        }

        try (FileWriter file = new FileWriter(infoFile, false)) {
            str += "\nИнформация о коллекции на момент: " + new Date();
            file.write(str);
        } catch (Exception e) {
            System.out.println("Неправильный путь для info." + e.getMessage());
        }
    }

    /**
     * Метод, вызываемыый при ошибке подключения
     */
    public void connecting(){

        FrameErrorConnection fec = new FrameErrorConnection(this);
        fec.setVisible(true);
        if (gui != null) gui.dispose();
        gui = new ClientGUI(this, 100, 100, 1200, 600);
    }






//    public void connecting(ConcurrentLinkedDeque<Personage> heroes){
//
//        System.out.println("Попробовать еще раз? Y/N");
//        Scanner input = new Scanner(System.in);
//        String command = input.nextLine();
//        while (true) {
//            if (command.equals("Y")) {
//                run();
//            } else if (command.equals("N") || command.equals("quit")) {
//                System.out.println("Продолжить работу без соединения? Y/N");
//                while (true) {
//                    command = input.nextLine();
//                    if (command.equals("Y")) {
//                        runWithoutConnection(heroes);
//                        return;
//                    } else if (command.equals("N")) {
//                        input.close();
//                        return;
//                    } else {
//                        System.out.println("Неизвестная команда.");
//                    }
//                }
//            } else if (!command.equals("Y")) {
//                while (!command.equals("Y") && !command.equals("Y") || command.equals("quit")) {
//                    System.out.println("Неизвестная команда.");
//                    command = input.nextLine();
//                }
//            }
//        }


}