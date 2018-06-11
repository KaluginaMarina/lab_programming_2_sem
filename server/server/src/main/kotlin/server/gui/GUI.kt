package server.gui

import model.Personage
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.Rectangle
import java.util.concurrent.ConcurrentLinkedDeque
import javax.swing.JFrame
import javax.swing.JMenuBar

class GUI(heroes : ConcurrentLinkedDeque<Personage>) : JFrame("SERVER"){
    var tree = PersonageTree(heroes)

    init {
        bounds = Rectangle(400, 100, 500, 500)
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        isResizable = false

        val menuBar = JMenuBar()
        menuBar.add(Menu(this))
        jMenuBar = JMenuBar()
        jMenuBar.add(menuBar)

        layout = GridBagLayout()
        val c = GridBagConstraints()
        c.gridx = 1
        c.gridy = 0
        this.add(tree, c)

        isVisible = true
    }
}