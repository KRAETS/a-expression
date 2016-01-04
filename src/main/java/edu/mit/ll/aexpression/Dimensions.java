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
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Dimensions {
	Set<Dimension> dimensions = null;

	public Set<Dimension> getDimensions() {
		return dimensions;
	}

	public void setDimensions(Set<Dimension> dimensions) {
		this.dimensions = new HashSet<Dimension>();
		for(Dimension d:dimensions){
			this.dimensions.add(d);
		}
	}

	public Dimensions() {
		dimensions = new HashSet<Dimension>();
	}
	
	public Dimensions(List<Dimension> dims) {
		dimensions = new HashSet<Dimension>();
		for (Dimension d: dims) {
			dimensions.add(d);
		}
	}
	public void addDimension(Dimension dim) {
		dimensions.add(dim);
	}

	public Dimension getDimension(String name) {
		for (Iterator<Dimension> dIter = dimensions.iterator(); dIter.hasNext();) {
			Dimension dim = dIter.next();
			if (dim.getName().equals(name))
				return dim;
		}
		return null;
	}
	
	public String getDimensionsString() {
		String retD = "";
		for (Dimension d: dimensions) {
			retD.concat(" " +d.getName());
			
		}
		return retD;
	}
}
