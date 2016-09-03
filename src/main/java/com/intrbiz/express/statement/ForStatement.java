package com.intrbiz.express.statement;

import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;
import com.intrbiz.express.operator.Operator;

public class ForStatement extends ControlStatement
{
    private Operator assignment;

    private Operator test;

    private Operator increment;

    private StatementBlock block;

    public ForStatement(Operator assignment, Operator test, Operator increment, StatementBlock block)
    {
        super("for");
        this.setAssignment(assignment);
        this.setTest(test);
        this.setIncrement(increment);
        this.setBlock(block);
    }

    @Override
    public void execute(ExpressContext ctx, Object source) throws ExpressException
    {
        // execute the assignment
        this.getAssignment().get(ctx, source);
        while ((Boolean) this.getTest().get(ctx, source))
        {
            this.getBlock().execute(ctx, source);
            // check the context state
            if (ctx.getFrame().isHalt())
            {
                if (ctx.getFrame().isBreak())
                {
                    ctx.getFrame().doResetBreak();
                    break;
                }
                else if (ctx.getFrame().isReturn())
                    return;
            }
            this.getIncrement().get(ctx, source);
        }
    }

    public Operator getAssignment()
    {
        return assignment;
    }

    public void setAssignment(Operator assignment)
    {
        this.assignment = assignment;
    }

    public Operator getTest()
    {
        return test;
    }

    public void setTest(Operator test)
    {
        this.test = test;
    }

    public Operator getIncrement()
    {
        return increment;
    }

    public void setIncrement(Operator increment)
    {
        this.increment = increment;
    }

    public StatementBlock getBlock()
    {
        return block;
    }

    public void setBlock(StatementBlock block)
    {
        this.block = block;
    }

    public String toString(String p)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(p).append("for (").append(this.getAssignment().toString()).append("; ").append(this.getTest().toString()).append("; ").append(this.getIncrement().toString()).append(") {\r\n");
        sb.append(this.block.toString(p + "  "));
        sb.append(p).append("}\r\n");
        return sb.toString();
    }
}
