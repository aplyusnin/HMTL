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
import java.util.Scanner;


public class Main {

	public static void main(String[] args) throws IOException {
	    Scanner scanner = new Scanner(System.in);

		TypeContext tctx = StlTypeContext.getInstance().createSubContext();
		ExecutionContext ectx = StlExecutionContext.getInstance().createSubContext();

		while (true) {

			String line = scanner.nextLine();

			try {
				ClojureLexer myLangLexer = new ClojureLexer(CharStreams.fromString(line));
				ClojureParser myLangParser = new ClojureParser(new CommonTokenStream(myLangLexer));
				var tree = myLangParser.file_();

				ParseTreeWalker walker = new ParseTreeWalker();
				HMTLListener listener = new HMTLListener();
				walker.walk(listener, tree);

				var firstLevelChildren = listener.getTree();

				for (var root : firstLevelChildren) {
					root.inferTypes(tctx);
					root.updateTypes(tctx);
					root.generify(tctx);

					var expr = root.generateExpression();
					var res = expr.eval(ectx);
					System.out.println(expr.getType().getName());

				}
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}
	}
}

// (print (append (append (list) 3) 4))

// (print ((if (= 2 3) (+ 3) (- 4)) 2))