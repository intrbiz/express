package com.intrbiz.express.operator;

import com.intrbiz.converter.Converter;
import com.intrbiz.express.ELContext;
import com.intrbiz.express.ELException;
import com.intrbiz.validator.Validator;

public abstract class Operator
{
    
    private final String name ;
    
    protected Operator (String name)
    {
        this.name = name ;
    }
    
    public Converter getConverter(ELContext context, Object source)  throws ELException
    {
        throw new ELException("Cannot get converter,  expression contains a operator") ;
    }
    
    public Object get(ELContext context, Object source) throws ELException
    {
        throw new ELException("Unimplemented");
    }
    public void set(ELContext context, Object value, Object source) throws ELException
    {
        throw new ELException("Unimplemented");
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
    
    public Validator getValidator(ELContext context, Object source)  throws ELException
    {
        throw new ELException("Cannot get validator,  expression contains a operater") ;
    }
}
