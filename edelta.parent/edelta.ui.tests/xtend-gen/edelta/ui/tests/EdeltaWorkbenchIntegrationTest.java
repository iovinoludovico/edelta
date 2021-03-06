package edelta.ui.tests;

import com.google.inject.Inject;
import edelta.ui.tests.EdeltaUiInjectorProvider;
import edelta.ui.tests.utils.EdeltaPluginProjectHelper;
import edelta.ui.tests.utils.PluginProjectHelper;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.ui.testing.AbstractWorkbenchTest;
import org.eclipse.xtext.ui.testing.util.IResourcesSetupUtil;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(XtextRunner.class)
@InjectWith(EdeltaUiInjectorProvider.class)
@SuppressWarnings("all")
public class EdeltaWorkbenchIntegrationTest extends AbstractWorkbenchTest {
  private IProject project;
  
  @Inject
  private PluginProjectHelper projectHelper;
  
  @Inject
  private EdeltaPluginProjectHelper edeltaProjectHelper;
  
  private final String TEST_PROJECT = "mytestproject";
  
  @Before
  @Override
  public void setUp() {
    try {
      super.setUp();
      this.project = this.edeltaProjectHelper.createEdeltaPluginProject(this.TEST_PROJECT).getProject();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @Test
  public void testValidProject() {
    try {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("package foo");
      _builder.newLine();
      _builder.newLine();
      _builder.append("metamodel \"mypackage\"");
      _builder.newLine();
      _builder.newLine();
      _builder.append("modifyEcore aTest epackage mypackage {");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("ecoreref(MyClass)");
      _builder.newLine();
      _builder.append("}");
      _builder.newLine();
      IResourcesSetupUtil.createFile(
        (this.TEST_PROJECT + "/src/Test.edelta"), _builder.toString());
      IResourcesSetupUtil.waitForBuild();
      this.projectHelper.assertNoErrors();
      IResourcesSetupUtil.waitForBuild();
      this.projectHelper.assertNoErrors();
      this.assertSrcGenFolderFile("foo", "Test.java");
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @Test
  public void testInvalidProject() {
    try {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("package foo");
      _builder.newLine();
      _builder.newLine();
      _builder.append("metamodel \"mypackage\"");
      _builder.newLine();
      _builder.newLine();
      _builder.append("modifyEcore aTest epackage mypackage {");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("ecoreref(Foo)");
      _builder.newLine();
      _builder.append("}");
      _builder.newLine();
      IResourcesSetupUtil.createFile(
        (this.TEST_PROJECT + "/src/Test.edelta"), _builder.toString());
      IResourcesSetupUtil.waitForBuild();
      IResourcesSetupUtil.waitForBuild();
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("Foo cannot be resolved.");
      this.projectHelper.assertErrors(_builder_1);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @Test
  public void testDerivedStateEPackagesDontInterfereWithOtherEdeltaFiles() {
    try {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("package foo");
      _builder.newLine();
      _builder.newLine();
      _builder.append("metamodel \"mypackage\"");
      _builder.newLine();
      _builder.newLine();
      _builder.append("modifyEcore aTest epackage mypackage {");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("addNewEClass(\"NewClass\")");
      _builder.newLine();
      _builder.append("}");
      _builder.newLine();
      IResourcesSetupUtil.createFile(
        (this.TEST_PROJECT + "/src/Test.edelta"), _builder.toString());
      IResourcesSetupUtil.waitForBuild();
      this.projectHelper.assertNoErrors();
      IResourcesSetupUtil.waitForBuild();
      this.projectHelper.assertNoErrors();
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("package foo");
      _builder_1.newLine();
      _builder_1.newLine();
      _builder_1.append("metamodel \"mypackage\"");
      _builder_1.newLine();
      _builder_1.newLine();
      _builder_1.append("modifyEcore aTest epackage mypackage {");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("addNewEClass(\"NewClass\")");
      _builder_1.newLine();
      _builder_1.append("}");
      _builder_1.newLine();
      IResourcesSetupUtil.createFile(
        (this.TEST_PROJECT + "/src/Test2.edelta"), _builder_1.toString());
      IResourcesSetupUtil.waitForBuild();
      this.projectHelper.assertNoErrors();
      IResourcesSetupUtil.waitForBuild();
      this.projectHelper.assertNoErrors();
      this.assertSrcGenFolderFile("foo", "Test.java");
      this.assertSrcGenFolderFile("foo", "Test2.java");
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  private void assertSrcGenFolderFile(final String expectedSubDir, final String expectedFile) {
    final String expectedSrcGenFolderSubDir = ("edelta-gen/" + expectedSubDir);
    final IFolder srcGenFolder = this.project.getFolder(expectedSrcGenFolderSubDir);
    Assert.assertTrue((expectedSrcGenFolderSubDir + " does not exist"), srcGenFolder.exists());
    final IFile genfile = srcGenFolder.getFile(expectedFile);
    Assert.assertTrue((expectedFile + " does not exist"), genfile.exists());
  }
}
