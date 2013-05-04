package com.intrbiz.express;


/**
 * An unexpected error occurred while processing an Express expression
 * 
 * Note:  This is a unchecked exception!
 * 
 */
public class ExpressException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    public ExpressException()
    {
        super();
    }

    public ExpressException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public ExpressException(String message)
    {
        super(message);
    }

    public ExpressException(Throwable cause)
    {
        super(cause);
    }
}
