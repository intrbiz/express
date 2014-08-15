package com.intrbiz.express.tests;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.junit.Before;
import org.junit.Test;

import com.intrbiz.express.DefaultContext;
import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.value.ValueExpression;

public class TestEquality
{
    private ExpressContext context;
    
    @Before
    public void setupContext()
    {
        this.context = new DefaultContext();
    }
    
    // equals
    
    @Test
    public void testEq1()
    {
        ValueExpression ve = new ValueExpression(this.context, "#{1 eq 1}");
        Object o = ve.get(this.context, null);
        assertThat(o, is(instanceOf(Boolean.class)));
        assertThat((Boolean) o, is(equalTo(true)));
    }
    
    @Test
    public void testEq2()
    {
        ValueExpression ve = new ValueExpression(this.context, "#{1.2 eq 1.2}");
        Object o = ve.get(this.context, null);
        assertThat(o, is(instanceOf(Boolean.class)));
        assertThat((Boolean) o, is(equalTo(true)));
    }
    
    @Test
    public void testEq3()
    {
        ValueExpression ve = new ValueExpression(this.context, "#{10000L eq 10000L}");
        Object o = ve.get(this.context, null);
        assertThat(o, is(instanceOf(Boolean.class)));
        assertThat((Boolean) o, is(equalTo(true)));
    }
    
    @Test
    public void testEq4()
    {
        ValueExpression ve = new ValueExpression(this.context, "#{1.2D eq 1.2D}");
        Object o = ve.get(this.context, null);
        assertThat(o, is(instanceOf(Boolean.class)));
        assertThat((Boolean) o, is(equalTo(true)));
    }
   
    @Test
    public void testEq5()
    {
        ValueExpression ve = new ValueExpression(this.context, "#{'test' eq 'test'}");
        Object o = ve.get(this.context, null);
        assertThat(o, is(instanceOf(Boolean.class)));
        assertThat((Boolean) o, is(equalTo(true)));
    }
    
    @Test
    public void testEq6()
    {
        ValueExpression ve = new ValueExpression(this.context, "#{1 eq 1L}");
        Object o = ve.get(this.context, null);
        assertThat(o, is(instanceOf(Boolean.class)));
        assertThat((Boolean) o, is(equalTo(true)));
    }
    
    @Test
    public void testEq7()
    {
        ValueExpression ve = new ValueExpression(this.context, "#{1 == 1}");
        Object o = ve.get(this.context, null);
        assertThat(o, is(instanceOf(Boolean.class)));
        assertThat((Boolean) o, is(equalTo(true)));
    }
    
    @Test
    public void testEq8()
    {
        ValueExpression ve = new ValueExpression(this.context, "#{1.2 == 1.2}");
        Object o = ve.get(this.context, null);
        assertThat(o, is(instanceOf(Boolean.class)));
        assertThat((Boolean) o, is(equalTo(true)));
    }
    
    @Test
    public void testEq9()
    {
        ValueExpression ve = new ValueExpression(this.context, "#{10000L == 10000L}");
        Object o = ve.get(this.context, null);
        assertThat(o, is(instanceOf(Boolean.class)));
        assertThat((Boolean) o, is(equalTo(true)));
    }
    
    @Test
    public void testEq10()
    {
        ValueExpression ve = new ValueExpression(this.context, "#{1.2D == 1.2D}");
        Object o = ve.get(this.context, null);
        assertThat(o, is(instanceOf(Boolean.class)));
        assertThat((Boolean) o, is(equalTo(true)));
    }
   
    @Test
    public void testEq11()
    {
        ValueExpression ve = new ValueExpression(this.context, "#{'test' == 'test'}");
        Object o = ve.get(this.context, null);
        assertThat(o, is(instanceOf(Boolean.class)));
        assertThat((Boolean) o, is(equalTo(true)));
    }
    
    @Test
    public void testEq12()
    {
        ValueExpression ve = new ValueExpression(this.context, "#{1 == 1L}");
        Object o = ve.get(this.context, null);
        assertThat(o, is(instanceOf(Boolean.class)));
        assertThat((Boolean) o, is(equalTo(true)));
    }
    
}
