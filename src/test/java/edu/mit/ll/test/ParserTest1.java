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
import edu.mit.ll.parser.Tokenizer;


public class ParserTest1 {

    static String testName = "ParserTest";    
//    static Logger log = Logger.getLogger(ParserTest.class);
    



    
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @BeforeClass
    static public void fileSetUp() throws Exception {
    	

    }
    
    @Test
    public void test() {
		
    	
		Tokenizer t = new Tokenizer();
		String ast = t.tokenize("(.ALL_TABLES. webwasher.DIMENSIONS.fqdn.LLCYSA1:dest.))");
		System.out.println(ast);
		System.out.println("------------------------------------------");
		
		
		ast = t.tokenize("ALL_TABLES. webwasher.DimensionSet.fqdn.LLCYSA1:dest");
		System.out.println(ast);
		System.out.println("------------------------------------------");
		
		
		ast = t.tokenize("ALL_FIELDS.webwasher.DimensionSet.fqdn.LLCYSA1:dest");
		System.out.println(ast);
		System.out.println("------------------------------------------");
		
		ast = t.tokenize("ALL_FIELDS.Time.LLCYSA1:dest");
		System.out.println(ast);
		System.out.println("------------------------------------------");
		
		ast = t.tokenize("ALL_FIELDS.Time.LLCYSA1:dest.AND.ALL_FIELDS.domain.XYZ:blah");
		System.out.println(ast);
		System.out.println("------------------------------------------");
		
		ast = t.tokenize("ALL_FIELDS.Time.LLCYSA1:dest.AND.ALL_FIELDS.domain.XYZ:blah.OR.ALL_FIELDS");
		System.out.println(ast);
		System.out.println("------------------------------------------");
		
		ast = t.tokenize("(.(.ALL_FIELDS.Time.LLCYSA1:dest.AND.ALL_FIELDS.domain.XYZ:blah.).OR.ALL_FIELDS.)");
		System.out.println(ast);
		System.out.println("------------------------------------------");
    }

}
