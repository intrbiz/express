package com.intrbiz.express.functions.script;

import java.util.List;
import java.util.Map.Entry;

import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;
import com.intrbiz.express.operator.Function;
import com.intrbiz.express.operator.Operator;
import com.intrbiz.express.statement.StatementBlock;

public class ScriptedFunction extends Function
{
    private final String[] argumentNames;
    
    private final StatementBlock block;

    public ScriptedFunction(String name, List<String> argumentNames, StatementBlock block)
    {
        super(name);
        this.argumentNames = argumentNames.toArray(new String[argumentNames.size()]);
        this.block = block;
    }

    @Override
    public Object get(ExpressContext context, Object source) throws ExpressException
    {
        // enter the root frame
        context.enterFrame(true);
        try
        {
            // we don't need to eval the arguments right now, we can leave that to chaining
            // expose our parameters as variables
            int i = 0;
            for (Operator parameter : this.getParameters())
            {
                if (i < this.argumentNames.length)
                    context.getFrame().setEntity(this.argumentNames[i], parameter);
                i++;
            }
            // expose our arguments directly
            context.getFrame().setEntity("arguments", this.getParameters());
            // expose our named parameters as variables
            for (Entry<String, Operator> namedParameter : this.getNamedParameters().entrySet())
            {
                context.getFrame().setEntity(namedParameter.getKey(), namedParameter.getValue());
            }
            // execute the script
            block.execute(context, source);
            // process the results
            if (context.getFrame().isReturn()) return context.getFrame().getReturnValue();
        }
        finally
        {
            context.exitFrame();
        }
        return null;
    }
    
}
