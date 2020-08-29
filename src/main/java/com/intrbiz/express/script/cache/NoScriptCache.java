package com.intrbiz.express.script.cache;

import javax.script.ScriptEngine;

public class NoScriptCache implements ScriptCache
{
    @Override
    public ScriptEngine get(String script)
    {
        return null;
    }

    @Override
    public ScriptEngine cache(String script, ScriptEngine engine)
    {
        return engine;
    }
}
