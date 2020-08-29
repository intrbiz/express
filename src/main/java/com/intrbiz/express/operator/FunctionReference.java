package com.intrbiz.express.operator;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;
import com.intrbiz.express.parser.ELParser;

public class FunctionReference extends Operator
{
    private final String functionName;

    public FunctionReference(String functionName)
    {
        super("::");
        this.functionName = Objects.requireNonNull(functionName);
    }

    @Override
    public Object get(ExpressContext context, Object source) throws ExpressException
    {
        context.checkOp();
        
        return new InvocationHandler() {
            @Override
            public Object invoke(Object proxyInstance, Method invokedMethod, Object[] parameters) throws Throwable
            {
                // load the function
                Function function = ELParser.loadFunction(functionName, context, false);
                // bind the parameters
                function.setParameters(Stream.of(parameters).map(Parameter::new).collect(Collectors.toList()));
                // enter the a frame
                context.enterFrame(true);
                try
                {
                    // execute the function
                    return function.get(context, source);
                }
                finally
                {
                    context.exitFrame();
                }
            }
        };
    }

    @Override
    public String toString()
    {
        return "::" + this.functionName;
    }
    
    @Override
    public boolean isConstant()
    {
        return false;
    }
    
    private static class Parameter extends Operator
    {
        private final Object value;
        
        public Parameter(Object value)
        {
            super("parameter");
            this.value = value;
        }

        @Override
        public Object get(ExpressContext context, Object source) throws ExpressException
        {
            return value;
        }
    }
}
