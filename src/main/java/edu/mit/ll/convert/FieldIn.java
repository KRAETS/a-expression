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
package edu.mit.ll.convert;

import java.util.ArrayList;
import java.util.List;

public class FieldIn {
	@Override
	public String toString() {
		return "FieldIn [id=" + id + ", name=" + name + ", tags=" + tags
				+ ", inSecondaryIndex=" + inSecondaryIndex + ", dim=" + dim
				+ "]";
	}
	String id = null;
	String name = null;
	List<String> tags = null;
	String inSecondaryIndex = null;
	String dim = null;
	
	
	
	public String getDim() {
		return dim;
	}
	public void setDim(String dim) {
		this.dim = dim;
	}
	public FieldIn(String id, String name) {
		super();
		this.id = id;
		this.name = name;
		this.tags = new ArrayList<String>();
	}
	public String getInSecondaryIndex() {
		return inSecondaryIndex;
	}
	public void setInSecondaryIndex(String inSecondaryIndex) {
		this.inSecondaryIndex = inSecondaryIndex;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getTags() {
		return tags;
	}
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	

}
