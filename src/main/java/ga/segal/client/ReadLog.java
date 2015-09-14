package ga.segal.client;


import java.io.PrintWriter;
import java.lang.Exception;
import java.io.File;

public class ReadLog {
    static String MakeCacheFolder () {

        String tmp_folder = System.getProperty("java.io.tmpdir");
        String cfolder = tmp_folder + "/cache";
        String tfile = cfolder + "/.testfile";

        System.out.println("Checking cache folder " + cfolder + " is writable...");

        File dir = new File(cfolder);
        boolean exists = dir.exists();
        if (!exists){
            System.out.println("Cache folder not exists, creating new one in " + tmp_folder);
            try {
                new File(cfolder).mkdir();

            } catch (Exception e) {
                System.out.println("Unable create cache folder in " + tmp_folder);
                System.exit(1);
            }
        }

        try {
            PrintWriter writer = new PrintWriter(tfile, "UTF-8");
            writer.println("The first line");
            writer.close();
        } catch (Exception e) {
            System.out.printf("Unable to write in cache folder %s%n", e.getMessage());
            System.exit(1);
        }
        try {
            File file = new File(tfile);
            file.delete();
        } catch (Exception e) {
            System.out.printf("Unable to remove files in cache folder %s%n", e.getMessage());
            System.exit(1);
        }

        System.out.println("All looks ok with cache folder, if you want re-initiate delete cache folder " + cfolder);
        return cfolder;
    }
}
