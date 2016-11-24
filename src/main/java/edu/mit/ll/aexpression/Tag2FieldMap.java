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

import java.util.*;

public class Tag2FieldMap {
	
	public Tag2FieldMap() {
		
		this.tag2fieldItems = new ArrayList<Tag2FieldItem>();
	}

	@Expose
	@SerializedName("tag2fieldItems")
	List<Tag2FieldItem> tag2fieldItems = null;

	public List<Tag2FieldItem> getTag2fieldItems() {
		return tag2fieldItems;
	}

	public void setTag2fieldItems(List<Tag2FieldItem> tag2fieldItems) {
		this.tag2fieldItems = tag2fieldItems;
	}

	public Collection<? extends String> getTags(String f) {
		Set<String> tags = new HashSet<String>();
		for (Tag2FieldItem tf: tag2fieldItems) {
			 if (f.equalsIgnoreCase(tf.getField())) 
				 tags.add(tf.getTag());
		}
		if (tags.size()> 0) return tags;
		return null;
	}

}
