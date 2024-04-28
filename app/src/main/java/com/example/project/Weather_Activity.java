package com.example.project;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Weather_Activity extends Thread{
    public void func() throws IOException, JSONException {
        String endPoint =  "http://apis.data.go.kr/1360000/VilageFcstInfoService/";
        String serviceKey = "lMVeP6C%2FH0HG0vEJ4pCnrRb3MVCmPBPs0AwzeSYZdMV%2Fxtkveko2nQ%2F8iSUcRbF7Ce5k62hw%2Bd6LF9clum1k9Q%3D%3D";
        String pageNo = "1";
        String numOfRows = "10";
        String baseDate = "20240428"; //원하는 날짜
        String baseTime = "1940"; //원하는 시간
        String nx = "37.7157"; //위경도임.
        String ny = "127.166"; //위경도 정보는 api문서 볼 것

        String s = endPoint+"getVilageFcst?serviceKey="+serviceKey
                +"&pageNo=" + pageNo
                +"&numOfRows=" + numOfRows
                +"+&dataType=JSON"
                + "&base_date=" + baseDate
                +"&base_time="+baseTime
                +"&nx="+nx
                +"&ny="+ny;

        URL url = new URL(s);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader bufferedReader = null;
        if(conn.getResponseCode() == 200) {
            bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        }else{
            //connection error :(
        }
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
        }
        bufferedReader.close();
        String result= stringBuilder.toString();
        conn.disconnect();

        JSONObject mainObject = new JSONObject(result);
        JSONArray itemArray = mainObject.getJSONObject("response").getJSONObject("body").getJSONObject("items").getJSONArray("item");
        for(int i=0; i<itemArray.length(); i++){
            JSONObject item = itemArray.getJSONObject(i);
            String category = item.getString("category");
            String value = item.getString("fcstValue");
            System.out.println(category+"  "+value);
        }
    }
}