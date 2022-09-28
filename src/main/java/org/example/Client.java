package org.example;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static spark.Spark.*;

public class Client {
    public static void main(String[] args) {
        port(8080);

        var client = new Client();
        var eq = new Earthquake();

        get("/", (req, res) -> {
            return "<form action=\"/result\" method=\"get\">\n" +
                    "  <label for=\"mag\">Minimum magnitude:</label><br>\n" +
                    "  <input type=\"text\" id=\"mag\" name=\"mag\"><br>\n" +
                    "  <input type=\"submit\" value=\"Submit\">\n" +
                    "</form> ";
        });

        get("/result", (req, res) -> {
            var mag = req.queryParams("mag");
            var response = client.getEarthquakes("https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/4.5_month.geojson");
            var filteredEq = eq.filter(response, Float.parseFloat(mag));

            var html = "<h1>Earthquakes</h1>";
            for (var item : filteredEq) {
                html += "<p>" + item + "</p>";
            }
            return html;
        });
    }

    private JSONObject getEarthquakes(String s) {
        var httpClient = HttpClient.newHttpClient();

        var request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(s))
                .build();

        try {
            var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return new JSONObject(response.body());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}