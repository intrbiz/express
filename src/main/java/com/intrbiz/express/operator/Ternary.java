package com.intrbiz.express.operator;

import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;

public class Ternary extends Operator
{
    private final Operator condition;
    
    private final Operator main;
    
    private final Operator alternate;
    
    public Ternary(Operator condition, Operator main, Operator alternate)
    {
        super("?");
        this.condition = condition;
        this.main = main;
        this.alternate = alternate;
    }

    @Override
    public Object get(ExpressContext context, Object source) throws ExpressException
    {
        context.checkOp();
        Object evo = this.condition.get(context,source);
        if (evo instanceof Boolean ? ((Boolean)evo).booleanValue() : evo != null)
        {
            return main.get(context,source);
        }
        else
        {
            return alternate.get(context,source);
        }
    }
    
    public Operator getCondition()
    {
        return this.condition;
    }

    public Operator getMain()
    {
        return this.main;
    }

    public Operator getAlternate()
    {
        return this.alternate;
    }

    @Override
    public boolean isConstant()
    {
        return this.condition.isConstant() && this.main.isConstant() && this.alternate.isConstant();
    }
    
    public String toString() {
        return this.condition.toString() + " ? " + this.main.toString() + " : " + this.alternate.toString();
    }
}
