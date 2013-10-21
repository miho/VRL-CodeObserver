/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.mihosoft.vrl.instrumentation;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupString;

/**
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
public class Scope2Code {

    private final Scope scope;

    public Scope2Code(Scope scope) {
        this.scope = scope;
    }

    public String getCode() {
        StringBuilder builder = new StringBuilder();

//        scope.g
        return builder.toString();
    }

    public static void main(String[] args) {
//        Invocation e = new InvocationImpl("1", "obj1", "m1", false, true, "unknown", new VariableImpl(null, new Type("java.lang.Class<?>"), "myVar", null, false), new VariableImpl(null, new Type("int"), "3", null, true));

//        InvocationCodeRenderer invocationCodeRenderer = new InvocationCodeRenderer();
//        System.out.println(invocationCodeRenderer.render(e));
    }
}

interface ScopeRenderer extends CodeRenderer<Scope> {

    public void setInvocationRenderer(CodeRenderer<Invocation> renderer);
}

class ScopeRendererImpl implements ScopeRenderer {

    private CodeRenderer<Invocation> invocationRenderer;

    @Override
    public void setInvocationRenderer(CodeRenderer<Invocation> renderer) {
        this.invocationRenderer = renderer;
    }

    @Override
    public String render(Scope e) {

        StringBuilder builder = new StringBuilder();

//        builder.append()
        switch (e.getType()) {
            case CLASS:
                renderClass(e, builder);
                break;
            case METHOD:
                renderMethod(e, builder);
                break;
            default:
                renderUnsupported(e, builder);
        }

        return builder.toString();
    }

    private void renderClass(Scope s, StringBuilder builder) {
//        builder.append();
    }

    private void renderMethod(Scope s, StringBuilder builder) {

//        builder.append(s.)
    }

    private void renderUnsupported(Scope s, StringBuilder builder) {
        throw new UnsupportedOperationException("TODO NB-AUTOGEN: Not supported yet."); // TODO NB-AUTOGEN
    }

    @Override
    public void render(Scope e, CodeBuilder cb) {
        throw new UnsupportedOperationException("TODO NB-AUTOGEN: Not supported yet."); // TODO NB-AUTOGEN
    }

}

class InvocationCodeRenderer implements CodeRenderer<Invocation> {

//    private static final STGroup group = new STGroupString(
//            "invocation(var,name,params) ::= \"<var>.<name>(<params>);\"\n"
//            + "scope"
//    );
//
//    private static final ST invocation = group.getInstanceOf("invocation");
    public InvocationCodeRenderer(CodeRenderer<Scope> scopeRenderer) {
    }

//    @Override
//    public String render(Invocation e) {
//
//        if (e.isConstructor()) {
//
//        } else if (e.isScope()) {
//            invocation.add("var", e.getVarName());
//            invocation.add("name", e.getMethodName());
//            invocation.add("params", e.getArguments());
//        }
//
//        return invocation.render();
//    }
    @Override
    public String render(Invocation e) {
        CodeBuilder cb = new CodeBuilder();
        render(e, cb);
        return cb.getCode();
    }

    @Override
    public void render(Invocation e, CodeBuilder cb) {

        if (e.isConstructor()) {
            cb.append("new ").append(e.getReturnValueName()).
                    append("= new").append(e.getVarName()).
                    append("(");
            renderParams(e, cb);
            cb.append(");");

        } else if (e.isScope()) {
            cb.
                    append(e.getVarName()).
                    append(".").
                    append(e.getMethodName()).append("(");
            renderParams(e, cb);

            cb.append(");");
        }

        cb.newLine();
    }

    private void renderParams(Invocation e, CodeBuilder cb) {
        boolean firstCall = true;
        for (Variable v : e.getArguments()) {

            if (firstCall) {
                firstCall = false;
            } else {
                cb.append(", ");
            }

            if (v.isConstant()) {
                cb.append(v.getValue().toString());
            } else {
                cb.append(v.getName());
            }
        }
    }
}

class ScopeCodeRendererImpl implements CodeRenderer<Scope> {

    @Override
    public String render(Scope e) {
        CodeBuilder cb = new CodeBuilder();
        render(e, cb);
        return cb.getCode();
    }

    public int var;

    @Override
    public void render(Scope e, CodeBuilder cb) {
        switch (e.getType()) {
            case CLASS:
                renderClass(e, cb);
                break;
            case CLOSURE:
                renderClosure(e, cb);
                break;
            case METHOD:
                renderMethod(e, cb);
                break;
            case FOR:
                renderFor(e, cb);
                break;
            case WHILE:
                renderWhile(e, cb);
                break;
            case IF:
                renderIf(e, cb);
                break;
            case ELSE:
                renderElse(e, cb);
                break;
            case NONE:
                renderNone(e, cb);
                break;
        }
    }

    private void renderClass(Scope e, CodeBuilder cb) {
        throw new UnsupportedOperationException("TODO NB-AUTOGEN: Not supported yet."); // TODO NB-AUTOGEN
    }

    private void renderClosure(Scope e, CodeBuilder cb) {
        throw new UnsupportedOperationException("TODO NB-AUTOGEN: Not supported yet."); // TODO NB-AUTOGEN
    }

    private void renderMethod(Scope e, CodeBuilder cb) {
//        cb.append(e.)
    }

    private void renderFor(Scope e, CodeBuilder cb) {
        throw new UnsupportedOperationException("TODO NB-AUTOGEN: Not supported yet."); // TODO NB-AUTOGEN
    }

    private void renderWhile(Scope e, CodeBuilder cb) {
        throw new UnsupportedOperationException("TODO NB-AUTOGEN: Not supported yet."); // TODO NB-AUTOGEN
    }

    private void renderIf(Scope e, CodeBuilder cb) {
        throw new UnsupportedOperationException("TODO NB-AUTOGEN: Not supported yet."); // TODO NB-AUTOGEN
    }

    private void renderElse(Scope e, CodeBuilder cb) {
        throw new UnsupportedOperationException("TODO NB-AUTOGEN: Not supported yet."); // TODO NB-AUTOGEN
    }

    private void renderNone(Scope e, CodeBuilder cb) {
        throw new UnsupportedOperationException("TODO NB-AUTOGEN: Not supported yet."); // TODO NB-AUTOGEN
    }

}

//class ScopeCodeRendererImpl implements CodeRenderer<Scope> {
//
//    private static final STGroup group = new STGroupString(
//            "class(accessModifier, name, superclass, methods)::=<<\n"
//            + "\n"
//            + "$accessModifier$ class $name$ extends $superclass$ {\n"
//            + "\n"
//            + "    $methods:method(); separator=\"\\n\"$\n"
//            + "\n"
//            + "}\n"
//            + ">>\n"
//            + "\n"
//            + "method(method)::=<<\n"
//            + "/**\n"
//            + " $method.comments$\n"
//            + "*/\n"
//            + "$method.accessModifier$ $method.returnType.name$ $name$ ($method.arguments:argument(); separator=\",\"$) {\n"
//            + "    $method.body$\n"
//            + "}\n"
//            + ">>\n"
//            + "\n"
//            + "argument(argument)::=<<\n"
//            + "$argument.type.name$ $argument.name$\n"
//            + ">>"
//    );
//
//    private static final ST invocation = group.getInstanceOf("invocation");
//
//    @Override
//    public String render(Scope e) {
//        return "";
//    }
//
//    public int var;
//
//}
