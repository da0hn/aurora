package org.aurora.core.analyzer.semantic.utils;

/*
 * @project org.aurora
 * @author Gabriel Honda on 18/03/2020
 */
public class Scope {
    private String label;
    private int level;

    public Scope(String label) {
        this.label = label;
        this.level = 0;
    }

    public String getLabel() {
        return label;
    }

    public int getLevel() {
        return level;
    }

    public void increaseLevel() {
        this.level++;
    }

    @Override
    public String toString() {
        return "Scope{" +
                "label='" + label + '\'' +
                ", level=" + level +
                '}';
    }
}
