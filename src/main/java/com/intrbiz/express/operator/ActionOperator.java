package com.intrbiz.express.operator;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;
import com.intrbiz.express.action.ActionHandler;
import com.intrbiz.express.action.ActionHandler.ActionArgument;

public class ActionOperator extends Function
{
    private ActionHandler action;
    
    private List<Operator> actionArguments = null;

    public ActionOperator(String name, List<Operator> args, Map<String, Operator> namedArgs)
    {
        super(name);
        this.setParameters(args);
        this.setNamedParameters(namedArgs);
    }
    
    @Override
    public String toString()
    {
        StringBuffer ret = new StringBuffer("@");
        ret.append(this.getName());
        ret.append("(");
        boolean npa = false;
        for (Operator op : this.getParameters())
        {
            if (npa) ret.append(", ");
            ret.append(op.toString());
            npa = true;
        }
        for (Entry<String,Operator> nop : this.getNamedParameters().entrySet())
        {
            if (npa) ret.append(", ");
            ret.append(nop.getKey()).append(" = ").append(nop.getValue());
            npa = true;
        }
        ret.append(")");
        return ret.toString();
    }

    @Override
    public Object get(ExpressContext context, Object source) throws ExpressException
    {
        // load the cached action
        if (this.action == null) this.setupAction(context, source);

        // fetch all the arguments
        Object[] args = new Object[this.actionArguments.size()];
        int i = 0;
        for (Operator op : this.actionArguments)
        {
            args[i++] = op.get(context, source);
        }
        // invoke the action
        try
        {
            return this.action.act(args);
        }
        catch (ExpressException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            throw new ExpressException("Error executing action", e);
        }
    }
    
    private void setupAction(ExpressContext context, Object source) throws ExpressException
    {
        // get the action
        ActionHandler a = context.getAction(this.getName(), source);
        if (a == null) throw new ExpressException("Cannot find action: " + this.getName());
        // map the arguments to the action
        List<Operator> args = new LinkedList<Operator>();
        // some sanity checks
        if ((this.getParameters().size() + this.getNamedParameters().size()) > a.getArguments().size()) throw new ExpressException("To many arguments are provided for action: " + this.getName());
        // firstly append any ordered arguments
        // then append named arguments
        Iterator<Operator> i = this.getParameters().iterator();
        for (ActionArgument aa : a.getArguments())
        {
            if (i.hasNext())
            {
                args.add(i.next());
            }
            else if (this.getNamedParameters().containsKey(aa.getName()))
            {
                args.add(this.getNamedParameters().get(aa.getName()));
            }
            else
            {
                args.add(new NullLiteral());
            }
        }
        // sanity check
        if (args.size() != a.getArguments().size()) throw new ExpressException("Some strange error binding action arguments, for action: " + this.getName());
        // cache
        this.actionArguments = args;
        this.action = a;
    }
    
    @Override
    public boolean isIdempotent()
    {
        return false;
    }
}
