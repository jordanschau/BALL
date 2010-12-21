/*
 * COMS W4115 PROGRAMMING LANGUAGES AND TRANSLATORS FALL 2009
 * Team llamamelon - BALL language
 * Authors: Cipta Herwana, Daniel Lasry, Sam Lee, Nathan Miller, Jordan Schau 
 * PlayerStat.java - this is a template of what a team stat looks like
 */
package javabackend;

/**
 * 
 * In this BALL implementation, stats are not added as fields inside a TeamObj
 * or PlayerObj. Instead, they are small objects that evaluate stats given
 * a player. This allows the compiler to easily declare new stats by making
 * new objects.
 */
public interface PlayerStat {
    
    /**
     * This is the core of the stat, a method that somehow retreives the stat
     * from a team. Primitive stats directly access the team's object attributes
     * but user-defined stats usually has references to other stats inside them.
     * 
     * @param team the team whose stats need to be extracted
     * @return the value of the stat. Always a number.
     */
    public float get(PlayerObj team);
    
}
