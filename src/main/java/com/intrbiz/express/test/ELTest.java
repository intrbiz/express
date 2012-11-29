package com.intrbiz.express.test;

import com.intrbiz.express.AbstractELContext;
import com.intrbiz.express.ELContext;
import com.intrbiz.express.ELException;
import com.intrbiz.express.operator.Decorator;
import com.intrbiz.express.operator.Function;
import com.intrbiz.express.value.ValueExpression;

public class ELTest
{
    public static void main(String[] args) throws Exception
    {
        //
        TestObject obj = new TestObject();
        ELContext c = testContext(obj);
        //
        // Literals
        test("Integer", c, "1234", 1234);
        test("Float", c, "1234.123", 1234.123F);
        test("Long", c, "1234L", 1234L);
        test("Double", c, "1234.123D", 1234.123D);
        test("Double", c, "1234.0D", 1234D);
        test("String", c, "'test'", "test");
        test("String", c, "'test \\'test\\''", "test 'test'");
        test("String append", c, "'test' + '_test'", "test_test");
        // Entities
        test("Resolve Entity 1", c, "TestBean", obj);
        test("Resolve Entity Property 1", c, "TestBean.testString", obj.getTestString());
        test("Resolve Entity Property 2", c, "TestBean.testString == 'test'", true);
        test("Resolve Entity Property 3", c, "TestBean.nullString == null", true);
        test("Resolve Entity Property 4", c, "TestBean.emptyString == ''", true);
        test("Resolve Entity Property 5", c, "(TestBean.testString + '_test') == 'test_test'", true);
        test("Resolve Entity Property 6", c, "(TestBean.testString + '_test') != 'test_test'", false);
        test("Resolve Entity Property 7", c, "TestBean.testInt == 134563", true);
        test("Resolve Entity Property 8", c, "TestBean.testInt != 134562", true);
        test("Resolve Entity Property 9", c, "TestBean.testInt < 200000", true);
        test("Resolve Entity Property 10", c, "TestBean.testInt > 20", true);
        test("Resolve Entity Property 11", c, "TestBean.testInt >= 134563", true);
        test("Resolve Entity Property 12", c, "TestBean.testInt <= 134563", true);
        // Math test
        test("Math Test 1", c, "- 2", -2);
        test("Math Test 2", c, "1 + 2", 3);
        test("Math Test 3", c, "2 - 1", 1);
        test("Math Test 4", c, "1 * 2", 2);
        test("Math Test 5", c, "4 / 2", 2);
        test("Math Test 6", c, "4 % 2", 0);
        test("Math Test 7", c, "4 ^ 2", 16D);
        test("Math Test 8", c, "4 ^ 2 + 4", 20D);
        test("Math Test 9", c, "4 ^ 2 * 2", 32D);
        test("Math Test 10", c, "4 ^ 2 / 2", 8D);
        test("Math Test 11", c, "4 ^ 2 * 4 / 2", 32D);
        test("Math Test 12", c, "4 ^ 2 / 2 * 4", 32D);
        test("Math Test 13", c, "4 / 2 * 6", 4 / 2 * 6);
        test("Math Test 14", c, "24 / (2 * 6)", 24 / (2 * 6));
        test("Math Test 15", c, "2 * 6 / 3", 2 * 6 / 3);
        test("Math Test 16", c, "(2 * 6) / 3", (2 * 6) / 3);
        test("Math Test 17", c, "2 * (6 / 3)", 2 * (6 / 3));
        test("Math Test 18", c, "2 * 6 - 2", 2 * 6 - 2);
        test("Math Test 19", c, "2 * 6 + 2", 2 * 6 + 2);
        test("Math Test 20", c, "2 * 6 * 4 / 3 + 2", 2 * 6 * 4 / 3 + 2);
        test("Math Test 21", c, "4 ^ 2 * 6 * 4 / 3 + 2", Math.pow(4, 2) * 6 * 4 / 3 + 2);
        test("Math Test 22", c, "6 ^ 2 / 3 * 2 + 5 - 8", Math.pow(6, 2) / 3 * 2 + 5 - 8);
        test("Math Test 23", c, "3 == 3", true);
        test("Math Test 24", c, "3 < 4", true);
        test("Math Test 25", c, "3 > 2", true);
        test("Math Test 26", c, "3 <= 4", true);
        test("Math Test 27", c, "3 >= 2", true);
        test("Math Test 28", c, "3 <= 3", true);
        test("Math Test 29", c, "3 >= 3", true);
        test("Math Test 30", c, "3 ^ 2 == 3 * 3", true);
        test("Math Test 31", c, "3 ^ 2 == 9", true);
        test("Math Test 32", c, "3 * 2 == 4 + 2", true);
        test("Math Test 33", c, "3 * 2 == 6", true);
        test("Math Test 34", c, "8 / 4 == 2", true);
        test("Math Test 35", c, "8 / 4 == 1 + 1", true);
        test("Math Test 36", c, "8 / 4 == 1 * 2", true);
        test("Math Test 37", c, "8 + 2 == 10", true);
        test("Math Test 38", c, "8 + 2 == 5 + 5", true);
        test("Math Test 39", c, "8 + 2 == 12 - 2", true);
        test("Math Test 40", c, "8 + 2 == 5 * 2", true);
        test("Math Test 41", c, "8 + 1 == 3 ^ 2", true);
        test("Math Test 42", c, "8 - 2 == 4 + 2", true);
        test("Math Test 43", c, "8 - 2 == 12 / 2", true);
        test("Math Test 44", c, "8 - 2 == 6 - 0", true);
        test("Math Test 45", c, "8 - 2 == 3 * 2", true);
        test("Math Test 46", c, "18 - 2 == 4 ^ 2", true);
        test("Math Test 47", c, "18 - 2 == 4 ^ 2 && 18 - 4 == 10 + 4", true);
        test("Math Test 48", c, "18 - 2 == 4 ^ 2 || 18 - 4 == 10 + 4", true);
        test("Math Test 49", c, "18 - 2 == 4 ^ 2 || 18 - 4 == 10 + 5", true);
        test("Math Test 50", c, "18 - 2 == 4 ^ 2 || true", true);
        test("Math Test 51", c, "18 - 2 == 4 ^ 2 || false", true);
        test("Math Test 52", c, "18 - 2 == 4 ^ 2 && true", true);
        test("Math Test 53", c, "18 - 2 == 4 ^ 2 && false", false);
        test("Math Test 54", c, "18 - 2 == 4 ^ 2 && 18 - 2 > 10", true);
        test("Math Test 55", c, "18 - 2 == 4 ^ 2 && 18 - 2 < 20", true);
        test("Math Test 56", c, "18 - 2 == 4 ^ 2 && 18 - 2 >= 10", true);
        test("Math Test 57", c, "18 - 2 == 4 ^ 2 && 18 - 2 <= 20", true);
        // Functions
        test("Function Test 1", c, "coalesce(null, 'test')", "test");
        test("Function Test 2", c, "isnull(TestBean.nullString)", true);
        test("Function Test 3", c, "isempty(list())", true);
        test("Function Test 4", c, "list().size", 0);
        test("Function Test 5", c, "! isempty(list())", false);
        test("Function Test 6", c, "getuuid() != null", true);
        test("Function Test 7", c, "if(true, 'test1', 'test2')", "test1");
        test("Function Test 8", c, "testNamed('test3', test = 'test', test2 = a + b)", "test");
        // Decorators
        test("Decorator Test 1", c, "TestBean.testDecorator('prefix', suffix = 'suffix')", "prefixTestObjectsuffix");
        // Statements

    }

