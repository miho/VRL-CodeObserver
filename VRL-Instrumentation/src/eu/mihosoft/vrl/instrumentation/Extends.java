/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.mihosoft.vrl.instrumentation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Michael Hoffer &lt;info@michaelhoffer.de&gt;
 */
public final class Extends implements IExtends {
    private final List<Type> types = new ArrayList<>();
    private List<Type> readOnlyTypes;

    public Extends(Type... types) {
        this.types.addAll(Arrays.asList(types));
    }

    @Override
    public List<Type> getTypes() {
        if (readOnlyTypes == null) {
            readOnlyTypes = Collections.unmodifiableList(types);
        }
        
        return readOnlyTypes;
    }
}
