package top.neospot.cloud.stats;

import com.google.gson.Gson;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * By neo.chen{neocxf@gmail.com} on 2018/9/21.
 */
public class HandlebarsTemplates {
    public static void main(String[] args) {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");

        try (FileReader reader = new FileReader(new File(HandlebarsTemplates.class.getClassLoader().getResource("HandlebarsTemplates.js").toURI()))) {
            engine.eval(reader);
            Invocable invocable = (Invocable) engine;

            ArrayList<Map<String, String>> items = new ArrayList<>();
            HashMap<String, String> item = new HashMap<>();
            item.put("name", "Nokia 1100");
            item.put("quantity", "10");
            items.add(item);

            item = new HashMap<>();
            item.put("name", "iPhone 7");
            item.put("quantity", "2");
            items.add(item);

            OrderDetails order = new OrderDetails();
            order.user = "Tim";
            order.orderId = 12314;
            order.items = items;

            Gson gson = new Gson();

            // calling the JavaScript function to generate the template
            invocable.invokeFunction("generateTemplate", gson.toJson(order));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

class OrderDetails {
    String user;
    Integer orderId;
    ArrayList<Map<String, String>> items = new ArrayList<>();


}
