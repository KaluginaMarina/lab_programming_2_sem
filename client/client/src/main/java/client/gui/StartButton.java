package client.gui;

import client.Client;
import control.UTF8Control;
import model.Mood;
import model.Personage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.stream.Collectors;

public class StartButton implements ActionListener{
    private ClientGUI gui;
    private int heightMaxValue;
    private Client client;
    ConcurrentLinkedDeque<Personage> heroesFilter;
    private Locale locale;

    public StartButton(ClientGUI gui, int heightMaxValue, Client client, Locale locale){
        this.client = client;
        this.gui = gui;
        this.heightMaxValue = heightMaxValue;
        heroesFilter = client.getHeroes();
        this.locale = locale;
    }

    @Override
    public void actionPerformed(ActionEvent e){
        class ErrorFrame extends JFrame {
            private ErrorFrame(String text){
                super();
                ResourceBundle rb = ResourceBundle.getBundle("Resources", locale, new UTF8Control());
                this.setTitle(rb.getString("error"));
                this.setBounds(400, 300, 450, 100);
                this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                JLabel info = new JLabel(text);
                info.setForeground(Color.darkGray);
                GridBagLayout gridBagLayout = new GridBagLayout();
                this.setLayout(gridBagLayout);
                GridBagConstraints c = new GridBagConstraints();
                c.fill = GridBagConstraints.HORIZONTAL;
                this.add(info, c);
            }
        }

        ResourceBundle rb = ResourceBundle.getBundle("Resources", locale, new UTF8Control());

        //вернуть все цвета
        gui.nameFromSpinner.setBackground(null);
        gui.nameToSpinner.setBackground(null);
        gui.powerFromSpinner.setBackground(null);
        gui.powerToSpinner.setBackground(null);
        gui.heightFromTextField.setBackground(null);
        gui.heightToTextField.setBackground(null);

        boolean error = false;
        if (gui.startButton.getText().equals(rb.getString("start"))) {
            //обработка ошибок
            if (gui.nameFromSpinner.getValue().toString().compareTo(gui.nameToSpinner.getValue().toString()) > 0) {
                ErrorFrame nameSpinnerError = new ErrorFrame(rb.getString("nameFromSpinner>nameToSpinner"));
                nameSpinnerError.setVisible(true);
                gui.nameFromSpinner.setBackground(new Color(255, 76, 76));
                gui.nameToSpinner.setBackground(new Color(255, 76, 76));
                error = true;
            }
            if (Integer.parseInt(gui.powerFromSpinner.getValue().toString()) > Integer.parseInt(gui.powerToSpinner.getValue().toString())) {
                ErrorFrame powerSpinnerError = new ErrorFrame(rb.getString("powerFromSpinner>powerToSpinner"));
                powerSpinnerError.setVisible(true);
                gui.powerFromSpinner.setBackground(new Color(255, 76, 76));
                gui.powerToSpinner.setBackground(new Color(255, 76, 76));
                error = true;
            }
            Integer heightFrom = null;
            Integer heightTo = null;
            try {
                heightFrom = Integer.parseInt(gui.heightFromTextField.getText());
            } catch (NumberFormatException ex) {
                ErrorFrame nameSpinnerError = new ErrorFrame(rb.getString("heightFromTextFiledError"));
                nameSpinnerError.setVisible(true);
                gui.heightFromTextField.setBackground(new Color(255, 76, 76));
                error = true;
            }
            try {
                heightTo = Integer.parseInt(gui.heightToTextField.getText());
            } catch (NumberFormatException ex) {
                ErrorFrame nameSpinnerError = new ErrorFrame(rb.getString("heightToTextFiledError"));
                nameSpinnerError.setVisible(true);
                gui.heightToTextField.setBackground(new Color(255, 76, 76));
                error = true;
            }
            if (heightFrom != null && heightFrom < 0) {
                ErrorFrame nameSpinnerError = new ErrorFrame(rb.getString("heightFromTextFiled<heightMinValue"));
                nameSpinnerError.setVisible(true);
                gui.heightFromTextField.setBackground(new Color(255, 76, 76));
                error = true;
            }
            if (heightFrom != null && heightFrom > heightMaxValue) {
                ErrorFrame nameSpinnerError = new ErrorFrame(rb.getString("heightFromTextFiled>heightMaxValue") + heightMaxValue + ")");
                nameSpinnerError.setVisible(true);
                gui.heightFromTextField.setBackground(new Color(255, 76, 76));
                error = true;
            }
            if (heightTo != null && heightTo < 0) {
                ErrorFrame nameSpinnerError = new ErrorFrame(rb.getString("heightToTextFiled<heightMinValue"));
                nameSpinnerError.setVisible(true);
                error = true;
                gui.heightToTextField.setBackground(new Color(255, 76, 76));
            }
            if (heightTo != null && heightTo > heightMaxValue) {
                ErrorFrame nameSpinnerError = new ErrorFrame(rb.getString("heightToTextFiled>heightMaxValue") + heightMaxValue + ")");
                nameSpinnerError.setVisible(true);
                error = true;
            }
            if (heightFrom != null && heightTo != null && heightFrom > heightTo) {
                ErrorFrame nameSpinnerError = new ErrorFrame(rb.getString("heightFromTextFiled>heightToTextFiled"));
                nameSpinnerError.setVisible(true);
                gui.heightFromTextField.setBackground(new Color(255, 76, 76));
                gui.heightToTextField.setBackground(new Color(255, 76, 76));
                error = true;
            }
        }
        //обработка нажатия кнопки
        if(!error){
            //start
            if(gui.startButton.getText().equals(rb.getString("start"))){
                gui.startButton.setText(rb.getString("stop"));
                gui.startButton.setBackground(Color.lightGray);
                gui.startButton.setForeground(Color.darkGray);

                heroesFilter = client.getHeroes();
                //фильтр по type
                heroesFilter = heroesFilter.stream()
                        .filter(x -> (x.getType().equals("Коротышка") && gui.typeShortiesCheckBox.isSelected()) ||
                                     (x.getType().equals("Лунатик") && gui.typeMoonlighterCheckBox.isSelected()) ||
                                     (x.getType().equals("Читатель") && gui.typeReaderCheckBox.isSelected()))
                        .collect(Collectors.toCollection(ConcurrentLinkedDeque::new));

                //фильтр по mood
                heroesFilter = heroesFilter.stream()
                        .filter(x -> (x.getMood().equals(Mood.HAPPY) && gui.moodHappyCheckBox.isSelected()) ||
                                     (x.getMood().equals(Mood.NORMAL) && gui.moodNormalCheckBox.isSelected()) ||
                                     (x.getMood().equals(Mood.SAD) && gui.moodSadCheckBox.isSelected()) ||
                                     (x.getMood().equals(Mood.FURY) && gui.moodFuryCheckBox.isSelected()))
                        .collect(Collectors.toCollection(ConcurrentLinkedDeque::new));

                //фильтр по height
                heroesFilter = heroesFilter.stream()
                        .filter(x -> x.getHeight() >= Integer.parseInt(gui.heightFromTextField.getText()) &&
                                     x.getHeight() <= Integer.parseInt(gui.heightToTextField.getText()))
                        .collect(Collectors.toCollection(ConcurrentLinkedDeque::new));

                //Фильтр по power
                heroesFilter = heroesFilter.stream()
                        .filter(x -> x.getForce() >= (int)gui.powerFromSpinner.getValue() &&
                                     x.getForce() <= (int)gui.powerToSpinner.getValue())
                        .collect(Collectors.toCollection(ConcurrentLinkedDeque::new));

                //Фильтр по onlyLetter
                heroesFilter = heroesFilter.stream()
                        .filter(x -> Character.isLetter(x.getName().charAt(0)) && !gui.onlyLetterCheckBox.isSelected())
                        .collect(Collectors.toCollection(ConcurrentLinkedDeque::new));

                //Фильтр по name
                heroesFilter = heroesFilter.stream()
                        .filter(x -> x.getName().toUpperCase().charAt(0) >= gui.nameFromSpinner.getValue().toString().charAt(0) &&
                                     x.getName().toUpperCase().charAt(0) <= gui.nameToSpinner.getValue().toString().charAt(0))
                        .collect(Collectors.toCollection(ConcurrentLinkedDeque::new));
                //gui.panel.redraw();
                for (int i = 0; i < gui.panel.ellipses.size(); ++i){
                    gui.panel.ellipses.get(i).start();
                }
            }
            //stop
            else {
                gui.startButton.setText(rb.getString("start"));
                gui.startButton.setBackground(Color.red);
                gui.startButton.setForeground(Color.white);
                heroesFilter = client.getHeroes();
                gui.panel.redraw();
                for (int i = 0; i < gui.panel.ellipses.size(); ++i){
                    gui.panel.ellipses.get(i).stop();
                }
            }
        }
    }



    public void changeLanguage(Locale locale){
        this.locale = locale;
    }
}
