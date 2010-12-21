/*
 * COMS W4115 PROGRAMMING LANGUAGES AND TRANSLATORS FALL 2009
 * Team llamamelon - BALL language
 * Authors: Cipta Herwana, Daniel Lasry, Sam Lee, Nathan Miller, Jordan Schau 
 * Tools.java - Miscellaneous functions predefined in BALL
 */

package javabackend;

import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.lang.Math;

public class Tools {
    
    public static int randomInt(int min, int max) {
        if (min > max) return randomInt(max, min);
        return myRand.nextInt(max-min) + min;
    }
    
    // rand(number x, number y)
    public static float randomFloat(float min, float max) {
    	if (min > max) return randomFloat(max, min);
    	return myRand.nextFloat() * (max - min) + min;
    }
    
    public static String fixFloat(float input) {
    	String str ="";
    	if (input > 1.0 && input != (float)Math.floor(input)) {
    		str = String.format("%.2f",input);
    	}
    	
    	else if (input == (float)Math.floor(input))
    	{ str = String.format("%.0f",input); }
    	
    	else if (input < 1.0){ 
    		str = String.format("%.3f",input); 
    	}
    	return str;
    }
    
    // max(number x, number y)
    public static float max(float low, float high) {
    	return Math.max(low,high);
    }
    
    // min(number x, number y)
    public static float min(float low, float high) {
    	return Math.min(low,high);
    }
    
    // topTeams(number x, list of team y, teamStat z)
    public static BallList<TeamObj> topTeams(float num, BallList<TeamObj> list, TeamStat stat) {
    	BallList<TeamObj> copy = new BallList<TeamObj>();
    	for (TeamObj ent : list) copy.add(ent);
    	
    	final TeamStat st = (TeamStat)stat;
    	Collections.sort(copy, new Comparator<TeamObj>() {
    		public int compare(TeamObj t0, TeamObj t1) {
    			float t0stat = st.get(t0);
    			float t1stat = st.get(t1);
    			// sort bigger stats in front
    			if (t0stat < t1stat) return 1;
    			if (t0stat == t1stat) return 0;
    			return -1;
    		}
    	});
    	return (BallList<TeamObj>) new BallList<TeamObj>(copy.subList(0, (int)num));
    }

    public static boolean isValid(Object obj) { return obj != null; }

    public static boolean isValid(float num) { return num != Float.NaN; }

    // topTeams(number x, list of player y, playerStat z)
    public static BallList<PlayerObj> topPlayers(float num, BallList<PlayerObj> list, PlayerStat stat) {
    	BallList<PlayerObj> copy = new BallList<PlayerObj>();
    	for (PlayerObj ent : list) copy.add(ent);
    	
    	final PlayerStat st = (PlayerStat)stat;
    	Collections.sort(copy, new Comparator<PlayerObj>() {
    		public int compare(PlayerObj p0, PlayerObj p1) {
    			float p0stat = st.get(p0);
    			float p1stat = st.get(p1);
    			// sort smaller stats in front
    			if (p0stat < p1stat) return 1;
    			if (p0stat == p1stat) return 0;
    			return -1;
    		}
    	});
    			
    	return (BallList<PlayerObj>) new BallList<PlayerObj>(copy.subList(0, (int)num));

    }
    
    // bottomTeams(number x, list of team y, teamStat z) 
    public static BallList<TeamObj> bottomTeams(float num, BallList<TeamObj> list, TeamStat stat) {
    	BallList<TeamObj> copy = new BallList<TeamObj>();
    	for (TeamObj ent : list) copy.add(ent);
    	
    	final TeamStat st = (TeamStat)stat;
    	Collections.sort(copy, new Comparator<TeamObj>() {
    		public int compare(TeamObj t0, TeamObj t1) {
    			float t0stat = st.get(t0);
    			float t1stat = st.get(t1);
    			// sort bigger stats in front
    			if (t0stat < t1stat) return -1;
    			if (t0stat == t1stat) return 0;
    			return 1;
    		}
    	});
    	return (BallList<TeamObj>) new BallList<TeamObj>(copy.subList(0, (int)num));
    }

    // bottomTeams(number x, list of player y, playerStat z)
    public static BallList<PlayerObj> bottomPlayers(float num, BallList<PlayerObj> list, PlayerStat stat) {
    	BallList<PlayerObj> copy = new BallList<PlayerObj>();
    	for (PlayerObj ent : list) copy.add(ent);
    	
    	final PlayerStat st = (PlayerStat)stat;
    	Collections.sort(copy, new Comparator<PlayerObj>() {
    		public int compare(PlayerObj p0, PlayerObj p1) {
    			float p0stat = st.get(p0);
    			float p1stat = st.get(p1);
    			// sort smaller stats in front
    			if (p0stat < p1stat) return -1;
    			if (p0stat == p1stat) return 0;
    			return 1;
    		}
    	});
    			
    	return (BallList<PlayerObj>) new BallList<PlayerObj>(copy.subList(0, (int)num));

    }
    
    private static final Random myRand = new Random(new java.util.Date().getTime());

}
