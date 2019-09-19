package top.neospot.cloud.stats;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import jdk.nashorn.api.scripting.NashornScriptEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.neospot.cloud.stats.dao.HotelCommonMapper;

import javax.script.*;
import javax.servlet.http.HttpServletRequest;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * By neo.chen{neocxf@gmail.com} on 2018/9/25.
 */
@RestController
public class LinkcenterFactory {
    public static final NashornScriptEngine globalScriptEngine;

    static {
        globalScriptEngine = (NashornScriptEngine) new ScriptEngineManager().getEngineByName("nashorn");
        try {
            globalScriptEngine.getBindings(ScriptContext.GLOBAL_SCOPE).put("bean1", new Bean1());
            globalScriptEngine.eval(read("jvm-npm.js"));
            globalScriptEngine.eval(read("nashorn-polyfill.js"));
            globalScriptEngine.eval(read("META-INF/resources/webjars/handlebars/4.0.11-1/handlebars.min.js"));
            globalScriptEngine.eval(read("lib/basic.js"));
            globalScriptEngine.eval(read("js/HandlebarsTemplates.js"));
            globalScriptEngine.eval(read("js/demo-usage.js"));

        } catch (ScriptException e) {
            throw new RuntimeException(e);
        }
    }


    private static Reader read(String path) {
        return new InputStreamReader(LinkcenterFactory.class.getClassLoader().getResourceAsStream(path));
    }

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private Gson gson;

    @Autowired
    private HotelCommonMapper hotelCommonMapper;


    @GetMapping("/booking.htm")
    public void parse() throws Exception {
        Map<String, String[]> maps = httpServletRequest.getParameterMap();

        Map<String, String> resultMap = maps.entrySet().parallelStream().map(entry -> new SimpleEntry<>(entry.getKey(), String.join(",", entry.getValue()))).collect(Collectors.toMap(SimpleEntry::getKey, SimpleEntry::getValue));

        Map<String, Object> bindingMap = Maps.newHashMap();
        bindingMap.put("_context", gson.toJson(resultMap));
        bindingMap.put("hotelCommonMapper", hotelCommonMapper);
        bindingMap.put("request", httpServletRequest);

        SimpleBindings bindings = new SimpleBindings(bindingMap);

        globalScriptEngine.setBindings(bindings, ScriptContext.GLOBAL_SCOPE);

        globalScriptEngine.eval(read("js/starwood.js"));

        Invocable invocable = (Invocable) globalScriptEngine;

        Object result = invocable.invokeFunction("starwood_main", "test_test");
        System.out.println(result);
    }
}
