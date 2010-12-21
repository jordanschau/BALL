/*
 * COMS W4115 PROGRAMMING LANGUAGES AND TRANSLATORS FALL 2009
 * Team llamamelon - BALL language
 * Authors: Cipta Herwana, Daniel Lasry, Sam Lee, Nathan Miller, Jordan Schau 
 * ParseTreeNode.java
 */

package codegen;

import compiler.SymbolTable;

public abstract class ParseTreeNode {
    
    public void gen(SymbolTable table) {
        System.out.println(this.code(table));
    }
    
    public abstract String code(SymbolTable table);

    public void setLine(int line){
	lineNo = line;
    }

    public RuntimeException throwErr(String err, String tok){
	String prepare = errStr + "\n*" + this.getClass().getName()
		+ "*" + " - " + err + "\nAT LINE: " + lineNo
		+ ", NEAR: '" + tok + "'." + errStr;
	RuntimeException rtx = new RuntimeException(prepare);
	throw rtx;
    }

    public RuntimeException throwErr(String err){
	String prepare = errStr + "\n*" + this.getClass().getName()
		+ "*" + " - " + err + "\nAT LINE: " + lineNo + errStr;
	RuntimeException rtx = new RuntimeException(prepare);
	throw rtx;
    }

    private String errStr = "\n=======================ERROR=======================";

    protected int lineNo = 0;
}
