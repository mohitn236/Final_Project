package ca.ucalgary.edu.ensf380;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherFetcher {

    public static String fetchWeatherData(String cityCode) {
        String urlString = "http://wttr.in/" + cityCode + "?format=%C+%t";
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
        // wttr.in provides data in plain text, so parsing might not need regex, just extraction
        return htmlContent.trim();
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