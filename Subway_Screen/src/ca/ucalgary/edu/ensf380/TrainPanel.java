package ca.ucalgary.edu.ensf380;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TrainPanel extends JPanel {
    private JLabel trainLabel;
    private List<TrainStation> stations;

    public TrainPanel() {
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(800, 100));
        trainLabel = new JLabel("Train Information");
        trainLabel.setForeground(Color.WHITE);
        add(trainLabel);
        
        stations = new ArrayList<>();
        loadStationsFromCSV("data/Map.csv");
        updateTrainInfo();
    }

    private void loadStationsFromCSV(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // Skip header line
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length < 7) continue;
                String lineCode = values[1].trim();
                int stationNumber = Integer.parseInt(values[2].trim());
                String stationCode = values[3].trim();
                String stationName = values[4].trim();
                double x = Double.parseDouble(values[5].trim());
                double y = Double.parseDouble(values[6].trim());
                stations.add(new TrainStation(lineCode, stationNumber, stationCode, stationName, x, y));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateTrainInfo() {
        if (!stations.isEmpty()) {
            // Fetch actual train data from a source
            StringBuilder trainInfo = new StringBuilder();
            for (TrainStation station : stations) {
                trainInfo.append("Line: ").append(station.lineCode)
                        .append(", Station: ").append(station.stationName)
                        .append(", Code: ").append(station.stationCode)
                        .append(", Position: (").append(station.x).append(", ").append(station.y).append(")<br>");
            }

            // Example update, should be replaced with actual dynamic data
            String transferInfo = "You can change to line blue";
            trainLabel.setText("<html>Next Stops:<br>" + trainInfo.toString() + "<br>" + transferInfo + "</html>");
        } else {
            trainLabel.setText("No train information available.");
        }
    }

    public List<TrainStation> getStations() {
        return stations;
    }

    public static class TrainStation {
        public String lineCode;
        public int stationNumber;
        public String stationCode;
        public String stationName;
        public double x;
        public double y;

        public TrainStation(String lineCode, int stationNumber, String stationCode, String stationName, double x, double y) {
            this.lineCode = lineCode;
            this.stationNumber = stationNumber;
            this.stationCode = stationCode;
            this.stationName = stationName;
            this.x = x;
            this.y = y;
        }
    }
}
