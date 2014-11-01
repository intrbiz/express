package com.intrbiz.express.operator;

import static com.intrbiz.Util.*;

import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;
import com.intrbiz.format.DateFormatter;
import com.intrbiz.format.ThreadSafeSimpleDateFormat;

public class DateFormat extends Function
{
    private Logger logger = Logger.getLogger(DateFormat.class);
    
    private volatile DateFormatter formatterCache;

    public DateFormat()
    {
        super("dateFormat");
    }

    @Override
    public Object get(ExpressContext context, Object source) throws ExpressException
    {
        Operator arg1 = this.getParameters().get(0);
        Operator arg2 = this.getParameters().get(1);
        
        // the formatter
        DateFormatter formatter = this.formatterCache;
        if (formatter == null)
        {
            // get the conversion pattern
            String pattern = (String) arg1.get(context, source);
            if (isEmpty(pattern))
            {
                this.logger.warn("No date format pattern given, usage: dateformat('pattern', date)");
                return null;
            }
            // create the formatter
            formatter = new ThreadSafeSimpleDateFormat(pattern);
            // cache
            if (arg1.isConstant()) this.formatterCache = formatter;
        }
        // get the date to convert
        Date date ;
        Object value = arg2.get(context,source);
        //
        if (value instanceof Date)
        {
            date = (Date) value;
        }
        else if (value instanceof Calendar)
        {
            date = ((Calendar)value).getTime();
        }
        else if (value instanceof Long)
        {
            date = new Date((Long) value);
        }
        else
        {
            return "" ;            
        }
        //
        return formatter.format(date);
    }

    @Override
    public boolean isIdempotent()
    {
        return true;
    }
}
