/*
 * COMS W4115 PROGRAMMING LANGUAGES AND TRANSLATORS FALL 2009
 * Team llamamelon - BALL language
 * Authors: Cipta Herwana, Daniel Lasry, Sam Lee, Nathan Miller, Jordan Schau 
 * MatchExpr.java - nodes representing 'from' and 'any' expressions
 */

package codegen;

import lexer.Identifier;
import lexer.Type;

import compiler.SymbolTable;

public class MatchExpr extends Expr {
    
    public MatchExpr(Expr arg, Expr list) {
        this.arg = arg;
        this.list = list;
    }
    
    public MatchExpr(Expr arg) {
        this.arg = null;
        this.list = arg;
    }

    @Override
    public Type getType(SymbolTable table) {
        Type listType = list.getType(table);
        if (!(listType instanceof ListType))
            throwErr("match: right hand must be a list", list.code(table));
        return ((ListType)listType).contents;
    }

    /**
     * In the spec, the order in which from operands are defined is implementation
     * dependent, here it's list first, then the matcher.
     * 
     * The argument is evaluated only once and it is matched against every
     * element, stopping at the first match and returning that.
     * 
     * If none of the elements match, from returns a "null" or 0, depending on
     * data type.
     */
    @Override
    public String code(SymbolTable table) {
        
        InsertionPoint insert = table.getIP();
        
        Type listType = list.getType(table);
        if (!(listType instanceof ListType))
            throwErr("match: right hand must be a list", list.code(table));
        Type contents = ((ListType)listType).contents;
        
        String listcode = list.code(table);
        
        // 'any' expr
        if (arg == null) {
            return listcode + ".get(Tools.randomInt(0, " + listcode + ".size()))";
        }
        
        String argcode = arg.code(table); // evaluated only once
        
        /*
         * <retType> <rettok> = <0/null>
         * for (<retType> <eachtok> : <list>) {
         *      if (<eachtok>.match(<arg>) { // or <eachtok> == <arg> for nums
         *          <rettok> = <eachtok>;
         *          break;
         *      }
         * }
         * ----- in the expr -----
         * <rettok>
         */
        Identifier rettok = table.newID();
        Identifier eachtok = table.newID();
        // put in table so it doesn't get generated again when it's still not safe
        table.putEntry(rettok, this);
        table.putEntry(eachtok, this);
        
        String head = table.indent() + contents.getType() + " " + rettok.getID() + " = " +
            (contents.equals(Type.number) ? "((float) 0)" : "null") + ";\n";
        head += table.indent() + "for (" + contents.getType() + " " + eachtok.getID() +
            " : " + listcode + ") {\n";
        insert.insert(head);
        
        String inloop = table.indent() + "\tif (";
        if (contents.equals(Type.number)) {
            inloop += eachtok.getID() + " == " + argcode;
        } else if (contents.equals(Type.string)) {
            inloop += eachtok.getID() + ".equals(" + argcode + ")";
        } else {
            inloop += eachtok.getID() + ".match(" + argcode + ")";
        } // if (blah.match(blah))
        inloop += ") {\n";
        insert.insert(inloop);
        
        inloop = table.indent() + "\t\t" + rettok.getID() + " = " + eachtok.getID() + ";\n";
        inloop += table.indent() + "\t\tbreak;\n";
        inloop += table.indent() + "\t}\n";
        inloop += table.indent() + "}\n";
        insert.insert(inloop);
        
        return rettok.getID();
    }

    private Expr arg;
    private Expr list;

}
