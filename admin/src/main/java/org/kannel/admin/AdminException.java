package org.kannel.admin;

/**
 * Exception thrown when an administrative function fails.
 *
 * @author garth
 */
public class AdminException extends Exception
{

    public AdminException(String message)
    {
	super(message);
    }

    public AdminException(String message, Throwable cause)
    {
	super(message, cause);
    }

}