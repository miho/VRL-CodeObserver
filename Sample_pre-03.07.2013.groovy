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

    def greet() {  
        
       System.out.println("GROOVY: " + b(b(b(3-8)+b(2))) + " : " + groovy.lang.GroovySystem.getVersion()); 
        
        int n = 3; 
        
		VSource.id(23)
        for(int i = 0; c(i,10); i++) { 
            
            int val = Math.abs(i-n*2);
            
            (1..10).each {
                it->println("closure i: " + b(i))
            }  
             
            
            System.out.println(" --> i: " + i + " = " + b(val));
//            
            b(i);
        }       
        
		VSource.id(123)
        System.out.println("FINAL CALL");
    }    
      
    public int b(int i){println("b: " + i); return i*i;};  
    
    public boolean c(int i, int n) {

            return i < n;
    }
   
}

class ABC {
	public void test1() {
		println("ABC")

		VSource.id(27)
		AkkaTest a = new AkkaTest()
		a.m1()
	}
}

class AkkaTest {

	public m1() {
		AkkaTest a = new AkkaTest()
	}
}
   

   
