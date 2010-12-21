/*
 * COMS W4115 PROGRAMMING LANGUAGES AND TRANSLATORS FALL 2009
 * Team llamamelon - BALL language
 * Authors: Cipta Herwana, Daniel Lasry, Sam Lee, Nathan Miller, Jordan Schau 
 * Token.java - Tokens passed to the symbol table
 */
package lexer;

/**
 * Primitive token type. Subclasses of these tokens will be put in the symbol
 * table. Subclasses of tokens are keywords (includes operators?) and types of
 * constants.
 */
public class Token {

    /**
     * Constructor sets the type of the token
     * @param tag
     */
    public Token(Tag t) {
        this.tag = t;
    }

    public enum Tag {
        PRINT, 
        IDENT, 
        STRINGCONST, 
        TYPE, 
        NUMERICCONST, 
        FUNCTION, 
        SIMFUNCTION, 
        RETURNS, 
        END, 
        RETURN, 
        STAT, 
        ACTIVATE, 
        WHERE, SELF,
        DO, TIMES, FOREACH, IN, STOPDO,
        IF, THEN, ELSE
    }

    public final Tag tag;

    public String toString() {
        return ("<tag " + tag.name() + ">");
    }

}
