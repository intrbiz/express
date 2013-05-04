package com.intrbiz.express.sample;

import com.intrbiz.express.DefaultContext;
import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;
import com.intrbiz.express.value.ValueExpression;

public class BasicValueExpression
{
    public static final void main(String[] args) throws ExpressException
    {
        // Create our ELContext
        ExpressContext context = new DefaultContext();
        // Parse an expression
        ValueExpression expression = new ValueExpression(context, "#{'Hello' + ' ' + 'World'}");
        // Evaluate the expression
        System.out.println(expression.get(context, null));
    }
}
