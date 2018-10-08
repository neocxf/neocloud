package top.neospot.cloud.stats;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * By neo.chen{neocxf@gmail.com} on 2018/9/25.
 */
public class NashornFactory {
    public static final ScriptEngine globalScriptEngine;

    static {
        globalScriptEngine = new ScriptEngineManager().getEngineByName("nashorn");
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
        return new InputStreamReader(NashornFactory.class.getClassLoader().getResourceAsStream(path));
    }


}
