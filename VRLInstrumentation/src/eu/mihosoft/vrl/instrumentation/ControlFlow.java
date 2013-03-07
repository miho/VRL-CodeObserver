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
    public void createInstance(String className, String varName, Variable... args);
    public void callMethod(String varName, String mName, boolean isVoid, String retValueName, Variable... args);
}

class ControlFlowImpl implements ControlFlow{
    List<Invocation> invocations = new ArrayList<>();

    @Override
    public void createInstance(String className, String varName, Variable... args) {
        invocations.add(new InvocationImpl(className, varName, true, false, className, args));
    }

    @Override
    public void callMethod(String varName, String mName, boolean isVoid, String retValueName, Variable... args) {
        invocations.add(new InvocationImpl(varName, mName, false, isVoid, retValueName, args));
    }
    
    @Override
    public String toString() {
        String result = "[\n";
        for (Invocation invocation : invocations) {
            result+=invocation.toString() + "\n";
        }
        
        result+="]";
        
        return result;
    }
    
}
