package com.intrbiz.express.tests;

import java.util.Arrays;

import com.intrbiz.express.DefaultContext;
import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.value.ValueExpression;
import com.intrbiz.express.value.ValueScript;

public class TestScript
{
    public static void main(String[] args)
    {
        ExpressContext context = new DefaultContext();
        context.setEntity("l", Arrays.asList("A", "B", "C"), null);
        //
        ValueExpression ve = new ValueExpression(context, "One #{1 + 1} Three #{ 'Four' }");
        System.out.println(ve);
        System.out.println(ve.get(context, null));
        //
        System.out.println();
        System.out.println();
        System.out.println();
        //
        ValueScript vs = new ValueScript(context, "Test #{ 1 } test <# if ( l != null ) { print('abc'); #> testing yay <# } #> Test <# for (x in l) { #> Test #{ x } <# } #>");
        System.out.println(vs);
        System.out.println(vs.execute(context, null));
    }
}
