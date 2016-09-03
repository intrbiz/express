package com.intrbiz.express.operator;

import com.intrbiz.converter.Converter;
import com.intrbiz.converter.converters.StringConverter;
import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;
import com.intrbiz.validator.Validator;
import com.intrbiz.validator.validators.TextValidator;

public class PlainLiteral extends Literal
{

    private String value;

    public PlainLiteral(String val)
    {
        super("PlainLiteral");
        this.value = val;
    }

    public PlainLiteral()
    {
        super("PlainLiteral");
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    public String toString()
    {
        return this.getValue();
    }

    @Override
    public Converter<?> getConverter(ExpressContext context, Object source) throws ExpressException
    {
        return new StringConverter();
    }

    @Override
    public Validator<?> getValidator(ExpressContext context, Object source) throws ExpressException
    {
        return new TextValidator();
    }
}
