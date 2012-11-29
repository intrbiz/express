package com.intrbiz.express.test;

import com.intrbiz.express.AbstractELContext;
import com.intrbiz.express.ELContext;
import com.intrbiz.express.operator.Decorator;
import com.intrbiz.express.operator.Function;
import com.intrbiz.express.value.ValueScript;

public class StatementsTest
{
    public static void main(String[] args) throws Exception
    {
        test1();
        test2();
        test3();
    }

    public static void test1() throws Exception
    {
        String script = "a = new java.util.LinkedList();" + "a.add('abc');" + "a.add('def');" + "a.add('123');" + "for(x in a) {" + "  print(x);" + "}" + "return a;";
        ELContext ctx = testContext();
        ValueScript vScript = new ValueScript(ctx, script);
        System.out.println(vScript);
        System.out.println("Executing");
        System.out.println(vScript.execute(ctx, null));
        System.out.println("Executed\r\n");
    }

    public static void test2() throws Exception
    {
        String script = "a = new java.util.TreeMap();" + "a.put('abc', '123');" + "a.put('def', '456');" + "for(x in a.keySet) {" + "  print(x + ' => ' + a.get(x));" + "}" + "return a;";
        ELContext ctx = testContext();
        ValueScript vScript = new ValueScript(ctx, script);
        System.out.println(vScript);
        System.out.println("Executing");
        System.out.println(vScript.execute(ctx, null));
        System.out.println("Executed\r\n");
    }

    public static void test3() throws Exception
    {
        String script = "a = 10;" + "for(i = 0; i < 100; i++){" + "    print(i);" + "    a = i;" + "}" + "return a;";
        ELContext ctx = testContext();
        ValueScript vScript = new ValueScript(ctx, script);
        System.out.println(vScript);
        System.out.println("Executing");
        System.out.println(vScript.execute(ctx, null));
        System.out.println("Executed\r\n");
    }

    public static ELContext testContext()
    {
        return new AbstractELContext()
        {
            @Override
            public Function getCustomFunction(String name)
            {
                return null;
            }

            @Override
            public Decorator getCustomDecorator(String name, Class<?> entityType)
            {
                return null;
            }

            @Override
            public Object getEntityInner(String name, Object source)
            {
                return null;
            }
        };
    }
}
