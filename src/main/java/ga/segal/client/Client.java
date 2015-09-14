package ga.segal.client;

import java.lang.Exception;
import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.File;
import java.util.Properties;

public class Client {

    private static long position;
    private static String cache_folder;
    private static volatile boolean keepRunning = true;
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

        String os = System.getProperty("os.name");
        if (os.equals("Windows 7")) {
            path = "short";
        }

        System.out.printf("Running with interval %s seconds on file %s%n", interval, path);

        cache_folder = ReadLog.MakeCacheFolder();
        String position_path = cache_folder + File.separator + "position";

        ShutdownHook shook = new ShutdownHook();
        shook.attachShutDownHook();

        try {
            while (keepRunning) {
                ReadPosition(position_path);
                System.out.printf("Reading from position %d%n", position);
                long newpos = ReadLog.FetchLines(path,position,cache_folder);
                if (newpos != position ) {
                   WritePosition(position_path,newpos);
                }
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

    static void ReadPosition (String Filename) {

        try {
            BufferedReader br = new BufferedReader(new FileReader(Filename));
            position = Long.parseLong(br.readLine());
        } catch (Exception e) {
            File pf = new File(cache_folder + File.separator + "position");
            boolean exists = pf.exists();
            if (!exists) {
                position = 0;
            } else {
                System.out.printf("Unable to read position from cache folder %s%n", e.getMessage());
                System.exit(1);
            }
        }
    }

    static void WritePosition(String Filename, Long NewPos) {

        try {
            PrintWriter pr = new PrintWriter(Filename,"UTF-8");
            pr.println(Long.toString(NewPos));
            pr.close();
        } catch (Exception e) {
            System.out.printf("Unable to write position, check your cache folder settings. %s", e.getMessage());
            System.exit(1);
        }
        position = NewPos;
    }
}