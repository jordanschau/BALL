/*
 * COMS W4115 PROGRAMMING LANGUAGES AND TRANSLATORS FALL 2009
 * Team llamamelon - BALL language
 * Authors: Cipta Herwana, Daniel Lasry, Sam Lee, Nathan Miller, Jordan Schau 
 * IterationStmt.java - Generates code for Iteration Statements
 */
package codegen;

import lexer.Identifier;
import lexer.Type;
import compiler.SymbolTable;
import java.util.LinkedList;
import java.util.Iterator;

/*
 * Iteration Statements extends Stmt
 */

/**
 * @sam
 * 
 */
public class IterationStmt extends Stmt {

   
    public IterationStmt(LinkedList<Stmt> bodylist) {
    	this.bodylist = bodylist;
    }
	public IterationStmt(Expr expr, LinkedList<Stmt> bodylist) {
    	this.expr = expr;
    	this.bodylist = bodylist;
    
    }
    
    public IterationStmt(Identifier ident1, Expr ident2, LinkedList<Stmt> bodylist){
    	this.element = ident1;
    	this.collection = ident2;
    	this.bodylist = bodylist;
    }

    
    @Override
    public String stmtCode(SymbolTable table) {
    	
    	// Infinite do loop
    	if (expr == null && element == null && collection == null) {
    		String loopCode = "while (true) { \n";
    		
    		SymbolTable inTable = new SymbolTable(true, table);
    		Iterator<Stmt> bodyIter = bodylist.iterator();
    		while (bodyIter.hasNext()) {
    			Stmt cur = bodyIter.next();
    			loopCode += cur.code(inTable) + "\n"; // code for each statement    
    		}
    		
    		loopCode += table.indent() + "}";
    		return table.indent() + loopCode;
    	}
    	
    	// do x times loop
    	if (expr != null) {
   		
    		if(! expr.getType(table).equals(Type.number)) {
        		throwErr("Incorrect expression type '"+expr.code(table)+"' needs to be a number");
    		}
    		
    		Identifier nid = table.newID();
    		
        	String loopCode = "float " + nid.getID() + " = (float) 0; \n";
    		loopCode += table.indent() + "while ( " + nid.getID() + " < ";
    		loopCode += expr.code(table); 
    		loopCode += ") { \n";  
    		
    		SymbolTable inTable = new SymbolTable(true, table);
    		Iterator<Stmt> bodyIter = bodylist.iterator();
    		while (bodyIter.hasNext()) {
    			Stmt cur = bodyIter.next();
    			loopCode += cur.code(inTable) + "\n"; // code for each statement    
    		}
        
    		loopCode += table.indent() + nid.getID() + "++; \n";
    		loopCode += table.indent() + "}";
        
    		return table.indent() + loopCode;
    	}
    	
    	// for each loop
    	if (element != null && collection != null) {
    		// get list type
    		Type collectionType = collection.getType(table);
    		
    		if (!(collectionType instanceof ListType)) {
    			throwErr("not iterating over a list");
    		}
    		ListType cType = (ListType) collectionType;
    		
    		if (!table.available(element))
    			throwErr("duplicate variable " + element);

    		SymbolTable inTable = new SymbolTable(false, table);
    		inTable.putEntry(element, new Declaration(cType.contents, element, null));
    		
    		String loopCode = "for ( " + cType.contents.getType() + " "+ element.getID()+" : "+collection.code(table)+" ) {\n";
    		
    		Iterator<Stmt> bodyIter = bodylist.iterator();
    		while (bodyIter.hasNext()) {
    			Stmt cur = bodyIter.next();
    			loopCode += cur.code(inTable) + "\n"; // code for each statement    
    		}
    		    loopCode +="}\n";
    		    return table.indent() + loopCode;
    	}
    	// return nothing, if it can't match to a loop type
    	String loopCode = "";
    	return table.indent() + loopCode;
    	
    }
    

   
    Expr expr, collection;
    Identifier element;
    LinkedList<Stmt> bodylist;
}
