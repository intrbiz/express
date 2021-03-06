package com.intrbiz.express.tests;

import java.io.PrintWriter;
import java.util.Arrays;

import com.intrbiz.express.DefaultContext;
import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.template.ExpressTemplate;
import com.intrbiz.express.template.loader.TemplateLoader;
import com.intrbiz.express.template.loader.impl.ClassPathTemplateSource;

public class TestScript
{
    public static void main(String[] args)
    {
        // the loader
        TemplateLoader loader = new TemplateLoader();
        loader.addSource(new ClassPathTemplateSource());
        // the context
        ExpressContext context = new DefaultContext(loader);
        context.setEntity("title", "Template Test", null);
        context.setEntity("list", Arrays.asList("A", "B", "C"), null);
        // load a template
        ExpressTemplate template = loader.load(context, "test1");
        System.out.println(template);
        for (int i = 0 ; i < 5; i++)
        {
            System.out.println("===================================================");
            System.out.println("=== EXECUTE");
            System.out.println("===================================================");
            long start = System.currentTimeMillis();
            PrintWriter writer = new PrintWriter(System.out);
            template.encode(context, null, writer);
            writer.flush();
            long end = System.currentTimeMillis();
            System.out.println();
            System.out.println("===================================================");
            System.out.println("Took: " + (end - start) + "ms");
            System.out.println("===================================================");
        }
    }
}
