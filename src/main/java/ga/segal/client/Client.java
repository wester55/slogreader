package ga.segal.client;

import java.lang.Exception;
import java.io.FileInputStream;
import java.util.Properties;

public class Client {

    static volatile boolean keepRunning = true;
    public static void main(String[] args) {

        String parameters[] = {"", ""};
        String file_name = "config.properties";

        Client cl = new Client();
        try {
            parameters = cl.Config(file_name,0);
        } catch (Exception e) {
            try {
                parameters = cl.Config(file_name,1);
            } catch (Exception ee) {
                System.out.printf("Unable to find config, stack trace: %s%n", ee.getMessage());
                System.exit(1);
            }
            System.out.println("Found config file " + file_name);
        }

        int interval = Integer.parseInt(parameters[0]) * 60;
        String path = parameters[1];

        System.out.printf("Running with interval %s seconds on file %s%n", interval, path);

        ShutdownHook stophook = new ShutdownHook();
        stophook.attachShutDownHook();
        int i = 0;
        try {
            while (keepRunning) {
                System.out.println("count=" + i);
                i++;
                Thread.sleep(100 * interval);
            }
        } catch (InterruptedException e) {
            keepRunning = false;
        }
}

    private String[] Config(String filename, int config_in_main_dir) throws Exception {

        String interval;
        String path;
        Properties prop = new Properties();
        FileInputStream input;
        switch (config_in_main_dir) {
            case (0):
                input = new FileInputStream("config/" + filename);
                break;
            default:
                input = new FileInputStream(filename);
                break;
        }
        System.out.println("Loading config...");
        prop.load(input);
        interval = prop.getProperty("interval");
        path = prop.getProperty("path");
        return new String[]{interval, path};
    }

}