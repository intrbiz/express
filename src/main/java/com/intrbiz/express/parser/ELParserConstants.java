/* Generated By:JavaCC: Do not edit this line. ELParserConstants.java */
package com.intrbiz.express.parser;


/**
 * Token literal values and constants.
 * Generated by org.javacc.parser.OtherFilesGen#start()
 */
public interface ELParserConstants {

  /** End of File. */
  int EOF = 0;
  /** RegularExpression Id. */
  int NON_EXPRESSION_TEXT = 1;
  /** RegularExpression Id. */
  int START_EXPRESSION = 2;
  /** RegularExpression Id. */
  int START_SCRIPT = 3;
  /** RegularExpression Id. */
  int DIGITS = 9;
  /** RegularExpression Id. */
  int DOT = 10;
  /** RegularExpression Id. */
  int ADD = 11;
  /** RegularExpression Id. */
  int SUB = 12;
  /** RegularExpression Id. */
  int MUL = 13;
  /** RegularExpression Id. */
  int DIV = 14;
  /** RegularExpression Id. */
  int DIV1 = 15;
  /** RegularExpression Id. */
  int POW = 16;
  /** RegularExpression Id. */
  int MOD = 17;
  /** RegularExpression Id. */
  int MOD1 = 18;
  /** RegularExpression Id. */
  int EQ = 19;
  /** RegularExpression Id. */
  int EQ1 = 20;
  /** RegularExpression Id. */
  int NE = 21;
  /** RegularExpression Id. */
  int NE1 = 22;
  /** RegularExpression Id. */
  int GT = 23;
  /** RegularExpression Id. */
  int GT1 = 24;
  /** RegularExpression Id. */
  int LT = 25;
  /** RegularExpression Id. */
  int LT1 = 26;
  /** RegularExpression Id. */
  int GTEQ = 27;
  /** RegularExpression Id. */
  int GTEQ1 = 28;
  /** RegularExpression Id. */
  int LTEQ = 29;
  /** RegularExpression Id. */
  int LTEQ1 = 30;
  /** RegularExpression Id. */
  int OR = 31;
  /** RegularExpression Id. */
  int OR1 = 32;
  /** RegularExpression Id. */
  int AND = 33;
  /** RegularExpression Id. */
  int AND1 = 34;
  /** RegularExpression Id. */
  int NOT = 35;
  /** RegularExpression Id. */
  int NOT1 = 36;
  /** RegularExpression Id. */
  int STCOMP = 37;
  /** RegularExpression Id. */
  int EDCOMP = 38;
  /** RegularExpression Id. */
  int ARGSEP = 39;
  /** RegularExpression Id. */
  int STARR = 40;
  /** RegularExpression Id. */
  int EDARR = 41;
  /** RegularExpression Id. */
  int ASSIGNMENT = 42;
  /** RegularExpression Id. */
  int ADDASSIGNMENT = 43;
  /** RegularExpression Id. */
  int SUBASSIGNMENT = 44;
  /** RegularExpression Id. */
  int MULASSIGNMENT = 45;
  /** RegularExpression Id. */
  int DIVASSIGNMENT = 46;
  /** RegularExpression Id. */
  int POWASSIGNMENT = 47;
  /** RegularExpression Id. */
  int MODASSIGNMENT = 48;
  /** RegularExpression Id. */
  int INCASSIGNMENT = 49;
  /** RegularExpression Id. */
  int DECASSIGNMENT = 50;
  /** RegularExpression Id. */
  int NEW = 51;
  /** RegularExpression Id. */
  int RETURN = 52;
  /** RegularExpression Id. */
  int IF = 53;
  /** RegularExpression Id. */
  int ELSE = 54;
  /** RegularExpression Id. */
  int IN = 55;
  /** RegularExpression Id. */
  int FOR = 56;
  /** RegularExpression Id. */
  int WHILE = 57;
  /** RegularExpression Id. */
  int BREAK = 58;
  /** RegularExpression Id. */
  int EXPORT = 59;
  /** RegularExpression Id. */
  int INCLUDE = 60;
  /** RegularExpression Id. */
  int FUNCTION = 61;
  /** RegularExpression Id. */
  int LACTION = 62;
  /** RegularExpression Id. */
  int NLINT = 63;
  /** RegularExpression Id. */
  int NLLONG = 64;
  /** RegularExpression Id. */
  int NLFLOAT = 65;
  /** RegularExpression Id. */
  int NLDOUBLE = 66;
  /** RegularExpression Id. */
  int LBOOLEAN = 67;
  /** RegularExpression Id. */
  int LNULL = 68;
  /** RegularExpression Id. */
  int LENTITY = 69;
  /** RegularExpression Id. */
  int HEXCHAR = 70;
  /** RegularExpression Id. */
  int ESCAPECHAR = 71;
  /** RegularExpression Id. */
  int UESCAPECHAR = 72;
  /** RegularExpression Id. */
  int LSTRING = 73;
  /** RegularExpression Id. */
  int END_EXPRESSION = 74;
  /** RegularExpression Id. */
  int QUOTE = 75;
  /** RegularExpression Id. */
  int STARTBLOCK = 76;
  /** RegularExpression Id. */
  int ENDBLOCK = 77;
  /** RegularExpression Id. */
  int STATEMENT = 78;
  /** RegularExpression Id. */
  int END_SCRIPT = 79;
  /** RegularExpression Id. */
  int ENDQUOTE = 81;
  /** RegularExpression Id. */
  int CHAR = 82;
  /** RegularExpression Id. */
  int CNTRLESC = 83;
  /** RegularExpression Id. */
  int HEX = 85;
  /** RegularExpression Id. */
  int HEXESC = 86;

