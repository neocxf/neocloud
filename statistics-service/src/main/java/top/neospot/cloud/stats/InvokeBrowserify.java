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
            String unTranspiledJsx = Utilities.readFromResourcesAsString("HelloWorldEs6Syntax.js");
            bindings.put("input", unTranspiledJsx);
            String transpileJavaScript = (String) nashorn.eval("Babel.transform(input, { presets: ['es2015'] }).code", bindings);
            String s = (String) nashorn.eval(transpileJavaScript);
            System.out.println(s);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
