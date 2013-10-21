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
class MethodDeclaration_Impl extends ScopeImpl implements MethodDeclaration {

    private final MethodDeclarationMetaData metadata;

    public MethodDeclaration_Impl(String id, Scope parent, IType returnType, IModifiers modifiers, IParameters params) {
        super(id, parent, ScopeType.CLASS, returnType.getFullClassName(), new MethodDeclarationMetaData(returnType, modifiers, params));
        metadata = (MethodDeclarationMetaData) getScopeArgs()[0];
    }

    @Override
    public IType getReturnType() {
        return null;
    }

    @Override
    public IModifiers getModifiers() {
        return null;
    }

    @Override
    public IParameters getParameters() {
        return null;
    }
}

final class MethodDeclarationMetaData {

    private final IType type;
    private final IModifiers modifiers;
    private final IParameters params;

    public MethodDeclarationMetaData(IType type, IModifiers modifiers, IParameters params) {
        this.type = type;
        this.modifiers = modifiers;
        this.params = params;
    }

    /**
     * @return the type
     */
    public IType getType() {
        return type;
    }

    /**
     * @return the modifiers
     */
    public IModifiers getModifiers() {
        return modifiers;
    }

    /**
     * @return the params
     */
    public IParameters getParams() {
        return params;
    }

}
