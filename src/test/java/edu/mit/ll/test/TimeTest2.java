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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import edu.mit.ll.aexpression.Dimension;
import edu.mit.ll.aexpression.DimensionSet;
import edu.mit.ll.aexpression.Field;
import edu.mit.ll.aexpression.Period;
import edu.mit.ll.aexpression.PreProcessMaps;
import edu.mit.ll.aexpression.RegistryOperators;
import edu.mit.ll.aexpression.Table;
import edu.mit.ll.aexpression.Tag;


public class TimeTest2 {
	
    static String testName = "TimeTest1";    
    static Logger log = Logger.getLogger(TimeTest2.class);
    



    
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @BeforeClass
    static public void fileSetUp() throws Exception {
    	

    }
    
    @Test
    public void test() {
		// Test1 files
    	String T2FMapFileName = "src/test/resources/timetest2/Table2FieldMap.json";
		String F2DMapFileName = "src/test/resources/timetest2/Field2DimensionMap.json";
	


		PreProcessMaps ffs = new PreProcessMaps();
		
		ffs.setT2FMapFileName(T2FMapFileName);
		ffs.setF2DMapFileName(F2DMapFileName);


		RegistryOperators ro = new RegistryOperators();
		
		

		ffs.init();
		/*
		 * Test1
		 */
		// Resolve Fields with just Dimension
		List<String> matchDimension = new ArrayList<String>();
		matchDimension.add("d1");
		List<Field> fs = ro.matchTablesDimensionsFields(ffs.getTables().getTables(),
				matchDimension);
		List<Period> in_durations = new ArrayList<Period>();
		in_durations.add(new Period("2013-05-30T09:31:00","2013-05-30T09:33:00"));
		
		List<Field> fs2 = ro.matchFieldsPeriodsFields(fs, in_durations);


    }



}
