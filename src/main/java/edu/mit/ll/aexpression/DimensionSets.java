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
import java.util.List;

public class DimensionSets {
	
	private String name = null;
	
	@Expose
	@SerializedName("dimensionSets")
	private List<DimensionSet> dimensionSets = null;
	
	public DimensionSets() {
		super();
		this.dimensionSets = new ArrayList<DimensionSet>();
	}

	public List<DimensionSet> getDimensionSets() {
		return dimensionSets;
	}

	public void setDimensionSets(List<DimensionSet> dimensionSets) {
		this.dimensionSets = dimensionSets;
	}
	public void addDimensionSet(DimensionSet target){
		this.dimensionSets.add(target);
	}
	public DimensionSet getDimensionSet(String dsname) {
		 for(DimensionSet ds: dimensionSets) {
			 if (ds.getName().equals(dsname)) return ds;
		 }
		
		return null;
	}

}
