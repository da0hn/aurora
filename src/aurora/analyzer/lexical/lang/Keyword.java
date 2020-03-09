package aurora.analyzer.lexical.lang;

import aurora.analyzer.lexical.interfaces.IToken;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/*
 * @project aurora
 * @author Gabriel Honda on 23/02/2020
 */
public enum Keyword implements IToken {

    INIT("aurora::init"),
    CLOSE("aurora::close"),
    IF("au::if"),
    ENDIF("au::endif"),
    ELSE("au::else"),
    LOOP("au::loop"),
    END_LOOP("au::endloop"),
    WRITE("au::write"),
    READ("au::read"),
    VAR("au::var");

    private final String keyword;

    Keyword(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public String getName() {
        return keyword;
    }

    public static Keyword toEnum(String str) {
        for(Keyword keyword : Keyword.values()) {
            if( keyword.getName().equals(str)) {
                return keyword;
            }
        }
        throw new IllegalArgumentException("Este simbolo '"+ str +"' nao pode ser convertido.");
    }

    public static List<String> getValues(){
        return Arrays.stream(Keyword.values())
                .map(Keyword::getName)
                .collect(Collectors.toList());
    }

}
