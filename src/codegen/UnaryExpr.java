/*
 * COMS W4115 PROGRAMMING LANGUAGES AND TRANSLATORS FALL 2009
 * Team llamamelon - BALL language
 * Authors: Cipta Herwana, Daniel Lasry, Sam Lee, Nathan Miller, Jordan Schau 
 * UnaryExpr.java - Generates code for Unary Expressions
 */
package codegen;

import compiler.SymbolTable;
import lexer.Identifier;
import lexer.Type;

/**
 * Arithmetic Expressions, extend Expr
 */
public class UnaryExpr extends Expr {

    /**
     * Constructor - Takes an operator, left expression and right expression
     * @param op
     * @param exprL
     * @param exprR
     */
    public UnaryExpr(Op op, Expr expr, Fix f) {
        this.operator = op;
        this.value = expr;
        this.fix = f;
    }

    public enum Op {
        PPLUS, MMIN
    }
    
    public enum Fix {
        PRE, POST
    }
    
    @Override
    public String code(SymbolTable table) {
        String code = value.code(table);
        Type type = value.getType(table);
        InsertionPoint insert = table.getIP();
        Identifier tempID = table.newID();
        
        /* MUST BE OF TYPE NUMBER */
        if (type !=null && type.equals(Type.number)) {
        	//prefix
        	if (getFix()) {
        		insert.insert(table.indent() + "float " + tempID.getID() + " = " + getOpCode() + code + ";\n");
        		return tempID.getID();
        	}
        	else {
        		insert.insert(table.indent() + "float " + tempID.getID() + " = " + code + getOpCode() + ";\n");
        		return tempID.getID();
        	}
        } 
        else {
            throw throwErr("expr: type " + type + " unsuitable for unary operation " + getOpCode(), getOpCode());
        }
    }
    
    @Override
    public Type getType(SymbolTable table) {
        Type type = value.getType(table);
        
        if (type.equals(Type.number)) {
            return Type.number;
        } 
        else {
            throw throwErr("expr: type " + value.getType(table) + " unsuitable for unary operation " + getOpCode(), getOpCode());
        }
    }
    
    private String getOpCode() {
        // TODO Auto-generated method stub
        switch(operator) {
        case PPLUS: return "++";
        case MMIN: return "--";
        default:
            throw new IllegalArgumentException("assignment: unknown operator");
        }
    }
    
    /**
     * 
     * @return true for prefix, false for postfix
     */
    private boolean getFix() {
        // TODO Auto-generated method stub
        switch(fix) {
        case PRE: return true;
        case POST: return false;
        default:
            throw new IllegalArgumentException("assignment: unknown operator");
        }
    }
    
	Op operator;
    Expr value;
    Fix fix;
}
    
    
