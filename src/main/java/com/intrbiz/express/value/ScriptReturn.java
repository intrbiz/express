package com.intrbiz.express.value;

/**
 * Simple structure to provide the state of a script upon return
 *
 */
public final class ScriptReturn
{
    public final boolean isReturn;
    
    public final Object value;
    
    public ScriptReturn(boolean isReturn, Object value)
    {
        this.isReturn = isReturn;
        this.value = value;
    }

    public boolean isReturn()
    {
        return this.isReturn;
    }

    public Object getValue()
    {
        return this.value;
    }
}
