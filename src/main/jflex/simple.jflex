package org.example;
import java.io.IOException;
import java_cup.runtime.Symbol;

%%
%public
%class JsonLexer
%unicode
%cup
%type Symbol

%eofval{
    return new Symbol(sym.EOF);
%eofval}

// Tokens
LEFT_BRACE    = "{"
RIGHT_BRACE   = "}"
LEFT_BRACKET  = "["
RIGHT_BRACKET = "]"
COLON         = ":"
COMMA         = ","
STRING        = \"([^\"\\]|\\[\"\\/bfnrt]|\\u[0-9a-fA-F]{4})*\"
NUMBER        = -?[0-9]+(\\.[0-9]+)?
TRUE          = "true"
FALSE         = "false"
NULL          = "null"
SPACES        = [ \t\n\r]

%%

// Match caracteres estructura json
{LEFT_BRACE}     { return new Symbol(sym.LEFT_BRACE, yytext()); }
{RIGHT_BRACE}    { return new Symbol(sym.RIGHT_BRACE, yytext()); }
{LEFT_BRACKET}   { return new Symbol(sym.LEFT_BRACKET, yytext()); }
{RIGHT_BRACKET}  { return new Symbol(sym.RIGHT_BRACKET, yytext()); }
{COLON}          { return new Symbol(sym.COLON, yytext()); }
{COMMA}          { return new Symbol(sym.COMMA, yytext()); }
{NUMBER}         { return new Symbol(sym.NUMBER, yytext()); }
{STRING}         { return new Symbol(sym.STRING, yytext().substring(1, yytext().length()-1)); }
{TRUE}           { return new Symbol(sym.TRUE, yytext()); }
{FALSE}          { return new Symbol(sym.FALSE, yytext()); }
{NULL}           { return new Symbol(sym.NULL, yytext()); }
{SPACES}         { /* Ignorar espacios en blanco */ }

. { throw new Error("Caracter inesperado: " + yytext()); }
