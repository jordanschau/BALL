/*
 * COMS W4115 PROGRAMMING LANGUAGES AND TRANSLATORS FALL 2009
 * Team llamamelon - BALL language
 * Authors: Cipta Herwana, Daniel Lasry, Sam Lee, Nathan Miller, Jordan Schau
 * ball.y - Parser specification of the BALL language (using BYACC/J)
 */

%{
import java.io.*;
import java.util.*;
import lexer.*;
import codegen.*;

/* necessary because ParserVal deals with generics */
@SuppressWarnings("unchecked")
%}

/* 
 * ===============================================================
 *  Token declarations for communication between lexer and parser
 * ===============================================================
 */

%token STRING
%token IDENTIFIER
%token NUMBER
%token SEMICOLON
%token COLON
%token AND OR NOT
%token EQL PLUSEQL MINEQL MULTEQL DIVEQL MODEQL
%token PLUS MIN MULT DIV MOD
%token PPLUS MMIN
%token IS ISNOT GT LT GTE LTE
%token COMMA
%token OPAREN
%token CPAREN
%token OSQUARE
%token CSQUARE
%token PRINT
%token ACTIVATE
%token FUNCTION
%token SIMFUNCTION
%token STAT
%token RETURN
%token RETURNS
%token PRIMITIVE
%token END
%token WHERE SELF
%token LIST OF
%token FROM ANY
%token APOSTROPHEESS PLAYBALL
%token IF THEN ELSE
%token DO TIMES FOREACH IN STOPDO

%%

/* 
 * ==================
 *  Language Grammar
 * ==================
 */

/*
 * What the heck is going on in here!?
 * ===================================
 * 
 * To put it simple, all the grammar code does is laying out the structure of 
 * the parse tree. No error checking is involved here. Only after the whole 
 * parse tree is complete will error checking and code generation start.
 * Therefore, gen() now takes a symbol table, because that's the phase that is
 * going to need it the most.
 */

/*
 * REMEMBER: ALL CODE CHECKING AND GENERATION STARTS AFTER TOP.GEN(TABLE). what
 * the byacc/j code does is just to prepare the program structure.
 */


/*** PROGRAM ***/
/*
 * This is the top node of the program. If the token sequence can be derived
 * from this node, a Java program can be spit out by the compiler.
 */
program : 
    statement_list { 
        //System.err.println("adding node for _program_");
        LinkedList<Stmt> stlist = (LinkedList<Stmt>)$1.obj;
        Program top = new Program(stlist, outname);
	top.setLine(currLine());
        top.gen(table); // moves to intermediate code generation stage
    }
;

/*
 * A statement list collects a sequence of statements inside a linked list (for
 * easy sequential reading). The way the grammar works is that it first seeks
 * the head of the statement list (by peeling off statements one by one from the
 * end to the beginning). Then, as the list is propagated up the calls, it is
 * appended with each "peel" of the list.
 * 
 * This kind of strategy when dealing with sequences of nodes that are all
 * similar (like parameter lists, etc) is found throughout the grammar.
 */
statement_list : 
    statement { // reached beginning of statement list
        LinkedList<Stmt> newlist = new LinkedList<Stmt>();
        newlist.addLast((Stmt)$1.obj);
        $$ = new ParserVal(newlist);
    }
    | statement_list statement { // add as we propagate up
        LinkedList<Stmt> cur = (LinkedList<Stmt>)$1.obj;
        cur.addLast((Stmt)$2.obj);
        $$ = new ParserVal(cur);
    }
;

/*
 * This represents a top-level statement. Since BALL has no main(), top-level
 * statements can be anything from a print commant, assignment, function def,
 * etc. BALL must then sort this to create a proper java program while
 * preserving the meaning of the original source.
 * 
 * However, body statements, that is those that can appear in function bodies,
 * loops or conditional blocks, cannot contain function definitions (nested
 * func definitions are not supported by the BALL language).
 */
statement : 
    body_statement { $$ = $1; }
    | function_definition { $$ = $1; }
    | sim_function_definition { $$ = $1; }
;

/*
 * Similar to statement_list, but specifically for body statements, that is
 * function bodies, if blocks, et cetera.
 */
