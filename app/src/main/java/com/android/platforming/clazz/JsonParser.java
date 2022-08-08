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

    public List jsonParseMeal(String resultJson){
        try {
            String result;
            JSONObject jsonObject = new JSONObject(resultJson);
            JSONObject jsonObject2;
            JSONObject jsonObject3;
            JSONArray jsonArray;
            JSONArray jsonArray2;


            jsonArray = jsonObject.getJSONArray("mealServiceDietInfo");
            Log.d("jsonParseMeal1", String.valueOf(jsonArray));
            jsonObject2 = jsonArray.getJSONObject(1);
            Log.d("jsonParseMeal2", String.valueOf(jsonObject2));
            jsonArray2 = jsonObject2.getJSONArray("row");
            Log.d("jsonParseMeal3", String.valueOf(jsonArray2));
            jsonObject3 = jsonArray2.getJSONObject(0);
            Log.d("jsonParseMeal4", String.valueOf(jsonObject3));
            result = jsonObject3.getString("DDISH_NM");
            Log.d("jsonParseMeal5", result);

            String[] foodNames = result.split("<br/>");
            for (int i = 0; i < foodNames.length; ++i){
                Log.d("jsonParseMeal", foodNames[i]);
            }

            List<String> list = Arrays.asList(foodNames);
            return list;

        } catch (JSONException e) {
            Log.d("error_parser_meal", e.getMessage());
        }
        return null;
    }

    public List jsonParseSchedule(String resultJson){
        try {
            Log.d("check", "ok");
            String result;
            JSONObject jsonObject = new JSONObject(resultJson);
            JSONObject jsonObject2;
            JSONObject jsonObject3;
            JSONArray jsonArray;
            JSONArray jsonArray2;


            jsonArray = jsonObject.getJSONArray("SchoolSchedule");
            Log.d("jsonParseSchedule1", String.valueOf(jsonArray));
            jsonObject2 = jsonArray.getJSONObject(1);
            Log.d("jsonParseSchedule2", String.valueOf(jsonObject2));
            jsonArray2 = jsonObject2.getJSONArray("row");
            Log.d("jsonParseSchedule3", String.valueOf(jsonArray2));
            jsonObject3 = jsonArray2.getJSONObject(0);
            Log.d("jsonParseSchedule4", String.valueOf(jsonObject3));
            result = jsonObject3.getString("EVENT_NM");
            Log.d("jsonParseSchedule5", result);

            String[] schedulenames = result.split("<br/>");
            for (int i = 0; i < schedulenames.length; ++i){
                Log.d("Names_parser", schedulenames[i]);
            }

            List<String> list = Arrays.asList(schedulenames);
            return list;

        } catch (JSONException e) {
            Log.d("error_parser_schedule", e.getMessage());
        }
        return null;
    }

}
