package ga.segal.client;


import java.io.PrintWriter;
import java.lang.Exception;
import java.io.File;

public class ReadLog {
    public void CheckCacheFolder (String cache_folder) {

        System.out.println("Checking cache folder is writable...");
        String testfile = cache_folder + "\\.testfile";
        try {
            PrintWriter writer = new PrintWriter(testfile, "UTF-8");
            writer.println("The first line");
            writer.close();
        } catch (Exception e) {
            System.out.printf("Unable to write in folder %s%n", e.getMessage());
            System.exit(1);
        }
        try {
            File file = new File(testfile);
            file.delete();
        } catch (Exception e) {
            System.out.printf("Unable to remove in cache folder %s%n", e.getMessage());
            System.exit(1);
        }

    }
}
