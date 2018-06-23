package server.gui

import java.awt.GridLayout
import java.awt.Rectangle
import javax.swing.JFrame
import javax.swing.JMenuBar

class GUI() : JFrame("SERVER"){
    var personageChange =  PersonageChange(this)
    var tree = PersonageTree(personageChange)
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
        add(personageChange)
        isVisible = true
    }
}