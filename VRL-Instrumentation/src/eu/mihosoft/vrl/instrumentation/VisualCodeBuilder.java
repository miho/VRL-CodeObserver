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

    private final Stack<String> variables = new Stack<>();
    private IdRequest idRequest;

    public String popVariable() {
        return variables.pop();
    }

    public Scope createScope(Scope parent, ScopeType type, String name, Object... args) {
        if (parent != null) {
            return parent.createScope(idRequest.request(), type, name, args);
        } else {
            return new ScopeImpl(idRequest.request(), null, type, name, args);
        }
    }

    public void createVariable(Scope scope, Type type, String varName) {
        scope.createVariable(type, varName);

        variables.push(varName);
    }

    public String createVariable(Scope scope, Type type) {
        String name = scope.createVariable(type);

        variables.push(name);

        return name;
    }

    public Scope declareMethod(Scope scope, Type returnType, String methodName, Parameter... params) {
        return scope.declareMethod(idRequest.request(), returnType, methodName, params);
    }

    public void declareFor(Scope scope, int from, int to, int inc) {

    }

    public void declareWhile(Scope scope, Invocation check) {

    }

    public void createInstance(Scope scope, Type type, String varName, Variable... args) {

        String id = idRequest.request();

        scope.getControlFlow().createInstance(id, type, varName, args);

        variables.push(varName);
    }

    public Invocation invokeMethod(Scope scope, String varName, String mName, boolean isVoid, String retValName, Variable... args) {
        String id = idRequest.request();

        Invocation result = scope.getControlFlow().callMethod(id, varName, mName, isVoid, retValName, args);

        return result;
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
