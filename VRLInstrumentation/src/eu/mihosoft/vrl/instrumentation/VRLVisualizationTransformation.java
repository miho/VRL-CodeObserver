/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.mihosoft.vrl.instrumentation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.codehaus.groovy.transform.ASTTransformation;
import org.codehaus.groovy.transform.GroovyASTTransformation;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.control.CompilePhase;
import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.expr.ArgumentListExpression;
import org.codehaus.groovy.ast.expr.BinaryExpression;
import org.codehaus.groovy.ast.expr.ClassExpression;
import org.codehaus.groovy.ast.expr.ConstantExpression;
import org.codehaus.groovy.ast.expr.ConstructorCallExpression;
import org.codehaus.groovy.ast.expr.DeclarationExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.MethodCallExpression;
import org.codehaus.groovy.ast.expr.PropertyExpression;
import org.codehaus.groovy.ast.expr.VariableExpression;
import org.codehaus.groovy.ast.stmt.BlockStatement;
import org.codehaus.groovy.ast.stmt.EmptyStatement;
import org.codehaus.groovy.ast.stmt.ForStatement;
import org.codehaus.groovy.ast.stmt.IfStatement;
import org.codehaus.groovy.ast.stmt.Statement;
import org.codehaus.groovy.ast.stmt.WhileStatement;
import org.codehaus.groovy.transform.StaticTypesTransformation;

