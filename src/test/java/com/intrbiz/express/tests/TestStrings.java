package com.intrbiz.express.tests;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.intrbiz.express.DefaultContext;
import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;
import com.intrbiz.express.util.ELUtil;

public class TestStrings
{
    private ExpressContext context;
    
    @Before
    public void setup()
    {
        this.context = new DefaultContext();
    }

    @Test
    public void concatString() throws ExpressException
    {
        Object o;
        o = ELUtil.parseEL("#{'test' + '_' + 'test'}", context).get(context, null);
        //
        assertThat(o, is(instanceOf(String.class)));
        assertThat((String) o, is(equalTo("test_test")));
    }

}
