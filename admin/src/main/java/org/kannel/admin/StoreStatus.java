package org.kannel.admin;

import java.io.InputStream;

/**
 * Status of kannel's store, parsed from store-status.xml admin query.
 *
 * @author garth
 */
public class StoreStatus
{

    public static StoreStatus parse(InputStream is) throws Exception
    {
	return new StoreStatus();
    }

}