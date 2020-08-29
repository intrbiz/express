package com.intrbiz.express.script.cache;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.script.ScriptEngine;

public class WeakScriptCache implements ScriptCache
{
    private final ConcurrentMap<String, ValueReference> cache = new ConcurrentHashMap<>();
    
    private final Lock queueLock = new ReentrantLock();
    
    private final ReferenceQueue<ScriptEngine> referenceQueue = new ReferenceQueue<ScriptEngine>();
    
    public WeakScriptCache()
    {
        super();
    }
    
    private void processQueue()
    {
        if (this.queueLock.tryLock())
        {
            try
            {
                ValueReference e;
                while ((e = (ValueReference) this.referenceQueue.poll()) != null)
                {
                    this.cache.remove(e.getKey());
                }
            }
            finally
            {
                this.queueLock.unlock();
            }
        }
    }
    
    public ScriptEngine get(String script)
    {
        this.processQueue();
        ValueReference ref = this.cache.get(script);
        return ref.get();
    }
    
    public ScriptEngine cache(String script, ScriptEngine engine)
    {
        this.processQueue();
        this.cache.put(script, new ValueReference(Objects.requireNonNull(script), Objects.requireNonNull(engine), this.referenceQueue));
        return engine;
    }
    
    private static class ValueReference extends WeakReference<ScriptEngine>
    {
        private final String key;
        
        public ValueReference(String key, ScriptEngine referent, ReferenceQueue<ScriptEngine> referenceQueue)
        {
            super(Objects.requireNonNull(referent), referenceQueue);
            this.key = key;
        }

        public String getKey()
        {
            return this.key;
        }
    }
}