body_statement_list : 
    body_statement {
        LinkedList<Stmt> newlist = new LinkedList<Stmt>();
        newlist.addLast((Stmt)$1.obj);
        $$ = new ParserVal(newlist);
    }
    | body_statement_list body_statement {
        LinkedList<Stmt> cur = (LinkedList<Stmt>)$1.obj;
        cur.addLast((Stmt)$2.obj);
        $$ = new ParserVal(cur);
    }
;

/*
 * Body Statements are all statements except function declarations. Having
 * function declarations not on the top level (corresponding to the 'class'
 * level of the java output) is not supported by the BALL language. 
 */
body_statement : 
      declaration { $$ = $1; }
    | stat_declaration { $$ = $1; }
    | expression_statement { $$ = $1; }
    | if_statement { $$ = $1; }
    | print_statement { $$ = $1; }
    | jump_statement { $$ = $1; }
    | assignment_statement { $$ = $1; } 
    | activate_statement { $$ = $1; }
    | iteration_statement { $$ = $1; }
    | playball { $$ = $1; }
;

/** FUNCTION_DEFINITION **/

type :
    PRIMITIVE { 
        $$ = $1; 
    }
    | LIST OF type {
        $$ = new ParserVal(new ListType((Type)$3.obj));
    }
;

/*
 * In BALL, function definitions can only happen in the top level. Naturally,
 * the only variables they'll get access to is global variables, parameters,
 * and variables declared in the body itself. 
 */
function_definition :
    FUNCTION IDENTIFIER OPAREN parameter_list0 CPAREN RETURNS type COLON END {
        //System.err.println("parser: function definition");
    
        Identifier name = (Identifier)$2.obj;
    
        /*
         * no function body
         */
        LinkedHashMap<Identifier,Type> paramlist = (LinkedHashMap<Identifier,Type>)$4.obj;
        Type retType = (Type)$7.obj;
        LinkedList<Stmt> bodylist = new LinkedList<Stmt>();
        bodylist.add(new ExprStmt());
    
        FuncDef newfun = new FuncDef((Identifier)$2.obj, retType, paramlist, bodylist);
	newfun.setLine(currLine());
    
    	$$ = new ParserVal(newfun);
    }
    | FUNCTION IDENTIFIER OPAREN parameter_list0 CPAREN RETURNS type COLON body_statement_list END {
        //System.err.println("parser: function definition");
        
        Identifier name = (Identifier)$2.obj;
        
        /*
         * Special caution here on the hash implementation used for parameter
         * list.
         * 
         * In BALL, the parameter list of the function behaves like Java. That
         * is, the parameters' order is also important in distinguishing a
         * function. Therefore, the LinkedHashMap class is used instead of the
         * plain HashMap which may not preserve order.
         */
        LinkedHashMap<Identifier,Type> paramlist = (LinkedHashMap<Identifier,Type>)$4.obj;
        Type retType = (Type)$7.obj;
        LinkedList<Stmt> bodylist = (LinkedList<Stmt>)$9.obj;
        
        FuncDef newfun = new FuncDef((Identifier)$2.obj, retType, paramlist, bodylist);
	newfun.setLine(currLine());
        
        $$ = new ParserVal(newfun);
    }
;

parameter_list0 :
    { $$ = new ParserVal(new LinkedHashMap<Identifier,Type>()); }
    | parameter_list { $$ = $1; }
    ;

/** SIM_FUNCTION_DEFINITION **/

/*
 * Just like Function definitions, Sim function definitions can only happen in the top level.
 * The only variables they'll get access to are the global team1 and team2 parameters.
 */
sim_function_definition :
    SIMFUNCTION IDENTIFIER IS COLON body_statement_list END {
        //System.err.println("parser: simfunction definition");
        
        Identifier name = (Identifier)$2.obj;
        LinkedList<Stmt> bodylist = (LinkedList<Stmt>)$5.obj;
        
        SimFuncDef newfun = new SimFuncDef((Identifier)$2.obj, bodylist);
	newfun.setLine(currLine());
        
        $$ = new ParserVal(newfun);
    }
;

