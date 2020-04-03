package aurora.analyzer.lexical.utils;

import aurora.analyzer.lexical.interfaces.LexicalObject;

/*
 * @project aurora
 * @author Gabriel Honda on 08/03/2020
 */
public class ErrorMessage implements LexicalObject {

	private String msg;
	private int line;
	private int column;

	public ErrorMessage(String msg, int line, int column) {
		this.msg = msg;
		this.line = line;
		this.column = column;
	}

	public String errorPosition() {
		return "[" + line + ", " + column + "]";
	}

	@Override
	public String print() {
		return "Error at " + errorPosition() + ": " + msg;
	}

	public static boolean isError(String type) {
		return type.equals("error");
	}
}
