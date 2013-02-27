/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.mihosoft.vrl.instrumentation;

import org.codehaus.groovy.transform.ASTTransformation;
import org.codehaus.groovy.transform.GroovyASTTransformation;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.control.CompilePhase;
import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.ClassCodeExpressionTransformer;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.CodeVisitorSupport;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.expr.ArgumentListExpression;
import org.codehaus.groovy.ast.expr.BinaryExpression;
import org.codehaus.groovy.ast.expr.ClosureExpression;
import org.codehaus.groovy.ast.expr.ConstantExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.MethodCallExpression;
import org.codehaus.groovy.ast.expr.StaticMethodCallExpression;
import org.codehaus.groovy.ast.expr.VariableExpression;
import org.codehaus.groovy.ast.stmt.BlockStatement;
import org.codehaus.groovy.classgen.Verifier;

/**
 * Adds instrumentation to each method call. Use {@link VRLInstrumentation} to
 * request this transformation.
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
@GroovyASTTransformation(phase = CompilePhase.SEMANTIC_ANALYSIS)
public class VRLInstrumentationTransformation implements ASTTransformation {

    @Override
    public void visit(ASTNode[] astNodes, SourceUnit sourceUnit) {
        
        System.out.println("VISIT: " + sourceUnit);

        // create transformer instance
        MethodCallExpressionTransformer transformer =
                new MethodCallExpressionTransformer(sourceUnit);

//        InstrumentVisitor visitor = new InstrumentVisitor(null);


        // apply transformation for each class in the specified source unit
        for (ClassNode clsNode : sourceUnit.getAST().getClasses()) {
            transformer.visitClass(clsNode);


//            for (MethodNode m : clsNode.getMethods()) {
//                if (m.getCode() instanceof BlockStatement) {
//                    visitor.visitBlockStatement((BlockStatement) m.getCode());
//                }
//            }

        }
    } 
}

//class InstrumentVisitor extends CodeVisitorSupport {
//
////    @Override
////    public void visitMethodCallExpression(MethodCallExpression methodCall) {
////        methodCall.
////    }
//    
//    public Expression transform(Expression exp) {
//        
//        System.out.println(" --> transform: " + exp);
//        
//        // don't transform if expression is null
//        if (exp == null) {
//            return exp;
//        }
//
//        // we ignore other expressions as we only want to transform/instrument
//        // method calls
//        if (!(exp instanceof MethodCallExpression)) {
//            return exp;
//        }
//
//        // we have a method call
//        MethodCallExpression methodCall = (MethodCallExpression) exp;
//
//        // if the method is the special each(..) method which does not seem
//        // to belong to a specific type, show warning and ignore it,
//        // including it's closure
//        if (methodCall.getMethod().getText().equals("each")) {
//            System.out.println(">> WARNING: each() cannot be instrumented (unsupported):");
//            System.out.println(" --> begin: line " + exp.getLineNumber() + ", col " + exp.getColumnNumber());
//            System.out.println(" --> end:   line " + exp.getLastLineNumber() + ", col " + exp.getLastColumnNumber());
//
//            return exp;
//        }
//
//        // name of the object
//        String objName = methodCall.getObjectExpression().getText();
//        // name of the type of the object
//        String typeName = methodCall.getObjectExpression().getType().getName();
//
//        // if both are equal, static method call detected
//        boolean staticCall = typeName.equals(objName);
//
//        // create a new argument list with object the method belongs to
//        // as first parameter and the method name as second parameter
//        ArgumentListExpression finalArgs = new ArgumentListExpression(
//                new ConstantExpression(staticCall),
//                methodCall.getObjectExpression(),
//                new ConstantExpression(methodCall.getMethod().getText()));
//
//        // original method args
//        ArgumentListExpression mArgs =
//                (ArgumentListExpression) methodCall.getArguments();
//
//        // add original method args to argument list of the instrumentation
//        // method
//        for (Expression e : mArgs.getExpressions()) {
//            finalArgs.addExpression(e);
//        }
//
//        // create a static method call to the instrumentation method which
//        // calls the original method via reflection
//        return new StaticMethodCallExpression(
//                new ClassNode(GroovyAST01.class),
//                "__instrumentCode", finalArgs);
//    }
//}
/**
 * Concrete transformer class.
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
class MethodCallExpressionTransformer extends ClassCodeExpressionTransformer {

    private final SourceUnit unit;

    public MethodCallExpressionTransformer(final SourceUnit unit) {
        this.unit = unit;
    }

    @Override
    public Expression transform(Expression exp) {

//        System.out.println(" --> transform: " + exp);

        // don't transform if expression is null
        if (exp == null) {
            System.out.println(" --> NULL");
            return exp;
        }


        // we ignore other expressions as we only want to transform/instrument
        // method calls
        if (!(exp instanceof MethodCallExpression)) {
//            System.out.println(" --skipping expression: " + exp.getClass());

            return exp.transformExpression(this);
        }

        // we have a method call
        MethodCallExpression methodCall = (MethodCallExpression) exp;
        
        if (methodCall.getMethod().getText().equals("__instrumentCode")) {
            System.out.println(" --> skipping already instrumented code");
            return exp;
        }

        // original method args
        ArgumentListExpression mArgs =
                (ArgumentListExpression) methodCall.getArguments();
        
        // instrument argument list (possible method calls as arguments )
        mArgs = (ArgumentListExpression) mArgs.transformExpression(this);

        // instrument method args (possible method calls inside)
        ArgumentListExpression instrumentedMargs = new ArgumentListExpression();
        for (Expression mArg : mArgs.getExpressions()) {
            Expression instrumentedArg = mArg.transformExpression(this);
            instrumentedMargs.addExpression(instrumentedArg);
            System.out.println(" -> arg: " + instrumentedArg);
        }
        mArgs = instrumentedMargs;

        // if the method is the special each(..) method which does not seem
        // to belong to a specific type, show warning and ignore it,
        // including it's closure
        if (methodCall.getMethod().getText().equals("each")) {
            System.out.println(">> WARNING: each() cannot be instrumented (unsupported):");
            System.out.println(" --> begin: line " + exp.getLineNumber() + ", col " + exp.getColumnNumber());
            System.out.println(" --> end:   line " + exp.getLastLineNumber() + ", col " + exp.getLastColumnNumber());

            return exp;
        } else {
            System.out.println(">> instrumenting method " + methodCall.getMethod().getText() + " : " + methodCall.getClass());
        }

        // name of the object
        String objName = methodCall.getObjectExpression().getText();
        // name of the type of the object
        String typeName = methodCall.getObjectExpression().getType().getName();

        // if both are equal, static method call detected
        boolean staticCall = typeName.equals(objName);

        // create a new argument list with object the method belongs to
        // as first parameter and the method name as second parameter
        ArgumentListExpression finalArgs = new ArgumentListExpression(
                new ConstantExpression(staticCall),
                methodCall.getObjectExpression(),
                new ConstantExpression(methodCall.getMethod().getText()));


        // add original method args to argument list of the instrumentation
        // method
        for (Expression e : mArgs.getExpressions()) {

            finalArgs.addExpression(e);
        }

        // create a static method call to the instrumentation method which
        // calls the original method via reflection
        return new StaticMethodCallExpression(
                new ClassNode(VRLInstrumentationUtil.class),
                "__instrumentCode", finalArgs);
    }

    @Override
    protected SourceUnit getSourceUnit() {
        return unit;
    }
}

class InstrumentVisitor extends CodeVisitorSupport {

//        private Map<MethodNode, BlockStatement> methodBodies = new HashMap<>();
//        private VariableScope parentScope;
    private MethodNode methodNode;

    public InstrumentVisitor(MethodNode methodNode) {
        this.methodNode = methodNode;
    }

    @Override
    public void visitBlockStatement(BlockStatement bs) {
//            System.out.println("enter block: " + bs.getText());
//            parentScope = bs.getVariableScope();
//            getMethodBodies().put(methodNode, bs);
        super.visitBlockStatement(bs);
    }

    @Override
    public void visitMethodCallExpression(MethodCallExpression methodCall) {

        methodCall.visit(new MethodCallExpressionTransformer(null));

        System.out.println("enter method: " + methodCall.getText());

        System.out.println(" --> method: " + methodCall.getMethod().getText());

        System.out.println(" --> object: " + methodCall.getObjectExpression().getText());
        System.out.println(" --> type: " + methodCall.getObjectExpression().getType().getName());

        String objName = methodCall.getObjectExpression().getText();
        String typeName = methodCall.getObjectExpression().getType().getName();


//            boolean isClassObject = typeName.equals(objName);
//
//            if (isClassObject) {
//                System.out.println(" --> static method");
//            } else {
//                System.out.println(" --> object method");
//            }


//            super.visitBlockStatement(createInstrumentedCall("start " + methodCall.getText(), "stop " + methodCall.getText(), methodCall, parentScope));
//            super.visitMethodCallExpression(methodCall);
    }
}
