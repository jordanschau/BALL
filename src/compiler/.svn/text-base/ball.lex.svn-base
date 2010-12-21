/*
 * COMS W4115 PROGRAMMING LANGUAGES AND TRANSLATORS FALL 2009
 * Team llamamelon - BALL language
 * Authors: Cipta Herwana, Daniel Lasry, Sam Lee, Nathan Miller, Jordan Schau
 * ball.lex - Lexer specification of the BALL language (using JFlex)
 */

package compiler;
import lexer.*;
import codegen.*;

%%

%byaccj
%public
%line
%column

/* 
 * =========================================================
 *  This section goes straight into Yylex.java untranslated
 * =========================================================
 */

%{ // open escape section

    /* store a reference to the parser object */
    private Parser yyparser;

    /* store a reference to the symbol table */
    private SymbolTable table;

    /* 
     * constructor initializes both the parser and table references.
     */
    public Yylex(java.io.Reader source, Parser yyparser, SymbolTable table) {
        this(source);
        this.yyparser = yyparser;
        this.table = table;
    }

    public int getLine() {
	return yyline;
    }
	

%} // close escape section

/* 
 * =====================
 *  Regular Expressions
 * =====================
 */

LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]
WhiteSpace     = {LineTerminator} | [ \t\f]

/* Comments */
Comment                 = {TraditionalComment}	
                        | {EndOfLineComment} 
                        | {DocumentationComment}

