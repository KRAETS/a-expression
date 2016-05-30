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
package edu.mit.ll.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import edu.mit.ll.aexpression.DimensionSet;
import edu.mit.ll.aexpression.Operator;
import edu.mit.ll.aexpression.PreProcessMaps;


public class OP_Test2 {

    static String testName = "Test1";    
    static Logger log = Logger.getLogger(Test1.class);
    



    
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @BeforeClass
    static public void fileSetUp() throws Exception {
    	

    }
    
    @Test
    public void test() {
		
    	
    	/*
		 * Test 2
		 */
		// Test2 files
		String Ds2DMapFileName = "src/test/resources/op_test2/DimensionSet2DimensionMap.json";
		String Operator2DimensionSetMapFileName = "src/test/resources/op_test2/Operator2DimensionSetMap1.json";
		PreProcessMaps ffs = new PreProcessMaps();
		ffs.setDs2DMapFileName(Ds2DMapFileName);
		ffs.setOperator2DimensionSetMapFileName(Operator2DimensionSetMapFileName);
		ffs.init();
		String s = "ds15";
		DimensionSet ds1 = ffs.getDs2dmap().getDimensionSet(s);
		String t = "ds11";
		DimensionSet ds2 = ffs.getDs2dmap().getDimensionSet(t);
		List<DimensionSet> dss = new ArrayList<DimensionSet>();
		dss.add(ds2);
	//	boolean yes = ffs.operatorReachabilityDs2Ds(s, t);
	//	boolean yes = ffs.operatorReachabilityDs2Ds(ds1, dss);
		List<Operator> ops = ffs.reachDimensionSetDimensionSetPaths(ds1, ds2);
    }

}
