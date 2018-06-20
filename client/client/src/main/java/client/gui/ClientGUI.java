package client.gui;

import client.Client;
import client.util.ManageCollection;
import control.UTF8Control;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;

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
    private JLabel typeLabel;
    private JLabel filterLabel;
    private JLabel nameLabel;
    private JLabel powerLabel;
    private JLabel moodLabel;
    private JLabel heightLabel;
    private JLabel fromLabel;
    private JLabel toLabel;
    private JLabel fromLabel1;
    private JLabel toLabel1;
    private JLabel fromLabel2;
    private JLabel toLabel2;
    private JButton refresh;
    public Locale locale;
    StartButton startbutton;

    public ClientGUI(Client client, int xBounds, int yBounds, int widthBounds, int heightBounds, Locale locale) {
        super();
        this.locale = locale;
        this.client = client;
        this.setBounds(xBounds, yBounds, widthBounds, heightBounds);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(800, 400));
        //Куча кода с Labelами
        filterLabel = new JLabel();
        Font heading = new Font("Verdana", Font.BOLD, 14);
        filterLabel.setFont(heading);
        typeLabel = new JLabel();
        typeLabel.setForeground(Color.darkGray);
        nameLabel = new JLabel();
        nameLabel.setForeground(Color.darkGray);
        powerLabel = new JLabel();
        powerLabel.setForeground(Color.darkGray);

        moodLabel = new JLabel();
        moodLabel.setForeground(Color.darkGray);

        heightLabel = new JLabel();
        heightLabel.setForeground(Color.darkGray);

        //Кучака кода для type
        typeShortiesCheckBox = new JCheckBox("", true);
        typeShortiesCheckBox.setForeground(Color.darkGray);
        typeMoonlighterCheckBox = new JCheckBox("", true);
        typeMoonlighterCheckBox.setForeground(Color.darkGray);
        typeReaderCheckBox = new JCheckBox("", true);
        typeReaderCheckBox.setForeground(Color.darkGray);

        //код для name
        String[] letter = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","А","Б","В","Г","Д","Е","Ж","З","И","Й","К","Л","М","Н","О","П","Р","С","Т","У","Ф","Х","Ц","Ч","Ш","Щ","Ъ","Ы","Ь","Э","Ю","Я"};
        SpinnerModel spinnerFromModel = new SpinnerListModel(letter);
        SpinnerModel spinnerToModel = new SpinnerListModel(letter);
        nameFromSpinner = new JSpinner(spinnerFromModel);
        nameToSpinner = new JSpinner(spinnerToModel);
        spinnerToModel.setValue("Я");
        fromLabel = new JLabel();
        fromLabel.setForeground(Color.darkGray);
        toLabel = new JLabel();
        toLabel.setForeground(Color.darkGray);
        onlyLetterCheckBox = new JCheckBox("", false);

        //Код для power filter
        int maxPower = ManageCollection.maxPower(client.getHeroes());
        SpinnerNumberModel powerFromSpinnerModel = new SpinnerNumberModel(0, 0, maxPower, 1);
        SpinnerNumberModel powerToSpinnerModel = new SpinnerNumberModel(maxPower, 0, maxPower,1);
        powerFromSpinner = new JSpinner(powerFromSpinnerModel);
        powerToSpinner = new JSpinner(powerToSpinnerModel);
        fromLabel1 = new JLabel();
        fromLabel1.setForeground(Color.darkGray);
        toLabel1 = new JLabel();
        toLabel1.setForeground(Color.darkGray);

        //Код для mood filter
        moodNormalCheckBox = new JCheckBox("", true);
        moodNormalCheckBox.setForeground(Color.darkGray);
        moodNormalCheckBox.setBackground(new Color(255, 255, 153));
        moodHappyCheckBox = new JCheckBox("", true);
        moodHappyCheckBox.setForeground(Color.darkGray);
        moodHappyCheckBox.setBackground(new Color(204, 255, 153));
        moodSadCheckBox = new JCheckBox("", true);
        moodSadCheckBox.setForeground(Color.darkGray);
        moodSadCheckBox.setBackground(new Color(255, 204, 153));
        moodFuryCheckBox = new JCheckBox("", true);
        moodFuryCheckBox.setForeground(Color.darkGray);
        moodFuryCheckBox.setBackground(new Color(255, 153, 153));

        //кучка кода для height filter
        int maxHeight = ManageCollection.maxHeight(client.getHeroes());
        this.heightFromTextField = new JTextField("0");
        heightFromTextField.setHorizontalAlignment(JTextField.RIGHT);
        this.heightToTextField = new JTextField(Integer.toString(maxHeight));
        heightToTextField.setHorizontalAlignment(JTextField.RIGHT);
        fromLabel2 = new JLabel();
        fromLabel2.setForeground(Color.darkGray);
        toLabel2 = new JLabel();
        toLabel2.setForeground(Color.darkGray);

        //buttons
        startButton = new JButton();
        startButton.setBackground(Color.red);
        startButton.setForeground(Color.white);
        startbutton = new StartButton(this, ManageCollection.maxHeight(client.getHeroes()), client, locale);
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

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(new Menu(this));
        this.setJMenuBar(menuBar);

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

        refresh = new JButton();
        refresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClientGUI tmp = new ClientGUI(client, ClientGUI.this.getBounds().x, ClientGUI.this.getBounds().y, ClientGUI.this.getBounds().width, ClientGUI.this.getBounds().height, locale);
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

        changeLanguage(Locale.getDefault());
        this.setVisible(true);
    }

    void changeLanguage(Locale locale){
        ResourceBundle rb = ResourceBundle.getBundle("Resources", locale, new UTF8Control());
        this.setTitle(rb.getString("CLIENT"));
        filterLabel.setText(rb.getString("filter"));
        typeLabel.setText(rb.getString("type"));
        nameLabel.setText(rb.getString("name"));
        powerLabel.setText(rb.getString("power"));
        moodLabel.setText(rb.getString("mood"));
        heightLabel.setText(rb.getString("height"));
        typeShortiesCheckBox.setText(rb.getString("shorties"));
        typeMoonlighterCheckBox.setText(rb.getString("moonlighter"));
        typeReaderCheckBox.setText(rb.getString("reader"));
        fromLabel.setText(rb.getString("from"));
        toLabel.setText(rb.getString("to"));
        onlyLetterCheckBox.setText(rb.getString("only_letter"));
        fromLabel1.setText(rb.getString("from"));
        toLabel1.setText(rb.getString("to"));
        moodNormalCheckBox.setText(rb.getString("normal"));
        moodHappyCheckBox.setText(rb.getString("happy"));
        moodSadCheckBox.setText(rb.getString("sad"));
        moodFuryCheckBox.setText(rb.getString("fury"));
        moodFuryCheckBox.setText(rb.getString("fury"));
        fromLabel2.setText(rb.getString("from"));
        toLabel2.setText(rb.getString("to"));
        startButton.setText(rb.getString("start"));
        refresh.setText(rb.getString("reset"));
        startbutton.changeLanguage(locale);
    }
}
