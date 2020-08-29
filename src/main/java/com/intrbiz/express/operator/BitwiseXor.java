package com.intrbiz.express.operator;

import java.nio.ByteBuffer;

import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;

public class BitwiseXor extends BinaryOperator
{
    
    public BitwiseXor()
    {
        super("|^");
    }
    
    public BitwiseXor(Operator l, Operator r)
    {
    	this();
    	this.setLeft(l);
    	this.setRight(r);
    }

    @Override
    public Object get(ExpressContext context, Object source) throws ExpressException
    {
        context.checkOp();
        
        Object left = this.getLeft().get(context, source);
        Object right = this.getRight().get(context, source);
        // numeric bitwise
        if (left instanceof Number && right instanceof Number)
        {
            Number nleft = (Number) left;
            Number nright = (Number) right;
            if (left instanceof Long || right instanceof Long)
            {
                return new Long(nleft.longValue() ^ nright.longValue());
            }
            if (left instanceof Integer || right instanceof Integer)
            {
                return new Integer(nleft.intValue() ^ nright.intValue());
            }
            if (left instanceof Byte || right instanceof Byte)
            {
                return new Byte((byte) (nleft.byteValue() ^ nright.byteValue()));
            }
        }
        else if (left instanceof byte[] && right instanceof byte[])
        {
            byte[] bleft = (byte[]) left;
            byte[] bright = (byte[]) right;
            byte[] result = new byte[bleft.length < bright.length ? bleft.length : bright.length];
            for (int i = 0; i < result.length; i++)
            {
                result[i] = (byte) (bleft[i] ^ bright[i]);
            }
            return result;
        }
        else if (left instanceof byte[] && right instanceof Number)
        {
            byte[] bleft = (byte[]) left;
            Number bright = (Number) right;
            byte[] result = new byte[bleft.length];
            int rval = bright.intValue();
            for (int i = 0; i < result.length; i++)
            {
                result[i] = (byte) (bleft[i] ^ rval);
            }
            return result;
        }
        else if (left instanceof ByteBuffer && right instanceof ByteBuffer)
        {
            ByteBuffer bleft = (ByteBuffer) left;
            ByteBuffer bright = (ByteBuffer) right;
            ByteBuffer result = ByteBuffer.allocate(bleft.remaining() < bright.remaining() ? bleft.remaining() : bright.remaining());
            for (int i = 0; i < result.remaining(); i++)
            {
                result.put((byte) (bleft.get() ^ bright.get()));
            }
            return result;   
        }
        else if (left instanceof ByteBuffer && right instanceof Number)
        {
            ByteBuffer bleft = (ByteBuffer) left;
            Number bright = (Number) right;
            ByteBuffer result = ByteBuffer.allocate(bleft.remaining());
            int rval = bright.intValue();
            for (int i = 0; i < result.remaining(); i++)
            {
                result.put((byte) (bleft.get() ^ rval));
            }
            return result;   
        }
        return null;
    }

    @Override
    public void set(ExpressContext context, Object value, Object source) throws ExpressException
    {
    }
    
    @Override
    public boolean isIdempotent()
    {
        return true;
    }
}
