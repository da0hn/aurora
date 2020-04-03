package aurora.analyzer.lexical.legacy;

import aurora.analyzer.lexical.interfaces.BufferAnalyzer;
import aurora.analyzer.lexical.log.LogLexical;
import aurora.analyzer.lexical.utils.TokenContainer;
import aurora.lang.Symbol;
import aurora.lang.Terminal;
import aurora.lang.Token;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import static aurora.analyzer.lexical.interfaces.BufferAnalyzer.identifier;
import static aurora.analyzer.lexical.interfaces.BufferAnalyzer.number;

/*
 * @project aurora
 * @author Gabriel Honda on 22/02/2020
 */
class Lexical_v1 {
	/*
	 * primeira implementacao do analisador lexico,
	 * sera mantido como consulta de exemplo
	 * */
	private Integer line = 0;
	private Integer column = 1;
	private List<TokenContainer> tokens;
	private LogLexical log;

	public Lexical_v1(LogLexical log) {
		this.tokens = new ArrayList<>();
		this.log = log;
	}

	public void analyze(List<String> code) {
		for(String line : code) {
			this.line++;
			analyzeLine(line);
			this.column = 1;
		}
		this.tokens.add(new TokenContainer(Token.$, "$",
		                                   this.line, this.column
		));
	}

	private void analyzeLine(String line) {
		if(line.isEmpty() && line.isBlank()) return;

		AtomicInteger index = new AtomicInteger(0);
		StringBuilder buffer = new StringBuilder();
		Character curr, next;

		while(index.get() < line.length()) {

			curr = line.charAt(index.getAndIncrement());
			next = index.get() < line.length() ? line.charAt(index.get()) : 0;

			if(curr.equals('/') && next.equals('/')) {
				return;
			}
			else if(Character.isWhitespace(curr)) {
				this.column++;
			}
			else if(curr.equals('"')) {
				analyzeQuote(line, index);
				buffer = new StringBuilder();
			}
			else if(Symbol.getValues().contains(curr.toString())) {
				var tk = new TokenContainer(Symbol.toEnum(curr.toString()),
				                            curr.toString(), this.line, this.column
				);
				tokens.add(tk);
				LogLexical.add(tk);
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

	private void analyzeQuote(String line, AtomicInteger index) {
		StringBuilder buffer = new StringBuilder();
		buffer.append('"');
		Character curr;
		do {
			curr = line.charAt(index.getAndIncrement());
			buffer.append(curr);
			if(index.get() == line.length()) {
				LogLexical.error("missing closing quotes", this.line, this.column);
				return;
			}
		} while(!curr.equals('"'));

		var tk = new TokenContainer(Token.STRING, buffer.toString(), this.line, this.column);
		LogLexical.add(tk);
		tokens.add(tk);
		this.column += buffer.length();
	}

	private void analyzeBuffer(String buffer) {
		Optional<Terminal> optToken = BufferAnalyzer.keyword().orElse(identifier())
				.orElse(number()).apply(buffer);

		Consumer<Terminal> addAndLog = tk -> {
			var temp = new TokenContainer(tk, buffer, this.line, this.column);
			tokens.add(temp);
			LogLexical.add(temp);
		};

		Runnable logError = () -> LogLexical.error("the lexeme was not recognized", this.line, this.column);

		optToken.ifPresentOrElse(addAndLog, logError);

	}

	public List<TokenContainer> getTokens() {
		return tokens;
	}
}
