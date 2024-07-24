import javax.swing.*;
import java.awt.*;

public class TrainPanel extends JPanel {
    private JLabel trainLabel;

    public TrainPanel() {
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(800, 100));
        trainLabel = new JLabel("Train Information");
        trainLabel.setForeground(Color.WHITE);
        add(trainLabel);
        // Call a method to update train data from CSV file
        updateTrainInfo();
    }

    private void updateTrainInfo() {
        // Mock data for now. You should replace this with actual data parsing logic.
        String nextStation = "Mill Creek Station";
        String transferInfo = "You can change to line blue";

        trainLabel.setText("<html>Next Stop: " + nextStation + "<br>" + transferInfo + "</html>");
    }
}
