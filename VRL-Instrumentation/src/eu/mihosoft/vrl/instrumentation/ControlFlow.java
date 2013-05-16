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
    public void createInstance(String id, String className, String varName, Variable... args);
    public void callMethod(String id, String varName, String mName, boolean isVoid, String retValueName, Variable... args);

    public void callScope(Scope scope);
    
    public List<Invocation> getInvocations();
}
class ControlFlowImpl implements ControlFlow{
    private List<Invocation> invocations = new ArrayList<>();

    @Override
    public void createInstance(String id, String className, String varName, Variable... args) {
        getInvocations().add(new InvocationImpl(id, className, "<init>", true, false, varName, args));
    }

    @Override
    public void callMethod(String id, String varName, String mName, boolean isVoid, String retValueName, Variable... args) {
        getInvocations().add(new InvocationImpl(id, varName, mName, false, isVoid, retValueName, args));
    }
    
    @Override
    public void callScope(Scope scope) {
        getInvocations().add(new ScopeInvocation(scope));
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
    public List<Invocation> getInvocations() {
        return invocations;
    }

    
    
}
