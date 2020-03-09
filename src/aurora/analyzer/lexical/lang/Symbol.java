package aurora.analyzer.lexical.lang;

import aurora.analyzer.lexical.interfaces.Terminal;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/*
 * @project aurora
 * @author Gabriel Honda on 23/02/2020
 */
public enum Symbol implements Terminal {

    OPEN_PARENTHESIS("("),
    CLOSE_PARENTHESIS(")"),
    SEMICOLON(";"),
    EQUALS("="),
    PLUS("+"),
    MINUS("-"),
    ASTERISK("*"),
    FORWARD_SLASH("/"),
    GREATER_THAN(">"),
    LESS_THAN("<");

    private final String symbol;

    Symbol(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String getName() {
        return symbol;
    }

    public static Symbol toEnum(String str) {
        for(Symbol symbol : Symbol.values()) {
            if( symbol.getName().equals(str)) {
                return symbol;
            }
        }
        throw new IllegalArgumentException("Este simbolo '"+ str +"' nao pode ser convertido.");
    }

    public static List<String> getValues(){
        return Arrays.stream(Symbol.values())
                .map(Symbol::getName)
                .collect(Collectors.toList());
    }
}
