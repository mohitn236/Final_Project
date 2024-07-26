//package ca.ucalgary.edu.ensf380;
//
//import javax.swing.*;
//import java.awt.*;
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.util.ArrayList;
//
//public class TrainLocationDisplay extends JFrame {
//    private ArrayList<Station> redLineStations = new ArrayList<>();
//    private ArrayList<Station> blueLineStations = new ArrayList<>();
//    private ArrayList<Station> greenLineStations = new ArrayList<>();
//
//    public TrainLocationDisplay() {
//        setTitle("Train Location Display");
//        setSize(800, 600);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setLayout(new GridLayout(1, 3));
//
//        readData("data.csv");
//
//        JPanel redLinePanel = createLinePanel("Red Line", redLineStations);
//        JPanel blueLinePanel = createLinePanel("Blue Line", blueLineStations);
//        JPanel greenLinePanel = createLinePanel("Green Line", greenLineStations);
//
//        add(redLinePanel);
//        add(blueLinePanel);
//        add(greenLinePanel);
//    }
//
//    private void readData(String fileName) {
//        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
//            String line;
//            while ((line = br.readLine()) != null) {
//                String[] data = line.split(",");
//                String lineName = data[1].trim();
//                int stationNumber = Integer.parseInt(data[2].trim());
//                String stationCode = data[3].trim();
//                String stationName = data[4].trim();
//                double x = Double.parseDouble(data[5].trim());
//                double y = Double.parseDouble(data[6].trim());
//                String commonStations = data.length > 7 ? data[7].trim() : "";
//
//                Station station = new Station(stationNumber, stationCode, stationName, x, y, commonStations);
//
//                switch (lineName) {
//                    case "R":
//                        redLineStations.add(station);
//                        break;
//                    case "B":
//                        blueLineStations.add(station);
//                        break;
//                    case "G":
//                        greenLineStations.add(station);
//                        break;
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private JPanel createLinePanel(String lineName, ArrayList<Station> stations) {
//        JPanel panel = new JPanel();
//        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
//        panel.setBorder(BorderFactory.createTitledBorder(lineName));
//
//        for (Station station : stations) {
//            JLabel label = new JLabel(station.toString());
//            panel.add(label);
//        }
//
//        return panel;
//    }
//
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            TrainLocationDisplay frame = new TrainLocationDisplay();
//            frame.setVisible(true);
//        });
//    }
//
//    class Station {
//        int number;
//        String code;
//        String name;
//        double x;
//        double y;
//        String commonStations;
//
//        public Station(int number, String code, String name, double x, double y, String commonStations) {
//            this.number = number;
//            this.code = code;
//            this.name = name;
//            this.x = x;
//            this.y = y;
//            this.commonStations = commonStations;
//        }
//
//        @Override
//        public String toString() {
//            return String.format("%s (%s) - X: %.2f, Y: %.2f", name, code, x, y);
//        }
//    }
//}
//
