package ga.segal.client;


import java.io.*;
import java.lang.Exception;
import java.lang.String;
import java.util.ArrayList;

public class ReadLog {
    static String MakeCacheFolder () {

        String tmp_folder = System.getProperty("java.io.tmpdir");
        String cfolder = tmp_folder + File.separator + "cache";
        String tfile = cfolder + File.separator + ".testfile";

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

    static long FetchLines(String Filename, long StartPosition, String CacheFolder) {

        ArrayList<String> lines = new ArrayList<>();
        long startPos = StartPosition;
        try {
            RandomAccessFile fl = new RandomAccessFile( Filename, "r" );
            long fileLength = fl.length();
            if( fileLength < startPos )
            {
                // Log file must have been rotated or deleted;
                // reopen the file and reset the file pointer
                fl = new RandomAccessFile( Filename, "r" );
                startPos = 0;
            }

            if( fileLength > startPos )
            {
                // There is data to read
                fl.seek(startPos);
                String line = fl.readLine();
                while( line != null )
                {
                    lines.add(line);
                    line = fl.readLine();
                }
                startPos = fl.getFilePointer();
                String cachefile = CacheFolder + File.separator + "content";
                try {
                    FileOutputStream fos = new FileOutputStream(cachefile);
                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                    oos.writeObject(lines);
                    oos.close();
                } catch(Exception e) {
                    System.out.printf("Unable to write to temp file %s. %s", cachefile, e.getMessage());
                    System.exit(1);
                }
            }

        } catch (Exception e){
            System.out.printf("Some error occured reading log file, exiting. %s", e.getMessage());
            System.exit(1);
        }
        return startPos;
    }
}
