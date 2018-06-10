package client.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Mouse implements ActionListener {

    String textForMouse = "";
    ClientGUI gui;
    ArrayList<Ellipse> ellipses;
    private Timer timer;

    public Mouse(ClientGUI gui) {
        this.gui = gui;
        this.ellipses = gui.panel.ellipses;
        timer = new Timer(100, this);
    }

    public Point getXY() {
        PointerInfo a = MouseInfo.getPointerInfo();
        Point b = a.getLocation();
        int x = (int) b.getX();
        int y = (int) b.getY();
        return new Point(x - gui.getX() - gui.panel.x, y - gui.getY() - gui.panel.y);
    }

    public boolean insideEllipse(ClientGUI gui, Ellipse ellipse) {
        Point position = this.getXY();
        return Math.sqrt((ellipse.x_center - position.x) * (ellipse.x_center - position.x) + (ellipse.y_center - position.y) * (ellipse.y_center - position.y)) < (ellipse.widthNow * ellipse.coef);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Boolean check = false;
        for (int k = 0; k < ellipses.size(); ++k) {
            if (this.insideEllipse(gui, ellipses.get(k))) {
                textForMouse = ellipses.get(k).personage.toString();
                check = true;
            }
        }
        if (!check) {
            textForMouse = "";
        }
        gui.panel.redraw();
    }

    public void start() {
        gui.panel.timerStart = true;
        timer.start();
    }

    public void stop() {
        gui.panel.timerStart = false;
        timer.stop();
    }
}
