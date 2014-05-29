/**
 * 
 */
package com.r.app.taobaoshua.taobao;

import com.r.app.taobaoshua.TaoBaoShuaStartup;
import com.r.app.taobaoshua.yuuboo.YuuBoo;
import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;

/**
 * @author oky
 * 
 */
public class TaoBao implements TaoBaoShuaStartup {
	private static final Logger logger = LoggerFactory.getLogger(YuuBoo.class);

	private static TaoBao taoBao;
	private TaoBaoManger taoBaoManger;
	private TaoBaoAction taoBaoAction;

	public TaoBao() {
		super();
		logger.debug("TaoBao newInstance..........");
	}

	public static TaoBao getInstance() {
		return TaoBao.taoBao;
	}

	public TaoBaoAction getTaoBaoAction() {
		return taoBaoAction;
	}

	public TaoBaoManger getTaoBaoManger() {
		return taoBaoManger;
	}

	@Override
	public void init() {
		TaoBao.taoBao = this;
		taoBaoAction = new TaoBaoAction();
		taoBaoManger = new TaoBaoManger();
	}

	@Override
	public void startup() {

	}
}
