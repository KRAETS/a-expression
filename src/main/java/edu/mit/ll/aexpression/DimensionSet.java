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
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class DimensionSet {
	@Expose
	private String name = null;
	@Expose
	private List<Dimension> dimensions = null;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDimensions(List<Dimension> dimensions) {
		this.dimensions = dimensions;
	}

	public DimensionSet(String name) {
		this.name = name;
		this.dimensions = new ArrayList<Dimension>();
	}

	public void addDimension(Dimension d) {
		dimensions.add(d);
	}

	public List<Dimension> getDimensions() {
		return dimensions;
	}

	public Boolean inDimensionSet(Dimension d) {
		for (Dimension di : this.dimensions) {
			if (di.getName().equals(d.getName()))
				return true;
		}
		return false;
	}

	public boolean removeDimension(Dimension dimin) {
		for (Dimension di : this.dimensions) {
			if (di == dimin) {
				this.dimensions.remove(di);
				return true;
			}
		}
		return false;
	}

	public String toString() {
		return this.name;
	}

	public static DimensionSet generateDimensionSet(List<Dimension> dimensionlist){
		DimensionSet d = new DimensionSet("temporary");
		d.setDimensions(dimensionlist);
		return d;

	}
	public static DimensionSet generateDimensionSet(Set<Dimension> dimensionlist){
		List<Dimension> tempList = new LinkedList<Dimension>();
		for(Dimension item:dimensionlist){
			tempList.add(item);
		}
		return DimensionSet.generateDimensionSet(tempList);

	}

}
