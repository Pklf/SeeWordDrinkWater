package SWDW;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;

public class DataJson {

    public String today;
    String filename = "config.json";

    public static ArrayList<String> date_pass = new ArrayList<>();
    public static ArrayList<String> date_fail = new ArrayList<>();

    public DataJson(){
        today = LocalDate.now().toString();

        File directory = new File("json");
        if (! directory.exists()){
            directory.mkdir();
        }

        File f = new File("json/" + filename);
        if(!f.exists()) {
            createJSON(filename);
        }

        try {
            System.out.println("import json...\n");
            importJSON("config.json");
        } catch (Exception e){
            System.out.println("import probelm ar"+e.getMessage());
        }
        getTotalDate_Pass_Fail();
    }

    private JSONArray jsonArray;

    private static String jsonDir = "json/";
    public static JSONObject jsonFile;


    public int getNextTimeJson(){
        int jsonOb = jsonFile.getJSONArray("value").getJSONObject(0).getInt("nextTime");
        return jsonOb;
    }

    public void setNextTimeJson(int newNextTime){
        jsonFile.getJSONArray("value").getJSONObject(0).put("nextTime",newNextTime);
        exportJSON();
    }

    public int getBeepIntervalJson(){
        int jsonOb = jsonFile.getJSONArray("setting").getJSONObject(0).getInt("beepInterval");
        return jsonOb;
    }

    public void setBeepIntervalJson(int newBeepInterval){
        jsonFile.getJSONArray("setting").getJSONObject(0).put("beepInterval",newBeepInterval);
        exportJSON();
    }

    public Boolean getBeepLoopJson(){
        Boolean jsonOb = jsonFile.getJSONArray("setting").getJSONObject(0).getBoolean("beepLoop");
        return jsonOb;
    }

    public void setBeepLoopJson(Boolean newStatus){
        jsonFile.getJSONArray("setting").getJSONObject(0).put("beepLoop",newStatus);
        exportJSON();
    }

    public Boolean getWhiteQuiteJson(){
        Boolean jsonOb = jsonFile.getJSONArray("setting").getJSONObject(0).getBoolean("whiteQuite");
        return jsonOb;
    }

    public void setWhiteQuiteJson(Boolean newStatus){
        jsonFile.getJSONArray("setting").getJSONObject(0).put("whiteQuite",newStatus);
        exportJSON();
    }

    public Boolean getAllQuiteJson(){
        Boolean jsonOb = jsonFile.getJSONArray("setting").getJSONObject(0).getBoolean("allQuite");
        return jsonOb;
    }

    public void setAllQuiteJson(Boolean newStatus){
        jsonFile.getJSONArray("setting").getJSONObject(0).put("allQuite",newStatus);
        exportJSON();
    }

    public ArrayList<String> getWhiteListJson(){
        String element;
        ArrayList<String> result = new ArrayList<String>();
        JSONArray jsonOb = jsonFile.getJSONArray("whiteList");
        for(int i=0; i<jsonOb.length();i++){
            if(jsonOb.get(i) != JSONObject.NULL){
                element = jsonOb.getString(i);
                result.add(element);
            }
        }
        return result;
    }

    public void setWhiteListJson(ArrayList<String> newWhiteList){

        JSONArray whiteListArray = new JSONArray();
        jsonFile.put("whiteList",whiteListArray);
        for (String value : newWhiteList) {
            //whiteListArray.put(value);
            jsonFile.getJSONArray("whiteList").put(value);
        }
        //jsonFile.getJSONArray("whiteList").put(newWhiteList);
        exportJSON();
    }

    public ArrayList<Integer> getCalendarJson(LocalDate day){
        int element;
        ArrayList<Integer> result = new ArrayList<Integer>();
        JSONArray jsonOb = jsonFile.getJSONObject("calendar").getJSONArray(day.toString());
//        System.out.println("Go"+jsonOb);

        element = jsonOb.getJSONObject(0).getInt("amount");
        result.add(element);
        element = jsonOb.getJSONObject(0).getInt("target");
        result.add(element);
        return result;
    }

