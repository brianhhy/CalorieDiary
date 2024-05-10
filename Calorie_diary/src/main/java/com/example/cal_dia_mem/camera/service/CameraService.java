package com.example.cal_dia_mem.camera.service;
import com.example.cal_dia_mem.camera.dto.OcrDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.*;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CameraService {

    public String ApiService (String myemail){
        OcrDTO ocr=new OcrDTO();
        String serverUrl = "http://49.50.160.28:5000/v1/object-detection/food_uolov5s";
        String imagePath = "src/main/resources/static/food/"+myemail+"_food.jpg";
        String nameValue = null;

        // Set up headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // Set up the request entity with headers and image file
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("image", new FileSystemResource(new File(imagePath)));
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        // Create RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // Execute the POST request
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                serverUrl,
                HttpMethod.POST,
                requestEntity,
                String.class
        );
        String result = responseEntity.getBody();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(result);

            // 배열에서 첫 번째 객체를 가져옴
            JsonNode firstObject = jsonNode.get(0);

            // "class" 필드의 값을 가져옴
            nameValue = firstObject.get("class").asText();

            System.out.println("Class: " + nameValue);


        } catch (Exception e) {
            e.printStackTrace();
        }

        // Print the response
        System.out.println("Response: " + responseEntity.getBody());

        return nameValue;
    }
    public OcrDTO ocr(String myEmail){
        String apiURL = "https://s8ajln5gaa.apigw.ntruss.com/custom/v1/25873/a17bbc25bf454fc8958a92d5e2a00befbf9f1e8f435e110f9098198080056a85/general"; //API URL
        String secretKey = "cWlmemNNTnNvSkp0TlFWVHpEc0VvWElTSWFBY1lDb2E="; //API secret key
        String foodName= " ";
        String kcal = null;
        String SaltInfo= null;
        String CarbohydrateInfo= null;
        String SugarInfo= null;
        String FatInfo= null;
        String TransFatInfo= null;
        String SaturatedFatInfo= null;
        String CholesterolInfo= null;
        String ProteinInfo= null;
        OcrDTO data = new OcrDTO();

        try {
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setUseCaches(false);
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            con.setRequestProperty("X-OCR-SECRET", secretKey);

            JSONObject json = new JSONObject();
            json.put("version", "V2");
            json.put("requestId", UUID.randomUUID().toString());
            json.put("timestamp", System.currentTimeMillis());
            JSONObject image = new JSONObject();
            image.put("format", "jpg");

            FileInputStream inputStream = new FileInputStream("src/main/resources/static/food/"+myEmail+"_nutrient.jpg"); //스캔할 파일 경로
            byte[] buffer = new byte[inputStream.available()];

            inputStream.read(buffer);
            inputStream.close();
            image.put("data", buffer);
            image.put("name", "demo");
            JSONArray images = new JSONArray();
            images.put(image);
            json.put("images", images);
            String postParams = json.toString();

            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(postParams);
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();
            BufferedReader br;
            if (responseCode == 200) {
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();


            String Response = response.toString(); // 스트링버퍼를 => 스트링으로

            StringBuffer nutrient = new StringBuffer(); // 스트링버퍼 선언

            JSONObject jsonObject = new JSONObject(Response);    // 가장큰 JSONObject를 가져온다

            JSONArray jsonArray = jsonObject.getJSONArray("images");  // 배열을 가져온다
            for(int i=0; i < jsonArray.length(); i++){
                JSONObject obj = jsonArray.getJSONObject(i);
                JSONArray fields = obj.getJSONArray("fields");
                for(int j=0; j < fields.length(); j++){
                    JSONObject field=fields.getJSONObject(j);
                    String inferText = field.getString("inferText");
                    nutrient.append(inferText); //위에 선언한 스트링 버퍼에 각 infeerText를 삽입
                    nutrient.append(" "); //inferText 하나마다 칸을 띄어줌
                    System.out.println("inferText("+j+"): "+inferText); //각 inferText 출력
                }
            }

            System.out.println(response); // API에서온 Json 파일을 출력

            String NutrientFacts=nutrient.toString(); // 스트링버퍼를 스트링으로 변환
            NutrientFacts=removeSpaceAfterNumber(NutrientFacts); // 숫자 앞에 공백 지우기
            NutrientFacts=removeCommas(NutrientFacts); // 콤마(,) 지우기

            // 원하는 영양성분 이름

            String NutrientData;

            kcal = extractNumberBeforeKcal(NutrientFacts);

            String Salt = "나트륨";
            String Carbohydrate = "탄수화물";
            String Sugar = "당류";
            String Fat = "지방";
            String TransFat = "트랜스지방";
            String SaturatedFat = "포화지방";
            String Cholesterol = "콜레스테롤";
            String Protein = "단백질";

            // 영양성분 정보 추출
            SaltInfo = extractNutrientInfo(NutrientFacts, Salt);
            SaltInfo = extractNutrientInfo(NutrientFacts, Salt);
            CarbohydrateInfo = extractNutrientInfo(NutrientFacts, Carbohydrate);
            SugarInfo = extractNutrientInfo(NutrientFacts, Sugar);
            FatInfo = extractNutrientInfo(NutrientFacts, Fat);
            TransFatInfo = extractNutrientInfo(NutrientFacts, TransFat);
            SaturatedFatInfo = extractNutrientInfo(NutrientFacts, SaturatedFat);
            CholesterolInfo = extractNutrientInfo(NutrientFacts, Cholesterol);
            ProteinInfo = extractNutrientInfo(NutrientFacts, Protein);

            /*SaltInfo=extractStringAfterKorean(SaltInfo);
            CarbohydrateInfo = extractStringAfterKorean(CarbohydrateInfo);
            SugarInfo = extractStringAfterKorean(SugarInfo);
            FatInfo = extractStringAfterKorean(FatInfo);
            TransFatInfo = extractStringAfterKorean(TransFatInfo);
            SaturatedFatInfo = extractStringAfterKorean(SaturatedFatInfo);
            CholesterolInfo = extractStringAfterKorean(CholesterolInfo);
            ProteinInfo = extractStringAfterKorean(ProteinInfo);*/


            //문자열 가공 --> 띄어쓰기 뒷부분만 추출 --> 영양성분만 추출하겠다는 뜻 ex) 탄수화물 36g 에서 36g만 추출
            int spaceIndex = SaltInfo.indexOf(' ');
            // 띄어쓰기가 있는 경우에만 추출
            SaltInfo = (spaceIndex != -1) ? SaltInfo.substring(spaceIndex + 1) : SaltInfo;

            //위에 두 줄을 반복
            spaceIndex = CarbohydrateInfo.indexOf(' ');
            CarbohydrateInfo= (spaceIndex != -1) ? CarbohydrateInfo.substring(spaceIndex + 1) : CarbohydrateInfo;

            spaceIndex = SugarInfo.indexOf(' ');
            SugarInfo= (spaceIndex != -1) ? SugarInfo.substring(spaceIndex + 1) : SugarInfo;

            spaceIndex = FatInfo.indexOf(' ');
            FatInfo= (spaceIndex != -1) ? FatInfo.substring(spaceIndex + 1) : FatInfo;

            spaceIndex = TransFatInfo.indexOf(' ');
            TransFatInfo= (spaceIndex != -1) ? TransFatInfo.substring(spaceIndex + 1) : TransFatInfo;

            spaceIndex = SaturatedFatInfo.indexOf(' ');
            SaturatedFatInfo= (spaceIndex != -1) ? SaturatedFatInfo.substring(spaceIndex + 1) : SaturatedFatInfo;

            spaceIndex = CholesterolInfo.indexOf(' ');
            CholesterolInfo= (spaceIndex != -1) ? CholesterolInfo.substring(spaceIndex + 1) : CholesterolInfo;

            spaceIndex = ProteinInfo.indexOf(' ');
            ProteinInfo= (spaceIndex != -1) ? ProteinInfo.substring(spaceIndex + 1) : ProteinInfo;

            //두번 째 문자열 가공 --> 숫자부분만 추출 --> ex) 26g 에서 26만 추출

            Pattern pattern = Pattern.compile("[^0-9.]");
            Matcher matcher = pattern.matcher(SaltInfo);
            SaltInfo = matcher.replaceAll("");

            matcher = pattern.matcher(CarbohydrateInfo);
            CarbohydrateInfo = matcher.replaceAll("");

            matcher = pattern.matcher(SugarInfo);
            SugarInfo = matcher.replaceAll("");

            matcher = pattern.matcher(FatInfo);
            FatInfo = matcher.replaceAll("");

            matcher = pattern.matcher(TransFatInfo);
            TransFatInfo = matcher.replaceAll("");

            matcher = pattern.matcher(SaturatedFatInfo);
            SaturatedFatInfo = matcher.replaceAll("");

            matcher = pattern.matcher(CholesterolInfo);
            CholesterolInfo = matcher.replaceAll("");

            matcher = pattern.matcher(ProteinInfo);
            ProteinInfo = matcher.replaceAll("");

            data.setFoodName("  ");
            data.setKcal(kcal);
            data.setSalt(SaltInfo);
            data.setCarbohydrate(CarbohydrateInfo);
            data.setSugars(SugarInfo);
            data.setFat(FatInfo);
            data.setTransfat(TransFatInfo);
            data.setSaturated_fatty(SaturatedFatInfo);
            data.setCholesterol(CholesterolInfo);
            data.setProtein(ProteinInfo);



            //전체 문자열 출력
            //System.out.println("테스트 :"+ data);
            // 결과 출력
            System.out.println(kcal+" : kcal");
            System.out.println("추출된 " + Salt + " 정보: " + SaltInfo);
            System.out.println("추출된 " + Carbohydrate + " 정보: " + CarbohydrateInfo);
            System.out.println("추출된 " + Sugar + " 정보: " + SugarInfo);
            System.out.println("추출된 " + Fat + " 정보: " + FatInfo);
            System.out.println("추출된 " + TransFat + " 정보: " + TransFatInfo);
            System.out.println("추출된 " + SaturatedFat + " 정보: " + SaturatedFatInfo);
            System.out.println("추출된 " + Cholesterol + " 정보: " + CholesterolInfo);
            System.out.println("추출된 " + Protein + " 정보: " + ProteinInfo);




        } catch (Exception e) {
            System.out.println(e);
        }

        return data;
    }


    public static String extractNutrientInfo(String input, String targetNutrient) {
        // 영양성분 정보를 찾기 위한 패턴
        String patternString = targetNutrient + " (\\d+(\\.\\d+)?[a-z]?[a-z]?)"; //a-z 는 영문자만 존재하는가,
        // 정규표현식 패턴 정의
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            // 매칭된 영양성분 정보 반환
            return matcher.group();
        } else {
            String patternString1 = targetNutrient + "(\\d+(\\.\\d+)?[a-z]?[a-z]?)";
            Pattern pattern1 = Pattern.compile(patternString1);
            Matcher matcher1 = pattern1.matcher(input);
            if(matcher1.find()){
                return matcher1.group();
            }else{
                return targetNutrient + " 0g";
            }
            // 매칭되는 정보가 없을 경우 예외 처리
        }
    }
    public static String removeSpaceAfterNumber(String input) {
        // 숫자 다음 공백 제거
        String processedString = input.replaceAll("(\\d+)\\s", "$1");

        return processedString;
    }
    public static String removeCommas(String input) {
        // 쉼표(,)를 제거한 문자열 반환
        String processedString = input.replaceAll(",", "");
        //processedString=input.replaceAll("g"," ");
        return processedString;
    }
    public static String extractNumberBeforeKcal(String input) {
        String pattern = "(\\d+)kcal";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(input);

        if (m.find()) {
            return m.group(1); // Group 1 contains the matched number
        } else {
            return null; // If "kcal" is not found or no number is present before it
        }
    }
    public static String extractStringAfterKorean(String input) {
        // 한글 유니코드 범위
        int koreanStart = '가';
        int koreanEnd = '힣';

        int koreanIndex = -1;

        // 입력 문자열에서 한글 다음 문자열 찾기
        for (int i = 0; i < input.length(); i++) {
            char currentChar = input.charAt(i);
            if (currentChar >= koreanStart && currentChar <= koreanEnd) {
                koreanIndex = i;
                break;
            }
        }

        if (koreanIndex != -1) {
            // 한글 다음의 문자열 추출
            return input.substring(koreanIndex + 1);
        } else {
            // 한글이 없는 경우 빈 문자열 반환
            return "";
        }
    }

}

