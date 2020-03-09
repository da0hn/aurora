package aurora.lang;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/*
 * @project aurora
 * @author Gabriel Honda on 22/02/2020
 */
public enum Token implements Terminal {

    FINAL(0,"$"),
    ID(1,"id"),
    EMPTY(2,"î"),
    NUMBER(3,"number"),
    STRING(4,"string_literal"),
    INIT(5,"init_program"),

    CLOSE(6,"close_program"),
    IF(7,"if"),
    ELSE(8,"else"),
    ENDIF(9,"endif"),
    LOOP(10,"loop"),
    END_LOOP(11,"endloop"),
    WRITE(12,"write"),
    READ(13,"read"),
    VAR(14,"var");

    private String token;
    private int index;

    Token(int index, String token) {
        this.token = token;
    }

    @Override
    public String getName() {
        return token;
    }

    @Override
    public int getIndex() {
        return index;
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
