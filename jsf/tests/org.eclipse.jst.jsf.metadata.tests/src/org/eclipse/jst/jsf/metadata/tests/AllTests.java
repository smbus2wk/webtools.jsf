/*******************************************************************************
 * Copyright (c) 2006 Oracle Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Gerry Kessler/Oracle - initial API and implementation
 *    
 ********************************************************************************/
package org.eclipse.jst.jsf.metadata.tests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.eclipse.jst.jsf.common.metadata.tests.AllDeprecatedMetadataTests;
import org.eclipse.jst.jsf.common.metadata.tests.updated.AllUpdatedMetadataTests;
import org.eclipse.jst.jsf.core.JSFVersion;
import org.eclipse.jst.jsf.core.tests.util.JSFCoreUtilHelper;
import org.eclipse.jst.jsf.metadata.tests.metadataprocessing.AttributeValueRuntimeTypeExtensionsTests;
import org.eclipse.jst.jsf.metadata.tests.metadataprocessing.AttributeValueRuntimeTypeFactoryTests;
import org.eclipse.jst.jsf.metadata.tests.metadataprocessing.AttributeValueRuntimeTypesRegistryTests;
import org.eclipse.jst.jsf.metadata.tests.metadataprocessing.MetaDataProcessorsFactoryTests;
import org.eclipse.jst.jsf.metadata.tests.pagedesigner.PaletteTests;
import org.eclipse.jst.jsf.metadata.tests.pagedesigner.PropertyDescriptorTests;
import org.eclipse.jst.jsf.metadata.tests.pagedesigner.QuickEditSectionsTests;
import org.eclipse.jst.jsf.metadata.tests.taglibprocessing.ActionTypeTests;
import org.eclipse.jst.jsf.metadata.tests.taglibprocessing.BooleanTypeTest;
import org.eclipse.jst.jsf.metadata.tests.taglibprocessing.CSSClassTypeTests;
import org.eclipse.jst.jsf.metadata.tests.taglibprocessing.CSSStyleTypeTests;
import org.eclipse.jst.jsf.metadata.tests.taglibprocessing.CharacterTypeTest;
import org.eclipse.jst.jsf.metadata.tests.taglibprocessing.ColorTypeTest;
import org.eclipse.jst.jsf.metadata.tests.taglibprocessing.ComponentBindingTypeTests;
import org.eclipse.jst.jsf.metadata.tests.taglibprocessing.ComponentIDTypeTests;
import org.eclipse.jst.jsf.metadata.tests.taglibprocessing.ContentTypeTest;
import org.eclipse.jst.jsf.metadata.tests.taglibprocessing.CurrencyCodeTypeTest;
import org.eclipse.jst.jsf.metadata.tests.taglibprocessing.DoubleTypeTest;
import org.eclipse.jst.jsf.metadata.tests.taglibprocessing.EnumeratedDoubleTypeTest;
import org.eclipse.jst.jsf.metadata.tests.taglibprocessing.EnumeratedIntegerTypeTest;
import org.eclipse.jst.jsf.metadata.tests.taglibprocessing.EnumeratedLongTypeTest;
import org.eclipse.jst.jsf.metadata.tests.taglibprocessing.FacesConfigConverterIDTypeTests;
import org.eclipse.jst.jsf.metadata.tests.taglibprocessing.FacesConfigValidatorIDTypeTests;
import org.eclipse.jst.jsf.metadata.tests.taglibprocessing.IntegerTypeTest;
import org.eclipse.jst.jsf.metadata.tests.taglibprocessing.JSFHTMLTestCase;
import org.eclipse.jst.jsf.metadata.tests.taglibprocessing.JavaClassTypeTest;
import org.eclipse.jst.jsf.metadata.tests.taglibprocessing.LanguageCodeTypeTest;
import org.eclipse.jst.jsf.metadata.tests.taglibprocessing.LengthTypeTest;
import org.eclipse.jst.jsf.metadata.tests.taglibprocessing.LinkTypeTest;
import org.eclipse.jst.jsf.metadata.tests.taglibprocessing.LocaleTypeTest;
import org.eclipse.jst.jsf.metadata.tests.taglibprocessing.LongTypeTest;
import org.eclipse.jst.jsf.metadata.tests.taglibprocessing.MethodBindingTypeTests;
import org.eclipse.jst.jsf.metadata.tests.taglibprocessing.NumberPatternTypeTest;
import org.eclipse.jst.jsf.metadata.tests.taglibprocessing.StringTypeTest;
import org.eclipse.jst.jsf.metadata.tests.taglibprocessing.TimeZoneTypeTest;
import org.eclipse.jst.jsf.metadata.tests.taglibprocessing.ValueBindingTypeTests;
import org.eclipse.jst.jsf.metadata.tests.taglibprocessing.ValueTypeTests;
import org.eclipse.jst.jsf.metadata.tests.util.IJSFRuntimeRequiredV11;
import org.eclipse.jst.jsf.metadata.tests.util.IJSFRuntimeRequiredV12;

