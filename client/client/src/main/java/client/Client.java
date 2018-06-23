package client;

import client.gui.ClientGUI;
import client.util.FrameErrorConnection;
import model.Personage;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.rmi.UnknownHostException;
import java.util.NoSuchElementException;
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
        try (Socket socket = new Socket("localhost", 27525);
             DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
             DataInputStream dis = new DataInputStream(socket.getInputStream());) {
            System.out.println("**Client connected to socket.");
            String command = "refresh";
            int i = 0;

            // код для автоматического обновления коллекции
            while (!socket.isOutputShutdown()){
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
                    System.out.println("Ошибка при передаче объекта");
                }
                synchronized (this) {wait(5000);}
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
     * Метод, вызываемыый при ошибке подключения
     */
    public void connecting(){

        FrameErrorConnection fec = new FrameErrorConnection(this);
        fec.setVisible(true);
        ClientGUI tmp = new ClientGUI(this, 100, 100, 1200, 600, gui.locale);
        if (gui != null) gui.dispose();
        gui = tmp;
    }
}