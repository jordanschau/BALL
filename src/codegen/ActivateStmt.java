/*
 * COMS W4115 PROGRAMMING LANGUAGES AND TRANSLATORS FALL 2009
 * Team llamamelon - BALL language
 * Authors: Cipta Herwana, Daniel Lasry, Sam Lee, Nathan Miller, Jordan Schau 
 * ActivateStmt.java - Code Generator for activate statements
 */

package codegen;
import lexer.Identifier;

import compiler.SymbolTable;

/**
 * Activation statement replaces (doesn't keep around the old) the simfunction
 * used for the builtin sim(). It doesn't need to check the type because a
 * simfunction is already a special kind of function.  
 */
public class ActivateStmt extends Stmt {

	// just need to know the name.
    public ActivateStmt(Identifier theName) {
        this.name = theName;
    }
    
    // generate code to swap simfunction
    public String stmtCode(SymbolTable table) {
        Object def = table.getEntry(name);
        if (!(def instanceof SimFuncDef)) {
            throwErr("identifier " + name.getID()
                    + " invalid, either nonexistent or not a simfunction", name.getID());
        }

        String begin = "Simulator.theSimFunction = ";
        begin += name.getID();
        begin += (";");
        return table.indent() + begin;
    }
    
    private Identifier name;

}
