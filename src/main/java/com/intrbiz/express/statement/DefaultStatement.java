package com.intrbiz.express.statement;

import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;
import com.intrbiz.express.operator.Operator;

public class DefaultStatement extends Statement
{
    private Operator operator;
    
    public DefaultStatement()
    {
        super();
    }
    
    public DefaultStatement(Operator operator)
    {
        this();
        this.operator = operator;
    }
    
    public void execute(ExpressContext ctx, Object source) throws ExpressException
    {
        this.getOperator().get(ctx, source);
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
        return p + this.getOperator().toString() + ";";
    }
}
