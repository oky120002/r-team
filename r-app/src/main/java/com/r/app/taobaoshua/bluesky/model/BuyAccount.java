/**
 * 
 */
package com.r.app.taobaoshua.bluesky.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.swing.Icon;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.GenericGenerator;

import com.r.app.taobaoshua.bluesky.BlueSky;
import com.r.app.taobaoshua.bluesky.model.enumtask.TaskAddr;

/**
 * 买号
 * 
 * @author oky
 * 
 */
@Entity
@Table(name = "buyaccount")
public class BuyAccount {

	public static final BuyAccount EMPTY = new BuyAccount("---请选择---");

	@Id
	@Column
	@GeneratedValue(generator = "sys_uuid")
	@GenericGenerator(name = "sys_uuid", strategy = "uuid")
	private String id;
	@Column
	@Enumerated(EnumType.STRING)
	private TaskAddr type; // 任务区
	@Column
	private String buyAccount; // 买号
	@Column
	private Integer buyPrestige; // 买号信誉
	@Column
	private Integer takeTaskByDay; // 今日接任务数
	@Column
	private Integer takeTaskByWeek; // 本周接任务数
	@Column
	private Boolean isIDCard;// 是否实名认证
	@Column
	private String taobaoTid; // 买号所属区域id
	@Column
	private Boolean isEnable;// 启用禁用

	public BuyAccount() {
		super();
	}

	public BuyAccount(String buyAccount) {
		super();
		this.buyAccount = buyAccount;
	}

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
	 * @return the type
	 */
	public TaskAddr getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(TaskAddr type) {
		this.type = type;
	}

	/**
	 * @return the buyAccount
	 */
	public String getBuyAccount() {
		return buyAccount;
	}

	/**
	 * @param buyAccount
	 *            the buyAccount to set
	 */
	public void setBuyAccount(String buyAccount) {
		this.buyAccount = buyAccount;
	}

	/**
	 * @return the buyPrestige
	 */
	public Integer getBuyPrestige() {
		return buyPrestige;
	}

	/**
	 * @param buyPrestige
	 *            the buyPrestige to set
	 */
	public void setBuyPrestige(Integer buyPrestige) {
		this.buyPrestige = buyPrestige;
	}

	/**
	 * @return the takeTaskByWeek
	 */
	public Integer getTakeTaskByWeek() {
		return takeTaskByWeek;
	}

	/**
	 * @param takeTaskByWeek
	 *            the takeTaskByWeek to set
	 */
	public void setTakeTaskByWeek(Integer takeTaskByWeek) {
		this.takeTaskByWeek = takeTaskByWeek;
	}

	/**
	 * @return the takeTaskByDay
	 */
	public Integer getTakeTaskByDay() {
		return takeTaskByDay;
	}

	/**
	 * @param takeTaskByDay
	 *            the takeTaskByDay to set
	 */
	public void setTakeTaskByDay(Integer takeTaskByDay) {
		this.takeTaskByDay = takeTaskByDay;
	}

	/**
	 * @return the isIDCard
	 */
	public Boolean getIsIDCard() {
		return isIDCard;
	}

	public boolean isIDCard() {
		return isIDCard == null ? false : isIDCard.booleanValue();
	}

	public Icon getIsIDCardIcon() {
		return isIDCard() ? BlueSky.getInstance().getIcon("ShiMing.gif") : null;
	}

	/**
	 * @param isIDCard
	 *            the isIDCard to set
	 */
	public void setIsIDCard(Boolean isIDCard) {
		this.isIDCard = isIDCard;
	}

	/**
	 * @return the taobaoTid
	 */
	public String getTaobaoTid() {
		return taobaoTid;
	}

	/**
	 * @param taobaoTid
	 *            the taobaoTid to set
	 */
	public void setTaobaoTid(String taobaoTid) {
		this.taobaoTid = taobaoTid;
	}

	/**
	 * @return the isEnable
	 */
	public Boolean getIsEnable() {
		return isEnable;
	}

	public boolean isEnable() {
		return isEnable == null ? false : isEnable.booleanValue();
	}

	/**
	 * @param isEnable
	 *            the isEnable to set
	 */
	public void setIsEnable(Boolean isEnable) {
		this.isEnable = isEnable;
	}

	public String getBuyDatas() {
		StringBuilder sb = new StringBuilder();
		sb.append(takeTaskByDay == null ? -1 : takeTaskByDay.intValue()).append('|');
		sb.append(takeTaskByWeek == null ? -1 : takeTaskByWeek.intValue()).append('|');
		sb.append(buyPrestige == null ? -1 : buyPrestige.intValue());
		return sb.toString();
	}

	@Override
	public String toString() {
		if (StringUtils.isNotBlank(id)) {
			return buyAccount;
		}
		return "---请选择---";

	}

}
