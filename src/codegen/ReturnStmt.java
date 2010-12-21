/*
 * COMS W4115 PROGRAMMING LANGUAGES AND TRANSLATORS FALL 2009
 * Team llamamelon - BALL language
 * Authors: Cipta Herwana, Daniel Lasry, Sam Lee, Nathan Miller, Jordan Schau 
 * ReturnStmt.java - Return Value depends on what expr evalutates to
 */
package codegen;

import compiler.SymbolTable;

import lexer.*;

public class ReturnStmt extends Stmt {

    public ReturnStmt(Expr expr) {
        this.expression = expr;
    }
    
    public ReturnStmt() {
        this.expression = null;
    }

    public final Expr expression;

    /**
     * Return value depends on what the expression evaluates to, which in turn
     * depends on the expression's type, which (might) depend on what type the
     * variables found inside the expression are.
     */
    public Type getType(SymbolTable table) {
        if (this.expression == null) {
            return new Type("nothing");
        }
        return expression.getType(table);
    }

    @Override
    public String stmtCode(SymbolTable table) {
        
        String stmt = "return ";
        stmt += expression.code(table);
        stmt += ";";
        return table.indent() + stmt;
    }

}
