package edelta.tests;

import edelta.edelta.EdeltaPackage;
import edelta.edelta.EdeltaProgram;
import edelta.lib.AbstractEdelta;
import edelta.tests.EdeltaAbstractTest;
import edelta.tests.EdeltaInjectorProviderCustom;
import edelta.validation.EdeltaValidator;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.diagnostics.Diagnostic;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.ObjectExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(XtextRunner.class)
@InjectWith(EdeltaInjectorProviderCustom.class)
@SuppressWarnings("all")
public class EdeltaValidatorTest extends EdeltaAbstractTest {
  @Test
  public void testEmptyProgram() {
    try {
      StringConcatenation _builder = new StringConcatenation();
      this._validationTestHelper.assertNoErrors(this._parseHelper.parse(_builder));
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @Test
  public void testCanReferToMetamodel() {
    this._validationTestHelper.assertNoErrors(this.parseWithTestEcore(this._inputs.referenceToMetamodel()));
  }
  
  @Test
  public void testUseImportedJavaTypes() {
    try {
      this._validationTestHelper.assertNoErrors(this._parseHelper.parse(this._inputs.useImportedJavaTypes()));
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @Test
  public void testReferenceToCreatedEClass() {
    this._validationTestHelper.assertNoErrors(this.parseWithTestEcore(this._inputs.referenceToCreatedEClass()));
  }
  
  @Test
  public void testReferenceToCreatedEAttribute() {
    this._validationTestHelper.assertNoErrors(this.parseWithTestEcore(this._inputs.referenceToCreatedEAttributeRenamed()));
  }
  
  @Test
  public void testValidUseAs() {
    try {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("import edelta.tests.additional.MyCustomEdelta;");
      _builder.newLine();
      _builder.append("use MyCustomEdelta as foo");
      _builder.newLine();
      this._validationTestHelper.assertNoIssues(this._parseHelper.parse(_builder));
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @Test
  public void testInvalidUseAsNotAnEdelta() {
    try {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("import java.util.List;");
      _builder.newLine();
      _builder.append("use List as foo");
      _builder.newLine();
      final String input = _builder.toString();
      EdeltaProgram _parse = this._parseHelper.parse(input);
      int _lastIndexOf = input.lastIndexOf("List");
      String _name = AbstractEdelta.class.getName();
      String _plus = ("Not a valid type: must be an " + _name);
      this._validationTestHelper.assertError(_parse, 
        EdeltaPackage.Literals.EDELTA_USE_AS, 
        EdeltaValidator.TYPE_MISMATCH, _lastIndexOf, 4, _plus);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @Test
  public void testInvalidUseAsAbstractEdelta() {
    try {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("import edelta.tests.additional.MyCustomAbstractEdelta;");
      _builder.newLine();
      _builder.append("use MyCustomAbstractEdelta as foo");
      _builder.newLine();
      final String input = _builder.toString();
      this._validationTestHelper.assertError(this._parseHelper.parse(input), 
        EdeltaPackage.Literals.EDELTA_USE_AS, 
        EdeltaValidator.TYPE_MISMATCH, 
        input.lastIndexOf("MyCustomAbstractEdelta"), "MyCustomAbstractEdelta".length(), 
        "Cannot be an abstract type");
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @Test
  public void testInvalidUseAsUnresolvedProxy() {
    try {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("use Unknown as foo");
      _builder.newLine();
      final String input = _builder.toString();
      this.assertErrorsAsStrings(this._parseHelper.parse(input), 
        "Unknown cannot be resolved to a type.");
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @Test
  public void testTimeoutInCancelIndicator() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("import org.eclipse.emf.ecore.EPackage");
    _builder.newLine();
    _builder.newLine();
    _builder.append("metamodel \"foo\"");
    _builder.newLine();
    _builder.newLine();
    _builder.append("def op(EPackage c) : void {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("var i = 10;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("while (i >= 0) {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("Thread.sleep(1000);");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("i++");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("// this will never be executed");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("c.abstract = true");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("modifyEcore aTest epackage foo {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("op(it)");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    final String input = _builder.toString();
    final int lastOpenCurlyBracket = input.lastIndexOf("{");
    final int lastClosedCurlyBracket = input.lastIndexOf("}");
    this._validationTestHelper.assertWarning(this.parseWithTestEcore(input), 
      EdeltaPackage.eINSTANCE.getEdeltaModifyEcoreOperation(), 
      EdeltaValidator.INTERPRETER_TIMEOUT, lastOpenCurlyBracket, ((lastClosedCurlyBracket - lastOpenCurlyBracket) + 1), 
      "Timeout interpreting initialization block");
  }
  
  @Test
  public void testUnresolvedEcoreReference() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("metamodel \"foo\"");
    _builder.newLine();
    _builder.newLine();
    _builder.append("modifyEcore aTest epackage foo {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("ecoreref(NonExistant)");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("ecoreref(FooClass)");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    this.assertErrorsAsStrings(this.parseWithTestEcore(_builder), "NonExistant cannot be resolved.");
  }
  
  @Test
  public void testNoDanglingReferencesAfterInterpretation() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("metamodel \"foo\"");
    _builder.newLine();
    _builder.newLine();
    _builder.append("modifyEcore aTest epackage foo {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("ecoreref(foo.FooClass).EPackage.EClassifiers.remove(ecoreref(foo.FooClass))");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    this._validationTestHelper.assertNoErrors(this.parseWithTestEcore(_builder));
  }
  
  @Test
  public void testCallMethodOnRenanedEClassInModifyEcore() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("metamodel \"foo\"");
    _builder.newLine();
    _builder.newLine();
    _builder.append("modifyEcore aTest epackage foo {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("ecoreref(foo.FooClass).name = \"RenamedClass\"");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("ecoreref(RenamedClass).getEAllStructuralFeatures");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    final EdeltaProgram prog = this.parseWithTestEcore(_builder);
    this._validationTestHelper.assertNoErrors(prog);
  }
  
  @Test
  public void testCallMethodOnQualifiedRenanedEClassInModifyEcore() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("metamodel \"foo\"");
    _builder.newLine();
    _builder.newLine();
    _builder.append("modifyEcore aTest epackage foo {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("ecoreref(foo.FooClass).name = \"RenamedClass\"");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("ecoreref(foo.RenamedClass).getEAllStructuralFeatures");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    final EdeltaProgram prog = this.parseWithTestEcore(_builder);
    this._validationTestHelper.assertNoErrors(prog);
  }
  
  @Test
  public void testCallNonExistingMethodOnRenanedEClassInModifyEcore() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("metamodel \"foo\"");
    _builder.newLine();
    _builder.newLine();
    _builder.append("modifyEcore aTest epackage foo {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("ecoreref(foo.FooClass).name = \"RenamedClass\"");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("ecoreref(RenamedClass).nonExistant(\"an arg\")");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("ecoreref(RenamedClass).sugarSet = \"an arg\"");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("\"a string\".sugarSet = \"an arg\"");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    final EdeltaProgram prog = this.parseWithTestEcore(_builder);
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("The method nonExistant(String) is undefined for the type EClass");
    _builder_1.newLine();
    _builder_1.append("The method sugarSet(String) is undefined for the type EClass");
    _builder_1.newLine();
    _builder_1.append("The method sugarSet(String) is undefined for the type String");
    _builder_1.newLine();
    this.assertErrorsAsStrings(prog, _builder_1);
  }
  
  @Test
  public void testReferenceToAddedAttributeofRenamedClassInModifyEcore() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("metamodel \"foo\"");
    _builder.newLine();
    _builder.newLine();
    _builder.append("modifyEcore aTest epackage foo {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("ecoreref(foo.FooClass).name = \"RenamedClass\"");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("ecoreref(RenamedClass).EStructuralFeatures.add(");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("newEAttribute(\"addedAttribute\"))");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("ecoreref(RenamedClass.addedAttribute)");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    this._validationTestHelper.assertNoErrors(this.parseWithTestEcore(_builder));
  }
  
  @Test
  public void testReferenceToAddedAttributeofRenamedClassInModifyEcore2() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("import org.eclipse.emf.ecore.EClass");
    _builder.newLine();
    _builder.newLine();
    _builder.append("metamodel \"foo\"");
    _builder.newLine();
    _builder.newLine();
    _builder.append("modifyEcore aTest epackage foo {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("ecoreref(foo.FooClass).name = \"RenamedClass\"");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("ecoreref(RenamedClass).EStructuralFeatures += newEAttribute(\"added\")");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("ecoreref(RenamedClass.added)");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    this._validationTestHelper.assertNoErrors(this.parseWithTestEcore(_builder));
  }
  
  @Test
  public void testReferenceToRenamedClassInModifyEcore() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("import org.eclipse.emf.ecore.EClass");
    _builder.newLine();
    _builder.newLine();
    _builder.append("metamodel \"foo\"");
    _builder.newLine();
    _builder.newLine();
    _builder.append("modifyEcore aTest epackage foo {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("ecoreref(foo.FooClass).name = \"RenamedClass\"");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("ecoreref(foo.RenamedClass) => [abstract = true]");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("ecoreref(foo.RenamedClass).setAbstract(true)");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("ecoreref(foo.RenamedClass).abstract = true");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    this._validationTestHelper.assertNoErrors(this.parseWithTestEcore(_builder));
  }
  
  @Test
  public void testReferenceToUnknownEPackageInModifyEcore() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("import org.eclipse.emf.ecore.EClass");
    _builder.newLine();
    _builder.newLine();
    _builder.append("modifyEcore aTest epackage foo {");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    this._validationTestHelper.assertError(this.parseWithTestEcore(_builder), 
      EdeltaPackage.eINSTANCE.getEdeltaModifyEcoreOperation(), 
      Diagnostic.LINKING_DIAGNOSTIC, 
      "foo cannot be resolved.");
  }
  
  @Test
  public void testValidLibMethodsInModifyEcore() {
    this._validationTestHelper.assertNoErrors(this.parseWithTestEcore(this._inputs.modifyEcoreUsingLibMethods()));
  }
  
  @Test
  public void testDuplicateDeclarations() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("import java.util.List");
    _builder.newLine();
    _builder.append("import org.eclipse.emf.ecore.EPackage");
    _builder.newLine();
    _builder.newLine();
    _builder.append("metamodel \"foo\"");
    _builder.newLine();
    _builder.newLine();
    _builder.append("def myFun(List<Integer> l) {}");
    _builder.newLine();
    _builder.append("def myFun(List<String> l) {}");
    _builder.newLine();
    _builder.append("def anotherFun(List<String> l) {} // OK, different params");
    _builder.newLine();
    _builder.append("def anotherDuplicate(EPackage p) {} // conflicts with modifyEcore");
    _builder.newLine();
    _builder.newLine();
    _builder.append("modifyEcore aTest epackage foo {}");
    _builder.newLine();
    _builder.append("modifyEcore aTest epackage foo {}");
    _builder.newLine();
    _builder.append("modifyEcore anotherDuplicate epackage foo {} // implicit Java method param: EPackage");
    _builder.newLine();
    _builder.append("modifyEcore anotherFun epackage foo {} // OK, different params");
    _builder.newLine();
    final String input = _builder.toString();
    EdeltaProgram _parseWithTestEcore = this.parseWithTestEcore(input);
    final Procedure1<EdeltaProgram> _function = (EdeltaProgram it) -> {
      this._validationTestHelper.assertError(it, 
        EdeltaPackage.eINSTANCE.getEdeltaOperation(), 
        EdeltaValidator.DUPLICATE_DECLARATION, 
        input.indexOf("anotherDuplicate"), "anotherDuplicate".length(), 
        "Duplicate definition \'anotherDuplicate\'");
      this._validationTestHelper.assertError(it, 
        EdeltaPackage.eINSTANCE.getEdeltaModifyEcoreOperation(), 
        EdeltaValidator.DUPLICATE_DECLARATION, 
        input.lastIndexOf("anotherDuplicate"), "anotherDuplicate".length(), 
        "Duplicate definition \'anotherDuplicate\'");
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("Duplicate definition \'aTest\'");
      _builder_1.newLine();
      _builder_1.append("Duplicate definition \'aTest\'");
      _builder_1.newLine();
      _builder_1.append("Duplicate definition \'anotherDuplicate\'");
      _builder_1.newLine();
      _builder_1.append("Duplicate definition \'anotherDuplicate\'");
      _builder_1.newLine();
      _builder_1.append("Duplicate definition \'myFun\'");
      _builder_1.newLine();
      _builder_1.append("Duplicate definition \'myFun\'");
      _builder_1.newLine();
      this.assertErrorsAsStrings(it, _builder_1);
    };
    ObjectExtensions.<EdeltaProgram>operator_doubleArrow(_parseWithTestEcore, _function);
  }
  
  @Test
  public void testReferenceToCreatedEClassRenamed() {
    this._validationTestHelper.assertNoErrors(this.parseWithTestEcore(this._inputs.referenceToCreatedEClassRenamed()));
  }
}
