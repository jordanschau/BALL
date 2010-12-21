/*
 * COMS W4115 PROGRAMMING LANGUAGES AND TRANSLATORS FALL 2009
 * Team llamamelon - BALL language
 * Authors: Cipta Herwana, Daniel Lasry, Sam Lee, Nathan Miller, Jordan Schau 
 * StopdoStmt.java - Generates code for stopdo
 */
package codegen;

import compiler.SymbolTable;

import lexer.*;

public class StopdoStmt extends Stmt {

    
    public StopdoStmt() {
       
    }

   
  
    @Override
    public String stmtCode(SymbolTable table) {
        
        String stmt = "break; ";
       
        return table.indent() + stmt;
    }

}