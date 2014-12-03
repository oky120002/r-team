/**
 * 
 */
package com.r.web.support.abs;

import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;

/**
 * 
 * @author oky
 * 
 */
public abstract class AbstractService {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    public AbstractService() {
        super();
        logger.debug("Instance  " + getClass().getSimpleName() + "............................");
    }
}
