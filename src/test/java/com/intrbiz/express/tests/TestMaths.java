package com.intrbiz.express.tests;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.intrbiz.express.DefaultContext;
import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;
import com.intrbiz.express.util.ELUtil;

public class TestMaths
{
    private ExpressContext context;
    
    @Before
    public void setup()
    {
        this.context = new DefaultContext();
    }

    @Test
    public void addition() throws ExpressException
    {
        Object o;
        o = ELUtil.parseEL("#{1 + 2}", context).get(context, null);
        //
        assertThat(o, is(instanceOf(Integer.class)));
        assertThat((Integer) o, is(equalTo(3)));
    }
    
    @Test
    public void substraction() throws ExpressException
    {
        Object o;
        o = ELUtil.parseEL("#{3 - 2}", context).get(context, null);
        //
        assertThat(o, is(instanceOf(Integer.class)));
        assertThat((Integer) o, is(equalTo(1)));
        //
        o = ELUtil.parseEL("#{1 - 2}", context).get(context, null);
        //
        assertThat(o, is(instanceOf(Integer.class)));
        assertThat((Integer) o, is(equalTo(-1)));
    }

    @Test
    public void multiplication() throws ExpressException
    {
        Object o;
        o = ELUtil.parseEL("#{2 * 2}", context).get(context, null);
        //
        assertThat(o, is(instanceOf(Integer.class)));
        assertThat((Integer) o, is(equalTo(4)));
    }
    
    @Test
    public void division() throws ExpressException
    {
        Object o;
        o = ELUtil.parseEL("#{6 / 2}", context).get(context, null);
        //
        assertThat(o, is(instanceOf(Integer.class)));
        assertThat((Integer) o, is(equalTo(3)));
    }
    
    @Test
    public void modulo() throws ExpressException
    {
        Object o;
        o = ELUtil.parseEL("#{4 % 2}", context).get(context, null);
        //
        assertThat(o, is(instanceOf(Integer.class)));
        assertThat((Integer) o, is(equalTo(0)));
        o = ELUtil.parseEL("#{4 % 3}", context).get(context, null);
        //
        assertThat(o, is(instanceOf(Integer.class)));
        assertThat((Integer) o, is(equalTo(1)));
    }
    
    @Test
    public void power() throws ExpressException
    {
        Object o;
        o = ELUtil.parseEL("#{2 ^ 2}", context).get(context, null);
        //
        assertThat(o, is(instanceOf(Double.class)));
        assertThat((Double) o, is(equalTo(4D)));
        //
        o = ELUtil.parseEL("#{2 ^ 3}", context).get(context, null);
        //
        assertThat(o, is(instanceOf(Double.class)));
        assertThat((Double) o, is(equalTo(8D)));
    }
    
    @Test
    public void minusNumber() throws ExpressException
    {
        Object o;
        o = ELUtil.parseEL("#{-2}", context).get(context, null);
        //
        assertThat(o, is(instanceOf(Integer.class)));
        assertThat(o, is(equalTo(-2)));
    }
    
    @Test
    public void testMathExpressionVsJava1() throws ExpressException
    {
        Object o;
        o = ELUtil.parseEL("#{ -2 * 2 - 4 + 10 - 10 / 2 * 2  }", context).get(context, null);        
        //
        assertThat(o, is(instanceOf(Integer.class)));
        assertThat(o, is(equalTo(-2 * 2 - 4 + 10 - 10 / 2 * 2)));
    }
    
    @Test
    public void testMathExpressionVsJava2() throws ExpressException
    {
        Object o;
        o = ELUtil.parseEL("#{ -3 * -3 + 4  }", context).get(context, null);
        //
        assertThat(o, is(instanceOf(Integer.class)));
        assertThat(o, is(equalTo(-3 * -3 + 4)));
    }
    
    @Test
    public void testMathExpressionVsJava3() throws ExpressException
    {
        Object o;
        o = ELUtil.parseEL("#{ -(1 + 2) * -(1 + 2) + 4  }", context).get(context, null);
        //
        assertThat(o, is(instanceOf(Integer.class)));
        assertThat(o, is(equalTo(-(1 + 2) * -(1 + 2) + 4)));
    }
    
    @Test
    public void testMathExpressionVsJava4() throws ExpressException
    {
        Object o;
        o = ELUtil.parseEL("#{ -(1 + 2) |^ 2 + 4  }", context).get(context, null);
        //
        assertThat(o, is(instanceOf(Integer.class)));
        assertThat(o, is(equalTo(-(1 + 2) ^ 2 + 4)));
    }
    
    @Test
    public void testMinusPower() throws ExpressException
    {
        Object o;
        o = ELUtil.parseEL("#{ -2^2 }", context).get(context, null);        
        //
        assertThat(o, is(instanceOf(Double.class)));
        assertThat(o, is(equalTo(4D)));
    }
    
    @Test
    public void testPrecedence1() throws ExpressException
    {
        Object o;
        o = ELUtil.parseEL("#{ -(1+2)^2 + 4 }", context).get(context, null);
        //
        assertThat(o, is(instanceOf(Double.class)));
        assertThat(o, is(equalTo(13.0D)));
    }
    
    @Test
    public void testPrecedence2() throws ExpressException
    {
        Object o;
        o = ELUtil.parseEL("#{ 0 - (1+2)^2 + 4 }", context).get(context, null);
        //
        assertThat(o, is(instanceOf(Double.class)));
        assertThat(o, is(equalTo(-5.0D)));
    }
    
    @Test
    public void testPrecedence3() throws ExpressException
    {
        Object o;
        o = ELUtil.parseEL("#{ -1 * (1+2)^2 + 4 }", context).get(context, null);
        //
        assertThat(o, is(instanceOf(Double.class)));
        assertThat(o, is(equalTo(-5.0D)));
    }
}
