
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;

public class NewsFetcher {
    public static String getNews(String apiKey, String keyword) {
        try {
            String apiUrl = "https://api.newscatcherapi.com/v2/search?q=" + keyword + "&lang=en";
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("x-api-key", apiKey);
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            connection.disconnect();

            JSONObject newsJson = new JSONObject(content.toString());
            JSONArray articles = newsJson.getJSONArray("articles");
            StringBuilder newsHeadlines = new StringBuilder();
            for (int i = 0; i < articles.length(); i++) {
                newsHeadlines.append(articles.getJSONObject(i).getString("title")).append("\n");
            }
            return newsHeadlines.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to load news data";
        }
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java NewsFetcher <apiKey> <keyword>");
            return;
        }
        String apiKey = args[0];
        String keyword = args[1];
        String news = getNews(apiKey, keyword);
        System.out.println(news);
    }
}
