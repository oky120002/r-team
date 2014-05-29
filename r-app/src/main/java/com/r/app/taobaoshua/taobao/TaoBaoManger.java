/**
 * 
 */
package com.r.app.taobaoshua.taobao;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

import com.r.core.exceptions.io.NetworkIOReadErrorException;
import com.r.core.httpsocket.HttpSocket;
import com.r.core.httpsocket.context.HttpProxy;
import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;

/**
 * @author oky
 * 
 */
public class TaoBaoManger {
	private static final Logger logger = LoggerFactory.getLogger(TaoBaoManger.class); // 日志

	private HttpSocket taoBaoHttpSocket = HttpSocket.newHttpSocket(true, null);

	public TaoBaoManger() {
		super();
		logger.debug("TaoBaoManger newInstance  ................");
	}

	/** 设置当前链接的代理 */
	public void setYuuBooSocketProxy(HttpProxy proxy) {
		this.taoBaoHttpSocket.setProxy(proxy);
	}

	/** 进入淘宝宝贝详情页面 */
	public void goinTaobaoItem(String itemid) {
		taoBaoHttpSocket.send("http://item.taobao.com/item.htm?id=" + itemid);
	}

	/**
	 * 查询宝贝结果列表
	 * 
	 * @param taoBaoitemSearch
	 * @param page
	 *            查询的页数
	 * @return
	 * @throws IOException
	 */
	public String searchItem(TaoBaoItemSearch taoBaoitemSearch, int page) throws NetworkIOReadErrorException {
		String key = taoBaoitemSearch.getSearchKey();
		String loc = taoBaoitemSearch.getLocation();
		int priceMin = taoBaoitemSearch.getPriceMin();
		int priceMax = taoBaoitemSearch.getPriceMax();
		boolean isTmall = taoBaoitemSearch.isTmall();
		String searchUrl = TaoBaoUtil.reloveTaobaoSearchAddr(key, loc, isTmall, priceMin, priceMax, page);
		return taoBaoHttpSocket.send(searchUrl).bodyToString();
	}

	/** 查询宝贝的真实"所在地" */
	public String searchItmeLoc(TaoBaoItemSearch taoBaoitemSearch) throws NetworkIOReadErrorException {
		long threadid = Thread.currentThread().getId();
		String location = taoBaoitemSearch.getLocation();
		int priceMin = taoBaoitemSearch.getPriceMin();
		int priceMax = taoBaoitemSearch.getPriceMax();
		String searchKeyCut = StringUtils.abbreviate(taoBaoitemSearch.getSearchKey(), 15);
		logger.info("currentThread : {}  搜索预置所在地[{}]的真实所在地!", threadid, location);
		if (StringUtils.isNotBlank(location) && 2 <= location.length()) {
			String html = null;
			String loc = null;
			int right = 2; // 从两个字判断起
			while (true) {
				loc = StringUtils.right(location, right++);
				html = searchItem(taoBaoitemSearch, 1);

				if (html.contains("点击返回上一步")) { // 截取出的所在地不存在
					if (loc.equals(location)) { // 截取出的所在地和原所在地相等,则设置成通用所在地"all"后返回
						logger.info("currentThread : {}  搜索到关键字为[{}]，售价区间为[{},{}]的宝贝的真实所在地不存在,所以设置成通用所在地 : all", threadid, searchKeyCut, priceMin, priceMax);
						return "all";
					} else { // 截取出的所在地和原所在地不相等,则继续截取

					}
				} else { // 截取出的所在地存在,则直接设置此所在地后跳出
					logger.info("currentThread : {}  成功搜索到关键字为[{}]，售价区间为[{},{}]的宝贝的真实所在地 : {}", threadid, searchKeyCut, priceMin, priceMax, loc);
					return loc;
				}
			}
		} else {
			logger.info("currentThread : {}  成功搜索到关键字为[{}]，售价区间为[{},{}]的宝贝的真实所在地 : {}", threadid, searchKeyCut, priceMin, priceMax, "all");
			return "all";
		}
	}
}
