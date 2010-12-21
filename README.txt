/*
 * COMS W4115 PROGRAMMING LANGUAGES AND TRANSLATORS FALL 2009
 * Team llamamelon - BALL language
 * Authors: Cipta Herwana, Daniel Lasry, Sam Lee, Nathan Miller, Jordan Schau 
 * README.txt - How to compile and run
 */
 
Where are the source files?
---------------------------
Our program consists of 16 java files now, most of them small files that contain
small fragments of the java code for a given nonterminal like print statements
or the program. The source is located in ./src, divided to 4 packages.

1. codegen  		= this package contains classes that correspond with the grammar
              		 nonterminals found in our program. All of them are subclasses of
              		 ParseTreeNode.java. Each has a method gen() that will print its 
              		 java code to stdout (for now).
             
2. compiler 		= this is where the compiler, that is the lexer and parser, are
             		 defined The intermediate code generator is just the gen()
              		 method for now, it might change when we add more stuff. The symbol
             		 table is defined here as well.
              
3. lexer    		= this package contains classes for lexical analysis. Things like
              		 what a token is, a keyword, string constants, etc. are defined
              		 here.

4. javabackend		= this package contains all of the supporting java files for BALL.
			 (for example, built-in functions and classes).
              
Compiling and running the program
---------------------------------
Compiling isn't done with a general makefile now. Instead, we're using Ant to
build the project.

http://en.wikipedia.org/wiki/Apache_Ant

To compile the program, just type "ant compile" in the terminal. There should be
an ant program wherever you have Java, I know cunix and clic has it. All the
class files are made in a new directory called "build".

To clean everything, go to the top level and just enter "ant clean". The "build"
directory will be deleted.

How it works
============
just run the BALL shell script in the newly created build/classes with the BALL
source as the argument.