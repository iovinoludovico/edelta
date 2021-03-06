package edelta.tests;

import com.google.inject.Inject;
import edelta.edelta.EdeltaProgram;
import edelta.tests.EdeltaAbstractTest;
import edelta.tests.EdeltaInjectorProviderCustom;
import edelta.util.EdeltaEcoreHelper;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ObjectExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(XtextRunner.class)
@InjectWith(EdeltaInjectorProviderCustom.class)
@SuppressWarnings("all")
public class EdeltaEcoreHelperTest extends EdeltaAbstractTest {
  @Inject
  @Extension
  private EdeltaEcoreHelper _edeltaEcoreHelper;
  
  @Test
  public void testProgramENamedElements() {
    Iterable<? extends ENamedElement> _programENamedElements = this._edeltaEcoreHelper.getProgramENamedElements(this.parseWithTestEcores(this._inputs.referencesToMetamodels()));
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("FooClass");
    _builder.newLine();
    _builder.append("FooDataType");
    _builder.newLine();
    _builder.append("FooEnum");
    _builder.newLine();
    _builder.append("myAttribute");
    _builder.newLine();
    _builder.append("myReference");
    _builder.newLine();
    _builder.append("FooEnumLiteral");
    _builder.newLine();
    _builder.append("BarClass");
    _builder.newLine();
    _builder.append("BarDataType");
    _builder.newLine();
    _builder.append("myAttribute");
    _builder.newLine();
    _builder.append("myReference");
    _builder.newLine();
    _builder.append("foo");
    _builder.newLine();
    _builder.append("bar");
    _builder.newLine();
    this.assertNamedElements(_programENamedElements, _builder);
  }
  
  @Test
  public void testProgramWithCreatedEClassENamedElements() {
    EdeltaProgram _parseWithTestEcore = this.parseWithTestEcore(this._inputs.referenceToCreatedEClass());
    final Procedure1<EdeltaProgram> _function = (EdeltaProgram it) -> {
      Iterable<? extends ENamedElement> _programENamedElements = this._edeltaEcoreHelper.getProgramENamedElements(it);
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("FooClass");
      _builder.newLine();
      _builder.append("FooDataType");
      _builder.newLine();
      _builder.append("FooEnum");
      _builder.newLine();
      _builder.append("NewClass");
      _builder.newLine();
      _builder.append("myAttribute");
      _builder.newLine();
      _builder.append("myReference");
      _builder.newLine();
      _builder.append("FooEnumLiteral");
      _builder.newLine();
      _builder.append("FooClass");
      _builder.newLine();
      _builder.append("FooDataType");
      _builder.newLine();
      _builder.append("FooEnum");
      _builder.newLine();
      _builder.append("myAttribute");
      _builder.newLine();
      _builder.append("myReference");
      _builder.newLine();
      _builder.append("FooEnumLiteral");
      _builder.newLine();
      _builder.append("foo");
      _builder.newLine();
      this.assertNamedElements(_programENamedElements, _builder);
    };
    ObjectExtensions.<EdeltaProgram>operator_doubleArrow(_parseWithTestEcore, _function);
  }
  
  @Test
  public void testEPackageENamedElements() {
    EdeltaProgram _parseWithTestEcore = this.parseWithTestEcore(this._inputs.referenceToMetamodel());
    final Procedure1<EdeltaProgram> _function = (EdeltaProgram it) -> {
      Iterable<? extends ENamedElement> _eNamedElements = this._edeltaEcoreHelper.getENamedElements(this.getEPackageByName(it, "foo"), it);
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("FooClass");
      _builder.newLine();
      _builder.append("FooDataType");
      _builder.newLine();
      _builder.append("FooEnum");
      _builder.newLine();
      this.assertNamedElements(_eNamedElements, _builder);
    };
    ObjectExtensions.<EdeltaProgram>operator_doubleArrow(_parseWithTestEcore, _function);
  }
  
