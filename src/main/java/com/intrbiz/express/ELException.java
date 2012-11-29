package com.intrbiz.express;


public class ELException extends Exception
{
    private static final long serialVersionUID = 1L;

    public ELException()
    {
        super();
    }

    public ELException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public ELException(String message)
    {
        super(message);
    }

    public ELException(Throwable cause)
    {
        super(cause);
    }
}
