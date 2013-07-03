/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.mihosoft.vrl.instrumentation;

import groovy.transform.TypeChecked;

/**
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
//@VRLInstrumentation 
@VRLVisualization
class SampleClass { 
    
    SampleClass() {
    }

    
    void playground() {
      
      int a = 1
      int b = 2
      
      methodTwo(methodOne(a),methodOne(b))
      
    }
    
     int methodOne(int a) {  
	return a;
    }
    
    int methodTwo(int a, int b) {
	return a+b;
    }
   
}

   

   
