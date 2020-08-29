package com.intrbiz.express.operator;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;

public class Join extends Function
{
    
    public Join()
    {
        super("join");
    }

    @Override
    public Object get(ExpressContext context, Object source) throws ExpressException
    {
        context.checkOp();
        String separator = (String) this.getParameter(0).get(context, source);
        Object value = this.getParameter(1).get(context, source);
        if (value instanceof Collection)
        {
            return ((Collection<?>) value).stream()
                    .filter(Objects::nonNull)
                    .map(String::valueOf)
                    .collect(Collectors.joining(separator));
        }
        else if (value instanceof Object[])
        {
            return Arrays.stream((Object[]) value)
                    .filter(Objects::nonNull)
                    .map(String::valueOf)
                    .collect(Collectors.joining(separator));
        }
        else if (value instanceof Iterable)
        {
            StreamSupport.stream(((Iterable<?>) value).spliterator(), false)
                .filter(Objects::nonNull)
                .map(String::valueOf)
                .collect(Collectors.joining(separator));
        }
        return "";
    }
    
    @Override
    public boolean isIdempotent()
    {
        return true;
    }
}
