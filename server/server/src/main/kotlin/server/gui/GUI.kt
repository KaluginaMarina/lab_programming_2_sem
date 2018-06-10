package server.gui

import java.awt.Rectangle
import javax.swing.JFrame

class GUI : JFrame("SERVER"){
    init {
        bounds = Rectangle(400, 100, 500, 500)
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        isResizable = false

        isVisible = true
    }
}