/**
 * Adds instrumentation to each method call. Use {@link VRLInstrumentation} to
 * request this transformation.
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
@GroovyASTTransformation(phase = CompilePhase.CANONICALIZATION)
public class VRLVisualizationTransformation implements ASTTransformation {

    @Override
    public void visit(ASTNode[] astNodes, SourceUnit sourceUnit) {

        StaticTypesTransformation transformation = new StaticTypesTransformation();
        transformation.visit(astNodes, sourceUnit);

        VisualCodeBuilder codeBuilder = new VisualCodeBuilder();

        Map<String, List<Scope>> scopes = new HashMap<>();

//        // apply transformation for each class in the specified source unit
        for (ClassNode clsNode : sourceUnit.getAST().getClasses()) {

            if (!scopes.containsKey(clsNode.getName())) {

                List<Scope> clsScopes = new ArrayList<>();
                scopes.put(clsNode.getName(), clsScopes);
            }

            ClassVisitor visitor = new ClassVisitor(sourceUnit, codeBuilder);
            clsNode.visitContents(visitor);
            scopes.get(clsNode.getName()).add(visitor.getRootScope());

        }

        for (String clazz : scopes.keySet()) {
            for (Scope s : scopes.get(clazz)) {
                System.out.println(s.toString());
            }
        }
    }
}

class ClassVisitor extends org.codehaus.groovy.ast.ClassCodeVisitorSupport {

    private SourceUnit sourceUnit;
    private VisualCodeBuilder codeBuilder;
    private Scope rootScope;
    private Scope currentScope;
    private Invocation lastMethod;
    

    public ClassVisitor(SourceUnit sourceUnit, VisualCodeBuilder codeBuilder) {
        this.sourceUnit = sourceUnit;
        this.codeBuilder = codeBuilder;
        this.rootScope = codeBuilder.createScope(null, ScopeType.NONE, new Object[0]);
        this.currentScope = rootScope;
    }

    @Override
    public void visitClass(ClassNode s) {

        currentScope = codeBuilder.createScope(currentScope, ScopeType.CLASS, new Object[0]);

        super.visitClass(s);

        currentScope = currentScope.getParent();
    }

    @Override
    public void visitMethod(MethodNode s) {

        currentScope = codeBuilder.createScope(currentScope, ScopeType.METHOD, new Object[0]);

        super.visitMethod(s);

        currentScope = currentScope.getParent();
    }

//    @Override
//    public void visitBlockStatement(BlockStatement s) {
//        System.out.println(" --> new Scope");
//        super.visitBlockStatement(s);
//        System.out.println(" --> leave Scope");
//    }
    
    @Override
    public void visitForLoop(ForStatement s) {
        System.out.println(" --> FOR-LOOP: " + s.getVariable());
        currentScope = codeBuilder.createScope(currentScope, ScopeType.FOR, new Object[0]);
        super.visitForLoop(s);
        currentScope = currentScope.getParent();
    }

    @Override
    public void visitWhileLoop(WhileStatement s) {
        System.out.println(" --> WHILE-LOOP: " + s.getBooleanExpression());
        currentScope = codeBuilder.createScope(currentScope, ScopeType.WHILE, new Object[0]);
        super.visitWhileLoop(s);
        currentScope = currentScope.getParent();
    }

    @Override
    public void visitIfElse(IfStatement ifElse) {
        System.out.println(" --> IF-STATEMENT: " + ifElse.getBooleanExpression());

        currentScope = codeBuilder.createScope(currentScope, ScopeType.IF, new Object[0]);

        ifElse.getBooleanExpression().visit(this);
        ifElse.getIfBlock().visit(this);

        currentScope = currentScope.getParent();

        currentScope = codeBuilder.createScope(currentScope, ScopeType.ELSE, new Object[0]);

        Statement elseBlock = ifElse.getElseBlock();
        if (elseBlock instanceof EmptyStatement) {
            // dispatching to EmptyStatement will not call back visitor, 
            // must call our visitEmptyStatement explicitly
            visitEmptyStatement((EmptyStatement) elseBlock);
        } else {
            elseBlock.visit(this);
        }

        currentScope = currentScope.getParent();

    }

    @Override
    public void visitConstructorCallExpression(ConstructorCallExpression s) {
//        System.out.println(" --> CONSTRUCTOR: " + s.getType());
//
//        super.visitConstructorCallExpression(s);
//
//        ArgumentListExpression args = (ArgumentListExpression) s.getArguments();
//
//        Variable[] arguments = new Variable[args.getExpressions().size()];
//
//        for (int i = 0; i < args.getExpressions().size(); i++) {
//            Expression e = args.getExpression(i);
//
//            Variable v = null;
//
//            if (e instanceof ConstantExpression) {
//                ConstantExpression ce = (ConstantExpression) e;
//
//                v = VariableFactory.createConstantVariable(currentScope, ce.getType().getName(), "", ce.getValue());
//            }
//
//            if (e instanceof VariableExpression) {
//                VariableExpression ve = (VariableExpression) e;
//
//                v = VariableFactory.createObjectVariable(currentScope, ve.getType().getName(), ve.getName());
//            }
//
//            if (v == null) {
//                v = VariableFactory.createObjectVariable(currentScope, "unknown", "don't know");
//            }
//
//            arguments[i] = v;
//        }
//
//        codeBuilder.createInstance(currentScope, s.getType().getName(),
//                codeBuilder.createVariable(currentScope, s.getType().getName()), arguments);


    }

    @Override
    public void visitMethodCallExpression(MethodCallExpression s) {
        System.out.println(" --> METHOD: " + s.getMethodAsString());

        super.visitMethodCallExpression(s);

        ArgumentListExpression args = (ArgumentListExpression) s.getArguments();

        Variable[] arguments = new Variable[args.getExpressions().size()];

        for (int i = 0; i < args.getExpressions().size(); i++) {
            Expression e = args.getExpression(i);

            Variable v = null;

            if (e instanceof ConstantExpression) {
                ConstantExpression ce = (ConstantExpression) e;

                v = VariableFactory.createConstantVariable(currentScope, ce.getType().getName(), "", ce.getValue());
            }

            if (e instanceof VariableExpression) {
                VariableExpression ve = (VariableExpression) e;

                v = VariableFactory.createObjectVariable(currentScope, ve.getType().getName(), ve.getName());
            }

            if (e instanceof PropertyExpression) {
                PropertyExpression pe = (PropertyExpression) e;

                v = VariableFactory.createObjectVariable(currentScope, "PROPERTYEXPR", "don't know");
            }

            if (v == null) {
                System.out.println("TYPE: " + e);
                v = VariableFactory.createObjectVariable(currentScope, "unknown", "don't know");
            }

            arguments[i] = v;
        }

        String objectName = "noname";

        if (s.getObjectExpression() instanceof VariableExpression) {
            VariableExpression ve = (VariableExpression) s.getObjectExpression();
            objectName = ve.getName();
        } else if (s.getObjectExpression() instanceof ClassExpression) {
            ClassExpression ce = (ClassExpression)s.getObjectExpression();
            objectName = ce.getType().getName();
        }

        String returnValueName = "void";

        boolean isVoid =false;

        if (!isVoid) {
            returnValueName = codeBuilder.createVariable(currentScope, "java.lang.Object");
        }

        codeBuilder.invokeMethod(currentScope, objectName, s.getMethod().getText(),isVoid,
                returnValueName, arguments);

    }

    @Override
    public void visitDeclarationExpression(DeclarationExpression s) {
        System.out.println(" --> DECLARATION: " + s.getVariableExpression());
        super.visitDeclarationExpression(s);
        codeBuilder.createVariable(currentScope, s.getVariableExpression().getType().getName(), s.getVariableExpression().getName());
    }

    @Override
    protected SourceUnit getSourceUnit() {
        return sourceUnit;
    }

    @Override
    public void visitBinaryExpression(BinaryExpression s) {
//        System.out.println(" --> BINARY: " + s.getOperation());

        super.visitBinaryExpression(s);
    }

    /**
     * @return the rootScope
     */
    public Scope getRootScope() {
        return rootScope;
    }

    /**
     * @param rootScope the rootScope to set
     */
    public void setRootScope(Scope rootScope) {
        this.rootScope = rootScope;
    }
}
