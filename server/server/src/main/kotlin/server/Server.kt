package server

import manage.Command

import java.io.*
import java.net.Socket
import java.net.SocketException

class Server(private val client: Socket) : Runnable {

    override fun run() {
        try
        /*(ServerSocket serverSocket = new ServerSocket(8080))*/ {
            //Socket client = serverSocket.accept();
            println("**Connection accepted.")
            val out = DataOutputStream(client.getOutputStream())
            val `in` = DataInputStream(client.getInputStream())
            while (!client.isClosed) {
                val ois = ObjectInputStream(`in`)
                val ob = ois.readObject()
                try {
                    val tmp = ob as String
                    println("\n**Получено...")
                    println(tmp)
                    val oos = ObjectOutputStream(out)
                    oos.writeObject(Command.heroes)
                    oos.flush()
                    out.flush()
                } catch (e: ClassCastException) {
                    println("Ошибка. Команда не команда")
                }

            }
            `in`.close()
            out.close()
            client.close()
        } catch (e: SocketException) {
            println("**Конец передачи.")
        } catch (e: EOFException) {
            println("**Конец передачи.")
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }

    }
}