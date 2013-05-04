package com.intrbiz.express.value;

import java.io.Serializable;
import java.io.StringReader;

import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;
import com.intrbiz.express.parser.ELParser;
import com.intrbiz.express.parser.ParseException;
import com.intrbiz.express.parser.TokenMgrError;
import com.intrbiz.express.statement.Statement;

public class ValueScript implements Serializable
{
    private static final long serialVersionUID = 1L;

    private Statement statement;

    public ValueScript()
    {
        super();
    }

    public ValueScript(ExpressContext context, String script) throws ExpressException
    {
        this();
        this.parse(context, script);
    }

    public ScriptReturn executeWithState(ExpressContext context, Object source) throws ExpressException
    {
        // enter the root frame
        context.enterFrame(true);
        try
        {
            // execute the script
            statement.execute(context, source);
            // process the results
            return new ScriptReturn(context.getFrame().isReturn(), context.getFrame().getReturnValue());
        }
        finally
        {
            context.exitFrame();
        }
    }

    public Object execute(ExpressContext context, Object source) throws ExpressException
    {
        // enter the root frame
        context.enterFrame(true);
        try
        {
            // execute the script
            statement.execute(context, source);
            // process the results
            if (context.getFrame().isReturn()) return context.getFrame().getReturnValue();
        }
        finally
        {
            context.exitFrame();
        }
        return null;
    }

    public void parse(ExpressContext context, String script) throws ExpressException
    {
        ELParser parser = new ELParser(new StringReader(script));
        try
        {
            this.statement = parser.readStatements(context);
        }
        catch (TokenMgrError e)
        {
            throw new ExpressException("Error parsing script: [" + script + "]", e);
        }
        catch (ExpressException ee)
        {
            throw new ExpressException("Error parsing script: [" + script + "]", ee);
        }
        catch (ParseException pe)
        {
            throw new ExpressException("Error parsing script: [" + script + "]", pe);
        }
    }

    public Statement getStatement()
    {
        return this.statement;
    }

    @Override
    public String toString()
    {
        return String.valueOf(this.statement);
    }
}
