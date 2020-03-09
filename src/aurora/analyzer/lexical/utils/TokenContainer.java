package aurora.analyzer.lexical.utils;

import aurora.analyzer.lexical.interfaces.IToken;
import aurora.analyzer.lexical.interfaces.LexicalObject;

/*
 * @project aurora
 * @author Gabriel Honda on 23/02/2020
 */
public class TokenContainer implements LexicalObject {

    private Integer line;
    private Integer column;
    private IToken token;
    private String lexeme;

    public TokenContainer(IToken token, String lexeme, Integer line, Integer column ) {
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

    public IToken getToken() {
        return token;
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
}
