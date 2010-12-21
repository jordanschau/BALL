/*
 * COMS W4115 PROGRAMMING LANGUAGES AND TRANSLATORS FALL 2009
 * Team llamamelon - BALL language
 * Authors: Cipta Herwana, Daniel Lasry, Sam Lee, Nathan Miller, Jordan Schau 
 * Identifier.java - Lexer token for Identifiers
 */

package lexer;

public class Identifier extends Token {

	// the word passed becomes the ID
    public Identifier(String s) {
        super(Tag.IDENT);
        this.val = s;
    }

    /**
     * Overrides equals method. Two identifiers are equal if the words they
     * contain are equal.
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Identifier)) {
            return false; // not equal
        }
        Identifier otheri = (Identifier) other;
        return (otheri.val.equals(this.val));   
    }
    
    @Override
    public int hashCode() {
        return this.val.hashCode();
    }
    
    public String toString() {
        return "<ident " + val + ">";
    }

    // just return what it should output in Java.
    public String getID() {
        return val;
    }

    private String val;

}
