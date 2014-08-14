package com.intrbiz.express.tests;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

import com.intrbiz.express.ExpressException;
import com.intrbiz.express.action.ActionHandler;
import com.intrbiz.express.action.MethodActionHandler;
import com.intrbiz.express.operator.ActionOperator;
import com.intrbiz.express.operator.Operator;
import com.intrbiz.express.util.ELUtil;
import com.intrbiz.metadata.Action;
import com.intrbiz.metadata.ArgName;

public class TestActions
{   
    @Test
    public void findActionMethods() throws ExpressException
    {
        ThingWithActions thing = new ThingWithActions();
        for (ActionHandler ah : MethodActionHandler.findActionHandlers(thing))
        {
            System.out.println(ah.toString());
        }
    }

    @Test
    public void noArgAction() throws ExpressException
    {
        Operator o;
        o = ELUtil.parseEL("#{@test()}", null);
        //
        System.out.println(o.toString());
        //
        assertThat(o, is(instanceOf(ActionOperator.class)));
    }
    
    @Test
    public void simpleArgsAction() throws ExpressException
    {
        Operator o;
        o = ELUtil.parseEL("#{@test('abc', 'def')}", null);
        //
        System.out.println(o.toString());
        //
        assertThat(o, is(instanceOf(ActionOperator.class)));
    }
    
    @Test
    public void namedArgsAction() throws ExpressException
    {
        Operator o;
        o = ELUtil.parseEL("#{@test(value = 'test', options = list('a', 'b', 'c'))}", null);
        //
        System.out.println(o.toString());
        //
        assertThat(o, is(instanceOf(ActionOperator.class)));
    }
    
    public static class ThingWithActions
    {
        @Action("saySomething")
        public void saySomething(@ArgName("message") String message)
        {
            System.out.println(message);
        }
    }

}
