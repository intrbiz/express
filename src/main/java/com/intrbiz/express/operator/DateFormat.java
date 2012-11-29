package com.intrbiz.express.operator;

import static com.intrbiz.Util.isEmpty;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.intrbiz.express.ELContext;
import com.intrbiz.express.ELException;

public class DateFormat extends Function
{

    public DateFormat()
    {
        super("dateFormat");
    }

    @Override
    public Object get(ELContext context, Object source) throws ELException
    {
        Operator arg1 = this.getParameters().get(0);
        Operator arg2 = this.getParameters().get(1);
        
        // get the conversion pattern
        String pattern = (String) arg1.get(context,source) ;
        if (isEmpty(pattern)) 
            return "";
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
        else
        {
            return "" ;            
        }
        //
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

}
