package top.neospot.cloud.stats;

import com.google.gson.Gson;
import jdk.nashorn.api.scripting.NashornScriptEngineFactory;
import lombok.Data;
import org.junit.Before;
import org.junit.Test;
import org.webjars.WebJarAssetLocator;

import javax.script.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * By neo.chen{neocxf@gmail.com} on 2018/9/25.
 */
public class NashornFactoryTest {

    private OrderDetails order;
    Gson gson = new Gson();


    @Before
    public void init() {
        ArrayList<Map<String, String>> items = new ArrayList<>();

        HashMap<String, String> item = new HashMap<>();
        item.put("name", "Nokia 1100");
        item.put("quantity", "10");
        items.add(item);

        item = new HashMap<>();
        item.put("name", "iPhone 7");
        item.put("quantity", "2");
        items.add(item);


        order = new OrderDetails();
        order.user = "Tim";
        order.orderId = 12314;
        order.items = items;
    }

    @Test
    public void testLoadHandlebarsLibraries() throws Exception {
        ScriptEngine scriptEngine = NashornFactory.globalScriptEngine;

        Invocable invocable = (Invocable) scriptEngine;


        // calling the JavaScript function to generate the template
        invocable.invokeFunction("generateTemplate", gson.toJson(order));

    }

    @Test
    public void readLib() {
        WebJarAssetLocator locator = new WebJarAssetLocator();
        locator.getWebJars().forEach((k, v) -> {
            System.out.println(k + "---" + v);
        });
        Set<String> fullPathsOfAssets = locator.listAssets("/handlebars/4.0.11-1");
        fullPathsOfAssets.forEach(System.out::println);
    }

    @Test
    public void invokeQueryStringFunction() throws Exception {

        ScriptEngine scriptEngine = NashornFactory.globalScriptEngine;


        Invocable invocable = (Invocable) scriptEngine;

        System.out.println(order);

        Object util = scriptEngine.get("util");

        invocable.invokeMethod(util, "echo", gson.toJson(order));
    }

    @Test
    public void testEs6() throws Exception {
        NashornScriptEngineFactory factory = new NashornScriptEngineFactory();
        ScriptEngine engine = factory.getScriptEngine("--language=es6");
        ScriptContext newContext = new SimpleScriptContext();
        newContext.setBindings(engine.createBindings(), ScriptContext.ENGINE_SCOPE);
        Bindings engineScope = newContext.getBindings(ScriptContext.ENGINE_SCOPE);
        engineScope.put("x", "scope world");
        engine.put("x", "global world");

        engine.eval("print(x)", newContext);

        engine.eval("const a = 100; const echo = function(){print(x)}; echo();");

        engine.eval("var obj = new Object()");
        engine.eval("obj.run = function(){print('obj.run() method called')}");

        Object obj = engine.get("obj");

        Invocable inv = (Invocable) engine;

        Runnable r = inv.getInterface(obj, Runnable.class);

        Thread th = new Thread(r);
        th.start();
        th.join();
    }
}

@Data
class OrderDetails {
    String user;
    Integer orderId;
    ArrayList<Map<String, String>> items = new ArrayList<>();

}

