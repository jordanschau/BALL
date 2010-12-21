/*
 * COMS W4115 PROGRAMMING LANGUAGES AND TRANSLATORS FALL 2009
 * Team llamamelon - BALL language
 * Authors: Cipta Herwana, Daniel Lasry, Sam Lee, Nathan Miller, Jordan Schau 
 * Run.java - Encapsulates BALL and Java compilation
 */

package compiler;

import java.io.*;
import java.lang.reflect.*;

import com.sun.tools.javac.Main;

/**
 * automatically compile and run the BALL program.
 */
public class Run {
    
    /**
     * This class implements the 'tee' program, useful when user wants to output
     * the java program as it is being generated.
     */
    public static class Tee extends OutputStream {
        
        /**
         * Creates the tee. main is the one that always gets written to, but the
         * branch can be null.
         */
        public Tee(OutputStream main, OutputStream branch) {
            if (main == null)
                throw new IllegalArgumentException("main cannot be null");
            this.main = main;
            this.branch = branch;
        }

        /* write to both branches */
        @Override
        public void write(int arg0) throws IOException {
            main.write(arg0);
            if (branch != null) branch.write(arg0);
        }
        
        /** Closes both, chained and tee, streams. */
        public void close() throws IOException {
             flush();
             main.close();
             if (branch != null) branch.close();
        }
        
        /** Flush both */
        public void flush() throws IOException {
             main.flush();
             if (branch != null) branch.flush();
        }
        
        private OutputStream main;
        private OutputStream branch;
        
    }
    
    /**
     * USAGE:
     * compiler.Run -n -t -k file 
     * 
     * tools.jar in the lib/ part of the JRE installation needs to be added
     * to the classpath.
     * 
     * COMMAND LINE ARGS:
     * 1. '-k' = keep the java file created. Default is delete
     * 2. '-n' = name the java file like the BALL file. Default is random temp
     * 3. '-t' = tee the java file to stdout
     * 5. file = the BALL source
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        
        boolean keep = false;
        boolean tee = false;
        boolean name = false;
        
        if (args.length < 1) {
            System.err.println("not enough arguments.");
            System.exit(1);
        }
        
        String cur;
        for (int i = 0; i < args.length - 1; i++) {
            cur = args[i];
            if (cur.equals("-n")) name = true;
            if (cur.equals("-t")) tee = true;
            if (cur.equals("-k")) keep = true;
        }
        
        if (keep)
        	System.err.println("BALL: keeping java files");
        
        /* 1. Create the output java file */
        
        File javaFile = null;
        try {
            javaFile = File.createTempFile("ball_", ".java", new File(System.getProperty("user.dir")));
            if (name) {
                // figure out class name
                String name1 = args[0];
                name1 = name1.substring(name1.lastIndexOf('/')+1);
                
                int val = name1.lastIndexOf('.');
                while (val != -1) {
                    name1 = name1.substring(0, val);
                    val = name1.lastIndexOf('.');
                }
                javaFile.renameTo(new File(javaFile.getParent() + name1));
            }
        } 
        catch (IOException e) {
            System.err.println("ball: error: " + e.getLocalizedMessage());
            System.exit(2);
        }
        
        String filename = javaFile.getName();
        String classname = filename.substring(0, filename.length()-5);

        /* 2. Change System.out to point to that file */

        PrintStream origOut = System.out;
        Tee out = null;
        
        try {
            if (tee)
                out = new Tee(new FileOutputStream(javaFile), System.out);
            else
                out = new Tee(new FileOutputStream(javaFile), null);
        } 
        catch (FileNotFoundException e) {
            System.err.println("ball: error: " + e.getLocalizedMessage());
            System.exit(3);
        }
        System.setOut(new PrintStream(out));
        
        /* 3. Make the parser, and compile the BALL program */

        Parser yyparser = null; 
        try {
            yyparser = new Parser(new FileReader(args[args.length-1]), 
                    new SymbolTable(true), classname);
        } 
        catch (FileNotFoundException e) {
            System.err.println("ball: error: " + e.getLocalizedMessage());
            System.exit(3);
        }
        yyparser.yyparse(); // SHOWTIME
        
        try {
            out.flush();
        } 
        catch (IOException e) {
            System.err.println("ball: error: " + e.getLocalizedMessage());
            System.exit(3);
        }
        
        System.setOut(origOut);
        
        /* HERE'S THE NEW PART: CREATE THE JAVAC OBJECT AND COMPILE */
        
        //com.sun.tools.javac.Main javac = new com.sun.tools.javac.Main();
        String[] myargs = new String[] {
                "-d", System.getProperty("user.dir"),
                filename
        };
        int status = Main.compile(myargs);
        
        switch (status) {
        case 0:  // OK
            // Make the class file temporary as well
            //File runfile = new File(javaFile.getParent(), classname + ".class");
            
            try {
                // Try to access the class and run its main method
                Class<?> run = Class.forName(classname);
                Method main = run.getMethod("main", new Class[] { String[].class });
                main.invoke(null, new Object[] { new String[0] });

            } 
            catch (InvocationTargetException ex) {
                // Exception in the main method we just tried to run
                System.err.println("Exception in main: " + ex.getTargetException());
                ex.getTargetException().printStackTrace();
            } 
            catch (Exception ex) {    
                System.err.println(ex.toString());
            }
            break;
        case 1: System.err.println("Compile status: ERROR"); break;
        case 2: System.err.println("Compile status: CMDERR"); break;
        case 3: System.err.println("Compile status: SYSERR"); break;
        case 4: System.err.println("Compile status: ABNORMAL"); break;
        default:
            System.err.println("Compile status: Unknown exit status");
        }
        
        try {
            out.close();
        } 
        catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
            System.exit(5);
        }
        
        // delete the class files
        
        File curdir = new File(javaFile.getParent());
        String[] children = curdir.list();
        for (String fname : children) {
            if (fname.startsWith(classname)) {
                File todel = new File(fname);
                if (!keep) todel.deleteOnExit();
            }
        }


    }

}
