package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private final static String LOG_TAG = JsonUtils.class.getSimpleName();
    private final static String NAME = "name";
    private final static String MAIN_NAME = "mainName";
    private final static String ALSO_KNOWN_AS = "alsoKnownAs";
    private final static String PLACE_OF_ORIGIN = "placeOfOrigin";
    private final static String DESCRIPTION = "description";
    private final static String IMAGE = "image";
    private final static String INGREDIENTS = "ingredients";

    public static Sandwich parseSandwichJson(String json) {
        if(json == null || json.isEmpty())
            return null;

        try {
            JSONObject jsonSandwich = new JSONObject(json);

            JSONObject nameObj = jsonSandwich.getJSONObject(NAME);
            String mainName = nameObj.getString(MAIN_NAME);

            JSONArray alsoKnownAsJsonArray = nameObj.getJSONArray(ALSO_KNOWN_AS);
            List<String> alsoKnownAs = getListOfStringFromJSONArray(alsoKnownAsJsonArray);

            String placeOfOrigin = jsonSandwich.getString(PLACE_OF_ORIGIN);

            String description = jsonSandwich.getString(DESCRIPTION);

            String image = jsonSandwich.getString(IMAGE);

            JSONArray ingredientsJsonArray = jsonSandwich.getJSONArray(INGREDIENTS);
            List<String> ingredients = getListOfStringFromJSONArray(ingredientsJsonArray);

            Sandwich thisSandwich = new Sandwich(mainName,
                                                 alsoKnownAs,
                                                 placeOfOrigin,
                                                 description,
                                                 image,
                                                 ingredients);
            return thisSandwich;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static List<String> getListOfStringFromJSONArray(JSONArray jsonArray) throws JSONException {
        List<String> result = new ArrayList<>();
        for(int i = 0; i < jsonArray.length(); i++)
            result.add(jsonArray.getString(i));
        return result;
    }
}
