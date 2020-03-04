package aurora.analyzer.lexical.tokens;

import aurora.analyzer.lexical.interfaces.IToken;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/*
 * @project aurora
 * @author Gabriel Honda on 22/02/2020
 */
public enum Token implements IToken {

    FINAL("$"),
    ID("id"),
    NUMBER("number"),
    STRING("string_literal"),

    INIT("init_program"),
    CLOSE("close_program"),
    IF("if"),
    ELSE("else"),
    ENDIF("endif"),
    LOOP("loop"),
    END_LOOP("endloop"),
    WRITE("write"),
    READ("read"),
    VAR("var");

    private String token;

    Token(String token) {
        this.token = token;
    }

    @Override
    public String getName() {
        return token;
    }

    public static Token toEnum(String str) {
        for(Token token : Token.values()) {
            if( token.getName().equals(str)) {
                return token;
            }
        }
        throw new IllegalArgumentException("Este simbolo '"+ str +"' nao pode ser convertido.");
    }

    public static List<String> getValues(){
        return Arrays.stream(Token.values())
                .map(Token::getName)
                .collect(Collectors.toList());
    }
}
