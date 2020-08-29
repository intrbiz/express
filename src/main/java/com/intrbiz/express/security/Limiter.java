package com.intrbiz.express.security;

import com.intrbiz.express.ExpressException;

public class Limiter implements Hidden
{
    private static final int DEFAULT_ITERATION_LIMIT = 10_000;
    
    private static final int DEFAULT_OP_LIMIT = 250_000;
    
    public static Limiter defaultIterationLimit() {
        return new Limiter(DEFAULT_ITERATION_LIMIT);
    }
    
    public static Limiter defaultOpLimit() {
        return new Limiter(DEFAULT_OP_LIMIT);
    }
    
    private final int limit;
    
    private int count;
    
    public Limiter(int limit)
    {
        super();
        this.limit = limit;
        this.count = 0;
    }
    
    public void reset()
    {
        this.count = limit;
    }
    
    public void check()
    {
        this.count++;
        if (this.count > this.limit)
            throw new ExpressException("Script iteration limit reached");
    }
}
