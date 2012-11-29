package com.intrbiz.express.stack;

import java.util.Map;
import java.util.TreeMap;

public class ELStatementFrame
{
    private static final class STATES
    {
        private static final long RUNNING = 1;

        private static final long HALT = 2;

        private static final long RETURN = 4;

        private static final long BREAK = 8;
    }

    private final ELStatementFrame parent;

    private Map<String, Object> entities = new TreeMap<String, Object>();

    private final boolean function;

    private long state = STATES.RUNNING;

    private Object returnValue;

    public ELStatementFrame(ELStatementFrame parent, boolean function)
    {
        super();
        this.parent = parent;
        this.function = function;
    }
    
    public ELStatementFrame()
    {
        this(null, false);
    }

    public boolean isFunction()
    {
        return this.function;
    }

    public ELStatementFrame getParent()
    {
        return this.parent;
    }

    public boolean containsEntity(String name)
    {
        if (this.entities.containsKey(name))
        {
            return true;
        }
        else if ((!this.function) && this.parent != null) { return this.parent.containsEntity(name); }
        return false;
    }

    public Object getEntity(String name)
    {
        if (this.entities.containsKey(name))
        {
            return this.entities.get(name);
        }
        else if ((!this.function) && this.parent != null) { return this.parent.getEntity(name); }
        return null;
    }

    public void setEntity(String name, Object value)
    {
        // if the variable does not exist, create it in this scope
        if (!this.containsEntity(name))
        {
            this.entities.put(name, value);
        }
        // if the variable exists in this scope, reassign it
        else if (this.entities.containsKey(name))
        {
            this.entities.put(name, value);
        }
        // if we are not a function scope, delegate up the chain
        else if ((!this.function) && this.parent != null)
        {
            this.parent.setEntity(name, value);
        }
    }
    
    public boolean isRunning()
    {
        if (! this.isFunction()) return this.parent.isRunning();
        return (this.state & STATES.RUNNING) == STATES.RUNNING;
    }

    public boolean isHalt()
    {
        if (! this.isFunction()) return this.parent.isHalt();
        return (this.state & STATES.HALT) == STATES.HALT;
    }

    public boolean isReturn()
    {
        if (! this.isFunction()) return this.parent.isReturn();
        return (this.state & STATES.RETURN) == STATES.RETURN;
    }

    public boolean isBreak()
    {
        if (! this.isFunction()) return this.parent.isBreak();
        return (this.state & STATES.BREAK) == STATES.BREAK;
    }

    public void doReturn(Object value)
    {
        if (! this.isFunction()) this.parent.doReturn(value);
        this.state = STATES.RETURN | STATES.HALT;
        this.returnValue = value;
    }

    public Object getReturnValue()
    {
        return this.returnValue;
    }

    public void doBreak()
    {
        if (! this.isFunction()) this.parent.doBreak();
        this.state = STATES.BREAK | STATES.HALT;
    }

    public void doResetBreak()
    {
        if (! this.isFunction()) this.parent.doResetBreak();
        this.state = STATES.RUNNING;
    }
}