parameter_list : 
    parameter {
        // keep track of what names have been used, etc.
        // a LinkedHashMap stores entries in insertion order.
        LinkedHashMap<Identifier,Type> paramlist = 
            new LinkedHashMap<Identifier,Type>();
        
        // parameter node returns a pair (Identifier, Type) inside an Object[]
        Object[] param = (Object[]) $1.obj;
        Identifier name = (Identifier)(param[1]);
        Type t = (Type)((Object[])$1.obj)[0];
        
        paramlist.put(name, t);
        $$ = new ParserVal(paramlist);
    }
    | parameter_list COMMA parameter {
        // add to previous parameter list
        LinkedHashMap<Identifier,Type> paramlist = 
            (LinkedHashMap<Identifier,Type>)$1.obj;

        // parse the parameter, just like before
        Object[] param = (Object[]) $3.obj;
        Identifier name = (Identifier)(param[1]);
        Type t = (Type)((Object[])$3.obj)[0];
      
        // simple error checking on paramlist
        if (paramlist.containsKey(name)) {
            System.err.println("parser: error: parameter with same name " + 
                    name + " already present.");
            System.exit(1);
        }
        
        paramlist.put(name, t);
        $$ = new ParserVal(paramlist);
    }
;

parameter : 
    type IDENTIFIER {
        // returns the Type and Identifier objects as a pair
        Object[] param = new Object[2];
        param[0] = (Object)$1.obj;
        param[1] = (Object)$2.obj;
        $$ = new ParserVal(param);
    }
;

/**PRINT_STATEMENT**/
print_statement : 
    PRINT expression SEMICOLON {
	PrintStmt printst = new PrintStmt((Expr)$2.obj);
	printst.setLine(currLine());
        $$ = new ParserVal(printst);
    }
;

/**IF_STATEMENT**/
if_statement : IF OPAREN expression CPAREN THEN COLON body_statement_list END {
			LinkedList<Stmt> bodylist = (LinkedList<Stmt>)$7.obj;
			IfStmt ifstatement = new IfStmt((Expr)$3.obj, bodylist);
			ifstatement.setLine(currLine());
			$$ = new ParserVal(ifstatement);
	     }
             | IF OPAREN expression CPAREN THEN COLON body_statement_list else_statement END{
			LinkedList<Stmt> bodylist = (LinkedList<Stmt>)$7.obj;
			LinkedList<Stmt> elselist = (LinkedList<Stmt>)$8.obj;
			IfStmt ifstatement = new IfStmt((Expr)$3.obj, bodylist, elselist);
			ifstatement.setLine(currLine());
			$$ = new ParserVal(ifstatement);
	     }
;

else_statement : ELSE COLON body_statement_list { $$ = $3; }
               ;


/**DECLARATION**/
declaration : 
    type variable_declarators SEMICOLON {
		Declaration dec = new Declaration((Type)$1.obj, (ArrayList<Object[]>)$2.obj);
		dec.setLine(currLine());
		$$ = new ParserVal(dec);
	}
;

variable_declarators : 
    variable_declarator {
        ArrayList<Object[]> a = new ArrayList<Object[]>();
        a.add((Object[])$1.obj);
        $$ = new ParserVal (a);
    }
    | variable_declarators COMMA variable_declarator {
        ArrayList<Object[]> a = (ArrayList<Object[]>)$1.obj;
        a.add((Object[])$3.obj);
        $$ = new ParserVal (a);
    }
;

variable_declarator : 
    IDENTIFIER {
	    Object[] a = new Object[2];
	    a[0] = $1.obj;
            a[1] = null;
	    $$ = new ParserVal (a);
    }
    | IDENTIFIER EQL expression {
        Object[] a = new Object[2];
        a[0] = $1.obj;
        a[1] = $3.obj;
        $$ = new ParserVal (a);
    }
;

/*
 * This is necessary because the compilation rules for stat declarations are
 * different to variable declarations. In variable declarations, the expression
 * is evaluated, but in stat declarations the expression is encapsulated into
 * a function.
 */
stat_declaration : 
    STAT IDENTIFIER EQL stat_expression SEMICOLON {
        StatDef decl = new StatDef((Identifier)$2.obj, (StatExpr)$4.obj);
	decl.setLine(currLine());
        $$ = new ParserVal(decl);
    }
;

/* stats don't have access to the full expression grammar, just a part of it */
stat_expression : 
    stat_mult_expr { 
	StatExpr stex = new StatExpr((StatMult)$1.obj);
	stex.setLine(currLine());
        $$ = new ParserVal(stex); 
    }
    | stat_expression PLUS stat_mult_expr {
	StatExpr stex = new StatExpr((StatExpr)$1.obj, (StatMult)$3.obj, StatExpr.Op.PLUS);
	stex.setLine(currLine());
        $$ = new ParserVal(stex);
    }
    | stat_expression MIN stat_mult_expr {
	StatExpr stex = new StatExpr((StatExpr)$1.obj, (StatMult)$3.obj, StatExpr.Op.MIN);
	stex.setLine(currLine());
        $$ = new ParserVal(stex);
    }
    ;

