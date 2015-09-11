package ga.segal.client;

import java.io.IOException;


/**
 * Created by Sasha on 9/4/2015.
 */
public class Client {
    public static boolean running = true;

    public static void main(String[] args) {

        //Runtime.getRuntime().addShutdownHook(new ShutdownHook());

        Config cf = new Config();
        try {
            cf.GetConfig();
        } catch (IOException ex){
            System.out.printf("Unable to find config, stack trace: %s%n", ex.getMessage());
            System.exit(1);
        }
        int interval = cf.GetInterval() * 60;
        String path = cf.GetPath();
        System.out.printf("Running on: %s at interval %d seconds%n", path,interval);

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

}
