package com.intrbiz.express.script;

import java.io.InputStream;
import java.io.Reader;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;

import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;
import com.intrbiz.express.statement.Statement;
import com.intrbiz.express.value.ScriptReturn;
import com.intrbiz.express.value.ValueScript;

public class RestrictedValueScript extends ValueScript
{
    private static final long serialVersionUID = 1L;

    private final AccessControlContext accessContext;

    public RestrictedValueScript(ExpressContext context, InputStream input, AccessControlContext accessContext) throws ExpressException
    {
        super(context, input);
        this.accessContext = accessContext;
    }

    public RestrictedValueScript(ExpressContext context, Reader input, AccessControlContext accessContext) throws ExpressException
    {
        super(context, input);
        this.accessContext = accessContext;
    }

    public RestrictedValueScript(ExpressContext context, String script, AccessControlContext accessContext) throws ExpressException
    {
        super(context, script);
        this.accessContext = accessContext;
    }

    @Override
    public ScriptReturn executeWithState(ExpressContext context, Object source) throws ExpressException
    {
        try
        {
            return AccessController.doPrivileged(new PrivilegedExceptionAction<ScriptReturn>() {
                @Override
                public ScriptReturn run() throws Exception
                {
                    return RestrictedValueScript.super.executeWithState(context, source);
                }
            }, this.accessContext);
        }
        catch (PrivilegedActionException e)
        {
            throw e.getCause() instanceof ExpressException ? (ExpressException) e.getCause() : new ExpressException(e);
        }
    }

    @Override
    public Object execute(ExpressContext context, Object source) throws ExpressException
    {
        try
        {
            return AccessController.doPrivileged(new PrivilegedExceptionAction<Object>() {
                @Override
                public Object run() throws Exception
                {
                    return RestrictedValueScript.super.execute(context, source);
                }
            }, this.accessContext);
        }
        catch (PrivilegedActionException e)
        {
            throw e.getCause() instanceof ExpressException ? (ExpressException) e.getCause() : new ExpressException(e);
        }
    }

    @Override
    public Statement getStatement()
    {
        return null;
    }

    @Override
    public String toString()
    {
        return super.toString();
    }
}
