package top.neospot.cloud.stats;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.SimpleBindings;

/**
 * By neo.chen{neocxf@gmail.com} on 2018/9/21.
 */
public class InvokeBrowserify {
    public static void main(String[] args) {
        ScriptEngine nashorn = new ScriptEngineManager().getEngineByName("nashorn");
        try {
            SimpleBindings bindings = new SimpleBindings();
            nashorn.eval(Utilities.readFromResourcesAsInputStream("babel-core-6.7.7-browser.js"), bindings);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