  @Test
  public void testEPackageENamedElementsWithCreatedEClass() {
    EdeltaProgram _parseWithTestEcore = this.parseWithTestEcore(this._inputs.referenceToCreatedEClass());
    final Procedure1<EdeltaProgram> _function = (EdeltaProgram it) -> {
      Iterable<? extends ENamedElement> _eNamedElements = this._edeltaEcoreHelper.getENamedElements(this.getEPackageByName(it, "foo"), it);
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("FooClass");
      _builder.newLine();
      _builder.append("FooDataType");
      _builder.newLine();
      _builder.append("FooEnum");
      _builder.newLine();
      _builder.append("NewClass");
      _builder.newLine();
      _builder.append("FooClass");
      _builder.newLine();
      _builder.append("FooDataType");
      _builder.newLine();
      _builder.append("FooEnum");
      _builder.newLine();
      this.assertNamedElements(_eNamedElements, _builder);
    };
    ObjectExtensions.<EdeltaProgram>operator_doubleArrow(_parseWithTestEcore, _function);
  }
  
  @Test
  public void testEPackageENamedElementsWithCreatedEClassWithoutCopiedEPackages() {
    EdeltaProgram _parseWithTestEcore = this.parseWithTestEcore(this._inputs.referenceToCreatedEClass());
    final Procedure1<EdeltaProgram> _function = (EdeltaProgram it) -> {
      Iterable<? extends ENamedElement> _eNamedElementsWithoutCopiedEPackages = this._edeltaEcoreHelper.getENamedElementsWithoutCopiedEPackages(this.getEPackageByName(it, "foo"), it);
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("FooClass");
      _builder.newLine();
      _builder.append("FooDataType");
      _builder.newLine();
      _builder.append("FooEnum");
      _builder.newLine();
      this.assertNamedElements(_eNamedElementsWithoutCopiedEPackages, _builder);
    };
    ObjectExtensions.<EdeltaProgram>operator_doubleArrow(_parseWithTestEcore, _function);
  }
  
  @Test
  public void testEDataTypeENamedElements() {
    EdeltaProgram _parseWithTestEcore = this.parseWithTestEcore(this._inputs.referenceToMetamodel());
    final Procedure1<EdeltaProgram> _function = (EdeltaProgram it) -> {
      Iterable<? extends ENamedElement> _eNamedElements = this._edeltaEcoreHelper.getENamedElements(this.getEClassifierByName(it, "foo", "FooDataType"), it);
      StringConcatenation _builder = new StringConcatenation();
      _builder.newLine();
      this.assertNamedElements(_eNamedElements, _builder);
    };
    ObjectExtensions.<EdeltaProgram>operator_doubleArrow(_parseWithTestEcore, _function);
  }
  
  @Test
  public void testENumENamedElements() {
    EdeltaProgram _parseWithTestEcore = this.parseWithTestEcore(this._inputs.referenceToMetamodel());
    final Procedure1<EdeltaProgram> _function = (EdeltaProgram it) -> {
      Iterable<? extends ENamedElement> _eNamedElements = this._edeltaEcoreHelper.getENamedElements(this.getEClassifierByName(it, "foo", "FooEnum"), it);
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("FooEnumLiteral");
      _builder.newLine();
      this.assertNamedElements(_eNamedElements, _builder);
    };
    ObjectExtensions.<EdeltaProgram>operator_doubleArrow(_parseWithTestEcore, _function);
  }
  
  @Test
  public void testENumENamedElementsWithCreatedEClass() {
    EdeltaProgram _parseWithTestEcore = this.parseWithTestEcore(this._inputs.referenceToCreatedEClass());
    final Procedure1<EdeltaProgram> _function = (EdeltaProgram it) -> {
      Iterable<? extends ENamedElement> _eNamedElements = this._edeltaEcoreHelper.getENamedElements(this.getEClassifierByName(it, "foo", "FooEnum"), it);
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("FooEnumLiteral");
      _builder.newLine();
      _builder.append("FooEnumLiteral");
      _builder.newLine();
      this.assertNamedElements(_eNamedElements, _builder);
    };
    ObjectExtensions.<EdeltaProgram>operator_doubleArrow(_parseWithTestEcore, _function);
  }
  
