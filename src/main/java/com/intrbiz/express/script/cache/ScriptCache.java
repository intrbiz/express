package com.intrbiz.express.script.cache;

import javax.script.ScriptEngine;

public interface ScriptCache
{
    ScriptEngine get(String script);
    
    ScriptEngine cache(String script, ScriptEngine engine);
}
