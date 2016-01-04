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

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.junit.*;
import org.junit.rules.ExpectedException;

import edu.mit.ll.aexpression.Dimension;
import edu.mit.ll.aexpression.DimensionSet;
import edu.mit.ll.aexpression.DimensionSets;
import edu.mit.ll.aexpression.F2DMap;
import edu.mit.ll.aexpression.Field;
import edu.mit.ll.aexpression.PreProcessMaps;
import edu.mit.ll.aexpression.RegistryOperators;
import edu.mit.ll.aexpression.Table;
import edu.mit.ll.aexpression.Tables;
import edu.mit.ll.aexpression.Tag;
import edu.mit.ll.aexpression.Tag2FieldMap;
import edu.mit.ll.convert.RegistryIn;



public class LegacyRegistryJsonConverter {
	

    static String testName = "LegacyRegistryJsonConverter";    
    static Logger log = Logger.getLogger(LegacyRegistryJsonConverter.class);
    



    
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @BeforeClass
    static public void fileSetUp() throws Exception {
    	

    }
    
    @Test
    public void test() {
		// Test1 files
    	String T2FMapFileName = "src/test/resources/classic/registry/Table2FieldMap.json";
		String F2DMapFileName = "src/test/resources/classic/registry/Field2DimensionMap.json";
		String Tag2FieldMapFileName = "src/test/resources/classic/registry/Tag2FieldMap.json";
		String Ds2DMapFileName = "src/test/resources/classic/registry/DimensionSet2DimensionMap.json";
		String Ts2TMapFileName = "src/test/resources/classic/registry/TagScheme2TagMap.json";
		String VirtualDimensionMapFileName = "src/test/resources/classic/registry/VirtualDimensionsMap.json";
		
		String LegacyRegistryFileName = "src/test/resources/classic/registry/registry.json";
		RegistryIn rin = new RegistryIn();
		rin.readLegacyRegistryJson(LegacyRegistryFileName);
		
		PreProcessMaps ffs = new PreProcessMaps();
		ffs.setT2FMapFileName(T2FMapFileName);
		
		Tables table = rin.populateT2FMap(T2FMapFileName);
		ffs.setTables(table);
		F2DMap f2dmap = rin.populateField2DimensionMap(F2DMapFileName);
		ffs.setF2dmap(f2dmap);
		
		Tag2FieldMap tag2fieldmap = rin.populateTag2FieldMap(Tag2FieldMapFileName);
		ffs.setT2FMapFileName(Tag2FieldMapFileName);
		
		DimensionSets dss = rin.populateDS2DMap(Ds2DMapFileName);
		ffs.setDs2DMapFileName(Ds2DMapFileName);
		
		
		try {
			/*
			PrintWriter out = new PrintWriter(T2FMapFileName);
			rin.writeJsonStreamT2FMap(out, table);
			
			PrintWriter out1 = new PrintWriter(F2DMapFileName);
			rin.writeJsonStreamF2DMap(out1, f2dmap);
			
			PrintWriter out2 = new PrintWriter(Tag2FieldMapFileName);
			rin.writeJsonStreamTag2FieldMap(out2, tag2fieldmap);
			*/
			PrintWriter out3 = new PrintWriter(Ds2DMapFileName);
			rin.writeJsonStreamDS2DMap(out3, dss);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*	
	
		ffs.setF2DMapFileName(F2DMapFileName);
		ffs.setTag2FieldMapFileName(Tag2FieldMapFileName);
		ffs.setDs2DMapFileName(Ds2DMapFileName);
		ffs.setTs2TMapFileName(Ts2TMapFileName);
		ffs.setVirtualDimensionMapFileName(VirtualDimensionMapFileName);
		RegistryOperators ro = new RegistryOperators();	

		ffs.init();
		*/
		
		
    }


}
