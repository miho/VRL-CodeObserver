/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.mihosoft.vrl.instrumentation;

import java.util.Stack;

/**
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
public class VisualCodeBuilder {

    private Stack<String> variables = new Stack<>();
    private IdRequest idRequest;

    public String popVariable() {
        return variables.pop();
    }

    public Scope createScope(Scope parent, ScopeType type, String name, Object... args) {
        Scope scope = new ScopeImpl(idRequest.request(), parent, type, name, args);
        if (parent != null && parent.getType() != ScopeType.CLASS && parent.getType() != ScopeType.NONE) {
            parent.getControlFlow().callScope(scope);
        }
        return scope;
    }

    public void createVariable(Scope scope, String typeName, String varName) {
        scope.createVariable(typeName, varName);

        variables.push(varName);
    }

    public String createVariable(Scope scope, String typeName) {
        String name = scope.createVariable(typeName);

        variables.push(name);

        return name;
    }

    public void createInstance(Scope scope, String typeName, String varName, Variable... args) {

        String id = idRequest.request();

        scope.getControlFlow().createInstance(id, typeName, varName, args);

        variables.push(varName);
    }

    public void invokeMethod(Scope scope, String varName, String mName, boolean isVoid, String retValName, Variable... args) {
        String id = idRequest.request();

        scope.getControlFlow().callMethod(id, varName, mName, isVoid, retValName, args);
    }

    public void assignVariable(Scope scope, String varNameDest, String varNameSrc) {
        scope.assignVariable(varNameDest, varNameSrc);
    }

    public void assignConstant(Scope scope, String varName, Object constant) {
        scope.assignConstant(varName, constant);
    }

    void setIdRequest(IdRequest idRequest) {
        this.idRequest = idRequest;
    }
}
