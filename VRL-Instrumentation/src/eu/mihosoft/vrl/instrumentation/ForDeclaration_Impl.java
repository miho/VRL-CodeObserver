/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.mihosoft.vrl.instrumentation;

/**
 *
 * @author Michael Hoffer &lt;info@michaelhoffer.de&gt;
 */
public class ForDeclaration_Impl extends ScopeImpl implements ForDeclaration {

    private final ForDeclarationMetaData metadata;

    public ForDeclaration_Impl(String id, Scope parent, String varName, int from, int to, int inc) {
        super(id, parent, ScopeType.FOR, ScopeType.FOR.name(), new ForDeclarationMetaData(varName, from, to, inc));
        metadata = (ForDeclarationMetaData) getScopeArgs()[0];
        
        createVariable(new Type("int"), varName);
    }

    @Override
    public String getVarName() {
        return metadata.getVarName();
    }

    @Override
    public int getFrom() {
        return metadata.getFrom();
    }

    @Override
    public int getTo() {
        return metadata.getTo();
    }

    @Override
    public int getInc() {
        return metadata.getInc();
    }

}

class ForDeclarationMetaData {

    private final String varName;
    private final int from;
    private final int to;
    private final int inc;

    public ForDeclarationMetaData(String varName, int from, int to, int inc) {
        this.varName = varName;
        this.from = from;
        this.to = to;
        this.inc = inc;
    }

    /**
     * @return the varName
     */
    public String getVarName() {
        return varName;
    }

    /**
     * @return the from
     */
    public int getFrom() {
        return from;
    }

    /**
     * @return the to
     */
    public int getTo() {
        return to;
    }

    /**
     * @return the inc
     */
    public int getInc() {
        return inc;
    }

}
