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
	private Number buyPrestige; // 买号信誉
	@Column
	private Number takeTaskByDay; // 今日接任务数
	@Column
	private Number takeTaskByWeek; // 本周接任务数
	@Column
	private Boolean isIDCard;// 是否实名认证
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
	public Number getBuyPrestige() {
		return buyPrestige;
	}

	/**
	 * @param buyPrestige
	 *            the buyPrestige to set
	 */
	public void setBuyPrestige(Number buyPrestige) {
		this.buyPrestige = buyPrestige;
	}

	/**
	 * @return the takeTaskByDay
	 */
	public Number getTakeTaskByDay() {
		return takeTaskByDay;
	}

	/**
	 * @param takeTaskByDay
	 *            the takeTaskByDay to set
	 */
	public void setTakeTaskByDay(Number takeTaskByDay) {
		this.takeTaskByDay = takeTaskByDay;
	}

	/**
	 * @return the takeTaskByWeek
	 */
	public Number getTakeTaskByWeek() {
		return takeTaskByWeek;
	}

	/**
	 * @param takeTaskByWeek
	 *            the takeTaskByWeek to set
	 */
	public void setTakeTaskByWeek(Number takeTaskByWeek) {
		this.takeTaskByWeek = takeTaskByWeek;
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

	@Override
	public String toString() {
		if (StringUtils.isNotBlank(id)) {
			StringBuilder sb = new StringBuilder();
			sb.append(buyAccount).append(':');
			sb.append(takeTaskByDay).append('|');
			sb.append(takeTaskByWeek).append('|');
			sb.append(buyPrestige);
			return sb.toString();
		}
		return "---请选择---";

	}

}
