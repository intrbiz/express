package com.intrbiz.express.functions;

import com.intrbiz.express.ExpressExtensionRegistry;
import com.intrbiz.express.functions.text.JoinFunction;
import com.intrbiz.express.functions.text.LcFunction;
import com.intrbiz.express.functions.text.UcFunction;
import com.intrbiz.express.functions.text.UcfirstFunction;

public class TextFunctionRegistry extends ExpressExtensionRegistry
{
    public TextFunctionRegistry()
    {
        super("text");
        // register our functions
        this.addFunction("uc", UcFunction.class); // uppercase
        this.addFunction("lc", LcFunction.class); // lowercase
        this.addFunction("ucfirst", UcfirstFunction.class); // uppercase first character
        this.addFunction("join", JoinFunction.class); // join a collection
    }
}
