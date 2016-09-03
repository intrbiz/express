package com.intrbiz.express.statement;

import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;
import com.intrbiz.express.operator.Operator;

public class TextStatement extends Statement
{
    private Operator operator;
    
    public TextStatement()
    {
        super();
    }
    
    public TextStatement(Operator operator)
    {
        this();
        this.operator = operator;
    }
    
    public void execute(ExpressContext ctx, Object source) throws ExpressException
    {
        System.out.println("Text: " + this.getOperator().get(ctx, source));
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
