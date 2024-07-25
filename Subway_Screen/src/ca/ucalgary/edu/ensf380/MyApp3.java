package ca.ucalgary.edu.ensf380;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Mahdi Ansari
 *
 */
public class MyApp3 extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JTextArea outputArea;
    private JButton startButton;
    private JButton stopButton;
    private Process process;
    private ExecutorService executor;

    public MyApp3() {
        setTitle("Subway Screen 3");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                stopProcess();
                dispose();
            }
        });

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setPreferredSize(new Dimension(400, 300));
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        startButton = new JButton("Start");
        startButton.addActionListener(this);
        startButton.setPreferredSize(new Dimension(100, 38));
        buttonPanel.add(startButton);

        stopButton = new JButton("Stop");
        stopButton.addActionListener(this);
        stopButton.setEnabled(false);
        stopButton.setPreferredSize(new Dimension(100, 38));
        buttonPanel.add(stopButton);

        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        executor = Executors.newFixedThreadPool(2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
            startProcess();
        } else if (e.getSource() == stopButton) {
            stopProcess();
        }
    }

    private void startProcess() {
        if (process == null) {
            try {
                ProcessBuilder builder = new ProcessBuilder("java", "-jar", "./exe/SubwaySimulator.jar", "--in", "./data/subway.csv", "--out", "./out");
                builder.redirectErrorStream(true);
                process = builder.start();

                executor.execute(() -> {
                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            System.out.println(line);
                            outputArea.append(line + "\n");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

                executor.execute(() -> {
                    try {
                        process.waitFor();
                        process = null;
                        SwingUtilities.invokeLater(() -> {
                            stopButton.setEnabled(false);
                            startButton.setEnabled(true);
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });

                stopButton.setEnabled(true);
                startButton.setEnabled(false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void stopProcess() {
        if (process != null) {
            process.destroy();
            process = null;
            stopButton.setEnabled(false);
            startButton.setEnabled(true);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MyApp3 frame = new MyApp3();

            // Create panels for each section
            TrainPanel trainPanel = new TrainPanel();
            List<TrainPanel.TrainStation> stations = trainPanel.getStations();
            AdvertisementPanel advertisementPanel = new AdvertisementPanel(stations);
            WeatherPanel weatherPanel = new WeatherPanel();
            NewsPanel newsPanel = new NewsPanel();

            // Layout configuration
            JPanel mainPanel = new JPanel(new BorderLayout());
            mainPanel.add(advertisementPanel, BorderLayout.CENTER);
            mainPanel.add(weatherPanel, BorderLayout.EAST); // Adjusted position for the WeatherPanel
            mainPanel.add(newsPanel, BorderLayout.SOUTH);
            mainPanel.add(trainPanel, BorderLayout.NORTH);

            frame.add(mainPanel);
            frame.setSize(800, 600);
            frame.setVisible(true);
        });
    }
}
