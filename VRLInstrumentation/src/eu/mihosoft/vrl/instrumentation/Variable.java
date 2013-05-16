/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.mihosoft.vrl.instrumentation;

import java.util.Objects;

/**
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
public interface Variable {

    public String getName();

    public String getTypeName();

    public Object getValue();

    public boolean isConstant();

    public Scope getScope();
    
    public void setValue(Object value);
    
    public  void setConstant(boolean b);
}



class VariableImpl implements Variable {

    private Scope scope;
    private String typeName;
    private String varName;
    private Object value;
    private boolean constant;

    public VariableImpl(Scope scope, String typeName, String varName, Object value, boolean constant) {
        this.scope = scope;
        this.typeName = typeName;
        this.varName = varName;
        this.value = value;
        this.constant = constant;
    }

    @Override
    public String getName() {
        return varName;
    }

    @Override
    public String getTypeName() {
        return typeName;
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public boolean isConstant() {
        return constant;
    }

    @Override
    public Scope getScope() {
        return scope;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final VariableImpl other = (VariableImpl) obj;
        if (!Objects.equals(this.scope, other.scope)) {
            return false;
        }
        if (!Objects.equals(this.varName, other.varName)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + Objects.hashCode(this.scope);
        hash = 83 * hash + Objects.hashCode(this.varName);
        return hash;
    }
    
    @Override
    public String toString() {
        return "[ const=" + constant + ", typeName=" + typeName + ", name=" + varName + ", val=" + value + " ]"; 
    }

    @Override
    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public void setConstant(boolean b) {
        this.constant = b;
    }
}