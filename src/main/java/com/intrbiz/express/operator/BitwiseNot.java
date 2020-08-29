package com.intrbiz.express.operator;

import java.nio.ByteBuffer;

import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;

public class BitwiseNot extends UnaryOperator
{

	public BitwiseNot()
	{
		super("~");
	}

	public BitwiseNot(Operator o)
	{
		this();
		this.setOperator(o);
	}

	@Override
	public Object get(ExpressContext context, Object source) throws ExpressException
	{
	    context.checkOp();
	    
		Object val = this.getOperator().get(context, source);
        // numeric bitwise
        if (val instanceof Number)
        {
            Number nval = (Number) val;
            if (nval instanceof Long)
            {
                return new Long(~ nval.longValue());
            }
            if (nval instanceof Integer)
            {
                return new Integer(~ nval.intValue());
            }
            if (nval instanceof Byte)
            {
                return new Byte((byte)(~ nval.byteValue()));
            }
        }
        else if (val instanceof byte[])
        {
            byte[] bval = (byte[]) val;
            byte[] result = new byte[bval.length];
            for (int i = 0; i < result.length; i++)
            {
                result[i] = (byte) (~ bval[i]);
            }
            return result;
        }
        else if (val instanceof ByteBuffer)
        {
            ByteBuffer bval = (ByteBuffer) val;
            ByteBuffer result = ByteBuffer.allocate(bval.remaining());
            for (int i = 0; i < result.remaining(); i++)
            {
                result.put((byte) (~ bval.get()));
            }
            return result;   
        }
        return null;
	}

	@Override
	public void set(ExpressContext context, Object value, Object source) throws ExpressException
	{
	}
}
