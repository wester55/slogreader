package ga.segal.client;

import java.lang.Exception;
//import java.lang.Throwable;
//import java.io.FileNotFoundException;
//import java.io.IOException;
import java.io.FileInputStream;
import java.util.Properties;
//import java.lang.Throwable;

/**
 * Created by Sasha on 9/4/2015.
 */
@SuppressWarnings("DefaultFileTemplate")
public class Client {

    public static void main(String[] args) {

        String parameters[] = {"",""};
        String file_name = "config.properties";

        Client cl = new Client();
        try {
            parameters = cl.Config(file_name);
        } catch (Exception e) {
            System.out.printf("Unable to find config, stack trace: %s%n", e.getMessage());
            System.exit(1);
        }
        System.out.printf("Success: %s %s%n",parameters[0],parameters[1]);

        /*int interval = cf.GetInterval() * 60;
        String path = cf.GetPath();
        System.out.printf("Running on: %s at interval %d seconds%n", path, interval);
*/
        /*int i = 0;
        while (running) {
            System.out.println("count=" + i);
            i++;
            try {
                Thread.sleep(1000 * interval);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/
    }

    private String[] Config(String filename) throws Exception {

        String interval;
        String path;
        Properties prop = new Properties();
        FileInputStream input = new FileInputStream(filename);
        System.out.println("Loading config...");
        prop.load(input);
        interval = prop.getProperty("interval");
        path = prop.getProperty("path");
        return new String[]{interval, path};
    }
}
