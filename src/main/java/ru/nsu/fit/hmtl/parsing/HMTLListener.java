package ru.nsu.fit.hmtl.parsing;

import ru.nsu.fit.hmtl.core.Expression;
import ru.nsu.fit.hmtl.core.typesystem.context.TypeContext;
import ru.nsu.fit.hmtl.core.typesystem.table.TypeTable;
import ru.nsu.fit.hmtl.core.typesystem.types.Type;
import ru.nsu.fit.hmtl.source.tree.*;

import java.util.*;

public class HMTLListener extends ClojureBaseListener {

    private int lambdaCount = 0;

    private int variableAliasCount = 0;

    private final Map<String, Stack<String>> variableAlias = new HashMap<>();

    private final Stack<TreeNode> stack = new Stack<>();

    private class FakeLetNode extends TreeNode {

        private final Stack<String> aliases = new Stack<>();

        @Override
        public Expression generateExpression() {
            return null;
        }

        @Override
        protected Type inferTypesInternal(TypeContext ctx) {
            return null;
        }

        @Override
        protected void updateTypesInternal(TypeContext ctx) {

        }

        @Override
        protected void generifyInternal(TypeContext ctx) {

        }

        TreeNode getRoot() {
            TreeNode cur = children.get(children.size() - 1);
            for (int i = children.size() - 3; i >= 0; i -= 2) {
                TreeNode node = new LetNode();
                node.addChild(children.get(i));
                node.addChild(children.get(i + 1));
                node.addChild(cur);
                freeGeneratedAlias(aliases.pop());
                cur = node;
            }
            return cur;
        }

        void addAlias(String alias) {
            aliases.push(alias);
        }
    }

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
                && ctx.getChild(1).getChild(0).getText().equals("fn")) {
            String functionName = generateLambdaName();
            var node = new AbstractionNode();
            node.addChild(new VariableNode(functionName, TypeTable.getInstance().createVaryingType()));
            stack.peek().addChild(node);
            stack.push(node);
        } else if (ctx.children.size() != 0
                && ctx.getChild(1).getChild(0).getText().equals("let")) {
            var node = new FakeLetNode();

            stack.push(node);
        } else {
            var lexeme = new ApplicationNode();
            stack.peek().addChild(lexeme);
            stack.push(lexeme);
        }
    }

    @Override
    public void exitList_(ClojureParser.List_Context ctx) {
        if (stack.peek() instanceof AbstractionNode) {
            for (int i = 0; i < ctx.getChild(1).getChildCount() - 1; ++i) {
                if (ctx.getChild(1).getChild(i).getText().equals("defn")) {
                    i++; // skip function name
                    continue;
                }
                if (ctx.getChild(1).getChild(i).getText().equals("fn")) {
                    continue;
                }
                freeAlias(ctx.getChild(1).getChild(i).getText());
            }
        }
        if (ctx.children.size() != 0
                && ctx.getChild(1).getChild(0).getText().equals("let")) {
            FakeLetNode fakeLetNode = (FakeLetNode) stack.pop();
            TreeNode node = fakeLetNode.getRoot();
            stack.peek().addChild(node);
            stack.push(node);
        }
        stack.pop();
    }

    @Override
    public void enterVector(ClojureParser.VectorContext ctx) {

    }

    @Override
    public void exitVector(ClojureParser.VectorContext ctx) {

    }

    @Override
    public void enterMap_(ClojureParser.Map_Context ctx) {
        var node = new VariableNode(
                ctx.getChild(1).getText(),
                TypeTable.getInstance().getType(ctx.getChild(2).getText()));
        stack.peek().addChild(node);
        stack.push(node);
    }

    @Override
    public void exitMap_(ClojureParser.Map_Context ctx) {
        stack.pop();
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

        if (stack.peek() instanceof VariableNode || ctx.getText().equals("let") || ctx.getText().equals("defn") || ctx.getText().equals("fn")) return;
        if (stack.peek() instanceof AbstractionNode) {
            if (ctx.getParent().getParent().getParent().getChild(1).equals(ctx.getParent().getParent())
            && ctx.getParent().getParent().getParent().getChild(0).getText().equals("defn")) {
                stack.peek().addChild(new VariableNode(ctx.getText(), TypeTable.getInstance().createVaryingType()));
            } else {
                stack.peek().addChild(new VariableNode(generateAlias(ctx.getText()), TypeTable.getInstance().createVaryingType()));
            }
        } else if (stack.peek() instanceof FakeLetNode) {
            String alias = generateAlias(ctx.getText()); // todo
            for (int i = 1; i < ctx.getParent().getParent().getParent().getChildCount(); ++i) {
                if (ctx.getParent().getParent().getParent().getChild(i).equals(ctx.getParent().getParent())) {
                    stack.peek().addChild(new VariableNode(alias, TypeTable.getInstance().createVaryingType()));
                    ((FakeLetNode) stack.peek()).addAlias(alias);
                    return;
                }
            }
            stack.peek().addChild(new VariableNode(alias, TypeTable.getInstance().createVaryingType()));
        } else {
            stack.peek().addChild(new ConstantNode(getAlias(ctx.getText())));

        }
    }


    @Override
    public void enterBoolean_(ClojureParser.Boolean_Context ctx) {
        stack.peek().addChild(new ConstantNode(ctx.getText()));
    }


    private String generateLambdaName() {
        String name = "__lambda#" + lambdaCount;
        lambdaCount++;
        return name;
    }

    private String generateAlias(String name) {
        String alias = "__" + name + "#" + variableAliasCount;
        variableAliasCount++;
        if (!variableAlias.containsKey(name)) {
            variableAlias.put(name, new Stack<>());
        }
        variableAlias.get(name).push(alias);
        return alias;
    }

    private void freeAlias(String name) {
        variableAlias.get(name).pop();
    }

    private void freeGeneratedAlias(String name) {
        freeAlias(name.split("#")[0].substring(2));
    }

    private String getAlias(String name) {
        if (variableAlias.containsKey(name)) {
            return variableAlias.get(name).peek();
        }
        return name;
    }
}
