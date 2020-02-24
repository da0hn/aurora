package aurora.analyzer.lexical;

import aurora.analyzer.lexical.enums.Keyword;
import aurora.analyzer.lexical.enums.Symbol;
import aurora.analyzer.lexical.enums.Token;
import aurora.analyzer.lexical.utils.Pointer;
import aurora.analyzer.lexical.utils.TokenContainer;
import aurora.log.Log;

import java.util.ArrayList;
import java.util.List;

/*
 * @project aurora
 * @author Gabriel Honda on 22/02/2020
 */
public class Lexical {

    private Integer line = 0;
    private Integer column = 1;
    private List<TokenContainer> tokens;
    private Log log;

    public Lexical(Log log) {
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

        Integer index = 0;
        StringBuilder buffer = new StringBuilder();

        Character curr = line.charAt(0);
        Character prev = curr;
        Character next = line.charAt(1);

        while(index < line.length()) {

            prev = curr;
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
                    prev = curr;
                    curr = line.charAt(index++);
                    buffer.append(curr);
                    if( index == line.length()) {
                        log.error("missing closing quotes", this.line, this.column);
                        break;
                    }

                } while(!curr.equals('"'));

                if (curr.equals('"')) {
                    log.message("string_literal", this.line, this.column);
                    tokens.add(new TokenContainer(Token.STRING, buffer.toString(), this.line, this.column));
                }

                this.column += buffer.length();
                buffer = new StringBuilder();
            }

            else if(Symbol.getValues().contains(curr.toString())) {
                log.message("token", this.line, this.column);
                tokens.add(new TokenContainer(Symbol.toEnum(curr.toString()),
                                              curr.toString(),
                                              this.line, this.column));
                this.column++;
                buffer = new StringBuilder();
            }

            else if(!Character.isLetterOrDigit(next) && !next.equals(':')) {
                buffer.append(curr);
                analyzeBuffer(buffer.toString());
                this.column += buffer.length();
                buffer = new StringBuilder();
            } else {
                buffer.append(curr);
            }
        }
    }

    private void updateCharacters(Pointer ptr, Integer index, String line) {
        ptr.setPrevious(ptr.getCurrent());
        ptr.setCurrent(ptr.getNext());
        if(index < line.length()-1) {
            ptr.setNext(line.charAt(index + 1));
        }
    }

    private void analyzeBuffer(String buffer) {
        if(Keyword.getValues().contains(buffer)) {
            log.message("keyword", this.line, this.column);
            this.tokens.add(new TokenContainer(Keyword.toEnum(buffer), buffer,
                                               this.line, this.column
            ));
        } else if(isIdentifier(buffer)) {
            log.message("identifier", this.line, this.column);
            this.tokens.add(new TokenContainer(Token.ID, buffer,
                                               this.line, this.column
            ));
        } else if(isNumber(buffer)) {
            log.message("number", this.line, this.column);
            this.tokens.add(new TokenContainer(Token.NUMBER, buffer,
                                               this.line, this.column
            ));
        } else {
//            log.error("error at analyzeBuffer method", this.line, this.column);
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
