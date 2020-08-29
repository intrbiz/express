package com.intrbiz.express.script;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.security.AccessControlException;
import java.util.Arrays;
import java.util.HashSet;

import org.junit.Test;

import com.intrbiz.express.DefaultScriptContext;
import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;

public class TestScriptSecurity
{
    @Test
    public void testJavaSecuirtyManager()
    {
        assertTrue("Java Security Manager is enabled", System.getSecurityManager() != null);
    }
    
    @Test
    public void testCheckAllowJavaClasses()
    {
        DefaultScriptContext ctx = new DefaultScriptContext(new HashSet<String>(Arrays.asList("java.lang.System", "java.util.*"))::contains);
        assertThat(ctx.checkJavaAccess("java.lang.System"), is(equalTo(true)));
        assertThat(ctx.checkJavaAccess("java.lang.Thread"), is(equalTo(false)));
        assertThat(ctx.checkJavaAccess("java.util.LinkedList"), is(equalTo(true)));
        assertThat(ctx.checkJavaAccess("java.util.HashMap"), is(equalTo(true)));
        assertThat(ctx.checkJavaAccess("java.net.ByteBuffer"), is(equalTo(false)));
    }
    
    @Test
    public void testAllowAllClasses()
    {
        DefaultScriptContext ctx = new DefaultScriptContext((cn) -> "*".equals(cn));
        assertThat(ctx.checkJavaAccess("java.lang.System"), is(equalTo(true)));
        assertThat(ctx.checkJavaAccess("java.lang.Thread"), is(equalTo(true)));
        assertThat(ctx.checkJavaAccess("java.util.LinkedList"), is(equalTo(true)));
        assertThat(ctx.checkJavaAccess("java.util.HashMap"), is(equalTo(true)));
        assertThat(ctx.checkJavaAccess("java.net.ByteBuffer"), is(equalTo(true)));
    }

    @Test
    public void testPreventExit()
    {
        System.class.getMethods()[0].setAccessible(true);
        //
        ExpressScriptEngineFactory factory = new RestrictedExpressScriptEngineFactory();
        try
        {
            ExpressScriptEngine script = factory.parseUnwrapped(
                "java('java.lang.System').exit(1);"
            );
            //
            ExpressContext context = new DefaultScriptContext(script.getExpressExtensionRegistry()) {
                @Override
                public boolean checkJavaAccess(String className)
                {
                    return true;
                }
            };
            //
            script.execute(context, null);
            fail("Exit didn't happen, yet a AccessControlException was not raised");
        }
        catch (Exception e)
        {
            assertTrue("Caught access exception when trying to call exit()", e.getCause() instanceof AccessControlException);
        }
    }
    
    
    @Test
    public void testIterationLimit()
    {
        System.class.getMethods()[0].setAccessible(true);
        //
        ExpressScriptEngineFactory factory = new RestrictedExpressScriptEngineFactory();
        try
        {
            ExpressScriptEngine script = factory.parseUnwrapped(
                "for (i = 0; i < 100000; i++)" +
                "{" +
                "}"
            );
            //
            ExpressContext context = script.createContext();
            //
            script.execute(context, null);
            fail("Script was not aborted due to iteration limit");
        }
        catch (Exception e)
        {
            assertTrue("Script was aborted due to iteration limit", e instanceof ExpressException);
        }
    }
    
    @Test
    public void testOpLimit()
    {
        System.class.getMethods()[0].setAccessible(true);
        //
        ExpressScriptEngineFactory factory = new RestrictedExpressScriptEngineFactory();
        try
        {
            ExpressScriptEngine script = factory.parseUnwrapped(
                "function test()" +
                "{" +
                "  test();" +
                "}"
            );
            //
            ExpressContext context = script.createContext();
            //
            script.execute(context, null);
            fail("Script was not aborted due to operation limit");
        }
        catch (Exception e)
        {
            assertTrue("Script was aborted due to operation limit", e instanceof ExpressException);
        }
    }
}
