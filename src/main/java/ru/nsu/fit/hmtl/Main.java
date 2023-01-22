package ru.nsu.fit.hmtl;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import ru.nsu.fit.hmtl.core.ExecutionContext;
import ru.nsu.fit.hmtl.core.StlExecutionContext;
import ru.nsu.fit.hmtl.core.typesystem.TypeUtils;
import ru.nsu.fit.hmtl.core.typesystem.context.StlTypeContext;
import ru.nsu.fit.hmtl.core.typesystem.context.TypeContext;
import ru.nsu.fit.hmtl.parsing.ClojureLexer;
import ru.nsu.fit.hmtl.parsing.ClojureParser;
import ru.nsu.fit.hmtl.parsing.HMTLListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Scanner;


public class Main {

	@SuppressWarnings("InfiniteLoopStatement")
	public static void main(String[] args) throws URISyntaxException {

		TypeContext tctx = StlTypeContext.getInstance().createSubContext();
		ExecutionContext ectx = StlExecutionContext.getInstance().createSubContext();
		executeSTL(tctx, ectx);
		executeSource(tctx, ectx, new File(
				ClassLoader.getSystemClassLoader().getResource("hmtl/example.hmtl").toURI()));

	    Scanner scanner = new Scanner(System.in);
		while (true) {

			String line = scanner.nextLine();

			try {
				executeLine(tctx, ectx, line);
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}
	}

	@SuppressWarnings("ConstantConditions")
	private static void executeSTL(TypeContext tctx, ExecutionContext ectx) {
		try {
			File file = new File(
					ClassLoader.getSystemClassLoader().getResource("hmtl/stl.hmtl").toURI());
			executeSource(tctx, ectx, file);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			System.err.println(Arrays.toString(e.getStackTrace()));
		}
	}

	private static void executeSource(TypeContext tctx, ExecutionContext ectx, File source) {
		try
		{
			InputStream inputStream = new FileInputStream(source);
			Scanner sc = new Scanner(inputStream);
			while (sc.hasNext()) {
				executeLine(tctx, ectx, sc.nextLine());
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
			System.err.println(Arrays.toString(e.getStackTrace()));
		}
	}

	private static void executeLine(TypeContext tctx, ExecutionContext ectx, String line) {
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
			System.out.println();
			System.out.println(TypeUtils.generatePrettyName(expr.getType()));
		}
	}
}