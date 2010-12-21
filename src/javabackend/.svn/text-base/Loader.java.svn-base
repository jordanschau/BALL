/*
 * COMS W4115 PROGRAMMING LANGUAGES AND TRANSLATORS FALL 2009
 * Team llamamelon - BALL language
 * Authors: Cipta Herwana, Daniel Lasry, Sam Lee, Nathan Miller, Jordan Schau 
 * Loader.java - CSV File Loader
 */


package javabackend;

import java.util.*;
import java.io.*;

public class Loader {

    public static void main (String args[]){
        System.out.println(load(args[0]));
    }
	
    /*
     * ==============LOAD============= 
     * read the csv file line by line. 
     * Return a fully populated team 
     * ===============================
     */
	public static TeamObj load(String fileName){
		
		// Set initial variables
		String teamName = "Unnamed Team";
		int teamWins = 0;
		int teamLosses = 0;
		BallList<PlayerObj> batters = new BallList<PlayerObj>();		//will contain extracted batters
		BallList<PlayerObj> pitchers = new BallList<PlayerObj>();		//will contain extracted pitchers
		
		String line; //hold the current line
		try {
			BufferedReader in = new BufferedReader(new FileReader("../teams/" + fileName));
			try{
				
				if((line = percolateThrough(in)) != null) {	//if the file is not empty
					
					//Read TEAM NAME
					if (line.lastIndexOf("Team Name:") > -1) {	//if Team Name: is present
						teamName = line.substring(line.indexOf(":")+1).trim();
						line = percolateThrough(in);
					}
					else {
						System.out.println("Warning: Team Name not found, using default.");
					}
					
					//Read TEAM STATS
					if (line!=null && line.lastIndexOf("Header:") > -1) {
						line = percolateThrough(in);
					}
					else {
						System.err.println("Warning: No Headers for Team Stats: check CSV file structure.");
					}
					
					try {	//get wins/losses
						StringTokenizer strtok = new StringTokenizer(line, ",");
						teamWins = Integer.parseInt(strtok.nextToken().trim());
						teamLosses = Integer.parseInt(strtok.nextToken().trim());
						line = percolateThrough(in);
					}
					catch (NumberFormatException e) {	//if the wins/losses are not numbers
						teamWins = 0;
						teamLosses = 0;
						System.err.println("Warning: Team wins/losses not present or misplaced: Using default zero.");
					}
					catch (NoSuchElementException e) {	//if only wins are provided
						System.err.println("Warning: Team losses not present or misplaced: Using default zero.");
					}
					catch (NullPointerException e) {	//if the line is null
						System.err.println("Warning: File ends before Team stats!");
					}
					
					//Read first set of PLAYERS
					if (line!=null && line.lastIndexOf("Type:") > -1) {
						
						//This if/else statement handles CSV files with batters first or pitchers first
						if (line.lastIndexOf("Batter") > -1) {	//batters first
							line = percolateThrough(in);
							batters = readBatters(in, line);
						}
						else {									//pitchers first
							line = percolateThrough(in);
							pitchers = readPitchers(in, line);
						}
					}
					else if(line!=null) {
						batters = readBatters(in, line);		//Not specified, do default
						System.err.println("Warning: Type of first set of players not specified: assuming batters.");
					}
					
					//Read second set of PLAYERS
					line = percolateThrough(in);
					if (line!=null && line.lastIndexOf("Type:") > -1) {
						
						//Same as above, handles CSV in any order
						if (line.lastIndexOf("Batter") > -1){
							line = percolateThrough(in);
							batters = readBatters(in, line);
						}
						else {
							line = percolateThrough(in);
							pitchers = readPitchers(in, line);
						}
					}
					else if(line!=null) {
						pitchers = readPitchers(in, line);		//Type not specified, do default
						System.out.println("Warning: Type of second set of players not specified: assuming pitchers.");
					}
					
				}
				else {
					System.err.println(fileName + " is empty, please check.");
				}
			}
			catch (IOException e) {
				System.err.println("Failed to read from file");
			}
		}
		catch (FileNotFoundException e) {
			System.err.println("Failed to read file: " + fileName);
		}
		
		//Create team to be returned
		TeamObj theTeam = new TeamObj(teamName, teamWins, teamLosses);
		theTeam.setBatters(batters);	//add players
		theTeam.setPitchers(pitchers);
		return theTeam;
	}
	
	//============percolateThrough===========================
	//Skip through empty lines and return the first non-empty
	//line found. Return null if end of file is reached.
	//=======================================================
	public static String percolateThrough(BufferedReader in) throws IOException {
		String line;
		while((line=in.readLine()) != null) {
			if(line.length() > 0){	//return the first line of length > 0
				return line;
			}
		}
		return null;
	}
	
