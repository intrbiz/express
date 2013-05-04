package com.intrbiz.express;

import com.intrbiz.express.action.ActionHandler;

/**
 * Resolve entities for Express
 */
public abstract class ExpressEntityResolver
{
    public Object getEntity(String name, Object source)
    {
        return null;
    }
    
    public ActionHandler getAction(String name, Object source)
    {
        return null;
    }
}
