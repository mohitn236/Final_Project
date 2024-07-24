import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class AdvertisementPanel extends JPanel {
    private JLabel advertisementLabel;
    private int currentAdIndex = 0;
    private String[] advertisements = {"Ad 1", "Ad 2", "Ad 3"};

    public AdvertisementPanel() {
        setBackground(Color.BLUE);
        setPreferredSize(new Dimension(600, 400));
        advertisementLabel = new JLabel(advertisements[currentAdIndex]);
        advertisementLabel.setForeground(Color.WHITE);
        add(advertisementLabel);
        
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                updateAdvertisement();
            }
        }, 0, 10000); // Update ad every 10 seconds
    }

    private void updateAdvertisement() {
        currentAdIndex = (currentAdIndex + 1) % advertisements.length;
        advertisementLabel.setText(advertisements[currentAdIndex]);
    }
}
