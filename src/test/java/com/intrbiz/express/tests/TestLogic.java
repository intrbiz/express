package com.intrbiz.express.tests;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

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
    
    @Test
    public void testAnd5()
    {
        ValueExpression ve = new ValueExpression(this.context, "#{true and true and false and true and false and true}");
        Object o = ve.get(this.context, null);
        assertThat(o, is(instanceOf(Boolean.class)));
        assertThat((Boolean) o, is(equalTo(false)));
    }
    
    @Test
    public void testAnd6()
    {
        ValueExpression ve = new ValueExpression(this.context, "#{true and true and true and true and true and true}");
        Object o = ve.get(this.context, null);
        assertThat(o, is(instanceOf(Boolean.class)));
        assertThat((Boolean) o, is(equalTo(true)));
    }
    
    @Test
    public void testAnd7()
    {
        ValueExpression ve = new ValueExpression(this.context, "#{true && true}");
        Object o = ve.get(this.context, null);
        assertThat(o, is(instanceOf(Boolean.class)));
        assertThat((Boolean) o, is(equalTo(true)));
    }
    
    // or
    
    @Test
    public void testOr1()
    {
        ValueExpression ve = new ValueExpression(this.context, "#{true or true}");
        Object o = ve.get(this.context, null);
        assertThat(o, is(instanceOf(Boolean.class)));
        assertThat((Boolean) o, is(equalTo(true)));
    }
    
    @Test
    public void testOr2()
    {
        ValueExpression ve = new ValueExpression(this.context, "#{true or false}");
        Object o = ve.get(this.context, null);
        assertThat(o, is(instanceOf(Boolean.class)));
        assertThat((Boolean) o, is(equalTo(true)));
    }
    
    @Test
    public void testOr3()
    {
        ValueExpression ve = new ValueExpression(this.context, "#{false or true}");
        Object o = ve.get(this.context, null);
        assertThat(o, is(instanceOf(Boolean.class)));
        assertThat((Boolean) o, is(equalTo(true)));
    }
    
    @Test
    public void testOr4()
    {
        ValueExpression ve = new ValueExpression(this.context, "#{false or false}");
        Object o = ve.get(this.context, null);
        assertThat(o, is(instanceOf(Boolean.class)));
        assertThat((Boolean) o, is(equalTo(false)));
    }
    
    @Test
    public void testOr5()
    {
        ValueExpression ve = new ValueExpression(this.context, "#{true or true or false or true or false or true}");
        Object o = ve.get(this.context, null);
        assertThat(o, is(instanceOf(Boolean.class)));
        assertThat((Boolean) o, is(equalTo(true)));
    }
    
    @Test
    public void testOr6()
    {
        ValueExpression ve = new ValueExpression(this.context, "#{false or false or false or false or false or false}");
        Object o = ve.get(this.context, null);
        assertThat(o, is(instanceOf(Boolean.class)));
        assertThat((Boolean) o, is(equalTo(false)));
    }
    
    @Test
    public void testOr7()
    {
        ValueExpression ve = new ValueExpression(this.context, "#{true || false}");
        Object o = ve.get(this.context, null);
        assertThat(o, is(instanceOf(Boolean.class)));
        assertThat((Boolean) o, is(equalTo(true)));
    }
    
    // precedence
    
    @Test
    public void testCompound1()
    {
        ValueExpression ve = new ValueExpression(this.context, "#{true and true or false}");
        Object o = ve.get(this.context, null);
        assertThat(o, is(instanceOf(Boolean.class)));
        assertThat((Boolean) o, is(equalTo(true)));
    }
    
    @Test
    public void testCompound2()
    {
        ValueExpression ve = new ValueExpression(this.context, "#{true and false or false}");
        Object o = ve.get(this.context, null);
        assertThat(o, is(instanceOf(Boolean.class)));
        assertThat((Boolean) o, is(equalTo(false)));
    }
    
    @Test
    public void testCompound3()
    {
        ValueExpression ve = new ValueExpression(this.context, "#{true or (true or false)}");
        Object o = ve.get(this.context, null);
        assertThat(o, is(instanceOf(Boolean.class)));
        assertThat((Boolean) o, is(equalTo(true)));
    }
    
    @Test
    public void testCompound4()
    {
        ValueExpression ve = new ValueExpression(this.context, "#{true or false and true}");
        Object o = ve.get(this.context, null);
        assertThat(o, is(instanceOf(Boolean.class)));
        assertThat((Boolean) o, is(equalTo(true)));
    }
    
    // not
    
    @Test
    public void testNotTrue()
    {
        ValueExpression ve = new ValueExpression(this.context, "#{! true}");
        Object o = ve.get(this.context, null);
        assertThat(o, is(instanceOf(Boolean.class)));
        assertThat((Boolean) o, is(equalTo(false)));
    }
    
    @Test
    public void testNotTrue2()
    {
        ValueExpression ve = new ValueExpression(this.context, "#{not true}");
        Object o = ve.get(this.context, null);
        assertThat(o, is(instanceOf(Boolean.class)));
        assertThat((Boolean) o, is(equalTo(false)));
    }
    
    @Test
    public void testNotFalse()
    {
        ValueExpression ve = new ValueExpression(this.context, "#{! false}");
        Object o = ve.get(this.context, null);
        assertThat(o, is(instanceOf(Boolean.class)));
        assertThat((Boolean) o, is(equalTo(true)));
    }
    
    @Test
    public void testNotFalse2()
    {
        ValueExpression ve = new ValueExpression(this.context, "#{not false}");
        Object o = ve.get(this.context, null);
        assertThat(o, is(instanceOf(Boolean.class)));
        assertThat((Boolean) o, is(equalTo(true)));
    }
    
    @Test
    public void testNotNotTrue()
    {
        ValueExpression ve = new ValueExpression(this.context, "#{!! true}");
        Object o = ve.get(this.context, null);
        assertThat(o, is(instanceOf(Boolean.class)));
        assertThat((Boolean) o, is(equalTo(true)));
    }
    
    @Test
    public void testNotNotTrue2()
    {
        ValueExpression ve = new ValueExpression(this.context, "#{not not true}");
        Object o = ve.get(this.context, null);
        assertThat(o, is(instanceOf(Boolean.class)));
        assertThat((Boolean) o, is(equalTo(true)));
    }
    
    @Test
    public void testNotNotTrue3()
    {
        ValueExpression ve = new ValueExpression(this.context, "#{not ! true}");
        Object o = ve.get(this.context, null);
        assertThat(o, is(instanceOf(Boolean.class)));
        assertThat((Boolean) o, is(equalTo(true)));
    }
    
    @Test
    public void testNotNotFalse()
    {
        ValueExpression ve = new ValueExpression(this.context, "#{!! false}");
        Object o = ve.get(this.context, null);
        assertThat(o, is(instanceOf(Boolean.class)));
        assertThat((Boolean) o, is(equalTo(false)));
    }
    
    @Test
    public void testNotNotFalse2()
    {
        ValueExpression ve = new ValueExpression(this.context, "#{not not false}");
        Object o = ve.get(this.context, null);
        assertThat(o, is(instanceOf(Boolean.class)));
        assertThat((Boolean) o, is(equalTo(false)));
    }
    
    @Test
    public void testNotNotFalse3()
    {
        ValueExpression ve = new ValueExpression(this.context, "#{! not false}");
        Object o = ve.get(this.context, null);
        assertThat(o, is(instanceOf(Boolean.class)));
        assertThat((Boolean) o, is(equalTo(false)));
    }
}
