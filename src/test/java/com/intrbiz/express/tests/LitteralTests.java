package com.intrbiz.express.tests;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.Test;

import com.intrbiz.express.ELException;
import com.intrbiz.express.util.ELUtil;

public class LitteralTests
{

    @Test
    public void litteralInteger() throws ELException
    {
        Object o;
        o = ELUtil.parseEL("#{1234}", null).get(null, null);
        //
        assertThat(o, is(instanceOf(Integer.class)));
        assertThat((Integer) o, is(equalTo(1234)));
    }

    @Test
    public void litteralFloat() throws ELException
    {
        Object o;
        o = ELUtil.parseEL("#{1234.567}", null).get(null, null);
        //
        assertThat(o, is(instanceOf(Float.class)));
        assertThat((Float) o, is(equalTo(1234.567F)));
    }

    @Test
    public void litteralLong() throws ELException
    {
        Object o;
        o = ELUtil.parseEL("#{1234L}", null).get(null, null);
        //
        assertThat(o, is(instanceOf(Long.class)));
        assertThat((Long) o, is(equalTo(1234L)));
    }
    
    @Test
    public void litteralDouble() throws ELException
    {
        Object o;
        o = ELUtil.parseEL("#{1234.567}", null).get(null, null);
        //
        assertThat(o, is(instanceOf(Double.class)));
        assertThat( (Double) o, is(equalTo(1234.567D)));
    }
    
    @Test
    public void litteralString() throws ELException
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

}
