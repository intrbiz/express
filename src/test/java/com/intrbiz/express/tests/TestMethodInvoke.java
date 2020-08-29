package com.intrbiz.express.tests;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.intrbiz.express.DefaultContext;
import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.tests.model.SomeBean;
import com.intrbiz.express.util.ELUtil;

public class TestMethodInvoke
{
    private ExpressContext context;
    
    @Before()
    public void setupContext()
    {
        this.context = new DefaultContext();
        this.context.setEntity("bean", new SomeBean(), null);
        //
        BasicConfigurator.configure();
        Logger.getRootLogger().setLevel(Level.DEBUG);
    }
    
    @Test()
    public void testGetSomeProperty()
    {
        Object o = ELUtil.parseEL("#{ bean.getSomeProperty() }", this.context).get(context, null);
        System.out.println(o);
        assertThat(o, is(equalTo("Has a value")));
    }
    
    @Test()
    public void testSetSomeProperty()
    {
        ELUtil.parseEL("#{ bean.setSomeProperty('Has a new value') }", this.context).get(context, null);
        assertThat(((SomeBean) this.context.getEntity("bean", null)).getSomeProperty(), is(equalTo("Has a new value")));
    }
    
    @Test()
    public void testArraysAsList()
    {
        Object o = ELUtil.parseEL("#{Arrays.asList(1, 2, 3)}", this.context).get(context, null);
        System.out.println(o);
        assertThat(o, is(instanceOf(List.class)));
        assertThat(o, is(equalTo(Arrays.asList(1, 2, 3))));
    }

    @Test()
    public void testStreams1()
    {
        Stream.of(1, 2, 3).map(String::valueOf).collect(Collectors.joining(", "));
        Object o = ELUtil.parseEL("#{ stream(1, 2, 3).map((o) -> string(o)).collect(Collectors.joining(', ')) }", this.context).get(context, null);
        System.out.println(o);
        assertThat(o, is(equalTo("1, 2, 3")));
    }
    
    @Test()
    public void testStreams()
    {
        Object o = ELUtil.parseEL("#{ Stream.of(1, 2, 3).map((o) -> string(o)).collect(Collectors.joining(', ')) }", this.context).get(context, null);
        System.out.println(o);
        assertThat(o, is(equalTo("1, 2, 3")));
    }
}
