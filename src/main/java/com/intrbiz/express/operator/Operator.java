package com.intrbiz.express.operator;

import com.intrbiz.converter.Converter;
import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;
import com.intrbiz.validator.Validator;

public abstract class Operator
{
    
    private final String name ;
    
    protected Operator (String name)
    {
        this.name = name ;
    }
    
    public Converter<?> getConverter(ExpressContext context, Object source)  throws ExpressException
    {
        throw new ExpressException("Cannot get converter,  expression contains a operator") ;
    }
    
    public Object get(ExpressContext context, Object source) throws ExpressException
    {
        throw new ExpressException("Unimplemented");
    }
    public void set(ExpressContext context, Object value, Object source) throws ExpressException
    {
        throw new ExpressException("Unimplemented");
    }

    /**
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    @Override
    /**
     * All operators must override Object.toString().
     * This method must reconstruct the expression
     */
    public String toString()
    {
        return this.name;
    } 
    
    public Validator<?> getValidator(ExpressContext context, Object source)  throws ExpressException
    {
        throw new ExpressException("Cannot get validator,  expression contains a operater") ;
    }
    
    /**
     * Is this operator constant, IE: will it always evaluate 
     * to the same value, making it ideal to optimisation.
     */
    public boolean isConstant()
    {
        return false;
    }
}
