package com.intrbiz.express.tests;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

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
}
