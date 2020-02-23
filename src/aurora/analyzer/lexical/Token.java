package aurora.analyzer.lexical;

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
    ENDLOOP("endloop"),
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

    public static List<String> getValues(){
        return Arrays.stream(Token.values())
                .map(Token::getName)
                .collect(Collectors.toList());
    }
}
