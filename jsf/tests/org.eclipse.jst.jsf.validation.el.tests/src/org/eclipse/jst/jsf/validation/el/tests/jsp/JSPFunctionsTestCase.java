package org.eclipse.jst.jsf.validation.el.tests.jsp;

import java.util.List;

import org.eclipse.jst.jsf.validation.el.tests.base.SingleJSPTestCase;

public class JSPFunctionsTestCase extends SingleJSPTestCase 
{
    protected void setUp() throws Exception
    {
        _srcFileName = "/testdata/jsps/jspFunctions.jsp.data";
        _destFileName = "/jspFunctions.jsp";
        super.setUp();
    }

    public void testSanity()
    {
        assertEquals("f:someFunc(6) > 8", getELText(_structuredDocument,967));
        assertEquals("f:someFunc(6)", getELText(_structuredDocument,1018));
        assertEquals("f:someFunc(true)", getELText(_structuredDocument,1065));
        assertEquals("someFunc(6) > 8", getELText(_structuredDocument,1260));
        assertEquals("someFunc(6)", getELText(_structuredDocument,1309));
        assertEquals("someFunc(true)", getELText(_structuredDocument,1354));
    }

    public void testNoErrorExprs() 
    {
        assertNoError(967, null);
        assertNoError(1018, null);
        assertNoError(1065, null);
    }

    public void testWarningExprs() 
    {
        // note: this will change to non-error when functions are properly supported
        // by the parser
        List list = assertSyntaxWarning(1260, 1);
        assertContainsProblem(list, 0);

        list = assertSyntaxWarning(1309, 1);
        assertContainsProblem(list, 0);
        
        list = assertSyntaxWarning(1354, 1);
        assertContainsProblem(list, 0);
    }
    
    public void testErrorExprs() 
    {
        // no error cases
    }

}
