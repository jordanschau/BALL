/*
 * COMS W4115 PROGRAMMING LANGUAGES AND TRANSLATORS FALL 2009
 * Team llamamelon - BALL language
 * Authors: Cipta Herwana, Daniel Lasry, Sam Lee, Nathan Miller, Jordan Schau 
 * ListInit.java - Implements List Declarations
 */
package codegen;

import java.util.ArrayList;

import lexer.Type;
import compiler.SymbolTable;

/**
 * Implements list declarations.
 * 
 * Lists in BALL is its own data type. The contents of the list must have the
 * same type.
 */
public class ListInit extends AtomicExpr {

    public ListInit(Type type) {
	this.t = type;
        this.args = new ArrayList<Expr>();
    }

    public ListInit(ArrayList<Expr> args) {
        this.args = args;
    }
    
    public String code(SymbolTable table) {
        ListType myType = (ListType)getType(table);
        
        // type checking done
        // new BallList<T>(new T[] { a, b, c })
        String code = "new " + myType.getType() + "(new Object[] {\n";
        
        table.increaseIndent(1);
        for (Expr content : args) {
            code += table.indent() + content.code(table) + " ,\n";
        }
        table.decreaseIndent(1);
        return code + table.indent() + "})";
            
    }
    
    public Type getType(SymbolTable table) {
        Type e = null;
        for (Expr expr : args) {
            e = expr.getType(table);
            if (t == null) t = e;
            if (!t.equals(e)) {
                /*
                 * It isn't specified in the reference manual whether BALL
                 * supports lists of different types being reassigned to the
                 * same variable. This implementation doesn't allow that.
                 * Therefore, a list variable is confined to always point
                 * to lists of the same content type.
                 */
                throwErr("listinit: element type mismatch, expected " 
                        + t + ", got " + e);
            }
        }
        
        return new ListType(t);
    }
    
    private Type t = null;
    private ArrayList<Expr> args;

}
