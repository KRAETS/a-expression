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

public class Tag {
	private String name;
	private TagScheme ts;
	
	public Tag(){};
	
	public Tag(String name){
		this.name = name; 
	}
	
	public Tag(TagScheme ts, String name) {
		this.ts = ts;
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	public void setName(String s){
		this.name=s;
	}
	public void setTs(TagScheme ts) {
		this.ts = ts;
	}
	public TagScheme getTs() {
		return ts;
	}
	public String toString() {
		return this.ts.getName() +":"+ this.name;
	}
	@Override
	public boolean equals(Object o){
		if(!(o instanceof Tag))
		{
			return false;
		}
		else{
			Tag t = (Tag)o;
			return (this.name.equals(t.getName()))&&(this.ts.equals(t.getTs()));
		}
	}
}
