package com.intrbiz.express.tests;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.Test;

import com.intrbiz.express.ELException;
import com.intrbiz.express.util.ELUtil;

public class StringTests
{

    @Test
    public void concatString() throws ELException
    {
        Object o;
        o = ELUtil.parseEL("#{'test' + '_' + 'test'}", null).get(null, null);
        //
        assertThat(o, is(instanceOf(String.class)));
        assertThat((String) o, is(equalTo("test_test")));
    }

}
