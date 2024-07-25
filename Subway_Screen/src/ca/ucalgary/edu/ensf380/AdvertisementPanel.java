package ca.ucalgary.edu.ensf380;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import ca.ucalgary.edu.ensf380.TrainPanel.TrainStation;

public class AdvertisementPanel extends JPanel {
    private List<TrainStation> stations;

    public AdvertisementPanel(List<TrainStation> stations) {
        this.stations = stations;
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(800, 600));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawTrainPositions(g);
    }

    private void drawTrainPositions(Graphics g) {
        g.setColor(Color.BLUE);
        for (TrainStation station : stations) {
            int x = (int) station.x;
            int y = (int) station.y;
            g.fillOval(x, y, 10, 10);
            g.drawString(station.stationName, x, y - 5);
        }
    }
}
