/*******************************************************************************
 * Copyright (c) 2006 Sybase, Inc. and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Sybase, Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jst.pagedesigner.converter;

import org.w3c.dom.Element;

/**
 * @author mengbo
 * @version 1.5
 */
public class DumDescriptionTagConverter extends AbstractTagConverter {
	/**
	 * @param host
	 */
	public DumDescriptionTagConverter(Element host) {
		super(host);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jst.pagedesigner.converter.AbstractTagConverter#doConvertRefresh()
	 */
	protected Element doConvertRefresh() {
		Element result = createElement(getHostElement().getTagName());
		ConverterUtil.copyAllAttributes(getHostElement(), result, null);
		copyChildren(getHostElement(), result);

		if (!this.isPreviewMode()
				&& ConverterUtil.isEmptyContainer(getHostElement())) {
			result.appendChild(ConverterUtil.createDescriptionElement(
					getDestDocument(), null));
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jst.pagedesigner.converter.ITagConverter#isMultiLevel()
	 */
	public boolean isMultiLevel() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jst.pagedesigner.css2.style.ITagEditInfo#isWidget()
	 */
	public boolean isWidget() {
		return false;
	}

}
