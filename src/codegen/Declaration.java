/*
 * COMS W4115 PROGRAMMING LANGUAGES AND TRANSLATORS FALL 2009
 * Team llamamelon - BALL language
 * Authors: Cipta Herwana, Daniel Lasry, Sam Lee, Nathan Miller, Jordan Schau 
 * Declaration.java - Code Generator for declaration
 */
package codegen;
/*
 * Team llamamelon - BALL language
 * Declaration.java - Code Generator for variable declarations
 */

import lexer.*;

import java.util.ArrayList;

import compiler.SymbolTable;

public class Declaration extends Stmt {

	// Constructor: quick for single definitions
	public Declaration(Type type, Identifier name, Expr ex) {
		this.type = type;
		this.idexpPairs = new ArrayList<Object[]>();
		Object[] entry = new Object[] { name, ex };
		idexpPairs.add(entry);
	}

    // Constructor: Get Type and Identifiers
    public Declaration(Type type, ArrayList<Object[]> identifiers) {
        this.type = type; // extract the Strings
        idexpPairs = identifiers;
    }
    
    /**
     * Initializes declaration type with the type specified by exprType (the
     * type of the expression following the declaration).
     */
    public void initDeclType(Type exprType) {
        
        if (!(exprType.equals(this.type))) {
            throwErr("declaration: expression type " + 
                    exprType + " does not match type; expected " 
                    + this.type);
        }

    }
    
    // generate the declaration
    public String stmtCode(SymbolTable table) {
        String begin = table.indent() + (type.getType() + " ");
        
        int i;
        for (i = 0; i < idexpPairs.size(); i++) { // set the identifier string
            // to id1, id2, ...
            Object[] ar = idexpPairs.get(i);
            Identifier id = (Identifier) ar[0];
            if (!table.available(id)) {
                throwErr("declaration: init: identifier " + id + " in use.", id.getID());
            }

            if (i > 0) // dont put a comma at the beginning
                begin += ("," + id.getID());
            else
                begin += (id.getID());

            // check is the last identifier has an = sign for assignment
            if (ar[1] != null) {
                
                Expr x = (Expr) ar[1];
                initDeclType(x.getType(table));

                begin += (" = ");
                begin += x.code(table);
            }
            
            table.putEntry(id, this);
        }

        begin += (";");
        return begin;
    }

    /**
     * Treat this function as a global variable, and print the initialization in
     * main().
     * 
     * number p = 3, b = 5.4; ==> p = 3;
     *                            b = 5.4;
     * (genGlobalDecl takes care of the declarations)
     */
    public void genGlobalMain(SymbolTable table) {
        table.setInsertPt(this);
        String begin = table.indent();

        int i;
        for (i = 0; i < idexpPairs.size(); i++) { // set the identifier string
            // to id1, id2, ...
            Object[] ar = idexpPairs.get(i);
            Identifier id = (Identifier) ar[0];
            
            if (!table.available(id)) {
                throwErr("declaration: global_main: identifier " + id + " in use.", id.getID());
            }

            // check is the last identifier has an = sign for assignment
            if (ar[1] != null) {
                begin += (id.getID());
                
                Expr x = (Expr) ar[1];
                initDeclType(x.getType(table));
                
                begin += " = ";
                begin += x.code(table);
                begin += "; ";
            }
            
            table.putEntry(id, this);
        }
        
        System.out.println(super.insert + begin);
    }
    
    /**
     * Print to stdout the declaration code, intended to be used by Program to
     * output global variable declarations in class level, not main()
     * @param table
     */
    public void genGlobalDecl(SymbolTable table) {
        String begin = (table.indent() + "private static " + type.getType() + " ");

        int i;
        for (i = 0; i < idexpPairs.size(); i++) { // set the identifier string
            // to id1, id2, ...
            Object[] ar = idexpPairs.get(i);
            Identifier id = (Identifier) ar[0];
            
            // check if all identifiers are entered correctly in the table
            if (!((table.getEntry(id)) == this)) {
                throwErr("declaration: init: redefining identifier " + id + ".", id.getID());
            }

            if (i > 0) // dont put a comma at the beginning
                begin += ("," + id.getID());
            else
                begin += (id.getID());
        }

        begin += (";");
        System.out.println(begin);
    }
    
    public Type type() {
        return type;
    }

    Type type;

    private ArrayList<Object[]> idexpPairs;

}
