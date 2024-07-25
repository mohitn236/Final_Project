package ca.ucalgary.edu.ensf380;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WeatherFetcher {

    public static String fetchWeatherData(String cityCode) {
        String urlString = "https://wttr.in/" + cityCode;
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

            in.close();
            connection.disconnect();

            return parseWeatherData(content.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return "Unable to fetch weather data.";
        }
    }

    private static String parseWeatherData(String htmlContent) {
        // Regex pattern to extract weather condition and temperature
        Pattern pattern = Pattern.compile("<span class=\"current-weather__description\">(.*?)<\\/span>.*?<span class=\"current-weather__temp\">(.*?)<\\/span>", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(htmlContent);

        if (matcher.find()) {
            String condition = matcher.group(1).trim();
            String temperature = matcher.group(2).trim();
            return "Weather: " + condition + ", Temperature: " + temperature;
        } else {
            return "Weather data not found.";
        }
    }

    public static void main(String[] args) {
        if (args.length > 0) {
            String cityCode = args[0];
            String weatherReport = fetchWeatherData(cityCode);
            System.out.println(weatherReport);
        } else {
            System.out.println("Please provide a city code.");
        }
    }
}
