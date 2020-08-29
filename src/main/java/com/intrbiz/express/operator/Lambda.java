package com.intrbiz.express.operator;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;
import com.intrbiz.express.statement.StatementBlock;

public class Lambda extends Operator
{
    private final String[] argumentNames;

    private final StatementBlock block;
    
    private final Operator expression;

    public Lambda(List<String> argumentNames, StatementBlock block, Operator expression)
    {
        super("lambda");
        this.argumentNames = argumentNames.toArray(new String[argumentNames.size()]);
        this.block = block;
        this.expression = expression;
    }

    @Override
    public Object get(ExpressContext context, Object source) throws ExpressException
    {
        context.checkOp();
        
        return new InvocationHandler() {
            @Override
            public Object invoke(Object proxyInstance, Method invokedMethod, Object[] parameters) throws Throwable
            {
                // enter the a frame
                context.enterFrame(true);
                try
                {
                    // expose our parameters as variables
                    for (int i = 0; i < parameters.length && i < argumentNames.length; i++)
                    {
                        context.getFrame().setEntity(argumentNames[i], parameters[i]);
                    }
                    // expose our parameters directly
                    context.getFrame().setEntity("_arguments", Arrays.asList(parameters));
                    // execute the script
                    if (block != null)
                    {
                        block.execute(context, source);
                        // process the results
                        if (context.getFrame().isReturn())
                            return context.getFrame().getReturnValue();
                    }
                    else if (expression != null)
                    {
                        return expression.get(context, source);
                    }
                }
                finally
                {
                    context.exitFrame();
                }
                return null;
            }
        };
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        boolean ns = false;
        for (String arg : this.argumentNames)
        {
            if (ns) sb.append(", ");
            sb.append(arg);
            ns = true;
        }
        sb.append(") -> ");
        if (this.block != null)
            sb.append("{ ").append(this.block.toString()).append(" }");
        if (this.expression != null)
            sb.append(this.expression.toString());
        return sb.toString();
    }
    
    @Override
    public boolean isConstant()
    {
        return false;
    }
}
