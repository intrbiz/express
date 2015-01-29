package com.intrbiz.express.functions.text;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.log4j.Logger;

import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;
import com.intrbiz.express.operator.Function;
import com.intrbiz.express.operator.Operator;

public class DecimalFormat extends Function
{
    private Logger logger = Logger.getLogger(DecimalFormat.class);

    public DecimalFormat()
    {
        super("decimalformat");
    }

    @Override
    public Object get(ExpressContext context, Object source) throws ExpressException
    {
        // pattern
        Operator arg1 = this.getParameters().get(0);
        // value
        Operator arg2 = this.getParameters().get(1);
        // format
        try
        {
            java.text.DecimalFormat df = new java.text.DecimalFormat((String) arg1.get(context, source));
            Object val = arg2.get(context, source);
            if (val instanceof Number || val instanceof AtomicLong || val instanceof AtomicInteger || val instanceof BigInteger || val instanceof BigDecimal)
            {
                return df.format(val);
            }
        }
        catch (Exception e)
        {
            logger.warn("Failed to format decimal", e);
        }
        return "";
    }

    @Override
    public boolean isIdempotent()
    {
        return true;
    }
}
