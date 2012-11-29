package com.intrbiz.express;

import com.intrbiz.express.action.ActionHandler;
import com.intrbiz.express.operator.Decorator;
import com.intrbiz.express.operator.Function;
import com.intrbiz.express.stack.ELStatementFrame;

public abstract class AbstractELContext implements ELContext
{
    private final ELStatementFrame root = new ELStatementFrame();

    private ELStatementFrame frame = root;

    public AbstractELContext()
    {
        super();
    }

    public Object getEntity(String name, Object source)
    {
        if (this.frame != null && this.frame.containsEntity(name))
        {
            return this.frame.getEntity(name);
        }
        else if (this.root.containsEntity(name)) { return this.root.getEntity(name); }
        return this.getEntityInner(name, source);
    }

    public abstract Object getEntityInner(String name, Object source);

    public void setEntity(String name, Object value, Object source)
    {
        if (this.frame != null)
            this.frame.setEntity(name, value);
        else
            this.root.setEntity(name, value);
    }

    public void exportEntity(String name, Object value, Object source)
    {
        // Export the entity to the root of the stack
        this.root.setEntity(name, value);
    }

    @Override
    public ActionHandler getAction(String name, Object source)
    {
        return null;
    }

    // default implementations to make like simpler

    @Override
    public Function getCustomFunction(String name)
    {
        return null;
    }

    @Override
    public Decorator getCustomDecorator(String name, Class<?> entityType)
    {
        return null;
    }

    // Stack Control

    public void enterFrame(boolean function)
    {
        this.frame = new ELStatementFrame(this.frame, function);
    }

    public void exitFrame()
    {
        if (this.frame != null)
        {
            if (this.frame.getParent() == null) throw new IllegalStateException("You cannot remove the root stack frame!");
            this.frame = this.frame.getParent();
        }
    }

    public ELStatementFrame getFrame()
    {
        return this.frame;
    }
}
