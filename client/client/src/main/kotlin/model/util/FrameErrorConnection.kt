package client.util

import client.Client
import control.UTF8Control

import javax.swing.*
import java.awt.*
import java.awt.event.ActionEvent
import java.util.Locale
import java.util.ResourceBundle

import java.lang.System.exit

/**
 * Окно для обработки ошибки соединения
 */
class FrameErrorConnection(client: Client) : JFrame() {
    init {
        val locale = Locale.getDefault()
        val rb = ResourceBundle.getBundle("Resources", locale, UTF8Control())
        this.title = rb.getString("connection_error")
        this.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        this.setBounds(300, 300, 400, 150)
        this.isAlwaysOnTop = true

        val head = JLabel(rb.getString("connection_error_text"))
        val head2 = JLabel(rb.getString("reset_error"))
        head.foreground = Color.darkGray

        head.horizontalAlignment = JLabel.CENTER
        head2.horizontalAlignment = JLabel.CENTER

        val layout = GridBagLayout()
        this.layout = layout

        val comstr = GridBagConstraints()
        comstr.anchor = GridBagConstraints.LINE_END
        comstr.fill = GridBagConstraints.HORIZONTAL
        comstr.gridx = 0
        comstr.gridy = 0
        comstr.gridwidth = 2
        comstr.gridheight = 1
        this.add(head, comstr)

        comstr.gridy = 1
        this.add(head2, comstr)

        val resetBottom = JButton(object : AbstractAction(rb.getString("reset")) {
            override fun actionPerformed(e: ActionEvent) {
                this@FrameErrorConnection.dispose()
                client.run()
            }
        })
        val closeBottom = JButton(object : AbstractAction(rb.getString("exit")) {
            override fun actionPerformed(e: ActionEvent) {
                exit(0)
            }
        })

        comstr.gridx = 0
        comstr.gridy = 2
        comstr.gridwidth = 1
        comstr.gridheight = 1
        this.add(resetBottom, comstr)

        comstr.gridx = 1
        this.add(closeBottom, comstr)
    }

}
