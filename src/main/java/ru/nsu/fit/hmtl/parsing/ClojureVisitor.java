package ru.nsu.fit.hmtl.parsing;// Generated from Clojure.g4 by ANTLR 4.7
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link ClojureParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface ClojureVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link ClojureParser#file_}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFile_(ClojureParser.File_Context ctx);
	/**
	 * Visit a parse tree produced by {@link ClojureParser#form}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForm(ClojureParser.FormContext ctx);
	/**
	 * Visit a parse tree produced by {@link ClojureParser#forms}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForms(ClojureParser.FormsContext ctx);
	/**
	 * Visit a parse tree produced by {@link ClojureParser#list_}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitList_(ClojureParser.List_Context ctx);
	/**
	 * Visit a parse tree produced by {@link ClojureParser#vector}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVector(ClojureParser.VectorContext ctx);
	/**
	 * Visit a parse tree produced by {@link ClojureParser#map_}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMap_(ClojureParser.Map_Context ctx);
	/**
	 * Visit a parse tree produced by {@link ClojureParser#set_}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSet_(ClojureParser.Set_Context ctx);
	/**
	 * Visit a parse tree produced by {@link ClojureParser#reader_macro}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReader_macro(ClojureParser.Reader_macroContext ctx);
	/**
	 * Visit a parse tree produced by {@link ClojureParser#quote}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQuote(ClojureParser.QuoteContext ctx);
	/**
	 * Visit a parse tree produced by {@link ClojureParser#backtick}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBacktick(ClojureParser.BacktickContext ctx);
	/**
	 * Visit a parse tree produced by {@link ClojureParser#unquote}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnquote(ClojureParser.UnquoteContext ctx);
	/**
	 * Visit a parse tree produced by {@link ClojureParser#unquote_splicing}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnquote_splicing(ClojureParser.Unquote_splicingContext ctx);
	/**
	 * Visit a parse tree produced by {@link ClojureParser#tag}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTag(ClojureParser.TagContext ctx);
	/**
	 * Visit a parse tree produced by {@link ClojureParser#deref}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeref(ClojureParser.DerefContext ctx);
	/**
	 * Visit a parse tree produced by {@link ClojureParser#gensym}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGensym(ClojureParser.GensymContext ctx);
	/**
	 * Visit a parse tree produced by {@link ClojureParser#lambda_}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLambda_(ClojureParser.Lambda_Context ctx);
	/**
	 * Visit a parse tree produced by {@link ClojureParser#meta_data}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMeta_data(ClojureParser.Meta_dataContext ctx);
	/**
	 * Visit a parse tree produced by {@link ClojureParser#var_quote}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVar_quote(ClojureParser.Var_quoteContext ctx);
	/**
	 * Visit a parse tree produced by {@link ClojureParser#host_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHost_expr(ClojureParser.Host_exprContext ctx);
	/**
	 * Visit a parse tree produced by {@link ClojureParser#discard}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDiscard(ClojureParser.DiscardContext ctx);
	/**
	 * Visit a parse tree produced by {@link ClojureParser#dispatch}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDispatch(ClojureParser.DispatchContext ctx);
	/**
	 * Visit a parse tree produced by {@link ClojureParser#regex}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRegex(ClojureParser.RegexContext ctx);
	/**
	 * Visit a parse tree produced by {@link ClojureParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteral(ClojureParser.LiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link ClojureParser#boolean_}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolean_(ClojureParser.Boolean_Context ctx);
	/**
	 * Visit a parse tree produced by {@link ClojureParser#string_}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitString_(ClojureParser.String_Context ctx);
	/**
	 * Visit a parse tree produced by {@link ClojureParser#hex_}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHex_(ClojureParser.Hex_Context ctx);
	/**
	 * Visit a parse tree produced by {@link ClojureParser#bin_}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBin_(ClojureParser.Bin_Context ctx);
	/**
	 * Visit a parse tree produced by {@link ClojureParser#bign}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBign(ClojureParser.BignContext ctx);
	/**
	 * Visit a parse tree produced by {@link ClojureParser#number}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumber(ClojureParser.NumberContext ctx);
	/**
	 * Visit a parse tree produced by {@link ClojureParser#character}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCharacter(ClojureParser.CharacterContext ctx);
	/**
	 * Visit a parse tree produced by {@link ClojureParser#named_char}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNamed_char(ClojureParser.Named_charContext ctx);
	/**
	 * Visit a parse tree produced by {@link ClojureParser#any_char}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAny_char(ClojureParser.Any_charContext ctx);
	/**
	 * Visit a parse tree produced by {@link ClojureParser#u_hex_quad}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitU_hex_quad(ClojureParser.U_hex_quadContext ctx);
	/**
	 * Visit a parse tree produced by {@link ClojureParser#nil_}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNil_(ClojureParser.Nil_Context ctx);
	/**
	 * Visit a parse tree produced by {@link ClojureParser#keyword}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitKeyword(ClojureParser.KeywordContext ctx);
	/**
	 * Visit a parse tree produced by {@link ClojureParser#simple_keyword}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimple_keyword(ClojureParser.Simple_keywordContext ctx);
	/**
	 * Visit a parse tree produced by {@link ClojureParser#macro_keyword}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMacro_keyword(ClojureParser.Macro_keywordContext ctx);
	/**
	 * Visit a parse tree produced by {@link ClojureParser#symbol}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSymbol(ClojureParser.SymbolContext ctx);
	/**
	 * Visit a parse tree produced by {@link ClojureParser#simple_sym}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimple_sym(ClojureParser.Simple_symContext ctx);
	/**
	 * Visit a parse tree produced by {@link ClojureParser#ns_symbol}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNs_symbol(ClojureParser.Ns_symbolContext ctx);
	/**
	 * Visit a parse tree produced by {@link ClojureParser#param_name}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParam_name(ClojureParser.Param_nameContext ctx);
}