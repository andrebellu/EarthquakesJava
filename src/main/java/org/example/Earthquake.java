package org.example;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Earthquake {
    public ArrayList<String> filter(JSONObject response, double x) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");


        var features = response.getJSONArray("features");
        ArrayList<String> list = new ArrayList<>();

        for (int i = 0; i < features.length(); i++) {
            var feature = features.getJSONObject(i);
            var properties = feature.getJSONObject("properties");
            var mag = properties.getDouble("mag");
            var place = properties.getString("place");
            var date = sdf.format(new Date(properties.getLong("time")));

            if (mag >= x) {
                list.add(String.format("Place: %s, Magnitude: %.1f, Date: %s",place, mag, date));
            }
        }

        return list;
    }
}