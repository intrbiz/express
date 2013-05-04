package com.intrbiz.express.statement;

import java.util.List;

import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;
import com.intrbiz.express.operator.Operator;

public class IfStatement extends ControlStatement
{
    private Operator test;

    private StatementBlock trueBlock;

    private List<ElseIfStatement> elseIfs = null;

    private StatementBlock falseBlock;

    public IfStatement(Operator test, StatementBlock trueBlock, List<ElseIfStatement> elseIfs, StatementBlock falseBlock)
    {
        super("if");
        this.test = test;
        this.trueBlock = trueBlock;
        this.falseBlock = falseBlock;
        this.elseIfs = elseIfs;
    }

    @Override
    public void execute(ExpressContext ctx, Object source) throws ExpressException
    {
        if ((Boolean) this.test.get(ctx, source))
        {
            this.trueBlock.execute(ctx, source);
            // check the context state
            if (ctx.getFrame().isHalt()) return;
        }
        else
        {
            boolean hitElseIf = false;
            // process else ifs
            if (this.elseIfs != null && (!this.elseIfs.isEmpty()))
            {
                for (ElseIfStatement eis : this.getElseIfs())
                {
                    if ((Boolean) eis.getTest().get(ctx, source))
                    {
                        hitElseIf = true;
                        eis.getBlock().execute(ctx, source);
                        // check the context state
                        if (ctx.getFrame().isHalt()) return;
                        break;
                    }
                }
            }
            if (this.falseBlock != null && (!hitElseIf))
            {
                this.falseBlock.execute(ctx, source);
                // check the context state
                if (ctx.getFrame().isHalt()) return;
            }
        }
    }

    public List<ElseIfStatement> getElseIfs()
    {
        return elseIfs;
    }

    public void setElseIfs(List<ElseIfStatement> elseIfs)
    {
        this.elseIfs = elseIfs;
    }

    public Operator getTest()
    {
        return test;
    }

    public void setTest(Operator test)
    {
        this.test = test;
    }

    public StatementBlock getTrueBlock()
    {
        return trueBlock;
    }

    public void setTrueBlock(StatementBlock trueBlock)
    {
        this.trueBlock = trueBlock;
    }

    public StatementBlock getFalseBlock()
    {
        return falseBlock;
    }

    public void setFalseBlock(StatementBlock falseBlock)
    {
        this.falseBlock = falseBlock;
    }

    public String toString(String p)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(p).append("if (").append(this.getTest().toString()).append(") {\r\n");
        sb.append(this.getTrueBlock().toString(p + "  "));
        sb.append(p).append("}");
        if (this.getElseIfs() != null)
        {
            for (ElseIfStatement eis : this.getElseIfs())
            {
                sb.append(p).append("\r\nelse if (").append(eis.getTest().toString()).append(") {\r\n");
                sb.append(eis.getBlock().toString(p + "  "));
                sb.append(p).append("}");                
            }
        }
        if (this.getFalseBlock() != null)
        {
            sb.append(p).append("\r\nelse {\r\n");
            sb.append(this.getFalseBlock().toString(p + "  "));
            sb.append(p).append("}");
        }
        return sb.toString();
    }

    public static class ElseIfStatement
    {
        private Operator test;

        private StatementBlock block;

        public ElseIfStatement(Operator test, StatementBlock block)
        {
            super();
            this.test = test;
            this.block = block;
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
    }
}
