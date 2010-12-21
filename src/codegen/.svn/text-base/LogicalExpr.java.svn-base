/*
 * COMS W4115 PROGRAMMING LANGUAGES AND TRANSLATORS FALL 2009
 * Team llamamelon - BALL language
 * Authors: Cipta Herwana, Daniel Lasry, Sam Lee, Nathan Miller, Jordan Schau 
 * LogicalExpr.java - Generates code for logical Expressions
 */
package codegen;

import compiler.SymbolTable;
import lexer.Identifier;
import lexer.Type;

/**
 * Logical Expressions, extend Expr
 */
public class LogicalExpr extends Expr {

    /**
     * Constructor - Takes an operator, left expression and right expression
     * If Right expression is null, the NOT op was used (!)
     * @param op
     * @param exprL
     * @param exprR
     */
    public LogicalExpr(Op op, Expr exprL, Expr exprR) {
        this.operator = op;
        this.valueL = exprL;
        this.valueR = exprR;
    }

    public enum Op {
        AND, OR, NOT
    }
    
    /**
     * logical expressions must be taken apart to make sure what needs to be
     * eval-ed is evaled and what can't be eval-ed doesn't get evaluated.
     * 
     * For example, suppose f1, f2 and f3 all modify global vars:
     * f1() && f2()
     * needs to be translated to
     * res = f1();
     * if (res) {
     *     // code for f2()
     *     res = f2();
     * }
     * ...
     * <expr ... (res substitutes the expression) ... >
     */
    @Override
    public String code(SymbolTable table) {
        InsertionPoint in = table.getIP();
        
    	if (valueR == null)
            // this one doesn't need special rules
    		return "!(" + valueL.code(table) + ")";
        
    	if(! valueL.getType(table).equals(valueR.getType(table))) {
    		throwErr("expr: type mismatch " + valueL.getType(table) + " and " + valueR.getType(table), valueL.code(table) + getOpCode() + valueR.code(table));
    	}
    	if(! valueL.getType(table).equals(Type.bool)) {
    		throwErr("expr: type is not bool: " + valueL.getType(table) + " and " + valueR.getType(table), valueL.code(table) + getOpCode() + valueR.code(table));
    	}
        
    	String lcode = valueL.code(table);
        Identifier res;
        if (valueL instanceof LogicalExpr) {
            LogicalExpr prev = (LogicalExpr)valueL;
            if (prev.operator == this.operator) {
                res = prev.getRes();
            } else {
                res = table.newID();
                in.insert(table.indent() + Type.bool.getType() + " " + res.getID() + " = " + lcode + ";\n");
            }
        } else {
            res = table.newID();
            in.insert(table.indent() + Type.bool.getType() + " " + res.getID() + " = " + lcode + ";\n");
        }

        this.resVar = res;
        
        String test = res.getID();
        if (operator == Op.OR) test = "!" + test;
        in.insert(table.indent() + "if (" + test + ") {\n");
        SymbolTable inTable = new SymbolTable(false, table);
        
        String rcode = valueR.code(inTable); // insert R init code first
        in.insert(inTable.indent() + res.getID() + " = " + rcode + ";\n");
        in.insert(table.indent() + "}\n");
        
    	return res.getID();
    }
    
    private Identifier getRes() {
        if (resVar == null) 
            throwErr("expression not generated yet.");
        return resVar;
    }

    @Override
    public Type getType(SymbolTable table) {
    	return Type.bool ;
    }
    
    private String getOpCode() {
        switch(operator) {
        case AND: return "&&";
        case OR: return "||";
        case NOT: return "!";
        default:
            throw new IllegalArgumentException("assignment: unknown operator");
        }
    }
    
	Op operator;
    Expr valueL;
    Expr valueR;
    private Identifier resVar = null;
    
}
    
    
