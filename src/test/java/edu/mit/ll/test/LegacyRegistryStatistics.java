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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
import edu.mit.ll.aexpression.Tag2FieldItem;
import edu.mit.ll.aexpression.Tag2FieldMap;
import edu.mit.ll.convert.RegistryIn;



public class LegacyRegistryStatistics {
	

    static String testName = "LegacyRegistryJsonConverter";    
    static Logger log = Logger.getLogger(LegacyRegistryStatistics.class);
    



    
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
		printTableStats(table);
		
		
		F2DMap f2dmap = rin.populateField2DimensionMap(F2DMapFileName);
		ffs.setF2dmap(f2dmap);
		
		Tag2FieldMap tag2fieldmap = rin.populateTag2FieldMap(Tag2FieldMapFileName);
		ffs.setT2FMapFileName(Tag2FieldMapFileName);
		
		
		
		DimensionSets dss = rin.populateDS2DMap(Ds2DMapFileName);
		ffs.setDs2DMapFileName(Ds2DMapFileName);
		
		printDimensionStats(dss);
		
		printTagStatistics(tag2fieldmap,f2dmap);
		
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

	private void printTagStatistics(Tag2FieldMap tag2fieldmap, F2DMap f2dmap) {
		System.out.println("--------------------------------------------------\n");
		System.out.println("Number of Tag2FieldAssignments: "+ tag2fieldmap.getTag2fieldItems().size());
		Set<String> uniqueTags = new HashSet<String>();
		Map<String, Integer> fieldTagCount = new HashMap<String, Integer>();
		Map<String, Integer> dimensionTagCount = new HashMap<String, Integer>(); //the tags allowed for each dimension

		
		int totalTags = 0;
		Map<String, HashSet<String>> dimTags = new HashMap<String,HashSet<String>>();
		
		for (Tag2FieldItem t: tag2fieldmap.getTag2fieldItems()) {
			uniqueTags.add(t.getTag());		
			totalTags ++;
			//see if already exists, if so inclrease count
			if (fieldTagCount.get(t.getTable()+"__"+t.getField()) != null) {
				fieldTagCount.put(t.getTable()+"__"+t.getField(), fieldTagCount.get(t.getTable()+"__"+t.getField())+1);
			} else
			fieldTagCount.put(t.getTable()+"__"+t.getField(), 1);

			if (f2dmap.getDimension(t.getField()) != null) {
				String dim = f2dmap.getDimension(t.getField());
				if (dimensionTagCount.get(dim) != null)
					dimensionTagCount.put(dim, dimensionTagCount.get(dim) + 1);
				else
					dimensionTagCount.put(dim, 1);
		
				HashSet<String> tags = new HashSet<String>(); //unique tags for all the fields

					if (tag2fieldmap.getTags(t.getField()) != null)
						for (String tag: tag2fieldmap.getTags(t.getField()))
							tags.add(tag);
				
				
				System.out.println("Dimension, "+ dim+ ",Field, "+ t.getField()+ ", Tags, " + tags);
				
				if (dimTags.get(dim) != null) {
					HashSet<String> oldTags = dimTags.get(dim);
					for (String tag: tags)
						oldTags.add(tag);
					dimTags.put(dim,oldTags);
				} else 
				dimTags.put(dim, tags);			

			}

			
		}
		
		System.out.println("\nNumber of Fields that have Assigned Tags: "+ fieldTagCount.size());		
	
		System.out.println("Unique Tags: "+ uniqueTags.toString() + "\n Total Tags: " + uniqueTags.size());
		
		int maxTagsPerField = 0;
		for (String f: fieldTagCount.keySet()) {
			
			int ind = f.indexOf("__");
			
//			System.out.println(" Field: "+ f + " \tDimension: "  + f2dmap.getDimension(f.substring(ind+2)) + "\t\t\t Tag Count: " + fieldTagCount.get(f));
			if ((fieldTagCount.get(f).intValue()  > maxTagsPerField)) 
				maxTagsPerField = fieldTagCount.get(f).intValue();
		}
		
		System.out.println("Max Tags on a Field: "+ maxTagsPerField);

		for (String d: dimTags.keySet()) {
			System.out.println("Dimension ," + d + ",Tags, " + dimTags.get(d).size() +"," +dimTags.get(d));
		}
		
		
		Map<String, HashSet<String>> tagDims = new HashMap<String,HashSet<String>>();

		for( String dim : dimTags.keySet()){
			for (String tag: dimTags.get(dim)){
				if (tagDims.get(tag) != null) {
					HashSet<String> oldDims = tagDims.remove(tag);
					oldDims.add(dim);
					tagDims.put(tag,oldDims);
					
				} else {
				HashSet<String> dims = new HashSet<String>();
				dims.add(dim);
				tagDims.put(tag, dims);
				}
			}
		}

		System.out.println("_____________Tag to Dimensions map____________");
		for (String tag: tagDims.keySet()) {
			System.out.println("Tag," + tag + ", dimensions," + tagDims.get(tag).size() +","  + tagDims.get(tag) );
		}
		
	}

