/*
 * COMS W4115 PROGRAMMING LANGUAGES AND TRANSLATORS FALL 2009
 * Team llamamelon - BALL language
 * Authors: Cipta Herwana, Daniel Lasry, Sam Lee, Nathan Miller, Jordan Schau 
 * FilterExpr.java - Code Generator for filter Expressions
 */
package codegen;

import lexer.Identifier;
import lexer.Type;

import compiler.SymbolTable;

/**
 * FILTERING A LIST
 * ================
 * 1. left expr must be a list
 * 2. right expr must be a boolean
 * 3. right expr can have special token '$$' which means the item being checked
 *
 */
public class FilterExpr extends Expr {
    
    public FilterExpr(Expr list, Expr filter) {
        this.list = list;
        this.filter = filter;
    }

    @Override
    public Type getType(SymbolTable table) {
        Type lt = list.getType(table);
        
        if (!(lt instanceof ListType)) {
            throwErr("filter: left hand expression must evaluate to a list.", list.code(table) + " where (" + filter.code(table) + ")");
        }
        ListType ltype = (ListType)lt;
        return ltype;
    }
    
    public static class SelfKeyword extends AtomicExpr {
        public Type getType(SymbolTable table) {
            if (table.hasEntry(inTyp))
                return (Type)table.getEntry(inTyp);
            throw throwErr("item: keyword placed not on a 'where' search", "item");
        }

        /**
         * @return Java code for this expression.
         */
        public String code(SymbolTable table) {
            if (table.hasEntry(inRef))
                return ((Identifier)table.getEntry(inRef)).getID();
            throw throwErr("item: keyword placed not on a 'where' search", "item");
        }
    }

    @Override
    /**
     * 1. the expression returning the list is evaluated first
     * 2. checked left to right, so order is preserved when filtering (guaranteed)
     */
    public String code(SymbolTable table) {
        InsertionPoint insert = table.getIP();
        
        String leftcode = list.code(table);
        getType(table);
        Type lt = list.getType(table);
        
        if (!(lt instanceof ListType)) {
            throwErr("filter: left hand expression must evaluate to a list.", list.code(table) + " where (" + filter.code(table) + ")");
        }
        ListType ltype = (ListType)getType(table);
        Type cont = ltype.contents;
        
        Identifier oldlist = table.newID();
        table.putEntry(oldlist, oldlist);
        Identifier newlist = table.newID();
        table.putEntry(newlist, newlist);
        Identifier each = table.newID();
        table.putEntry(each, each);
        /*
         * BallList<...> newlist = new BallList();
         * for (T each : <listcode>) {
         *     if (< where code, with 'self' replaced with 'each'>)
         *         newlist.addEnd(each);
         * }
         * <original expr, with newlist
         */
        
        // note that this makes using '++' and '--' impossible.
        
        //insert.insert(table.indent() + lt.getType() + " " + oldlist.getID() + " = " + leftcode + ";\n");
        insert.insert(table.indent() + lt.getType() + " " + newlist.getID() + " = new " + lt.getType() + "();\n");
        insert.insert(table.indent() + "for (" + cont.getType() + " " + each.getID() + " : " + leftcode + ") {\n");
        
        SymbolTable inTable = new SymbolTable(true, table);
        inTable.putEntry(inRef, each);
        inTable.putEntry(inTyp, cont);
        inTable.setInsertPt(insert);
        insert.insert(
                inTable.indent() + "if (" + filter.code(inTable) + ")\n" +
                    inTable.indent() + "\t" + newlist.getID() + ".add(" + each.getID() + ");\n");
        
        insert.insert(table.indent() + "}\n");
        
        return newlist.getID();
    }
        
    private static final Identifier inRef = new Identifier("item");
    private static final Identifier inTyp = new Identifier(".item_type");
    
    private Expr list, filter;

}
