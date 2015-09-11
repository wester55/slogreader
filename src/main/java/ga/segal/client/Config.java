package ga.segal.client;

import java.io.IOException;
import java.io.FileInputStream;
import java.util.Properties;
//import java.lang.Throwable;

/**
 * Created by Sasha on 9/4/2015.
 */
public class Config {

    private Integer interval;
    private String path;

    Config(){
        Integer interval;
        String path;
    }

    public void GetConfig () throws IOException{

        Properties prop = new Properties();
        FileInputStream input = null;
        System.out.println("Loading config...");
        try {

            String filename = "config.properties";
            input = new FileInputStream(filename);
            if (input == null) {
                System.out.println("Sorry, unable to find " + filename);
                return;
            }

            //load a properties file from class path, inside static method
            prop.load(input);

            interval = Integer.parseInt(prop.getProperty("interval"));
            path = prop.getProperty("path");

        } catch (Exception ex) {
            ex.printStackTrace();
            return;
        } finally{
            if(input!=null){
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    String GetPath(){
        return path;
    }

    int GetInterval(){
        return interval;
    }
}