stat_mult_expr : 
    stat_atom_expr { 
	StatMult stmu = new StatMult((StatAtom)$1.obj);
	stmu.setLine(currLine());
        $$ = new ParserVal(stmu); 
    }
    | stat_mult_expr MULT stat_atom_expr { 
	StatMult stmu = new StatMult((StatMult)$1.obj, (StatAtom)$3.obj, StatMult.Op.MULT);
	stmu.setLine(currLine());
        $$ = new ParserVal(stmu);
    }
    | stat_mult_expr DIV stat_atom_expr { 
	StatMult stmu = new StatMult((StatMult)$1.obj, (StatAtom)$3.obj, StatMult.Op.DIV);
	stmu.setLine(currLine());
        $$ = new ParserVal(stmu);
    }
    | stat_mult_expr MOD stat_atom_expr { 
	StatMult stmu = new StatMult((StatMult)$1.obj, (StatAtom)$3.obj, StatMult.Op.MOD);
	stmu.setLine(currLine());
        $$ = new ParserVal(stmu);
    }
    ;

stat_atom_expr : 
    IDENTIFIER {
	StatAtom statm = new StatAtom((Identifier)$1.obj);
	statm.setLine(currLine());
        $$ = new ParserVal(statm);
    }
    | NUMBER {
	StatAtom statm = new StatAtom((NumericConst)$1.obj);
	statm.setLine(currLine());
        $$ = new ParserVal(statm);
    }
    | OPAREN stat_expression CPAREN {
	StatAtom statm = new StatAtom((StatExpr)$2.obj);
	statm.setLine(currLine());
        $$ = new ParserVal(statm);
    }
    ;

/*
 * Return statements
 * -----------------
 * Functions need to verify what they return to the program. However, since
 * function bodies themselves are multi-layered structures, the "return"
 * statement might be buried deep somewhere inside a loop within a conditional
 * inside another loop, for example.
 */
jump_statement : 
    RETURN expression SEMICOLON {
        ReturnStmt newret = new ReturnStmt((Expr)$2.obj);
	newret.setLine(currLine());
        $$ = new ParserVal(newret);
    }
    | STOPDO SEMICOLON {
    	StopdoStmt stop = new StopdoStmt();
	stop.setLine(currLine());
    	$$ = new ParserVal(stop);
    }
;

/**ASSIGNMENT_STATEMENT**/ 
/*
 * A few notes on assignment:
 * - the normal EQL operator, '=', can be used on any type. All other operators
 *   must be a number assignment (for example, 'team x += load("dodgers.team");'
 *   returns an error)
 * - both the variable the identifier is referring to and the expression must
 *   match in type.
 */
assignment_statement :
    // make the node
    IDENTIFIER assignment_operator expression SEMICOLON {
        AssignmentStmt node = 
            new AssignmentStmt((Identifier)$1.obj, 
                    (AssignmentStmt.Op)$2.obj,
                    (Expr)$3.obj);
	node.setLine(currLine());
        $$ = new ParserVal(node);
    }
;

/**ITERATION_STATEMENT**/
iteration_statement :
	DO COLON body_statement_list END {
		LinkedList<Stmt> bodylist = (LinkedList<Stmt>)$3.obj;
		IterationStmt stmt = new IterationStmt(bodylist);
		stmt.setLine(currLine());
		$$ = new ParserVal(stmt);
	}
	| DO expression TIMES COLON body_statement_list END {
		LinkedList<Stmt> bodylist = (LinkedList<Stmt>)$5.obj;
		IterationStmt stmt = new IterationStmt((Expr)$2.obj, bodylist);
		stmt.setLine(currLine());
        	$$ = new ParserVal(stmt);
	}
	| FOREACH IDENTIFIER IN expression COLON body_statement_list END {
		LinkedList<Stmt> bodylist = (LinkedList<Stmt>)$6.obj;
		IterationStmt stmt = new IterationStmt((Identifier)$2.obj, (Expr)$4.obj, bodylist);
		stmt.setLine(currLine());
		$$ = new ParserVal(stmt);
	}
;

