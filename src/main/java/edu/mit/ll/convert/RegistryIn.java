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
package edu.mit.ll.convert;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonWriter;

import edu.mit.ll.aexpression.Dimension;
import edu.mit.ll.aexpression.DimensionSet;
import edu.mit.ll.aexpression.DimensionSets;
import edu.mit.ll.aexpression.F2DMap;
import edu.mit.ll.aexpression.F2DMapItem;
import edu.mit.ll.aexpression.Field;
import edu.mit.ll.aexpression.Table;
import edu.mit.ll.aexpression.Tables;
import edu.mit.ll.aexpression.Tag2FieldItem;
import edu.mit.ll.aexpression.Tag2FieldMap;

public class RegistryIn {
	Events registry = null;
	RegistryIn registryObj = null;
	
	public RegistryIn () {
		registry = new Events();
	}

	public Events getEvents() {
		return registry;
	}

	public void setEvents(Events events) {
		this.registry = events;
	}
	/**
	 * Reads a json string from the input file and returns it
	 * @param fileName
	 * @return
	 */
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
	public RegistryIn readLegacyRegistryJson(String fileName) {
		String jsonString = readJSONFile(fileName);
		;

		Gson gson = new Gson();
		// System.out.println("MyJSONSTring: " + jsonString);
		registryObj = gson.fromJson(jsonString, RegistryIn.class);

		List<EventIn> events = registryObj.getEvents().getEvents();
		for (int i = 0; i < events.size(); i++) {
	/*		
			 System.out.println("Event is:");
			  System.out.println(events.get(i).getName());
			  
			  System.out.println("Fields are :"); List<FieldIn> fields =
			  events.get(i).getFields(); for (int j = 0; j < fields.size();
			  j++) { System.out.println(fields.get(j)); }
	*/		 
		}

		return registryObj;

	}
	
	public Tables populateT2FMap(String filename) {
		Tables tmap = new Tables("tables");
		List<Table> tables = tmap.getTables();
		List<EventIn> events = registryObj.getEvents().getEvents();
		for (EventIn event: events) {
			Table table = new Table(event.getTableName());
			tables.add(table);
			for (FieldIn fin: event.getFields()) {
				Field f = new Field(fin.getName());
				table.addField(f);
				f.setTable(table);
			}
		}
		
		return tmap;
		
	}
	
	public void writeJsonStreamT2FMap(PrintWriter out, Tables tables) throws IOException {
		 final GsonBuilder builder = new GsonBuilder();
		 	builder.excludeFieldsWithoutExposeAnnotation();
		 	builder.setPrettyPrinting();
		    final Gson gson = builder.create();

		    final String json = gson.toJson(tables);
		    System.out.printf("%s%n", json);
		    
		    out.write(json);
		    out.close();
	}

	

	
	public F2DMap populateField2DimensionMap(String filename) {
		F2DMap f2dmaps = new F2DMap();
		List<F2DMapItem> f2dmap = f2dmaps.getF2dmap();
		List<EventIn> events = registryObj.getEvents().getEvents();
		for (EventIn event: events) {
			for (FieldIn fin: event.getFields()) {
				if (fin.getDim() != null) {
					F2DMapItem f2ditem = new F2DMapItem();
					f2ditem.setField(fin.getName());
					f2ditem.setTable(event.getTableName());
					f2ditem.setDimension(fin.getDim());
					f2dmap.add(f2ditem);
				}
			}
			
			
		}
		f2dmaps.setF2dmap(f2dmap);
		return f2dmaps;
		
	}
	
	public void writeJsonStreamF2DMap(PrintWriter out, F2DMap f2dmaps) throws IOException {
		 final GsonBuilder builder = new GsonBuilder();
		 	builder.excludeFieldsWithoutExposeAnnotation();
		 	builder.setPrettyPrinting();
		    final Gson gson = builder.create();

		    final String json = gson.toJson(f2dmaps);
		    System.out.printf("%s%n", json);
		    
		    out.write(json);
		    out.close();
	}
	

	public Tag2FieldMap populateTag2FieldMap(String filename) {
		Tag2FieldMap t2fmaps = new Tag2FieldMap();
		List<Tag2FieldItem> t2fmap = t2fmaps.getTag2fieldItems();
		List<EventIn> events = registryObj.getEvents().getEvents();
		for (EventIn event: events) {
			for (FieldIn fin: event.getFields()) {
				
				if (fin.getTags()!= null) {
					int i = 0;
					for (String tag: fin.getTags()) {
						Tag2FieldItem tag2fitem = new Tag2FieldItem();
						tag2fitem.setField(fin.getName());
						tag2fitem.setTable(event.getTableName());
						tag2fitem.setTagScheme("LLCYSA"+i);
						tag2fitem.setTag(tag);
						t2fmap.add(tag2fitem);
						i++;
					}
					
					
				}
				
			}			
		}
		
		t2fmaps.setTag2fieldItems(t2fmap);
		
		return t2fmaps;
		
	}
	
	public void writeJsonStreamTag2FieldMap(PrintWriter out, Tag2FieldMap t2dmaps) throws IOException {
		 final GsonBuilder builder = new GsonBuilder();
		 	builder.excludeFieldsWithoutExposeAnnotation();
		 	builder.setPrettyPrinting();
		    final Gson gson = builder.create();

		    final String json = gson.toJson(t2dmaps);
		    System.out.printf("%s%n", json);
		    
		    out.write(json);
		    out.close();
	}
	
	public DimensionSets populateDS2DMap(String filename) {
		DimensionSets DSs = new DimensionSets();
		List<DimensionSet> dss = DSs.getDimensionSets();
		List<EventIn> events = registryObj.getEvents().getEvents();
		int count = 0;
		for (EventIn event: events) {
			//create a dimensionset for each event using its name ( not table name)
			if (event.getName()!=null) {
				DimensionSet ds = new DimensionSet(event.getName());
				for (FieldIn fin: event.getFields()) {
					if (fin.getDim() != null) {
						//See whether the dimension is already added
						
						Dimension dimension = new Dimension(fin.getDim());
						if (!ds.inDimensionSet(dimension))
							ds.getDimensions().add(dimension);
					}
				}
				dss.add(ds); count++;
			}
			
			
		}
		
		System.out.println("Total DimensionSets: "+count);
		return DSs;
		
	}
	
	
	public void writeJsonStreamDS2DMap(PrintWriter out, DimensionSets dss) throws IOException {
		 final GsonBuilder builder = new GsonBuilder();
		 	builder.excludeFieldsWithoutExposeAnnotation();
		 	builder.setPrettyPrinting();
		    final Gson gson = builder.create();

		    final String json = gson.toJson(dss);
		    System.out.printf("%s%n", json);
		    
		    out.write(json);
		    out.close();
	}
	

}
