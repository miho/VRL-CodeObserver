/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.mihosoft.vrl.instrumentation

/**
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
@VRLInstrumentation
class SampleClass02 {
	public void method() {
            int x = Math.abs(Math.min(3,4));
            
            double y = Math.sin(Math.sqrt(x*x+2.0*2.0));
            
            println("x= " + x + ", y=" + y);
            
            double z = Math.sin(2.3)
        }
}

