/*
 * COMS W4115 PROGRAMMING LANGUAGES AND TRANSLATORS FALL 2009
 * Team llamamelon - BALL language
 * Authors: Cipta Herwana, Daniel Lasry, Sam Lee, Nathan Miller, Jordan Schau 
 * Expr.java - Code Generator for expressions
 */

package codegen;

import compiler.SymbolTable;

import lexer.*;

/**
 * Implements atomic expressions.
 * 
 * An expression has a type associated with it, which can be output by
 * getType(). In case the expression is an identifier, a symbol table is needed to figure out what 
 */
public class AtomicExpr extends Expr {

    public AtomicExpr(StringConst const1) {
        this.stringval = const1.val;
        type = new Type("string");
    }

    public AtomicExpr(Identifier id) {
        this.stringval = id.getID();
        this.ident = id;
        // TODO: find out identifier type through symbol table.
        type = new Type("identifier");
    }

    public AtomicExpr(NumericConst n) {
        this.stringval = n.val;
        type = new Type("number");
    }

    public AtomicExpr() {
    }
    
    public AtomicExpr(Expr expr) {
    	this.inside = expr;
    	this.stringval = null;
    	this.type = null;
    }
    
    public Type getType(SymbolTable table) {
    	if (this.inside != null) {
    		return inside.getType(table);
    	}
        if (this.type.val.equals("identifier")) {
            Object val = table.getEntry(this.ident);
            if (val == null) {
                throwErr("expr: unknown identifier " + ident.getID(), ident.getID());
            } else if (val instanceof FuncDef) {
                throwErr("expr: identifier " + ident.getID() 
                        + " is a function.", ident.getID());
            } else if (val instanceof Declaration)
                return ((Declaration)val).type();
        }
        return new Type(this.type.val);
    }

    /**
     * @return Java code for this expression.
     */
    public String code(SymbolTable table) {
    	if(this.inside != null) {
    		return "(" + this.inside.code(table) + ")";
    	}
        if (this.type.val.equals("identifier")) {
            Object val = table.getEntry(this.ident);
            if (val instanceof StatDef) {
            	StatDef decl = (StatDef)val;
            	return decl.name.getID();
            }
        }
        return this.stringval;
    }

    public String toString() {
        return stringval;
    }

    private Type type;

    private String stringval;
    private Identifier ident = null;

    public static final int STRINGCONST = 1;

    public static final int IDENTIFIER = 2;

    public static final int NUMERICCONST = 3;
    
    private Expr inside = null;
}
