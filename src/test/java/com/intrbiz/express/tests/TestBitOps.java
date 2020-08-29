package com.intrbiz.express.tests;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.intrbiz.express.DefaultContext;
import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;
import com.intrbiz.express.util.ELUtil;

public class TestBitOps
{
    private ExpressContext context;
    
    @Before
    public void setup()
    {
        this.context = new DefaultContext();
    }

    @Test
    public void testOr() throws ExpressException
    {
        Object o;
        o = ELUtil.parseEL("#{1 | 2}", context).get(context, null);
        //
        assertThat(o, is(equalTo(3)));
    }
    
    @Test
    public void testXor() throws ExpressException
    {
        Object o;
        o = ELUtil.parseEL("#{2 |^ 6}", context).get(context, null);
        //
        assertThat(o, is(equalTo(4)));
    }
    
    @Test
    public void testAnd() throws ExpressException
    {
        Object o;
        o = ELUtil.parseEL("#{6 & 2}", context).get(context, null);
        //
        assertThat(o, is(equalTo(2)));
    }
    
    @Test
    public void testBitopsLikeJava1() throws ExpressException
    {
        Object o;
        o = ELUtil.parseEL("#{ 1 | 2 & 7 }", context).get(context, null);
        //
        assertThat(o, is(equalTo( 1 | 2 & 7 )));
    }
    
    @Test
    public void testBitopsLikeJava2() throws ExpressException
    {
        Object o;
        o = ELUtil.parseEL("#{ 1 | 2 |^ 3 }", context).get(context, null);
        //
        assertThat(o, is(equalTo( 1 | 2 ^ 3 )));
    }
    
    @Test
    public void testBitopsLikeJava3() throws ExpressException
    {
        Object o;
        o = ELUtil.parseEL("#{ 1 | 2 |^ 3 & 8 }", context).get(context, null);
        //
        assertThat(o, is(equalTo( 1 | 2 ^ 3 & 8)));
    }
    
    @Test
    public void testBitShiftLeft() throws ExpressException
    {
        Object o;
        o = ELUtil.parseEL("#{ 1 << 4 }", context).get(context, null);
        //
        assertThat(o, is(equalTo(16)));
    }
    
    @Test
    public void testBitShiftRight() throws ExpressException
    {
        Object o;
        o = ELUtil.parseEL("#{ 16 >> 4 }", context).get(context, null);
        //
        assertThat(o, is(equalTo(1)));
    }
    
    @Test
    public void testBitShiftURight() throws ExpressException
    {
        Object o;
        o = ELUtil.parseEL("#{ 16 >>> 4 }", context).get(context, null);
        //
        assertThat(o, is(equalTo(1)));
    }
}
