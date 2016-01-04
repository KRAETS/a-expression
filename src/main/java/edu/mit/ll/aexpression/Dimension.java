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
import java.util.List;

public class Dimension {
	@Expose
	private String name = null;
	private List<Dimension> virtualDimensions = new ArrayList<Dimension>();
	private int position = 0;

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public void setVirtualDimensions(List<Dimension> virtualDimensions) {
		this.virtualDimensions = virtualDimensions;
	}

	public Dimension(String name) {
		this.name = name;
	}

	public void addVirtualDimension(Dimension d) {
		virtualDimensions.add(d);
	}

	public List<Dimension> getVirtualDimensions() {
		return virtualDimensions;
	}

	public String getName() {
		return this.name;
	}
	@Override
	public String toString() {
		return this.name;
	}
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Dimension)){
			return false;

		}
		else{
			Dimension dObj = (Dimension)obj;
			boolean samename = dObj.getName().equals(this.getName());
			boolean samevirtual = dObj.getVirtualDimensions().equals(this.getVirtualDimensions());
			return (samename && samevirtual);
		}
	}
}
