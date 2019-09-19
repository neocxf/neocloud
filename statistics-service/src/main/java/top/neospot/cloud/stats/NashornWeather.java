package top.neospot.cloud.stats;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.stream.Collectors;

/**
 * By neo.chen{neocxf@gmail.com} on 2018/9/25.
 */
public class NashornWeather {
    public static void main(String[] args) throws Exception {
        String response;
        String url = "http://samples.openweathermap.org//data/2.5/forecast?id=524901&appid=b1b15e88fa797225412429c1c50c122a1";
        URLConnection connection = new URL(url).openConnection();
        String redirect = connection.getHeaderField("Location");
        if (redirect != null) {
            connection = new URL(redirect).openConnection();
        }
        try (
                InputStreamReader is = new InputStreamReader(connection.getInputStream());
                BufferedReader reader = new BufferedReader(is)) {
            response = reader.lines().collect(Collectors.joining());
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("nashorn");
            engine.put("response", response);
            System.out.println(engine.eval("JSON.stringify(JSON.parse(response))"));
            HttpServletRequest request;

        }
    }
}
