package com.intrbiz.express.tests;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.intrbiz.express.DefaultContext;
import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.operator.Operator;
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
    
    @Test()
    public void testSomeBoole()
    {
        Object o = ELUtil.parseEL("#{bean.someBool}", this.context).get(context, null);
        assertThat(o, is(instanceOf(Boolean.class)));
        assertThat((Boolean) o, is(equalTo(true)));
    }
    
    @Test()
    public void testGetSetSomeProperty()
    {
        Operator op = ELUtil.parseEL("#{bean.someProperty}", this.context);
        // get
        Object a = op.get(context, null);
        assertThat(a, is(instanceOf(String.class)));
        assertThat((String) a, is(equalTo("Has a value")));
        // set
        op.set(context, "Some different value", null);
        // get again
        Object b = op.get(context, null);
        assertThat(b, is(instanceOf(String.class)));
        assertThat((String) b, is(equalTo("Some different value")));
    }
    
    @Test()
    public void testBuiltinArrays()
    {
        Object o = ELUtil.parseEL("#{Arrays}", this.context).get(context, null);
        assertThat(o, is(equalTo(Arrays.class)));
    }
}