  /** Lexical state. */
  int DEFAULT = 0;
  /** Lexical state. */
  int IN_EXPRESSION = 1;
  /** Lexical state. */
  int IN_SCRIPT = 2;
  /** Lexical state. */
  int STRINGSTATE = 3;
  /** Lexical state. */
  int ESCSTATE = 4;
  /** Lexical state. */
  int HEXSTATE = 5;

  /** Literal token values. */
  String[] tokenImage = {
    "<EOF>",
    "<NON_EXPRESSION_TEXT>",
    "\"#{\"",
    "\"<#\"",
    "\" \"",
    "\"\\t\"",
    "\"\\n\"",
    "\"\\r\"",
    "\"\\r\\n\"",
    "<DIGITS>",
    "\".\"",
    "\"+\"",
    "\"-\"",
    "\"*\"",
    "\"/\"",
    "\"div\"",
    "\"^\"",
    "\"%\"",
    "\"mod\"",
    "\"==\"",
    "\"eq\"",
    "\"!=\"",
    "\"ne\"",
    "\">\"",
    "\"gt\"",
    "\"<\"",
    "\"lt\"",
    "\">=\"",
    "\"gteq\"",
    "\"<=\"",
    "\"lteq\"",
    "\"||\"",
    "\"or\"",
    "\"&&\"",
    "\"and\"",
    "\"!\"",
    "\"not\"",
    "\"(\"",
    "\")\"",
    "\",\"",
    "\"[\"",
    "\"]\"",
    "\"=\"",
    "\"+=\"",
    "\"-=\"",
    "\"*=\"",
    "\"/=\"",
    "\"^=\"",
    "\"%=\"",
    "\"++\"",
    "\"--\"",
    "\"new\"",
    "\"return\"",
    "\"if\"",
    "\"else\"",
    "\"in\"",
    "\"for\"",
    "\"while\"",
    "\"break\"",
    "\"export\"",
    "\"include\"",
    "\"function\"",
    "\"@\"",
    "<NLINT>",
    "<NLLONG>",
    "<NLFLOAT>",
    "<NLDOUBLE>",
    "<LBOOLEAN>",
    "<LNULL>",
    "<LENTITY>",
    "<HEXCHAR>",
    "<ESCAPECHAR>",
    "<UESCAPECHAR>",
    "<LSTRING>",
    "\"}\"",
    "\"\\\"\"",
    "\"{\"",
    "\"}\"",
    "\";\"",
    "\"#>\"",
    "\"\\\\\"",
    "<ENDQUOTE>",
    "<CHAR>",
    "<CNTRLESC>",
    "\"u\"",
    "<HEX>",
    "<HEXESC>",
  };

}
