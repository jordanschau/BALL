/*
 * COMS W4115 PROGRAMMING LANGUAGES AND TRANSLATORS FALL 2009
 * Team llamamelon - BALL language
 * Authors: Cipta Herwana, Daniel Lasry, Sam Lee, Nathan Miller, Jordan Schau 
 * TeamObj.java - Runtime Team Class
 */

package javabackend;

/**
 * Java representation of BALL 'teams'
 */
public class TeamObj implements BallDataType {
    
    /**
     * Name, wins, losses. Used by loader.
     */
    public TeamObj(String Name,int w, int l) {
        this.Name = Name;
        this.w = w;
        this.l = l;
    }
        
    /* reference manual says reference equality */
    public boolean match(Object other) {
        return this == other; // reference equality
    }

    /**
     * Add player to the correct list.
     */
	public void addPlayer(PlayerObj p) {
		if (p.getType() == PlayerObj.BATTER)
			batters.add(p);
		if (p.getType() == PlayerObj.PITCHER)
			pitchers.add(p);
	}
	
    /** Mutator for setting the batter list */
	public void setBatters(BallList<PlayerObj> a) {
		batters = a;
	}
	
    /** Mutator for setting the pitcher list */
	public void setPitchers(BallList<PlayerObj> a) {
		pitchers = a;
	}
    
    /* METHODS IN THIS REGION DOUBLE AS ATTRIBUTES IN THE BALL PROGRAM */
	
    /** Accessor for batters */
	public BallList<PlayerObj> getBatters() {
		return batters;
	}
    
    /** Accessor for pitchers */
	public BallList<PlayerObj> getPitchers() {
		return pitchers;
	}
	
    /** Accessor for all players, both batters and pitchers */
	public BallList<PlayerObj> getPlayers() {
		BallList<PlayerObj> players = new BallList<PlayerObj>();
		players.addAll(batters);
		players.addAll(pitchers);
		return players;
	}
	
    /** Accessor for name of the team */
	public String getName() {
		return Name;
	}
    
    /* METHODS IN THIS REGION ARE THE BASES FOR STATS IN THE BALL PROGRAM */
    
	public int getW() {
		return w;
	}
	
    public int getL() {
		return l;
	}
	
	public String toString() {
		String toReturn = "";
		toReturn += "Team Name: " + Name + "\n" + "Header:W,L\n";
		toReturn += Integer.toString(w) + "," + Integer.toString(l) + "\n\n";
		
		toReturn += "Type:Batter\nHeader:Name,AB,R,H,2B,3B,HR,BB\n";
		int i;
		for(i=0; i<batters.size(); i++){
			toReturn += batters.get(i) + "\n";
		}
		
		toReturn += "\nType:Pitcher\nHeader:Name,IP,H,ER,HR,BB,K,BF\n";
		for(i=0; i<pitchers.size(); i++){
			toReturn += pitchers.get(i) + "\n";
		}
		
		return toReturn;
	}
    
    /* TEAM STATS */
	
	public static final TeamStat W = new TeamStat() {
		public float get(TeamObj team) {
			return team.getW();
		}
	};
	
    public static final TeamStat L = new TeamStat() {
		public float get(TeamObj team) {
			return team.getL();
		}
	};
	
	/* TEAM LISTS */
	
	public static final TeamList PLAYERS = new TeamList() {
		public BallList<PlayerObj> get(TeamObj team) {
			return team.getPlayers();
		}
	};
	
	public static final TeamList PITCHERS = new TeamList() {
		public BallList<PlayerObj> get(TeamObj team) {
			return team.getPitchers();
		}
	};
	
	public static final TeamList BATTERS = new TeamList() {
		public BallList<PlayerObj> get(TeamObj team) {
			return team.getBatters();
		}
	};
	
	/* TEAM ATTRIBUTES */
    public static final TeamAttribute name = new TeamAttribute() {
    	public String get(TeamObj team) {
    		return team.getName();
    	}
    };

	private String Name;
	private int w;
	private int l;
	private BallList<PlayerObj> batters;
	private BallList<PlayerObj> pitchers;
}
