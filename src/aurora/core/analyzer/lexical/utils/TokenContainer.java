package aurora.core.analyzer.lexical.utils;

import aurora.core.analyzer.lexical.interfaces.LexicalObject;
import aurora.core.lang.Terminal;

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
        return this.getToken() + " at [" + this.getLine()
                + ", " + this.getColumn() + "]" +
                ": '" + this.getLexeme() + '\'';
    }

    @Override
    public String toString() {
        return print();
    }
}
