/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.mihosoft.vrl.instrumentation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
public interface Scope extends CodeEntity {

    public Scope getParent();

    public ScopeType getType();

    public String getName();

    public Object[] getScopeArgs();

    public Collection<Variable> getVariables();

    public Variable getVariable(String name);

    public Variable createVariable(IType type, String varName);
    
    public Variable createStaticVariable(IType type);

    public void assignConstant(String varName, Object constant);

    public void assignVariable(String varNameDest, String varNameSrc);

    public ControlFlow getControlFlow();

    public List<Scope> getScopes();

    public Scope getScopeById(String id);

    public Variable createVariable(IType type);

    public DataFlow getDataFlow();

    public void generateDataFlow();

    public Scope createScope(String id, ScopeType type, String name, Object[] args);
}

class ScopeImpl implements Scope {

    private String id;
    Scope parent;
    ScopeType type;
    private final String name;
    Object[] scopeArgs;
    Map<String, Variable> variables = new HashMap<>();
    ControlFlow controlFlow;
    DataFlow dataFlow;
    private final List<Scope> scopes = new ArrayList<>();
    private String code;
    private List<Scope> readOnlyScopes;

    public ScopeImpl(String id, Scope parent, ScopeType type, String name, Object... scopeArgs) {
        this.id = id;
        this.parent = parent;

        this.type = type;
        this.name = name;
        this.scopeArgs = scopeArgs;
        this.controlFlow = new ControlFlowImpl(this);
        this.dataFlow = new DataFlowImpl();

        if (parent != null) {
            if (this.parent instanceof ScopeImpl) {
                ((ScopeImpl) this.parent).addScope(this);
            } else {
                throw new UnsupportedOperationException("Unsupported parent scope specified."
                        + " Only " + ScopeImpl.class + " based implementations are supported!");
            }

            if (parent.getType() != ScopeType.CLASS && parent.getType() != ScopeType.NONE && parent.getType() != ScopeType.COMPILATION_UNIT) {
                parent.getControlFlow().callScope(this);
            }
        }
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
    public Variable createVariable(IType type, String varName) {
        Variable variable = new VariableImpl(this, type, varName, null, false);
        variables.put(varName, variable);
        return variable;
    }

    @Override
    public Variable createVariable(IType type) {
        String varNamePrefix = "vrlInternalVar";

        int counter = 0;
        String varName = varNamePrefix + counter;

        while (getVariable(varName) != null) {
            counter++;
            varName = varNamePrefix + counter;
        }

        return createVariable(type, varName);
    }
    
    @Override
    public Variable createStaticVariable(IType type) {
        Variable variable = VariableImpl.createStaticVar(parent, type);
        variables.put(variable.getName(), variable);
        return variable;
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

        System.out.println(">> assignment: " + varNameDest + "=" + varNameSrc);
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

        if (readOnlyScopes == null) {
            readOnlyScopes = Collections.unmodifiableList(scopes);
        }

        return readOnlyScopes;
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

    /**
     * @return the name
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * @return the id
     */
    @Override
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    @Override
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the code
     */
    @Override
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    @Override
    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public DataFlow getDataFlow() {
        return dataFlow;
    }

    @Override
    public void generateDataFlow() {

        System.out.println("DATAFLOW---------------------------------");

        for (Invocation i : controlFlow.getInvocations()) {
//            System.out.println("invocation: " + i);
            for (Variable v : i.getArguments()) {
                System.out.println("--> varname: " + v.getName() + ", " + i);
            }

            if (i instanceof ScopeInvocation) {
                ((ScopeInvocation) i).getScope().generateDataFlow();
            }
        }

        boolean isClassOrScript = getType() == ScopeType.CLASS || getType() == ScopeType.NONE || getType() == ScopeType.COMPILATION_UNIT;

        if (isClassOrScript) {
            for (Scope s : getScopes()) {
                s.generateDataFlow();
            }
        }
    }

    @Override
    public Scope createScope(String id, ScopeType type, String name, Object[] args) {
        Scope scope = new ScopeImpl(id, this, type, name, args);

        return scope;
    }

    @Override
    public Scope getScopeById(String id) {
        for (Scope s : scopes) {
            if (s.getId().equals(id)) {
                return s;
            }
        }
        return null;
    }

    private void addScope(ScopeImpl s) {
        scopes.add(s);
    }
}
