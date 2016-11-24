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

import javax.xml.bind.DatatypeConverter;

public class Period {
	private String start_time = null;
	private String end_time = null;
	public Period(String start, String end) {
		this.start_time = start;
		this.end_time = end;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

    public long getTimeLongVal( String dateTimeString ) {
		java.util.Calendar cal = DatatypeConverter.parseDateTime(dateTimeString);
        long longval = cal.getTimeInMillis();
        return longval;
    }
    @Override
    public String toString(){
    	return "["+start_time+","+end_time+"]";
    }
    
    @Override
	public boolean equals(Object d){
		if(d instanceof Period)
			return ((Period)d).getStart_time()==this.getStart_time()&&((Period)d).getEnd_time()==this.getEnd_time();
		else return false;
	}
}
