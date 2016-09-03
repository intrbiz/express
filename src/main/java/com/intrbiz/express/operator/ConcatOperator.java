package com.intrbiz.express.operator;

import java.util.LinkedList;
import java.util.List;

import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;

public class ConcatOperator extends Operator
{
    private List<Operator> operators = new LinkedList<Operator>();
    
    private boolean constant = true;
    
    public ConcatOperator()
    {
        super("concat");
    }
    
    public ConcatOperator(Operator first)
    {
        this();
        this.operators.add(first);
        this.constant = first.isConstant();
    }

    public List<Operator> getOperators()
    {
        return operators;
    }

    public void setOperators(List<Operator> operators)
    {
        this.operators = operators;
        this.constant = true;
        for (Operator op : operators)
        {
            this.constant &= op.isConstant();
        }
    }
    
    public void addOperator(Operator operator)
    {
        this.operators.add(operator);
        this.constant &= operator.isConstant();
    }
    
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        for (Operator op : this.operators)
        {
            sb.append(op);
        }
        return sb.toString();
    }

    @Override
    public boolean isConstant()
    {
        return this.constant;
    }

    @Override
    public Object get(ExpressContext context, Object source) throws ExpressException
    {
        StringBuilder sb = new StringBuilder();
        for (Operator op : this.operators)
        {
            sb.append(op.get(context, source));
        }
        return sb.toString();
    }
}
