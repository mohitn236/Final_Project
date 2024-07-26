package ca.ucalgary.edu.ensf380;
import javax.swing.*;
import java.awt.*;
public class WeatherPanel extends JPanel {
   private static final long serialVersionUID = 1L; // Added serialVersionUID
   private JLabel weatherLabel;
   public WeatherPanel() {
       setBackground(Color.GRAY);
       setPreferredSize(new Dimension(200, 150));
       weatherLabel = new JLabel("Weather Information", SwingConstants.CENTER);
       weatherLabel.setForeground(Color.WHITE);
       add(weatherLabel);
       updateWeather("Calgary,CA"); // Example city code for Calgary
   }
   private void updateWeather(String cityCode) {
       new Thread(() -> {
           String weatherData = WeatherFetcher.fetchWeatherData(cityCode);
           SwingUtilities.invokeLater(() -> {
               weatherLabel.setText("<html>" + weatherData.replace("\n", "<br>") + "</html>");
           });
       }).start();
   }
}
