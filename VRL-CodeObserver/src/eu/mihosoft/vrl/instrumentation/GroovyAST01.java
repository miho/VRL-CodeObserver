/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.mihosoft.vrl.instrumentation;

/**
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
public class GroovyAST01 {

    // http://groovy.codehaus.org/Compile-time+Metaprogramming+-+AST+Transformations
    // http://hamletdarcy.blogspot.de/2009/01/groovy-compile-time-meta-magic.html
    // methodcall transformations:
    // http://groovy.329449.n5.nabble.com/AST-Transforming-a-MethodCallExpression-td5711841.html
    // type transformations:
    // http://groovy.329449.n5.nabble.com/is-possible-an-AST-transformation-to-convert-all-BigDecimals-to-doubles-in-GroovyLab-td5711461.html
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here  
        
        SampleClass sampleClass = new SampleClass();
        sampleClass.greet();
        
        SampleClass02 sampleClass02 = new SampleClass02();
        sampleClass02.method();

    }

    
}
