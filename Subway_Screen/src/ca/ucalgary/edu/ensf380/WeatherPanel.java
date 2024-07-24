package ca.ucalgary.edu.ensf380;
import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class WeatherPanel extends JPanel {
    private JLabel weatherLabel;

    public WeatherPanel() {
        setBackground(Color.GRAY);
        setPreferredSize(new Dimension(200, 150));
        weatherLabel = new JLabel("Weather Information");
        weatherLabel.setForeground(Color.WHITE);
        add(weatherLabel);
        updateWeather("5913490"); // Example city code for Calgary
    }

    private void updateWeather(String cityCode) {
        try {
            ProcessBuilder builder = new ProcessBuilder("java", "-jar", "WeatherFetcher.jar", cityCode, "YOUR_API_KEY");
            Process process = builder.start();
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            weatherLabel.setText("<html>" + content.toString().replace("\n", "<br>") + "</html>");
        } catch (Exception e) {
            weatherLabel.setText("Failed to load weather data");
            e.printStackTrace();
        }
    }
}
