import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.List;
import java.util.Arrays;

public class NewsPanel extends JPanel {
    private JLabel newsLabel;
    private int currentNewsIndex = 0;
    private List<String> newsHeadlines = Arrays.asList("News 1", "News 2", "News 3");

    public NewsPanel() {
        setBackground(Color.DARK_GRAY);
        setPreferredSize(new Dimension(800, 50));
        newsLabel = new JLabel(newsHeadlines.get(currentNewsIndex));
        newsLabel.setForeground(Color.WHITE);
        add(newsLabel);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                updateNews();
            }
        }, 0, 5000); // Update news every 5 seconds
    }

    private void updateNews() {
        currentNewsIndex = (currentNewsIndex + 1) % newsHeadlines.size();
        newsLabel.setText(newsHeadlines.get(currentNewsIndex));
    }
}
