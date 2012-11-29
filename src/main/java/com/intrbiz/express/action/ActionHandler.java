package com.intrbiz.express.action;

import java.util.List;

public interface ActionHandler
{
    String getName();
    
    List<ActionArgument> getArguments();
    
    Object act(Object[] arguments) throws Exception;
    
    
    public static class ActionArgument
    {
        private Class<?> type;
        private String name;
        
        public ActionArgument(Class<?> type, String name)
        {
            this.type = type;
            this.name = name;
        }

        public Class<?> getType()
        {
            return type;
        }

        public void setType(Class<?> type)
        {
            this.type = type;
        }

        public String getName()
        {
            return name;
        }

        public void setName(String name)
        {
            this.name = name;
        }
    }
}
