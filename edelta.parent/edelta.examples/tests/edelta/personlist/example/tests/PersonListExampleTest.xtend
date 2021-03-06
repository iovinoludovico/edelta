package edelta.personlist.example.tests

import edelta.personlist.example.PersonListExample
import edelta.testutils.EdeltaTestUtils
import org.junit.Test

class PersonListExampleTest {

	@Test
	def void testPersonList() {
		// Create an instance of the generated Java class
		val edelta = new PersonListExample();
		// Make sure you load all the used Ecores
		edelta.loadEcoreFile("model/PersonList.ecore");
		// Execute the actual transformations defined in the DSL
		edelta.execute();
		// Save the modified Ecore model into a new path
		edelta.saveModifiedEcores("modified");

		EdeltaTestUtils.compareSingleFileContents(
			"modified/PersonList.ecore",
			'''
			<?xml version="1.0" encoding="UTF-8"?>
			<ecore:EPackage xmi:version="2.0" xmlns:xmi="http://www.omg.org/XMI" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
			    xmlns:ecore="http://www.eclipse.org/emf/2002/Ecore" name="PersonList" nsURI="http://cs.gssi.it/PersonMM" nsPrefix="person">
			  <eClassifiers xsi:type="ecore:EClass" name="List">
			    <eStructuralFeatures xsi:type="ecore:EReference" name="members" upperBound="-1"
			        eType="#//Person" containment="true" eOpposite="#//Person/list"/>
			    <eStructuralFeatures xsi:type="ecore:EReference" name="places" eType="#//Place"/>
			  </eClassifiers>
			  <eClassifiers xsi:type="ecore:EClass" name="Person" abstract="true">
			    <eStructuralFeatures xsi:type="ecore:EReference" name="list" lowerBound="1" eType="#//List"
			        eOpposite="#//List/members"/>
			    <eStructuralFeatures xsi:type="ecore:EReference" name="works" lowerBound="1" eType="#//WorkingPosition"
			        containment="true" eOpposite="#//WorkingPosition/persons"/>
			    <eStructuralFeatures xsi:type="ecore:EReference" name="home" lowerBound="1" eType="#//LivingPlace"
			        eOpposite="#//LivingPlace/persons"/>
			    <eStructuralFeatures xsi:type="ecore:EAttribute" name="name" eType="ecore:EDataType http://www.eclipse.org/emf/2002/Ecore#//EString"/>
			  </eClassifiers>
			  <eClassifiers xsi:type="ecore:EClass" name="WorkPlace" eSuperTypes="#//Place">
			    <eStructuralFeatures xsi:type="ecore:EReference" name="position" eType="#//WorkingPosition"
			        eOpposite="#//WorkingPosition/works"/>
			  </eClassifiers>
			  <eClassifiers xsi:type="ecore:EClass" name="LivingPlace" eSuperTypes="#//Place">
			    <eStructuralFeatures xsi:type="ecore:EReference" name="persons" upperBound="-1"
			        eType="#//Person" eOpposite="#//Person/home"/>
			  </eClassifiers>
			  <eClassifiers xsi:type="ecore:EEnum" name="Gender">
			    <eLiterals name="Male"/>
			    <eLiterals name="Female" value="1"/>
			  </eClassifiers>
			  <eClassifiers xsi:type="ecore:EClass" name="Male" eSuperTypes="#//Person"/>
			  <eClassifiers xsi:type="ecore:EClass" name="Female" eSuperTypes="#//Person"/>
			  <eClassifiers xsi:type="ecore:EClass" name="Place" abstract="true">
			    <eStructuralFeatures xsi:type="ecore:EAttribute" name="address" eType="ecore:EDataType http://www.eclipse.org/emf/2002/Ecore#//EString"/>
			  </eClassifiers>
			  <eClassifiers xsi:type="ecore:EClass" name="WorkingPosition">
			    <eStructuralFeatures xsi:type="ecore:EAttribute" name="description" eType="ecore:EDataType http://www.eclipse.org/emf/2002/Ecore#//EString"/>
			    <eStructuralFeatures xsi:type="ecore:EReference" name="works" lowerBound="1" eType="#//WorkPlace"
			        eOpposite="#//WorkPlace/position"/>
			    <eStructuralFeatures xsi:type="ecore:EReference" name="persons" lowerBound="1"
			        eType="#//Person" eOpposite="#//Person/works"/>
			  </eClassifiers>
			</ecore:EPackage>
			'''
		)
	}
}
