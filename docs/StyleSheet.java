/*
 * COMS W4115 PROGRAMMING LANGUAGES AND TRANSLATORS FALL 2009
 * Team llamamelon - BALL language
 * StyleSheet.java - style sheet for Java classes
 */

package style;

// package declaration comes after header, separated by one line
/*
 * header contains course name, team name, language name, file name, and a short
 * description of what the file is.
 */

// imports come right after, separated by at least 1 empty line
import java.util.Arrays;

import java.io.*;
// imports can have empty lines separating them, especially when importing
// different trees

import javabackend.SimFunction;
import javabackend.TeamObj;

// single line comments can use // or /* (for more important comments)
// two-line comments written like this are also permitted. For 3 or more, use /*

/*
 * Very important single-line comments are made like this
 */

/*
 * Multi-line comments also use this format. The maximum width for each line in
 * a multi-line comment is 80 characters from the start of the line (not the
 * start of the comment, which is after the space after a '*'.
 * 
 * 1.  this is how to write lists with numbers.
 * 2.  When a list goes over a line, the next line starts under the start of the
 *     first line of the list.
 *     ...
 * 10. If the numbering goes over 10, entries 1 to 9 need to have 1 more space.
 *     This way all list text starts at the same place. Having a list of more 
 *     than 10 members is discouraged.
 */

/**
 * Comments before a class are highly recommended, even if just one
 * sentence. Give a brief description of what role it has in the compiler. 
 * 
 * Class names are capitalized on each word. 
 * - playerObj - WRONG 
 * - PlayerObj - RIGHT
 */
public class StyleSheet extends StyleSuper implements StyleInterface {

    // comments follow the indentation of the non-commented statements
    
    // public constructor comes first.
    /**
     * Sets use to default value. 
     */
    public StyleSheet() {
        this.use = 1001;
    }

    /*
     * Comments describing a method (highly recommended for public methods, even
     * for just one line). Some details:
     *
     * 1. comments for private methods are recommended.
     * 2. Javadoc style is optional. Can both be /* or /** or even //
     * 3. comments for methods that override a superclass method / implement an
     *    interface method are highly recommended it's not already documented in
     *    the superclass / interface. Otherwise, it's optional.
     * 5. Methods that override builtin classes' methods like toString() or
     *    hashCode() don't need to be commented.
     * 
     * Method names
     * 1. first letter of name NOT capitalized. However, words after that are
     *    captialized.
     * 2. Order of methods:
     *    a. public
     *    b. protected
     *    c. <no scope>
     *    d. private
     *    e. public static
     *    ...
     *    h. private static
     *    the names themselves can go in any order.
     * 
     * 1. Annotations like @Override are OPTIONAL. They go after the comment.
     * 2. Public methods cannot have underscores.
     */
    public int sampleFun(int arg1, int arg2, Object arg3) {
        // no space here ^ between function name and opening parentheses.
        // single space between arguments, and btwn close paren and open brace

        int p = 0, q; // space after each variable name
        q = 10;
        
        // note the spaces on for loops
        for (int i = 0; i < 10, i++) {
            // inside of for loops are also indented
            p++;
            q += 3;
            if (q >= 20) // single-line ifs like this are permitted
                System.out.println("bleh");
        }
        
        /*
         * Inner variables don't have to be capitalized properly.
         * 
         * Anonymous classes have a space after parentheses and opening curly
         * brace. The inside is indented.
         */
        SimFunction mysim = new SimFunction() {
            public TeamObj doSim(TeamObj team1, TeamObj team2) {
                return team1;
            }
        };

        return 1037; // parentheses for return exprs aren't necessary
    }
    
    /**
     * Since toString() is already in object, this is technically unnecessary
     */
    @Override
    public String toString() {
        return "style sheet";
    }
    
    /*
     * Order of fields is just like order of methods. "final" goes after
     * non-final but inside the same bracket above. So, "protected final" goes
     * after "public final" or "protected" but before "private".
     * 
     * protected x
     * public static final y
     */
    public int use;
    
    public final String bleh = "bleh";
    
    private float[] arrayExample;
    // variables/fields are named like methods

}
