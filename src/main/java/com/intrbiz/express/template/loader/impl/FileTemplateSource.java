package com.intrbiz.express.template.loader.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;
import com.intrbiz.express.template.ExpressTemplate;
import com.intrbiz.express.template.loader.NameSuffix;
import com.intrbiz.express.template.loader.NameSuffixRegistry;
import com.intrbiz.express.template.loader.TemplateSource;
import com.intrbiz.express.value.ValueScript;

public class FileTemplateSource implements TemplateSource
{    
    private final File basePath;
    
    public FileTemplateSource(File basePath)
    {
        super();
        this.basePath = basePath;
    }
    
    public File getBasePath()
    {
        return this.basePath;
    }

    @Override
    public ExpressTemplate load(ExpressContext context, NameSuffixRegistry nameSuffixRegistry, String name) throws ExpressException
    {
        // search for the template
        for (NameSuffix suffix : nameSuffixRegistry)
        {
            File file = new File(this.basePath, name + "." + suffix.getSuffix());
            if (file.exists())
            {
                try
                {
                    // parse the template and return it
                    return new ExpressTemplate(name, this, suffix.getDefaultContentFilter(), new ValueScript(context, new FileInputStream(file)));
                }
                catch (IOException e)
                {
                    throw new ExpressException("Failed to load template file", e);
                }
            }
        }
        return null;
    }
}
