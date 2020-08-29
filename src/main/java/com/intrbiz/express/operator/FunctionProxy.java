package com.intrbiz.express.operator;

import com.intrbiz.converter.Converter;
import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;
import com.intrbiz.express.parser.ELParser;
import com.intrbiz.validator.Validator;

/**
 * A function which is resolved at runtime rather than parse time
 */
public class FunctionProxy extends Function
{
    private Function function;
    
    public FunctionProxy(String functionName)
    {
        super(functionName);
    }
    
    private Function loadFunction(ExpressContext context) throws ExpressException
    {
        if (this.function != null) return this.function;
        // load the function
        Function function = ELParser.loadFunction(this.getName(), context, false);
        if (function == null) throw new ExpressException("Failed to load function " + this.getName());
        // bind parameters
        function.setParameters(this.getParameters());
        // bind named parameters
        function.setNamedParameters(this.getNamedParameters());
        // cache
        this.function = function;
        return function;
    }

    @Override
    public Object get(ExpressContext context, Object source) throws ExpressException
    {
        context.checkOp();
        return this.loadFunction(context).get(context, source);
    }
    
    @Override
    public Converter<?> getConverter(ExpressContext context, Object source) throws ExpressException
    {
        context.checkOp();
        return this.loadFunction(context).getConverter(context, source);
    }

    @Override
    public Validator<?> getValidator(ExpressContext context, Object source) throws ExpressException
    {
        context.checkOp();
        return this.loadFunction(context).getValidator(context, source);
    }

    @Override
    public void set(ExpressContext context, Object value, Object source) throws ExpressException
    {
        context.checkOp();
        this.loadFunction(context).set(context, value, source);
    }

    @Override
    public boolean isConstant()
    {
        return false;
    }

    @Override
    public boolean isIdempotent()
    {
        return false;
    }
}
