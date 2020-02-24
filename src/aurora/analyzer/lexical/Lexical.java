package aurora.analyzer.lexical;

import aurora.analyzer.lexical.enums.Keyword;
import aurora.analyzer.lexical.enums.Symbol;
import aurora.analyzer.lexical.enums.Token;
import aurora.analyzer.lexical.utils.LogLexical;
import aurora.analyzer.lexical.utils.TokenContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/*
 * @project aurora
 * @author Gabriel Honda on 22/02/2020
 */
public class Lexical {

    private Integer line = 0;
    private Integer column = 1;
    private List<TokenContainer> tokens;
    private LogLexical log;

    public Lexical(LogLexical log) {
        this.tokens = new ArrayList<>();
        this.log = log;
    }

    public void analyze(List<String> code) {
        for(String line : code) {
            this.line++;
            analyzeLine(line);
            this.column = 1;
        }
        this.tokens.add(new TokenContainer(Token.FINAL, "$",
                                           this.line, this.column
        ));
    }

    private void analyzeLine(String line) {
        if(line.isEmpty() && line.isBlank()) return;

//        AtomicInteger index = new AtomicInteger(0);
        Integer index = 0;
        StringBuilder buffer = new StringBuilder();

        Character curr;
        Character next;

        while(index < line.length()) {

            curr = line.charAt(index++);
            next = index < line.length() ? line.charAt(index) : ' ';

            if(curr.equals('/') && next.equals('/')) {
                return;
            }
            else if(Character.isWhitespace(curr)) {
                this.column++;
            }
            else if(curr.equals('"')) {

                buffer = new StringBuilder();
                buffer.append('"');
                do {
                    curr = line.charAt(index++);
                    buffer.append(curr);
                    if(index == line.length()) {
                        log.error("Error: missing closing quotes", this.line, this.column);
                        break;
                    }

                } while(!curr.equals('"'));

                if(curr.equals('"')) {
                    var tk = new TokenContainer(Token.STRING, buffer.toString(), this.line, this.column);
                    log.message(tk);
                    tokens.add(tk);
                }

                this.column += buffer.length();
                buffer = new StringBuilder();
            }
            else if(Symbol.getValues().contains(curr.toString())) {
                var tk = new TokenContainer(Symbol.toEnum(curr.toString()),
                                            curr.toString(), this.line, this.column
                );
                tokens.add(tk);
                log.message(tk);
                this.column++;
                buffer = new StringBuilder();
            }
            else if(!Character.isLetterOrDigit(next) && !next.equals(':')) {
                buffer.append(curr);
                analyzeBuffer(buffer.toString());
                this.column += buffer.length();
                buffer = new StringBuilder();
            }
            else {
                buffer.append(curr);
            }
        }
    }

    private void quoteHandler(String line, Character curr, int index) {
        StringBuilder buffer = new StringBuilder();
        buffer.append('"');
        do {
            curr = line.charAt(index++);
            buffer.append(curr);
            if(index == line.length()) {
                log.error("Error: missing closing quotes", this.line, this.column);
                return;
            }

        } while(!curr.equals('"'));
        if(curr.equals('"')) {
            var tk = new TokenContainer(Token.STRING, buffer.toString(), this.line, this.column);
            log.message(tk);
            tokens.add(tk);
        }

        this.column += buffer.length();
    }

    private void analyzeBuffer(String buffer) {
        TokenContainer tk = null;

        if(Keyword.getValues().contains(buffer)) {
            tk = new TokenContainer(Keyword.toEnum(buffer), buffer, this.line, this.column);
            log.message(tk);
        }
        else if(isIdentifier(buffer)) {
            tk = new TokenContainer(Token.ID, buffer, this.line, this.column);
            log.message(tk);
        }
        else if(isNumber(buffer)) {
            tk = new TokenContainer(Token.NUMBER, buffer, this.line, this.column);
            log.message(tk);
        }

        if(tk != null) {
            this.tokens.add(tk);
        }
        else {
            log.error("the lexeme was not recognized", this.line, this.column);
        }
    }

    private boolean isIdentifier(String str) {
        if(Character.isDigit(str.charAt(0))) {
            return false;
        }
        return str.chars().allMatch(Character::isLetterOrDigit);
    }
    private boolean isNumber(String str) {
        return str.chars().allMatch(Character::isDigit);
    }
    public List<TokenContainer> getTokens() {
        return tokens;
    }
}
