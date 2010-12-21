/*
 * COMS W4115 PROGRAMMING LANGUAGES AND TRANSLATORS FALL 2009
 * Team llamamelon - BALL language
 * Authors: Cipta Herwana, Daniel Lasry, Sam Lee, Nathan Miller, Jordan Schau 
 * BuiltinAttributeDef.java - Represents attribute in the symbol table
 */
package codegen;

import compiler.SymbolTable;

import lexer.Identifier;
import lexer.Type;

public class BuiltinAttributeDef extends StatDef {

    public BuiltinAttributeDef(Identifier identifier, Type type) {
        super(identifier, null);
        if (!(type.equals(Type.string))) {
            throw new IllegalArgumentException("error: builtin attributes must " 
                    + "be of type string");
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
