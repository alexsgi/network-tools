package network.tor;

public class ExitNode {

    private String name, ip;

    public ExitNode(String name, String ip) {
        this.name = name;
        this.ip = ip;
    }

    public String getName() {
        return name;
    }

    public String getIp() {
        return ip;
    }

    @Override
    public String toString() {
        return name + "@" + ip;
    }
}
