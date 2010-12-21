/*
 * COMS W4115 PROGRAMMING LANGUAGES AND TRANSLATORS FALL 2009
 * Team llamamelon - BALL language
 * Authors: Cipta Herwana, Daniel Lasry, Sam Lee, Nathan Miller, Jordan Schau 
 * InsertionPoint.java - Defines location for insertion statements
 */
package codegen;

/**
 * Defines a location inside the java code where statements can be inserted.
 * Usually used for inserting new statements _before_ the current point, as
 * in folding a piece of a long expression into more manageable chunks.
 */
public interface InsertionPoint {
    
    public void insert(String code);

}
