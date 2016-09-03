package com.intrbiz.express.util;

import java.io.StringReader;

import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;
import com.intrbiz.express.operator.Operator;
import com.intrbiz.express.parser.ELParser;
import com.intrbiz.express.parser.ParseException;
import com.intrbiz.express.parser.TokenMgrError;

public class ELUtil
{
    public static final Operator parseEL(String expression, ExpressContext context) throws ExpressException
    {
        ELParser parser = new ELParser(new StringReader(expression));
        try
        {
            return parser.readFullExpression(context);
        }
        catch (TokenMgrError e)
        {
            throw new ExpressException("Error parsing expression: [" + expression + "]", e);
        }
        catch (ExpressException ee)
        {
            throw new ExpressException("Error parsing expression: [" + expression + "]", ee);
        }
        catch (ParseException pe)
        {
            throw new ExpressException("Error parsing expression: [" + expression + "]", pe);
        }
    }
    
    public static final Operator parseEmbeddedEL(String input, ExpressContext context) throws ExpressException
    {
        return parseEL(input, context);
    }
}
