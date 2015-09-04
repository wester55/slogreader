package ga.segal.client;

import java.lang.Thread;
/**
 * Created by Sasha on 9/4/2015.
 */
public class ShutdownHook extends Thread{
    @Override
    public void run() {
        System.out.println("Exiting.....");

// stop running threads
// store data to DB
// close connection to DB
// disconnect...
// release other resources...

        Client.running = false;
    }
}
