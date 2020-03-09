package aurora.analyzer.syntactic.utils;

import aurora.lang.Language;
import aurora.lang.NonTerminal;
import aurora.lang.Symbol;
import aurora.lang.Token;

import java.util.List;
import java.util.Stack;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

/*
 * @project aurora
 * @author Gabriel Honda on 08/03/2020
 */
public class PopulateService {

    public static List<List<Language>> stackTable() {
        return asList(
//                <aurora> ::= init_program <recursive_statement> close_program
                asList(Token.INIT, NonTerminal.RECURSIVE_STATEMENT, Token.CLOSE),
//                <aurora> ::= î
                singletonList(Token.EMPTY),
//                <recursive_statement> ::= <statement> <recursive_statement>
                asList(NonTerminal.STATEMENT, NonTerminal.RECURSIVE_STATEMENT),
//                <recursive_statement> ::= î
                singletonList(Token.EMPTY),
//                <statement> ::= <write>
                singletonList(NonTerminal.WRITE),
//                <statement> ::= <read>
                singletonList(NonTerminal.READ),
//                <statement> ::= <conditional>
                singletonList(NonTerminal.CONDITIONAL),
//                <statement> ::= <loop>
                singletonList(NonTerminal.LOOP),
//                <statement> ::= <declaration>
                singletonList(NonTerminal.DECLARATION),
//                <statement> ::= <assignment>
                singletonList(NonTerminal.ASSIGNMENT),
//                <write> ::= write "(" <any_expression> ")" ";"
                asList(Token.WRITE, Symbol.OPEN_PARENTHESIS, NonTerminal.ANY_EXPRESSION, Symbol.CLOSE_PARENTHESIS, Symbol.SEMICOLON),
//                <read> ::= read "(" id ")" ";"
                asList(Token.READ, Symbol.OPEN_PARENTHESIS, Token.ID, Symbol.CLOSE_PARENTHESIS, Symbol.SEMICOLON),
//                <conditional> ::= <if> <else> endif
                asList(NonTerminal.IF, NonTerminal.ELSE, Token.ENDIF),
//                <if> ::= if "(" <basic_expression> ")" <recursive_statement>
                asList(Token.IF, Symbol.OPEN_PARENTHESIS, NonTerminal.BASIC_EXPRESSION, Symbol.CLOSE_PARENTHESIS, NonTerminal.RECURSIVE_STATEMENT),
//                <else> ::= else <recursive_statement>
                asList(Token.ELSE, NonTerminal.RECURSIVE_STATEMENT),
//                <else> ::= î
                singletonList(Token.EMPTY),
//                <loop> ::= <while> endloop
                asList(NonTerminal.WHILE, Token.END_LOOP),
//                <while> ::= loop "(" <basic_expression> ")" <recursive_statement>
                asList(Token.LOOP, Symbol.OPEN_PARENTHESIS, NonTerminal.BASIC_EXPRESSION, Symbol.CLOSE_PARENTHESIS, NonTerminal.RECURSIVE_STATEMENT),
//                <declaration> ::= var id ";"
                asList(Token.VAR, Token.ID, Symbol.SEMICOLON),
//                <assignment> ::= id "=" <basic_expression> ";"
                asList(Token.ID, Symbol.EQUALS, NonTerminal.BASIC_EXPRESSION, Symbol.SEMICOLON),
//                <basic_expression> ::= id <basic_operator>
                asList(Token.ID, NonTerminal.BASIC_OPERATOR),
//                <basic_expression> ::= number <basic_operator>
                asList(Token.NUMBER, NonTerminal.BASIC_OPERATOR),
//                <basic_expression> ::= "+" <basic_expression>
                asList(Symbol.PLUS, NonTerminal.BASIC_EXPRESSION),
//                <basic_expression> ::= "-" <basic_expression>
                asList(Symbol.MINUS, NonTerminal.BASIC_EXPRESSION),
//                <basic_expression> ::= "(" <basic_expression> ")" <basic_operator>
                asList(Symbol.OPEN_PARENTHESIS, NonTerminal.BASIC_EXPRESSION, Symbol.CLOSE_PARENTHESIS, NonTerminal.BASIC_OPERATOR),
//                <basic_operator> ::= "+" <basic_expression>
                asList(Symbol.PLUS, NonTerminal.BASIC_EXPRESSION),
//                <basic_operator> ::= "-" <basic_expression>
                asList(Symbol.MINUS, NonTerminal.BASIC_EXPRESSION),
//                <basic_operator> ::= "*" <basic_expression>
                asList(Symbol.ASTERISK, NonTerminal.BASIC_EXPRESSION),
//                <basic_operator> ::= "/" <basic_expression>
                asList(Symbol.FORWARD_SLASH, NonTerminal.BASIC_EXPRESSION),
//                <basic_operator> ::= "<" <basic_expression>
                asList(Symbol.LESS_THAN, NonTerminal.BASIC_EXPRESSION),
//                <basic_operator> ::= ">" <basic_expression>
                asList(Symbol.GREATER_THAN, NonTerminal.BASIC_EXPRESSION),
//                <basic_operator> ::= î
                singletonList(Token.EMPTY),
//                <string_expression> ::= string_literal
                singletonList(Token.STRING),
//                <any_expression> ::= <basic_expression>
                singletonList(NonTerminal.BASIC_EXPRESSION),
//                <any_expression> ::= <string_expression>
                singletonList(NonTerminal.STRING_EXPRESSION)
        );
    }

    public static List<List<Integer>> parseTable() {
        return asList(
            asList( 1, -1, -1, -1, -1,  0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1),
            asList(-1,  2, -1, -1, -1, -1,  3,  2,  3,  3,  2,  3,  2,  2,  2, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1),
            asList(-1,  9, -1, -1, -1, -1, -1,  6, -1, -1,  7, -1,  4,  5,  8, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1),
            asList(-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 10, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1),
            asList(-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 11, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1),
            asList(-1, -1, -1, -1, -1, -1, -1, 12, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1),
            asList(-1, -1, -1, -1, -1, -1, -1, 13, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1),
            asList(-1, -1, -1, -1, -1, -1, -1, -1, 14, 15, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1),
            asList(-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 16, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1),
            asList(-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 17, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1),
            asList(-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 18, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1),
            asList(-1, 19, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1),
            asList(-1, 20, -1, 21, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 24, -1, -1, -1, 22, 23, -1, -1, -1, -1),
            asList(-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 31, 31, -1, 25, 26, 27, 28, 29, 30),
            asList(-1, -1, -1, -1, 32, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1),
            asList(-1, 33, -1, 33, 34, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 33, -1, -1, -1, 33, 33, -1, -1, -1, -1)
        );
    }

    public static void stack(Stack<Language> stack){
        stack.push(Token.FINAL);
        stack.push(NonTerminal.INIT);
    }
}
