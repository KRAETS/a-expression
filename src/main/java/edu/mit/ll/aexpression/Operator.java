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

import java.util.ArrayList;
import java.util.List;

public class Operator {
	private String name = null;
	private List<DimensionSet> dimensionSetIn = null;
	private DimensionSet dimensionSetOut = null;
	private List<Dimension> dimensionsOut = null;

	// private List<String> scalars = null;

	public Operator(String name) {
		this.name = name;
	}

	public Operator(String name, List<DimensionSet> inset, DimensionSet outSet,
			List<Dimension> outDims) {
		this.name = name;
		this.dimensionSetOut = outSet;
		this.dimensionSetIn = inset;
		this.dimensionsOut = outDims;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<DimensionSet> getDimensionSetIn() {
		return dimensionSetIn;
	}

	public void setDimensionSetIn(List<DimensionSet> dimensionSetIn) {
		this.dimensionSetIn = dimensionSetIn;
	}


	public void addOutputDimension(Dimension d) {
		if (dimensionsOut == null)
			dimensionsOut = new ArrayList<Dimension>();
		dimensionsOut.add(d);
	}

	public DimensionSet getDimensionSetOut() {
		return dimensionSetOut;
	}

	public void setDimensionSetOut(DimensionSet dimensionSetOut) {
		this.dimensionSetOut = dimensionSetOut;
	}

	public List<Dimension> getDimensionsOut() {
		return dimensionsOut;
	}

	public void setDimensionsOut(List<Dimension> dimensionOut) {
		this.dimensionsOut = dimensionOut;
	}

}
