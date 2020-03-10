package aurora.lang;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/*
 * @project aurora
 * @author Gabriel Honda on 23/02/2020
 */
public enum Keyword implements Terminal {

    INIT(-1,"aurora::init"),
    CLOSE(-1,"aurora::close"),
    IF(-1,"au::if"),
    ENDIF(-1,"au::endif"),
    ELSE(-1,"au::else"),
    LOOP(-1,"au::loop"),
    END_LOOP(-1,"au::endloop"),
    WRITE(-1,"au::write"),
    READ(-1,"au::read"),
    VAR(-1,"au::var");

    private final String keyword;
    private int index;

    Keyword(int index, String keyword) {
        this.keyword = keyword;
        this.index = index;
    }

    @Override
    public String getName() {
        return keyword;
    }

    @Override
    public int getIndex() {
        return index;
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
