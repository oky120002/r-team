package com.r.qqcard.card.qqhome.impl;

import com.r.qqcard.card.qqhome.QQHome;
import com.r.qqcard.card.qqhome.QQHomeBoxChange;
import com.r.qqcard.card.qqhome.QQHomeBoxStore;
import com.r.qqcard.card.qqhome.QQHomeBoxStove;
import com.r.qqcard.card.qqhome.QQHomeElf;
import com.r.qqcard.card.qqhome.QQHomeHello;
import com.r.qqcard.card.qqhome.QQHomeInfo;
import com.r.qqcard.card.qqhome.QQHomeRefineguide;
import com.r.qqcard.card.qqhome.QQHomeRentSlot;
import com.r.qqcard.card.qqhome.QQHomeUser;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * QQ魔法卡片获取到的主要信息实体<br />
 * 此包内的所有QQHome开头的类都是支持bean类
 * 
 * @author oky
 * 
 */
@XStreamAlias(value = "QQHOME")
public class QQHomeImpl implements QQHome {

	@XStreamAsAttribute
	private int code;
	@XStreamAlias(value = "hello")
	private QQHomeHelloImpl hello; // 欢迎信息
	@XStreamAlias(value = "user")
	private QQHomeUserImpl user; // 账户信息
	@XStreamAlias(value = "elf")
	private QQHomeElfImpl elf; // 宠物信息
	@XStreamAlias(value = "info")
	private QQHomeInfoImpl info; // 信息?
	@XStreamAlias(value = "changebox")
	private QQHomeBoxChangeImpl changeBox; // 换卡箱
	@XStreamAlias(value = "storebox")
	private QQHomeBoxStoreImpl storebox; // 卡片箱
	@XStreamAlias(value = "stovebox")
	private QQHomeBoxStoveImpl stovebox; // 炼卡箱
	@XStreamAlias(value = "rent_slot")
	private QQHomeRentSlotImpl rentSlot; // 偷卡信息
	@XStreamAlias(value = "refineguide")
	private QQHomeRefineguideImpl refineguide; // ?

	/**
	 * @return the code
	 */
	@Override
	public int getCode() {
		return code;
	}

	/**
	 * @return the info
	 */
	@Override
	public QQHomeInfo getInfo() {
		return info;
	}

	/**
	 * @param info
	 *            the info to set
	 */
	public void setInfo(QQHomeInfoImpl info) {
		this.info = info;
	}

	/**
	 * @return the rentSlot
	 */
	@Override
	public QQHomeRentSlot getRentSlot() {
		return rentSlot;
	}

	/**
	 * @param rentSlot
	 *            the rentSlot to set
	 */
	public void setRentSlot(QQHomeRentSlotImpl rentSlot) {
		this.rentSlot = rentSlot;
	}

	/**
	 * @return the refineguide
	 */
	@Override
	public QQHomeRefineguide getRefineguide() {
		return refineguide;
	}

	/**
	 * @param refineguide
	 *            the refineguide to set
	 */
	public void setRefineguide(QQHomeRefineguideImpl refineguide) {
		this.refineguide = refineguide;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(int code) {
		this.code = code;
	}

	/**
	 * @return the hello
	 */
	@Override
	public QQHomeHello getHello() {
		return hello;
	}

	/**
	 * @param hello
	 *            the hello to set
	 */
	public void setHello(QQHomeHelloImpl hello) {
		this.hello = hello;
	}

	/**
	 * @return the user
	 */
	@Override
	public QQHomeUser getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(QQHomeUserImpl user) {
		this.user = user;
	}

	/**
	 * @return the elf
	 */
	@Override
	public QQHomeElf getElf() {
		return elf;
	}

	/**
	 * @param elf
	 *            the elf to set
	 */
	public void setElf(QQHomeElfImpl elf) {
		this.elf = elf;
	}

	/**
	 * @return the changeBox
	 */
	@Override
	public QQHomeBoxChange getChangeBox() {
		return changeBox;
	}

	/**
	 * @param changeBox
	 *            the changeBox to set
	 */
	public void setChangeBox(QQHomeBoxChangeImpl changeBox) {
		this.changeBox = changeBox;
	}

	/**
	 * @return the storebox
	 */
	@Override
	public QQHomeBoxStore getStoreBox() {
		return storebox;
	}

	/**
	 * @param storebox
	 *            the storebox to set
	 */
	public void setStorebox(QQHomeBoxStoreImpl storebox) {
		this.storebox = storebox;
	}

	/**
	 * @return the stovebox
	 */
	@Override
	public QQHomeBoxStove getStoveBox() {
		return stovebox;
	}

	/**
	 * @param stovebox
	 *            the stovebox to set
	 */
	public void setStovebox(QQHomeBoxStoveImpl stovebox) {
		this.stovebox = stovebox;
	}
}
