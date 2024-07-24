import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class WeatherFetcher {
    public static String getWeather(String cityCode, String apiKey) {
        try {
            String apiUrl = "https://api.openweathermap.org/data/2.5/weather?id=" + cityCode + "&appid=" + apiKey;
            URL url = new URL(apiUrl);
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

            JSONObject weatherJson = new JSONObject(content.toString());
            String weatherDescription = weatherJson.getJSONArray("weather").getJSONObject(0).getString("description");
            double tempK = weatherJson.getJSONObject("main").getDouble("temp");
            double tempC = tempK - 273.15;

            return "Weather: " + weatherDescription + ", Temp: " + String.format("%.2f", tempC) + " °C";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to load weather data";
        }
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java WeatherFetcher <cityCode> <apiKey>");
            return;
        }
        String cityCode = args[0];
        String apiKey = args[1];
        String weather = getWeather(cityCode, apiKey);
        System.out.println(weather);
    }
}
