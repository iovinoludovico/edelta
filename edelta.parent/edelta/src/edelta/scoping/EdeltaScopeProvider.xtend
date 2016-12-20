/*
 * generated by Xtext 2.10.0
 */
package edelta.scoping

import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import edelta.edelta.EdeltaPackage
import edelta.edelta.EdeltaProgram
import org.eclipse.xtext.scoping.Scopes

/**
 * This class contains custom scoping description.
 * 
 * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#scoping
 * on how and when to use it.
 */
class EdeltaScopeProvider extends AbstractEdeltaScopeProvider {

	override getScope(EObject context, EReference reference) {
		if (reference == EdeltaPackage.Literals.EDELTA_PROGRAM__ECLASSES) {
			val prog = context as EdeltaProgram
			return Scopes.scopeFor(
				prog.metamodels.map[
					EClassifiers
				].flatten
			)
		} else if (reference == EdeltaPackage.Literals.EDELTA_PROGRAM__METAMODELS) {
			val del = delegateGetScope(context, reference)
//			println(del)
		}
		super.getScope(context, reference)
	}
	
}
