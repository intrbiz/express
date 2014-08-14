package com.intrbiz.express.tests;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.junit.Before;
import org.junit.Test;

import com.intrbiz.express.DefaultContext;
import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.value.ValueExpression;

public class TestValueExpression
{
    private ExpressContext context;
    
    @Before
    public void setupContext()
    {
        this.context = new DefaultContext();
    }
    
    @Test()
    public void testBasicValueExpression()
    {
        // Parse an expression
        ValueExpression expression = new ValueExpression(context, "#{'Hello' + ' ' + 'World'}");
        //
        Object o = expression.get(context, null);
        // Evaluate the expression
        assertThat(o, is(instanceOf(String.class)));
        assertThat((String) o, is(equalTo("Hello World")));
    }
}