  @Test
  public void testENumENamedElementsWithCreatedEClassWithoutCopiedEPackages() {
    EdeltaProgram _parseWithTestEcore = this.parseWithTestEcore(this._inputs.referenceToCreatedEClass());
    final Procedure1<EdeltaProgram> _function = (EdeltaProgram it) -> {
      Iterable<? extends ENamedElement> _eNamedElementsWithoutCopiedEPackages = this._edeltaEcoreHelper.getENamedElementsWithoutCopiedEPackages(this.getEClassifierByName(it, "foo", "FooEnum"), it);
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("FooEnumLiteral");
      _builder.newLine();
      this.assertNamedElements(_eNamedElementsWithoutCopiedEPackages, _builder);
    };
    ObjectExtensions.<EdeltaProgram>operator_doubleArrow(_parseWithTestEcore, _function);
  }
  
  @Test
  public void testNullENamedElements() {
    EdeltaProgram _parseWithTestEcore = this.parseWithTestEcore(this._inputs.referenceToMetamodel());
    final Procedure1<EdeltaProgram> _function = (EdeltaProgram it) -> {
      Assert.assertTrue(IterableExtensions.isEmpty(this._edeltaEcoreHelper.getENamedElements(null, it)));
    };
    ObjectExtensions.<EdeltaProgram>operator_doubleArrow(_parseWithTestEcore, _function);
  }
  
  @Test
  public void testEClassENamedElements() {
    EdeltaProgram _parseWithTestEcore = this.parseWithTestEcore(this._inputs.referenceToMetamodel());
    final Procedure1<EdeltaProgram> _function = (EdeltaProgram it) -> {
      Iterable<? extends ENamedElement> _eNamedElements = this._edeltaEcoreHelper.getENamedElements(this.getEClassifierByName(it, "foo", "FooClass"), it);
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("myAttribute");
      _builder.newLine();
      _builder.append("myReference");
      _builder.newLine();
      this.assertNamedElements(_eNamedElements, _builder);
    };
    ObjectExtensions.<EdeltaProgram>operator_doubleArrow(_parseWithTestEcore, _function);
  }
  
  @Test
  public void testEClassENamedElementsWithCreatedEClass() {
    EdeltaProgram _parseWithTestEcore = this.parseWithTestEcore(this._inputs.referenceToCreatedEClass());
    final Procedure1<EdeltaProgram> _function = (EdeltaProgram it) -> {
      Iterable<? extends ENamedElement> _eNamedElements = this._edeltaEcoreHelper.getENamedElements(this.getEClassifierByName(it, "foo", "FooClass"), it);
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("myAttribute");
      _builder.newLine();
      _builder.append("myReference");
      _builder.newLine();
      _builder.append("myAttribute");
      _builder.newLine();
      _builder.append("myReference");
      _builder.newLine();
      this.assertNamedElements(_eNamedElements, _builder);
    };
    ObjectExtensions.<EdeltaProgram>operator_doubleArrow(_parseWithTestEcore, _function);
  }
  
  @Test
  public void testEClassENamedElementsWithCreatedEClassWithoutCopiedEPackages() {
    EdeltaProgram _parseWithTestEcore = this.parseWithTestEcore(this._inputs.referenceToCreatedEClass());
    final Procedure1<EdeltaProgram> _function = (EdeltaProgram it) -> {
      Iterable<? extends ENamedElement> _eNamedElementsWithoutCopiedEPackages = this._edeltaEcoreHelper.getENamedElementsWithoutCopiedEPackages(this.getEClassifierByName(it, "foo", "FooClass"), it);
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("myAttribute");
      _builder.newLine();
      _builder.append("myReference");
      _builder.newLine();
      this.assertNamedElements(_eNamedElementsWithoutCopiedEPackages, _builder);
    };
    ObjectExtensions.<EdeltaProgram>operator_doubleArrow(_parseWithTestEcore, _function);
  }
  
  @Test
  public void testGetAllEClasses() {
    EdeltaProgram _parseWithTestEcore = this.parseWithTestEcore(this._inputs.referenceToMetamodel());
    final Procedure1<EdeltaProgram> _function = (EdeltaProgram it) -> {
      Iterable<EClass> _allEClasses = this._edeltaEcoreHelper.getAllEClasses(this.getEPackageByName(it, "foo"));
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("FooClass");
      _builder.newLine();
      this.assertNamedElements(_allEClasses, _builder);
    };
    ObjectExtensions.<EdeltaProgram>operator_doubleArrow(_parseWithTestEcore, _function);
  }
}
