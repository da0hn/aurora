package org.aurora.core.analyzer.semantic.utils;

/*
 * @project org.aurora
 * @author Gabriel Honda on 18/03/2020
 */
public class NameMangling {

    public enum Status {
        ZERO,
        NON_ZERO
    }

    private final String declared;
    private final String decoration;
    private final int line;
    private final int column;
    private Status status;

    public NameMangling(String declared, String decoration, int line, int column, Status status) {
        this.declared = declared;
        this.decoration = decoration;
        this.line = line;
        this.column = column;
        this.status = status;
    }

    public String getDeclared() {
        return declared;
    }

    public String getDecoration() {
        return decoration;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "NameMangling{" +
                "declared='" + declared + '\'' +
                ", decoration='" + decoration + '\'' +
                ", line=" + line +
                ", column=" + column +
                ", status=" + status +
                '}';
    }
}
