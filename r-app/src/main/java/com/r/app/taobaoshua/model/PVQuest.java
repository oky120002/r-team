/**
 * 
 */
package com.r.app.taobaoshua.model;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * @author oky
 * 
 */
public class PVQuest {
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	private static final Set<String> cacheLocations = new HashSet<String>();
	{
		cacheLocations.addAll(Arrays.asList(new String[] { "all", "江苏", "浙江", "上海", "香港", //
				"澳门", "台湾", "广州", "深圳", "中山", "珠海", "佛山", "东莞", "惠州", "北京", "天津", //
				"江苏", "浙江", "上海", "安徽", "黑龙江", "吉林", "辽宁", "北京", "天津", "河北", "重庆",//
				"长沙", "长春", "成都", "大连", "东莞", "福州", "合肥", "济南", "嘉兴", "昆明", "宁波",//
				"南京", "南昌", "青岛", "苏州", "沈阳", "天津", "温州", "无锡", "武汉", "西安", "厦门", //
				"郑州", "中山", "石家庄", "哈尔滨", "福建", "甘肃", "广东", "广西", "贵州", "河北", "河南", //
				"湖北", "湖南", "海南", "江苏", "江西", "吉林", "辽宁", "宁夏", "青海", "山东", "山西", //
				"陕西", "四川", "西藏", "新疆", "云南", "武汉", "内蒙古", "江沪浙", "港澳台", "珠三角",//
				"京津", "江浙沪皖", "东三省", "京津冀", "海外", "南通", }));
	}

	private String id; // 任务编号
	private String publisher; // 任务发布者
	private String questid; // 任务id
	private String searchKey; // 搜索关键字
	private int priceMin; // 搜索最小价格
	private int priceMax; // 搜索最大价格
	private String location; // 搜索所在地
	private String shopKeeper; // 店主
	private String boardTime; // 接手时间
	private String releaseTime; // 发布时间

	private boolean isStayFor5Minutes = false; // 是否停留5分钟
	private boolean isIntoStoreAndSearch = false; // 是否进店再搜索
	private boolean isTmall = false; // 是否天猫店搜索

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the publisher
	 */
	public String getPublisher() {
		return publisher;
	}

	/**
	 * @param publisher
	 *            the publisher to set
	 */
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	/**
	 * @return the questid
	 */
	public String getQuestid() {
		return questid;
	}

	/**
	 * @param questid
	 *            the questid to set
	 */
	public void setQuestid(String questid) {
		this.questid = questid;
	}

	/**
	 * @return the searchKey
	 */
	public String getSearchKey() {
		return StringUtils.isBlank(searchKey) ? "" : searchKey.split("\r\n")[0];
	}

	/** 截取搜索的关键字,以cutNumber长度输出 */
	public String getSearchKeyCut(int cutNumber) {
		return StringUtils.abbreviate(getSearchKey(), cutNumber);
	}

