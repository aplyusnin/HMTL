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

import java.util.Scanner;


public class Main {

	@SuppressWarnings("InfiniteLoopStatement")
	public static void main(String[] args) {
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

// (print (let [a (list) b (append a 5) c (concat b b)] (concat c c)))
// (defn a [b c d] (if b (c 1) (d 1)))
// (defn a :A [b :B c :C d :D] (if b (c 1) (d 1)))