/* operators of assignment */
assignment_operator : 
    EQL { $$ = new ParserVal(AssignmentStmt.Op.EQL); }
    | PLUSEQL { $$ = new ParserVal(AssignmentStmt.Op.PLUSEQL); }
    | MINEQL  { $$ = new ParserVal(AssignmentStmt.Op.MINEQL); }
    | MULTEQL { $$ = new ParserVal(AssignmentStmt.Op.MULTEQL); }
    | DIVEQL  { $$ = new ParserVal(AssignmentStmt.Op.DIVEQL); }
    | MODEQL  { $$ = new ParserVal(AssignmentStmt.Op.MODEQL); }
;

/**ACTIVATE_STATEMENT**/ 
/*
 * Set Simulator.theSimFunction to the simulation function.
 */
activate_statement :
    ACTIVATE IDENTIFIER SEMICOLON {
	ActivateStmt activateNode = new ActivateStmt((Identifier)$2.obj);
	activateNode.setLine(currLine());
	$$ = new ParserVal(activateNode);
    }
;


/**EXPRESSION_STATEMENT**/
/* simply evaluate the expression. Usually wanted for the side effects. */
expression_statement : 
    SEMICOLON {
        ExprStmt stmt = new ExprStmt();
	stmt.setLine(currLine());
        $$ = new ParserVal(stmt);
    }
    | expression SEMICOLON {
        ExprStmt stmt = new ExprStmt((Expr)$1.obj);
	stmt.setLine(currLine());
        $$ = new ParserVal(stmt);
    }
;


/* EXPRESSION */
expression : logical_or_expression { $$ = $1; }
;

/* LOGICAL */
logical_or_expression :
	logical_and_expression { $$ = $1; }
    | logical_or_expression OR logical_and_expression { 
			LogicalExpr logicalex = new LogicalExpr(LogicalExpr.Op.OR,(Expr)$1.obj, (Expr)$3.obj);
			logicalex.setLine(currLine());
           	$$ = new ParserVal(logicalex);
	}
;

logical_and_expression :
	logical_not_expression { $$ = $1; }
    | logical_and_expression AND logical_not_expression {  
			LogicalExpr logicalex = new LogicalExpr(LogicalExpr.Op.AND,(Expr)$1.obj, (Expr)$3.obj);
			logicalex.setLine(currLine());
            $$ = new ParserVal(logicalex);
	}
;

logical_not_expression : 
    comparison_expression { $$ = $1; }
    | NOT logical_not_expression { 
			LogicalExpr logicalex = new LogicalExpr(LogicalExpr.Op.NOT,(Expr)$2.obj, null);
			logicalex.setLine(currLine());
           	$$ = new ParserVal(logicalex);
	}
;


/* COMPARISON */
comparison_expression : addition_expression { $$ = $1; }
                      | comparison_expression IS   addition_expression {
				ComparisonExpr compex = new ComparisonExpr(ComparisonExpr.Op.IS,(Expr)$1.obj, (Expr)$3.obj);
				compex.setLine(currLine());
                    		$$ = new ParserVal(compex);}
                      | comparison_expression ISNOT addition_expression {
				ComparisonExpr compex = new ComparisonExpr(ComparisonExpr.Op.ISNOT,(Expr)$1.obj, (Expr)$3.obj);
				compex.setLine(currLine()); 
                    		$$ = new ParserVal(compex);}
                      | comparison_expression GT    addition_expression {
				ComparisonExpr compex = new ComparisonExpr(ComparisonExpr.Op.GT,(Expr)$1.obj, (Expr)$3.obj);
				compex.setLine(currLine()); 
                    		$$ = new ParserVal(compex);}
                      | comparison_expression LT    addition_expression {
				ComparisonExpr compex = new ComparisonExpr(ComparisonExpr.Op.LT,(Expr)$1.obj, (Expr)$3.obj);
				compex.setLine(currLine()); 
                    		$$ = new ParserVal(compex);}
                      | comparison_expression GTE   addition_expression {
				ComparisonExpr compex = new ComparisonExpr(ComparisonExpr.Op.GTE,(Expr)$1.obj, (Expr)$3.obj);
				compex.setLine(currLine()); 
                    		$$ = new ParserVal(compex);}
                      | comparison_expression LTE   addition_expression {
				ComparisonExpr compex = new ComparisonExpr(ComparisonExpr.Op.LTE,(Expr)$1.obj, (Expr)$3.obj);
				compex.setLine(currLine()); 
                    		$$ = new ParserVal(compex);}
