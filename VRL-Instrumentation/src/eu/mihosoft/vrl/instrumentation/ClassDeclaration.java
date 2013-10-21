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
class ClassDeclaration extends ScopeImpl {

    private ClassDeclarationMetaData metadata;

    public ClassDeclaration(String id, Scope parent, Type type, Modifiers modifiers, Extends extendz, Extends implementz) {
        super(id, parent, ScopeType.CLASS, type.getFullClassName(), new ClassDeclarationMetaData(type, modifiers, extendz, implementz));
        metadata = (ClassDeclarationMetaData) getScopeArgs()[0];
    }

    public Type getClassType() {
        return metadata.getType();
    }

    public Modifiers getClassModifiers() {
        return metadata.getModifiers();
    }

    public Extends getExtends() {
        return metadata.getExtendz();
    }

    public Extends getImplements() {
        return metadata.getImplementz();
    }
}

final class ClassDeclarationMetaData {

    private final Type type;
    private final Modifiers modifiers;
    private final Extends extendz;
    private final Extends implementz;

    public ClassDeclarationMetaData(Type type, Modifiers modifiers, Extends extendz, Extends implementz) {
        this.type = type;
        this.modifiers = modifiers;
        this.extendz = extendz;
        this.implementz = implementz;
    }

    /**
     * @return the extendz
     */
    public Extends getExtendz() {
        return extendz;
    }

    /**
     * @return the implementz
     */
    public Extends getImplementz() {
        return implementz;
    }

    /**
     * @return the type
     */
    public Type getType() {
        return type;
    }

    /**
     * @return the modifiers
     */
    public Modifiers getModifiers() {
        return modifiers;
    }

}
