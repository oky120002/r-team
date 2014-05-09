/**
 * 
 */
package com.r.web.support.control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.r.common.log.Logger;
import com.r.common.log.LoggerFactory;

/**
 * @author oky
 *
 */
@Controller
@RequestMapping(value = "/")
public class SystemInitControl {
	private static final Logger logger = LoggerFactory.getLogger(SystemInitControl.class);
	
	
}
