/*
 * COMS W4115 PROGRAMMING LANGUAGES AND TRANSLATORS FALL 2009
 * Team llamamelon - BALL language
 * Authors: Cipta Herwana, Daniel Lasry, Sam Lee, Nathan Miller, Jordan Schau 
 * Keyword.java - Lexer token for keywords
 */

package lexer;

public class Keyword extends Token {
    
	// register the keyword
    public Keyword(Tag t, String lex) {
        super(t);
        this.lexeme = lex;
    }

    public final String lexeme;

    /*
     * Premade keywords
     */
    public static final Keyword
        print = new Keyword(Tag.PRINT, "print"),
        activate = new Keyword(Tag.ACTIVATE, "activate"),
        function = new Keyword(Tag.FUNCTION, "function"),
        simfunction = new Keyword(Tag.SIMFUNCTION, "simfunction"),
        stat = new Keyword(Tag.STAT, "stat"),
        end = new Keyword(Tag.END, "end"),
        ret = new Keyword(Tag.RETURN, "return"),
        returns = new Keyword(Tag.RETURNS, "returns"),
        where = new Keyword(Tag.WHERE, "where"),
        self = new Keyword(Tag.SELF, "self"),
    	mydo = new Keyword(Tag.DO, "do"),
    	times = new Keyword(Tag.TIMES, "times"),
    	in = new Keyword(Tag.IN, "in"),
    	ifKwd = new Keyword(Tag.IF, "if"),
    	then = new Keyword(Tag.THEN, "then"),
    	elseKwd = new Keyword(Tag.ELSE, "else"),
    	stopdo = new Keyword(Tag.STOPDO, "stopdo"),
    	foreach = new Keyword(Tag.FOREACH, "foreach");
}
