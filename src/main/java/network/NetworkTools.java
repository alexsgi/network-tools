package network;

import network.execution.CommandCallback;
import network.execution.ParallelTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class NetworkTools {

    private static final String osName = System.getProperty("os.name");

    public static void checkForHosts(String subnet, HostCallback callback) {
        checkForHosts(subnet, callback, 100, 0, 255);
    }

    public static void checkForHosts(String subnet, HostCallback callback, int timeout) {
        checkForHosts(subnet, callback, timeout, 0, 255);
    }

    public static void checkForHosts(String subnet, HostCallback callback, int timeout, int beginIndex) {
        checkForHosts(subnet, callback, timeout, beginIndex, 255);
    }

    public static void checkForHosts(String subnet, HostCallback callback, int timeout, int beginIndex, int endIndex) {
        ParallelTask.runParallel(() -> {
            try {
                int tmt = (timeout < 1) ? 1000 : timeout;
                int begin = (beginIndex < 0 || beginIndex >= 255) ? 0 : beginIndex;
                int end = (endIndex < 0 || endIndex >= 255 || endIndex < begin) ? 255 : endIndex;
                ArrayList<String> devices = new ArrayList<>();
                for (int i = begin; i < end; i++) {
                    String host = subnet + "." + i;
                    if (InetAddress.getByName(host).isReachable(tmt)) {
                        callback.onDeviceFound(host);
                        devices.add(host);
                    }
                }
                String[] dev = new String[devices.size()];
                callback.onFinish(devices.toArray(dev));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Deprecated
    public static void ping(String url, CommandCallback callback) {
        ParallelTask.runParallel(() -> {
            try {
                InputStream inputStream = (osName.toLowerCase().contains("windows")) ? NTools.pingWin(url) : NTools.pingLin(url);
                String output = formatData(inputStream);
                callback.onFinish(output);
            } catch (IOException e) {
                callback.onError(e);
            }
        }, callback);
    }

    @Deprecated
    public static void traceroute(String url, CommandCallback callback) {
        ParallelTask.runParallel(() -> {
            try {
                InputStream inputStream = (osName.toLowerCase().contains("windows")) ? NTools.tracerouteWin(url) : NTools.tracerouteLin(url);
                String output = formatData(inputStream);
                callback.onFinish(output);
            } catch (IOException e) {
                callback.onError(e);
            }
        }, callback);
    }

    public static String getIpByUrl(URL url) {
        try {
            return InetAddress.getByName(url.getHost()).getHostAddress();
        } catch (IOException ignored) {
            return null;
        }
    }

    public static String getIpByUrl(String url) throws UnknownHostException, MalformedURLException {
        URL u = new URL(url);
        return InetAddress.getByName(u.getHost()).getHostAddress();
    }

    private static String formatData(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        return sb.toString();
    }

    public interface HostCallback {

        void onDeviceFound(String host);

        void onFinish(String[] hosts);

    }

    private static final class NTools {

        private static InputStream tracerouteWin(String url) throws IOException {
            ProcessBuilder processBuilder = new ProcessBuilder("tracert", url);
            Process process = processBuilder.start();
            return process.getInputStream();
        }

        private static InputStream tracerouteLin(String url) throws IOException {
            ProcessBuilder processBuilder = new ProcessBuilder("traceroute", url);
            Process process = processBuilder.start();
            return process.getInputStream();
        }

        private static InputStream pingWin(String url) throws IOException {
            ProcessBuilder processBuilder = new ProcessBuilder("ping", url);
            Process process = processBuilder.start();
            return process.getInputStream();
        }

        private static InputStream pingLin(String url) throws IOException {
            ProcessBuilder processBuilder = new ProcessBuilder("ping", "-c " + 4, "-s " + 32, url);
            Process process = processBuilder.start();
            return process.getInputStream();
        }

    }

}