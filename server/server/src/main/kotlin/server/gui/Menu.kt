package server.gui

import authorization.AutorizationGUI
import manage.Command
import javax.swing.JMenu
import javax.swing.JMenuItem

class Menu(gui : GUI) : JMenu("Меню"){
    init {
        val loadItem = JMenuItem("Обновить коллекцию с файла")
        loadItem.addActionListener{
            Command.load()
        }
        add(loadItem)

        val saveItem = JMenuItem("Сохрaнить коллекцию в файл")
        saveItem.addActionListener{
            Command.collectionSave()
        }
        add(saveItem)

        val logoutItem = JMenuItem("Смена пользователя")
        logoutItem.addActionListener{
            gui.isVisible = false
            AutorizationGUI()
            gui.dispose()
        }
        add(logoutItem)

        val closeItem = JMenuItem("Выход")
        closeItem.addActionListener{
            System.exit(0)
        }
        add(closeItem)
    }
}