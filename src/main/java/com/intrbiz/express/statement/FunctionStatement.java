package com.intrbiz.express.statement;

import java.util.List;

import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;
import com.intrbiz.express.ExpressExtensionRegistry;
import com.intrbiz.express.functions.script.ScriptedFunction;

public class FunctionStatement extends Statement
{
    private String functionName;

    private List<String> argumentNames;

    private StatementBlock block;

    public FunctionStatement(String functionName, List<String> argumentNames, StatementBlock block)
    {
        this.setFunctionName(functionName);
        this.setArgumentNames(argumentNames);
        this.setBlock(block);
    }
    
    public void register(ExpressContext ctx) throws ExpressException
    {
        ExpressExtensionRegistry reg = ctx.getExpressExtensionRegistry();
        if (reg == null) throw new ExpressException("Cannot register function, context has no registry");
        // register this script block as an immutable function
        reg.addFunction(new ScriptedFunction(this.functionName, this.argumentNames, block));
    }

    @Override
    public void execute(ExpressContext ctx, Object source) throws ExpressException
    {
        // nout to do
    }

    public String getFunctionName()
    {
        return functionName;
    }

    public void setFunctionName(String functionName)
    {
        this.functionName = functionName;
    }

    public List<String> getArgumentNames()
    {
        return argumentNames;
    }

    public void setArgumentNames(List<String> argumentNames)
    {
        this.argumentNames = argumentNames;
    }

    public StatementBlock getBlock()
    {
        return block;
    }

    public void setBlock(StatementBlock block)
    {
        this.block = block;
    }

    public String toString(String p)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(p).append("function ").append(this.getFunctionName()).append("(");
        boolean ns = false;
        for (String argName : this.getArgumentNames())
        {
            if (ns) sb.append(", ");
            sb.append(argName);
            ns = true;
        }
        sb.append(")\n");
        sb.append(p).append("{\n");
        sb.append(this.block.toString());
        sb.append(p).append("}\n");
        return sb.toString();
    }
}
