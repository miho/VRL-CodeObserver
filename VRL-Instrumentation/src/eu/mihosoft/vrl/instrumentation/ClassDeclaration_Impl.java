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
class ClassDeclaration_Impl extends ScopeImpl implements ClassDeclaration {

    private final ClassDeclarationMetaData metadata;

    public ClassDeclaration_Impl(String id, Scope parent, Type type, Modifiers modifiers, Extends extendz, Extends implementz) {
        super(id, parent, ScopeType.CLASS, type.getFullClassName(), new ClassDeclarationMetaData(type, modifiers, extendz, implementz));
        metadata = (ClassDeclarationMetaData) getScopeArgs()[0];
    }

    @Override
    public IType getClassType() {
        return metadata.getType();
    }

    @Override
    public IModifiers getClassModifiers() {
        return metadata.getModifiers();
    }

    @Override
    public IExtends getExtends() {
        return metadata.getExtendz();
    }

    @Override
    public IExtends getImplements() {
        return metadata.getImplementz();
    }

    @Override
    public MethodDeclaration declareMethod(String id, IModifiers modifiers, IType returnType, String methodName, IParameters params) {
        if (this.getType() != ScopeType.CLASS && this.getType() != ScopeType.NONE) {
            throw new IllegalArgumentException("Specified scopetype does not support method declaration: " + this.getType());
        }

        MethodDeclaration methodScope = new MethodDeclaration_Impl(id, parent, returnType, modifiers, params);

        return methodScope;
    }
}

final class ClassDeclarationMetaData {

    private final IType type;
    private final IModifiers modifiers;
    private final IExtends extendz;
    private final IExtends implementz;

    public ClassDeclarationMetaData(IType type, IModifiers modifiers, IExtends extendz, IExtends implementz) {
        this.type = type;
        this.modifiers = modifiers;
        this.extendz = extendz;
        this.implementz = implementz;
    }

    /**
     * @return the extendz
     */
    public IExtends getExtendz() {
        return extendz;
    }

    /**
     * @return the implementz
     */
    public IExtends getImplementz() {
        return implementz;
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

}


