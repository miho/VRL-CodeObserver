/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.mihosoft.vrl.instrumentation;

/**
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
public class VariableFactory {

    public static Variable createObjectVariable(Scope scope, String typeName, String varName) {
        return new VariableImpl(scope, typeName, varName, null, false);
    }

    public static Variable createConstantVariable(Scope scope, String typeName, String varName, Object constant) {
        return new VariableImpl(scope, typeName, varName, constant, true);
    }
}
