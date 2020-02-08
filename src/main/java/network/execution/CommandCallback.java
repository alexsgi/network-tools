package network.execution;

public interface CommandCallback {

    void onFinish(String output);

    void onError(Exception e);

}
