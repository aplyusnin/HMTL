package ru.nsu.fit.hmtl;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import ru.nsu.fit.hmtl.core.ExecutionContext;
import ru.nsu.fit.hmtl.core.StlExecutionContext;
import ru.nsu.fit.hmtl.core.typesystem.context.StlTypeContext;
import ru.nsu.fit.hmtl.core.typesystem.context.TypeContext;
import ru.nsu.fit.hmtl.parsing.ClojureLexer;
import ru.nsu.fit.hmtl.parsing.ClojureParser;
import ru.nsu.fit.hmtl.parsing.HMTLListener;

import java.io.FileInputStream;
import java.io.IOException;


public class Main {

	public static void main(String[] args) throws IOException {
		String filename;
		if (args.length >= 1) {
			filename = args[0];
		} else {
			System.out.println("File not provided");
			return;
		}


		ClojureLexer myLangLexer = new ClojureLexer(
				CharStreams.fromStream(new FileInputStream(filename)));
		ClojureParser myLangParser = new ClojureParser(new CommonTokenStream(myLangLexer));
		var tree = myLangParser.file_();

		ParseTreeWalker walker = new ParseTreeWalker();
		HMTLListener listener = new HMTLListener();
		walker.walk(listener, tree);


		TypeContext tctx = StlTypeContext.getInstance().createSubContext();
		ExecutionContext ectx = StlExecutionContext.getInstance().createSubContext();

		var firstLevelChildren = listener.getTree();

		for (var root : firstLevelChildren) {
			root.inferTypes(tctx);
			root.updateTypes(tctx);
			root.generify(tctx);

			var expr = root.generateExpression();
			var res = expr.eval(ectx);

			System.out.println(res);
		}
	}
}