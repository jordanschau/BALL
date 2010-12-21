/*
 * COMS W4115 PROGRAMMING LANGUAGES AND TRANSLATORS FALL 2009
 * Team llamamelon - BALL language
 * Authors: Cipta Herwana, Daniel Lasry, Sam Lee, Nathan Miller, Jordan Schau 
 * BallList.java - Runtime BALL typed lists
 */
package javabackend;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Changes from regular ArrayLists:
 * 1. Implements match() for 'from' exprs
 * 2. Can construct lists from Object[]s (though unsafe, all types are checked
 *    by the compiler anyway)
 * 3. prints like this: [ x_0, x_1, x_2, ]
 */
public class BallList<T> extends ArrayList<T> implements BallDataType {

    /**
     * Makes a list out of an array of references (in BALL these would contain
     * references to objects that are expression results)
     * 
     * unchecked, BALL compiler does it so there should be very few errors.
     */
    @SuppressWarnings("unchecked")
    public BallList(Object[] create) {
        super((List<T>)Arrays.asList((T[])create));
    }
    
    /** empty constructor that just initializes the arraylist code */
    public BallList() {
        super();
    }
    
    public BallList(Collection<T> name) {
		super(name);
	}

	/** prints like a python string */
    public String toString() {
        String begin = "[ ";
        boolean first = true;
        for (Object in : this) {
            if (!first) begin +=  ", ";
            else first = false;

            if (in instanceof String) {
                begin += "\"";
            }
            begin += in.toString();
            if (in instanceof String) {
                begin += "\"";
            }
        }
        begin += " ]";
        return begin;
    }
    
    /**
     * Appends two lists. Returns a new list, the old ones are unchanged.
     * Can only append lists of the same type.
     */
    @SuppressWarnings("unchecked")
    public BallList<T> append(BallList<T> right) {
        BallList result = new BallList<T>();
        
        result.addAll(this);
        result.addAll(right);
        return result;
    }
    
    /** as the specification says, reference equality */
    public boolean match(Object other) {
        return this == other; // reference equality
    }
    
    private static final long serialVersionUID = 1L;

}
