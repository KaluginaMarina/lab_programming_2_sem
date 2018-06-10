package client.gui;

import model.Personage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

class Ellipse implements ActionListener {
    Personage personage;
    double x_center;
    double y_center;
    double width;
    double widthNow;
    boolean filter;
    private Timer timer;
    private char direction;
    private PanelCollection panelCollection;
    double coef;

    Ellipse(Personage personage, double x, double y, double w, PanelCollection panelCollection){
        this.personage = personage;
        this.x_center = x;
        this.y_center = y;
        this.width = w / panelCollection.coef;
        this.coef = panelCollection.coef;
        filter = false;
        timer = new Timer((int)(4000*3/(width*2)), this);
        widthNow = this.width;
        direction = 'D';
        this.panelCollection = panelCollection;
    }

    @Override
    public String toString() {
        return "Ellipse{" +
                "personage=" + personage +
                ", x_center=" + x_center +
                ", y_center=" + y_center +
                ", width=" + width +
                ", widthNow=" + widthNow +
                ", filter=" + filter +
                ", timer=" + timer +
                ", direction=" + direction +
                ", panelCollection=" + panelCollection +
                '}';
    }

    public void start() {
        panelCollection.timerStart = true;
        timer.start();
    }

    public void stop() {
        panelCollection.timerStart = false;
        this.widthNow = width;
        timer.stop();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.filter) {
            if (this.direction == 'D') {
                this.widthNow--;
                if (this.width / 3.0 > this.widthNow) {
                    this.direction = 'U';
                    this.timer.setDelay((int)(4000*3/(width*2*2)));
                }
            } else {
                this.widthNow++;
                if (this.widthNow > this.width) {
                    this.direction = 'D';
                    this.timer.setDelay((int)(4000*3/(width*2)));
                }
            }
        } else {
            this.widthNow = this.width;
        }

        panelCollection.redraw();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ellipse ellipse = (Ellipse) o;
        return Objects.equals(personage, ellipse.personage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(personage, x_center, y_center, width, widthNow, filter, timer, direction, panelCollection);
    }
}