TraditionalComment      = "/*" [^*] ~"*/" | "/*" "*"+ "/"
EndOfLineComment        = "//" {InputCharacter}* {LineTerminator}
DocumentationComment    = "/**" {CommentContent} "*"+ "/"
CommentContent          = ( [^*] | \*+ [^/*] )*

/* Constants */
StringConst	        = \"([^\"\\]|\\.)*\"
NumericConst		= 0 | [-]?[0-9]*[.]?[0-9]+

/* Types */
Primitive			= number|string|team|player|stat|nothing


/* Identifiers */
Identifier              = [:jletterdigit:]*[:jletter:][:jletterdigit:]*

%%

/* 
 * ======================
 *  Lexer (Syntax) Rules
 * ======================
 */
 
 /*
  * FILE NEEDS TO HAVE A BLANK LINE AT THE END!!!!!!!!  OKAY?!!!!!!!!!!!!! 
  */

";"     { return Parser.SEMICOLON; }

","     { return Parser.COMMA; }

"and"	{ return Parser.AND; }

"or" 	{ return Parser.OR; }

"not" 	{ return Parser.NOT; }

"="	{ return Parser.EQL; }

"+" 	{ return Parser.PLUS; }

"-" 	{ return Parser.MIN; }

"*" 	{ return Parser.MULT; }

"/" 	{ return Parser.DIV; }

"%" 	{ return Parser.MOD; }

"++" 	{ return Parser.PPLUS; }

"--" 	{ return Parser.MMIN; }

"is"	{ return Parser.IS; }
	
"isnot" { return Parser.ISNOT; }

">" 	{ return Parser.GT; }

"<" 	{ return Parser.LT; }

">=" 	{ return Parser.GTE; }

"<=" 	{ return Parser.LTE; }

"+="    { return Parser.PLUSEQL; }

"-="    { return Parser.MINEQL; }

"*="    { return Parser.MULTEQL; }

"/="    { return Parser.DIVEQL; }

"%="    { return Parser.MODEQL; }

"("	{ return Parser.OPAREN; }

")"	{ return Parser.CPAREN; }

"[" 	{ return Parser.OSQUARE; }

"]" 	{ return Parser.CSQUARE; }

"is"	{ return Parser.IS; }

":"     { return Parser.COLON; }

"'s"	{ /* got an apostrophe-s, notify parser */
			//System.err.println("lexer: found ''s'");
			return Parser.APOSTROPHEESS;			
		}

playball 	{ return Parser.PLAYBALL; }

any             { return Parser.ANY; }

list            { return Parser.LIST; }

of              { return Parser.OF; }

from            { return Parser.FROM; }

if		{ /* got an if statement, notify parser */
		    //System.err.println("lexer: found 'if'");
		    yyparser.yylval = new ParserVal(Keyword.ifKwd);
                    return Parser.IF;
		}

then		{
		    yyparser.yylval = new ParserVal(Keyword.then);
                    return Parser.THEN;
		}

else		{/* got an else statement, notify parser */
		    //System.err.println("lexer: found 'else'");
		    yyparser.yylval = new ParserVal(Keyword.elseKwd);
                    return Parser.ELSE;
		}

do           	{ /* got a do statement, notify parser */
                    //System.err.println("lexer: found 'do'");
                    yyparser.yylval =  new ParserVal(Keyword.mydo);
                    return Parser.DO; // TODO: couple return with table reference
                } 
            
					
times			{ /* got a times statement, notify parser */
					//System.err.println("lexer: found 'times'");
					yyparser.yylval = new ParserVal(Keyword.times);
					return Parser.TIMES;
				}
				
stopdo			{ /* got a stopdo statement, notify parser */
					//System.err.println("lexer: found 'stopdo'");
					yyparser.yylval = new ParserVal(Keyword.stopdo);
					return Parser.STOPDO;
				}

foreach			{ /* got a foreach loop */
					//System.err.println("lexer found a foreach");
					yyparser.yylval = new ParserVal(Keyword.foreach);
					return Parser.FOREACH;
				}
				
in				{ /* got an in statement */
					//System.err.println("lexer found an 'in'");
					yyparser.yylval = new ParserVal(Keyword.in);
					return Parser.IN;
				}

print           { /* got a print statement, notify parser */
                    //System.err.println("lexer: found 'print'");
                    yyparser.yylval =  new ParserVal(Keyword.print);
                    return Parser.PRINT; // TODO: couple return with table reference
                }

activate        { /* got anactivation statement, notify parser */
                    //System.err.println("lexer: found 'activate'");
                    yyparser.yylval =  new ParserVal(Keyword.activate);
                    return Parser.ACTIVATE;
                }

function        {
                    yyparser.yylval = new ParserVal(Keyword.function);
                    return Parser.FUNCTION;
                }

simfunction     {
                    yyparser.yylval = new ParserVal(Keyword.simfunction);
                    return Parser.SIMFUNCTION;
                }

stat            {
                    //System.err.println("lexer: found 'stat' keyword.");
                    yyparser.yylval = new ParserVal(Keyword.stat);
                    return Parser.STAT;
                }

return          {
                    yyparser.yylval = new ParserVal(Keyword.ret);
                    return Parser.RETURN;
                }

end             {
                    yyparser.yylval = new ParserVal(Keyword.end);
                    return Parser.END;
                }

returns         {
                    yyparser.yylval = new ParserVal(Keyword.returns);
                    return Parser.RETURNS;
                }

where           {
                    //System.err.println("lexer: 'where' keyword");
                    yyparser.yylval = new ParserVal(Keyword.where);
                    return Parser.WHERE;
                }

item            {
                    yyparser.yylval = new ParserVal(Keyword.self);
                    return Parser.SELF;
                }

{StringConst}	{ /* got a string, add to sym. tbl. and notify parser */
                    // TODO: add symbol table addition code here
		            //System.err.println("lexer: found a String Const");
                    StringConst s = new StringConst(yytext());
                    //table.putEntry(s, null);
                    yyparser.yylval = new ParserVal(s);
                    return Parser.STRING; // TODO: couple return with table reference
                }

{NumericConst}	{ /* got a number, add to sym. tbl. and notify parser */
		            NumericConst n = new NumericConst(yytext());
                    //table.putEntry(n, null);
                    yyparser.yylval = new ParserVal(n);
                    //System.err.println("lexer: found a Numeric Const " + n);
                    return Parser.NUMBER; // TODO: couple return with table reference
                }

{Primitive}		{   /* got a type, notify parser */
		            Type t = new Type(yytext());
		            yyparser.yylval = new ParserVal(t);
		            //System.err.println("lexer: found type " + t);
		            return Parser.PRIMITIVE;
		        }

{Identifier}	{   /* got an identifier, notify parser */
		    Identifier i = new Identifier(yytext());
		    yyparser.yylval = new ParserVal(i);
		    //System.err.println("lexer: found identifier \"" + i.getID() + "\"");
            
            // add the identifier to the symbol table in case we need it later
            //table.putEntry(i, null);
		    return Parser.IDENTIFIER;
		}

{Comment} 		{ //System.err.println("lexer: found comment")
					; 
				}
{WhiteSpace}	{ /* ignore */ }

