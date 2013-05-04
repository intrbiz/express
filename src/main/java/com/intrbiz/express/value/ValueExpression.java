package com.intrbiz.express.value;

import java.io.Serializable;

import com.intrbiz.converter.Converter;
import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;
import com.intrbiz.express.operator.Operator;
import com.intrbiz.express.util.ELUtil;
import com.intrbiz.validator.Validator;

public class ValueExpression implements Serializable
{
    private static final long serialVersionUID = 1L;

    private Operator operator = null;

    public ValueExpression()
    {
        super();
    }

    public ValueExpression(ExpressContext context, String expression) throws ExpressException
    {
        this();
        this.parse(context, expression);
    }

    public ValueExpression(Operator operator)
    {
        this.operator = operator;
    }

    public static boolean isValueExpression(String s)
    {
        return s.startsWith("#{") && s.endsWith("}");
    }

    public Object get(ExpressContext context, Object source) throws ExpressException
    {
        return this.operator.get(context, source);
    }

    public void set(ExpressContext context, Object in, Object source) throws ExpressException
    {
        this.operator.set(context, in, source);
    }

    public void parse(ExpressContext context, String expression) throws ExpressException
    {
        // treat as a string with embedded EL expressions
        this.operator = ELUtil.parseEmbeddedEL(expression, context);
    }

    @Override
    public String toString()
    {
        return "#{" + String.valueOf(this.operator) + "}";
    }

    public Converter getConverter(ExpressContext context, Object source) throws ExpressException
    {
        return operator.getConverter(context, source);
    }

    public Validator getValidator(ExpressContext context, Object source) throws ExpressException
    {
        return operator.getValidator(context, source);
    }

    public Operator getOperator()
    {
        return this.operator;
    }
}
