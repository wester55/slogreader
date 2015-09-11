package ga.segal.client;

import java.lang.Exception;
import java.io.FileInputStream;
import java.util.Properties;

public class Client {

    static volatile boolean keepRunning = true;
    public static void main(String[] args) {

        String parameters[] = {"", ""};
        String file_name = "src/main/config.properties";

        Client cl = new Client();
        try {
            parameters = cl.Config(file_name);
        } catch (Exception e) {
            System.out.printf("Unable to find config, stack trace: %s%n", e.getMessage());
            System.exit(1);
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