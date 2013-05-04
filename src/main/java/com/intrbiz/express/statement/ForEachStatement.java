package com.intrbiz.express.statement;

import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;
import com.intrbiz.express.operator.Operator;

public class ForEachStatement extends ControlStatement
{
    private String entityName;

    private Operator collection;

    private StatementBlock block;

    public ForEachStatement(String entityName, Operator collection, StatementBlock block)
    {
        super("foreach");
        this.setEntityName(entityName);
        this.setCollection(collection);
        this.setBlock(block);
    }

    @Override
    public void execute(ExpressContext ctx, Object source) throws ExpressException
    {
        // get the collection to process
        Object col = this.getCollection().get(ctx, source);
        if (col instanceof Iterable<?>)
        {
            for (Object colObj : (Iterable<?>) col)
            {
                // set the entity
                ctx.setEntity(this.getEntityName(), colObj, source);
                // execute the block
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
        // error if it is not a collection
        else if (col != null)
        {
            throw new ExpressException("Cannot execute foreach on: " + col.getClass());
        }
    }

    public String getEntityName()
    {
        return entityName;
    }

    public void setEntityName(String entityName)
    {
        this.entityName = entityName;
    }

    public Operator getCollection()
    {
        return collection;
    }

    public void setCollection(Operator collection)
    {
        this.collection = collection;
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
        sb.append(p).append("for (").append(this.getEntityName()).append(" in ").append(this.getCollection().toString()).append(") {\r\n");
        sb.append(this.block.toString(p + "  "));
        sb.append(p).append("}");
        return sb.toString();
    }
}
