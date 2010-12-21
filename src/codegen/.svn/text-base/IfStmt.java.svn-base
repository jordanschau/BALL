/*
 * COMS W4115 PROGRAMMING LANGUAGES AND TRANSLATORS FALL 2009
 * Team llamamelon - BALL language
 * Authors: Cipta Herwana, Daniel Lasry, Sam Lee, Nathan Miller, Jordan Schau 
 * IfStmt.java - nodes representing 'if' statements
 */

package codegen;

import java.util.Iterator;
import java.util.LinkedList;

import compiler.SymbolTable;

public class IfStmt extends Stmt {

    /*Constructor 1: expression to evaluate, no else. */
    public IfStmt(Expr expr, LinkedList<Stmt> bodylist) {
    	this.expr = expr;
	this.bodylist = bodylist;
    }

    /*Constructor 2: expression to evaluate, with else. */
    public IfStmt(Expr expr, LinkedList<Stmt> bodylist, LinkedList<Stmt> elselist){
    	this.expr = expr;
	this.bodylist = bodylist;
	this.elselist = elselist;
    }
    
    @Override
    public String stmtCode(SymbolTable table) {
        String begin = table.indent() + "if (" + expr.code(table) + ") {\n";

	/* declarations made in if statements
	 * should not be accessible anywhere outside */
	SymbolTable bodyTable = new SymbolTable(true, table);

	/* Iterate through the body statements */
        Iterator<Stmt> bodyIter = bodylist.iterator();
        while (bodyIter.hasNext()) {
            Stmt cur = bodyIter.next();
            begin += cur.code(bodyTable) + "\n"; // code for each statement    
        }

        begin += table.indent() + "}";

	/**** CASE WITH AN ELSE STATEMENT ****/
	if(elselist != null){
	    begin += " else {\n";
	    
	    /* declarations made in else statements
	     * should not be accessible anywhere outside */
	    SymbolTable elseTable = new SymbolTable(true, table);

	    /* Iterate through the else body statements */
            Iterator<Stmt> elseIter = elselist.iterator();
            while (elseIter.hasNext()) {
                Stmt cur = elseIter.next();
                begin += cur.code(elseTable) + "\n"; // code for each statement    
            }
   	    begin += table.indent() + "}";
	}
	/**** END ELSE CASE ****/

        return begin;
    }
    
    protected String global = null;

    LinkedList<Stmt> bodylist;

    LinkedList<Stmt> elselist = null;

    Expr expr;

}

