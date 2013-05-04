package com.intrbiz.express.operator;

import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;
import com.intrbiz.util.uuid.UUIDFactory;

public class GetUUID extends Function
{
    
    public GetUUID()
    {
        super("getuuid");
    }

    @Override
    public Object get(ExpressContext context,Object source) throws ExpressException
    {
    	return UUIDFactory.uuid();
    }

    @Override
    public void set(ExpressContext context, Object value,Object source) throws ExpressException
    {
    }
}
