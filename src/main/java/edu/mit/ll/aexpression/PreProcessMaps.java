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

import java.util.*;

/**
 * @author su22797
 * 
 */
public class PreProcessMaps {

	private String T2FMapFileName = null;
	private String F2DMapFileName = null;
	private String Tag2FieldMapFileName = null;
	private String Ds2DMapFileName = null;
	private String Ts2TMapFileName = null;
	private String Operator2DimensionSetMapFileName = null;
	private String VirtualDimensionMapFileName = null;

	private Tables tables = null;
	private F2DMap f2dmap = null; // temporary - will be processed and deleted
	private DimensionSets ds2dmap = null;
	private TagSchemes ts2tmap = null;
	private Dimensions dimensions = null;
	private Fields fields = null;
	private Tag2FieldMap tag2fmap = null; // temporary - will be processed and
	// deleted
	private Operators operators = null;
	private VirtualDimensions virtualDimensions = null;

	private Registry registry = null;

	public String getT2FMapFileName() {
		return T2FMapFileName;
	}

	public void setT2FMapFileName(String t2fMapFileName) {
		T2FMapFileName = t2fMapFileName;
	}

	public String getF2DMapFileName() {
		return F2DMapFileName;
	}

	public void setF2DMapFileName(String f2dMapFileName) {
		F2DMapFileName = f2dMapFileName;
	}

	public String getTag2FieldMapFileName() {
		return Tag2FieldMapFileName;
	}

	public void setTag2FieldMapFileName(String tag2FieldMapFileName) {
		Tag2FieldMapFileName = tag2FieldMapFileName;
	}

	public String getDs2DMapFileName() {
		return Ds2DMapFileName;
	}

	public void setDs2DMapFileName(String ds2dMapFileName) {
		Ds2DMapFileName = ds2dMapFileName;
	}

	public String getTs2TMapFileName() {
		return Ts2TMapFileName;
	}

	public void setTs2TMapFileName(String ts2tMapFileName) {
		Ts2TMapFileName = ts2tMapFileName;
	}

	public String getOperator2DimensionSetMapFileName() {
		return Operator2DimensionSetMapFileName;
	}

	public void setOperator2DimensionSetMapFileName(
			String operator2DimensionSetMapFileName) {
		Operator2DimensionSetMapFileName = operator2DimensionSetMapFileName;
	}

	public String getVirtualDimensionMapFileName() {
		return VirtualDimensionMapFileName;
	}

	public void setVirtualDimensionMapFileName(
			String virtualDimensionMapFileName) {
		VirtualDimensionMapFileName = virtualDimensionMapFileName;
	}

	public Tables getTables() {
		return tables;
	}

	public void setTables(Tables tables) {
		this.tables = tables;
	}

	public F2DMap getF2dmap() {
		return f2dmap;
	}

	public void setF2dmap(F2DMap f2dmap) {
		this.f2dmap = f2dmap;
	}

	public DimensionSets getDs2dmap() {
		return ds2dmap;
	}

	public void setDs2dmap(DimensionSets ds2dmap) {
		this.ds2dmap = ds2dmap;
	}

	public TagSchemes getTs2tmap() {
		return ts2tmap;
	}

	public void setTs2tmap(TagSchemes ts2tmap) {
		this.ts2tmap = ts2tmap;
	}

	public Dimensions getDimensions() {
		return dimensions;
	}

	public void setDimensions(Dimensions dimensions) {
		this.dimensions = dimensions;
	}

	public Tag2FieldMap getTag2fmap() {
		return tag2fmap;
	}

	public void setTag2fmap(Tag2FieldMap tag2fmap) {
		this.tag2fmap = tag2fmap;
	}

	public Operators getOperators() {
		return operators;
	}

	public void setOperators(Operators operators) {
		this.operators = operators;
	}

	public VirtualDimensions getVirtualDimensions() {
		return virtualDimensions;
	}

	public void setVirtualDimensions(VirtualDimensions virtualDimensions) {
		this.virtualDimensions = virtualDimensions;
	}

	/*
	 * Return all DimensionSets reachable from teh given Dimension Set. Detect and avoid
	 * operator cycles
	 */
	public Set<DimensionSet> reachDimensionSetDimensionSets(DimensionSet in_ds) {

		// Find all the operators that have dimensionset s as its dimensionSetIn
		String traversalList = null;

		List<Operator> startOps = new ArrayList<Operator>();
		for (Operator op : operators.getOperators()) {
			for (DimensionSet ds : op.getDimensionSetIn()) {
				if (ds.getName().equals(in_ds.getName())) {
					if (!startOps.contains(op))
						startOps.add(op);
				}
			}
		}
		Set<DimensionSet> dimSetsOut = new HashSet<DimensionSet>();
		List<Operator> allVisitedOps = getReachedOperators(startOps);

		for (Operator op : allVisitedOps) {
			dimSetsOut.add(op.getDimensionSetOut());
		}

		if (dimSetsOut.size() == 0)
			return null;
		else
			return dimSetsOut;
	}

