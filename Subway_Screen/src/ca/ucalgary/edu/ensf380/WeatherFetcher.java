package ca.ucalgary.edu.ensf380;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
public class WeatherFetcher {
   public static String fetchWeatherData(String cityCode) {
       try {
           String baseUrl = "https://wttr.in/";
           String format = "?format=%C+%t+%w+%p+%h+%m";
           String encodedCityCode = URLEncoder.encode(cityCode, StandardCharsets.UTF_8.toString());
           String encodedFormat = URLEncoder.encode(format, StandardCharsets.UTF_8.toString());
           String urlString = baseUrl + encodedCityCode + encodedFormat;
          
           HttpClient client = HttpClient.newHttpClient();
           HttpRequest request = HttpRequest.newBuilder()
                   .uri(new URI(urlString))
                   .GET()
                   .build();
           HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
           return response.body();
       } catch (Exception e) {
           e.printStackTrace();
           return "Unable to fetch weather data.";
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
