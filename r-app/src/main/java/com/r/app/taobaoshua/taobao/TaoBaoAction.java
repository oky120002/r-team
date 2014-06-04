package com.r.app.taobaoshua.taobao;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.r.core.httpsocket.context.HttpProxy;
import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;

public class TaoBaoAction {
	private static final Logger logger = LoggerFactory.getLogger(TaoBaoAction.class); // 日志
	private static final TaoBao taoBao = TaoBao.getInstance();
	private static final Set<String> cacheLocations = new HashSet<String>();
	{
		cacheLocations.addAll(Arrays.asList(new String[] { "all", "江苏", "浙江", "上海", "香港", //
				"澳门", "台湾", "广州", "深圳", "中山", "珠海", "佛山", "惠州", "北京", "天津", //
				"江苏", "浙江", "上海", "安徽", "黑龙江", "吉林", "辽宁", "北京", "天津", "河北", "重庆",//
				"长沙", "长春", "成都", "大连", "东莞", "福州", "济南", "嘉兴", "昆明", "宁波",//
				"南京", "南昌", "青岛", "苏州", "沈阳", "天津", "温州", "无锡", "武汉", "西安", "厦门", //
				"郑州", "中山", "石家庄", "哈尔滨", "福建", "甘肃", "广东", "广西", "贵州", "河北", "河南", //
				"湖北", "湖南", "海南", "江苏", "江西", "吉林", "辽宁", "宁夏", "青海", "山东", "山西", //
				"陕西", "四川", "西藏", "新疆", "云南", "武汉", "内蒙古", "江沪浙", "港澳台", "珠三角",//
				"京津", "江浙沪皖", "东三省", "京津冀", "海外", "南通", "江门", "杭州", "贵阳",//
				"合肥", "金华", "济南", "嘉兴", "昆明", "宁波", "南昌", "南京", "青岛", "泉州", "沈阳",//
				"苏州", "天津", "温州", "无锡", "武汉", "西安", "厦门", "郑州", "中山", "石家庄",//
				"哈尔滨", "安徽", "福建", "甘肃", "广东", "广西", "贵州", "海南", "河北", "河南",//
				"湖北", "湖南", "江苏", "江西", "吉林", "辽宁", "宁夏", "青海", "山东", "山西", "陕西",//
				"云南", "浙江", "澳门", "香港", "台湾", "内蒙古", "黑龙江", }));
	}

	public TaoBaoAction() {
		super();
		logger.debug("TaoBaoAction newInstance..............");
	}

	/** 设置当前链接的代理 */
	public void setSocketProxy(HttpProxy proxy) {
		taoBao.getTaoBaoManger().setSocketProxy(proxy);
	}

	/** 进入淘宝宝贝详情页面 */
	public void goinTaobaoItem(String itemid) {
		taoBao.getTaoBaoManger().goinTaobaoItem(itemid);
	}

	/** 通过此检查的宝贝所在地,检查宝贝所在地址是否是默认的正确地址 */
	public boolean checkLoc(String location) {
		return cacheLocations.contains(location);
	}

	/** 查询宝贝的真实"所在地" */
	public String searchItmeLoc(TaoBaoItemSearch taoBaoitemSearch) {
		return taoBao.getTaoBaoManger().searchItmeLoc(taoBaoitemSearch);
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
	public String searchItemList(TaoBaoItemSearch taoBaoitemSearch, int page) {
		return taoBao.getTaoBaoManger().searchItem(taoBaoitemSearch, page);
	}
	
	
}
