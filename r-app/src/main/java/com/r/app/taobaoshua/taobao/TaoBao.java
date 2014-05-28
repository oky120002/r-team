/**
 * 
 */
package com.r.app.taobaoshua.taobao;

import com.r.app.taobaoshua.TaoBaoShuaStartup;
import com.r.app.taobaoshua.yuuboo.YuuBoo;
import com.r.core.httpsocket.HttpSocket;
import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;

/**
 * @author oky
 * 
 */
public class TaoBao implements TaoBaoShuaStartup {
	private static final Logger logger = LoggerFactory.getLogger(YuuBoo.class);

	private static TaoBao taoBao = null;
	private HttpSocket taoBaoSocket = null;
	private HttpSocket newTaoBaoSocket = null;

	public TaoBao() {
		super();
		TaoBao.taoBao = this;
		this.taoBaoSocket = HttpSocket.newHttpSocket(true, null);
		this.newTaoBaoSocket = HttpSocket.newHttpSocket(false, null);
	}

	public static TaoBao getInstance() {
		return TaoBao.taoBao;
	}

	public HttpSocket getNewTaoBaoSocket() {
		return newTaoBaoSocket;
	}

	public HttpSocket getTaoBaoSocket() {
		return taoBaoSocket;
	}

	@Override
	public void start() {

	}
}