	//============readBatters====================================
	//read a new line until end of this goup of players.
	//for each line create a player and add to the returned list.
	//===========================================================
	public static BallList<PlayerObj> readBatters(BufferedReader in, String aLine) throws IOException {
		
		in.mark(5000);
		String line = aLine;
		if (line!=null && line.lastIndexOf("Header:") > -1)	//skip the header
			line = percolateThrough(in);
		else	//header check
			System.err.println("Warning: Headers not provided for Batters. Unclean CSV.");
	
		BallList<PlayerObj> batters = new BallList<PlayerObj>();
		String batterName = "Unnamed Batter";
		int AB = 0;
		int R = 0;
		int H = 0;
		int DBL = 0;		//initializing everything.
		int TPL = 0;
		int HR = 0;
		int BB = 0;
		
		while(line != null) {	//for every new line...
								//break if you find a header or a type definition:
			if (line.lastIndexOf("Type:") > -1 || line.lastIndexOf("Header:") > -1) {
				in.reset();
				break;
			}
			in.mark(5000);
			
			try {
				batterName = "Unnamed Batter";
				AB = 0;
				R = 0;
				H = 0;		//this is a static method so values must be reset
				DBL = 0;
				TPL = 0;
				HR = 0;
				BB = 0;
				
				//StrTok the comma separated player stats
				StringTokenizer strtok = new StringTokenizer(line, ",");
				batterName = strtok.nextToken().trim();
				AB = Integer.parseInt(strtok.nextToken().trim());
				R = Integer.parseInt(strtok.nextToken().trim());
				H = Integer.parseInt(strtok.nextToken().trim());
				DBL = Integer.parseInt(strtok.nextToken().trim());
				TPL = Integer.parseInt(strtok.nextToken().trim());
				HR = Integer.parseInt(strtok.nextToken().trim());
				BB = Integer.parseInt(strtok.nextToken().trim());
			}
			catch (NoSuchElementException e) {	//in case there are less stats than required
				System.err.println("Warning: The batter " + batterName + " Has (a) missing stat(s), using 0 as defaults.");
			}
			catch (NumberFormatException e) {	//yep.
				System.err.println("Warning: The batter " + batterName + " Has (a) invalid stat(s), using 0 as defaults.");
			}
			
			//Add this new player
			batters.add(new PlayerObj(batterName, PlayerObj.BATTER, AB, R, H, DBL, TPL, HR, BB));
			line = percolateThrough(in);
		}
		return batters;
	}
	
	//============readPitchers====================================
	//read a new line until end of this goup of players.
	//for each line create a player and add to the returned list.
	//===========================================================
	public static BallList<PlayerObj> readPitchers(BufferedReader in, String aLine) throws IOException {
		
		in.mark(5000);
		String line = aLine;
		if (line!=null && line.lastIndexOf("Header:") > -1)	//skip the header
			line = percolateThrough(in);
		else		//header check
			System.err.println("Warning: Headers not provided for Pitchers. Unclean CSV.");
		
		BallList<PlayerObj> pitchers = new BallList<PlayerObj>();
		String pitcherName = "Unnamed Pitcher";
		double IP = 0;
		int H = 0;
		int ER = 0;		//initializing everything.
		int HR = 0;
		int BB = 0;
		int K = 0;
		int BF = 0;
		while(line != null) {
			if (line.lastIndexOf("Type:") > -1 || line.lastIndexOf("Header:") > -1) {
				in.reset();
				break;
			}
			in.mark(5000);
			try {
				pitcherName = "Unnamed Pitcher";
				IP = 0;
				H = 0;
				ER = 0;
				HR = 0;
				BB = 0;
				K = 0;
				BF = 0;
				
				//StrTok the comma separated player stats
				StringTokenizer strtok = new StringTokenizer(line, ",");
				pitcherName = strtok.nextToken().trim();
				IP = Double.parseDouble(strtok.nextToken().trim());
				H = Integer.parseInt(strtok.nextToken().trim());
				ER = Integer.parseInt(strtok.nextToken().trim());
				HR = Integer.parseInt(strtok.nextToken().trim());
				BB = Integer.parseInt(strtok.nextToken().trim());
				K = Integer.parseInt(strtok.nextToken().trim());
				BF = Integer.parseInt(strtok.nextToken().trim());
			}
			catch (NoSuchElementException e) {	//in case there are less stats than required
				System.err.println("Warning: The pitcher " + pitcherName + " Has (a) missing stat(s), using 0 as defaults.");
			}
			catch (NumberFormatException e) {	//yep.
				System.err.println("Warning: The pitcher " + pitcherName + " Has (a) invalid stat(s), using 0 as defaults.");
			}
			
			//Add this new player
			pitchers.add(new PlayerObj(pitcherName, PlayerObj.PITCHER, IP, H, ER, HR, BB, K, BF));
			line = percolateThrough(in);
		}
		return pitchers;
	}

}
