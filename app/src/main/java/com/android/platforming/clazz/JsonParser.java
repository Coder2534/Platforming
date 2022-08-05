package com.android.platforming.clazz;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Arrays;
import java.util.List;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class JsonParser {

    public static List JsonParser(String resultJson) {
        try {
            String result;
            JSONObject jsonObject = new JSONObject(resultJson);
            JSONObject jsonObject2;
            JSONObject jsonObject3;
            JSONArray jsonArray;
            JSONArray jsonArray2;


            jsonArray = jsonObject.getJSONArray("mealServiceDietInfo");
            Log.d("Array1_parser", String.valueOf(jsonArray));
            jsonObject2 = jsonArray.getJSONObject(1);
            Log.d("Array2_parser", String.valueOf(jsonObject2));
            jsonArray2 = jsonObject2.getJSONArray("row");
            Log.d("Array3_parser", String.valueOf(jsonArray2));
            jsonObject3 = jsonArray2.getJSONObject(0);
            Log.d("Array4_parser", String.valueOf(jsonObject3));
            result = jsonObject3.getString("DDISH_NM");
            Log.d("Array5_parser", result);

            String[] foodNames = result.split("<br/>");
            for (int i = 0; i < foodNames.length; ++i){
                Log.d("foodNames_parser", foodNames[i]);
            }

            List<String> list = Arrays.asList(foodNames);
            return list;

        } catch (JSONException e) {
            Log.d("error_parser", e.getMessage());
        }
        return null;
    }
}
