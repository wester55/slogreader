package ga.segal.client;


public class ShutdownHook {
    public void attachShutDownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.out.println("exit");
            }
        });

    }
}
