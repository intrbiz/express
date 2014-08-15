package com.intrbiz.express.tests;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.junit.Before;
import org.junit.Test;

import com.intrbiz.express.DefaultContext;
import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.value.ValueExpression;

public class TestLogic
{
    private ExpressContext context;
    
    @Before
    public void setupContext()
    {
        this.context = new DefaultContext();
    }
    
    // and
    
    @Test
    public void testAnd1()
    {
        ValueExpression ve = new ValueExpression(this.context, "#{true and true}");
        Object o = ve.get(this.context, null);
        assertThat(o, is(instanceOf(Boolean.class)));
        assertThat((Boolean) o, is(equalTo(true)));
    }
    
    @Test
    public void testAnd2()
    {
        ValueExpression ve = new ValueExpression(this.context, "#{true and false}");
        Object o = ve.get(this.context, null);
        assertThat(o, is(instanceOf(Boolean.class)));
        assertThat((Boolean) o, is(equalTo(false)));
    }
    
    @Test
    public void testAnd3()
    {
        ValueExpression ve = new ValueExpression(this.context, "#{false and true}");
        Object o = ve.get(this.context, null);
        assertThat(o, is(instanceOf(Boolean.class)));
        assertThat((Boolean) o, is(equalTo(false)));
    }
    
    @Test
    public void testAnd4()
    {
        ValueExpression ve = new ValueExpression(this.context, "#{false and false}");
        Object o = ve.get(this.context, null);
        assertThat(o, is(instanceOf(Boolean.class)));
        assertThat((Boolean) o, is(equalTo(false)));
    }
    
}
