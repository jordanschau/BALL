/*
 * COMS W4115 PROGRAMMING LANGUAGES AND TRANSLATORS FALL 2009
 * Team llamamelon - BALL language
 * Authors: Cipta Herwana, Daniel Lasry, Sam Lee, Nathan Miller, Jordan Schau 
 * Simulator.java - the sim() wrapper function
 */

package javabackend;

public class Simulator {
	
    /** Main function just for testing. (TODO: Delete)*/
    public static void main(String args[]) {
        theSimFunction = new SimFunction() {
            public TeamObj doSim(TeamObj team1, TeamObj team2){
                return team1;
            }
        };

        System.out.println(sim(Loader.load("dodgers.team"), Loader.load("astros.team"), 6));
    }

    /* sim() function
     * Takes two teams and a number, returns the winner.*/
    public static TeamObj sim(TeamObj team1, TeamObj team2, float number) {
	//Keep track of how many times each team won
	int team1Wins=0;
	int team2Wins=0;
	
	//Ge the number of times to play
	int timesToPlay = (int) number;

	int plays=0;
	/* Play a certain number of times, if the winner cannot be determined, play more*/
	while(team1Wins==team2Wins || plays < timesToPlay){
		//Determine the winner
		TeamObj winner = theSimFunction.doSim(team1, team2);
		if(winner == team1)
		    team1Wins++;
		else if(winner == team2)
		    team2Wins++;

		plays++;
		if (plays > timesToPlay+20){
		    System.err.println("Could not determine a winner after 20 tries, returning team1.");
		    return team1;
		}
		
	}
	
	if(team1Wins>team2Wins)
		return team1;
	else
		return team2;
    }

    public static SimFunction theSimFunction;
}