	/**
	 * @param searchKey
	 *            the searchKey to set
	 */
	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}

	/**
	 * @return the priceMin
	 */
	public int getPriceMin() {
		return priceMin;
	}

	/**
	 * @return the priceMax
	 */
	public int getPriceMax() {
		return priceMax;
	}

	/**
	 * @param priceMin
	 * @param priceMax
	 */
	public void setPrice(int priceMin, int priceMax) {
		int minPrice = priceMin > priceMax ? priceMax : priceMin;
		int maxPrice = priceMin > priceMax ? priceMin : priceMax;
		// -1和+1是为了解决淘宝价格搜索边界值时,有时会搜索不出来,也解决人为录入边界值问题
		this.priceMin = (minPrice == 0 ? 0 : minPrice - 1);
		this.priceMax = maxPrice + 1;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location
	 *            the location to set
	 */
	public void setLocation(String location) {
		if (StringUtils.isBlank(location)) {
			this.location = "all";
			return;
		}
		// 过滤错误地址
		switch (location) {
		case "全国":
		case "中国":
		case "不搜":
		case "0":
		case "00":
		case "000":
			location = "all";
			break;
		}
		this.location = location;
	}

	/**
	 * @return the shopKeeper
	 */
	public String getShopKeeper() {
		return shopKeeper;
	}

	/**
	 * @param shopKeeper
	 *            the shopKeeper to set
	 */
	public void setShopKeeper(String shopKeeper) {
		this.shopKeeper = shopKeeper;
	}

	/**
	 * @return the boardTime
	 */
	public String getBoardTime() {
		return boardTime;
	}

	/**
	 * @param boardTime
	 *            the boardTime to set
	 */
	public void setBoardTime(String boardTime) {
		this.boardTime = boardTime;
	}

	/**
	 * @return the releaseTime
	 */
	public String getReleaseTime() {
		return releaseTime;
	}

	/**
	 * @param releaseTime
	 *            the releaseTime to set
	 */
	public void setReleaseTime(String releaseTime) {
		this.releaseTime = releaseTime;
	}

	/**
	 * @return the isStayFor5Minutes
	 */
	public boolean isStayFor5Minutes() {
		return isStayFor5Minutes;
	}

	/**
	 * @param isStayFor5Minutes
	 *            the isStayFor5Minutes to set
	 */
	public void setStayFor5Minutes(boolean isStayFor5Minutes) {
		this.isStayFor5Minutes = isStayFor5Minutes;
	}

	/**
	 * @return the isIntoStoreAndSearch
	 */
	public boolean isIntoStoreAndSearch() {
		return isIntoStoreAndSearch;
	}

	/**
	 * @param isIntoStoreAndSearch
	 *            the isIntoStoreAndSearch to set
	 */
	public void setIntoStoreAndSearch(boolean isIntoStoreAndSearch) {
		this.isIntoStoreAndSearch = isIntoStoreAndSearch;
	}

	/**
	 * @return the isTmall
	 */
	public boolean isTmall() {
		return isTmall;
	}

	/**
	 * @param isTmall
	 *            the isTmall to set
	 */
	public void setTmall(boolean isTmall) {
		this.isTmall = isTmall;
	}

	/** 获取特殊条件字符串 */
	public String getSpecialConditions() {
		StringBuilder sb = new StringBuilder();
		if (isStayFor5Minutes || isIntoStoreAndSearch) {
			sb.append('(');
			if (isStayFor5Minutes) {
				sb.append("停留5分钟");
			}
			if (isIntoStoreAndSearch) {
				sb.append(" , ").append("进店再搜索");
			}
			if (isTmall) {
				sb.append(" , ").append("天猫");
			}
			sb.append(')');
		}
		return sb.toString();
	}

	/**
	 * 获取淘宝查询地址<br />
	 * http://s.taobao.com/search?tab=all&loc=all&filter=reserve_price[5,35]&fs=
	 * 0&stats_click=search_radio_all:1&initiative_id=staobaoz_20140521&q=
	 * 夏装新款装夏季潮上衣圆领纯棉女t恤&filterFineness=2&promote=0&s=160#J_relative
	 * 
	 * @param page
	 *            页数
	 * 
	 * @return
	 */
	public String getTaobaoSearchAddr(int page) {
		if (page < 1) {
			page = 1;
		}
		StringBuilder url = new StringBuilder();
		String key = this.searchKey;
		try {
			key = URLEncoder.encode(key, "gbk");
		} catch (UnsupportedEncodingException e) {
		}

		url.append("http://s.taobao.com/search?");
		url.append("tab=").append(this.isTmall ? "mall" : "all");
		url.append("&q=").append(key); // 必须存在
		url.append("&loc=").append(convertLocation(this.location));
		// 增加一个判断.就是发布任务的可能就是要人不输入价格.填写价格 0 0(我这个0 1是set时做了处理)
		if (!(this.priceMin == 0 && this.priceMax == 1)) {
			url.append("&filter=reserve_price[").append(this.priceMin).append(',').append(this.priceMax).append(']');
		}
		url.append("&filterFineness=2&promote=0");
		url.append("&fs=0&stats_click=search_radio_all%3A1&initiative_id=staobaoz_").append(sdf.format(new Date()));
		url.append("&s=").append((page - 1) * 40).append("#J_relative");
		return url.toString();
	}

	/**
	 * 获取淘宝查询地址<br />
	 * http://s.taobao.com/search?tab=all&loc=all&filter=reserve_price[5,35]&fs=
	 * 0&stats_click=search_radio_all:1&initiative_id=staobaoz_20140521&q=
	 * 夏装新款装夏季潮上衣圆领纯棉女t恤&filterFineness=2&promote=0&s=160#J_relative
	 * 
	 * @param page
	 *            页数
	 * 
	 * @return
	 */
	// http://s.taobao.com/search?tab=all&fs=0&stats_click=search_radio_all%3A1&initiative_id=staobaoz_20140524&q=%C5%B7%C9%AF%B1%B4%B6%F9&filterFineness=2
	public String checkSearchAddr() {
		StringBuilder url = new StringBuilder();
		url.append("http://s.taobao.com/search?tab=").append(this.isTmall ? "mall" : "all");
		url.append("&loc=").append(convertLocation(this.location));
		url.append("&fs=0&stats_click=search_radio_all%3A1&initiative_id=staobaoz_").append(sdf.format(new Date()));
		url.append("&q=").append(convertSearchKey(this.searchKey)).append("&filterFineness=2");
		return url.toString();
	}

	/** 转换搜索条件 */
	private String convertSearchKey(String key) {
		try {
			return URLEncoder.encode(key, "gbk");
		} catch (UnsupportedEncodingException e) {
		}
		return key;
	}

	/** 转换所在地 */
	private String convertLocation(String loc) {
		if (StringUtils.isBlank(loc)) {
			return "all";
		}
		loc = loc.trim();
		// 过滤特殊地址
		switch (loc) {
		case "江沪浙":
			loc = "江苏,浙江,上海";
			break;
		case "港澳台":
			loc = "香港,澳门,台湾";
			break;
		case "珠三角":
			loc = "广州,深圳,中山,珠海,佛山,东莞,惠州";
			break;
		case "京津":
			loc = "北京,天津";
			break;
		case "江浙沪皖":
			loc = "江苏,浙江,上海,安徽";
			break;
		case "东三省":
			loc = "黑龙江,吉林,辽宁";
			break;
		case "京津冀":
			loc = "北京,天津,河北";
		case "海外":
			loc = "美国,英国,法国,瑞士,澳洲,新西兰,加拿大,奥地利,韩国,日本,德国,意大利,西班牙,俄罗斯,泰国,印度,荷兰,新加坡,其它国家";
			break;
		default:
			break;
		}
		try {
			loc = URLEncoder.encode(loc, "gbk");
		} catch (UnsupportedEncodingException e) {
		}
		return loc;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("PVQuest").append("\r\n");
		sb.append("任务编号 : ").append(this.id).append("\r\n");
		sb.append("任务ID : ").append(this.questid).append("\r\n");
		sb.append("任务发布者 : ").append(this.publisher).append("\r\n");
		sb.append("发布时间 : ").append(this.releaseTime).append("\r\n");
		sb.append("店主 : ").append(this.shopKeeper).append("\r\n");
		sb.append("接手时间 : ").append(this.boardTime).append("\r\n");
		sb.append("关键字 : ").append(this.searchKey).append("\r\n");
		sb.append("搜索提示 价格区间 : ").append(this.priceMin).append('-').append(this.priceMax).append("元 ").append("\r\n");
		sb.append("搜索提示 所在地 : ").append(this.location);
		sb.append(getSpecialConditions());
		sb.append("\r\n");
		sb.append("\r\n");
		return sb.toString();
	}

	// XXX 如果优保刷流量的"一个账号一天势能刷一个流量地址"的规则有改变,则更改hashCode和equals方法
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PVQuest other = (PVQuest) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	/** 获取店主的正则表达式 */
	public String getShopKeeperPattern() {
		if (shopKeeper.length() <= 4) {
			return shopKeeper;
		} else {
			String substring = shopKeeper.substring(2, shopKeeper.length() - 2);
			int length = substring.length();
			Pattern pattern = Pattern.compile("\\*+");
			Matcher matcher = pattern.matcher(this.shopKeeper);
			return matcher.replaceFirst(".{" + length + "}");
		}
	}

	public static void main(String[] args) {
		String sss = "babcd";

		System.out.println(sss.split("b.a")[0]);
	}

	/** 通过此检查的宝贝所在地,即为淘宝能够识别的所在地. */
	public static boolean checkLoc(String location) {
		return cacheLocations.contains(location);
	}
}
