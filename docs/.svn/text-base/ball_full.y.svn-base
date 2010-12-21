/*
 * COMS W4119 PROGRAMMING LANGUAGES AND TRANSLATORS FALL 2009
 * Team llamamelon - BALL language
 * ball_full.y - summary of the full grammar
 */

%token STRING IDENTIFIER NUMBER
%token SEMICOLON COLON COMMA
%token AND OR NOT
%token EQL PLUSEQL MINEQL MULTEQL DIVEQL MODEQL
%token PLUS MIN MULT DIV MOD
%token PPLUS MMIN
%token IS ISNOT GT LT GTE LTE
%token OPAREN CPAREN OSQUARE CSQUARE
%token PRINT ACTIVATE
%token FUNCTION SIMFUNCTION STAT RETURN RETURNS
%token PRIMITIVE
%token END
%token WHERE SELF
%token LIST OF
%token FROM ANY
%token APOSTROPHEESS
%token IF THEN ELSE
%token DO TIMES FOREACH IN STOPDO


%%

/*** PROGRAM ***/
program : 
    statement_list
;

statement_list : 
    statement
    | statement_list statement 
;

statement : 
    body_statement
    | function_definition
    | sim_function_definition
;

/*
 * Similar to statement_list, but specifically for body statements, that is
 * function bodies, if blocks, et cetera.
 */
body_statement_list : 
    body_statement
    | body_statement_list body_statement
;

/*
 * Body Statements are all statements except function declarations. Having
 * function declarations not on the top level (corresponding to the 'class'
 * level of the java output) is not supported by the BALL language. 
 */
body_statement : 
    declaration
    | stat_declaration 
    | expression_statement 
    | if_statement 
    | print_statement 
    | jump_statement 
    | assignment_statement  
    | activate_statement 
    | iteration_statement 
;

/** FUNCTION DEFINITION **/

type :
    PRIMITIVE
    | LIST OF type
;

/*
 * In BALL, function definitions can only happen in the top level. Naturally,
 * the only variables they'll get access to is global variables, parameters,
 * and variables declared in the body itself. 
 */
function_definition :
    FUNCTION IDENTIFIER OPAREN parameter_list0 CPAREN RETURNS type COLON END
    | FUNCTION IDENTIFIER OPAREN parameter_list0 CPAREN RETURNS type COLON body_statement_list END
;

parameter_list0 :
    // empty string
    | parameter_list 
;

/** SIM_FUNCTION_DEFINITION **/

sim_function_definition :
    SIMFUNCTION IDENTIFIER IS COLON body_statement_list END
;

parameter_list : 
    parameter
    | parameter_list COMMA parameter
;

parameter : 
    type IDENTIFIER
;

/**PRINT_STATEMENT**/
print_statement : 
    PRINT expression SEMICOLON
;

/**IF_STATEMENT**/
if_statement : IF OPAREN expression CPAREN THEN COLON body_statement_list END
             | IF OPAREN expression CPAREN THEN COLON body_statement_list else_statement END
             ;

else_statement : ELSE COLON body_statement_list
               ;


/**DECLARATION**/
declaration : 
    type variable_declarators SEMICOLON
;

variable_declarators : 
    variable_declarator
    | variable_declarators COMMA variable_declarator
;

variable_declarator : 
    IDENTIFIER
    | IDENTIFIER EQL expression
;

/* STAT DECLARATION */
stat_declaration : 
    STAT IDENTIFIER EQL stat_expression SEMICOLON
;

/* stats don't have access to the full expression grammar, just a part of it */
stat_expression : 
    stat_mult_expr
    | stat_expression PLUS stat_mult_expr
    | stat_expression MIN stat_mult_expr
    ;

stat_mult_expr : 
    stat_atom_expr
    | stat_mult_expr MULT stat_atom_expr
    | stat_mult_expr DIV stat_atom_expr
    | stat_mult_expr MOD stat_atom_expr
    ;

stat_atom_expr : 
    IDENTIFIER
    | NUMBER
    | OPAREN stat_expression CPAREN
    ;

jump_statement : 
    RETURN expression SEMICOLON
    | STOPDO SEMICOLON
;

/**ASSIGNMENT_STATEMENT**/ 
assignment_statement :
    IDENTIFIER assignment_operator expression SEMICOLON
;

/**ITERATION_STATEMENT**/
iteration_statement :
	DO COLON body_statement_list END
	| DO expression TIMES COLON body_statement_list END 
	| FOREACH IDENTIFIER IN expression COLON body_statement_list END 
;

/* operators of assignment */
assignment_operator : 
    EQL
    | PLUSEQL
    | MINEQL 
    | MULTEQL
    | DIVEQL 
    | MODEQL 
;

/**ACTIVATE_STATEMENT**/ 
activate_statement :
    ACTIVATE IDENTIFIER SEMICOLON
;


/**EXPRESSION_STATEMENT**/
expression_statement : 
    SEMICOLON
    | expression SEMICOLON
;


/* EXPRESSION */
expression : logical_or_expression 
;
/* LOGICAL */
logical_or_expression :
	logical_and_expression
    | logical_or_expression OR logical_and_expression
;

logical_and_expression :
	logical_not_expression
    | logical_and_expression AND logical_not_expression
;

logical_not_expression : 
    comparison_expression
    | NOT logical_not_expression
;


/* COMPARISON */
comparison_expression : addition_expression
                      | comparison_expression IS   addition_expression
                      | comparison_expression ISNOT addition_expression 
                      | comparison_expression GT    addition_expression
                      | comparison_expression LT    addition_expression
                      | comparison_expression GTE   addition_expression
                      | comparison_expression LTE   addition_expression
;

/* ARITHMETIC */
addition_expression : multiplication_expression
                    | addition_expression PLUS multiplication_expression
                    | addition_expression MIN multiplication_expression
;

multiplication_expression : unary_expression
			  | multiplication_expression MULT unary_expression
              | multiplication_expression DIV unary_expression
              | multiplication_expression MOD unary_expression
;

/* UNARY */
unary_expression : 
    postfix_expression
    | PPLUS unary_expression
    | MMIN unary_expression
    | primary_expression FROM unary_expression
    | ANY unary_expression
;

/* POSTFIX */
postfix_expression : 
    primary_expression
    | postfix_expression APOSTROPHEESS IDENTIFIER 
    | postfix_expression WHERE OPAREN expression CPAREN
    | postfix_expression PPLUS
    | postfix_expression MMIN 
;

/* PRIMARY */
primary_expression : 
    atom_expression
	| function_call
;

/* FUNCTION_CALL */
function_call : IDENTIFIER OPAREN CPAREN
              | IDENTIFIER OPAREN argument_list CPAREN
;

argument_list : 
    expression
    | argument_list COMMA expression
;


/* ATOM_EXPRESSION */
atom_expression : 
    STRING
    | IDENTIFIER
    | NUMBER
    | OPAREN expression CPAREN
    | list_initializer
    | SELF
;

list_initializer : 
    OSQUARE argument_list CSQUARE 
    | type OSQUARE CSQUARE
    ;

%%
