package com.intrbiz.express.tests;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.lang.reflect.InvocationHandler;
import java.util.Arrays;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.intrbiz.express.DefaultPermissiveContext;
import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.tests.model.SomeBean;
import com.intrbiz.express.util.ELUtil;

public class TestLambda
{
    private ExpressContext context;
    
    @Before()
    public void setupContext()
    {
        this.context = new DefaultPermissiveContext();
        this.context.setEntity("bean", new SomeBean(), null);
        //
        BasicConfigurator.configure();
        Logger.getRootLogger().setLevel(Level.DEBUG);
    }
    
    @Test()
    public void testLambdaCreatesInvocationHandler() throws Throwable
    {
        Object o = ELUtil.parseEL("#{ (a, b, c) -> 'Test' }", this.context).get(context, null);
        System.out.println(o);
        assertThat(o, is(instanceOf(InvocationHandler.class)));
        Object result = ((InvocationHandler) o).invoke(null, null, new Object[] { "a", "b", "c" });
        assertThat(result, is(equalTo("Test")));
    }
    
    @Test()
    public void testLambdaCreatesInvocationHandler2() throws Throwable
    {
        Object o = ELUtil.parseEL("#{ (a) -> { ;; return 'Test'; } }", this.context).get(context, null);
        System.out.println(o);
        assertThat(o, is(instanceOf(InvocationHandler.class)));
        Object result = ((InvocationHandler) o).invoke(null, null, new Object[] { "a" });
        assertThat(result, is(equalTo("Test")));
    }
    
    @Test()
    public void testFunctionReference() throws Throwable
    {
        Object o = ELUtil.parseEL("#{ ::list }", this.context).get(context, null);
        System.out.println(o);
        assertThat(o, is(instanceOf(InvocationHandler.class)));
        Object result = ((InvocationHandler) o).invoke(null, null, new Object[] { "a", "b", "c" });
        assertThat(result, is(equalTo(Arrays.asList("a", "b", "c"))));
    }
    
    @Test()
    public void testStreams() throws Throwable
    {
        Object o = ELUtil.parseEL("#{ list('test 1', 'test 2').stream().map((s) -> s + ' test').map(::urlencode).collect(Collectors.joining('&')) }", this.context).get(context, null);
        System.out.println(o);
        assertThat(o, is(equalTo("test+1+test&test+2+test")));
    }
}
