package aurora.analyzer.semantic;

import aurora.analyzer.lexical.interfaces.AnalyzerService;
import aurora.analyzer.lexical.utils.TokenContainer;
import aurora.analyzer.lexical.utils.Tokens;
import aurora.analyzer.semantic.utils.NameMangling;
import aurora.analyzer.semantic.utils.Scope;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static aurora.analyzer.semantic.log.LogSemantic.error;
import static aurora.analyzer.semantic.log.LogSemantic.log;
import static aurora.analyzer.semantic.utils.NameMangling.Status.NON_ZERO;
import static aurora.analyzer.semantic.utils.NameMangling.Status.ZERO;
import static aurora.lang.Symbol.EQUALS;
import static aurora.lang.Symbol.SEMICOLON;
import static aurora.lang.Token.*;

/*
 * @project aurora
 * @author Gabriel Honda on 21/02/2020
 */
public class Semantic {

	private final List<TokenContainer> tokens;
	private final List<NameMangling> table;
	private final Stack<Scope> scopeStack;

	public Semantic() {
		this.tokens = new ArrayList<>(Tokens.get());
		this.table = new ArrayList<>();
		this.scopeStack = new Stack<>();
	}

	public void analyze() {
		var index = new AtomicInteger(0);
		scopeStack.push(new Scope("_0"));

		log("begin scope " + scopeStack.peek().getLabel() + ".");

		while(index.get() < tokens.size()) {

			var container = tokens.get(index.get());

			if(VAR.equals(container.getToken())) {
				declarationProcedure(index);
			}
			else if(IF.equals(container.getToken()) || LOOP.equals(container.getToken())) {
				ifOrLoopProcedure(index, container);
			}
			else if(ELSE.equals(container.getToken())) {
				closeScope(container, () -> scopeStack.peek().increaseLevel());
				initScope(container);
			}
			else if(END_LOOP.equals(container.getToken()) || ENDIF.equals(container.getToken())) {
				closeScope(container, () -> this.scopeStack.peek().increaseLevel());
			}
			else if(CLOSE.equals(container.getToken())) {
				closeScope(container, () -> {});
			}
			else if(ID.equals(container.getToken())) {
				varDeclaredProcedure(index, container);
				index.getAndIncrement();
				if(EQUALS.equals(tokens.get(index.get()).getToken())) {
					var id = scopeStack.peek().getLabel();
					Optional<NameMangling> label = findNextScope(container.getLexeme() + id);
					index.getAndIncrement();
					StringBuilder basicExpression = new StringBuilder();

					expressionProcedure(index, container, basicExpression);

					label.ifPresentOrElse(obj -> {
						if("0".equals(basicExpression.toString())) {
							obj.setStatus(ZERO);
						}
						else if(basicExpression.toString().contains("/0")) {
							error("division by zero", container.getLine(), container.getColumn());
						}
						else {
							obj.setStatus(NON_ZERO);
						}
						log("|\tassign: " + obj.getDecoration() + ", value: " + basicExpression);
					}, () -> {
						var err = "identifier '" + tokens.get(index.get()) + "' was not declared.";
						error(err, container.getLine(), container.getColumn());
					});
				}
			}
			index.getAndIncrement();
		}
		table.forEach(System.out::println);
	}

	private void expressionProcedure(AtomicInteger index, TokenContainer container, StringBuilder basicExpression) {
		while(!SEMICOLON.equals(tokens.get(index.get()).getToken())) {
			var expression = tokens.get(index.get()).getLexeme();

			if(AnalyzerService.isIdentifier(expression)) {
				findNextScope(expression + scopeStack.peek().getLabel())
						.ifPresentOrElse(obj -> {
							basicExpression.append(ZERO.equals(obj.getStatus()) ? "0" : obj.getDeclared());
						}, () -> {
							var err = "identifier '" + tokens.get(index.get()) + "' was not declared.";
							error(err, container.getLine(), container.getColumn());
						});
			}
			else {
				basicExpression.append(expression);
			}
			index.getAndIncrement();
		}
	}

	private void declarationProcedure(AtomicInteger index) {
		var variable = tokens.get(index.get() + 1);
		var declared = variable.getLexeme();
		var decoration = declared + scopeStack.peek().getLabel();

		var line = variable.getLine();
		var column = variable.getColumn();

		boolean notDeclared = table.stream()
				.map(NameMangling::getDecoration)
				.noneMatch(decoration::equals);
		if(notDeclared) {
			table.add(new NameMangling(declared, decoration, line, column, ZERO));
		}
		else {
			var err = "identifier '" + variable.getLexeme() + "' was already declared.";
			error(err, line, column);
		}
	}

	private void ifOrLoopProcedure(AtomicInteger index, TokenContainer container) {
		while(!tokens.get(index.incrementAndGet()).getLexeme().equals(")")) {
			varDeclaredProcedure(index, container);
		}
		initScope(container);
	}

	private void varDeclaredProcedure(AtomicInteger index, TokenContainer container) {
		if(tokens.get(index.get()).getToken().equals(ID)) {
			final var level = scopeStack.peek().getLabel();
			final var decoration = tokens.get(index.get()).getLexeme() + level;
			Optional<NameMangling> obj = findNextScope(decoration);
			obj.ifPresentOrElse(_obj -> {
				tokens.get(index.get()).setLexeme(_obj.getDecoration());
			}, () -> {
				var err = "identifier '" + tokens.get(index.get()) + "' was not declared.";
				error(err, container.getLine(), container.getColumn());
			});
		}
	}

	private Optional<NameMangling> findNextScope(String decoration) {
		var arr = new ArrayList<>(table);
		Collections.reverse(arr);
		return arr.stream()
				.filter(n -> decoration.startsWith(n.getDecoration()))
				.findFirst();
	}

	private void initScope(TokenContainer container) {
		var label = scopeStack.peek().getLabel() + "_" + scopeStack.peek().getLevel();
		scopeStack.push(new Scope(label));
		log("begin scope " + scopeStack.peek().getLabel() + " " + container.print() + ".");
	}

	private void closeScope(TokenContainer container, Runnable action) {
		log("end scope " + scopeStack.peek().getLabel() + " " + container.print() + ".");
		scopeStack.pop();
		action.run();
	}
}
