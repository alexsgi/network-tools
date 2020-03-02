package network.news;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NewsFetcher {

    private static String baseURL = "http://newsapi.org/v2/%s?apiKey=%s";
    private static String KEY = null;

    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    static {
        init("c68cd74b012f4aa3943a46b3c104efcb");
    }

    public static void init(String key) {
        KEY = key;
    }

    public static void main(String[] args) throws IOException {
        System.err.println(fetchTopHeadlines());
    }

    public static String fetchTopHeadlines() throws IOException {
        String stringUrl = String.format(baseURL, "top-headlines", KEY) + "&country=de";
        URL url = new URL(stringUrl);
        return fetchContent(url);
    }

    private static String fetchContent(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        StringBuilder stringBuilder = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line).append("\n");
        }
        reader.close();
        connection.disconnect();
        return gson.toJson(JsonParser.parseString(stringBuilder.toString()));
    }

}
