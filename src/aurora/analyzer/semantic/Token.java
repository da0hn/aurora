package aurora.analyzer.semantic;

/*
 * @project aurora
 * @author Gabriel Honda on 22/02/2020
 */
public enum Token {

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
    VAR("var"),

    OPEN_PARENTHESIS("("),
    CLOSE_PARENTHESIS(")"),
    SEMICOLON(";"),
    EQUALS("="),
    PLUS("+"),
    MINUS("-"),
    ASTERISK("*"),
    FOWARDSLASH("/"),
    GREATERTHAN(">"),
    LESSTHAN("<");

    private String token;

    Token(String token) {
        this.token = token;
    }

    public String getName() {
        return token;
    }
}
