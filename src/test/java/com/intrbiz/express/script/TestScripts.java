package com.intrbiz.express.script;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.intrbiz.express.DefaultContext;
import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.value.ValueScript;

public class TestScripts
{
    public static void main(String[] args)
    {
        ExpressContext context = new DefaultContext();
        //
        //
        ValueScript script = new ValueScript(
                context, 
                "<#\n"+
                "                function add(a, b)\n" + 
                "                {\n" + 
                "                    return a + b;\n" + 
                "                }\n" + 
                "                \n" + 
                "                function sum()\n" + 
                "                {\n" + 
                "                    total = 0;\n" + 
                "                    for(x in arguments)\n" + 
                "                    {\n" + 
                "                        total += x;\n" + 
                "                    }\n" + 
                "                    return total;\n" + 
                "                }\n" +
                " return add(1, 2);\n" +
                "#>\n"
        );
        System.out.println(script.execute(context, null));
        System.out.println();
        //
        script = new ValueScript(
                context, 
                "<# function incr(a) { return a + 1; } l = list(1, 2, 3); return l.stream().map( (v) -> { return v + 1; } ); #>"
        );
        System.out.println(script);
        System.out.println( ((Stream) script.execute(context, null)).collect(Collectors.toList()));
        System.out.println();
        //
        script = new ValueScript(
                context, 
                "<# function incr(a) { return a + 1; } l = list(1, 2, 3); return l.stream().map((v) -> v + 1); #>"
        );
        System.out.println(script);
        System.out.println( ((Stream) script.execute(context, null)).collect(Collectors.toList()));
    }
}
