package de.zudeick.galacticclash;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

public class ADBClient {
    public static String adb = null;
    public static ADBClient adbClient = null;
    private HashMap<ADBPoint, Point> pointHash = null;
    private HashMap<Integer, ADBPoint> numHash = null;

    private ADBClient() throws Exception {
        Properties properties = new Properties(System.getProperties());
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream("conf/Galactic-Clash-Tools.properties");
        }
        catch (Exception exception) {
            System.out.println("properties not found");
            inputStream = ClassLoader.getSystemResourceAsStream("Galactic-Clash-Tools.properties");
        }
        finally {
            properties.load(inputStream);
        }
        System.setProperties(properties);
        InetAddress.getAllByName(System.getProperty("http.proxyHost"));
    }

    public static ADBClient getInstance() throws Exception {
        if (ADBClient.adbClient == null) {
            ADBClient.adbClient = new ADBClient();
        }
        return ADBClient.adbClient;
    }

    public static void main(String[] args) throws Exception {
        ADBClient.getInstance();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        Date date = simpleDateFormat.parse(System.getProperty("gc.scanner.start.time", simpleDateFormat.format(new Date(new Date().getTime()+1000*60))));

        while (date.getTime() > new Date().getTime()){
            System.out.println("Start time not reached, waiting until:" + simpleDateFormat.format(new Date()));
            Thread.sleep(1000*60*5); //5 minuten
        }

        if (new File(System.getProperty("adb.home")+"\\" + System.getProperty("adb.exe")).exists()) {
            ADBClient.adb = System.getProperty("adb.home")+"\\" + System.getProperty("adb.exe");
            ADBAction.adb_kill();
            Thread.sleep(2000);
            ADBAction.adb_tcpip();
            Thread.sleep(2000);
            ADBAction.adb_connect(System.getProperty("adb.connect.ip"));
            Thread.sleep(2000);
            ADBAction.devices();
        } else {
            System.out.println("Can't find " + System.getProperty("adb.home")+"\\" + System.getProperty("adb.exe"));
        }

        for (String activity : System.getProperty("gc.clients").split(",")) {
            ADBClient.getInstance().initPointHash(activity);

            ADBAction.stop(activity);
            Thread.sleep(Integer.valueOf(System.getProperty("gc.client.timeout.stop")));

            ADBAction.launch(activity + "/com.ansca.corona.CoronaActivity");
            Thread.sleep(Integer.valueOf(System.getProperty("gc.client.timeout.launch")));


            switch (activity) {
                case "com.next2fun.gc": {
                }
                break;
                case "com.next2fun.gc2": {
                    ADBAction.tap(ADBPoint.CLOSE_EVENT);
                    Thread.sleep(1000);
                }
                break;
            }

            ADBJob.galaxy(activity);
            Thread.sleep(1000);

            ADBAction.stop(activity);
            Thread.sleep(1000);
        }
        java.awt.Toolkit.getDefaultToolkit().beep();
    }

    public void initPointHash(String activity) throws Exception {
        this.numHash = new HashMap<Integer, ADBPoint>();
        this.numHash.put(1, ADBPoint.NUM_1);
        this.numHash.put(2, ADBPoint.NUM_2);
        this.numHash.put(3, ADBPoint.NUM_3);
        this.numHash.put(4, ADBPoint.NUM_4);
        this.numHash.put(5, ADBPoint.NUM_5);
        this.numHash.put(6, ADBPoint.NUM_6);
        this.numHash.put(7, ADBPoint.NUM_7);
        this.numHash.put(8, ADBPoint.NUM_8);
        this.numHash.put(9, ADBPoint.NUM_9);
        this.numHash.put(0, ADBPoint.NUM_0);

        this.pointHash = new HashMap<ADBPoint, Point>();
        this.pointHash.put(ADBPoint.NUM_CLOSE, new Point(415, 175));
        this.pointHash.put(ADBPoint.NUM_1, new Point(450, 255));
        this.pointHash.put(ADBPoint.NUM_2, new Point(635, 255));
        this.pointHash.put(ADBPoint.NUM_3, new Point(820, 255));
        this.pointHash.put(ADBPoint.NUM_4, new Point(450, 350));
        this.pointHash.put(ADBPoint.NUM_5, new Point(635, 350));
        this.pointHash.put(ADBPoint.NUM_6, new Point(820, 350));
        this.pointHash.put(ADBPoint.NUM_7, new Point(450, 445));
        this.pointHash.put(ADBPoint.NUM_8, new Point(635, 445));
        this.pointHash.put(ADBPoint.NUM_9, new Point(820, 445));
        this.pointHash.put(ADBPoint.NUM_0, new Point(450, 585));
        this.pointHash.put(ADBPoint.NUM_DEL, new Point(635, 585));
        this.pointHash.put(ADBPoint.NUM_OK, new Point(820, 585));

        switch (activity) {
            case "com.next2fun.gc": {
                this.pointHash.put(ADBPoint.CLOSE, new Point(125, 45));
                this.pointHash.put(ADBPoint.START_GALAXY, new Point(375, 700));
                this.pointHash.put(ADBPoint.GALAXY_SEARCH, new Point(640, 700));

                this.pointHash.put(ADBPoint.GALAXY_SEARCH_GALAXY, new Point(420, 40));
                this.pointHash.put(ADBPoint.GALAXY_SEARCH_SYSTEM, new Point(650, 40));
                this.pointHash.put(ADBPoint.GALAXY_SEARCH_SUBMIT, new Point(875, 40));

                this.pointHash.put(ADBPoint.GALAXY_NEXT, new Point(1025, 745));
                this.pointHash.put(ADBPoint.GALAXY_PREVIOUS, new Point(260, 745));
            }
            break;
            case "com.next2fun.gc2": {
                this.pointHash.put(ADBPoint.CLOSE_EVENT, new Point(265, 115));
                this.pointHash.put(ADBPoint.CLOSE, new Point(125, 45));

                this.pointHash.put(ADBPoint.START_GALAXY, new Point(320, 720));
                this.pointHash.put(ADBPoint.GALAXY_SEARCH, new Point(640, 700));

                this.pointHash.put(ADBPoint.GALAXY_SEARCH_GALAXY, new Point(420, 40));
                this.pointHash.put(ADBPoint.GALAXY_SEARCH_SYSTEM, new Point(650, 40));
                this.pointHash.put(ADBPoint.GALAXY_SEARCH_SUBMIT, new Point(875, 40));

                this.pointHash.put(ADBPoint.GALAXY_NEXT, new Point(1025, 745));
                this.pointHash.put(ADBPoint.GALAXY_PREVIOUS, new Point(260, 745));
            }
            break;
        }
    }

    public Point getPoint(ADBPoint adbPoint) {
        return this.pointHash.get(adbPoint);
    }

    public ADBPoint getNum(Integer integer) {
        return this.numHash.get(integer);
    }

}
