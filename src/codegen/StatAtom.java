/*
 * COMS W4115 PROGRAMMING LANGUAGES AND TRANSLATORS FALL 2009
 * Team llamamelon - BALL language
 * Authors: Cipta Herwana, Daniel Lasry, Sam Lee, Nathan Miller, Jordan Schau 
 * StatAtom.java - Refers to other stats inside a stat declaration
 */
package codegen;

import compiler.SymbolTable;

import lexer.Identifier;
import lexer.NumericConst;

/**
 * Stat atomic expressions are either identifiers, numeric constants, or
 * other stat expressions inside parentheses.
 */
public class StatAtom extends ParseTreeNode {
    
    public StatAtom(Identifier stat) {
        id = stat;
        num = null;
        par = null;
    }
    
    public StatAtom(NumericConst stat) {
        id = null;
        num = stat;
        par = null;
    }
    
    public StatAtom(StatExpr stat) {
        id = null;
        num = null;
        par = stat;
    }
    
    @Override
    public String code(SymbolTable table) {
        if (id != null) { // another stat
            StatDef mydef = null;
            Identifier arg = null;
            try {
                mydef = (StatDef) table.getEntry(this.id);
                arg = (Identifier) StatDef.getArgName(table);
            } catch (Exception e) {
                throwErr("statAtom: cannot fetch entries of stat and arg from symbol table");
            } finally {
                if (mydef == null || arg == null)
                    throwErr("statAtom: data extraction failure");
            }
            
            StatDef.consistentType(table, mydef);
            
            return mydef.name.getID() + ".get(" + arg.getID() + ")";
        } else if (num != null) {
            return num.val;
        } else {
            return "(" + par.code(table) + ")";
        }
    }
    
    private final Identifier id;
    private final NumericConst num;
    private final StatExpr par;

}
