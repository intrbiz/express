package com.intrbiz.express.operator;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import com.intrbiz.converter.Converter;
import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;
import com.intrbiz.validator.Validator;

/**
 * A user extendable function within the EL language.
 * 
 * To create a function, create a sub class of this class and 
 * register it with the context.  Its functionality is then 
 * accessible from the EL.
 *
 */
public abstract class Function extends Operator
{

    private List<Operator> parameters = new LinkedList<Operator>();

    private Map<String, Operator> namedParameters = new TreeMap<String, Operator>();

    protected Function(String name)
    {
        super(name);
    }

    /**
     * Get the functions parameters list
     * @return the parameters
     */
    public List<Operator> getParameters()
    {
        return parameters;
    }

    /**
     * Set the functions parameters list
     * @param parameters
     *            the parameters to set
     */
    public void setParameters(List<Operator> parameters)
    {
        this.parameters = parameters;
    }

    /**
     * Get the functions named parameters
     * @return
     * returns Map<String,Operator>
     */
    public Map<String, Operator> getNamedParameters()
    {
        return namedParameters;
    }

    /**
     * Set the functions named parameters
     * @param namedParameters
     * returns void
     */
    public void setNamedParameters(Map<String, Operator> namedParameters)
    {
        this.namedParameters = namedParameters;
    }
    
    /**
     * Get a standard parameter by its index.
     * The index is not affect by named parameters.
     * @param idx
     * @return
     * returns Operator
     */
    public Operator getParameter(int idx)
    {
        return this.parameters.get(idx);
    }
    
    /**
     * Get a named parameter
     * @param name
     * @return
     * returns Operator
     */
    public Operator getParameter(String name)
    {
        return this.namedParameters.get(name);
    }

    @Override
    public String toString()
    {
        StringBuffer ret = new StringBuffer();
        ret.append(this.getName());
        ret.append("(");
        boolean npa = false;
        for (Operator op : this.getParameters())
        {
            if (npa) ret.append(", ");
            ret.append(op.toString());
            npa = true;
        }
        for (Entry<String,Operator> nop : this.getNamedParameters().entrySet())
        {
            if (npa) ret.append(", ");
            ret.append(nop.getKey()).append(" = ").append(nop.getValue());
            npa = true;
        }
        ret.append(")");
        return ret.toString();
    }

    @Override
    public Converter getConverter(ExpressContext context, Object source) throws ExpressException
    {
        throw new ExpressException("Cannot get converter,  expression contains a function");
    }

    @Override
    public Validator getValidator(ExpressContext context, Object source) throws ExpressException
    {
        throw new ExpressException("Cannot get validator,  expression contains a function");
    }
    
    @Override
    public void set(ExpressContext context, Object value, Object source) throws ExpressException
    {
        throw new ExpressException("Cannot set value, expression contains a function");
    }

}
