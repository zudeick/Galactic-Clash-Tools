package de.zudeick.galacticclash;

import org.apache.commons.codec.binary.Hex;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Properties;

public class ImportTCPDump {

    public static void main(String[] args) throws Exception {
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

        if (new File(System.getProperty("gc.import.file")).exists()) {
            BufferedReader bufferedReader = null;
            bufferedReader = new BufferedReader(new FileReader(System.getProperty("gc.import.file")));
            ArrayList<JSONObject> objects = ImportTCPDump.dump_to_json(bufferedReader);
            bufferedReader.close();
            ImportTCPDump.dump_to_db(objects);
        } else {
            System.out.println("Can't find "+ System.getProperty("gc.import.file"));
        }
    }

    public static void dump_to_db(ArrayList<JSONObject> arrayList) throws Exception {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://"+System.getProperty("jdbc.host")+"/"+System.getProperty("jdbc.database"), System.getProperty("jdbc.user"), System.getProperty("jdbc.pass"));
        preparedStatement = connection.prepareStatement("INSERT INTO gc_import set server_ip = ?, data = ?");

        int i = 0;
        for (JSONObject object:arrayList) {
            i++;
            if(i%100==0){
                System.out.println(i + " of " + arrayList.size());
            }
            preparedStatement.clearParameters();
            preparedStatement.setString(1, object.get("server_ip").toString());
            object.remove("server_ip");
            preparedStatement.setString(2, object.toJSONString());
            preparedStatement.executeUpdate();
        }
    }

    public static ArrayList<JSONObject> dump_to_json(BufferedReader bufferedReader) throws Exception {
        ArrayList<JSONObject> objects = new ArrayList<JSONObject>();
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            if (line.contains("HTTP/1.1 200 OK")){

                String ip="";
                if(line.contains("192.155.80.179")){
                    ip="192.155.80.179";
                }
                if(line.contains("173.255.250.86")){
                    ip="173.255.250.86";
                }

                while ((line = bufferedReader.readLine()) != null) {
                    if(line.contains("192.155.80.179")){
                        ip="192.155.80.179";
                    }
                    if(line.contains("173.255.250.86")){
                        ip="173.255.250.86";
                    }

                    if (line.startsWith("Uncompressed entity body")) {
                        StringBuffer response = new StringBuffer();
                        line = line.replace("Uncompressed entity body (", "");
                        line = line.replace("bytes):", "");
                        int byte_length = Integer.parseInt(line.trim());
                        while ((line = bufferedReader.readLine()) != null) {

                            int identifier_int = Integer.parseInt(line.substring(0,5).trim(), 16);
                            boolean _break =(byte_length-16) <= identifier_int && identifier_int <= byte_length;

                            line = line.substring(5,6+48).replaceAll(" ", "").trim();
                            line = new String(Hex.decodeHex(line.toCharArray()), "UTF-8");
                            response.append(line);
                            if(_break){
                                break;
                            }
                        }
                        try {
                            JSONObject jsonObject = null;
                            jsonObject = (JSONObject) new JSONParser().parse(response.toString());
                            jsonObject.put("server_ip", ip.toString());
                            objects.add(jsonObject);
                        } catch (Exception exception) {
                            // exception.printStackTrace();
                            // System.out.println(response);
                        }
                    }
                }
            }
        }
        return objects;
    }
}
