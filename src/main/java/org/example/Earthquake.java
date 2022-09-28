package org.example;

import org.json.JSONObject;

import java.util.ArrayList;

public class Earthquake {
    public ArrayList<String> filter(JSONObject response, double x) {
        var features = response.getJSONArray("features");
        ArrayList<String> list = new ArrayList<>();

        for (int i = 0; i < features.length(); i++) {
            var feature = features.getJSONObject(i);
            var properties = feature.getJSONObject("properties");
            var mag = properties.getDouble("mag");
            var place = properties.getString("place");
            var date = properties.getLong("time");

            if (mag >= x) {
                list.add(String.format("%s, %.1f, %s",place, mag, date));
            }
        }
        return list;
    }
}
