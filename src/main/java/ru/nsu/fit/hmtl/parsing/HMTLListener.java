package ru.nsu.fit.hmtl.parsing;

import ru.nsu.fit.hmtl.core.Expression;
import ru.nsu.fit.hmtl.core.typesystem.context.TypeContext;
import ru.nsu.fit.hmtl.core.typesystem.table.TypeTable;
import ru.nsu.fit.hmtl.core.typesystem.types.Type;
import ru.nsu.fit.hmtl.source.tree.*;

import java.util.List;
import java.util.Stack;

public class HMTLListener extends ClojureBaseListener {

    private final Stack<TreeNode> stack = new Stack<>();


    private static class RootNode extends TreeNode {

        @Override
        public Expression generateExpression() {
            return null;
        }

        @Override
        protected Type inferTypesInternal(TypeContext ctx) {
            return null;
        }

        @Override
        protected void updateTypesInternal(TypeContext ctx) {}

        @Override
        protected void generifyInternal(TypeContext ctx) {}

        public List<TreeNode> getChildren() {
            return children;
        }
    }

    public List<TreeNode> getTree() {
        if (stack.size() != 1 || !(stack.peek() instanceof RootNode)) {
            throw new RuntimeException();
        }
        return ((RootNode)stack.pop()).getChildren();
    }

    @Override
    public void enterFile_(ClojureParser.File_Context ctx) {
        stack.push(new RootNode());
    }

    // Despite its name, this method is responsible for application and any other parentheses
    @Override
    public void enterList_(ClojureParser.List_Context ctx) {

        if (ctx.children.size() != 0
                && ctx.getChild(1).getChild(0).getText().equals("defn")
                ) {
            var node = new AbstractionNode();
            stack.peek().addChild(node);
            stack.push(node);
        } else if (ctx.children.size() != 0
                && ctx.getChild(1).getChild(0).getText().equals("let")) {
            var node = new LetNode();
            stack.peek().addChild(node);
            stack.push(node);
        } else {
            var lexeme = new ApplicationNode();
            stack.peek().addChild(lexeme);
            stack.push(lexeme);
        }
    }

    @Override
    public void exitList_(ClojureParser.List_Context ctx) {
        stack.pop();
    }

    @Override
    public void enterVector(ClojureParser.VectorContext ctx) {
        var node = new VariableNode(
                ctx.getChild(1).getText(),
                TypeTable.getInstance().getType(ctx.getChild(2).getText()));
        stack.peek().addChild(node);
        stack.push(node);
    }

    @Override
    public void exitVector(ClojureParser.VectorContext ctx) {
        stack.pop();
    }

    @Override
    public void enterMap_(ClojureParser.Map_Context ctx) {
    }

    @Override
    public void exitMap_(ClojureParser.Map_Context ctx) {
    }

    @Override
    public void enterSet_(ClojureParser.Set_Context ctx) {
    }

    @Override
    public void exitSet_(ClojureParser.Set_Context ctx) {
    }

    @Override
    public void enterLambda_(ClojureParser.Lambda_Context ctx) {
        var lexeme = new AbstractionNode();
        stack.peek().addChild(lexeme);
        stack.push(lexeme);
    }

    @Override
    public void exitLambda_(ClojureParser.Lambda_Context ctx) {
        stack.pop();
    }

    @Override
    public void enterString_(ClojureParser.String_Context ctx) {
        var lexeme = new ConstantNode(ctx.getText());
        stack.peek().addChild(lexeme);
    }

    @Override
    public void exitString_(ClojureParser.String_Context ctx) {
    }

    @Override
    public void enterHex_(ClojureParser.Hex_Context ctx) {
    }

    @Override
    public void exitHex_(ClojureParser.Hex_Context ctx) {
    }

    @Override
    public void enterBin_(ClojureParser.Bin_Context ctx) {
    }

    @Override
    public void exitBin_(ClojureParser.Bin_Context ctx) {
    }

    @Override
    public void enterBign(ClojureParser.BignContext ctx) {
    }

    @Override
    public void enterNumber(ClojureParser.NumberContext ctx) {
        stack.peek().addChild(new ConstantNode(ctx.getText()));
    }

    @Override
    public void enterCharacter(ClojureParser.CharacterContext ctx) {
        stack.peek().addChild(new ConstantNode(ctx.getText()));
    }


    @Override
    public void enterSymbol(ClojureParser.SymbolContext ctx) {

        if (stack.peek() instanceof VariableNode || ctx.getText().equals("let") || ctx.getText().equals("defn")) return;
        if (stack.peek() instanceof AbstractionNode) {
            if (ctx.getParent().getParent().getParent().getChild(1).equals(ctx.getParent().getParent())) {
                stack.peek().addChild(new VariableNode(ctx.getText(), TypeTable.getInstance().createVaryingType()));
            } else {
                stack.peek().addChild(new VariableNode(ctx.getText(), TypeTable.getInstance().createVaryingType()));
            }
        } else if (stack.peek() instanceof LetNode && ctx.getParent().getParent().getParent().getChild(1).equals(ctx.getParent().getParent())) {
            stack.peek().addChild(new VariableNode(ctx.getText(), TypeTable.getInstance().createVaryingType()));
        } else {
            stack.peek().addChild(new ConstantNode(ctx.getText()));

        }
    }


    @Override
    public void enterBoolean_(ClojureParser.Boolean_Context ctx) {
        stack.peek().addChild(new ConstantNode(ctx.getText()));
    }



}
