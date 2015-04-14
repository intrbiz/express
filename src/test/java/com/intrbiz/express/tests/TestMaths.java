package com.intrbiz.express.tests;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

import com.intrbiz.express.ExpressException;
import com.intrbiz.express.util.ELUtil;

public class TestMaths
{

    @Test
    public void addition() throws ExpressException
    {
        Object o;
        o = ELUtil.parseEL("#{1 + 2}", null).get(null, null);
        //
        assertThat(o, is(instanceOf(Integer.class)));
        assertThat((Integer) o, is(equalTo(3)));
    }
    
    @Test
    public void substraction() throws ExpressException
    {
        Object o;
        o = ELUtil.parseEL("#{3 - 2}", null).get(null, null);
        //
        assertThat(o, is(instanceOf(Integer.class)));
        assertThat((Integer) o, is(equalTo(1)));
        //
        o = ELUtil.parseEL("#{1 - 2}", null).get(null, null);
        //
        assertThat(o, is(instanceOf(Integer.class)));
        assertThat((Integer) o, is(equalTo(-1)));
    }

    @Test
    public void multiplication() throws ExpressException
    {
        Object o;
        o = ELUtil.parseEL("#{2 * 2}", null).get(null, null);
        //
        assertThat(o, is(instanceOf(Integer.class)));
        assertThat((Integer) o, is(equalTo(4)));
    }
    
    @Test
    public void division() throws ExpressException
    {
        Object o;
        o = ELUtil.parseEL("#{6 / 2}", null).get(null, null);
        //
        assertThat(o, is(instanceOf(Integer.class)));
        assertThat((Integer) o, is(equalTo(3)));
    }
    
    @Test
    public void modulo() throws ExpressException
    {
        Object o;
        o = ELUtil.parseEL("#{4 % 2}", null).get(null, null);
        //
        assertThat(o, is(instanceOf(Integer.class)));
        assertThat((Integer) o, is(equalTo(0)));
        o = ELUtil.parseEL("#{4 % 3}", null).get(null, null);
        //
        assertThat(o, is(instanceOf(Integer.class)));
        assertThat((Integer) o, is(equalTo(1)));
    }
    
    @Test
    public void power() throws ExpressException
    {
        Object o;
        o = ELUtil.parseEL("#{2 ^ 2}", null).get(null, null);
        //
        assertThat(o, is(instanceOf(Double.class)));
        assertThat((Double) o, is(equalTo(4D)));
        //
        o = ELUtil.parseEL("#{2 ^ 3}", null).get(null, null);
        //
        assertThat(o, is(instanceOf(Double.class)));
        assertThat((Double) o, is(equalTo(8D)));
    }
    
    @Test
    public void minusNumber() throws ExpressException
    {
        Object o;
        o = ELUtil.parseEL("#{-2}", null).get(null, null);
        //
        assertThat(o, is(instanceOf(Integer.class)));
        assertThat((Integer) o, is(equalTo(-2)));
    }
    
}
