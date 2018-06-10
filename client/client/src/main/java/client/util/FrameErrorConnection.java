package client.util;

import client.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import static java.lang.System.exit;

/**
 *  Окно для обработки ошибки соединения
 */
public class FrameErrorConnection extends JFrame {
    public FrameErrorConnection(Client client){
        super("Ошибка соединения.");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(300, 300, 400, 150);
        this.setAlwaysOnTop(true);

        JLabel head = new JLabel("Произошла ошибка соединения.");
        JLabel head2 = new JLabel("Попробовать еще раз?");
        head.setForeground(Color.darkGray);

        head.setHorizontalAlignment(JLabel.CENTER);
        head2.setHorizontalAlignment(JLabel.CENTER);

        GridBagLayout layout = new GridBagLayout();
        this.setLayout(layout);

        GridBagConstraints comstr = new GridBagConstraints();
        comstr.anchor = GridBagConstraints.LINE_END;
        comstr.fill = GridBagConstraints.HORIZONTAL;
        comstr.gridx = 0;
        comstr.gridy = 0;
        comstr.gridwidth = 2;
        comstr.gridheight = 1;
        this.add(head, comstr);

        comstr.gridy = 1;
        this.add(head2, comstr);

        JButton resetBottom = new JButton(new AbstractAction("Обновить") {
            @Override
            public void actionPerformed(ActionEvent e) {
                FrameErrorConnection.this.dispose();
                client.run();
            }
        });
        JButton closeBottom = new JButton(new AbstractAction("Выйти") {
            @Override
            public void actionPerformed(ActionEvent e) {
                exit(0);
            }
        });

        comstr.gridx = 0;
        comstr.gridy = 2;
        comstr.gridwidth = 1;
        comstr.gridheight = 1;
        this.add(resetBottom, comstr);

        comstr.gridx = 1;
        this.add(closeBottom, comstr);
    }

}
