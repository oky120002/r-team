/**
 * 
 */
package com.r.web.vote931.support.abs;

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
        logger.info("Instance " + getClass().getSimpleName() + "............................");
    }

}
