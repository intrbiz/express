package com.intrbiz.express.util;

import static com.intrbiz.Util.isEmpty;

import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Method;

import com.intrbiz.express.ELContext;
import com.intrbiz.express.ELException;
import com.intrbiz.express.operator.Add;
import com.intrbiz.express.operator.NullLiteral;
import com.intrbiz.express.operator.Operator;
import com.intrbiz.express.operator.StringLiteral;
import com.intrbiz.express.parser.ELParser;
import com.intrbiz.express.parser.ParseException;
import com.intrbiz.express.parser.TokenMgrError;

public class ELUtil
{
    public static final Operator parseEL(String expression, ELContext context) throws ELException
    {
        expression = expression.substring(2, expression.length() - 1);
        ELParser parser = new ELParser(new StringReader(expression));
        try
        {
            return parser.readExpression(context);
        }
        catch (TokenMgrError e)
        {
            throw new ELException("Error parsing script: [" + expression + "]", e);
        }
        catch (ELException ee)
        {
            throw new ELException("Error parsing expression: [" + expression + "]", ee);
        }
        catch (ParseException pe)
        {
            throw new ELException("Error parsing expression: [" + expression + "]", pe);
        }
        
    }
    
    public static final Operator parseEmbeddedEL(String input, ELContext context) throws ELException
    {
        if (input == null) return new NullLiteral();
        if (isEmpty(input)) return new StringLiteral("", false);
        // parse the string looking for #{ELEXP} and build up a corresponding EL expression
        Operator op = null;
        StringBuilder expression = new StringBuilder();
        StringBuilder plain = new StringBuilder();
        boolean inQuotes = false;
        boolean inExp = false;
        StringReader reader = new StringReader(input);
        int i;
        try
        {
            while ((i = reader.read()) != -1)
            {
                char c = (char) i;
                switch (c)
                {
                    case '\'':
                        inQuotes = !inQuotes;
                        if (inExp) expression.append(c);
                        else plain.append(c);
                        break;
                    case '#':
                        if (!inQuotes)
                        {
                            int n = reader.read();
                            char next = (char) n;
                            if (next == '{')
                            {
                                inExp = true;
                                expression.append(c).append(next);
                            }
                            else
                            {
                                plain.append(c).append(next);
                            }
                        }
                        else
                        {
                            if (inExp) expression.append(c);
                            else plain.append(c);
                        }
                        break;
                    case '}':
                        if (inExp) expression.append(c);
                        else plain.append(c);
                        if ((!inQuotes) && (inExp))
                        {
                            // parse the el expression
                            if (op == null)
                            {
                                if (plain.length() > 0)
                                {
                                    op = new Add(new StringLiteral( plain.toString(), false ), parseEL( expression.toString(), context ));
                                }
                                else
                                {
                                    op = parseEL( expression.toString(), context );
                                }
                            }
                            else
                            {
                                if (plain.length() > 0)
                                {
                                    op = new Add( op, new Add( new StringLiteral( plain.toString(), false ), parseEL( expression.toString(), context ) ) );
                                }
                                else
                                {
                                    op = new Add( op, parseEL( expression.toString(), context ) );
                                }
                            }
                            // reset
                            expression = new StringBuilder();
                            plain = new StringBuilder();
                            inQuotes = false;
                            inExp = false;
                        }
                        break;
                    default:
                        if (inExp) expression.append(c);
                        else plain.append(c);
                        break;
                }
            }
        }
        catch (IOException e)
        {
            throw new ELException("Error processing el, io error", e);
        }
        finally
        {
            reader.close();
        }
        if (op == null)
        {
            op = new StringLiteral(plain.toString(), false);
        }
        else
        {
            if (plain.length() > 0)
            {
                op = new Add(op, new StringLiteral(plain.toString(), false));
            }
        }
        return op;
    }
   
    public static Method findGetter(Object entity, String field)
    {
        if (entity == null) return null;
        String methodName = "get" + field.substring(0, 1).toUpperCase() + field.substring(1);
        Method[] methods = entity.getClass().getMethods();
        for (int i = 0; i < methods.length; i++)
        {
            Method method = methods[i];
            if (method.getName().equals(methodName)) { return method; }
        }
        methodName = "is" + field.substring(0, 1).toUpperCase() + field.substring(1);
        for (int i = 0; i < methods.length; i++)
        {
            Method method = methods[i];
            if (method.getName().equals(methodName)) { return method; }
        }
        methodName = field;
        for (int i = 0; i < methods.length; i++)
        {
            Method method = methods[i];
            if (method.getName().equals(methodName)) { return method; }
        }
        return null;
    }

    public static Method findSetter(Object entity, String field)
    {
        if (entity == null) return null;
        String methodName = "set" + field.substring(0, 1).toUpperCase() + field.substring(1);
        Method[] methods = entity.getClass().getMethods();
        for (int i = 0; i < methods.length; i++)
        {
            Method method = methods[i];
            if (method.getName().equals(methodName)) { return method; }
        }
        methodName = field;
        for (int i = 0; i < methods.length; i++)
        {
            Method method = methods[i];
            if (method.getName().equals(methodName)) { return method; }
        }
        return null;
    }

}
