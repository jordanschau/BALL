/*
 * COMS W4115 PROGRAMMING LANGUAGES AND TRANSLATORS FALL 2009
 * Team llamamelon - BALL language
 * Authors: Cipta Herwana, Daniel Lasry, Sam Lee, Nathan Miller, Jordan Schau 
 * Expr.java - Dummy Expr
 */
package codegen;

import lexer.Type;
import compiler.SymbolTable;

/**
 * Note: Translated expression bodies need to be side-effect free. That way
 * things like the 'where' insertion works correctly. However, some function
 * calls might have side effects and must have their evaluations 'insert'-ed.
 * 
 * Expressions that have special evaluation rules also need to have their own
 * 'insert' rules (things like AND and OR). The code's going to look ugly but
 * there's no other way.
 * 
 */
public abstract class Expr extends ParseTreeNode {

    public abstract Type getType(SymbolTable table);

}
