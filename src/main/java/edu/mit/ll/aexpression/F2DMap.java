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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class F2DMap {
	
	@SerializedName("f2dmap")
	@Expose
	List<F2DMapItem> f2dmap = null;

	public List<F2DMapItem> getF2dmap() {
		return f2dmap;
	}

	public void setF2dmap(List<F2DMapItem> f2dmap) {
		this.f2dmap = f2dmap;
	}

	public F2DMap() {
		f2dmap = new ArrayList<F2DMapItem>();
	}
	
	public String getDimension(String field) {
		
		for (F2DMapItem fmap: f2dmap) {
			if (field.equalsIgnoreCase(fmap.getField()) )
					return (fmap.getDimension());
		}
		
		return null;
	}

	public Set<String> getFields(String dim) {
		Set<String> fields = new HashSet<String>();
		for (F2DMapItem fmap: f2dmap) {
			if (dim.equalsIgnoreCase(fmap.getDimension())) 
					fields.add(fmap.getDimension());
		}
		if (fields.size() > 0) return fields;
		return null;
		
	}
}