	private List<Operator> getReachedOperators(List<Operator> visitedOps) {

		List<Operator> startOps = new ArrayList<Operator>();
		startOps.addAll(visitedOps);
		boolean addedNewOps = false;
		for (Operator visitedOp : visitedOps) {
			for (Operator op : operators.getOperators()) {
				// check if the output dimensionSets of visitedOps is in the
				// inputDimensionSets of any operators
				for (DimensionSet ds_op : op.getDimensionSetIn()) {
					if (ds_op.getName().equals(
							visitedOp.getDimensionSetOut().getName()))  {
						if (!startOps.contains(visitedOp)) {
							startOps.add(op);
							addedNewOps = true;
						}
					}
				}

			}
		}
		if (addedNewOps) {
			return getReachedOperators(startOps);
		} else
			return visitedOps;
	}

	public List<Operator> reachDimensionSetDimensionSetPaths(
			DimensionSet in_ds1, DimensionSet in_ds2) {

		List<Operator> startOps = new ArrayList<Operator>();
		for (Operator op : operators.getOperators()) {
			for (DimensionSet ds : op.getDimensionSetIn()) {
				if (ds.getName().equals(in_ds1.getName())) {
					if (!startOps.contains(op))
						startOps.add(op);
//					System.out.println("Start Op:" + op.getName());
				}
			}
		}
		List<Operator> allVisitedOps = getReachedOperators(startOps, in_ds2);

		return allVisitedOps;
	}

	private List<Operator> getReachedOperators(List<Operator> visitedOps,
			DimensionSet final_ds) {

		List<Operator> startOps = new ArrayList<Operator>();
		startOps.addAll(visitedOps);
		boolean addedNewOps = false;
		for (Operator visitedOp : visitedOps) {
			for (Operator op : operators.getOperators()) {

				if (op.getDimensionSetOut().getName().equals(final_ds.getName())) { // return
					// the
					// path
					// - TBD
					// this
					// algo
					// needs
					// expansion
					// to
					// multiple
					// paths
					startOps.clear();
					startOps.addAll(visitedOps);
					startOps.add(op);
//					System.out.print(" " +op.getName() +" End Path");
					return startOps;
				}

				// check if the output dimensionSets of visitedOps is in the
				// inputDimensionSets of any operators
				for (DimensionSet ds_op : op.getDimensionSetIn()) {

					if (ds_op.getName().equals(
							visitedOp.getDimensionSetOut().getName())) {

						if (!startOps.contains(op)) {
							startOps.add(op);
//							System.out.print("Reached: " + op.getName());
							addedNewOps = true;
							break; // try the next operator , this op is already
							// in
						}

					}
				}

			}
		}
		if (addedNewOps) {
			return getReachedOperators(startOps, final_ds);
		} else
			return visitedOps;
	}

	public void init() {

		/*
		 * Dimension has its own Set, and are created from F2D map. Obviously,
		 * in this case, at least one field should be mapped to a Dimension to
		 * be counted. To remove this constraint, when Ds2D is processed, any
		 * Dimensions not already mentioned in F2D will be also added to
		 * Dimensions.
		 * 
		 * Fields are always integral parts of Tables - so Dimensions found in
		 * F2D has to be inserted into T2F.
		 * 
		 * DS2D has to be updated to point to the right Dimension. The existing
		 * Dimensions will be searched, and reference to the existng Dimensions
		 * will replace existing Dimensions in Ds2D. Any new Dimensions found
		 * will be added to Dimensions.
		 */

		registry = new Registry();
		processTable2FieldMap();

		processField2DimensionMap();

		processDimensionSet2DimensionMap();

		processTagScheme2TagMap();

		processTag2FieldMap();

		processOperator2DimMap();

		processVirtualDimensionMap();

	}

	private void processVirtualDimensionMap() {

		if (VirtualDimensionMapFileName != null) {
			virtualDimensions = registry
					.readVirtualDimensions(VirtualDimensionMapFileName);

			// Process virtualDimensions now to fix references to Dimensions.
			// Add a new
			// Dimension if needed
			List<Dimension> dset = virtualDimensions.getVirtualDimensions();
			if (dimensions == null) // Dimensions map is not read
				// in - so we will have to
				// create all new dimensions
				dimensions = new Dimensions();

			for (Dimension ds : dset) {

				List<Dimension> dims = ds.getVirtualDimensions();
				List<Dimension> dimsTobeAdded = new ArrayList<Dimension>();

				for (Dimension dimin : dims) {

					Dimension dimfound = null;
					{
						dimfound = dimensions.getDimension(dimin.getName());
						if (dimfound == null) { // special case - there is
							// no
							// such
							// Dimension
							dimfound = new Dimension(dimin.getName());
							dimensions.addDimension(dimfound);
						}
					}

					dimsTobeAdded.add(dimfound);
				}

				ds.setVirtualDimensions(new ArrayList<Dimension>()); // remove
				// all
				// old
				// Dimensions

				for (Iterator<Dimension> dIter = dimsTobeAdded.iterator(); dIter
						.hasNext();) {
					Dimension dimfound = dIter.next();
					ds.addVirtualDimension(dimfound); // note - new references
				}

			}
		}

	}

