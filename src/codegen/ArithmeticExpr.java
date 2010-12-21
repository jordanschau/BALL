/*
 * COMS W4115 PROGRAMMING LANGUAGES AND TRANSLATORS FALL 2009
 * Team llamamelon - BALL language
 * Authors: Cipta Herwana, Daniel Lasry, Sam Lee, Nathan Miller, Jordan Schau 
 * ArithmeticExpr.java - Code Generator for Arithmetic Expressions
 */

package codegen;

import compiler.SymbolTable;
import lexer.Type;

/**
 * Arithmetic Expressions, extend Expr
 */
public class ArithmeticExpr extends Expr {

	 /**
     * Constructor - Takes an operator, left expression and right expression
     */
    public ArithmeticExpr(Op op, Expr exprL, Expr exprR) {
        this.operator = op;
        this.valueL = exprL;
        this.valueR = exprR;
    }
	
	public enum Op {
        PLUS, MIN, MULT, DIV, MOD
    }
    
	/*
	 * Arithmethic operations, especially '+' are overloaded. + can work on
	 * string + any or any + string, list + list, or number + number.
	 */
    @Override
    public String code(SymbolTable table) {
        String lcode = valueL.code(table);
        String rcode = valueR.code(table);
        Type ltype = valueL.getType(table);
        Type rtype = valueR.getType(table);
        
        /* STRING CONCATENATION */
        if (ltype.equals(Type.string)) {
            // convert r to string
            if (rtype.equals(Type.number))
                //fixFloat
            	return lcode + ".concat(Tools.fixFloat(" + rcode + "))";
            return lcode + ".concat((" + rcode + ").toString())";
        }
        if (rtype.equals(Type.string)) {
            // convert r to string
            if (ltype.equals(Type.number))
                //fixFloat here too
            	return "Tools.fixFloat(" + lcode + ").concat(" + rcode + ")";
            return lcode + ".toString().concat(" + rcode + ")";
        }
        
    	if(! ltype.equals(rtype)) {
    		throwErr("expr: type mismatch " + valueL.getType(table) + " and " + valueR.getType(table), valueL.code(table) + getOpCode() + valueR.code(table));
    	}
    	if (ltype.equals(Type.number)) {
            /* Number addition */
            String result = lcode;
    		result += " " + getOpCode() + " ";
    		result += rcode;
    		return result;
        } else if (ltype instanceof ListType) {
            /* List append */
            String result = "(" + lcode + ").append(";
            result += rcode;
            return result + ")";
        } else {
            throw throwErr("expr: type " + ltype + " unsuitable for addition.", valueL.code(table) + getOpCode() + valueR.code(table));
        }
    }
    
    @Override
    public Type getType(SymbolTable table) {
        Type ltype = valueL.getType(table);
        Type rtype = valueR.getType(table);
        
        if (ltype.equals(Type.string) || rtype.equals(Type.string)) {
            return Type.string;
        }
        
        if(! ltype.equals(rtype)) {
            throwErr("expr: type mismatch " + ltype + " and " + valueR.getType(table), valueL.code(table) + getOpCode() + valueR.code(table));
        }
        
        if (ltype.equals(Type.number) || 
                (ltype instanceof ListType) ||
                ltype.equals(Type.string)) {
            return ltype;
        } else {
            throw throwErr("expr: type " + valueL.getType(table) + " unsuitable for addition.", valueL.code(table) + getOpCode() + valueR.code(table));
        }
    }
    
    private String getOpCode() {
        // TODO Auto-generated method stub
        switch(operator) {
        case PLUS: return "+";
        case MIN: return "-";
        case MULT: return "*";
        case DIV: return "/";
        case MOD: return "%";
        default:
            throw new IllegalArgumentException("assignment: unknown operator");
        }
    }
    
	Op operator;
    Expr valueL;
    Expr valueR;
}
    
    
