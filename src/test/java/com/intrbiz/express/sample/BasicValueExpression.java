package com.intrbiz.express.sample;

import com.intrbiz.express.AbstractELContext;
import com.intrbiz.express.ELContext;
import com.intrbiz.express.ELException;
import com.intrbiz.express.value.ValueExpression;

public class BasicValueExpression
{
    public static final void main(String[] args) throws ELException
    {
        // Create our ELContext
        ELContext context = new AbstractELContext() {
            @Override
            public Object getEntityInner(String name, Object source)
            {
                // We have no entities to offer up :(
                return null;
            }
        };
        // Parse an expression
        ValueExpression expression = new ValueExpression(context, "#{'Hello' + ' ' + 'World'}");
        // Evaluate the expression
        System.out.println(expression.get(context, null));
    }
}
