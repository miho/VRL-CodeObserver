/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.mihosoft.vrl.instrumentation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
public interface Scope {

    public Scope getParent();

    public ScopeType getType();

    public Object[] getScopeArgs();

    public Collection<Variable> getVariables();

    public Variable getVariable(String name);

    public void createVariable(String typeName, String varName);

    public void assignConstant(String varName, Object constant);
    
    public void assignVariable(String varNameDest, String varNameSrc);

    public ControlFlow getControlFlow();

    public List<Scope> getScopes();
    
    public String createVariable(String typeName);
}

class ScopeImpl implements Scope {

    Scope parent;
    ScopeType type;
    Object[] scopeArgs;
    Map<String, Variable> variables = new HashMap<>();
    ControlFlow controlFlow;
    private List<Scope> scopes = new ArrayList<>();

    public ScopeImpl(Scope parent, ScopeType type, Object[] scopeArgs) {
        this.parent = parent;
        if (parent != null) {
            this.parent.getScopes().add(this);
        }
        this.type = type;
        this.scopeArgs = scopeArgs;
        this.controlFlow = new ControlFlowImpl();
    }

    @Override
    public ScopeType getType() {
        return type;
    }

    @Override
    public Object[] getScopeArgs() {
        return scopeArgs;
    }

    @Override
    public Collection<Variable> getVariables() {
        return variables.values();
    }

    @Override
    public Variable getVariable(String name) {
        Variable result = variables.get(name);

        if (result == null && getParent() != null) {
            result = getParent().getVariable(name);
        }

        return result;
    }

    @Override
    public void createVariable(String typeName, String varName) {
        variables.put(varName, new VariableImpl(this, typeName, varName, null, false));
    }
    
    @Override
    public String createVariable(String typeName) {
        String varNamePrefix = "vrlInternalVar";
        
        int counter = 0;
        String varName = varNamePrefix + counter;
        
        while(getVariable(varName)!=null) {
            counter++;
            varName = varNamePrefix + counter;
        }
        
        createVariable(typeName, varName);
        
        return varName;
    }

    @Override
    public void assignConstant(String varName, Object constant) {
        Variable var = getVariable(varName);

        if (var == null) {
            throw new IllegalArgumentException("Variable " + varName + " does not exist!");
        }
        
        var.setValue(constant);
        var.setConstant(true);
        
    }
    
    @Override
    public void assignVariable(String varNameDest, String varNameSrc) {
        Variable varDest = getVariable(varNameDest);
        Variable varSrc = getVariable(varNameSrc);

        if (varDest == null) {
            throw new IllegalArgumentException("Variable " + varNameDest + " does not exist!");
        }
        
        if (varSrc == null) {
            throw new IllegalArgumentException("Variable " + varNameSrc + " does not exist!");
        }

        System.out.println(">> assignment: " + varNameDest+"="+varNameSrc);
    }

    @Override
    public ControlFlow getControlFlow() {
        return controlFlow;
    }

    @Override
    public Scope getParent() {
        return parent;
    }

    /**
     * @return the scopes
     */
    @Override
    public List<Scope> getScopes() {
        return scopes;
    }

    @Override
    public String toString() {
        String result = "Scope:" + type;

        result += "\n>> Variables:\n";

        for (Variable v : variables.values()) {
            result += " --> " + v.toString() + "\n";
        }
        
        result += "\n>> ControlFlow:\n" + controlFlow.toString();
        
        result += "\n>> SubScopes:\n";
        
        for (Scope s : scopes) {
            result += s.toString() + "\n";
        }

        return result;
    }
}
