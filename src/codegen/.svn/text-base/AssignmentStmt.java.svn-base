/*
 * COMS W4115 PROGRAMMING LANGUAGES AND TRANSLATORS FALL 2009
 * Team llamamelon - BALL language
 * Authors: Cipta Herwana, Daniel Lasry, Sam Lee, Nathan Miller, Jordan Schau 
 * AssignmentStmt.java - Code Generator for Assignment statements
 */

package codegen;

import lexer.Identifier;
import lexer.Type;
import compiler.SymbolTable;

/**
 * Assignment statements. Identifier on the left, expression on the right.
 * Assignments are not expressions.
 */
public class AssignmentStmt extends Stmt {

    public AssignmentStmt(Identifier identifier, Op op, Expr expr) {
        this.name = identifier;
        this.operator = op;
        this.value = expr;
    }

    public enum Op {
        EQL, PLUSEQL, MINEQL, MULTEQL, DIVEQL, MODEQL
    }

    /*
     * (non-Javadoc)
     * 
     * @see codegen.ParseTreeNode#code(compiler.SymbolTable)
     */
    @Override
    public String stmtCode(SymbolTable table) {
        /*
         * 1. check name 
         * 2. check operator vs type 
         * 3. check value
         */
        Object data = table.getEntry(this.name);
        if (data == null || !(data instanceof Declaration)) {
            throw new RuntimeException("error: unknown variable + " + name);
        }
        
        Declaration decl = (Declaration) data;
        if (!(this.operator == Op.EQL)) {
            // if operator is not plain assignment, can only support numbers
            if (!(decl.type().equals(new Type("number")))) {
                throw new RuntimeException("error: nonstandard assignment "
                        + "with non-number variable " + name);
            }
        }
        
        Type valType = value.getType(table);
        
        if (valType instanceof ListType && (decl.type.equals(Type.list) || decl.type() instanceof ListType))
            decl.type = valType;
        
        if (!(value.getType(table).equals(decl.type()))) {
            throw new RuntimeException("assign: expression type mismatch in"+
                    " assignment of variable " + name + ",\n\texpected " + 
                    decl.type() + " but got " + value.getType(table));
        }
        
        // all OK, output the assignment code
        String result = name.getID() + " " + getOpCode() +
            " " + value.code(table) + ";";
        
        return table.indent() + result;
    }

    private String getOpCode() {
        // TODO Auto-generated method stub
        switch(operator) {
        case EQL: return "=";
        case PLUSEQL: return "+=";
        case MINEQL: return "-=";
        case MULTEQL: return "*=";
        case DIVEQL: return "/=";
        case MODEQL: return "%=";
        default:
            throw new IllegalArgumentException("assignment: unknown operator");
        }
    }

    Identifier name;

    Op operator;

    Expr value;

}
