package com.intrbiz.express.tests;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.Test;

import com.intrbiz.express.ExpressException;
import com.intrbiz.express.util.ELUtil;

public class TestStrings
{

    @Test
    public void concatString() throws ExpressException
    {
        Object o;
        o = ELUtil.parseEL("#{'test' + '_' + 'test'}", null).get(null, null);
        //
        assertThat(o, is(instanceOf(String.class)));
        assertThat((String) o, is(equalTo("test_test")));
    }

}