public class AllTests {

	private static boolean _inited;
	private static boolean _jsfRuntimePresentV11;
	private static boolean _jsfRuntimePresentV12;

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"Test Suite for org.eclipse.jst.jsf.metadata.tests");
		//$JUnit-BEGIN$
			
//		//model 		
//		addTestSuite(suite, TraitImplTests.class);
//		addTestSuite(suite, IncludeEntityGroupImplTests.class);
//		addTestSuite(suite, EntityImplTests.class);
//		addTestSuite(suite, ModelImplTests.class);
//		addTestSuite(suite, MergeTests.class);
//		addTestSuite(suite, TinyTestTests.class);
//		
//		addTestSuite(suite, MissingMDExtensionModelTests.class);
//
////		//query + main API
//		addTestSuite(suite, EmptyResultSetTest.class);
//		addTestSuite(suite, MetaDataExceptionTest.class);
//		addTestSuite(suite, AbstractMetaDataVisitorTest.class);
//		addTestSuite(suite, AbstractEntityQueryVisitorTest.class);
//		addTestSuite(suite, AbstractTraitQueryVisitorTest.class);
//		addTestSuite(suite, MetaDataQueryHelperTests.class);
//		addTestSuite(suite, TraitValueHelperTests.class);
//		addTestSuite(suite, ModelProviderAdapterTests.class);		
		
		suite.addTest(AllDeprecatedMetadataTests.suite());
		suite.addTest(AllUpdatedMetadataTests.suite());
		
//		//metadataprocessing
		addTestSuite(suite, MetaDataProcessorsFactoryTests.class);
		addTestSuite(suite, AttributeValueRuntimeTypeExtensionsTests.class);
		addTestSuite(suite, AttributeValueRuntimeTypesRegistryTests.class);
		addTestSuite(suite, AttributeValueRuntimeTypeFactoryTests.class);
		
		//PageDesigner Metadata tests
		addTestSuite(suite, PropertyDescriptorTests.class);
		addTestSuite(suite, QuickEditSectionsTests.class);
		
//		//taglib processing
		//Tests NOT requiring JSF Implementation
		addTestSuite(suite, StringTypeTest.class);
		addTestSuite(suite, BooleanTypeTest.class);
		addTestSuite(suite, IntegerTypeTest.class);
		addTestSuite(suite, EnumeratedIntegerTypeTest.class);
		addTestSuite(suite, LongTypeTest.class);
		addTestSuite(suite, EnumeratedLongTypeTest.class);
		addTestSuite(suite, DoubleTypeTest.class);
		addTestSuite(suite, EnumeratedDoubleTypeTest.class);
		
		addTestSuite(suite, ColorTypeTest.class);
		addTestSuite(suite, LanguageCodeTypeTest.class);
		addTestSuite(suite, LocaleTypeTest.class);
		addTestSuite(suite, CurrencyCodeTypeTest.class);
		addTestSuite(suite, TimeZoneTypeTest.class);
		addTestSuite(suite, LengthTypeTest.class);
		addTestSuite(suite, NumberPatternTypeTest.class);
