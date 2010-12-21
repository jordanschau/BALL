/*
 * COMS W4115 PROGRAMMING LANGUAGES AND TRANSLATORS FALL 2009
 * Team llamamelon - BALL language
 * Authors: Cipta Herwana, Daniel Lasry, Sam Lee, Nathan Miller, Jordan Schau 
 * PrintStmt.java - Code Generator for print statements
 */

package codegen;

import compiler.SymbolTable;
import lexer.Type;

public class PrintStmt extends Stmt {

    public PrintStmt(Expr expr) {
        this.toprint = expr;
    }
    
    public String stmtCode(SymbolTable table) {
        Type thisType = toprint.getType(table);
        String begin;
        // if expr is of type number float fix it 
        if (thisType.equals(Type.number)){
        	begin = table.indent() + "System.out.println(Tools.fixFloat(";
        	begin += toprint.code(table);
        	begin += "));";
        }
        // else continue normally
        else { 
        	begin = table.indent() + "System.out.println("; 
        	begin += toprint.code(table);
            begin += (");");
        }
    	
               
        
        return begin;
    }
    
    private Expr toprint;

}
