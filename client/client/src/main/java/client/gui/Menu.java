package client.gui;

import control.UTF8Control;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;

public class Menu extends JMenu {
    Menu (ClientGUI gui){
        super();
        Locale locale = Locale.getDefault();
        ResourceBundle rb = ResourceBundle.getBundle("Resources", locale, new UTF8Control());
        this.setText(rb.getString("language"));

        JMenuItem ru_Ru = new JMenuItem("Русский");
        ru_Ru.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gui.changeLanguage(new Locale("ru", "RU"));
            }
        });
        this.add(ru_Ru);

        JMenuItem no_NO = new JMenuItem("Norsk");
        no_NO.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gui.changeLanguage(new Locale("no", "NO"));
            }
        });
        this.add(no_NO);

        JMenuItem fr_CA = new JMenuItem("Français");
        fr_CA.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gui.changeLanguage(new Locale("fr", "CA"));
            }
        });
        this.add(fr_CA);

        JMenuItem es_PR = new JMenuItem("Español");
        es_PR.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gui.changeLanguage(new Locale("es", "PR"));
            }
        });
        this.add(es_PR);
    }

    public void changeLanguage(Locale locale){
        ResourceBundle rb = ResourceBundle.getBundle("Resources", locale, new UTF8Control());
        this.setText(rb.getString("language"));
    }
}
