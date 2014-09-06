package com.intrbiz.express.operator;

import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;
import com.intrbiz.express.statement.Statement;
import com.intrbiz.express.value.ValueScript;

public class ScriptFunction extends Function
{
    private Statement script;

    public ScriptFunction(String name, ValueScript script)
    {
        super(name);
        this.script = script.getStatement();
    }
    
    public ScriptFunction(String name, Statement script)
    {
        super(name);
        this.script = script;
    }

    public Statement getScript()
    {
        return script;
    }

    public void setScript(Statement script)
    {
        this.script = script;
    }
    
    protected void bindParameters(ExpressContext sctx)
    {
        // bind parameters
        int i = 0;
        for (Operator o : this.getParameters())
        {
            sctx.setEntity("arg" + i, o, null);
            i++;
        }        
    }
    
    protected void bindValue(ExpressContext sctx, Object value)
    {
        // bind value
        sctx.setEntity("value", value, null);
    }

    @Override
    public Object get(ExpressContext context, Object source) throws ExpressException
    {
        // enter the root frame
        context.enterFrame(true);
        try
        {
            this.bindParameters(context);
            // execute the script
            this.script.execute(context, source);
            // process the results
            if (context.getFrame().isReturn()) return context.getFrame().getReturnValue();
        }
        finally
        {
            context.exitFrame();
        }
        return null;
    }

    @Override
    public void set(ExpressContext context, Object value, Object source) throws ExpressException
    {
        // enter the root frame
        context.enterFrame(true);
        try
        {
            this.bindParameters(context);
            this.bindValue(context, value);
            // execute the script
            this.script.execute(context, source);
        }
        finally
        {
            context.exitFrame();
        }
    }
    
    @Override
    public boolean isIdempotent()
    {
        return false;
    }
}
