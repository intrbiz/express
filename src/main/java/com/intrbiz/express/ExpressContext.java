package com.intrbiz.express;

import com.intrbiz.express.action.ActionHandler;
import com.intrbiz.express.operator.Decorator;
import com.intrbiz.express.operator.Function;
import com.intrbiz.express.stack.ELStatementFrame;
import com.intrbiz.express.template.filter.ContentFilter;

public interface ExpressContext
{
    // customisable stuff
    
    Function getCustomFunction(String name);
    
    Decorator getCustomDecorator(String name, Class<?> entityType);
    
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
    
    void write(String content) throws ExpressException;
    
    void writePrefiltered(String content) throws ExpressException;
    
    void setContentFilter(ContentFilter filter);
    
    void resetContentFilter();
    
    ContentFilter getContentFilter();
    
    void flush() throws ExpressException;
}
