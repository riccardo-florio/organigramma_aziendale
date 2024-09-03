package organigramma.gui;

import organigramma.main.Observer;
import organigramma.main.UnitaIF;

import javax.swing.*;

public class ViewerPanel extends JPanel implements Observer {
    public ViewerPanel() {

    }//Costruttore

    public void drawGraph(UnitaIF root) {

    }//drawGraph

    // OBSERVER PATTERN
    @Override
    public void update(UnitaIF unita) {
        // TODO: 03/09/2024
    }// update

}//Viewer
