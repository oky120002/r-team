/**
 * 
 */
package com.r.core.desktop.ctrl.swing;

import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.concurrent.ConcurrentSkipListSet;

import javax.swing.AbstractListModel;

/**
 * 排序的ListModel<br />
 * 利用 TreeSet 来排序
 * 
 * @author Administrator
 * 
 */
public class SortedListModel<T> extends AbstractListModel {
	private static final long serialVersionUID = 8505134122262609492L;

	private final SortedSet<T> model;

	public SortedListModel() {
		this.model = new ConcurrentSkipListSet<T>();
	}

	@Override
	public int getSize() {
		return this.model.size();
	}

	@Override
	public T getElementAt(final int index) {
		final Iterator<T> it = this.model.iterator();
		for (int i = 0;; i++) {
			final T e = it.next();
			if (i == index) {
				return e;
			}
		}
	}

	public void addElement(final T element) {
		if (this.model.add(element)) {
			this.fireContentsChanged(this, 0, this.getSize());
		}
	}

	public void addAll(final List<T> values) {
		this.model.addAll(values);
		this.fireContentsChanged(this, 0, this.getSize());
	}

	public void clear() {
		this.model.clear();
		this.fireContentsChanged(this, 0, this.getSize());
	}

	public boolean contains(final T element) {
		return this.model.contains(element);
	}

	public boolean removeElement(final T element) {
		final boolean removed = this.model.remove(element);
		if (removed) {
			this.fireContentsChanged(this, 0, this.getSize());
		}
		return removed;
	}

	public void removeAll(final List<T> values) {
		this.model.removeAll(values);
		this.fireContentsChanged(this, 0, this.getSize());
	}
}
