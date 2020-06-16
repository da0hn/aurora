package aurora.analyzer.lexical.utils;

import aurora.analyzer.lexical.interfaces.LexicalObject;
import aurora.lang.Terminal;

/**
 * @author Gabriel Honda on 23/02/2020
 * @project aurora
 */
public class TokenContainer implements LexicalObject {

    private Integer line;
    private Integer column;
    private Terminal token;
    private String lexeme;

    public TokenContainer(Terminal token, String lexeme, Integer line, Integer column) {
        this.line = line;
        this.column = column;
        this.token = token;
        this.lexeme = lexeme;
    }

    public Integer getLine() {
        return line;
    }

    public Integer getColumn() {
        return column;
    }

    public Terminal getToken() {
        return token;
    }

    public void setLexeme(String lexeme) {
        this.lexeme = lexeme;
    }

    public String getLexeme() {
        return lexeme;
    }

    @Override
    public String print() {
        var separator = "|";
        return String.format(" %3d:%3d %2s  %-17s%4s\t%-20s\t", this.getLine(),
                this.getColumn(), separator, this.getToken(), separator,this.getLexeme()
        );
    }

    @Override
    public String toString() {
        return print();
    }
}