    public static ELContext testContext(final TestObject obj)
    {
        return new AbstractELContext()
        {
            @Override
            public Function getCustomFunction(String name)
            {
                if ("testNamed".equalsIgnoreCase(name)) return new Function("testNamed")
                {
                    @Override
                    public Object get(ELContext context, Object source) throws ELException
                    {
                        return this.getParameter("test").get(context, source);
                    }
                };
                return null;
            }

            @Override
            public Decorator getCustomDecorator(String name, Class<?> entityType)
            {
                if ("testDecorator".equals(name)) return new Decorator("testDecorator")
                {
                    @Override
                    public Object get(ELContext context, Object source) throws ELException
                    {
                        Object o = this.getEntity().get(context, source);
                        String p = (String) this.getParameter(0).get(context, source);
                        String s = (String) this.getParameter("suffix").get(context, source);
                        return p + o.getClass().getSimpleName() + s;
                    }
                };
                return null;
            }

            @Override
            public Object getEntityInner(String name, Object source)
            {
                if ("TestBean".equals(name)) return obj;
                return null;
            }
        };
    }

    public static void test(String name, ELContext c, String exp, Object expres) throws Exception
    {
        exp = "#{" + exp + "}";
        ValueExpression ve = new ValueExpression(c, exp);
        System.out.println("-= " + name + " =------------------");
        if (exp.equals(ve.toString()))
            System.out.println("Test: " + name + " parse (" + ve.toString() + " == " + exp + ") passed");
        else
            System.err.println("Test: " + name + " parse (" + ve.toString() + " == " + exp + ") failed");
        System.out.flush();
        System.err.flush();
        Object o = ve.get(c, null);
        if (expres == o || expres.equals(o))
            System.out.println("Test: " + name + " evaluation (" + o + " == " + expres + ") passed");
        else
            System.err.println("Test: " + name + " evaluation (" + o + " == " + expres + ") failed");
        System.out.flush();
        System.err.flush();
    }
}
