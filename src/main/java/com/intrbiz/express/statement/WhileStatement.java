package com.intrbiz.express.statement;

import com.intrbiz.express.ELContext;
import com.intrbiz.express.ELException;
import com.intrbiz.express.operator.Operator;

public class WhileStatement extends ControlStatement
{
    private Operator test;

    private StatementBlock block;

    public WhileStatement(Operator test, StatementBlock block)
    {
        super("while");
        this.setTest(test);
        this.setBlock(block);
    }

    @Override
    public void execute(ELContext ctx, Object source) throws ELException
    {
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
        }
    }

    public Operator getTest()
    {
        return test;
    }

    public void setTest(Operator test)
    {
        this.test = test;
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
        sb.append(p).append("while (").append(this.getTest().toString()).append(") {\r\n");
        sb.append(this.block.toString(p + "  "));
        sb.append(p).append("}");
        return sb.toString();
    }
}
