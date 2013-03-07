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
public interface Invocation {

    public String getVarName();
    public String getMethodName();
    public String getReturnValueName();
    public List<Variable> getArguments();
    public boolean isConstructor();
    public boolean isVoid();
}

class InvocationImpl implements Invocation {

    private String varName;
    private String methodName;
    private String returnValueName;
    private List<Variable> arguments = new ArrayList<>();
    private boolean constructor;
    private boolean Void;

    public InvocationImpl(
            String varName, String methodName,
            boolean constructor, boolean isVoid, String retValName, Variable... args) {

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

    public boolean isVoid() {
        return Void;
    }
    
    
    @Override
    public String toString() {
        String result = "[ varName=" + varName + ", mName="+methodName + ", retValName=" + returnValueName + ", args=[";
                
        for (Variable a : arguments) {
            result+=a + ", ";
        }
        
        result += "]";
        
        return result;
    }
}
