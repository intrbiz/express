package com.intrbiz.express.tests;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.intrbiz.express.DefaultContext;
import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;
import com.intrbiz.express.util.ELUtil;

public class TestObjectLiterals
{
    private ExpressContext context;
    
    @Before
    public void setup()
    {
        this.context = new DefaultContext();
    }

    @Test
    public void testLiteralEmptyList() throws ExpressException
    {
        Object o;
        o = ELUtil.parseEL("#{ [] }", context).get(context, null);
        //
        assertThat(o, is(instanceOf(List.class)));
        assertThat(o, is(equalTo(new ArrayList<Object>())));
    }
    
    @Test
    public void testLiteralOneEntryList() throws ExpressException
    {
        Object o;
        o = ELUtil.parseEL("#{ [ 1 ] }", context).get(context, null);
        //
        assertThat(o, is(instanceOf(List.class)));
        assertThat(o, is(equalTo(Arrays.asList(1))));
    }
    
    @Test
    public void testLiteralList() throws ExpressException
    {
        Object o;
        o = ELUtil.parseEL("#{ [ 1, '2', 1 + 2, false ] }", context).get(context, null);
        //
        assertThat(o, is(instanceOf(List.class)));
        assertThat(o, is(equalTo(Arrays.asList(1, "2", 3, false))));
    }

    @Test
    public void testLiteralEmptyMap() throws ExpressException
    {
        Object o;
        o = ELUtil.parseEL("#{ {} }", context).get(context, null);
        //
        assertThat(o, is(instanceOf(LinkedHashMap.class)));
        assertThat(o, is(equalTo(new LinkedHashMap<String, Object>())));
    }
    
    @Test
    public void testLiteralOneEntryMap() throws ExpressException
    {
        Object o;
        o = ELUtil.parseEL("#{ { one: 1 } }", context).get(context, null);
        //
        assertThat(o, is(instanceOf(LinkedHashMap.class)));
        assertThat(((Map)o).get("one"), is(equalTo(1)));
    }
    
    @Test
    public void testLiteralMap() throws ExpressException
    {
        Object o;
        o = ELUtil.parseEL("#{ { one: 1, 'Two': 2, '3': 'Three', _test: 'Testing', 'testing testing': true, 'add': 1 + 2 } }", context).get(context, null);
        //
        assertThat(o, is(instanceOf(LinkedHashMap.class)));
        assertThat(((Map)o).get("one"), is(equalTo(1)));
        assertThat(((Map)o).get("Two"), is(equalTo(2)));
        assertThat(((Map)o).get("3"), is(equalTo("Three")));
        assertThat(((Map)o).get("_test"), is(equalTo("Testing")));
        assertThat(((Map)o).get("testing testing"), is(equalTo(true)));
        assertThat(((Map)o).get("add"), is(equalTo(3)));
    }
}
