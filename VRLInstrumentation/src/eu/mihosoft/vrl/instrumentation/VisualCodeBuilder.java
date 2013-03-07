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
    
    public String popVariable() {
        return variables.pop();
    }

    public Scope createScope(Scope parent, ScopeType type, Object... args) {
        return new ScopeImpl(parent, type, args);
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
        scope.getControlFlow().createInstance(typeName, varName, args);
        
        variables.push(varName);
    }
    
    public void invokeMethod(Scope scope, String varName, String mName, boolean isVoid, String retValName, Variable... args) {
        scope.getControlFlow().callMethod(varName, mName, isVoid, retValName, args);
    }
    
    public void assignVariable(Scope scope, String varNameDest, String varNameSrc) {
        scope.assignVariable(varNameDest, varNameSrc);
    }
    
    public void assignConstant(Scope scope, String varName, Object constant) {
        scope.assignConstant(varName, constant);
    }
}








