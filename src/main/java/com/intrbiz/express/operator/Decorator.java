package com.intrbiz.express.operator;

/**
 * <h4>A user extendable decorator function within the EL language.</h4>
 * 
 * <p>
 * Decorator functions allow additional functionality to be added to an entity. 
 * Decorator functions are as such invoked as if they are native methods on an 
 * entity.
 * <p>
 * <h5>For example:</h5>
 * <code>
 * <pre>
 *      entity.decorator(a, '', test=5)
 * </pre>
 * </code>
 * 
 */
public abstract class Decorator extends Function
{
    private Operator entity;

    public Decorator(String name)
    {
        super(name);
    }

    public Operator getEntity()
    {
        return this.entity;
    }

    public void setEntity(Operator entity)
    {
        this.entity = entity;
    }
    
    public String toString()
    {
        StringBuilder sb = new StringBuilder(this.getEntity().toString());
        sb.append(".").append( super.toString() );
        return sb.toString();
    }

}
