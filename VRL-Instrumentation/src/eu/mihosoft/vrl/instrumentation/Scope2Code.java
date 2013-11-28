/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.mihosoft.vrl.instrumentation;

//import org.stringtemplate.v4.ST;
import java.util.HashMap;
import java.util.Map;

//import org.stringtemplate.v4.STGroup;
//import org.stringtemplate.v4.STGroupString;
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
        CompilationUnitDeclaration scope = demoScope();

        CompilationUnitRenderer renderer
                = new CompilationUnitRenderer(
                        new ClassDeclarationRenderer(
                                new MethodDeclarationRenderer(
                                        new InvocationCodeRenderer())));

        System.out.println(renderer.render(scope));
    }

    public static CompilationUnitDeclaration demoScope() {
        VisualCodeBuilder builder = new VisualCodeBuilder_Impl();

        CompilationUnitDeclaration myFile = builder.declareCompilationUnit("MyFile.java");
        ClassDeclaration myFileClass = builder.declareClass(myFile,
                new Type("my.testpackage.MyFileClass"),
                new Modifiers(Modifier.PUBLIC), new Extends(), new Extends());

        MethodDeclaration m1 = builder.declareMethod(myFileClass,
                new Modifiers(Modifier.PUBLIC), new Type("int"), "m1",
                new Parameters(new Parameter(new Type("double"), "v1")));

        builder.invokeMethod(m1, "this", m1.getName(), true, "retM1", m1.getVariable("v1"));
        builder.invokeMethod(m1, "this", m1.getName(), true, "retM1", m1.getVariable("v1"));

        MethodDeclaration m2 = builder.declareMethod(myFileClass,
                new Modifiers(Modifier.PUBLIC), new Type("int"), "m2",
                new Parameters(new Parameter(new Type("double"), "v1")));

        return myFile;
    }

}

final class Utils {

    private static Map<Modifier, String> modifierNames = new HashMap<>();

    private Utils() {
        throw new AssertionError();
    }

    static {
        modifierNames.put(Modifier.ABSTRACT, "abstract");
        modifierNames.put(Modifier.FINAL, "final");

        modifierNames.put(Modifier.PRIVATE, "private");
        modifierNames.put(Modifier.PROTECTED, "protected");

        modifierNames.put(Modifier.PUBLIC, "public");
        modifierNames.put(Modifier.STATIC, "static");
    }

    public static String modifierToName(Modifier m) {

        return modifierNames.get(m);
    }
}

interface ScopeRenderer extends CodeRenderer<Scope> {

    public void setCompilationUnitRenderer(CodeRenderer<CompilationUnitDeclaration> renderer);

    public void setClassDeclarationRenderer(CodeRenderer<ClassDeclaration> renderer);

    public void setMethodDeclarationRenderer(CodeRenderer<MethodDeclaration> renderer);

    public void setInvocationRenderer(CodeRenderer<Invocation> renderer);

    public void setForLoopRenderer(CodeRenderer<ForDeclaration> renderer);

    public void setWhileLoopRenderer(CodeRenderer<WhileDeclaration> renderer);

//    public void setIfStatementRenderer(CodeRenderer)
}

class ScopeRendererImpl implements ScopeRenderer {

    private CodeRenderer<CompilationUnitDeclaration> compilationUnitRenderer;
    private CodeRenderer<ClassDeclaration> classDeclarationRenderer;
    private CodeRenderer<MethodDeclaration> methodDeclarationRenderer;
    private CodeRenderer<Invocation> invocationRenderer;
    private CodeRenderer<ForDeclaration> forLoopRenderer;
    private CodeRenderer<WhileDeclaration> whileLoopRenderer;

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

    /**
     * @param compilationUnitRenderer the compilationUnitRenderer to set
     */
    @Override
    public void setCompilationUnitRenderer(CodeRenderer<CompilationUnitDeclaration> compilationUnitRenderer) {
        this.compilationUnitRenderer = compilationUnitRenderer;
    }

    /**
     * @param classDeclarationRenderer the classDeclarationRenderer to set
     */
    @Override
    public void setClassDeclarationRenderer(CodeRenderer<ClassDeclaration> classDeclarationRenderer) {
        this.classDeclarationRenderer = classDeclarationRenderer;
    }

    /**
     * @param methodDeclarationRenderer the methodDeclarationRenderer to set
     */
    @Override
    public void setMethodDeclarationRenderer(CodeRenderer<MethodDeclaration> methodDeclarationRenderer) {
        this.methodDeclarationRenderer = methodDeclarationRenderer;
    }

    /**
     * @param forLoopRenderer the forLoopRenderer to set
     */
    @Override
    public void setForLoopRenderer(CodeRenderer<ForDeclaration> forLoopRenderer) {
        this.forLoopRenderer = forLoopRenderer;
    }

    /**
     * @param whileLoopRenderer the whileLoopRenderer to set
     */
    @Override
    public void setWhileLoopRenderer(CodeRenderer<WhileDeclaration> whileLoopRenderer) {
        this.whileLoopRenderer = whileLoopRenderer;
    }

}

