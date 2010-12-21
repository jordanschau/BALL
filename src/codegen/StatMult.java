/*
 * COMS W4115 PROGRAMMING LANGUAGES AND TRANSLATORS FALL 2009
 * Team llamamelon - BALL language
 * Authors: Cipta Herwana, Daniel Lasry, Sam Lee, Nathan Miller, Jordan Schau 
 * StatMult.java - Handles mult ops on stats
 */
package codegen;

import compiler.SymbolTable;

public class StatMult extends ParseTreeNode {
    
    public StatMult(StatAtom ls) {
        this(null,ls,null);
    }
    
    public StatMult(StatMult ls, StatAtom rs, Op oper) {
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
        case MULT: return " * ";
        case DIV: return " / ";
        case MOD: return " % ";
        default:
            throw new IllegalArgumentException("statMult: unknown operator");
        }
    }

    public enum Op {
        MULT, DIV, MOD
    }
    
    StatMult left;
    StatAtom right;
    Op op;

}
