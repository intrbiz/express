package com.intrbiz.express;

import com.intrbiz.express.action.ActionHandler;
import com.intrbiz.express.security.Hidden;

/**
 * Resolve entities for Express
 */
public abstract class ExpressEntityResolver implements Hidden
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