class InvocationCodeRenderer implements CodeRenderer<Invocation> {

//    private static final STGroup group = new STGroupString(
//            "invocation(var,name,params) ::= \"<var>.<name>(<params>);\"\n"
//            + "scope"
//    );
//
//    private static final ST invocation = group.getInstanceOf("invocation");
    public InvocationCodeRenderer() {
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

        } else if (!e.isScope()) {
            cb.
                    append(e.getVarName()).
                    append(".").
                    append(e.getMethodName()).append("(");
            renderParams(e, cb);

            cb.append(");");
        } else {
            // scope
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

class ClassDeclarationRenderer implements CodeRenderer<ClassDeclaration> {

    private CodeRenderer<MethodDeclaration> methodDeclarationRenderer;

    public ClassDeclarationRenderer(CodeRenderer<MethodDeclaration> methodDeclarationRenderer) {
        this.methodDeclarationRenderer = methodDeclarationRenderer;
    }

    @Override
    public String render(ClassDeclaration cd) {
        CodeBuilder cb = new CodeBuilder();

        render(cd, cb);

        return cb.getCode();
    }

    @Override
    public void render(ClassDeclaration cd, CodeBuilder cb) {

        createModifiers(cd, cb);
        cb.append("class ");
        cb.append(cd.getName());
        createExtends(cd, cb);
        createImplements(cd, cb);
        cb.append(" {").newLine();
        cb.incIndentation();

        createDeclaredVariables(cd, cb);

        for (MethodDeclaration md : cd.getDeclaredMethods()) {
            methodDeclarationRenderer.render(md, cb);
        }

        cb.decIndentation();
        cb.newLine().append("}").newLine();
    }

    private void createDeclaredVariables(ClassDeclaration cd, CodeBuilder cb) {
        for (Variable v : cd.getVariables()) {
            cb.newLine().append(v.getType().getFullClassName()).
                    append(" ").append(v.getName()).append(";");
        }
    }

    private void createModifiers(ClassDeclaration cd, CodeBuilder cb) {
        for (Modifier m : cd.getClassModifiers().getModifiers()) {
            cb.append(Utils.modifierToName(m)).append(" ");
        }
    }

    private void createExtends(ClassDeclaration cd, CodeBuilder cb) {

        if (cd.getExtends().getTypes().isEmpty()) {
            return;
        }

        cb.append(" extends ");

        boolean first = true;

        for (IType type : cd.getExtends().getTypes()) {
            if (first) {
                first = false;
            } else {
                cb.append(", ");
            }
            cb.append(type.getFullClassName());
        }
    }

    private void createImplements(ClassDeclaration cd, CodeBuilder cb) {

        if (cd.getImplements().getTypes().isEmpty()) {
            return;
        }

        cb.append(" implements ");

        boolean first = true;

        for (IType type : cd.getExtends().getTypes()) {
            if (first) {
                first = false;
            } else {
                cb.append(", ");
            }
            cb.append(type.getFullClassName());
        }
    }
}

class MethodDeclarationRenderer implements CodeRenderer<MethodDeclaration> {

    private CodeRenderer<Invocation> invocationRenderer;

    public MethodDeclarationRenderer(CodeRenderer<Invocation> invocationRenderer) {
        this.invocationRenderer = invocationRenderer;
    }

    @Override
    public String render(MethodDeclaration e) {
        CodeBuilder cb = new CodeBuilder();

        render(e, cb);

        return cb.toString();
    }

    @Override
    public void render(MethodDeclaration e, CodeBuilder cb) {

        createModifiers(e, cb);
        cb.append(e.getReturnType().getFullClassName());
        cb.append(" ").append(e.getName()).append("(");
        renderParams(e, cb);
        cb.append(") {").newLine();
        cb.incIndentation();

        for (Invocation i : e.getControlFlow().getInvocations()) {
            invocationRenderer.render(i, cb);
        }

        cb.decIndentation().append("}").newLine();
    }

    private void createModifiers(MethodDeclaration md, CodeBuilder cb) {
        for (Modifier m : md.getModifiers().getModifiers()) {
            cb.append(Utils.modifierToName(m)).append(" ");
        }
    }

    private void renderParams(MethodDeclaration e, CodeBuilder cb) {
        boolean firstCall = true;
        for (IParameter v : e.getParameters().getParamenters()) {

            if (firstCall) {
                firstCall = false;
            } else {
                cb.append(", ");
            }

            cb.append(v.getType().getFullClassName()).append(" ").append(v.getName());
        }
    }
}

class CompilationUnitRenderer implements CodeRenderer<CompilationUnitDeclaration> {

    private CodeRenderer<ClassDeclaration> classDeclarationRenderer;

    public CompilationUnitRenderer() {
    }

    public CompilationUnitRenderer(CodeRenderer<ClassDeclaration> classDeclarationRenderer) {
        this.classDeclarationRenderer = classDeclarationRenderer;
    }

    @Override
    public String render(CompilationUnitDeclaration e) {
        CodeBuilder cb = new CodeBuilder();

        render(e, cb);

        return cb.getCode();
    }

    @Override
    public void render(CompilationUnitDeclaration e, CodeBuilder cb) {
        for (ClassDeclaration cd : e.getDeclaredClasses()) {
            classDeclarationRenderer.render(cd, cb);
        }
    }

    /**
     * @return the classDeclarationRenderer
     */
    public CodeRenderer<ClassDeclaration> getClassDeclarationRenderer() {
        return classDeclarationRenderer;
    }

    /**
     * @param classDeclarationRenderer the classDeclarationRenderer to set
     */
    public void setClassDeclarationRenderer(CodeRenderer<ClassDeclaration> classDeclarationRenderer) {
        this.classDeclarationRenderer = classDeclarationRenderer;
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