    public void getTotalDate_Pass_Fail(){
        JSONObject jsonObject = jsonFile.getJSONObject("calendar");
        ArrayList<Integer> result = new ArrayList<Integer>();

        for (String keyStr : jsonObject.keySet()) {
            JSONArray keyvalue = jsonObject.getJSONArray(keyStr);

            if(keyvalue.getJSONObject(0).getInt("amount") >= keyvalue.getJSONObject(0).getInt("target")){
                date_pass.add(keyStr);
            }else {

            date_fail.add(keyStr);
            }
        }

    }

    public ArrayList<Integer> getLastCalendar_Amount_Target_Json(){
        int element;
        JSONObject jsonObject = jsonFile.getJSONObject("calendar");
        ArrayList<Integer> result = new ArrayList<Integer>();
        Iterator<String> keys = jsonObject.keys();
        //System.out.println(keys);

        while (keys.hasNext()) {
            String tempKey = keys.next();

            if(!jsonObject.isNull(tempKey) && !keys.hasNext()){

                result = new ArrayList<Integer>();

                JSONArray jsonOb = jsonObject.getJSONArray(tempKey);

                element = jsonOb.getJSONObject(0).getInt("amount");
                result.add(element);
                element = jsonOb.getJSONObject(0).getInt("target");
                result.add(element);

                //Print key and value
                //System.out.println(result);

                //for nested objects iteration if required
                //if (keyvalue instanceof JSONObject)
                //    printJsonObject((JSONObject)keyvalue);
            }
        }
        return result;
    }

    public ArrayList<Integer> getTotalDay_Pass(LocalDate day1, LocalDate day2){
        JSONObject jsonObject = jsonFile.getJSONObject("calendar");
        ArrayList<Integer> result = new ArrayList<Integer>();
        int total = 0;
        int pass = 0;

        for (String keyStr : jsonObject.keySet()) {
            JSONArray keyvalue = jsonObject.getJSONArray(keyStr);

            LocalDate date = LocalDate.parse(keyStr);
            if(date.isAfter(day1) && date.isBefore(day2)){
                if(keyvalue.getJSONObject(0).getInt("amount") >= keyvalue.getJSONObject(0).getInt("target")){
                    //System.out.println("key: "+ keyStr + " value: " + keyvalue);
                    pass+=1;
                }
                total+=1;
                //Print key and value

                //for nested objects iteration if required
                //if (keyvalue instanceof JSONObject)
                //    printJsonObject((JSONObject)keyvalue);
            }
        }
        result.add(total);
        result.add(pass);

        return result;
    }

    public int getTargetJson(LocalDate day){
        int jsonOb = -1;
        try{
            ArrayList<Integer> result = getCalendarJson(day);
            jsonOb = result.get(1);
        }catch(Exception e){

        }
        return jsonOb;
    }

    public void setTargetJson(LocalDate day, int newTarget){
        jsonFile.getJSONObject("calendar").getJSONArray(day.toString()).getJSONObject(0).put("target",newTarget);
        exportJSON();
    }

    public int getAmountJson(LocalDate day){
        int jsonOb = -1;
        try{
            ArrayList<Integer> result = getCalendarJson(day);
            jsonOb = result.get(0);
            //System.out.println(result);
        }catch(Exception e){
            //e.printStackTrace();
        }
        return jsonOb;
    }

    public void setAmountJson(LocalDate day,int newAmount){
        jsonFile.getJSONObject("calendar").getJSONArray(day.toString()).getJSONObject(0).put("amount",newAmount);
        //System.out.println(jsonFile);
        exportJSON();
    }

//    public void setWhiteListJson(ArrayList<String> newWhiteList){
//
//        JSONArray whiteListArray = new JSONArray();
//        jsonFile.put("whiteList",whiteListArray);
//        for (String value : newWhiteList) {
//            //whiteListArray.put(value);
//            jsonFile.getJSONArray("whiteList").put(value);
//	}
//        //jsonFile.getJSONArray("whiteList").put(newWhiteList);
//        exportJSON("config.json");
//    }

    public JSONArray getJsonArray() {
        return jsonArray;
    }

    public void setJsonArray(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
    }

