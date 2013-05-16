/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package groovy.runtime.metaclass.org.codehaus.groovy.ast

/**
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
class ASTNodeMetaClass extends DelegatingMetaClass {
	    ASTNodeMetaClass(MetaClass meta) {
        super(meta);
    }

    Object invokeMethod(Object object, String method, Object[] arguments) {
        print("INVOKE: " + object + ", " + method + "[ ")
        
        arguments.each{
            it->print( it + ",")
        }
        
        println("]")
        
        super.invokeMethod object, method, arguments
        
    }
}

