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
public class CompilationUnitDeclaration_Impl extends ScopeImpl implements CompilationUnitDeclaration{

    public CompilationUnitDeclaration_Impl(String id, Scope parent, String name) {
        super(id, parent, ScopeType.COMPILATION_UNIT, name, new Object[0]);
    }
}
