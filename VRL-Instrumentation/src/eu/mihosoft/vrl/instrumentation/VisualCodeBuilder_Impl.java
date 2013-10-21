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
public class VisualCodeBuilder_Impl implements VisualCodeBuilder {

    private final Stack<String> variables = new Stack<>();
    private IdRequest idRequest;

    String popVariable() {
        return variables.pop();
    }

    Scope createScope(Scope parent, ScopeType type, String name, Object... args) {
        if (parent != null) {
            return parent.createScope(idRequest.request(), type, name, args);
        } else {
            return new ScopeImpl(idRequest.request(), null, type, name, args);
        }
    }

    @Override
    public Variable createVariable(Scope scope, IType type, String varName) {
        Variable result = scope.createVariable(type, varName);

        variables.push(varName);
        
        return result;
    }

    public Variable createVariable(Scope scope, IType type) {
        Variable result = scope.createVariable(type);

        variables.push(result.getName());

        return result;
    }

    @Override
    public MethodDeclaration declareMethod(ClassDeclaration scope,
            IModifiers modifiers, Type returnType, String methodName, IParameters params) {
        return scope.declareMethod(idRequest.request(), modifiers, returnType, methodName, params);
    }

    @Override
    public void declareFor(Scope scope, int from, int to, int inc) {

    }

    @Override
    public void declareWhile(Scope scope, Invocation check) {

    }

    @Override
    public void createInstance(Scope scope, Type type, String varName, Variable... args) {

        String id = idRequest.request();

        scope.getControlFlow().createInstance(id, type, varName, args);

        variables.push(varName);
    }

    @Override
    public Invocation invokeMethod(Scope scope, String varName, String mName, boolean isVoid, String retValName, Variable... args) {
        String id = idRequest.request();

        Invocation result = scope.getControlFlow().callMethod(id, varName, mName, isVoid, retValName, args);

        return result;
    }

    @Override
    public void assignVariable(Scope scope, String varNameDest, String varNameSrc) {
        scope.assignVariable(varNameDest, varNameSrc);
    }

    @Override
    public void assignConstant(Scope scope, String varName, Object constant) {
        scope.assignConstant(varName, constant);
    }

    void setIdRequest(IdRequest idRequest) {
        this.idRequest = idRequest;
    }
}
