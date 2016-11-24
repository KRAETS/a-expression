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
import java.util.Iterator;
import java.util.List;

public class Table {
	private List<Period> durations = null;
	@Expose
	private List<Field> fields = null;
	@Expose
	private String name = null;

	public Table(String name) {
		this.name = name;
		this.fields = new ArrayList<Field>();
	}

	public Table(String name, List<Field> fl) {
		this.name = name;
		this.fields = fl;

	}

	public void addField(Field d) {
		fields.add(d);
	}

	public boolean fieldAvailable(Period in_du) {
		if (durations != null) {
			for (Period du : durations) {
				if ((du.getTimeLongVal(du.getStart_time()) <= in_du
						.getTimeLongVal(in_du.getStart_time()))
						&& du.getTimeLongVal(du.getEnd_time()) >= du
								.getTimeLongVal(du.getEnd_time()))
					return true;
			}
		}
		return false;
	}

	public List<Period> getDurations() {
		return durations;
	}

	public Field getField(String name) {
		for (Iterator<Field> fIter = fields.iterator(); fIter.hasNext();) {
			Field f = fIter.next();
			if (f.getName().equals(name))
				return f;
		}
		return null;
	}

	public List<Field> getFields() {
		return fields;
	}

	public String getName() {
		return this.name;
	}

	public void printName() {
		System.out.println(this.name);
	}

	public void printString() {
		System.out.println("\n" + "Fields: ");
		for (Field f : this.fields) {
			System.out.println(" : " + f.getName());
		}

	}

	public void setDurations(List<Period> durations) {
		this.durations = durations;
	}

	public void setListOfFields(List<Field> fields) {
		this.fields = fields;
	}

	@Override
	public String toString() {
		return "Table [durations=" + durations + ", fields=" + fields
				+ ", name=" + name + "]";
	}
}
