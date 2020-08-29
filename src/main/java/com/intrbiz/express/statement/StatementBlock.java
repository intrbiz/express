package com.intrbiz.express.statement;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;

public class StatementBlock extends Statement
{
    private List<Statement> statements = new LinkedList<Statement>();
    
    private boolean tagged;

    public StatementBlock()
    {
        super();
    }
    
    public StatementBlock(boolean tagged)
    {
        super();
        this.tagged = tagged;
    }

    public StatementBlock(List<Statement> statements)
    {
        this.statements = statements;
    }
    
    public StatementBlock(Statement... statements)
    {
        this(Arrays.asList(statements));
    }

    @Override
    public void execute(ExpressContext ctx, Object source) throws ExpressException
    {
        try
        {
            ctx.enterFrame(false);
            for (Statement stmt : this.getStatements())
            {
                stmt.execute(ctx, source);
                // check the context state
                if (ctx.getFrame().isHalt()) return;
            }
        }
        finally
        {
            ctx.exitFrame();
        }
    }

    public List<Statement> getStatements()
    {
        return statements;
    }

    public void setStatements(List<Statement> statements)
    {
        this.statements = statements;
    }

    public boolean isTagged()
    {
        return tagged;
    }

    public void setTagged(boolean tagged)
    {
        this.tagged = tagged;
    }

    public String toString(String p)
    {
        StringBuilder sb = new StringBuilder();
        if (this.tagged) sb.append("<# ");
        for (Statement s : this.getStatements())
        {
            sb.append(s.toString(p));
        }
        if (this.tagged) sb.append(" #>");
        return sb.toString();
    }
}
