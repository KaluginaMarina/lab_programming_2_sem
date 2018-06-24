package client

import client.gui.ClientGUI
import client.util.FrameErrorConnection
import model.Personage
import java.io.*
import java.net.ConnectException
import java.net.Socket
import java.net.SocketException
import java.rmi.UnknownHostException
import java.util.*
import java.util.concurrent.ConcurrentLinkedDeque

class Client(heroes: ConcurrentLinkedDeque<Personage>) : Runnable {

    @Volatile
    var heroes: ConcurrentLinkedDeque<Personage>? = null
        private set
    var gui: ClientGUI? = null

    init {
        this.heroes = heroes
    }

    /**
     * Клиет для работы с сервером
     */
    override fun run() {
        try {
            Socket("localhost", 27525).use { socket ->
                DataOutputStream(socket.getOutputStream()).use { dos ->
                    DataInputStream(socket.getInputStream()).use { dis ->
                        println("**Client connected to socket.")
                        val command = "refresh"

                        // код для автоматического обновления коллекции
                        while (!socket.isOutputShutdown) {
                            val oos = ObjectOutputStream(dos)
                            oos.writeObject(command)
                            oos.flush()
                            dos.flush()
                            val ois = ObjectInputStream(dis)
                            try {
                                val ob = ois.readObject()
                                val tmp = ob as ConcurrentLinkedDeque<Personage>
                                heroes = tmp
                            } catch (e: Exception) {
                                println("Ошибка при передаче объекта")
                            }
                            Thread.sleep(5000)
                        }
                    }
                }
            }
        } catch (e: FileNotFoundException) {
            println("Файл не найден")
        } catch (e: ConnectException) {
            println("**Ошибка соединения.")
            connecting()
        } catch (e: SocketException) {
            println("**Потеряно соединение с сервером.")
            connecting()
        } catch (e: NoSuchElementException) {
            println("**Потеряно соединение с сервером")
            connecting()
        } catch (e: EOFException) {
            println("**Конец передачи")
            connecting()
        } catch (e: UnknownHostException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

    }

    /**
     * Метод, вызываемыый при ошибке подключения
     */
    fun connecting() {

        val fec = FrameErrorConnection(this)
        fec.isVisible = true
        val tmp = ClientGUI(this, 100, 100, 1200, 600, gui!!.locale)
        if (gui != null) gui!!.dispose()
        gui = tmp
    }
}