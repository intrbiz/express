package com.intrbiz.express.tests;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.Test;

import com.intrbiz.express.ExpressException;
import com.intrbiz.express.util.ELUtil;

public class TestLitterals
{

    @Test
    public void litteralInteger() throws ExpressException
    {
        Object o;
        o = ELUtil.parseEL("#{1234}", null).get(null, null);
        //
        assertThat(o, is(instanceOf(Integer.class)));
        assertThat((Integer) o, is(equalTo(1234)));
    }

    @Test
    public void litteralFloat() throws ExpressException
    {
        Object o;
        o = ELUtil.parseEL("#{1234.567}", null).get(null, null);
        //
        assertThat(o, is(instanceOf(Float.class)));
        assertThat((Float) o, is(equalTo(1234.567F)));
    }

    @Test
    public void litteralLong() throws ExpressException
    {
        Object o;
        o = ELUtil.parseEL("#{1234L}", null).get(null, null);
        //
        assertThat(o, is(instanceOf(Long.class)));
        assertThat((Long) o, is(equalTo(1234L)));
    }
    
    @Test
    public void litteralDouble() throws ExpressException
    {
        Object o;
        o = ELUtil.parseEL("#{1234.567D}", null).get(null, null);
        //
        assertThat(o, is(instanceOf(Double.class)));
        assertThat( (Double) o, is(equalTo(1234.567D)));
    }
    
    @Test
    public void litteralString() throws ExpressException
    {
        Object o;
        o = ELUtil.parseEL("#{'test'}", null).get(null, null);
        //
        assertThat(o, is(instanceOf(String.class)));
        assertThat( (String) o, is(equalTo("test")));
        //
        o = ELUtil.parseEL("#{'test \\'test\\''}", null).get(null, null);
        //
        assertThat(o, is(instanceOf(String.class)));
        assertThat( (String) o, is(equalTo("test 'test'")));
    }

    @Test
    public void litteralNull() throws ExpressException
    {
        Object o = ELUtil.parseEL("#{null}", null).get(null, null);
        assertThat(o, is(nullValue()));
    }
    
}
