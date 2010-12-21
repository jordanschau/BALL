/*
 * COMS W4115 PROGRAMMING LANGUAGES AND TRANSLATORS FALL 2009
 * Team llamamelon - BALL language
 * Authors: Cipta Herwana, Daniel Lasry, Sam Lee, Nathan Miller, Jordan Schau 
 * StatExpr.java - Generates code for stat exprs
 */
package codegen;

import compiler.SymbolTable;

/**
 * Implements addition, the highest level
 */
public class StatExpr extends ParseTreeNode {
    
    public StatExpr(StatMult ls) {
        this(null,ls,null);
    }
    
    public StatExpr(StatExpr ls, StatMult rs, Op oper) {
        left = ls;
        right = rs;
        op = oper;
    }
    
    @Override
    public String code(SymbolTable table) {
        if (left == null) return right.code(table);
        String mid = getOp();
        return left.code(table) + mid + right.code(table);
    }
    
    private String getOp() {
        switch(op) {
        case PLUS: return " + ";
        case MIN: return " - ";
        default:
            throw new IllegalArgumentException("statMult: unknown operator");
        }
    }

    public enum Op {
        PLUS, MIN
    }
    
    StatExpr left;
    StatMult right;
    Op op;

}
