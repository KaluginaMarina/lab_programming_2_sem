package server.gui

import model.Personage
import java.awt.GridLayout
import java.awt.Rectangle
import java.util.concurrent.ConcurrentLinkedDeque
import javax.swing.JFrame
import javax.swing.JMenuBar

class GUI(heroes : ConcurrentLinkedDeque<Personage>) : JFrame("SERVER"){
    var tree = PersonageTree(heroes)

    init {
        bounds = Rectangle(400, 100, 550, 400)
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        isResizable = false

        val menuBar = JMenuBar()
        menuBar.add(Menu(this))
        jMenuBar = JMenuBar()
        jMenuBar.add(menuBar)

        layout = GridLayout(1, 2)

        add(javax.swing.JScrollPane(tree))
        add(PersonageChange())
        isVisible = true
    }
}