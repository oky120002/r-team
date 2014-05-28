/**
 * 
 */
package com.r.app.taobaoshua.taobao;

import com.r.app.taobaoshua.yuuboo.YuuBoo;
import com.r.core.httpsocket.HttpSocket;
import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;

/**
 * @author oky
 * 
 */
public class TaoBao {
	private static final Logger logger = LoggerFactory.getLogger(YuuBoo.class);

	private static TaoBao taoBao = null;
	private HttpSocket taoBaoSocket = null;
	private HttpSocket newTaoBaoSocket = null;

	public TaoBao() {
		super();
		this.taoBao = this;
		this.taoBaoSocket = HttpSocket.newHttpSocket(true, null);
		this.newTaoBaoSocket = HttpSocket.newHttpSocket(false, null);
	}

	public static TaoBao getInstance() {
		return taoBao;
	}

	public HttpSocket getNewTaoBaoSocket() {
		return newTaoBaoSocket;
	}

	public HttpSocket getTaoBaoSocket() {
		return taoBaoSocket;
	}
}
