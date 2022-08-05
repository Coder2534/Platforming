package com.android.platforming.clazz;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.List;

public class SchoolApi extends Thread {
    List result;
    String calenerTime = ;
    String key = "key=abfda37c182d43bb9fa4a7e698b91fbb";
    String Type = "&Type=json";
    String base_url = "https://open.neis.go.kr/hub/mealServiceDietInfo?";
    String sub_url = "&ATPT_OFCDC_SC_CODE=R10&SD_SCHUL_CODE=8750447&MLSV_YMD="+calenerTime;
    String result_URL;

    public void schoolApi(int Time){
        calenerTime = String.valueOf(Time);
        result_URL = base_url + key + Type+"&Plndex=1&pSize=10"+ sub_url;
    }




    @Override
    public void run() {
        Log.d("result_URL_School", result_URL);
        try {
            URL url = new URL(result_URL);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();

            if (conn != null){
                conn.connect();
                conn.setConnectTimeout(10000);
                conn.setRequestMethod("GET");


                int recode = conn.getResponseCode();
                int HTTP_OK = HttpURLConnection.HTTP_OK;
                Log.d("json_School","recode : "+ recode);
                Log.d("json_School","HTTp_OK : "+ HTTP_OK);

                if(recode == HTTP_OK){
                    BufferedReader bf = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                    String line;

                    while (true){
                        line = bf.readLine();

                        if(line == null){
                            break;
                        }
                        result = JsonParser.JsonParser(line);
                        if(result == null){
                            result = Collections.singletonList("등록된 식단이 없습니다.");
                        }
                        Log.d("result_School", String.valueOf(result));
                    }
                    bf.close();
                }
                conn.disconnect();
            }
        }catch (Exception e) {
            Log.d("error_School",e.getMessage());
        }
    }
    public List<String> getResult(){
        return result;
    }

}