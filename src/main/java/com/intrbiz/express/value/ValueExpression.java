package com.intrbiz.express.value;

import java.io.Serializable;

import com.intrbiz.converter.Converter;
import com.intrbiz.express.ELContext;
import com.intrbiz.express.ELException;
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

    public ValueExpression(ELContext context, String expression) throws ELException
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

    public Object get(ELContext context, Object source) throws ELException
    {
        return this.operator.get(context, source);
    }

    public void set(ELContext context, Object in, Object source) throws ELException
    {
        this.operator.set(context, in, source);
    }

    public void parse(ELContext context, String expression) throws ELException
    {
        // treat as a string with embedded EL expressions
        this.operator = ELUtil.parseEmbeddedEL(expression, context);
    }

    @Override
    public String toString()
    {
        return "#{" + String.valueOf(this.operator) + "}";
    }

    public Converter getConverter(ELContext context, Object source) throws ELException
    {
        return operator.getConverter(context, source);
    }

    public Validator getValidator(ELContext context, Object source) throws ELException
    {
        return operator.getValidator(context, source);
    }

    public Operator getOperator()
    {
        return this.operator;
    }
}
