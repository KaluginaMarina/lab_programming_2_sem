package client.gui;

import client.util.ManageCollection;
import model.Personage;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedDeque;

public class PanelCollection extends JPanel {

    private ConcurrentLinkedDeque<Personage> heroes;
    private ArrayList<Personage> heroesFilter;
    private ClientGUI clientGUI;
    private StartButton startButton;
    boolean timerStart = false;
    private Graphics2D gg;

    int ox;
    int oy;
    int left;
    int right;
    int top;
    int bottom;
    double coef;
    int x, y;
    ArrayList<Personage> heroesList;
    ArrayList<Ellipse> ellipses = new ArrayList<>();
    Mouse mouse;

    public PanelCollection(ConcurrentLinkedDeque<Personage> heroes, ClientGUI clientGUI, StartButton startButton){
        this.heroes = heroes;
        this.clientGUI = clientGUI;
        this.startButton = startButton;
        this.heroesFilter = new ArrayList<>(startButton.heroesFilter);
        this.heroesList = new ArrayList<>(heroes);
    }


    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        coef = (clientGUI.getHeight() - 100)/(20*(ManageCollection.maxHeight(heroes) - ManageCollection.minHeight(heroes)));
        x = 50;
        y = 40;
        int h = Math.min(clientGUI.getHeight() - 2*y, clientGUI.getWidth() / 2 - 2*x); // height

        //Прямоугольник
        g.setColor(Color.white);
        g.fillRect(x, y, h, h);

        //Бордюр
        g.setColor(Color.darkGray);
        g.drawLine(x, y, x+h, y);
        g.drawLine(x, y, x, y+h);
        g.drawLine(x, y+h, x+h, y+h);
        g.drawLine(x+h, y, x+h, y+h);

        //oпределение границ
        top = (int)(ManageCollection.maxY(heroes) < 0 ? 0 : ManageCollection.maxY(heroes) + (ManageCollection.maxHeight(heroes)*coef)/2 + 1);
        bottom = (int) (ManageCollection.minY(heroes) > 0 ? 0 : ManageCollection.minY(heroes) - (ManageCollection.maxHeight(heroes)*coef)/2 - 1 );
        right = (int) (ManageCollection.maxX(heroes) < 0 ? 0 : ManageCollection.maxX(heroes) + (ManageCollection.maxHeight(heroes)*coef)/2 + 1);
        left = (int) (ManageCollection.minX(heroes) > 0 ? 0 : ManageCollection.minX(heroes) - (ManageCollection.maxHeight(heroes)*coef)/2 - 1);


