package top.neospot.cloud.stats;

import com.eclipsesource.v8.V8;

/**
 * By neo.chen{neocxf@gmail.com} on 2018/9/21.
 */
public class InvokeJavaScript {
    public static void main(String[] args) {
        V8 runtime = V8.createV8Runtime();
        int result = runtime.executeIntegerScript(""
                + "var hello = 'hello, ';\n"
                + "var world = 'world!';\n"
                + "hello.concat(world).length;\n");
        System.out.println(result);
        runtime.release();
    }
}