//		addTestSuite(suite, RelativePathTypeTest.class);
//		addTestSuite(suite, WebPathTypeTest.class);
		addTestSuite(suite, CharacterTypeTest.class);
//		addTestSuite(suite, ScriptTypeTest.class);
		addTestSuite(suite, LinkTypeTest.class);
		addTestSuite(suite, ContentTypeTest.class);
//		addTestSuite(suite, URITypeTest.class);
		
		//Tests requiring JSF Implementation
		addTestSuite(suite, JavaClassTypeTest.class);
		addTestSuite(suite, FacesConfigConverterIDTypeTests.class);
		addTestSuite(suite, FacesConfigValidatorIDTypeTests.class);
		addTestSuite(suite, MethodBindingTypeTests.class);
		addTestSuite(suite, ComponentIDTypeTests.class);
		addTestSuite(suite, ComponentBindingTypeTests.class);
		addTestSuite(suite, ValueBindingTypeTests.class);
		addTestSuite(suite, ValueTypeTests.class);
		addTestSuite(suite, ActionTypeTests.class);
		
		addTestSuite(suite, CSSClassTypeTests.class);
		addTestSuite(suite, CSSStyleTypeTests.class);			
		
		addTestSuite(suite, JSFHTMLTestCase.class);
//		addTestSuite(suite, JSFCoreTestCase.class);
		addTestSuite(suite, PaletteTests.class);
		
		//$JUnit-END$
		return suite;
	}

	private static void addTestSuite(TestSuite suite, Class<? extends TestCase> klass) {
		if (!_inited) {
			_jsfRuntimePresentV11 = JSFCoreUtilHelper.isJSFRuntimeJarsDirectoryPropertySet(JSFVersion.V1_1);
			_jsfRuntimePresentV12 = JSFCoreUtilHelper.isJSFRuntimeJarsDirectoryPropertySet(JSFVersion.V1_2);
			_inited = true;
		}
		if (IJSFRuntimeRequiredV11.class.isAssignableFrom(klass)){
			if (_jsfRuntimePresentV11)
				suite.addTestSuite(klass);
			else 
				System.err.println("Warning: JSF Runtime v1.1 is required and not present - "+klass.getName()+" test was skipped.");
		} else if (IJSFRuntimeRequiredV12.class.isAssignableFrom(klass)){
			if (_jsfRuntimePresentV12)
				suite.addTestSuite(klass);
			else 
				System.err.println("Warning: JSF Runtime v1.2 is required and not present - "+klass.getName()+" test was skipped.");
		} else {
			suite.addTestSuite(klass);
		}
	}

	/**
	 * Old tests that no longer need to run since moving to new metadata system
	 */
	public void Oldsuite() {
//		TestSuite suite = new TestSuite(
//				"Test for org.eclipse.jst.jsf.metadata.tests");
//		//$JUnit-BEGIN$
//		
//		//annotations 
//		addTestSuite(suite, AnnotationMapTestCases.class);
//		addTestSuite(suite, AnnotationHelperTestCases.class);
//		addTestSuite(suite, DuplicateAnnotationsTestCases.class);
//		addTestSuite(suite, CaseInsensitiveAnnotationsTestCases.class);
//		addTestSuite(suite, NegativeAnnotationFileTestCases.class);
//
//		//metadataprocessing
//		addTestSuite(suite, MetaDataProcessorsFactoryTests.class);
//		addTestSuite(suite, AttributeValueRuntimeTypeExtensionsTests.class);
//		addTestSuite(suite, AttributeValueRuntimeTypesRegistryTests.class);
//		addTestSuite(suite, AttributeValueRuntimeTypeFactoryTests.class);
//		
//		//taglib processing
//		addTestSuite(suite, StringTypeTest.class);
//		addTestSuite(suite, BooleanTypeTest.class);
//		addTestSuite(suite, IntegerTypeTest.class);
//		addTestSuite(suite, LongTypeTest.class);
//		addTestSuite(suite, DoubleTypeTest.class);
//		addTestSuite(suite, JavaClassTypeTest.class);

		//$JUnit-END$
//		return suite;
	}

}
