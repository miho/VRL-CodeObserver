///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package eu.mihosoft.vrl.instrumentation;
//
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//import java.util.ArrayList;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
///**
// *
// * @author Michael Hoffer <info@michaelhoffer.de>
// */
//public class GroovyAST01 {
//
//    // http://groovy.codehaus.org/Compile-time+Metaprogramming+-+AST+Transformations
//    // http://hamletdarcy.blogspot.de/2009/01/groovy-compile-time-meta-magic.html
//    // methodcall transformations:
//    // http://groovy.329449.n5.nabble.com/AST-Transforming-a-MethodCallExpression-td5711841.html
//    // type transformations:
//    // http://groovy.329449.n5.nabble.com/is-possible-an-AST-transformation-to-convert-all-BigDecimals-to-doubles-in-GroovyLab-td5711461.html
//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String[] args) {
//        // TODO code application logic here
//        
//        
//        SampleClass sampleClass = new SampleClass();
//        sampleClass.greet();
//
//
//    }
//
//    /**
//     * Do not call manually! This method will be used by AST transformations
//     * to instrument method calls.
//     * @param staticCall defines whether method call is a static method call
//     * @param o instance the method belongs to
//     * @param mName method name
//     * @param args method arguments
//     * @return return value of the method that shall be instrumented
//     * @throws Throwable 
//     */
//    public static Object __instrumentCode(boolean staticCall, Object o, String mName, Object[] args) throws Throwable {
//
//        ArrayList<Class<?>> paramTypes = new ArrayList<Class<?>>();
//
//        System.out.println("calling " + mName + "(...)");
//
//        System.out.println(" --> o: " + o + " : " + o.getClass());
//
//        for (Object p : args) {
//            System.out.println(" --> arg: " + p + " : " + p.getClass());
//            Class<?> c = p.getClass();
//            paramTypes.add(c);
//        }
//        
//        // updateVisualization(o, mName, args);
//
//        try {
//
//            Class<?> cls;
//
//            if (staticCall) {
//                cls = (Class<?>) o;
////                System.out.println(" -> static: " + staticCall);
//            } else {
//                cls = o.getClass();
////                System.out.println(" -> static: " + staticCall);
//            }
//
//            Method method = null;
//
//            try {
//                method = cls.getMethod(mName, paramTypes.toArray(new Class<?>[]{}));
//            } catch (NoSuchMethodException ex) {
//                //
//            }
//
//            if (method == null) {
//
//                for (Method m : cls.getMethods()) {
//
//                    if (!m.getName().equals(mName)) {
//                        continue;
//                    }
//
//                    Class<?>[] methodParams = m.getParameterTypes();
//
//                    if (paramTypes.size() != methodParams.length) {
//                        continue;
//                    }
//
//                    boolean compatibleParameterTypes = true;
//
//                    for (int i = 0; i < paramTypes.size(); i++) {
//                        if (!VClassLoaderUtil.convertPrimitiveToWrapper(paramTypes.get(i)).getName().
//                                equals(VClassLoaderUtil.convertPrimitiveToWrapper(methodParams[i]).getName())) {
//                            compatibleParameterTypes = false;
//                            break;
//                        }
//                    }
//
//                    if (compatibleParameterTypes) {
//                        method = m;
//                        break;
//                    }
//                }
//            }
//
//            // last attempt to fix the method, maybe println() without System.out
//            if (method == null) {
//                if (mName.equals("print") || mName.equals("println")) {
//                    try {
//                        method = System.out.getClass().getMethod(mName, String.class);
//                        o = System.out;
//                    } catch (NoSuchMethodException ex) {
//                        //
//                    }
//                }
//            }
//
//
//            // access this method even if language visibility forbids
//            // invocation of this method
//            method.setAccessible(true);
//
//            Object returnValue =  method.invoke(o, (Object[]) args);
//            
//            // updateVisualization(o, mName, returnValue);
//            
//            return returnValue;
//
//        } catch (IllegalAccessException ex) {
//            Logger.getLogger(GroovyAST01.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IllegalArgumentException ex) {
//            Logger.getLogger(GroovyAST01.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (InvocationTargetException ex) {
//            Logger.getLogger(GroovyAST01.class.getName()).log(Level.SEVERE, null, ex);
//            throw ex.getCause();
//        } catch (SecurityException ex) {
//            Logger.getLogger(GroovyAST01.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        return null;
//    }
//}
