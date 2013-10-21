/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.mihosoft.vrl.instrumentation;

/**
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
public interface ClassDeclaration extends Scope {

    IModifiers getClassModifiers();

    IType getClassType();

    IExtends getExtends();

    IExtends getImplements();

    MethodDeclaration declareMethod(String id, IModifiers modifiers, IType returnType, String methodName, IParameters params);
    
}