        //координатные оси
        gg = (Graphics2D) g;
        Stroke defaultStroke = gg.getStroke();
        gg.setColor(Color.red);
        gg.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 0, new float[]{1}, 0));
        ox = (int)((double)h/(double) (right - left));
        oy = (int)((double)h/(double)(top - bottom));
        g.drawLine((0-left * ox) + x, y, (0-left * ox) + x,  y+h);
        g.drawLine(x, y+top*oy, x+h, y+top*oy);

        gg.setStroke(defaultStroke);

        //масштаб
        g.drawString("0", -left * ox + x + 1, +y+top*oy - 1);
        if (top != 0) g.drawString(Integer.toString(top), -left * ox + x + 1, y + 13);
        if (bottom != 0) g.drawString(Integer.toString(bottom), -left * ox + x + 1, y + h - 1);
        if (right != 0) g.drawString(Integer.toString(left), x, y+top*oy - 1);
        if (left != 0) g.drawString(Integer.toString(right), x + h - 20, y+top*oy - 1);

        //рисуем коллекцию
        ConcurrentLinkedDeque<Personage> c = new ConcurrentLinkedDeque<>();
        c.addAll(heroes);
        while (!c.isEmpty()) {
            Personage p = c.removeFirst();
            Ellipse e = new Ellipse(p, p.x * ox + x + Math.abs(left * ox) - (p.height * coef + 12) / 2, y + top * oy - p.y * oy - (p.height * coef + 12) / 2, p.height * coef + 10, this);
            boolean check = false;
            for (int j = 0; j < ellipses.size(); ++j) {
                if (ellipses.get(j).equals(e)) {
                    check = true;
                }
            }
            if (!check) {
                ellipses.add(e);
            }
            e.filter = false;
            for (int j = 0; j < heroesFilter.size(); ++j) {
                if (e.personage.equals(heroesFilter.get(j))) {
                    e.filter = true;
                }
            }
            for (int j = 0; j < ellipses.size(); ++j) {
                if (ellipses.get(j).equals(e)) {
                    ellipses.get(j).filter = e.filter;
                }
            }
            for (int j = 0; j < ellipses.size(); ++j) {
                Boolean check1 = false;
                for (int k = 0; k < heroes.size(); ++k) {
                    if (ellipses.get(j).personage.equals(heroesList.get(k))) {
                        check1 = true;
                        break;
                    }
                }
                if (!check1) {
                    ellipses.remove(j);
                }
            }
        }

        gg.setColor(Color.lightGray);
        for (int j = 0; j < ellipses.size(); ++j){
            Color color1;
            Color color2;
            gg.setColor(Color.gray);
            switch (ellipses.get(j).personage.mood){
                case FURY: {
                    color1 = new Color(255, 153, 153);
                    color2 = Color.red;
                    break;
                }
                case HAPPY: {
                    color1 = new Color(204, 255, 153);
                    color2 = Color.green;
                    break;
                }
                case NORMAL: {
                    color1 = new Color(255, 255, 153);
                    color2 = Color.yellow;
                    break;
                }
                case SAD: {
                    color1 = new Color(255, 204, 153);
                    color2 = Color.orange;
                    break;
                }
                default: {
                    color1 = Color.BLACK;
                    color2 = Color.BLACK;
                }
            }
            gg.fill(new Ellipse2D.Double(ellipses.get(j).personage.x * ox + x + Math.abs(left * ox) - (ellipses.get(j).widthNow * coef + 12) / 2, -ellipses.get(j).personage.y * oy + y + top * oy - (ellipses.get(j).widthNow * coef + 12) / 2, ellipses.get(j).widthNow * coef + 12, ellipses.get(j).widthNow * coef + 12));
            gg.setPaint(new GradientPaint((int) (ellipses.get(j).personage.x * ox + x + Math.abs(left * ox) - (ellipses.get(j).widthNow * coef + 12) / 2), (int) ((-ellipses.get(j).personage.y * oy + y + top * oy - (ellipses.get(j).personage.height * coef + 12) / 2)), color1, (int) (ellipses.get(j).personage.x * ox + x + Math.abs(left * ox + ellipses.get(j).widthNow * coef + 12) - (ellipses.get(j).widthNow * coef + 12) / 2), (int) (-ellipses.get(j).personage.y * oy + y + top * oy - (ellipses.get(j).widthNow * coef + 12) / 2), color2));
            gg.fill(new Ellipse2D.Double(ellipses.get(j).personage.x * ox + x + Math.abs(left * ox) - (ellipses.get(j).widthNow * coef + 12) / 2, y + top * oy - ellipses.get(j).personage.y * oy - (ellipses.get(j).widthNow * coef + 12) / 2, ellipses.get(j).widthNow * coef + 10, ellipses.get(j).widthNow * coef+ 10));

            //подписи к персонажам
            g.setColor(Color.darkGray);
            g.drawString(mouse.textForMouse, mouse.getXY().x, mouse.getXY().y);
        }
    }

    public void draw(ConcurrentLinkedDeque<Personage> heroes){
        this.heroes = heroes;
        repaint();
    }

    public void redraw(){
        this.heroes = clientGUI.client.getHeroes();
        heroesFilter = new ArrayList<>(startButton.heroesFilter);
        repaint();
    }
}