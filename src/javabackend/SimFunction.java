/*
 * COMS W4115 PROGRAMMING LANGUAGES AND TRANSLATORS FALL 2009
 * Team llamamelon - BALL language
 * Authors: Cipta Herwana, Daniel Lasry, Sam Lee, Nathan Miller, Jordan Schau 
 * SimFunction.java - the simulation function interface
 */

package javabackend;

/**
 * simfunctions need to be stored for activation later on. This is the interface
 * that defines simfunction objects to be made as anonymous classes by the BALL
 * translated code.
 */
public interface SimFunction {

    /**
     * Like what the language reference manual states:<br>
     * ===============================================<br>
     * team1 and team2 are implicitly passed to the simfunction whenever sim is
     * called. These are implicit keywords.
     * 
     * @return the "winning" team, really whatever the user wanted it to return
     */
    public TeamObj doSim(TeamObj team1, TeamObj team2);
}
