/*
 * COMS W4115 PROGRAMMING LANGUAGES AND TRANSLATORS FALL 2009
 * Team llamamelon - BALL language
 * Authors: Cipta Herwana, Daniel Lasry, Sam Lee, Nathan Miller, Jordan Schau 
 * Funcall.java - Code Generator for function calls
 */

package codegen;

import lexer.*;
import java.util.ArrayList;
import compiler.SymbolTable;

public class Funcall extends Expr {

    /* First constructor (with arguments) */
    public Funcall(Identifier name, ArrayList<Expr> args) {
        this.name = name;
        this.args = args;
    }

    /* Second constructor (no args) */
    public Funcall(Identifier name) {
        this.name = name;
    }

    public Type getType(SymbolTable table) {
        /*
         * Check function return types, etc
         */
        Object def = table.getEntry(name);
        if (!(def instanceof FuncDef)) {
            throwErr("funcall: identifier '" + name.getID()
                    + "' invalid, either nonexistent or not a function", name.getID());
        }
        FuncDef define = (FuncDef) def;
        return define.retType;

    }

    /*
     * Create the code for the function call.
     * 
     * Remember, the expressions have no side effects, which means the call
     * itself has to be moved back to its own statement and assignment, because
     * the function might change some variables up.
     */
    public String code(SymbolTable table) {
        InsertionPoint insert = table.getIP();
        
        Object def = table.getEntry(name);
        if (!(def instanceof FuncDef)) {
            throwErr("funcall: identifier '" + name.getID()
                    + "' invalid, either nonexistent or not a function", name.getID());
        }
        FuncDef define = (FuncDef) def;        
        Type retType = define.retType;
        Identifier realname = define.name;
        
        String begin = (realname.getID() + "(");
        Identifier tempID = table.newID();

        if (args != null) { // print out all the args
            int i;
            for (i = 0; i < args.size(); i++) {
                if (i > 0)
                    begin += (","); // comma separated
                begin += args.get(i).code(table); // call gen() of each
                // argument
            }
        }
        begin += (")");
        
        if (retType.equals(Type.nothing)) {
            // only used in expression statements
            insert.insert(table.indent() + begin + ";\n");
            return "";
        }
        insert.insert(table.indent() + retType.getType() + " " + tempID.getID()
                + " = " + begin + ";\n");

        return tempID.getID();
    }

    private ArrayList<Expr> args = null;

    private Identifier name;

}
