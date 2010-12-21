/*
 * COMS W4115 PROGRAMMING LANGUAGES AND TRANSLATORS FALL 2009
 * Team llamamelon - BALL language
 * Authors: Cipta Herwana, Daniel Lasry, Sam Lee, Nathan Miller, Jordan Schau 
 * Stmt.java - Code Generator for statements
 */

package codegen;

import compiler.SymbolTable;

public abstract class Stmt extends ParseTreeNode implements InsertionPoint {
    
    /**
     * By default, inserts done after a previous insert are APPENDED to the
     * insert chain (meaning the latest insert is closest to the original
     * statement)
     */
    public void insert(String code) {
        insert += code;
    }
    
    /**
     * Don't touch this
     */
    public String code(SymbolTable table) {
        table.setInsertPt(this);
        String stmt = stmtCode(table);
        return insert + stmt;
    }
    
    abstract String stmtCode(SymbolTable table);
    
    protected String insert = "";

}
