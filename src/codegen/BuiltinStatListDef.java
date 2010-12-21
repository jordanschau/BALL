/*
 * COMS W4115 PROGRAMMING LANGUAGES AND TRANSLATORS FALL 2009
 * Team llamamelon - BALL language
 * Authors: Cipta Herwana, Daniel Lasry, Sam Lee, Nathan Miller, Jordan Schau 
 * BuiltinStatListDef.java - Represents StatList in the symbol table
 */
package codegen;

import compiler.SymbolTable;

import lexer.Identifier;
import lexer.Type;

public class BuiltinStatListDef extends StatDef {

    public BuiltinStatListDef(Identifier identifier, Type type) {
        super(identifier, null);
        if (!(type.equals(Type.list))) {
            throw new IllegalArgumentException("error: builtin stat-lists must " 
                    + "be of type list");
        }
        this.type = type;
    }
    
    @Override
    public Type getType() {
        return type;
    }

    @Override
    public String code(SymbolTable table) {
        // can actually be silenced, just let us know
        return "/* ERROR: trying to get definition of a builtin stat */";
    }
    
    private final Type type;

}
