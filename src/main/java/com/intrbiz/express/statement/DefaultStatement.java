package com.intrbiz.express.statement;

import com.intrbiz.express.ELContext;
import com.intrbiz.express.ELException;
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
    
    public void execute(ELContext ctx, Object source) throws ELException
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
