package com.intrbiz.express.template.loader;

import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;
import com.intrbiz.express.template.ExpressTemplate;

public interface TemplateSource
{
    ExpressTemplate load(ExpressContext context, NameSuffixRegistry nameRegistry, String name) throws ExpressException;
}
