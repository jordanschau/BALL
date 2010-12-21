/*
 * COMS W4115 PROGRAMMING LANGUAGES AND TRANSLATORS FALL 2009
 * Team llamamelon - BALL language
 * Authors: Cipta Herwana, Daniel Lasry, Sam Lee, Nathan Miller, Jordan Schau 
 * ExprStmt.java - Code Generator for Expression Statement 
 */

package codegen;

import compiler.SymbolTable;

public class ExprStmt extends Stmt {
    
    public ExprStmt() {
        this(null);
    }

    public ExprStmt(Expr expression) {
        this.expr = expression;
    }

    @Override
    public String stmtCode(SymbolTable table) {
        if (expr == null) return "";
        String outcode = expr.code(table).concat(";");
        // "purified" expression can safely be commented
        return table.indent() + "/*" + outcode + "*/";
    }
    
    private Expr expr;

}
