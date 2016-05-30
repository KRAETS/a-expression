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
package edu.mit.ll.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;



public class Tokenizer {
	private enum TYPE {
		FIELDS, TAGS, TABLES, NULL
	};

	private enum REG_OP {
		DIMENSIONS, DIMENSIONSET
	}

	public String tokenize(String inputString) {

		Pattern pattern = Pattern.compile(Pattern.quote("."));

		String[] splits = pattern.split(inputString);

		
		String ast = "";

	
		int tokenCount = splits.length;

		//String[] splits2 = new String[tokenCount - 1];
		//System.arraycopy(splits, 1, splits2, 0, tokenCount - 1);
		
		return addressParser(tokenCount,TYPE.NULL, splits);

	}

	private String addressParser (int tokenCount, TYPE currentType , String[] splits2) {
		// System.out.println(splits2);
		String ast = "";
		
		if (splits2[0].trim().equals("ALL_FIELDS")) {		
			
			if (currentType == TYPE.NULL) ast = ast.concat("(ALL_FIELDS");
			else ast = ast.concat("ALL_FIELDS");
			currentType = TYPE.FIELDS;

		} else if (splits2[0].trim().equalsIgnoreCase("ALL_TABLES")) {	
			if (currentType == TYPE.NULL) ast = ast.concat("(ALL_TABLES");
			else ast = ast.concat("ALL_TABLES");
			//ast = ast.concat("ALL_TABLES");
			currentType = TYPE.TABLES;	

		} else if (splits2[0].trim().equalsIgnoreCase("(ALL_TAGS")) {
			if (currentType == TYPE.NULL) ast = ast.concat("ALL_TAGS");
			else ast = ast.concat("ALL_TAGS");
			ast = ast.concat("ALL_TAGS");
			currentType = TYPE.TAGS;
		} else	if (splits2[0].equalsIgnoreCase("(")) {
			// pass it on
			ast = ast.concat(splits2[0]);
			currentType = TYPE.TABLES;
			
		}else {
			System.out.println("Erroneous splits2[0]: " + splits2[0]);
			return null;
		}

		String t = null;
		for (int i = 1; i < tokenCount - 1; i++) {
//			System.out.println(ast);
			String token = splits2[i].trim();
			switch (currentType) {
			case FIELDS:
				if (token.equalsIgnoreCase("Dimensionset")) {
					// convert tables to dimensions
					ast = ast.concat(",DIMENSIONSET");
					// ast = ast.concat(token);
					currentType = TYPE.TABLES;
					break;
				}
				if (token.equalsIgnoreCase("(") | token.equalsIgnoreCase(")")) {
					// pass it on
					ast = ast.concat(token);
				
					break;
				}
				if (token.equalsIgnoreCase("AND")) {
					// split the string into two sections
					String[] splits3 = new String[tokenCount - (i+1)]; // AND is not needed
					System.arraycopy(splits2, i+1, splits3, 0, tokenCount - (i+1));
					ast= ast.concat("),(");
					ast = ast.concat(addressParser(splits3.length, currentType, splits3));
					//ast= ast.concat(")");
					ast = ast.concat("*intersectionFields*");
					currentType = TYPE.FIELDS;
					return ast;
					// ast = ast.concat(token);
					
				}
				if (token.equalsIgnoreCase("OR")) {
					// split the string into two sections
					String[] splits3 = new String[tokenCount - (i+1)]; 
					System.arraycopy(splits2, i+1, splits3, 0, tokenCount - (i+1));
					ast= ast.concat("),(");
					ast = ast.concat(addressParser(splits3.length, currentType, splits3));
					ast= ast.concat(")");
					ast = ast.concat(",unionFields");
					currentType = TYPE.FIELDS;
					return ast;
					// ast = ast.concat(token);
					
				}
				if (token.equalsIgnoreCase("NOT")) {
					// split the string into two sections
					String[] splits3 = new String[tokenCount - (i+1)]; 
					System.arraycopy(splits2, i+1, splits3, 0, tokenCount - (i+1));
					ast= ast.concat("),(");
					ast = ast.concat(addressParser(splits3.length, currentType, splits3));
					ast= ast.concat(")");
					ast = ast.concat(",minusFields");
					currentType = TYPE.FIELDS;
					return ast;
					// ast = ast.concat(token);
					
				}
				
				if (token.contains(":")) {
					// its a tag
					ast = ast.concat(",");
					ast = ast.concat(token);
					ast = ast.concat("*matchFieldsTagFields");
					currentType = TYPE.FIELDS;
					break;
				}

				else {
					ast = ast.concat(",");
					ast = ast.concat(token);
					ast = ast.concat("*matchFieldsDimensionsFields");

					currentType = TYPE.FIELDS;
				}

				break;

			case TABLES: // There are two possible operators, FIELDS or a
							// DimensionSet
				if (token.equalsIgnoreCase("Dimension")
						| token.equalsIgnoreCase("DIMENSIONS")) {
					// convert tables to dimensions
					ast = ast.concat(",DIMENSIONS");
					currentType = TYPE.FIELDS;
					break;

				} 	if (token.equalsIgnoreCase("(") | token.equalsIgnoreCase(")")) {
					// pass it on
					ast = ast.concat(token);
					currentType = TYPE.TABLES;
					break;
				}

				if (token.equalsIgnoreCase("Dimensionset")) {
					// convert tables to dimensions
					ast = ast.concat("*DIMENSIONSET");
					System.out
							.println(">>>>>>>>Error - DimensionSet operator on DimensionSets\n" + ast.concat("-->ERROR"));
					currentType = TYPE.TABLES;
					return null;
				} 			if (token.equalsIgnoreCase("AND")) {
					// split the string into two sections
					String[] splits3 = new String[tokenCount - (i+1)]; // AND is not needed
					System.arraycopy(splits2, i+1, splits3, 0, tokenCount - (i+1));
					ast= ast.concat("),(");
					ast = ast.concat(addressParser(splits3.length, currentType, splits3));
					ast= ast.concat(")");
					ast = ast.concat(",intersectionTables");
					currentType = TYPE.TABLES;
					return ast;
					// ast = ast.concat(token);
					
				}
				if (token.equalsIgnoreCase("OR")) {
					// split the string into two sections
					String[] splits3 = new String[tokenCount - (i+1)]; 
					System.arraycopy(splits2, i+1, splits3, 0, tokenCount - (i+1));
					ast= ast.concat("),(");
					ast = ast.concat(addressParser(splits3.length, currentType, splits3));
					ast= ast.concat(")");
					ast = ast.concat(",unionTables");
					currentType = TYPE.TABLES;
					return ast;
					// ast = ast.concat(token);
					
				}
				if (token.equalsIgnoreCase("NOT")) {
					// split the string into two sections
					String[] splits3 = new String[tokenCount - (i+1)]; 
					System.arraycopy(splits2, i+1, splits3, 0, tokenCount - (i+1));
					ast= ast.concat("),(");
					ast = ast.concat(addressParser(splits3.length, currentType, splits3));
					ast= ast.concat(")");
					ast = ast.concat(",minusTables");
					currentType = TYPE.TABLES;
					return ast;
					// ast = ast.concat(token);
					
				} else {
					ast = ast.concat(",");
					ast = ast.concat(token);
					ast = ast.concat("*matchTablesDimensionSetTable");
					currentType = TYPE.TABLES;

				}

				break;

			case TAGS:
				ast.concat("*matchFieldsTagsFields");
				ast = ast.concat(token);
				currentType = TYPE.FIELDS;
				break;

			default:
				System.out.println(">>>>>>>>Parse Error!" + token);

			}
		}

		if (currentType == TYPE.FIELDS) {
			ast = ast.concat(")");
			return ast;
		}
		else {
			System.out.println(">>>>>>>>ERROR: A-Expression doesn't resolve to Fields. Try Dimension operator at the end.\n" + ast.concat("-->ERROR"));
			return null;
		}
	}
}
