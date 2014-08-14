package com.intrbiz.express.tests;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.intrbiz.express.DefaultContext;
import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.tests.model.SomeBean;
import com.intrbiz.express.util.ELUtil;

public class TestEntities
{
    private ExpressContext context;
    
    @Before()
    public void setupContext()
    {
        this.context = new DefaultContext();
        this.context.setEntity("bean", new SomeBean(), null);
    }
    
    @Test()
    public void testGetBean()
    {
        Object o = ELUtil.parseEL("#{bean}", this.context).get(context, null);
        assertThat(o, is(instanceOf(SomeBean.class)));
    }
    
    @Test()
    public void testGetSomeProperty()
    {
        Object o = ELUtil.parseEL("#{bean.someProperty}", this.context).get(context, null);
        assertThat(o, is(instanceOf(String.class)));
        assertThat( (String) o, is(equalTo("Has a value")));
    }
    
    @Test()
    public void testDoSomeThing()
    {
        Object o = ELUtil.parseEL("#{bean.doSomeThing()}", this.context).get(context, null);
        assertThat(o, is(instanceOf(String.class)));
        assertThat( (String) o, is(equalTo("Done")));
    }
    
    @Test()
    public void testGetSomeMap()
    {
        Object o = ELUtil.parseEL("#{bean.someMap}", this.context).get(context, null);
        assertThat(o, is(instanceOf(Map.class)));
    }
    
    @Test()
    public void testSomeMapGetNameA()
    {
        Object o = ELUtil.parseEL("#{bean.someMap.name_a}", this.context).get(context, null);
        assertThat(o, is(instanceOf(String.class)));
        assertThat( (String) o, is(equalTo("Value A")));
    }
    
    @Test()
    public void testSomeMapGetNameB()
    {
        Object o = ELUtil.parseEL("#{bean.someMap['name b']}", this.context).get(context, null);
        assertThat(o, is(instanceOf(String.class)));
        assertThat( (String) o, is(equalTo("Value B")));
    }
}
