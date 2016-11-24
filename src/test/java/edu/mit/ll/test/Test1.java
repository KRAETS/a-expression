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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.junit.*;
import org.junit.rules.ExpectedException;

import edu.mit.ll.aexpression.Dimension;
import edu.mit.ll.aexpression.DimensionSet;
import edu.mit.ll.aexpression.Field;
import edu.mit.ll.aexpression.PreProcessMaps;
import edu.mit.ll.aexpression.RegistryOperators;
import edu.mit.ll.aexpression.Table;
import edu.mit.ll.aexpression.Tag;



public class Test1 {
	

    static String testName = "Test1";    
    static Logger log = Logger.getLogger(Test1.class);
    



    
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @BeforeClass
    static public void fileSetUp() throws Exception {
    	

    }
    
    @Test
    public void test() {
		// Test1 files
    	String T2FMapFileName = "src/test/resources/test1/Table2FieldMap.json";
		String F2DMapFileName = "src/test/resources/test1/Field2DimensionMap.json";
		String Tag2FieldMapFileName = "src/test/resources/test1/Tag2FieldMap.json";
		String Ds2DMapFileName = "src/test/resources/test1/DimensionSet2DimensionMap.json";
		String Ts2TMapFileName = "src/test/resources/test1/TagScheme2TagMap.json";
		String VirtualDimensionMapFileName = "src/test/resources/test1/VirtualDimensionsMap.json";
		PreProcessMaps ffs = new PreProcessMaps();
		
		ffs.setT2FMapFileName(T2FMapFileName);
		ffs.setF2DMapFileName(F2DMapFileName);
		ffs.setTag2FieldMapFileName(Tag2FieldMapFileName);
		ffs.setDs2DMapFileName(Ds2DMapFileName);
		ffs.setTs2TMapFileName(Ts2TMapFileName);
		ffs.setVirtualDimensionMapFileName(VirtualDimensionMapFileName);
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

		// Resolve Tables with just DimensionSet
		String matchDimensionSet = "ds2";
		DimensionSet ds_in = ffs.getDs2dmap().getDimensionSet(matchDimensionSet);
		List<Table> matchedTable = ro.matchTablesDimensionSetTables(ffs.getTables().getTables(),ds_in);

		// Resolve Fields with just DimensionSet.Dimension
		String matchDimensionSet1 = "ds2";
		String matchDimension1 = "d1";
		ds_in = ffs.getDs2dmap().getDimensionSet(matchDimensionSet1);
		List<Table> res1 = ro.matchTablesDimensionSetTables(ffs.getTables().getTables(), ds_in);
		Set<Dimension> dims = new HashSet<Dimension>();
		dims.add(ffs.getDimensions().getDimension(matchDimension1));
		fs = ro.matchFieldsDimensionsFields(ro.getTablesFields(res1), dims);

		// Resolve Tag to Field
		String matchTagScheme = "ts1";
		String matchTag = "t1";
		List<Tag> in_tags = new ArrayList<Tag>();
		in_tags.add(ffs.getTs2tmap().getTag(matchTagScheme, matchTag));
		fs = ro.matchFieldsTagsFields(ro.getTablesFields(ffs.getTables().getTables()),
				in_tags);

		// Resolve to a Tag and to a Dimension

		// Resolve to a Tag and to a DimensionSet/Dimension
		// TagScheme1:Tag3.DimensionSet2.Dimension3
		String tag1 = "t3";
		String ts1 = "ts1";
		String ds1 = "ds2";
		String d1 = "d3";

		// First get the fields for tag
		in_tags = new ArrayList<Tag>();
		in_tags.add(ffs.getTs2tmap().getTag(ts1, tag1));
		List<Field> ftag = ro.matchFieldsTagsFields(
				ro.getTablesFields(ffs.getTables().getTables()), in_tags);

		// Then get the fields for dimensionset and dimension
	//	List<Field> fds = ffs.matchDimensionSetDimension(ds1, d1);
		
		ds_in = ffs.getDs2dmap().getDimensionSet(ds1);
		res1 = ro.matchTablesDimensionSetTables(ffs.getTables().getTables(), ds_in);
		dims = new HashSet<Dimension>();
		dims.add(ffs.getDimensions().getDimension(d1));
		List<Field> fds = ro.matchFieldsDimensionsFields(ro.getTablesFields(res1), dims);

		// Now find what are common
		Set<Field> cfield = ro.intersectionFields(ftag, fds);

		// Logical operations on Tags AND, i.e., when both tags are true for
		// fields
		ts1 = "ts1";
		tag1 = "t2";
		String ts2 = "ts2";
		String tag2 = "t1";
		List<Tag> in_tags1 = new ArrayList<Tag>();
		in_tags1.add(ffs.getTs2tmap().getTag(ts1, tag1));
		List<Field> afields = ro.matchFieldsTagsFields(
				ro.getTablesFields(ffs.getTables().getTables()), in_tags1);
		List<Tag> in_tags2 = new ArrayList<Tag>();
		in_tags2.add(ffs.getTs2tmap().getTag(ts2, tag2));
		List<Field> bfields = ro.matchFieldsTagsFields(
				ro.getTablesFields(ffs.getTables().getTables()), in_tags2);	
		Set<Field> fAND = ro.intersectionFields(afields, bfields);
		
		
		
		// Now find tag NOT B - find those fields that do not have tagB
		Set<Field> fNOT = ro.minusFields(bfields, afields); // Now find tag A OR
															// tag B
		Set<Field> fOR = ro.unionFields(afields, bfields);
    }


}
