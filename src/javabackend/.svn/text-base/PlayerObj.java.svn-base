/*
 * COMS W4115 PROGRAMMING LANGUAGES AND TRANSLATORS FALL 2009
 * Team llamamelon - BALL language
 * Authors: Cipta Herwana, Daniel Lasry, Sam Lee, Nathan Miller, Jordan Schau 
 * playerObj.java - Players class
 */

package javabackend;

import java.math.*;

public class PlayerObj implements BallDataType {

    /* This is the batter constructor */
    public PlayerObj (String Name, int type, int ab, int r,
            int h, int dbl, int tpl, int hr, int bb) {
        if (type != BATTER) {
            System.err.println("Incorrect Player Type Found");
            return;
        }
        this.Name = Name;
        this.type = type;
        this.ab = ab;
        this.r = r;
        this.h = h;
        this.dbl = dbl;
        this.tpl = tpl;
        this.hr = hr;
        this.bb = bb;
    }

    // This is the pitcher constructor
    public PlayerObj (String Name, int type, double ipp, int h,
            int er, int hr, int bb, int k, int bf) {
        if (type != PITCHER) {
            System.err.println("Incorrect Player Type Found");
            return;
        }
        float ip = (float)ipp;
        float temp = (float)(ipp - Math.floor(ipp));
        this.Name = Name;
        this.type = type;
        if (temp == 0)
            this.ip = ip;
        else {
            this.ip = (float)Math.floor(ipp);
            if (temp == .1f)
                this.ip += (1f/3f);
            else if (temp == .2f)
                this.ip += (2f/3f);
            else {
                System.err.println("Incorrect Innings Pitched Value " + ip);
                return;
            }
        }
        this.h = h;
        this.er = er;
        this.hr = hr;
        this.bb = bb;
        this.k = k;
        this.bf = bf;
    }
    
    //  reference equality, or Name lookup
    public boolean match(Object other) {
        if (other instanceof String) {
            String Name = (String)other;
            return Name.equals(this.Name);
        }
        return this == other; // reference equality
    }

    public int getType() {
        return type;
    }
    
    /*
     * ACCESSOR METHODS FOR STATS
     */

    public int getAb() {
        if (type != BATTER) {
            System.err.println("Incorrect Statistic: AB is only applicable to batters");
            return -1;
        }
        return ab;
    }

    public int getR() {
        if (type != BATTER) {
            System.err.println("Incorrect Statistic: R is only applicable to batters");
            return -1;
        }
        return r;
    }

    public int getH() {
        return h;
    }

    public int getDbl() {
        if (type != BATTER) {
            System.err.println("Incorrect Statistic: Dbl is only applicable to batters");
            return -1;
        }
        return dbl;
    }

    public int getTpl() {
        if (type != BATTER) {
            System.err.println("Incorrect Statistic: Tpl is only applicable to batters");
            return -1;
        }
        return tpl;
    }

    public int getHr() {
        return hr;
    }

    public int getBb() {
        return bb;
    }

    public float getIp() {
        if (type != PITCHER) {
            System.err.println("Incorrect Statistic: IP is only applicable to pitchers");
            return -1;
        }
        return ip;
    }

    public int getEr() {
        if (type != PITCHER) {
            System.err.println("Incorrect Statistic: ER is only applicable to pitchers");
            return -1;
        }
        return er;
    }

    public int getK() {
        if (type != PITCHER) {
            System.err.println("Incorrect Statistic: K is only applicable to pitchers");
            return -1;
        }
        return k;
    }
    
    public int getBf() {
    	if (type != PITCHER) {
    		System.err.println("Incorrect Statistic: BF is only applicable to pitchers");
    		return -1;
    	}
    	return bf;
    }

    /*
     * ACCESSOR METHODS FOR ATTRIBUTES / BUILTINS
     */
    
    public String getName() {
        return Name;
    }

    public String toString() {
        String toReturn = "";

        if(type == BATTER){	//Create concatenation depending on player type
            toReturn = Name+","+ab+","+r+","+h+","+dbl+","+tpl+","+hr+","+bb;
        }
        else {
            toReturn += Name+","+ip+","+h+","+er+","+bb+","+k;
        }

        return toReturn;
    }
    
    /* the two following methods below are used to determine type of player */
    
    public static boolean isBatter(PlayerObj player) {
        return player.getType() == PlayerObj.BATTER;
    }
    
    public static boolean isPitcher(PlayerObj player) {
        return player.getType() == PlayerObj.PITCHER;
    }
    
    public static final Attribute name = new Attribute() {
    	public String get(PlayerObj player) {
    		return player.getName();
    	}
    };

    public static final PlayerStat AB = new PlayerStat() {
        public float get(PlayerObj player) {
            return player.getAb();
        }
    };
    public static final PlayerStat R = new PlayerStat() {
        public float get(PlayerObj player) {
            return player.getR();
        }
    };
    public static final PlayerStat H = new PlayerStat() {
        public float get(PlayerObj player) {
            return player.getH();
        }
    };
    public static final PlayerStat DBL = new PlayerStat() {
        public float get(PlayerObj player) {
            return player.getDbl();
        }
    };
    public static final PlayerStat TPL = new PlayerStat() {
        public float get(PlayerObj player) {
            return player.getTpl();
        }
    };
    public static final PlayerStat HR = new PlayerStat() {
        public float get(PlayerObj player) {
            return player.getHr();
        }
    };
    public static final PlayerStat BB = new PlayerStat() {
        public float get(PlayerObj player) {
            return player.getBb();
        }
    };
    public static final PlayerStat IP = new PlayerStat() {
        public float get(PlayerObj player) {
            return player.getIp();
        }
    };
    public static final PlayerStat ER = new PlayerStat() {
        public float get(PlayerObj player) {
            return player.getEr();
        }
    };
    public static final PlayerStat K = new PlayerStat() {
        public float get(PlayerObj player) {
            return player.getK();
        }
    };
    public static final PlayerStat BF = new PlayerStat() {
    	public float get(PlayerObj player) {
    		return player.getBf();
    	}
    };

    private String Name;

    private int type;

    private int ab;
    private int r;
    private int h;
    private int dbl;
    private int tpl;
    private int hr;
    private int bb;
    private float ip;
    private int er;
    private int k;
    private int bf;

    public final static int PITCHER = 1;
    public final static int BATTER = 2;
}