	private void processOperator2DimMap() {
		if (Operator2DimensionSetMapFileName != null) {
			operators = registry
					.readOperator2DimensionSet(Operator2DimensionSetMapFileName);
		}
	}

	private void processTagScheme2TagMap() {
		if (Ts2TMapFileName != null) {
			ts2tmap = registry.readTagScheme2TagMap(Ts2TMapFileName); // Now
			// update
			// tagschemes
			// in
			// all tags
			List<TagScheme> tagSchemes = ts2tmap.getTagSchemes();
			for (Iterator<TagScheme> tsIter = tagSchemes.iterator(); tsIter
					.hasNext();) {
				TagScheme ts = tsIter.next();
				List<Tag> tags = ts.getTags();
				for (Iterator<Tag> tIter = tags.iterator(); tIter.hasNext();) {
					tIter.next().setTs(ts);
				}
			}
		}
	}

	public void processTag2FieldMap() {

		if (Tag2FieldMapFileName != null) {
			tag2fmap = registry.readClassTag2FieldMap(Tag2FieldMapFileName); // Now
			// update
			// tags
			// in
			// all
			// fields
			List<Tag2FieldItem> tag2fieldItems = tag2fmap.getTag2fieldItems();
			for (Iterator<Tag2FieldItem> t2fItemIter = tag2fieldItems
					.iterator(); t2fItemIter.hasNext();) {
				Tag2FieldItem tag2fItem = t2fItemIter.next();
				Field f = tables.getField(tag2fItem.getTable(),
						tag2fItem.getField());
				Tag t = ts2tmap.getTag(tag2fItem.getTagScheme(),
						tag2fItem.getTag());
				f.addTag(t);
			}
		}
	}

	private void processDimensionSet2DimensionMap() {
		if (Ds2DMapFileName != null) {
			ds2dmap = registry.readDimensionSet2DimensionMap(Ds2DMapFileName);

			// Process ds2dmap now to fix references to Dimensions. Add a new
			// Dimension if needed
			List<DimensionSet> dset = ds2dmap.getDimensionSets();
			if (dimensions == null) // Dimensions map is not read
				// in - so we will have to
				// create all new dimensions
				dimensions = new Dimensions();

			for (Iterator<DimensionSet> dsIter = dset.iterator(); dsIter
					.hasNext();) {
				DimensionSet ds = dsIter.next();
				List<Dimension> dims = ds.getDimensions();
				List<Dimension> dimsTobeAdded = new ArrayList<Dimension>();
				for (Iterator<Dimension> dIter = dims.iterator(); dIter
						.hasNext();) {
					Dimension dimin = dIter.next();
					Dimension dimfound = null;
					{
						dimfound = dimensions.getDimension(dimin.getName());
						if (dimfound == null) { // special case - there is
							// no
							// such
							// Dimension
							dimfound = new Dimension(dimin.getName());
							dimensions.addDimension(dimfound);
						}
					}

					dimsTobeAdded.add(dimfound);
				}

				ds.setDimensions(new ArrayList<Dimension>()); // remove all old
				// Dimensions

				for (Iterator<Dimension> dIter = dimsTobeAdded.iterator(); dIter
						.hasNext();) {
					Dimension dimfound = dIter.next();
					ds.addDimension(dimfound); // note - new references
				}

			}
		}
	}

	private void processField2DimensionMap() {
		if (F2DMapFileName != null) {
			f2dmap = registry.readField2DimensionMap(F2DMapFileName);

			// registry.writeClassT2F();

			// Process f2dmap now to update Dimensions in tables (i.e., the
			// fields
			// in a table in tables)

			dimensions = new Dimensions();
			Set<Dimension> dimSet = new HashSet<Dimension>();
			// All all dimensions from f2dmap to fields in T2F Map
			List<F2DMapItem> f2dmapItems = f2dmap.getF2dmap();
			for (Iterator<F2DMapItem> f = f2dmapItems.iterator(); f.hasNext();) {
				F2DMapItem fItem = f.next();
				Dimension d = new Dimension(fItem.getDimension());
				boolean found = false;
				for(Dimension dSetDim:dimSet){
					if(d.equals(dSetDim)){
						d=dSetDim;
						found = true;
						break;
					}
				}
				if(!found)
					dimSet.add(d);

				// Add the dimension to the Fields in Table to fields map
				Table t = tables.getTable(fItem.getTable());
				if (t != null) {
					Field field = t.getField(fItem.getField());
					field.setDimension(d);
				}

			}

			dimensions.setDimensions(dimSet);
		}
	}

	private void processTable2FieldMap() {
		if (T2FMapFileName != null) {
			tables = registry.readTable2FieldMap(T2FMapFileName); // Tables will
			fields = new Fields();														// be
			// unchanged,
			// except for adding the table
			// information to the field

			for (Table t : tables.getTables()) {
				for (Field f : t.getFields()) {
					f.setTable(t);
					fields.addField(f);
				}
			}
		}
	}

	public Fields getFields() {
		return fields;
	}

	public void setFields(Fields fields) {
		this.fields = fields;
	}

}
