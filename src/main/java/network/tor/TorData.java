package network.tor;

import network.execution.CommandCallback;
import network.execution.ParallelTask;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class TorData {

    private static String torExitAddressURL = "https://check.torproject.org/exit-addresses";

    public static void checkIpIsExitNode(String ip, CommandCallback callback) {
        ParallelTask.runParallel(() -> {
            ArrayList<ExitNode> list = getAllExitNodes();
            for (ExitNode node : list) {
                if (ip != null && ip.toLowerCase().equals(node.getIp().toLowerCase())) {
                    callback.onFinish(ip);
                    return;
                }
            }
            callback.onFinish(null);

        }, callback);
    }

    public static ArrayList<ExitNode> getAllExitNodes() {
        String content = checkFromNetwork();
        ArrayList<ExitNode> list = new ArrayList<>();
        assert content != null;
        String name = null, ip = null;
        String[] x = content.split("ExitNode");
        for (String s : x) {
            String[] y = (s.trim() + "\n").split("\n");
            for (String ignored : y) {
                if (!y[0].equals(name)) {
                    name = y[0];
                    ip = y[3].split(" ")[1];
                    list.add(new ExitNode(name, ip));
                }
            }

        }
        return list;
    }

    private static String checkFromNetwork() {
        try {
            HttpsURLConnection conn = (HttpsURLConnection) new URL(torExitAddressURL).openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void setTorExitAddressURL(String newUrl) {
        torExitAddressURL = newUrl;
    }


}
