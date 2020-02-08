package network;

import network.execution.CommandCallback;
import network.execution.ParallelTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class NetworkTools {

    private static final String osName = System.getProperty("os.name");

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

    public static void tracerout(String url, CommandCallback callback) {
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

    public static void getIpLocation(String ip, CommandCallback callback) {
        ParallelTask.runParallel(() -> {
            try {
                String url = String.format("http://ip-api.com/json/%s?fields=27518454", ip);
                String content = formatData((new URL(url).openConnection().getInputStream()));
                System.err.println(content);
                callback.onFinish(content);
            } catch (Exception e) {
                callback.onError(e);
            }
        }, callback);
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