/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.mihosoft.vrl.instrumentation;

/**
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
@VRLInstrumentation 
class SampleClass { 
    
    SampleClass() {
    }

    def greet() {  
        
       System.out.println("GROOVY: " + b(c(2,3)) + " : " + groovy.lang.GroovySystem.getVersion()); 
        
        int n = 3; 
        
        for(int i = 0; c(i,n); i++) { 
             
            int val = Math.abs(i-n*2);
            
            (1..10).each {
                it->println("closure i: " + i)
            }  
             
            
            System.out.println(" --> i: " + i + " = " + b(val));
//            
            b(i);    
        }       
        
        System.out.println("FINAL CALL");
    }    
      
    public int b(int i){println("i: " + i); return i*i;};  
    
    public boolean c(int i, int n) {
            return i < n;
    }
}
   

   