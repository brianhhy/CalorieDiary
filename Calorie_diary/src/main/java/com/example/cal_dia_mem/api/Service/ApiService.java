package com.example.cal_dia_mem.api.Service;

import com.example.cal_dia_mem.api.dto.ApiDTO;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ApiService {
    public String callApi(String foodname) {
        StringBuilder result = new StringBuilder();
        try {
            String apiUrl = "http://openapi.foodsafetykorea.go.kr/api/db8f865acfd64a048404/I2790/json/1/1000/" +
                    "DESC_KOR=" + foodname;

            URL url = new URL(apiUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));

            String returnLine;

            while((returnLine = bufferedReader.readLine()) != null) {
                result.append(returnLine + "\n");
            }
            urlConnection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    public List<ApiDTO> jsonParse(String jsonStr) throws ParseException {
        List<ApiDTO> dataList = new ArrayList<>();
        JSONParser parser = new JSONParser();
        JSONObject object = (JSONObject) parser.parse(jsonStr);
        JSONObject i2790Object = (JSONObject) object.get("I2790");
        String totalCount = (String) i2790Object.get("total_count");
        System.out.println("Total Count: " + totalCount);  // 검색결과 개수 출력

        JSONArray rowArray = (JSONArray) i2790Object.get("row");

        for (Object item : rowArray) {
            JSONObject jsonItem = (JSONObject) item;

            ApiDTO data = new ApiDTO();
            data.setNum((String) jsonItem.get("NUM"));
            data.setFood_name((String) jsonItem.get("DESC_KOR"));
            data.setMaker_name((String) jsonItem.get("MAKER_NAME"));
            data.setServing_size((String) jsonItem.get("SERVING_SIZE"));
            data.setKcal((String) jsonItem.get("NUTR_CONT1"));
            data.setCarbohydrate((String) jsonItem.get("NUTR_CONT2"));
            data.setProtein((String) jsonItem.get("NUTR_CONT3"));
            data.setFat((String) jsonItem.get("NUTR_CONT4"));
            data.setSugars((String) jsonItem.get("NUTR_CONT5"));
            data.setSalt((String) jsonItem.get("NUTR_CONT6"));
            data.setCholesterol((String) jsonItem.get("NUTR_CONT7"));
            data.setSaturated_fatty((String) jsonItem.get("NUTR_CONT8"));
            data.setTransfat((String) jsonItem.get("NUTR_CONT9"));

            dataList.add(data);
        }
        return dataList;
    }
}

