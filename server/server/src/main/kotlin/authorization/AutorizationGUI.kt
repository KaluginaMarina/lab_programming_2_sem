package authorization

import org.mindrot.jbcrypt.BCrypt
import server.gui.GUI
import java.awt.Color
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.Rectangle
import java.io.File
import javax.swing.*

class AutorizationGUI : JFrame("Авторизация"){
    init {
        bounds = Rectangle(400, 100, 300, 150)
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        isResizable = false

        val headLabel = JLabel("Для входа введите логин и пароль.")
        headLabel.foreground = Color.darkGray
        val loginTextFiled = JTextField("Логин", 25)
        loginTextFiled.foreground = Color.gray
        val passwordFiled = JPasswordField("Пароль", 25)
        passwordFiled.foreground = Color.gray
        val button = JButton("Войти")
        val logins = File("materials/logins.csv").readLines().map{ it.split(",") }
        button.addActionListener {
            val check = logins.any {
                it[0] == loginTextFiled.text && BCrypt.checkpw(String(passwordFiled.password), it[1])
            }
            if (check) {
                this@AutorizationGUI.isVisible = false
                GUI()
                dispose()
            } else {
                loginTextFiled.background = Color(255, 156, 140)
                passwordFiled.background = Color(255, 156, 140)
            }
        }

        layout = GridBagLayout()
        var c = GridBagConstraints()
        c.fill = GridBagConstraints.HORIZONTAL
        c.gridx = 0
        c.gridy = 0
        this.add(headLabel, c)

        c.gridy = 1
        this.add(loginTextFiled, c)

        c.gridy = 2
        this.add(passwordFiled, c)

        c.gridy = 3
        this.add(button, c)
        isVisible = true
    }
}