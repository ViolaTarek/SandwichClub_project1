package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich;
        JSONObject name;
        String main = null;
        List<String> alsoKnownAs = null;
        String placeOfOrigin = null;
        String description = null;
        String img = null;
        List<String> ingredients = null;

        try {
            JSONObject root = new JSONObject(json);
            name = root.getJSONObject("name");
            main = name.getString("mainName");
            JSONArray mAlsoKnownAs = name.getJSONArray("alsoKnownAs");
            alsoKnownAs = new ArrayList<>();
            if (mAlsoKnownAs.length() != 0) {
                for (int i = 0; i < mAlsoKnownAs.length(); i++) {
                    alsoKnownAs.add(mAlsoKnownAs.getString(i));
                }

            }
            placeOfOrigin = root.getString("placeOfOrigin");
            description = root.getString("description");
            img = root.getString("image");
            JSONArray mIngredients = root.getJSONArray("ingredients");
            ingredients = new ArrayList<>();
            for (int i = 0; i < mIngredients.length(); i++) {
                ingredients.add(mIngredients.getString(i));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        sandwich = new Sandwich(main, alsoKnownAs, placeOfOrigin, description, img, ingredients);
        return sandwich;

    }


}