	private void printDimensionStats(DimensionSets dss) {
		System.out.println("--------------------------------------------------\n");
		System.out.println("\nNumber of Static DimensionSets: "+ dss.getDimensionSets().size());
		Map<DimensionSet, Integer> dimensionCount = new HashMap<DimensionSet, Integer>();
		Map<DimensionSet, Integer> dimensionCountBesidesTime = new HashMap<DimensionSet, Integer>();
		Set<String> uniqueDimensions = new HashSet<String>();
		
		int totalDimensions = 0;
		int totalDimensionsBesidesTime = 0; //Tables with just _time as a field is not counted
		int totalDimensionSetsBesidesTime = 0;
		System.out.println("DimensionSet and number of Dimensions___________\n");
		for (DimensionSet t: dss.getDimensionSets()) {
			dimensionCount.put(t,t.getDimensions().size());
			totalDimensions += t.getDimensions().size();
			for (Dimension d: t.getDimensions()) {
				uniqueDimensions.add(d.getName());
			}
			
			//System.out.println(t.getName()+":"+ t.getDimensions().size()+ "\t");
			
			if (t.getDimensions().size() > 1) {
				dimensionCountBesidesTime.put(t,t.getDimensions().size());
				totalDimensionsBesidesTime += t.getDimensions().size();
				System.out.println(t.getName()+":"+ t.getDimensions().size()+ "\t");
				totalDimensionSetsBesidesTime++;
			}
			
		}
		
		System.out.println("\nNumber of Fields Assigned Dimensions: "+ totalDimensions);
		System.out.println("Average Dimensions in a DimensionSet: "+ totalDimensions/dss.getDimensionSets().size());
		
		System.out.println("\nNumber of Nontrivial DimensionSets: "+ totalDimensionSetsBesidesTime);
		System.out.println("\nNumber of Nontrivial Fields Assigned Dimensions: "+ totalDimensionsBesidesTime);
		System.out.println("Average Nontrivial Dimensions in a DimensionSet: "+ totalDimensionsBesidesTime/totalDimensionSetsBesidesTime);
		
		System.out.println("Unique Dimensions: "+ uniqueDimensions.toString() + "\n Total Dimensions: " + uniqueDimensions.size());

	}

	private void printTableStats(Tables tables) {
		System.out.println("--------------------------------------------------\n");
		System.out.println("Number of tables: "+ tables.getTables().size());
		Map<Table, Integer> fieldCount = new HashMap<Table, Integer>();
		Map<Table, Integer> fieldCountBesidesTime = new HashMap<Table, Integer>();
		int totalFields = 0;
		int totalFieldsBesidesTime = 0; //Tables with just _time as a field is not counted
		int totalTablesBesidesTime = 0;
		System.out.println("Table and number of fields___________\n");
		for (Table t: tables.getTables()) {
			fieldCount.put(t,t.getFields().size());
			totalFields += t.getFields().size();
			//System.out.println(t.getName()+":"+ t.getFields().size()+ "\t");
			
			if (t.getFields().size() > 1) {
				fieldCountBesidesTime.put(t,t.getFields().size());
				totalFieldsBesidesTime += t.getFields().size();
				System.out.println(t.getName()+":"+ t.getFields().size()+ "\t");
				totalTablesBesidesTime++;
			}
			
		}
		System.out.println("\nNumber of Fields: "+ totalFields);
		System.out.println("Average Fields in a Table: "+ totalFields/tables.getTables().size());
		
		System.out.println("\nNumber of Nontrivial Tables: "+ totalTablesBesidesTime);
		System.out.println("\nNumber of Nontrivial Fields: "+ totalFieldsBesidesTime);
		System.out.println("Average Nontrivial Fields in a Table: "+ totalFieldsBesidesTime/totalTablesBesidesTime);

	}


}
