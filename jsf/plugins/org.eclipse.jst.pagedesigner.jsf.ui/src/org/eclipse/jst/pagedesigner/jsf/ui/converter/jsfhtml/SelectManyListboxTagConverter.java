/*******************************************************************************
 * Copyright (c) 2006 Sybase, Inc. and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http:// www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Sybase, Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jst.pagedesigner.jsf.ui.converter.jsfhtml;

import java.util.List;

import org.w3c.dom.Element;

/**
 * @author mengbo
 * @version 1.5
 * @deprecated Use DTTagConverter meta-data instead
 */
public class SelectManyListboxTagConverter extends SelectBasedTagConverter
{

    /**
     * @param host
     */
    public SelectManyListboxTagConverter(Element host)
    {
        super(host);
    }

    /* (non-Javadoc)
     * @see org.eclipse.jst.pagedesigner.converter.jsfhtml.SelectBasedTagConverter#handleMultipleAndSize(org.w3c.dom.Element, org.w3c.dom.Element)
     */
    protected void handleMultipleAndSize(Element hostEle, Element selectEle)
    {
        // If the component is a UISelectMany instance, render "multiple" as the value of the 
        // "multiple" attribute.
        selectEle.setAttribute("multiple", "multiple"); //$NON-NLS-1$ //$NON-NLS-2$

        // If the "size" attribute is specified, render its value as the value of the "size" 
        // attribute. Otherwise use the number of items as the value of the "size" attribute.
        String sizeattr = hostEle.getAttribute("size"); //$NON-NLS-1$

        List selectItems = this.getSelectItems(hostEle);
        if (sizeattr == null)
        {
            if (!selectItems.isEmpty())
            {
                selectEle.setAttribute("size", String.valueOf(selectItems.size())); //$NON-NLS-1$
            }
        }
    }

}
