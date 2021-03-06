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

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class TagSchemes {
	private String name = null;
	private List<TagScheme> tagSchemes = new LinkedList<TagScheme>();
	
	public void addTagScheme(TagScheme ts) {
		tagSchemes.add(ts);
	}

	public List<TagScheme> getTagSchemes() {
		return tagSchemes;
	}

	public void setTagSchemes(List<TagScheme> tagSchemes) {
		this.tagSchemes = tagSchemes;
	}

	public TagScheme getTagScheme(String name) {
		for (Iterator<TagScheme> tIter = tagSchemes.iterator(); tIter.hasNext();) {
			TagScheme t = tIter.next();
			if (t.getName().equals(name))
				return t;
		}
		return null;
	}

	public Tag getTag(String in_ts, String in_tag) {
		for (TagScheme ts : this.tagSchemes) {
			if (ts.getName().equals(in_ts)) {
				for (Tag tag : ts.getTags()) {
					if (tag.getName().equals(in_tag)) {
						return tag;
					}
				}
			}
		}
		return null;
	}

}
