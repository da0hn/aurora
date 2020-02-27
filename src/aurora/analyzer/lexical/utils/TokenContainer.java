package aurora.analyzer.lexical.utils;

import aurora.analyzer.lexical.interfaces.IToken;

/*
 * @project aurora
 * @author Gabriel Honda on 23/02/2020
 */
public class TokenContainer {

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
    public String toString() {
        return "TokenContainer{" +
                "line=" + line +
                ", column=" + column +
                ", token=" + token +
                ", lexeme='" + lexeme + '\'' +
                '}';
    }
}
