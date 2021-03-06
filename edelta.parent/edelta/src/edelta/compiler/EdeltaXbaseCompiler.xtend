package edelta.compiler

import com.google.inject.Inject
import edelta.edelta.EdeltaEcoreReferenceExpression
import org.eclipse.xtext.xbase.XExpression
import org.eclipse.xtext.xbase.compiler.XbaseCompiler
import org.eclipse.xtext.xbase.compiler.output.ITreeAppendable

class EdeltaXbaseCompiler extends XbaseCompiler {

	@Inject extension EdeltaCompilerUtil

	override protected doInternalToJavaStatement(XExpression obj, ITreeAppendable appendable, boolean isReferenced) {
		switch (obj) {
			EdeltaEcoreReferenceExpression: {
				compileAsStatementIfNotReferenced(appendable, isReferenced) [
					compileEdeltaEcoreReferenceExpression(obj, appendable)
				]
			}
			default:
				super.doInternalToJavaStatement(obj, appendable, isReferenced)
		}
	}

	override protected internalToConvertedExpression(XExpression obj, ITreeAppendable appendable) {
		switch (obj) {
			EdeltaEcoreReferenceExpression: {
				compileEdeltaEcoreReferenceExpression(obj, appendable)
			}
			default:
				super.internalToConvertedExpression(obj, appendable)
		}
	}

	private def void compileEdeltaEcoreReferenceExpression(EdeltaEcoreReferenceExpression obj, ITreeAppendable appendable) {
		appendable.append(obj.stringForEcoreReferenceExpression)
	}

	private def void compileAsStatementIfNotReferenced(ITreeAppendable appendable, boolean isReferenced,
		()=>void compileLambda) {
		if (!isReferenced) {
			appendable.newLine
			compileLambda.apply
			appendable.append(";")
		}
	}

}
