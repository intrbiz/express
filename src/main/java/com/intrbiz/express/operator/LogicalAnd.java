package com.intrbiz.express.operator;

import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;

public class LogicalAnd extends BinaryOperator
{

	public LogicalAnd()
	{
		super("&&");
	}

	public LogicalAnd(Operator l, Operator r)
	{
		this();
		this.setLeft(l);
		this.setRight(r);
	}

	@Override
	public Object get(ExpressContext context, Object source) throws ExpressException
	{
	    context.checkOp();
	    
		Object left = this.getLeft().get(context, source);
		Object right = this.getRight().get(context, source);
		
		boolean l =  left instanceof Boolean ?  ((Boolean) left).booleanValue() :  left != null;
        boolean r = right instanceof Boolean ? ((Boolean) right).booleanValue() : right != null;
        
        return l && r;
	}

	@Override
	public void set(ExpressContext context, Object value, Object source) throws ExpressException
	{
	}
	
	@Override
    public boolean isIdempotent()
    {
        return true;
    }
}