;

/* ARITHMETIC */
addition_expression : multiplication_expression
                    | addition_expression PLUS multiplication_expression { 
			ArithmeticExpr arex = new ArithmeticExpr(ArithmeticExpr.Op.PLUS,(Expr)$1.obj, (Expr)$3.obj);
			arex.setLine(currLine());
                    	$$ = new ParserVal(arex);}
                    | addition_expression MIN multiplication_expression { 
			ArithmeticExpr arex = new ArithmeticExpr(ArithmeticExpr.Op.MIN,(Expr)$1.obj, (Expr)$3.obj);
			arex.setLine(currLine()); 
                    	$$ = new ParserVal(arex);}
;

multiplication_expression : unary_expression
			  | multiplication_expression MULT unary_expression { 
					ArithmeticExpr arex = new ArithmeticExpr(ArithmeticExpr.Op.MULT,(Expr)$1.obj, (Expr)$3.obj);
					arex.setLine(currLine());
                    			$$ = new ParserVal(arex);}
              | multiplication_expression DIV unary_expression {  
					ArithmeticExpr arex = new ArithmeticExpr(ArithmeticExpr.Op.DIV,(Expr)$1.obj, (Expr)$3.obj);
					arex.setLine(currLine());
                    			$$ = new ParserVal(arex);}
              | multiplication_expression MOD unary_expression {  
					ArithmeticExpr arex = new ArithmeticExpr(ArithmeticExpr.Op.MOD,(Expr)$1.obj, (Expr)$3.obj);
					arex.setLine(currLine());
                    			$$ = new ParserVal(arex);}
;

/* UNARY */
unary_expression : 
    postfix_expression { $$ = $1; }
    | PPLUS unary_expression {
	UnaryExpr unex = new UnaryExpr(UnaryExpr.Op.PPLUS, (Expr)$2.obj, UnaryExpr.Fix.PRE);
	unex.setLine(currLine());
    	$$ = new ParserVal(unex);
    }
    | MMIN unary_expression {
	UnaryExpr unex = new UnaryExpr(UnaryExpr.Op.MMIN, (Expr)$2.obj, UnaryExpr.Fix.PRE);
	unex.setLine(currLine());
    	$$ = new ParserVal(unex);
    }
    | primary_expression FROM unary_expression {
        //fetching
        MatchExpr match = new MatchExpr((Expr)$1.obj, (Expr)$3.obj);
	match.setLine(currLine());
        $$ = new ParserVal(match);
    }
    | ANY unary_expression {
        // random fetching
        MatchExpr match = new MatchExpr((Expr)$2.obj);
	match.setLine(currLine());
        $$ = new ParserVal(match);
    }
;

/* POSTFIX */
postfix_expression : 
    primary_expression { 
        $$ = $1; 
    }
    | postfix_expression APOSTROPHEESS IDENTIFIER {
    	// attribute/stats call
	ApostrExpr apex = new ApostrExpr((Expr)$1.obj, (Identifier)$3.obj);
	apex.setLine(currLine());
    	$$ = new ParserVal(apex);
    }
    | postfix_expression WHERE OPAREN expression CPAREN {
        // filtering
	FilterExpr filex = new FilterExpr((Expr)$1.obj, (Expr)$4.obj);
	filex.setLine(currLine());
        $$ = new ParserVal(filex);
    }
    | postfix_expression PPLUS {
	UnaryExpr unex = new UnaryExpr(UnaryExpr.Op.PPLUS, (Expr)$1.obj, UnaryExpr.Fix.POST);
	unex.setLine(currLine());
    	$$ = new ParserVal(unex);
    }
    | postfix_expression MMIN {
	UnaryExpr unex = new UnaryExpr(UnaryExpr.Op.MMIN, (Expr)$1.obj, UnaryExpr.Fix.POST);
	unex.setLine(currLine());
    	$$ = new ParserVal(unex);
    }
;

/* PRIMARY */
primary_expression : atom_expression { $$ = $1; }
		   | function_call { $$ = $1; }
;

