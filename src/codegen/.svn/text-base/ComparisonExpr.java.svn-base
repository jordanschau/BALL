/*
 * COMS W4115 PROGRAMMING LANGUAGES AND TRANSLATORS FALL 2009
 * Team llamamelon - BALL language
 * Authors: Cipta Herwana, Daniel Lasry, Sam Lee, Nathan Miller, Jordan Schau 
 * ComparisonExpr.java - Code Generator for comparison Expressions
 */
package codegen;

import compiler.SymbolTable;
import lexer.Type;

/**
 * Logical Expressions, extend Expr
 */
public class ComparisonExpr extends Expr {

    /**
     * Constructor - Takes an operator, left expression and right expression
     * If Right expression is null, the NOT op was used (!)
     * @param op
     * @param exprL
     * @param exprR
     */
    public ComparisonExpr(Op op, Expr exprL, Expr exprR) {
        this.operator = op;
        this.valueL = exprL;
        this.valueR = exprR;
    }

    public enum Op {
        IS, ISNOT, GT, LT, GTE, LTE
    }
    
    @Override
    public String code(SymbolTable table) {
    	if (valueR == null)
    		return "! " + valueL.code(table);
    	if(! valueL.getType(table).equals(valueR.getType(table))) {
    		throwErr("expr: type mismatch " + valueL.getType(table) + " and " + valueR.getType(table), valueL.code(table) + getOpCode() + valueR.code(table));
    	}
    	String result;
    	if(valueL.getType(table).equals(Type.string)) {
    		result = valueL.code(table);
    		result += ".equals(";
    		result += valueR.code(table);
    		result += " )";
    	}
    	else {
    	    	
    		result = valueL.code(table);
    			result += " " + getOpCode() + " ";
    			result += valueR.code(table);
    	}
    	
    	return result;
    	
    }
    
    @Override
    public Type getType(SymbolTable table) {
    	return Type.bool ;
    }
    
    private String getOpCode() {
        // TODO Auto-generated method stub
        switch(operator) {
        case IS: return "==";
        case ISNOT: return "!=";
        case GT: return ">";
        case LT: return "<";
        case GTE: return ">=";
        case LTE: return "<=";
        default:
            throw new IllegalArgumentException("assignment: unknown operator");
        }
    }
    
	Op operator;
    Expr valueL;
    Expr valueR;
}
    
    
