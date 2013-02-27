
package eu.mihosoft.vrl.instrumentation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
public class VRLInstrumentationUtil {
  // http://groovy.codehaus.org/Compile-time+Metaprogramming+-+AST+Transformations
    // http://hamletdarcy.blogspot.de/2009/01/groovy-compile-time-meta-magic.html
    // methodcall transformations:
    // http://groovy.329449.n5.nabble.com/AST-Transforming-a-MethodCallExpression-td5711841.html
    // type transformations:
    // http://groovy.329449.n5.nabble.com/is-possible-an-AST-transformation-to-convert-all-BigDecimals-to-doubles-in-GroovyLab-td5711461.html


    /**
     * Do not call manually! This method will be used by AST transformations
     * to instrument method calls.
     * @param staticCall defines whether method call is a static method call
     * @param o instance the method belongs to
     * @param mName method name
     * @param args method arguments
     * @return return value of the method that shall be instrumented
     * @throws Throwable 
     */
    public static Object __instrumentCode(boolean staticCall, Object o, String mName, Object[] args) throws Throwable {

        ArrayList<Class<?>> paramTypes = new ArrayList<>();

        System.out.println("calling " + mName + "(...)");

        System.out.println(" --> o: " + o + " : " + o.getClass());
        
        if (args==null) {
            args = new Object[0];
        }


        for (int i = 0; i < args.length;i++) {
            Object p = args[i];
            System.out.println(" --> arg: " + p + " : " + p.getClass());
            Class<?> c = p.getClass();
            
//            if (p instanceof java.math.BigDecimal) {
//                c = Double.class;
//                args[i] = ((java.math.BigDecimal)p).doubleValue();
//            }
            
            paramTypes.add(c);
        }
        
        // updateVisualization(o, mName, args);

        try {

            Class<?> cls;

            if (staticCall) {
                cls = (Class<?>) o;
//                System.out.println(" -> static: " + staticCall);
            } else {
                cls = o.getClass();
//                System.out.println(" -> static: " + staticCall);
            }

            Method method = null;

            try {
                method = cls.getMethod(mName, paramTypes.toArray(new Class<?>[]{}));
            } catch (NoSuchMethodException ex) {
                //
            }
            
            List<Method> candidates = new ArrayList<>();

            if (method == null) {

                for (Method m : cls.getMethods()) {
                    
//                    System.out.println(">> checking " + m.getName() + " == " + mName);

                    if (!m.getName().equals(mName)) {
                        continue;
                    }

                    Class<?>[] methodParams = m.getParameterTypes();
                    
                    

                    if (paramTypes.size() != methodParams.length) {
//                        System.out.println(" --> numargs differ: " + paramTypes.size() + " == " + methodParams.length);
                        continue;
                    }
                    
                    candidates.add(m);

                    boolean compatibleParameterTypes = true;

                    for (int i = 0; i < paramTypes.size(); i++) {
                        
//                        Object p = castParameter(args[i],methodParams);
                        
//                        System.out.print("marg: sig: " + methodParams[i].getName() + " == given:" + paramTypes.get(i).getName());
                        
                        if (!VClassLoaderUtil.convertPrimitiveToWrapper(paramTypes.get(i)).getName().
                                equals(VClassLoaderUtil.convertPrimitiveToWrapper(methodParams[i]).getName())) {
                            
                            compatibleParameterTypes = isParamCompatible(args[i], methodParams[i]);
//                            
                            if (!compatibleParameterTypes) {
                                System.out.println(" [FALSE]");
                                break;
                            }
                        } else {
//                            System.out.println(" [TRUE]");
                        }
                    }

                    if (compatibleParameterTypes) {
//                        System.out.println(">> method found!");
                        method = m;
                        break;
                    }
                }
            }

            // last attempt to fix the method, maybe println() without System.out
            if (method == null) {
                if (mName.equals("print") || mName.equals("println")) {
                    try {
                        method = System.out.getClass().getMethod(mName, String.class);
                        o = System.out;
                    } catch (NoSuchMethodException ex) {
                        //
                    }
                }
            }
            

            
//            boolean compatibleParameterTypes = true;
//            
//            for(Method m : candidates) {   
//            
//            Class<?>[] methodParams = m.getParameterTypes();
//                
//                for (int i = 0; i < paramTypes.size(); i++) {
//                    
//                    // indicates whether method param can be casted to actual param type
//                    boolean castable = isCastableFrom(methodParams[i], paramTypes.get(i));
            
//                    if castable or type equal 
//                            
//                    // perform cast from param type to expected method parameter
//                    p = castParameter(p,methodParams);
//                        
////                        System.out.print("marg: sig: " + methodParams[i].getName() + " == given:" + paramTypes.get(i).getName());
//                        
//                        if (!VClassLoaderUtil.convertPrimitiveToWrapper(paramTypes.get(i)).getName().
//                                equals(VClassLoaderUtil.convertPrimitiveToWrapper(methodParams[i]).getName())) {
//                            compatibleParameterTypes = false;
////                            System.out.println(" [FALSE]");
//                            break;
//                        } else {
////                            System.out.println(" [TRUE]");
//                        }
//                    }
//
//                    if (compatibleParameterTypes) {
////                        System.out.println(">> method found!");
//                        method = m;
//                        break;
//                    }
//            }
            
            if (method == null) {
//                System.out.println("ERROR: method = null! obj: " + o + ", args: " + args);
                return null;
            }


            // access this method even if language visibility forbids
            // invocation of this method
            method.setAccessible(true);

            Object returnValue =  method.invoke(o, (Object[]) args);
            
            // updateVisualization(o, mName, returnValue);
            
            return returnValue;

        } catch (IllegalAccessException ex) {
            Logger.getLogger(VRLInstrumentationUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(VRLInstrumentationUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(VRLInstrumentationUtil.class.getName()).log(Level.SEVERE, null, ex);
            throw ex.getCause();
        } catch (SecurityException ex) {
            Logger.getLogger(VRLInstrumentationUtil.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    private static boolean isParamCompatible(Object param, Class<?> methodParamType) {
        
//        Class<?> paramClass = param.getClass();
//        
//        if (methodParamType.equals(paramClass)) {
//            return param;
//        }
//        
//        if (methodParamType.isAssignableFrom(paramClass)) {
//            return methodParamType.cast(param);
//        }
        
        try {
            methodParamType.cast(param);
        } catch (ClassCastException ex) {
            return false;
        }
        
        return true; 
    }
    
}
