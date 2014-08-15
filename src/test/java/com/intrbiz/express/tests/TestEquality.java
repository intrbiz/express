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
    
    // not equal
    
    @Test
    public void testNe1()
    {
        ValueExpression ve = new ValueExpression(this.context, "#{1 ne 1}");
        Object o = ve.get(this.context, null);
        assertThat(o, is(instanceOf(Boolean.class)));
        assertThat((Boolean) o, is(equalTo(false)));
    }
    
    @Test
    public void testNe2()
    {
        ValueExpression ve = new ValueExpression(this.context, "#{1.2 ne 1.2}");
        Object o = ve.get(this.context, null);
        assertThat(o, is(instanceOf(Boolean.class)));
        assertThat((Boolean) o, is(equalTo(false)));
    }
    
    @Test
    public void testNe3()
    {
        ValueExpression ve = new ValueExpression(this.context, "#{10000L ne 10000L}");
        Object o = ve.get(this.context, null);
        assertThat(o, is(instanceOf(Boolean.class)));
        assertThat((Boolean) o, is(equalTo(false)));
    }
    
    @Test
    public void testNe4()
    {
        ValueExpression ve = new ValueExpression(this.context, "#{1.2D ne 1.2D}");
        Object o = ve.get(this.context, null);
        assertThat(o, is(instanceOf(Boolean.class)));
        assertThat((Boolean) o, is(equalTo(false)));
    }
   
    @Test
    public void testNe5()
    {
        ValueExpression ve = new ValueExpression(this.context, "#{'test' ne 'test'}");
        Object o = ve.get(this.context, null);
        assertThat(o, is(instanceOf(Boolean.class)));
        assertThat((Boolean) o, is(equalTo(false)));
    }
    
    @Test
    public void testNe6()
    {
        ValueExpression ve = new ValueExpression(this.context, "#{1 ne 1L}");
        Object o = ve.get(this.context, null);
        assertThat(o, is(instanceOf(Boolean.class)));
        assertThat((Boolean) o, is(equalTo(false)));
    }
    
    @Test
    public void testNe7()
    {
        ValueExpression ve = new ValueExpression(this.context, "#{1 != 1}");
        Object o = ve.get(this.context, null);
        assertThat(o, is(instanceOf(Boolean.class)));
        assertThat((Boolean) o, is(equalTo(false)));
    }
    
    @Test
    public void testNe8()
    {
        ValueExpression ve = new ValueExpression(this.context, "#{1.2 != 1.2}");
        Object o = ve.get(this.context, null);
        assertThat(o, is(instanceOf(Boolean.class)));
        assertThat((Boolean) o, is(equalTo(false)));
    }
    
    @Test
    public void testNe9()
    {
        ValueExpression ve = new ValueExpression(this.context, "#{10000L != 10000L}");
        Object o = ve.get(this.context, null);
        assertThat(o, is(instanceOf(Boolean.class)));
        assertThat((Boolean) o, is(equalTo(false)));
    }
    
    @Test
    public void testNe10()
    {
        ValueExpression ve = new ValueExpression(this.context, "#{1.2D != 1.2D}");
        Object o = ve.get(this.context, null);
        assertThat(o, is(instanceOf(Boolean.class)));
        assertThat((Boolean) o, is(equalTo(false)));
    }
   
    @Test
    public void testNe11()
    {
        ValueExpression ve = new ValueExpression(this.context, "#{'test' != 'test'}");
        Object o = ve.get(this.context, null);
        assertThat(o, is(instanceOf(Boolean.class)));
        assertThat((Boolean) o, is(equalTo(false)));
    }
    
    @Test
    public void testNe12()
    {
        ValueExpression ve = new ValueExpression(this.context, "#{1 != 1L}");
        Object o = ve.get(this.context, null);
        assertThat(o, is(instanceOf(Boolean.class)));
        assertThat((Boolean) o, is(equalTo(false)));
    }
    
    // lt
    
    @Test
    public void testLt1()
    {
        ValueExpression ve = new ValueExpression(this.context, "#{1 < 2}");
        Object o = ve.get(this.context, null);
        assertThat(o, is(instanceOf(Boolean.class)));
        assertThat((Boolean) o, is(equalTo(true)));
    }
    
    @Test
    public void testLt2()
    {
        ValueExpression ve = new ValueExpression(this.context, "#{1 < 2L}");
        Object o = ve.get(this.context, null);
        assertThat(o, is(instanceOf(Boolean.class)));
        assertThat((Boolean) o, is(equalTo(true)));
    }
    
    @Test
    public void testLt3()
    {
        ValueExpression ve = new ValueExpression(this.context, "#{1L < 2L}");
        Object o = ve.get(this.context, null);
        assertThat(o, is(instanceOf(Boolean.class)));
        assertThat((Boolean) o, is(equalTo(true)));
    }
    
    @Test
    public void testLt4()
    {
        ValueExpression ve = new ValueExpression(this.context, "#{-1 < 2}");
        Object o = ve.get(this.context, null);
        assertThat(o, is(instanceOf(Boolean.class)));
        assertThat((Boolean) o, is(equalTo(true)));
    }
    
    @Test
    public void testLt5()
    {
        ValueExpression ve = new ValueExpression(this.context, "#{2 < 1}");
        Object o = ve.get(this.context, null);
        assertThat(o, is(instanceOf(Boolean.class)));
        assertThat((Boolean) o, is(equalTo(false)));
    }
    
    @Test
    public void testLt6()
    {
        ValueExpression ve = new ValueExpression(this.context, "#{-2 < -1}");
        Object o = ve.get(this.context, null);
        assertThat(o, is(instanceOf(Boolean.class)));
        assertThat((Boolean) o, is(equalTo(true)));
    }
    
    @Test
    public void testLt7()
    {
        ValueExpression ve = new ValueExpression(this.context, "#{1 lt 2}");
        Object o = ve.get(this.context, null);
        assertThat(o, is(instanceOf(Boolean.class)));
        assertThat((Boolean) o, is(equalTo(true)));
    }
    
    @Test
    public void testLt8()
    {
        ValueExpression ve = new ValueExpression(this.context, "#{1 lt 2L}");
        Object o = ve.get(this.context, null);
        assertThat(o, is(instanceOf(Boolean.class)));
        assertThat((Boolean) o, is(equalTo(true)));
    }
    
    @Test
    public void testLt9()
    {
        ValueExpression ve = new ValueExpression(this.context, "#{1L lt 2L}");
        Object o = ve.get(this.context, null);
        assertThat(o, is(instanceOf(Boolean.class)));
        assertThat((Boolean) o, is(equalTo(true)));
    }
    
    @Test
    public void testLt10()
    {
        ValueExpression ve = new ValueExpression(this.context, "#{-1 lt 2}");
        Object o = ve.get(this.context, null);
        assertThat(o, is(instanceOf(Boolean.class)));
        assertThat((Boolean) o, is(equalTo(true)));
    }
    
    @Test
    public void testLt11()
    {
        ValueExpression ve = new ValueExpression(this.context, "#{2 lt 1}");
        Object o = ve.get(this.context, null);
        assertThat(o, is(instanceOf(Boolean.class)));
        assertThat((Boolean) o, is(equalTo(false)));
    }
    
    @Test
    public void testLt12()
    {
        ValueExpression ve = new ValueExpression(this.context, "#{-2 lt -1}");
        Object o = ve.get(this.context, null);
        assertThat(o, is(instanceOf(Boolean.class)));
        assertThat((Boolean) o, is(equalTo(true)));
    }
    
    // gt
    
    @Test
    public void testGt1()
    {
        ValueExpression ve = new ValueExpression(this.context, "#{2 > 1}");
        Object o = ve.get(this.context, null);
        assertThat(o, is(instanceOf(Boolean.class)));
        assertThat((Boolean) o, is(equalTo(true)));
    }
    
    @Test
    public void testGt2()
    {
        ValueExpression ve = new ValueExpression(this.context, "#{2 > 1L}");
        Object o = ve.get(this.context, null);
        assertThat(o, is(instanceOf(Boolean.class)));
        assertThat((Boolean) o, is(equalTo(true)));
    }
    
    @Test
    public void testGt3()
    {
        ValueExpression ve = new ValueExpression(this.context, "#{10L > 2L}");
        Object o = ve.get(this.context, null);
        assertThat(o, is(instanceOf(Boolean.class)));
        assertThat((Boolean) o, is(equalTo(true)));
    }
    
    @Test
    public void testGt4()
    {
        ValueExpression ve = new ValueExpression(this.context, "#{1 > -2}");
        Object o = ve.get(this.context, null);
        assertThat(o, is(instanceOf(Boolean.class)));
        assertThat((Boolean) o, is(equalTo(true)));
    }
    
    @Test
    public void testGt5()
    {
        ValueExpression ve = new ValueExpression(this.context, "#{1 > 2}");
        Object o = ve.get(this.context, null);
        assertThat(o, is(instanceOf(Boolean.class)));
        assertThat((Boolean) o, is(equalTo(false)));
    }
    
    @Test
    public void testGt6()
    {
        ValueExpression ve = new ValueExpression(this.context, "#{-1 > -2}");
        Object o = ve.get(this.context, null);
        assertThat(o, is(instanceOf(Boolean.class)));
        assertThat((Boolean) o, is(equalTo(true)));
    }
    
    @Test
    public void testGt7()
    {
        ValueExpression ve = new ValueExpression(this.context, "#{2 gt 1}");
        Object o = ve.get(this.context, null);
        assertThat(o, is(instanceOf(Boolean.class)));
        assertThat((Boolean) o, is(equalTo(true)));
    }
    
    @Test
    public void testGt8()
    {
        ValueExpression ve = new ValueExpression(this.context, "#{2 gt 1L}");
        Object o = ve.get(this.context, null);
        assertThat(o, is(instanceOf(Boolean.class)));
        assertThat((Boolean) o, is(equalTo(true)));
    }
    
    @Test
    public void testGt9()
    {
        ValueExpression ve = new ValueExpression(this.context, "#{10L gt 2L}");
        Object o = ve.get(this.context, null);
        assertThat(o, is(instanceOf(Boolean.class)));
        assertThat((Boolean) o, is(equalTo(true)));
    }
    
    @Test
    public void testGt10()
    {
        ValueExpression ve = new ValueExpression(this.context, "#{1 gt -2}");
        Object o = ve.get(this.context, null);
        assertThat(o, is(instanceOf(Boolean.class)));
        assertThat((Boolean) o, is(equalTo(true)));
    }
    
    @Test
    public void testGt11()
    {
        ValueExpression ve = new ValueExpression(this.context, "#{1 gt 2}");
        Object o = ve.get(this.context, null);
        assertThat(o, is(instanceOf(Boolean.class)));
        assertThat((Boolean) o, is(equalTo(false)));
    }
    
    @Test
    public void testGt12()
    {
        ValueExpression ve = new ValueExpression(this.context, "#{-1 gt -2}");
        Object o = ve.get(this.context, null);
        assertThat(o, is(instanceOf(Boolean.class)));
        assertThat((Boolean) o, is(equalTo(true)));
    }

}
