/*
 * COMS W4115 PROGRAMMING LANGUAGES AND TRANSLATORS FALL 2009
 * Team llamamelon - BALL language
 * Authors: Cipta Herwana, Daniel Lasry, Sam Lee, Nathan Miller, Jordan Schau 
 * StatDef.java - Handles Stat Definitions
 */
package codegen;

import lexer.Identifier;
import lexer.Type;
import compiler.SymbolTable;

public class StatDef extends Declaration {

    public StatDef(Identifier identifier, StatExpr expr) {
        super(null, null);
        this.name = identifier;
        this.expr = expr;
    }
    
    public Type getType() {
        if (ownType == null) {
            throwErr("trying to get type before initialization", name.getID());
        }
        return this.ownType;
    }

    private String initCode(SymbolTable table) {
        // stats can be overloaded (I suppose so, since variables can, and
        // stats are on the same level as variables, because of body_statement
        if (!table.available(this.name)) {
            throwErr("statdef: error: name " + this.name + " in use for " + 
                    table.getEntry(this.name), this.name.getID());
        }
        if (isInside(table))
            throwErr("statdef: error: already in a stat def", name.getID());
        
        /*
         * markTable is a symbol table that has .in_stat_decl stored in it. What
         * this means is that the current program view is inside a symbol table.
         * When an expression (atomic expression, that is) encounters a stat
         * identifier, it checks whether it's an expression for a stat
         * declaration.
         * 
         * However, this is not used for stat calls, which are a whole different
         * matter and called differently.
         */
        SymbolTable markTable = new SymbolTable(false, table);
        markTable.putEntry(argname, table.newID());

        String exprCode = expr.code(markTable);
        
        /*
         * This is how the current stat declaration deduces its own type. Expr
         * code would have checked for consistency of the type using the two
         * hidden names .in_stat_decl and .stat_decl_type.
         * 
         * If a stat uses builtin stats consistently this shouldn't happen.
         * Builtin stats need to be inserted in the symbol table at the earliest
         * possible time, probably in the Program() node.
         */
        if (!markTable.hasEntry(type)) {
            throwErr("statdef: error: stat type cannot be "
                    + "resolved using builtins.", name.getID());
        } else if (!markTable.hasEntry(argname)) {
            throwErr("statdef: error: stat argname cannot be "
                    + "resolved.", name.getID());
        }
        
        // PlayerStat or TeamStat
        Type statType = (Type)markTable.getEntry(type);
        
        // PlayerObject or TeamObject
        Type argType = null;
        if (statType.equals(Type.playerStat)) argType = Type.player;
        else if (statType.equals(Type.teamStat)) argType = Type.team;
        else throwErr("statdef: unexpected statType " + statType, name.getID());
        
        // what name to call the object that will be passed to the expression
        Identifier argName = (Identifier)markTable.getEntry(argname);
        
        this.ownType = statType;
        table.putEntry(this.name, this);
        
        /*
         * <Team|Player>Stat <name> = new <Team|Player>Stat {
         *     public float get(<TeamObj|PlayerObj> <argname>) {
         *         return <expr>;
         *     }
         * };
         */
        return this.name.getID() + " = new " + statType.getType() + "() {\n"
            + table.indent() + "\t" + "public float get(" + argType.getType() + " " + argName.getID() + ") {\n"
            + table.indent() + "\t\treturn " + exprCode + ";\n"
            + table.indent() + "\t}\n" + table.indent() + "};";
        
    }

    @Override
    public String code(SymbolTable table) {
        String init = initCode(table);
        return  "final " + getType().getType() + " " + init;
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
        System.out.println(table.indent() + initCode(table));
    }
    
    /**
     * Print to stdout the declaration code, intended to be used by Program to
     * output global variable declarations in class level, not main()
     * @param table
     */
    public void genGlobalDecl(SymbolTable table) {
        System.out.println(table.indent() + "private static " + getType().getType() + " " + this.name.getID() + ";");
    }
    
    /**
     * Checks if the current symbol table says that we're in the middle of some
     * kind of stat declaration.
     * @param table
     * @return true/false.
     */
    public static boolean isInside(SymbolTable table) {
        return table.hasEntry(argname);
    }

    /**
     * If the table does NOT contain a .stat_decl_type, assign myDecl's type.
     * If the table DOES contain a .stat_decl-type, check if it and myDecl's
     * type match.
     * @param table symbol table
     * @param another stat definition whose type needs to be checked. 
     * @return
     */
    public static boolean consistentType(SymbolTable table, StatDef another) {
        if (table.hasEntry(type)) {
            // make sure to use getType, since this might be a builtin.
            Type tableType = (Type)table.getEntry(type);
            if (!tableType.equals(another.getType())) {
                throw new RuntimeException("stat type mismatch");
            }
            return true; // all OK
        } else { // first guy, just add
            table.putEntry(type, another.getType());
            return true;
        }
    }
    
    /**
     * Returns the temporary argument name used in the declaration.
     * @param table
     * @return
     */
    public static Identifier getArgName(SymbolTable table) {
        return (Identifier) table.getEntry(argname);
    }
    
    
    public Identifier getName() {
    	return name;
    }

    public final Identifier name;

    private final StatExpr expr;
    
    private Type ownType = null;
    
    private final static Identifier type = new Identifier(".stat_decl_type");
    private final static Identifier argname = new Identifier(".stat_arg_name");

}
