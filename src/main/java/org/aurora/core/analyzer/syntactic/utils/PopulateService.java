package org.aurora.core.analyzer.syntactic.utils;

import org.aurora.core.analyzer.syntactic.log.LogSyntactic;
import org.aurora.core.lang.Language;
import org.aurora.core.lang.NonTerminal;
import org.aurora.core.lang.Symbol;
import org.aurora.core.lang.Token;

import java.util.List;
import java.util.Stack;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

/*
 * @project org.aurora
 * @author Gabriel Honda on 08/03/2020
 */
public class PopulateService {

    public static List<List<Language>> commandSequenceTable() {
        return asList(
//              0 - <aurora> ::= init_program <recursive_statement> close_program
                asList(Token.INIT, NonTerminal.RECURSIVE_STATEMENT, Token.CLOSE),
//              1 - <aurora> ::= î
                List.of(),
//              2 - <recursive_statement> ::= <statement> <recursive_statement>
                asList(NonTerminal.STATEMENT, NonTerminal.RECURSIVE_STATEMENT),
//              3 - <recursive_statement> ::= î
                List.of(),
//              4 - <statement> ::= <write>
                singletonList(NonTerminal.WRITE),
//              5 - <statement> ::= <read>
                singletonList(NonTerminal.READ),
//              6 - <statement> ::= <conditional>
                singletonList(NonTerminal.CONDITIONAL),
//              7 - <statement> ::= <loop>
                singletonList(NonTerminal.LOOP),
//              8 - <statement> ::= <declaration>
                singletonList(NonTerminal.DECLARATION),
//              9 - <statement> ::= <assignment>
                singletonList(NonTerminal.ASSIGNMENT),
//              10 - <write> ::= write "(" <any_expression> ")" ";"
                asList(Token.WRITE, Symbol.OPEN_PARENTHESIS, NonTerminal.ANY_EXPRESSION, Symbol.CLOSE_PARENTHESIS, Symbol.SEMICOLON),
//              11 - <read> ::= read "(" id ")" ";"
                asList(Token.READ, Symbol.OPEN_PARENTHESIS, Token.ID, Symbol.CLOSE_PARENTHESIS, Symbol.SEMICOLON),
//              12 - <conditional> ::= <if> <else> endif
                asList(NonTerminal.IF, NonTerminal.ELSE, Token.ENDIF),
//              13 - <if> ::= if "(" <logical_expression> ")" <recursive_statement>
                asList(Token.IF, Symbol.OPEN_PARENTHESIS, NonTerminal.LOGICAL_EXPRESSION,
                       Symbol.CLOSE_PARENTHESIS, NonTerminal.RECURSIVE_STATEMENT),
//              14 - <else> ::= else <recursive_statement>
                asList(Token.ELSE, NonTerminal.RECURSIVE_STATEMENT),
//              15 - <else> ::= î
                List.of(),
//              16 - <loop> ::= <while> endloop
                asList(NonTerminal.WHILE, Token.END_LOOP),
//              17 - <while> ::= loop "(" <logical_expression> ")" <recursive_statement>
                asList(Token.LOOP, Symbol.OPEN_PARENTHESIS, NonTerminal.LOGICAL_EXPRESSION,
                       Symbol.CLOSE_PARENTHESIS, NonTerminal.RECURSIVE_STATEMENT),
//              18 - <declaration> ::= var id ";"
                asList(Token.VAR, Token.ID, Symbol.SEMICOLON),
//              19 - <assignment> ::= id "=" <basic_expression> ";"
                asList(Token.ID, Symbol.EQUALS, NonTerminal.BASIC_EXPRESSION, Symbol.SEMICOLON),
//              20 - <basic_expression> ::= id <basic_operator>
                asList(Token.ID, NonTerminal.BASIC_OPERATOR),
//              21 - <basic_expression> ::= number <basic_operator>
                asList(Token.NUMBER, NonTerminal.BASIC_OPERATOR),
//              22 - <basic_expression> ::= "+" <basic_expression>
                asList(Symbol.PLUS, NonTerminal.BASIC_EXPRESSION),
//              23 - <basic_expression> ::= "-" <basic_expression>
                asList(Symbol.MINUS, NonTerminal.BASIC_EXPRESSION),
//              24 - <basic_expression> ::= "(" <basic_expression> ")" <basic_operator>
                asList(Symbol.OPEN_PARENTHESIS, NonTerminal.BASIC_EXPRESSION, Symbol.CLOSE_PARENTHESIS, NonTerminal.BASIC_OPERATOR),
//              25 - <basic_operator> ::= "+" <basic_expression>
                asList(Symbol.PLUS, NonTerminal.BASIC_EXPRESSION),
//              26 - <basic_operator> ::= "-" <basic_expression>
                asList(Symbol.MINUS, NonTerminal.BASIC_EXPRESSION),
//              27 - <basic_operator> ::= "*" <basic_expression>
                asList(Symbol.ASTERISK, NonTerminal.BASIC_EXPRESSION),
//              28 - <basic_operator> ::= "/" <basic_expression>
                asList(Symbol.FORWARD_SLASH, NonTerminal.BASIC_EXPRESSION),
//              29 -,<basic_operator> ::= î
                List.of(),
//              30 - <string_expression> ::= string_literal
                singletonList(Token.STRING),
//              31 - <id_or_number> ::= id
                singletonList(Token.ID),
//              32 - <id_or_number> ::= number
                singletonList(Token.NUMBER),
//              33 - <any_expression> ::= <basic_expression>
                singletonList(NonTerminal.BASIC_EXPRESSION),
//              34 - <any_expression> ::= <string_expression>
                singletonList(NonTerminal.STRING_EXPRESSION),
//              35 - <logical_operator> ::= "<"
                singletonList(Symbol.LESS_THAN),
//              36 - <logical_operator> ::= ">"
                singletonList(Symbol.GREATER_THAN),
//              37 - <logical_expression> ::= <id_or_number> <logical_operator> <id_or_number>
                asList(NonTerminal.ID_OR_NUMBER, NonTerminal.LOGICAL_OPERATOR, NonTerminal.ID_OR_NUMBER)
        );
    }

