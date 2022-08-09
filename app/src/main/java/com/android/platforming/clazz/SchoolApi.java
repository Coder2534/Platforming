package com.android.platforming.clazz;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class SchoolApi {

    List resultmeal;
    public List<String> getMealResult(){
        return resultmeal;
    }

    //schoolMeal
    String keyMeal = "key=abfda37c182d43bb9fa4a7e698b91fbb";
    String TypeMeal = "&Type=json";
    String baseUrlMeal = "https://open.neis.go.kr/hub/mealServiceDietInfo?";
    String resultUrlMeal;

    public void getSchoolMeal(String calenerTime) throws InterruptedException {
        resultUrlMeal = getResultUrlMeal(calenerTime);

        threadMeal.start();
        threadMeal.join();
    }

    public void getSchoolMeal() throws InterruptedException {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
        String y = dateFormat.format(date);
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("MM");
        String m = dateFormat1.format(date);
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd");
        String d = dateFormat2.format(date);

        resultUrlMeal = getResultUrlMeal(y+m+d);

        threadMeal.start();
        threadMeal.join();
    }

    private String getResultUrlMeal(String calenerTime){
        String sub_url = "&ATPT_OFCDC_SC_CODE=R10&SD_SCHUL_CODE=8750447&MLSV_YMD="+calenerTime;
        String result_URL = baseUrlMeal + keyMeal + TypeMeal +"&Plndex=1&pSize=10"+ sub_url;
        return result_URL;
    }

    Thread threadMeal = new Thread(() -> {
        Log.d("SchoolApi", "url : " + resultUrlMeal);
        try {
            URL url = new URL(resultUrlMeal);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();

            if (conn != null){
                conn.connect();
                conn.setConnectTimeout(10000);
                conn.setRequestMethod("GET");

                int recode = conn.getResponseCode();
                int HTTP_OK = HttpURLConnection.HTTP_OK;
                Log.d("SchoolApi","recode : "+ recode);
                Log.d("SchoolApi","HTTp_OK : "+ HTTP_OK);

                if(recode == HTTP_OK){
                    BufferedReader bf = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                    String line;

                    while (true){
                        line = bf.readLine();

                        if(line == null){
                            break;
                        }
                        JsonParser jsonParser = new JsonParser();
                        resultmeal = jsonParser.jsonParseMeal(line);
                        if(resultmeal == null){
                            resultmeal = Collections.singletonList("등록된 식단이 없습니다.");
                        }
                        Log.d("SchoolApi", String.valueOf(resultmeal));
                    }
                    bf.close();
                }
                conn.disconnect();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    });

    //schoolSchedule
    List resultschedule;
    public List<String> getScheduleResult(){
        return resultschedule;
    }
    String keySchedule = "key=abfda37c182d43bb9fa4a7e698b91fbb";
    String TypeSchedule = "&Type=json";
    String baseUrlSchedule = "https://open.neis.go.kr/hub/SchoolSchedule?";
    String resultUrlSchedule;

    public void getSchoolSchedule(String calenerTime) throws InterruptedException {
        resultUrlSchedule = getResultUrlSchedule(calenerTime);

        threadSchedule.start();
        threadSchedule.join();
    }

    public void getSchoolSchedule() throws InterruptedException {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
        String y = dateFormat.format(date);
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("MM");
        String m = dateFormat1.format(date);
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd");
        String d = dateFormat2.format(date);

        resultUrlSchedule = getResultUrlSchedule(y+m+d);

        threadSchedule.start();
        threadSchedule.join();
    }

    private String getResultUrlSchedule(String calenerTime){
        String subUrl = "&ATPT_OFCDC_SC_CODE=R10&SD_SCHUL_CODE=8750447&AA_YMD="+calenerTime;
        String resultUrl = baseUrlSchedule + keySchedule + TypeSchedule +"&Plndex=1&pSize=10"+ subUrl;
        return resultUrl;
    }

    Thread threadSchedule = new Thread(() -> {
        Log.d("SchoolApi_Schedule", resultUrlSchedule);
        try {
            URL url = new URL(resultUrlSchedule);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();

            if (conn != null){
                conn.connect();
                conn.setConnectTimeout(10000);
                conn.setRequestMethod("GET");


                int recode = conn.getResponseCode();
                int HTTP_OK = HttpURLConnection.HTTP_OK;
                Log.d("SchoolApi","recode : "+ recode);
                Log.d("SchoolApi","HTTp_OK : "+ HTTP_OK);

                if(recode == HTTP_OK){
                    BufferedReader bf = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                    String line;

                    while (true){
                        line = bf.readLine();

                        if(line == null){
                            break;
                        }
                        JsonParser jsonParser = new JsonParser();
                        resultschedule = jsonParser.jsonParseSchedule(line);
                        if(resultschedule == null){
                            resultschedule = Collections.singletonList("등록된 일정이 없습니다.");
                        }
                        Log.d("result_SchoolSchedule", String.valueOf(resultschedule));
                    }
                    bf.close();
                }
                conn.disconnect();
            }
        }catch (Exception e) {
            Log.d("error_School",e.getMessage());
        }
    });
}