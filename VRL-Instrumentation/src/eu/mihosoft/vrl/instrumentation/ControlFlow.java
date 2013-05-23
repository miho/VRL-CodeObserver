/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.mihosoft.vrl.instrumentation;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
public interface ControlFlow {
    public Invocation createInstance(String id, String className, String varName, Variable... args);
    public Invocation callMethod(String id, String varName, String mName, boolean isVoid, String retValueName, Variable... args);

    public ScopeInvocation callScope(Scope scope);
    
    public List<Invocation> getInvocations();
}
class ControlFlowImpl implements ControlFlow{
    private List<Invocation> invocations = new ArrayList<>();

    @Override
    public Invocation createInstance(String id, String className, String varName, Variable... args) {
        Invocation result = new InvocationImpl(id, className, "<init>", true, false, varName, args);
        getInvocations().add(result);
        return result;
    }

    @Override
    public Invocation callMethod(String id, String varName, String mName, boolean isVoid, String retValueName, Variable... args) {
        Invocation result = new InvocationImpl(id, varName, mName, false, isVoid, retValueName, args);
        getInvocations().add(result);
        return result;
    }
    
    @Override
    public ScopeInvocation callScope(Scope scope) {
        ScopeInvocation result = new ScopeInvocationImpl(scope);
        getInvocations().add(result);
        return result;
    }
    
    @Override
    public String toString() {
        String result = "[\n";
        for (Invocation invocation : getInvocations()) {
            result+=invocation.toString() + "\n";
        }
        
        result+="]";
        
        return result;
    }

    /**
     * @return the invocations
     */
    @Override
    public List<Invocation> getInvocations() {
        return invocations;
    }

    
}
