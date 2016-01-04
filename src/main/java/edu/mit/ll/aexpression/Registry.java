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

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Registry {
	private List<String> tables = new ArrayList<String>();
	private List<String> dimensions = new ArrayList<String>();
	private List<String> dimensionSets = new ArrayList<String>();

	private Map<String, String> OpT2F = new HashMap<String, String>();
	private Map<String, String> OpD2F = new HashMap<String, String>();
	private Map<String, String> OpDs2D = new HashMap<String, String>();

	private String readJSONFile(String fileName) {
		String myfileName = fileName;
		String jsonString = "";
		BufferedReader bufferedReader = null;

		try {

			bufferedReader = new BufferedReader(new FileReader(myfileName));
			String sCurrentLine;
			while ((sCurrentLine = bufferedReader.readLine()) != null) {
				jsonString = jsonString + sCurrentLine;
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return jsonString;
	}

	public Tables readTable2FieldMap(String fileName) {
		String jsonString = readJSONFile(fileName);
		;

		Gson gson = new Gson();
		// System.out.println("MyJSONSTring: " + jsonString);
		Tables tablesObj = gson.fromJson(jsonString, Tables.class);

		List<Table> tables = tablesObj.getTables();
		for (int i = 0; i < tables.size(); i++) {
			/*
			 * System.out.println("Table is:");
			 * System.out.println(tables.get(i).getName());
			 * 
			 * System.out.println("Fields are :"); List<Field> fields =
			 * tables.get(i).getFields(); for (int j = 0; j < fields.size();
			 * j++) { System.out.println(fields.get(j)); }
			 */
		}

		return tablesObj;

	}

	public F2DMap readField2DimensionMap(String fileName) {

		String jsonString = readJSONFile(fileName);

		Gson gson = new Gson();
		// System.out.println("MyJSONSTring: " + jsonString);
		F2DMap f2dmapObj = gson.fromJson(jsonString, F2DMap.class);

		List<F2DMapItem> f2dmap = f2dmapObj.getF2dmap();
		/*
		 * for (int i = 0; i < f2dmap.size(); i++) {
		 * System.out.println("Table: " + f2dmap.get(i).getTable() + " Field: "
		 * + f2dmap.get(i).getField() + " Dimension: " +
		 * f2dmap.get(i).getDimension()); }
		 */
		return f2dmapObj;
	}

	public Tag2FieldMap readClassTag2FieldMap(String fileName) {

		String jsonString = readJSONFile(fileName);

		Gson gson = new Gson();
		// System.out.println("MyJSONSTring: " + jsonString);
		Tag2FieldMap tag2fieldmapObj = gson.fromJson(jsonString,
				Tag2FieldMap.class);

		List<Tag2FieldItem> tag2fieldmap = tag2fieldmapObj.getTag2fieldItems();
		/*
		 * for (int i = 0; i < tag2fieldmap.size(); i++) {
		 * System.out.println("Table: " + tag2fieldmap.get(i).getTable() +
		 * " Field: " + tag2fieldmap.get(i).getField() + " TagScheme: " +
		 * tag2fieldmap.get(i).getTagScheme() + " Tag: " +
		 * tag2fieldmap.get(i).getTag()); }
		 */
		return tag2fieldmapObj;
	}

	public DimensionSets readDimensionSet2DimensionMap(String fileName) {

		String jsonString = readJSONFile(fileName);

		Gson gson = new Gson();
		// System.out.println("MyJSONSTring: " + jsonString);
		DimensionSets ds2dmapObj = gson.fromJson(jsonString,
				DimensionSets.class);

		List<DimensionSet> dimensionSets = ds2dmapObj.getDimensionSets();
		/*
		 * for (int i = 0; i < dimensionSets.size(); i++) { List<Dimension>
		 * dimensions = dimensionSets.get(i).getDimensions();
		 * System.out.println("DimensionSet: " +
		 * dimensionSets.get(i).getName()); for (int j = 0; j <
		 * dimensions.size(); j++) { System.out.println("Dimension: " +
		 * dimensions.get(j).getName()); } }
		 */
		return ds2dmapObj;
	}

	public TagSchemes readTagScheme2TagMap(String fileName) {

		String jsonString = readJSONFile(fileName);

		Gson gson = new Gson();
		// System.out.println("MyJSONSTring: " + jsonString);
		TagSchemes ts2tmapObj = gson.fromJson(jsonString, TagSchemes.class);

		List<TagScheme> tagSchemes = ts2tmapObj.getTagSchemes();
		/*
		 * for (int i = 0; i < tagSchemes.size(); i++) { List<Tag> tags =
		 * tagSchemes.get(i).getTags(); System.out.println("TagScheme: " +
		 * tagSchemes.get(i).getName()); for (int j = 0; j < tags.size(); j++) {
		 * System.out.println("Tag: " + tags.get(j).getName()); } }
		 */
		return ts2tmapObj;
	}

	public Operators readOperator2DimensionSet(String fileName) {
		String jsonString = readJSONFile(fileName);
		;

		Gson gson = new Gson();
		// System.out.println("MyJSONSTring: " + jsonString);
		Operators operatorsObj = gson.fromJson(jsonString, Operators.class);

		List<Operator> operators = operatorsObj.getOperators();
		for (int i = 0; i < operators.size(); i++) {
			System.out.println("Operator is:");
			System.out.println(operators.get(i).getName());

			System.out.println("DimensionSetIn are :");
			List<DimensionSet> dimsin = operators.get(i).getDimensionSetIn();
			for (int j = 0; j < dimsin.size(); j++) {
				System.out.println(dimsin.get(j));
			}

			DimensionSet dimsout = operators.get(i).getDimensionSetOut();
			if (dimsout != null) {
				System.out.println(dimsout);
			}

			System.out.println("Dimensions are :");
			List<Dimension> dims = operators.get(i).getDimensionsOut();
			if (dims != null) {
				for (int j = 0; j < dims.size(); j++) {
					System.out.println(dims.get(j));
				}
			}

		}

		return operatorsObj;

	}


	public VirtualDimensions readVirtualDimensions(
			String fileName) {
		String jsonString = readJSONFile(fileName);
		;

		Gson gson = new Gson();
		// System.out.println("MyJSONSTring: " + jsonString);
		VirtualDimensions virtualDimensionsObj = gson.fromJson(jsonString, VirtualDimensions.class);

		List<Dimension> virtualDimensions = virtualDimensionsObj.getVirtualDimensions();
	/*	for (int i = 0; i < virtualDimensions.size(); i++) {
			System.out.println("Virtual Dimension is:");
			System.out.println(virtualDimensions.get(i).getName());

			System.out.println("Dimensions are :");
			List<Dimension> dimin = virtualDimensions.get(i).getVirtualDimensions();
			for (int j = 0; j < dimin.size(); j++) {
				System.out.println(dimin.get(j));
			}

	

		}*/

		return virtualDimensionsObj;
	}
	
	public void writeClassT2F() {

		Table table1, table2;
		Field f1, f2, f3, f4;
		table1 = new Table("EventTable1");

		List<Field> fields = new ArrayList<Field>();
		f1 = new Field("f1");
		fields.add(f1);
		f2 = new Field("f2");
		fields.add(f2);

		table1.setListOfFields(fields);

		Gson gson = new Gson();
		String json = gson.toJson(table1);
		try {
			// write converted json data to a file named "CountryGSON.json"
			FileWriter writer = new FileWriter("src/main/resources/testT.json");
			writer.write(json);
			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println(json);

	}


}
