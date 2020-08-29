package com.intrbiz.express.statement;

import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;
import com.intrbiz.express.operator.Operator;
import com.intrbiz.express.stack.ELStatementFrame;

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
    public void execute(ExpressContext ctx, Object source) throws ExpressException
    {
        Operator test = this.getTest();
        StatementBlock block = this.getBlock();
        ELStatementFrame frame = ctx.getFrame();
        while ((Boolean) test.get(ctx, source))
        {
            ctx.checkIteration();
            block.execute(ctx, source);
            // check the context state
            if (frame.isHalt())
            {
                if (frame.isBreak())
                {
                    frame.doReset();
                    break;
                }
                else if (frame.isContinue())
                {
                    frame.doReset();
                }
                else if (frame.isReturn())
                {
                    return;
                }
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
        sb.append(p).append("}\r\n");
        return sb.toString();
    }
}
