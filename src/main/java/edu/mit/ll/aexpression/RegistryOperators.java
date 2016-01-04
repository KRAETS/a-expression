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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RegistryOperators {
	public static boolean debug = false;
	
	public static Set<Field> intersectionFields(List<Field> fields1,
			List<Field> fields2) {
		if (fields1 == null) { if(debug)System.out.println("intersectionFields: fields1 is NULL"); }
		if (fields2 == null) { if(debug)System.out.println("intersectionFields: fields2 is NULL"); }
		if (fields1 == null || fields2 == null) { return null;}
	
		Set<Field> cf = new HashSet<Field>();
		for (Field f1 : fields1) {
			for (Field f2 : fields2) {
				if (f1.getName().equals(f2.getName())) {
					cf.add(f1);
					if(debug)
					System.out.println(" 	matches " + f1.getTable().getName()
							+ ":" + f1.getName());
				}
			}
		}
	
		if (cf.size() > 0)
			return cf;
		else
	
			return null;
	}

	public static Set<Table> intersectionTables(List<Table> tables1,
			List<Table> tables2) {
		if (tables1 == null) { if(debug) System.out.println("intersectionTables: tables1 is NULL"); }
		if (tables2 == null) { if(debug) System.out.println("intersectionTables: tables2 is NULL"); }
		if (tables1 == null || tables2 == null) { return null;}
	
		Set<Table> cf = new HashSet<Table>();
		for (Table f1 : tables1) {
			for (Table f2 : tables2) {
				if (f1.getName().equals(f2.getName())) {
					cf.add(f1);
					if(debug)
					System.out.println(" 	matches " + f1.getName()
							+ ":" + f1.getName());
				}
			}
		}
	
		if (cf.size() > 0)
			return cf;
		else
	
			return null;
	}

	public static List<Field> matchFieldsDimensionsFields(List<Field> l_fields,
			Set<Dimension> in_dimensions) {
	
		List<Field> matchedFields = new ArrayList<Field>();
	
		for (Field f : l_fields) {
			for (Dimension in_dimension : in_dimensions) {
				if (f.getDimension().getName().equals(in_dimension.getName())) {
					matchedFields.add(f);
					if(debug)
					System.out.println("Dimension:" + in_dimension.getName()
							+ " matches " + f.getTable().getName() + ":"
							+ f.getName());
				}
			}
		}
	
		if (matchedFields.size() == 0) {
			if(debug)
			System.out.println("Dimension:" + in_dimensions + " matches NONE");
			return null;
		}
	
		else
			return matchedFields;
	
	}
	
	public static List<Field> matchFieldsPeriodsFields(List<Field> in_fields, List<Period> in_durations) {
		List<Field> fields = new ArrayList<Field>();
		for (Field f : in_fields) {
			for (Period du: in_durations) {
				if (f.getTable().fieldAvailable( du)) {
					fields.add(f);
					if(debug)
					System.out.println("Field:" + f.getTable().getName()+"."+ f.getName()
							+ " matches period");
				}
					break;			
			}
			
		}
		if (fields.size() > 0)
			return fields;
		else {
			if(debug)
			System.out.println("Time period" + " matches NONE");
			return null;
		}
	}
	
	public static List<Field> matchTablesPeriodsFields(List<Table> in_tables, List<Period> in_durations) {
		List<Field> fields = new ArrayList<Field>();
		for (Table t : in_tables) {
			for (Field f: t.getFields()){
			for (Period du: in_durations) {
				if (f.fieldAvailable( du)) {
					fields.add(f);
					if(debug)
					System.out.println("Field:" + f.getTable().getName()+"."+ f.getName()
							+ " matches period");
				}
					break;			
			}
			}
			
		}
		if (fields.size() > 0)
			return fields;
		else {
			if(debug)
			System.out.println("Time period" + " matches NONE");
			return null;
		}
	}

	public static List<Field> matchFieldsTagsFields(List<Field> in_fields,
			List<Tag> in_tags) {
		if (in_tags.size() == 0) {
			if(debug)
			System.out.println("	matchFieldsTagsFields : in_tags is empty");
			return null;
		}
		List<Field> fields = new ArrayList<Field>();
	
		for (Field f : in_fields) {
			for (Tag tg : f.getTags()) {
				// System.out.println("field " + f.getName()+ " TagScheme: "
				// + tg.getTs().getName()+ "--Tags " + tg.getName());
				for (Tag in_tag : in_tags) {
					if (in_tags == null) {
						if(debug)
						System.out
								.println("	matchFieldsTagsFields : in_tags is empty");
						continue;
					}
					if (tg.getName().equals(in_tag.getName())
							&& (tg.getTs().getName().equals(in_tag.getTs()
									.getName()))) {
						fields.add(f);
						if(debug)
						System.out.println("TagScheme:" + tg.getTs().getName()
								+ " Tag:" + tg.getName() + " matches "
								+ f.getTable().getName() + ":" + f.getName());
					}
				}
			}
		}
	
		if (fields.size() > 0)
			return fields;
		else {
			if(debug)
			System.out.println("TagScheme:" + " Tag:" + " matches NONE");
			return null;
		}
	}

	public static List<Table> matchTablesDimensionSetTables(List<Table> in_tables, DimensionSet ds) {
		List<Table> matchedTables = new ArrayList<Table>();
		// for each table check the all dimensions in dimensionset match
		// a subset of the fields in the table
		for (Table t : in_tables) {
			int matchesNeeded = ds.getDimensions().size();
			for (Dimension d : ds.getDimensions()) {
				for (Field f : t.getFields()) {
					if (f.getDimension().getName().equals(d.getName())) {
						matchesNeeded--;
						break;
					}
				}
			}
			if (matchesNeeded < 1) {// we have a matching table
				matchedTables.add(t);
			}
	
		}
		if (matchedTables.size() > 0) {
			for (Table t : matchedTables) {
				if(debug)
				System.out.println("DimensionSet:" + ds.getName()
						+ " matches " + t.getName());
			}
			return matchedTables;
		}
	
		else {
			if(debug)
			System.out.println("DimensionSet:" + ds.getName()
					+ " matches NONE");
			return null;
		}
	
	}

	public static List<Field> matchTablesDimensionsFields(List<Table> l_tables,
			List<String> l_dimensions) {
	
		List<Field> matchedFields = new ArrayList<Field>();
	
		for (Table t : l_tables) {
			for (Field f : t.getFields()) {
				for (String dimension : l_dimensions) {
					if (f.getDimension().getName().equals(dimension)) {
						matchedFields.add(f);
						if(debug)
						System.out.println("Dimension:" + dimension
								+ " matches " + f.getTable().getName() + ":"
								+ f.getName());
					}
				}
			}
		}
	
		if (matchedFields.size() == 0) {
			if(debug)
			System.out.println("Dimension:" + l_dimensions + " matches NONE");
			return null;
		}
	
		else
			return matchedFields;
	
	}

	public static Set<Field> minusFields(List<Field> bfields, List<Field> afields) {
		Set<Field> cf = new HashSet<Field>();
		for (Field f1 : afields) {
			if (!bfields.contains(f1)) cf.add(f1);
		}
		if (cf.size() > 0)
			return cf;
		return null;
	}

	public static Set<Table> minusTables(List<Table> btables, List<Table> atables) {
		Set<Table> cf = new HashSet<Table>();
		for (Table f1 : atables) {
			if (!btables.contains(f1)) cf.add(f1);
		}
		if (cf.size() > 0)
			return cf;
		return null;
	}

	public static Set<Field> unionFields(List<Field> fields1, List<Field> fields2) {
	
		Set<Field> cf = new HashSet<Field>();
		cf.addAll(fields1);
		cf.addAll(fields2);
	
		if (cf.size() > 0)
			return cf;
		else
	
			return null;
	}

	public static Set<Table> unionTables(List<Table> tables1, List<Table> tables2) {
	
		Set<Table> cf = new HashSet<Table>();
		cf.addAll(tables1);
		cf.addAll(tables2);
	
		if (cf.size() > 0)
			return cf;
		else
	
			return null;
	}
	
	public static List<Field> getTablesFields(List<Table> in_tables) {
		List<Field> out_fields = new ArrayList<Field>();
		for (Table t : in_tables) {
			out_fields.addAll(t.getFields());

		}
		return out_fields;

	}
	

}
