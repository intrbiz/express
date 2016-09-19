package com.intrbiz.express;

import java.io.Writer;

import com.intrbiz.express.action.ActionHandler;
import com.intrbiz.express.operator.Decorator;
import com.intrbiz.express.operator.Function;
import com.intrbiz.express.stack.ELStatementFrame;
import com.intrbiz.express.template.filter.ContentFilter;
import com.intrbiz.express.template.filter.ContentFilterRegistry;
import com.intrbiz.express.template.io.TemplateWriter;
import com.intrbiz.express.template.loader.TemplateLoader;

public interface ExpressContext
{
    // customisable stuff
    
    Function getCustomFunction(String name);
    
    Decorator getCustomDecorator(String name, Class<?> entityType);
    
    ExpressExtensionRegistry getExpressExtensionRegistry();
    
    // entities

    Object getEntity(String name, Object source);

    void setEntity(String name, Object value, Object source);
    
    void exportEntity(String name, Object value, Object source);
    
    // actions
    
    ActionHandler getAction(String name, Object source);

    // Stack

    void enterFrame(boolean function);

    void exitFrame();

    ELStatementFrame getFrame();
    
    // config
    
    boolean isCaching();
    
    void setCaching(boolean caching);
    
    // template stuff
    
    ContentFilterRegistry getContentFilterRegistry();
    
    TemplateLoader getTemplateLoader();
    
    TemplateWriter getWriter();
    
    void setupWriter(Writer to, ContentFilter defaultFilter);
    
    void clearWriter();
}
