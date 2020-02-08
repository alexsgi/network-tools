package network.execution;

public class ParallelTask {

    public static void runParallel(Runnable runnable, CommandCallback callback) {
        Thread thread = new Thread(() -> {
            try {
                runnable.run();
            } catch (Exception e) {
                callback.onError(e);
            }
        });
        thread.start();
    }

}
