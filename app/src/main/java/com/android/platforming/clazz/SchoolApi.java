package com.android.platforming.clazz;

import android.util.Log;

import com.android.platforming.interfaze.ListenerInterface;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class SchoolApi {

    //global
    List result;
    public List<String> getResult(){
        return result;
    }

    ListenerInterface listenerInterface;

    String year;
    String month;
    String day;

    public String getYear() {
        return year;
    }

    public String getMonth() {
        return month;
    }

    public String getDay() {
        return day;
    }

    public String getDate() {
        return String.format("%s년 %s월 %s일",year, month, day);
    }

    public String fixDate(String date){
        if(Integer.parseInt(date) < 10)
            return "0" + date;
        else
            return date;
    }

    private String dateFormat(Date date){
        SimpleDateFormat dateFormat_year = new SimpleDateFormat("yyyy");
        year = dateFormat_year.format(date);
        SimpleDateFormat dateFormat_month = new SimpleDateFormat("M");
        month = dateFormat_month.format(date);
        SimpleDateFormat dateFormat_day = new SimpleDateFormat("d");
        day = dateFormat_day.format(date);

        return year + month + day;
    }

    //schoolMeal
    String keyMeal = "key=abfda37c182d43bb9fa4a7e698b91fbb";
    String TypeMeal = "&Type=json";
    String baseUrlMeal = "https://open.neis.go.kr/hub/mealServiceDietInfo?";
    String resultUrlMeal;

    public void getSchoolMeal(String year, String month, String day, ListenerInterface listenerInterface) throws InterruptedException {
        this.listenerInterface = listenerInterface;

        this.year = year;
        this.month = month;
        this.day = day;

        String month_fix = fixDate(month);
        String day_fix = fixDate(day);

        resultUrlMeal = getResultUrlMeal(year + month_fix + day_fix);
        threadMeal.setDaemon(true);
        threadMeal.start();
    }

    public void getSchoolMeal(ListenerInterface listenerInterface) throws InterruptedException {
        this.listenerInterface = listenerInterface;

        resultUrlMeal = getResultUrlMeal(dateFormat(new Date(System.currentTimeMillis())));
        threadMeal.setDaemon(true);
        threadMeal.start();
    }

    public void joinThreadMeal() throws InterruptedException{
        threadMeal.join();
    }

    private String getResultUrlMeal(String date){
        String sub_url = "&ATPT_OFCDC_SC_CODE=R10&SD_SCHUL_CODE=8750447&MLSV_YMD="+date;
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
                        result = jsonParser.jsonParseMeal(line);
                        if(result == null){
                            result = Collections.singletonList("등록된 식단이 없습니다.");
                        }
                    }
                    listenerInterface.onSuccess();
                    bf.close();
                }
                conn.disconnect();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    });

    //schoolSchedule
    int dayOfWeek;

    String keySchedule = "key=abfda37c182d43bb9fa4a7e698b91fbb";
    String TypeSchedule = "&Type=json";
    String baseUrlSchedule = "https://open.neis.go.kr/hub/SchoolSchedule?";
    String resultUrlSchedule;

    public void getSchoolSchedule(String year, String month, String day, int dayOfWeek, ListenerInterface listenerInterface) throws InterruptedException {
        this.listenerInterface = listenerInterface;

        this.year = year;
        this.month = month;
        this.day = day;
        this.dayOfWeek = dayOfWeek;

        String month_fix = fixDate(month);
        String day_fix = fixDate(day);

        resultUrlSchedule = getResultUrlSchedule(year + month_fix + day_fix);
        threadSchedule.setDaemon(true);
        threadSchedule.start();
    }

    public void getSchoolSchedule(int dayOfWeek, ListenerInterface listenerInterface) throws InterruptedException {
        this.listenerInterface = listenerInterface;

        this.dayOfWeek = dayOfWeek;

        resultUrlSchedule = getResultUrlSchedule(dateFormat(new Date(System.currentTimeMillis())));
        threadSchedule.setDaemon(true);
        threadSchedule.start();
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
                        result = jsonParser.jsonParseSchedule(line);
                        if(result == null){
                            result = Collections.singletonList("등록된 일정이 없습니다.");
                        }
                    }
                    listenerInterface.onSuccess();
                    bf.close();
                }
                conn.disconnect();
            }
        }catch (Exception e) {
            Log.d("error_School",e.getMessage());
        }
    });

    public String getDayOfWeek(){
        String dayOfWeek = null;
        switch (this.dayOfWeek){
            case 1:
                dayOfWeek = "(일요일)";
                break;
            case 2:
                dayOfWeek = "(월요일)";
                break;
            case 3:
                dayOfWeek = "(화요일)";
                break;
            case 4:
                dayOfWeek = "(수요일)";
                break;
            case 5:
                dayOfWeek = "(목요일)";
                break;
            case 6:
                dayOfWeek = "(금요일)";
                break;
            case 7:
                dayOfWeek = "(토요일)";
                break;
        }
        return dayOfWeek;
    }
}