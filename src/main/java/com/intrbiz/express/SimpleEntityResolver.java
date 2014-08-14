package com.intrbiz.express;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class SimpleEntityResolver extends ExpressEntityResolver
{
    private ConcurrentMap<String, Object> entities = new ConcurrentHashMap<String, Object>();
    
    public SimpleEntityResolver()
    {
        super();
    }
    
    public SimpleEntityResolver(Map<String, Object> entities)
    {
        super();
        this.entities.putAll(entities);
    }

    @Override
    public Object getEntity(String name, Object source)
    {
        return this.entities.get(name);
    }
    
    public SimpleEntityResolver addEntity(String name, Object entity)
    {
        this.entities.put(name, entity);
        return this;
    }
}
