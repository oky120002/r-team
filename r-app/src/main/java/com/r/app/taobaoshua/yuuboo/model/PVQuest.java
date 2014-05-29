/**
 * 
 */
package com.r.app.taobaoshua.yuuboo.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.r.app.taobaoshua.taobao.TaoBaoItemSearch;

/**
 * @author oky
 * 
 */
public class PVQuest implements TaoBaoItemSearch {
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

	@Override
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

	@Override
	public int getPriceMin() {
		return priceMin;
	}

	@Override
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

	@Override
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

	@Override
	public boolean isTmall() {
		return isTmall;
	}

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
}
