package de.zudeick.galacticclash;

import java.awt.*;
import java.io.*;
import java.lang.ProcessBuilder.Redirect;

public class ADBAction {
    public static void adb_connect(String ip) throws Exception {
        ProcessBuilder processBuilder = new ProcessBuilder(ADBClient.adb, "connect", ip);
        processBuilder.redirectError(Redirect.INHERIT);
        processBuilder.redirectOutput(Redirect.INHERIT);
        Process process = processBuilder.start();
        process.waitFor();
    }

    public static void adb_tcpip() throws Exception {
        ProcessBuilder processBuilder = new ProcessBuilder(ADBClient.adb, "tcpip");
        processBuilder.redirectError(Redirect.INHERIT);
        processBuilder.redirectOutput(Redirect.INHERIT);
        Process process = processBuilder.start();
        process.waitFor();
    }

    public static void adb_usb() throws Exception {
        ProcessBuilder processBuilder = new ProcessBuilder(ADBClient.adb, "usb");
        processBuilder.redirectError(Redirect.INHERIT);
        processBuilder.redirectOutput(Redirect.INHERIT);
        Process process = processBuilder.start();
        process.waitFor();
    }

    public static void adb_kill() throws Exception {
        ProcessBuilder processBuilder = new ProcessBuilder(ADBClient.adb, "kill-server");
        processBuilder.redirectError(Redirect.INHERIT);
        processBuilder.redirectOutput(Redirect.INHERIT);
        Process process = processBuilder.start();
        process.waitFor();
    }

    public static void devices() throws Exception {
        ProcessBuilder processBuilder = new ProcessBuilder(ADBClient.adb, "devices");
        processBuilder.redirectError(Redirect.INHERIT);
        processBuilder.redirectOutput(Redirect.INHERIT);
        Process process = processBuilder.start();
        process.waitFor();
    }

    public static Point dumpsys_dimension() throws Exception {
        Point max = null;
        ProcessBuilder processBuilder = new ProcessBuilder(ADBClient.adb, "shell", "dumpsys", "window");
        processBuilder.redirectError(Redirect.INHERIT);
        Process process = processBuilder.start();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            if (line.contains("mUnrestrictedScreen")) { // RestrictedOverscanScreen
                max = new Point(Integer.valueOf(line.trim().split(" ")[1].split("x")[0]), Integer.valueOf(line.trim().split(" ")[1].split("x")[1]));
            }
        }
        process.waitFor();
        return max;
    }

    public static int dumpsys_orientation() throws Exception {
        int orientation = -1;
        ProcessBuilder processBuilder = new ProcessBuilder(ADBClient.adb, "shell", "dumpsys", "input");
        processBuilder.redirectError(Redirect.INHERIT);
        Process process = processBuilder.start();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            if (line.contains("SurfaceOrientation")) {
                orientation = Integer.valueOf(line.trim().split(":")[1].trim());
            }
        }
        process.waitFor();
        return orientation;
    }

    public static String getprop(String property) throws Exception {
        ProcessBuilder processBuilder = new ProcessBuilder(ADBClient.adb, "shell", "getprop", property);
        processBuilder.redirectError(Redirect.INHERIT);
        Process process = processBuilder.start();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = null;
        StringBuilder value = new StringBuilder();
        while ((line = bufferedReader.readLine()) != null) {
            value.append(line);
        }
        process.waitFor();
        return value.toString().trim();
    }

    public static void launch(String activity) throws Exception {
        ProcessBuilder processBuilder = new ProcessBuilder(ADBClient.adb, "shell", "am", "start", "-n", activity);
        processBuilder.redirectError(Redirect.INHERIT);
        processBuilder.redirectOutput(Redirect.INHERIT);
        Process process = processBuilder.start();
        process.waitFor();
    }

    public static void pull(String remote_path, String locale_path) throws Exception {
        ProcessBuilder processBuilder = new ProcessBuilder(ADBClient.adb, "pull", remote_path, locale_path);
        processBuilder.redirectError(Redirect.INHERIT);
        processBuilder.redirectOutput(Redirect.INHERIT);
        Process process = processBuilder.start();
        process.waitFor();
    }

    public static void rm(String remote_path) throws Exception {
        ProcessBuilder processBuilder = new ProcessBuilder(ADBClient.adb, "shell", "rm", remote_path);
        processBuilder.redirectError(Redirect.INHERIT);
        processBuilder.redirectOutput(Redirect.INHERIT);
        Process process = processBuilder.start();
        process.waitFor();
    }

    public static void screencap(String remote_path) throws Exception {
        ProcessBuilder processBuilder = new ProcessBuilder(ADBClient.adb, "shell", "screencap", remote_path);
        processBuilder.redirectError(Redirect.INHERIT);
        processBuilder.redirectOutput(Redirect.INHERIT);
        Process process = processBuilder.start();
        process.waitFor();
    }

    public static void stop(String activity) throws Exception {
        ProcessBuilder processBuilder = new ProcessBuilder(ADBClient.adb, "shell", "am", "force-stop", activity);
        processBuilder.redirectError(Redirect.INHERIT);
        processBuilder.redirectOutput(Redirect.INHERIT);
        Process process = processBuilder.start();
        process.waitFor();
    }

    public static void swipe(ADBPoint adbPointA, ADBPoint adbPointB) throws Exception {
        ProcessBuilder processBuilder = new ProcessBuilder(ADBClient.adb, "shell", "input", "swipe",
                String.valueOf(ADBClient.getInstance().getPoint(adbPointA).x),
                String.valueOf(ADBClient.getInstance().getPoint(adbPointA).y),
                String.valueOf(ADBClient.getInstance().getPoint(adbPointB).x),
                String.valueOf(ADBClient.getInstance().getPoint(adbPointB).y)
        );
        processBuilder.redirectError(Redirect.INHERIT);
        processBuilder.redirectOutput(Redirect.INHERIT);
        Process process = processBuilder.start();
        process.waitFor();
    }

    public static void swipe(Point pointA, Point pointB) throws Exception {
        ProcessBuilder processBuilder = new ProcessBuilder(ADBClient.adb, "shell", "input", "swipe",
                String.valueOf(pointA.x),
                String.valueOf(pointA.y),
                String.valueOf(pointB.x),
                String.valueOf(pointB.y)
        );
        processBuilder.redirectError(Redirect.INHERIT);
        processBuilder.redirectOutput(Redirect.INHERIT);
        Process process = processBuilder.start();
        process.waitFor();
    }

    public static void tap(ADBPoint adbPoint) throws Exception {
        System.out.println(adbPoint);
        ProcessBuilder processBuilder = new ProcessBuilder(ADBClient.adb, "shell", "input", "tap",
                String.valueOf(ADBClient.getInstance().getPoint(adbPoint).x),
                String.valueOf(ADBClient.getInstance().getPoint(adbPoint).y));
        processBuilder.redirectError(Redirect.INHERIT);
        processBuilder.redirectOutput(Redirect.INHERIT);
        Process process = processBuilder.start();
        process.waitFor();
    }

    public static void text(String text) throws Exception {
        ProcessBuilder processBuilder = new ProcessBuilder(ADBClient.adb, "shell", "input", "text", "'" + text + "'");
        processBuilder.redirectError(Redirect.INHERIT);
        processBuilder.redirectOutput(Redirect.INHERIT);
        Process process = processBuilder.start();
        process.waitFor();
    }
}
