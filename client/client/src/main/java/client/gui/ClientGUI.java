package client.gui;

import client.Client;
import client.util.ManageCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientGUI extends JFrame{

    JCheckBox typeShortiesCheckBox;
    JCheckBox typeMoonlighterCheckBox;
    JCheckBox typeReaderCheckBox;
    JSpinner nameFromSpinner;
    JSpinner nameToSpinner;
    JSpinner powerFromSpinner;
    JSpinner powerToSpinner;
    JCheckBox moodNormalCheckBox;
    JCheckBox moodHappyCheckBox;
    JCheckBox moodSadCheckBox;
    JCheckBox moodFuryCheckBox;
    JTextField heightFromTextField;
    JTextField heightToTextField;
    JCheckBox onlyLetterCheckBox;
    JButton startButton;
    PanelCollection panel;
    Client client;


    public ClientGUI(Client client, int xBounds, int yBounds, int widthBounds, int heightBounds) {
        super("CLIENT");
        this.client = client;
        System.out.println(widthBounds + " " + heightBounds);
        this.setBounds(xBounds, yBounds, widthBounds, heightBounds);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(800, 400));
        //Куча кода с Labelами
        JLabel filterLabel = new JLabel("Фильтры:");
        Font heading = new Font("Verdana", Font.BOLD, 14);
        filterLabel.setFont(heading);

        JLabel typeLabel = new JLabel("Тип:");
        typeLabel.setForeground(Color.darkGray);

        JLabel nameLabel = new JLabel("Имя:");
        nameLabel.setForeground(Color.darkGray);

        JLabel powerLabel = new JLabel("Сила:");
        powerLabel.setForeground(Color.darkGray);

        JLabel moodLabel = new JLabel("Настроение:");
        moodLabel.setForeground(Color.darkGray);

        JLabel heightLabel = new JLabel("Высота:");
        heightLabel.setForeground(Color.darkGray);

        //Кучака кода для type
        this.typeShortiesCheckBox = new JCheckBox("Коротышка", true);
        typeShortiesCheckBox.setForeground(Color.darkGray);
        this.typeMoonlighterCheckBox = new JCheckBox("Лунатик", true);
        typeMoonlighterCheckBox.setForeground(Color.darkGray);
        this.typeReaderCheckBox = new JCheckBox("Читатель", true);
        typeReaderCheckBox.setForeground(Color.darkGray);

        //код для name
        String[] letter = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","А","Б","В","Г","Д","Е","Ж","З","И","Й","К","Л","М","Н","О","П","Р","С","Т","У","Ф","Х","Ц","Ч","Ш","Щ","Ъ","Ы","Ь","Э","Ю","Я"};
        SpinnerModel spinnerFromModel = new SpinnerListModel(letter);
        SpinnerModel spinnerToModel = new SpinnerListModel(letter);
        this.nameFromSpinner = new JSpinner(spinnerFromModel);
        this.nameToSpinner = new JSpinner(spinnerToModel);
        spinnerToModel.setValue("Я");
        JLabel fromLabel = new JLabel("От: ");
        fromLabel.setForeground(Color.darkGray);
        JLabel toLabel = new JLabel("До: ");
        toLabel.setForeground(Color.darkGray);
        this.onlyLetterCheckBox = new JCheckBox("Только буквы", false);

        //Код для power filter
        int maxPower = ManageCollection.maxPower(client.getHeroes());
        SpinnerNumberModel powerFromSpinnerModel = new SpinnerNumberModel(0, 0, maxPower, 1);
        SpinnerNumberModel powerToSpinnerModel = new SpinnerNumberModel(maxPower, 0, maxPower,1);
        this.powerFromSpinner = new JSpinner(powerFromSpinnerModel);
        this.powerToSpinner = new JSpinner(powerToSpinnerModel);
        JLabel fromLabel1 = new JLabel("От: ");
        fromLabel1.setForeground(Color.darkGray);
        JLabel toLabel1 = new JLabel("До: ");
        toLabel1.setForeground(Color.darkGray);

        //Код для mood filter
        this.moodNormalCheckBox = new JCheckBox("Нормальный", true);
        moodNormalCheckBox.setForeground(Color.darkGray);
        moodNormalCheckBox.setBackground(new Color(255, 255, 153));
        this.moodHappyCheckBox = new JCheckBox("Веселый", true);
        moodHappyCheckBox.setForeground(Color.darkGray);
        moodHappyCheckBox.setBackground(new Color(204, 255, 153));
        this.moodSadCheckBox = new JCheckBox("Грустный", true);
        moodSadCheckBox.setForeground(Color.darkGray);
        moodSadCheckBox.setBackground(new Color(255, 204, 153));
        this.moodFuryCheckBox = new JCheckBox("Яростный", true);
        moodFuryCheckBox.setForeground(Color.darkGray);
        moodFuryCheckBox.setBackground(new Color(255, 153, 153));

        //кучка кода для height filter
        int maxHeight = ManageCollection.maxHeight(client.getHeroes());
        this.heightFromTextField = new JTextField("0");
        heightFromTextField.setHorizontalAlignment(JTextField.RIGHT);
        this.heightToTextField = new JTextField(Integer.toString(maxHeight));
        heightToTextField.setHorizontalAlignment(JTextField.RIGHT);
        JLabel fromLabel2 = new JLabel("От: ");
        fromLabel2.setForeground(Color.darkGray);
        JLabel toLabel2 = new JLabel("До: ");
        toLabel2.setForeground(Color.darkGray);

        //buttons
        this.startButton = new JButton("Старт");
        startButton.setBackground(Color.red);
        startButton.setForeground(Color.white);
        StartButton startbutton = new StartButton(this, ManageCollection.maxHeight(client.getHeroes()), client);
        startButton.addActionListener(startbutton);

        //Кучка необходимого кода
        GridLayout gridLayout = new GridLayout(1, 2);
        this.setLayout(gridLayout);

        //панель с картинкой
        panel = new PanelCollection(client.getHeroes(), this, startbutton);
        Mouse mouse = new Mouse(this);
        mouse.start();
        panel.mouse = mouse;
        panel.draw(client.getHeroes());

        //панель с фильтром
        JPanel filter = new JPanel();
        filter.setLayout(new GridBagLayout());

        GridBagConstraints filters = new GridBagConstraints();

        //Код, для расположения всего этого
        //Заголовок
        filters.insets = new Insets(2, 0, 0, 0 );
        filters.anchor = GridBagConstraints.LINE_END;
        filters.fill = GridBagConstraints.HORIZONTAL;
        filters.gridx = 1;
        filters.gridy = 0;
        filters.gridwidth = 2;
        filters.gridheight = 1;
        filter.add(filterLabel, filters);

        //type фильтр
        filters.gridx = 1;
        filters.gridy = 1;
        filter.add(typeLabel, filters);

        filters.gridx = 3;
        filters.insets = new Insets(0,0,0,0);
        filters.gridy = 1;
        filter.add(typeShortiesCheckBox, filters);

        filters.gridx = 3;
        filters.gridy = 2;
        filter.add(typeMoonlighterCheckBox, filters);

        filters.gridx = 3;
        filters.gridy = 3;
        filter.add(typeReaderCheckBox, filters);

        //name фильтр
        filters.gridx = 1;
        filters.gridy = 4;
        filter.add(nameLabel, filters);

        filters.gridx = 2;
        filters.gridy = 4;
        filters.gridwidth = 1;
        filter.add(fromLabel, filters);

        filters.gridx = 3;
        filters.gridy = 4;
        filter.add(nameFromSpinner, filters);

        filters.gridx = 2;
        filters.gridy = 5;
        filter.add(toLabel, filters);

        filters.gridy = 5;
        filters.gridx = 3;
        filter.add(nameToSpinner, filters);

        filters.gridx = 3;
        filters.gridy = 6;
        filter.add(onlyLetterCheckBox, filters);

        //power filter
        filters.gridx = 1;
        filters.gridy = 7;
        filter.add(powerLabel, filters);

        filters.gridx = 2;
        filters.gridy = 7;
        filter.add(fromLabel1, filters);

        filters.gridx = 3;
        filters.gridy = 7;
        filter.add(powerFromSpinner, filters);

        filters.gridx = 2;
        filters.gridy = 8;
        filter.add(toLabel1, filters);

        filters.gridx = 3;
        filters.gridy = 8;
        filter.add(powerToSpinner, filters);

        //mood filter
        filters.gridx = 1;
        filters.gridy = 9;
        filter.add(moodLabel, filters);

        filters.gridx = 3;
        filters.gridy = 10;
        filter.add(moodNormalCheckBox, filters);

        filters.gridx = 3;
        filters.gridy = 9;
        filter.add(moodHappyCheckBox, filters);

        filters.gridx = 3;
        filters.gridy = 11;
        filter.add(moodSadCheckBox, filters);

        filters.gridx = 3;
        filters.gridy = 12;
        filter.add(moodFuryCheckBox, filters);

        //height filter
        filters.gridx  = 1;
        filters.gridy = 13;
        filters.gridheight = 2;
        filter.add(heightLabel, filters);

        filters.gridx = 2;
        filters.gridy = 13;
        filter.add(fromLabel2, filters);

        filters.gridx = 3;
        filters.gridy = 13;
        filter.add(heightFromTextField, filters);

        filters.gridx = 2;
        filters.gridy = 15;
        filter.add(toLabel2, filters);

        filters.gridx = 3;
        filters.gridy = 15;
        filter.add(heightToTextField, filters);

        //start button
        filters.gridx = 1;
        filters.gridy = 17;
        filters.gridwidth = 3;
        filter.add(startButton, filters);

        JButton refresh = new JButton("Обновить");
        refresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClientGUI tmp = new ClientGUI(client, ClientGUI.this.getBounds().x, ClientGUI.this.getBounds().y, ClientGUI.this.getBounds().width, ClientGUI.this.getBounds().height);
                panel = new PanelCollection(client.getHeroes(), tmp, startbutton);
                ClientGUI.this.setVisible(false);
                tmp.setVisible(true);
                panel.draw(client.getHeroes());
            }
        });
        filters.gridx = 3;
        filters.gridy = 20;
        filters.anchor = GridBagConstraints.NORTH;
        refresh.setSize(35, 15);
        filter.add(refresh, filters);

        this.add(panel);
        this.add(filter);

        this.setVisible(true);
    }
}
