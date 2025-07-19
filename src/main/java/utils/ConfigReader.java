package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    public static Object GetProperty(String key)
    {

        String filepath = "resources/properties.config";
        String value = null;

        try {
            InputStream configfile = new FileInputStream(filepath);
            Properties propertyObj  = new Properties();
            propertyObj.load(configfile);
            value = propertyObj.getProperty(key);

        }
        catch(Exception exception)
        {
            System.out.println("Exception occurred in fetching value from property file.");
            exception.printStackTrace();
        }



        return value;

    }

}
