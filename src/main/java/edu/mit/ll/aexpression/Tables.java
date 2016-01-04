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
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Tables {
	
	
	String name = null;

	public Tables(String name) {
		this.name = name;
		tables = new ArrayList<Table>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Tables(){
		this.name = "";
		this.tables = new LinkedList<Table>();
	}

	public List<Table> getTables() {
		return tables;
	}

	public void setTables(List<Table> tables) {
		this.tables = tables;
	}
	
	public void addTable(Table t){
		this.tables.add(t);
	}

	@SerializedName("tables")
	@Expose
	List<Table> tables = null;

	public Table getTable(String name) {
		for (Iterator<Table> tIter = tables.iterator(); tIter.hasNext();) {
			Table t = tIter.next();
			if (t.getName().equals(name))
				return t;
		}
		return null;
	}

	public Field getField(String table, String field) {
		Table t = getTable(table);
		List<Field> fields = t.getFields();
		if (fields != null) {
			for(Iterator<Field> fIter = fields.iterator(); fIter.hasNext();) {
				Field fieldFound = fIter.next();
				if (fieldFound.getName().equals(field)) return fieldFound;
			}
		}
		return null;
	}

}
