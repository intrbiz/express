package com.intrbiz.express.action;

import static com.intrbiz.Util.isEmpty;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.intrbiz.Util;
import com.intrbiz.collections.Condition;
import com.intrbiz.collections.Mapping;
import com.intrbiz.express.ExpressException;
import com.intrbiz.metadata.Action;
import com.intrbiz.metadata.ArgName;
import com.intrbiz.metadata.ArgNames;

public final class MethodActionHandler implements ActionHandler
{
    private final Object on;

    private final String action;

    private final Method method;

    private final List<ActionArgument> arguments;

    public MethodActionHandler(Object on, String action, Method method)
    {
        super();
        this.on = on;
        this.action = action;
        this.method = method;
        this.arguments = this.parseArguments(method);
    }

    private List<ActionArgument> parseArguments(Method m)
    {
        List<ActionArgument> args = new LinkedList<ActionArgument>();
        if (m.getParameterTypes().length > 0)
        {
            // if there is an @ArgNames annotation use that
            ArgNames names = m.getAnnotation(ArgNames.class);
            if (names != null)
            {
                if (names.value().length != m.getParameterTypes().length)
                {
                    int i = 0;
                    for (Class<?> pt : m.getParameterTypes())
                    {
                        args.add(new ActionArgument(pt, "arg" + (i++)));
                    }
                }
                else
                {
                    int i = 0;
                    for (Class<?> pt : m.getParameterTypes())
                    {
                        args.add(new ActionArgument(pt, names.value()[i++].value()));
                    }
                }
            }
            else
            {
                int i = 0;
                Annotation[][] pa = m.getParameterAnnotations();
                for (Class<?> pt : m.getParameterTypes())
                {
                    ArgName name = (ArgName) Util.first(pa[i], new Condition<Annotation>(){
                        @Override
                        public boolean match(Annotation i)
                        {
                            return i instanceof ArgName;
                        }
                    });
                    args.add(new ActionArgument(pt, name == null ? "arg" + i : name.value()));
                    i++;
                }
            }
        }
        //
        return Collections.unmodifiableList(args);
    }

    @Override
    public List<ActionArgument> getArguments()
    {
        return this.arguments;
    }

    @Override
    public String getName()
    {
        return this.action;
    }

    @Override
    public Object act(Object[] arguments) throws Exception
    {
        try
        {
            return this.method.invoke(this.on, arguments);
        }
        catch (InvocationTargetException e)
        {
            // try to unwrap the exception
            Throwable t = e.getCause();
            if (t instanceof Exception) throw (Exception) e;
            if (t instanceof RuntimeException) throw (RuntimeException) t;
            if (t instanceof Error)
                throw (Error) t;
            else
                throw e;
        }
        catch (IllegalArgumentException e)
        {
            throw new ExpressException("Failed to execute action: " + this.action, e);
        }
        catch (IllegalAccessException e)
        {
            throw new ExpressException("Failed to execute action: " + this.action, e);
        }
    }

    public static List<ActionHandler> findActionHandlers(Object on)
    {
        List<ActionHandler> actions = new LinkedList<ActionHandler>();
        if (on != null)
        {
            for (Method method : on.getClass().getDeclaredMethods())
            {
                if (Modifier.isPublic(method.getModifiers()))
                {
                    Action action = method.getAnnotation(Action.class);
                    if (action != null)
                    {
                        String name = action.value();
                        if (isEmpty(name)) name = method.getName();
                        actions.add(new MethodActionHandler(on, name, method));
                    }
                }
            }
        }
        return actions;
    }
    
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("@");
        sb.append(this.getName());
        sb.append("(");
        sb.append(Util.join(", ", Util.map(this.getArguments(), new Mapping<ActionArgument, String>(){
            @Override
            public String map(ActionArgument input)
            {
                return input.getName();
            }
        })));
        sb.append(")");
        return sb.toString();
    }
}
