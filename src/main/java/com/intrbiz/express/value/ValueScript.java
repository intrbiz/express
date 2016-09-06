package com.intrbiz.express.value;

import java.io.InputStream;
import java.io.Reader;
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
        ELParser parser = new ELParser(new StringReader(script));
        try
        {
            this.statement = parser.readFullStatements(context);
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
    
    public ValueScript(ExpressContext context, Reader input) throws ExpressException
    {
        this();
        ELParser parser = new ELParser(input);
        try
        {
            this.statement = parser.readFullStatements(context);
        }
        catch (TokenMgrError e)
        {
            throw new ExpressException("Error parsing script", e);
        }
        catch (ExpressException ee)
        {
            throw new ExpressException("Error parsing script", ee);
        }
        catch (ParseException pe)
        {
            throw new ExpressException("Error parsing script", pe);
        }
    }
    
    public ValueScript(ExpressContext context, InputStream input) throws ExpressException
    {
        this();
        ELParser parser = new ELParser(input);
        try
        {
            this.statement = parser.readFullStatements(context);
        }
        catch (TokenMgrError e)
        {
            throw new ExpressException("Error parsing script", e);
        }
        catch (ExpressException ee)
        {
            throw new ExpressException("Error parsing script", ee);
        }
        catch (ParseException pe)
        {
            throw new ExpressException("Error parsing script", pe);
        }
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