/* FUNCTION_CALL */
function_call : IDENTIFIER OPAREN CPAREN {
		Funcall fcall = new Funcall((Identifier)$1.obj);
		fcall.setLine(currLine());
		$$ = new ParserVal(fcall);
	      }	
              | IDENTIFIER OPAREN argument_list CPAREN {
		Funcall fcall = new Funcall((Identifier)$1.obj, (ArrayList<Expr>)$3.obj);
		fcall.setLine(currLine());
		$$ = new ParserVal(fcall);
	      }	
;

argument_list : 
    expression {
        ArrayList<Expr> a = new ArrayList<Expr>();
		a.add((Expr)$1.obj);
		$$ = new ParserVal(a);
    }
    | argument_list COMMA expression {
		ArrayList<Expr> a = (ArrayList<Expr>)$1.obj;
		a.add((Expr)$3.obj);
		$$ = new ParserVal(a);
    }
;


/* ATOM_EXPRESSION */
atom_expression : 
    STRING { 
        //System.err.println("got string " + $1.obj);
	AtomicExpr atex = new AtomicExpr((StringConst)($1.obj));
	atex.setLine(currLine());
        $$ = new ParserVal(atex);
    }
    | IDENTIFIER {
	AtomicExpr atex = new AtomicExpr((Identifier)($1.obj));
	atex.setLine(currLine());
        $$ = new ParserVal(atex);
    }
    | NUMBER {
	AtomicExpr atex = new AtomicExpr((NumericConst)($1.obj));
	atex.setLine(currLine());
        $$ = new ParserVal(atex);
    }
    | OPAREN expression CPAREN {
	AtomicExpr atex = new AtomicExpr((Expr)($2.obj));
	atex.setLine(currLine());
    	$$ = new ParserVal(atex);
    }
    | list_initializer {
        $$ = $1;
    }
    | SELF {
        $$ = new ParserVal(new FilterExpr.SelfKeyword());
    }
;

list_initializer : 
    OSQUARE argument_list CSQUARE {
        ArrayList<Expr> args = (ArrayList<Expr>)$2.obj;
	ListInit linit = new ListInit(args);
	linit.setLine(currLine());
        $$ = new ParserVal(linit);
    }//list of type number x = number[];
    | type OSQUARE CSQUARE {
	ListInit linit = new ListInit((Type)$1.obj);
	linit.setLine(currLine());
        $$ = new ParserVal(linit);
    }
    ;

playball : 
    PLAYBALL SEMICOLON{
	/* What's this? Nothing, move along. */
	$$ = new ParserVal(new PlayBall());
    }
;
%%

/* 
 * ====================================================================
 *  Other enhancements to the parser class, plus constructors and main
 * ====================================================================
 */

/* a reference to the lexer object */
private Yylex lexer;
  
/* a reference to the symbol table */
private SymbolTable table;

/* what to call the class */
private String outname;

/*A list of variable declarations */
LinkedList<Declaration> varDeclarations = new LinkedList<Declaration>();

/* interface to the lexer */
private int yylex () {
	int yyl_return = -1;
    try {
		yyl_return = lexer.yylex();
    }
    catch (IOException e) {
		System.err.println("yylex: IO error :"+e);
    }
	return yyl_return;
}

public int currLine(){
    return lexer.getLine()+1;
}

public String currTok(){
    return lexer.yytext();
}

/* error reporting */
private String errStr = "=======================ERROR=======================";
public void yyerror (String error) {
	System.err.println (errStr + "\n" + outname + ".ball Line " + currLine() + ": "
		+ error + " before '" + currTok() + "'\n" + errStr);
}

/* lexer is created in the constructor */
public Parser(Reader r, SymbolTable table, String out) {
    lexer = new Yylex(r, this, table);
    this.table = table;
    outname = out;
}

/* 
 * ===============
 *  Main function
 * ===============
 */

public static void main(String args[]) throws IOException {
	if (args.length == 0) {
		System.err.println("no arguments");
		System.exit(0);
	}
    
    /*
     * Determine the name of the output file
     */
    String name = args[0];
    name = name.substring(name.lastIndexOf('/')+1);
    
    int val = name.lastIndexOf('.');
    while (val != -1) {
        name = name.substring(0, val);
        val = name.lastIndexOf('.');
    }
    
    if (name.length() < 1) {
        System.err.println("invalid name");
        System.exit(1);
    }
    
    /*
     * create the parser
     */
	Parser yyparser = new Parser(new FileReader(args[0]), 
            new SymbolTable(true), name);
	yyparser.yyparse();
}

