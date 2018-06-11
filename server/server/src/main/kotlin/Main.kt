import authorization.AutorizationGUI
import manage.Command
import server.manage.Manage
import server.Server

import java.net.ServerSocket
import java.util.concurrent.Executors


var executeIt = Executors.newFixedThreadPool(2)

fun main(args: Array<String>) {

    val manage = Manage()
    val manager = Thread(manage)
    manager.start()

    Command.collectionCreater()

    Runtime.getRuntime().addShutdownHook(Thread { Command.collectionSave() })

    var gui = AutorizationGUI()
    println("Сервер начал работу.")
    try {
        ServerSocket(8080).use { server ->
            while (!server.isClosed) {
                val client = server.accept()
                executeIt.execute(Server(client))
                print("Connection accepted.")
            }
            executeIt.shutdown()

        }
    } catch (e: Exception) {
        println("exp e")
    }

}