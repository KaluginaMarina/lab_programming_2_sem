package server.gui

import authorization.AutorizationGUI
import javax.swing.JMenu
import javax.swing.JMenuItem

class Menu(gui : GUI) : JMenu("Меню"){
    init {
        val loadItem = JMenuItem("Обновить коллекцию с файла")
        loadItem.addActionListener{
            manage.Command.load()
            gui.tree.refresh()
        }
        this.add(loadItem)

        val saveItem = JMenuItem("Сохрпнить коллекцию в файл")
        saveItem.addActionListener{
            manage.Command.collectionSave()
        }
        this.add(saveItem)

        val logoutItem = JMenuItem("Смена пользователя")
        logoutItem.addActionListener{
            gui.isVisible = false
            AutorizationGUI()
            gui.dispose()
        }
        this.add(logoutItem)

        val closeItem = JMenuItem("Выход")
        closeItem.addActionListener{
            System.exit(0)
        }
        this.add(closeItem)
    }
}