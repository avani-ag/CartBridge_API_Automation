package utils;


import org.apache.commons.io.FileUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;

public class JSONReader {

    public static Object GetJsonValue(String key, String filepath)
    {
        JSONObject jsonobj = getJSONReader(filepath);
        Object value = jsonobj.get(key);
        return value;
    }

    private static JSONObject getJSONReader(String filepath)

    {     JSONObject jsonObject = null;

        try {
            File fileobj = new File(filepath);
            String jsonfile = FileUtils.readFileToString(fileobj, "UTF-8");
            Object parseobj = new JSONParser().parse(jsonfile);
            jsonObject = (JSONObject) parseobj;
        }

        catch (Exception e)
        {
            System.out.println("Exception occurred in reading JSON File ");
            e.printStackTrace();
        }

        return jsonObject;


    }


}
