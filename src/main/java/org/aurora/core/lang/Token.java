package org.aurora.core.lang;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/*
 * @project org.aurora
 * @author Gabriel Honda on 22/02/2020
 */
public enum Token implements Terminal {

    $(0, "$", null),
    ID(1, "id", null),
    EMPTY(2, "î", null),
    NUMBER(3, "number", null),
    STRING(4, "string_literal", null),

    INIT(5, "init_program", "aurora::init"),
    CLOSE(6, "close_program", "aurora::close"),
    IF(7, "if", "au::if"),
    ELSE(8, "else", "au::else"),
    ENDIF(9, "endif", "au::endif"),
    LOOP(10, "loop", "au::loop"),
    END_LOOP(11, "endloop", "au::endloop"),
    WRITE(12, "write", "au::write"),
    READ(13, "read", "au::read"),
    VAR(14, "var", "au::var");

    private final String keyword;
    private       String token;
    private       int    index;

    Token(int index, String token, String keyword) {
        this.token   = token;
        this.index   = index;
        this.keyword = keyword;
    }

    public static Token toEnum(String str) {
        for(Token token : Token.values()) {
            if(str.equals(token.getKeyword())) {
                return token;
            }
        }
        throw new IllegalArgumentException("Este simbolo '" + str + "' nao pode ser convertido.");
    }

    public static List<String> getKeywords() {
        return Arrays.stream(Token.values())
                .map(Token::getKeyword)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public static List<String> getValues() {
        return Arrays.stream(Token.values())
                .map(Token::getName)
                .collect(Collectors.toList());
    }

    private String getKeyword() {
        return this.keyword;
    }

    @Override
    public String getName() {
        return token;
    }

    @Override
    public int getIndex() {
        return index;
    }
}
