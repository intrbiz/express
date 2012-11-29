package com.intrbiz.express.operator;

import com.intrbiz.express.ELContext;
import com.intrbiz.express.ELException;
import com.intrbiz.util.uuid.UUIDFactory;

public class GetUUID extends Function
{
    
    public GetUUID()
    {
        super("getuuid");
    }

    @Override
    public Object get(ELContext context,Object source) throws ELException
    {
    	return UUIDFactory.uuid();
    }

    @Override
    public void set(ELContext context, Object value,Object source) throws ELException
    {
    }
}
