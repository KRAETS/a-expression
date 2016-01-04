/*******************************************************************************
 * Copyright (c) 2013 Massachusetts Institute of Technology
 * 
 * Not with standing any copyright notice, U.S. Government rights in this work
 * are defined by DFARS 252.227-7013 or DFARS 252.227-7014 as detailed below.
 * Use of this work other than as specifically authorized by the U.S.
 * Government may violate any copyrights that exist in this work.
 * 
 * UNLIMITED RIGHTS
 * DFARS Clause reference: 252.227-7013 (a)(16) and 252.227-7014 (a)(16)
 * Unlimited Rights. The Government has the right to use, modify, reproduce, perform,
 * display, release or disclose this (technical data or computer software) in whole or in part, in
 * any manner, and for any purpose whatsoever, and to have or authorize others to do so.
 * 
 * THE SOFTWARE IS PROVIDED TO YOU ON AN "AS IS" BASIS.
 ******************************************************************************/
package edu.mit.ll.aexpression;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class Field {
	@Expose
	private String name = null;
	private Dimension dimension = null;
	private Table table = null;
	private List<Tag> tags = null;
	private List<Period> durations = null;

	public List<Period> getDurations() {
		return durations;
	}

	public void setDurations(List<Period> durations) {
		this.durations = durations;
	}

	public Field(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public Dimension getDimension() {
		return dimension;
	}

	public void setDimension(Dimension dimension) {
		this.dimension = dimension;
	}

	public void addTag(Tag t) {
		if (tags == null)
			tags = new ArrayList<Tag>();
		tags.add(t);
	}

	public List<Tag> getTags() {
		return tags;
	}
	@Override
	public String toString() {
		return (name);
	}

	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
	}

	public boolean fieldAvailable(Period in_du) {
		for (Period du: durations) {
			if ((du.getTimeLongVal(du.getStart_time()) <= in_du.getTimeLongVal(in_du.getStart_time())) && du.getTimeLongVal(du.getEnd_time()) >= du.getTimeLongVal(du.getEnd_time())) return true;
		}
		return false;
	}

}
