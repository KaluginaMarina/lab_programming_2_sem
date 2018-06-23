package server;

import manage.Command;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class Server implements Runnable{
    private Socket client;

    public Server (Socket client){
        this.client = client;
    }

    @Override
    public void run() {
        try /*(ServerSocket serverSocket = new ServerSocket(8080))*/ {
            //Socket client = serverSocket.accept();
            System.out.println("**Connection accepted.");
            DataOutputStream out = new DataOutputStream(client.getOutputStream());
            DataInputStream in = new DataInputStream(client.getInputStream());
            while(!client.isClosed()){
                ObjectInputStream ois = new ObjectInputStream(in);
                Object ob = ois.readObject();
                try {
                    String tmp = (String) ob;
                    System.out.println("\n**Получено...");
                    System.out.println(tmp);
                    ObjectOutputStream oos = new ObjectOutputStream(out);
                    oos.writeObject(Command.INSTANCE.getHeroes());
                    oos.flush();
                    out.flush();
                } catch (ClassCastException e) {
                    System.out.println("Ошибка. Команда не команда");
                }
            }
            in.close();
            out.close();
            client.close();
        } catch (SocketException e){
            System.out.println("**Конец передачи.");
        } catch (EOFException e){
            System.out.println("**Конец передачи.");
        } catch (IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}