    public static List<List<Integer>> parseTable() {
        return asList(
                asList(1,-1,-1,-1,-1,0,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1  ),
                asList(-1,2,-1,-1,-1,-1,3,2,3,3,2,3,2,2,2,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1          ),
                asList(-1,9,-1,-1,-1,-1,-1,6,-1,-1,7,-1,4,5,8,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1      ),
                asList(-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,10,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1),
                asList(-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,11,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1),
                asList(-1,-1,-1,-1,-1,-1,-1,12,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1),
                asList(-1,-1,-1,-1,-1,-1,-1,13,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1),
                asList(-1,-1,-1,-1,-1,-1,-1,-1,14,15,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1),
                asList(-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,16,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1),
                asList(-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,17,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1),
                asList(-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,18,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1),
                asList(-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1),
                asList(-1,20,-1,21,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,24,-1,-1,-1,22,23,-1,-1,-1,-1),
                asList(-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,29,29,-1,25,26,27,28,-1,-1),
                asList(-1,-1,-1,-1,30,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1),
                asList(-1,33,-1,33,34,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,33,-1,-1,-1,33,33,-1,-1,-1,-1),
                asList(-1,37,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1),
                asList(-1,31,-1,32,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1),
                asList(-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,35,36)
        );
    }

    public static Stack<Language> initializeStack() {
        var resource = new Stack<Language>();
        resource.push(Token.$);
        LogSyntactic.log("token " + resource.peek() + " was pushed to the stack.");
        resource.push(NonTerminal.AURORA);
        LogSyntactic.log("non terminal " + resource.peek() + " was pushed to the stack.");
        return resource;
    }
}
