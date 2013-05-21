/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.mihosoft.vrl.instrumentation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
public interface Invocation extends CodeEntity{

    public String getVarName();
    public String getMethodName();
    public String getReturnValueName();
    public List<Variable> getArguments();
    public boolean isConstructor();
    public boolean isVoid();
    public boolean isScope();
}




class ScopeInvocation extends InvocationImpl {
    
    private Scope scope;

    public ScopeInvocation(Scope s) {
        super("","","scope",false,true, "", new Variable[0]);
        this.scope = s;
    }

    /**
     * @return the scope
     */
    public Scope getScope() {
        return scope;
    }
    
    @Override
    public boolean isScope() {
        return true;
    }
    
}

class InvocationImpl implements Invocation {
    private String id;
    private String varName;
    private String methodName;
    private String returnValueName;
    private List<Variable> arguments = new ArrayList<>();
    private boolean constructor;
    private boolean Void;

    public InvocationImpl(
            String id,
            String varName, String methodName,
            boolean constructor, boolean isVoid, String retValName, Variable... args) {
        this.id = id;
        this.varName = varName;
        this.methodName = methodName;
        this.constructor = constructor;
        this.Void = isVoid;
        this.returnValueName = retValName;

        arguments.addAll(Arrays.asList(args));
    }

    @Override
    public String getVarName() {
        return varName;
    }

    @Override
    public String getMethodName() {
        return methodName;
    }
    
    @Override
    public String getReturnValueName() {
        return returnValueName;
    }

    @Override
    public List<Variable> getArguments() {
        return arguments;
    }

    @Override
    public boolean isConstructor() {
        return constructor;
    }

    @Override
    public boolean isVoid() {
        return Void;
    }
    
    
    @Override
    public String toString() {
        
        String result = "[ ";
        
        if (this instanceof ScopeInvocation) {
            ScopeInvocation scopeInvocation = (ScopeInvocation) this;
            result+="scopeType: " + scopeInvocation.getScope().getType() + ", ";
        }
        
        result+="constructor=" + constructor + ", varName=" + varName + ", mName="+methodName + ", retValName=" + returnValueName + ", args=[";
                
        for (Variable a : arguments) {
            result+=a + ", ";
        }
        
        result += "]";
        
        return result;
    }

    /**
     * @return the id
     */
    @Override
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean isScope() {
        return false;
    }
}
