package org.eclipse.jst.pagedesigner.tests.tagcreator;

import org.eclipse.jst.jsf.core.internal.tld.IJSFConstants;
import org.eclipse.jst.jsf.core.tests.util.JSFCoreUtilHelper;
import org.eclipse.jst.pagedesigner.itemcreation.customizer.CustomizationDataImpl;
import org.eclipse.jst.pagedesigner.itemcreation.customizer.ICustomizationData;
import org.eclipse.jst.pagedesigner.itemcreation.customizer.IWritableCustomizationData;
import org.eclipse.jst.pagedesigner.tests.tagcreatorPlugin.UserCustomizedElementEditFactory;

/**
 * Test class for parent tag customization
 * 
 * @author Debajit Adhikary
 *
 */
public class TestUserCustomizedTagCreatorForJSFHTML_ParentCustomization extends BaseUserCustomizedTagCreatorTestCase 
{
	/**
	 * Path to directory where the test-data files are located. The XML generated from this
	 * test case is compared against this test data.
	 *  
	 */
	private static final String TESTDATA_FILES_PATH = "/testdata/UserCustomizedTagCreator/ParentCustomization";


	public void testParentTagInsertion() throws Exception
    {
        doCreateTest(IJSFConstants.TAG_IDENTIFIER_COMMANDBUTTON, "jsp", "jsp", 358, false, getCustomizationData());
        doCreateTest(IJSFConstants.TAG_IDENTIFIER_COMMANDBUTTON, "jspx", "jspx", 495, false, getCustomizationData());
        
        JSFCoreUtilHelper.injectTestTagRegistryFactoryProvider(JSFCoreUtilHelper.createSimpleRegistryFactory());
        doCreateTest(IJSFConstants.TAG_IDENTIFIER_COMMANDBUTTON, "xhtml", "xhtml", 350, false, getCustomizationData());
        JSFCoreUtilHelper.injectTestTagRegistryFactoryProvider(null);
    }

    protected ICustomizationData getCustomizationData()
    {
    	// Setup tag
    	IWritableCustomizationData data = new CustomizationDataImpl(getTagIdentifier());
    	data.addAttribute("attr1", "value1");

    	//--------------------------------------------
    	// Add parent tags ((outermost parent first)
    	//
    	// We will create the tag hierarchy:
    	// f:view > TAG1 > TAG2 > TAG3 > TagDropped
    	//--------------------------------------------
    	
		// Setup <f:view>. (This will not use the UserCustomizedTagCreator but
		// will use its own tag creator)
    	IWritableCustomizationData view = new CustomizationDataImpl(IJSFConstants.TAG_IDENTIFIER_VIEW);
    	data.addParentData(view);
    	
    	// Setup greatgrandparent 
    	IWritableCustomizationData grandParent = new CustomizationDataImpl(UserCustomizedElementEditFactory.TAG1);
    	grandParent.addAttribute("grandParentAttr1", "grandParentValue1");
    	data.addParentData(grandParent);

    	// Setup grandparent
    	IWritableCustomizationData parent = new CustomizationDataImpl(UserCustomizedElementEditFactory.TAG2);
    	parent.addAttribute("parentAttr1", "parentValue1");
    	data.addParentData(parent);
    	
    	// Setup parent
    	IWritableCustomizationData parent2 = new CustomizationDataImpl(UserCustomizedElementEditFactory.TAG3);
    	data.addParentData(parent2);

    	return data;
    }

    
	@Override
    protected final String getExpectedResult(final String tagName, final String outExt) throws Exception
    {
        final String ext = outExt == null ? "" : "." + outExt;
        final String fileName = "expectedResult_"
                + tagName.replaceAll(":", "_") + ext + ".data";
        final String pathStr = TESTDATA_FILES_PATH + "/" + _compareDataSubDir
                + "/" + fileName;
        return getExpectedResult(pathStr);
    }
}
