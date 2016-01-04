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

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Fields {
	private Set<Field> allfields = new HashSet<Field>();
	public Set<Field> getFields(){
		return allfields;
	}
	public void setFields(List<Field> fieldList){
		for(Field f: fieldList){
			this.allfields.add(f);
		}
	}
	public void setFields(Set<Field> fieldSet){
		this.allfields = fieldSet;
	}
	public void addField(Field f){
		this.allfields.add(f);
	}
	public List<Field> getAllFieldsList(){
		
		List<Field> afl = new LinkedList<Field>();
		for(Field f : allfields){
			afl.add(f);
		}
		return afl;
	}
	
}
