package com.intrbiz.express.statement;

import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;
import com.intrbiz.express.operator.Operator;
import com.intrbiz.express.template.io.TemplateWriter;

/**
 * Evaluate the expression and write it to the context
 */
public class WriteStatement extends Statement
{
    private Operator operator;
    
    public WriteStatement()
    {
        super();
    }
    
    public WriteStatement(Operator operator)
    {
        this();
        this.operator = operator;
    }
    
    public void execute(ExpressContext ctx, Object source) throws ExpressException
    {
       TemplateWriter writer = ctx.getWriter();
       if (writer != null)
       {
           String text = String.valueOf(this.getOperator().get(ctx, source));
           writer.write(text);
       }
    }

    public Operator getOperator()
    {
        return operator;
    }

    public void setOperator(Operator operator)
    {
        this.operator = operator;
    }
    
    public String toString(String p)
    {
        return this.getOperator().toString();
    }
}