    public JSONObject importJSON(String filename) throws Exception{
        try{
            String content = readFile(filename);
            //System.out.println(content);
            if (content == null){
                return null;
            }
            jsonFile = new JSONObject(content);
            return new JSONObject(content);
        } catch (JSONException e){
            System.out.println("Import JSON problem: "+e.getMessage());
            return null;
        }
    }

    public String readFile(String filename){
        String content ="";
        try{
            FileReader fin = new FileReader(jsonDir+filename);
            BufferedReader in = new BufferedReader(fin);
            String line;
            while((line = in.readLine()) != null){
                content += line;
            }
            in.close();
        }catch (IOException e){
            System.out.println("File read problem: "+e.getMessage());
            return null;
        }
        return content;
    }

    public void exportJSON() {
        try {

            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(jsonDir + filename)));

            out.println(jsonFile);
            out.close();
            //return jsonObject;

        } catch (IOException e) {
            System.out.println("Export JSON problem: " + e.getMessage());
            //return null;
        }
    }

    public void createTodayJson(){

        JSONArray calendarArray = new JSONArray();

        ArrayList<Integer> lastDay =  getLastCalendar_Amount_Target_Json();

        calendarArray.put(new JSONObject().put("target",lastDay.get(1)).put("amount",0));

        jsonFile.getJSONObject("calendar").put(today, calendarArray);

        try {

            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(jsonDir + filename)));
            out.println(jsonFile);
            out.close();
            System.out.println(jsonFile);
        } catch (IOException e) {
            System.out.println("Create JSON problem: " + e.getMessage());
        }

        try {
            System.out.println("import json...\n");
            importJSON("config.json");
        } catch (Exception e){
            System.out.println("import probelm ar"+e.getMessage());
        }
    }

    public void createJSON(String filename) {

        JSONArray settingArray = new JSONArray();

        JSONObject settingElement = new JSONObject().put("beepInterval", 5000).put("beepLoop", false).put("whiteQuite", true).put("allQuite",false);
        settingArray.put(settingElement);

        JSONArray valueArray = new JSONArray();

        valueArray.put(new JSONObject().put("nextTime", 2700));

        JSONArray whiteListArray = new JSONArray();

        whiteListArray.put(JSONObject.NULL);

        JSONArray calendarArray = new JSONArray();
        String today = LocalDate.now().toString();

        JSONObject day = new JSONObject();

        calendarArray.put(new JSONObject().put("target",2000).put("amount",0));
        //calendarArray.put(JSONObject.NULL);
        day.put(today, calendarArray);

        JSONObject json = new JSONObject().put("calendar",day).put("setting",settingArray).put("value", valueArray).put("whiteList", whiteListArray);


        try {

            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(jsonDir + filename)));
            out.println(json);
            out.close();
            System.out.println(json);
        } catch (IOException e) {
            System.out.println("Create JSON problem: " + e.getMessage());
        }
    }

    public static void main(String[] args){
        DataJson test1 = new DataJson();
        //test1.createJSON("config.json");
        try {
            System.out.println("import json...\n");
            test1.importJSON("config.json");
        } catch (Exception e){
            System.out.println("import probelm ar"+e.getMessage());
        }
        //System.out.println(test1.getTargetJson());
        //System.out.println(test1.getAllQuiteJson());
        //System.out.println(test1.getBeepIntervalJson());
        //System.out.println(test1.getBeepLoopJson());
        //System.out.println(test1.getWhiteQuiteJson());
        //System.out.println(test1.getNextTimeJson());
        //System.out.println(test1.getWhiteListJson());
        //System.out.println(test1.getAmountJson());
        //test1.setTargetJson(2500);
        LocalDate today = LocalDate.now();
        LocalDate next = LocalDate.now().plusDays(1L);

        //System.out.println(test1.getCalendarJson(today));
        //System.out.println(test1.getCalendarJson(next));

        String date = "2020-08-16";
        LocalDate localDate = LocalDate.parse(date);

        date = "2020-12-30";
        LocalDate localDate2 = LocalDate.parse(date);

        test1.getTotalDay_Pass(localDate, localDate2);
    }

}
