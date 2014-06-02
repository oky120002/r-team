/**
 * 
 */
package com.r.core.log;

/**
 * @author Administrator
 *
 */
public interface LoggerListener {

	boolean warn(String message, Throwable error);

	boolean debug(String message, Throwable error);

	boolean info(String message, Throwable error);

	boolean error(String message, Throwable error);
}
