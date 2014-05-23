/**
 * 
 */
package com.r.app.taobaoshua.model;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * @author oky
 * 
 */
public class PVQuest {
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

	private String id; // 任务编号
	private String publisher; // 任务发布者
	private String questid; // 任务id
	private String searchKey; // 搜索关键字
	private int priceMin; // 搜索最小价格
	private int priceMax; // 搜索最大价格
	private String location; // 搜索所在地 -- 因为是手动填写,可能出现不可识别的情况.要进行处理
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
		return searchKey.split("\r\n")[0];
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
	 * @param priceMin
	 *            the priceMin to set
	 */
	public void setPriceMin(int priceMin) {
		this.priceMin = priceMin;
	}

	/**
	 * @return the priceMax
	 */
	public int getPriceMax() {
		return priceMax;
	}

	/**
	 * @param priceMax
	 *            the priceMax to set
	 */
	public void setPriceMax(int priceMax) {
		this.priceMax = priceMax;
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
		this.location = StringUtils.isBlank(location) ? "all" : location.trim();
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
		String loc = convertLocation(this.location);
		int minPrice = this.priceMin > this.priceMax ? this.priceMax : this.priceMin;
		int maxPrice = this.priceMin > this.priceMax ? this.priceMin : this.priceMax;
		if (minPrice == maxPrice) {
			if (maxPrice == 0) { // 如果minPrice == maxPrice == 0
				maxPrice = 1;
			} else {
				minPrice--;
				maxPrice++;
			}
		}

		String key = this.searchKey;
		try {
			key = URLEncoder.encode(key, "gbk");
		} catch (UnsupportedEncodingException e) {
		}
		String curPage = String.valueOf((page - 1) * 40);

		url.append("http://s.taobao.com/search?tab=all&loc=").append(loc);
		url.append("&filter=reserve_price[").append(minPrice).append(',').append(maxPrice).append(']');
		url.append("&fs=0&stats_click=search_radio_all:1&initiative_id=staobaoz_").append(sdf.format(new Date()));
		url.append("&q=").append(key);
		url.append("&filterFineness=2&promote=0&s=").append(curPage);
		url.append("#J_relative");
		return url.toString();
	}

	/** 转换所在地 */
	private String convertLocation(String loc) {
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
		case "海外":
			loc = "美国,英国,法国,瑞士,澳洲,新西兰,加拿大,奥地利,韩国,日本,德国,意大利,西班牙,俄罗斯,泰国,印度,荷兰,新加坡,其它国家";
			break;
		case "全国":
			loc = "all";
			break;
		default:
			break;
		}
		try {
			loc = URLEncoder.encode(loc, "gbk");
		} catch (UnsupportedEncodingException e1) {
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
}
