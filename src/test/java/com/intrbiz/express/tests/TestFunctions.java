package com.intrbiz.express.tests;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import com.intrbiz.express.DefaultContext;
import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.value.ValueExpression;

public class TestFunctions
{
    private ExpressContext context;
    
    @Before
    public void setupContext()
    {
        this.context = new DefaultContext();
    }
    
    @Test
    public void testCoalesce()
    {
        ValueExpression ve = new ValueExpression(this.context, "#{coalesce(null, some.entity, 'C')}");
        Object o = ve.get(this.context, null);
        assertThat(o, is(instanceOf(String.class)));
        assertThat((String) o, is(equalTo("C")));
    }
    
    @Test
    public void testGetUUID()
    {
        ValueExpression ve = new ValueExpression(this.context, "#{getuuid()}");
        Object o = ve.get(this.context, null);
        assertThat(o, is(instanceOf(UUID.class)));
    }
    
    @Test
    public void testIsNullTrue()
    {
        ValueExpression ve = new ValueExpression(this.context, "#{isnull(null)}");
        Object o = ve.get(this.context, null);
        assertThat(o, is(instanceOf(Boolean.class)));
        assertThat((Boolean) o, is(equalTo(true)));
    }
    
    @Test
    public void testIsNullFalse()
    {
        ValueExpression ve = new ValueExpression(this.context, "#{isnull('test')}");
        Object o = ve.get(this.context, null);
        assertThat(o, is(instanceOf(Boolean.class)));
        assertThat((Boolean) o, is(equalTo(false)));
    }
    
    @Test
    public void testIsEmptyNullTrue()
    {
        ValueExpression ve = new ValueExpression(this.context, "#{isempty(null)}");
        Object o = ve.get(this.context, null);
        assertThat(o, is(instanceOf(Boolean.class)));
        assertThat((Boolean) o, is(equalTo(true)));
    }

    @Test
    public void testIsEmptyStringTrue()
    {
        ValueExpression ve = new ValueExpression(this.context, "#{isempty('')}");
        Object o = ve.get(this.context, null);
        assertThat(o, is(instanceOf(Boolean.class)));
        assertThat((Boolean) o, is(equalTo(true)));
    }
    
    @Test
    public void testIsEmptyStringFalse()
    {
        ValueExpression ve = new ValueExpression(this.context, "#{isempty('blah')}");
        Object o = ve.get(this.context, null);
        assertThat(o, is(instanceOf(Boolean.class)));
        assertThat((Boolean) o, is(equalTo(false)));
    }
    
    @Test
    public void testIsEmptyCollectionTrue()
    {
        this.context.setEntity("alist", new LinkedList<String>(), null);
        ValueExpression ve = new ValueExpression(this.context, "#{isempty(alist)}");
        Object o = ve.get(this.context, null);
        assertThat(o, is(instanceOf(Boolean.class)));
        assertThat((Boolean) o, is(equalTo(true)));
    }
    
    @Test
    public void testIsEmptyCollectionFalse()
    {
        this.context.setEntity("alist", Arrays.asList("A", "B", "C"), null);
        ValueExpression ve = new ValueExpression(this.context, "#{isempty('blah')}");
        Object o = ve.get(this.context, null);
        assertThat(o, is(instanceOf(Boolean.class)));
        assertThat((Boolean) o, is(equalTo(false)));
    }
    
    @Test
    public void testIsEmptyMapTrue()
    {
        this.context.setEntity("alist", new HashMap<String, String>(), null);
        ValueExpression ve = new ValueExpression(this.context, "#{isempty(alist)}");
        Object o = ve.get(this.context, null);
        assertThat(o, is(instanceOf(Boolean.class)));
        assertThat((Boolean) o, is(equalTo(true)));
    }
    
    @Test
    public void testIsEmptyMapFalse()
    {
        Map<String, String> map = new HashMap<String, String>();
        map.put("A", "1");
        map.put("B", "2");
        this.context.setEntity("alist", map, null);
        ValueExpression ve = new ValueExpression(this.context, "#{isempty('blah')}");
        Object o = ve.get(this.context, null);
        assertThat(o, is(instanceOf(Boolean.class)));
        assertThat((Boolean) o, is(equalTo(false)));
    }
    
    @Test
    public void testURLEncode()
    {
        ValueExpression ve = new ValueExpression(this.context, "#{urlencode('Some /:/@ value to encode')}");
        Object o = ve.get(this.context, null);
        assertThat(o, is(instanceOf(String.class)));
        assertThat((String) o, is(equalTo("Some+%2F%3A%2F%40+value+to+encode")));
    }
    
    @Test
    public void testURLDecode()
    {
        ValueExpression ve = new ValueExpression(this.context, "#{urldecode('Some%20%2F%3A%2F%40%20value%20to%20encode')}");
        Object o = ve.get(this.context, null);
        assertThat(o, is(instanceOf(String.class)));
        assertThat((String) o, is(equalTo("Some /:/@ value to encode")));
    }
}
