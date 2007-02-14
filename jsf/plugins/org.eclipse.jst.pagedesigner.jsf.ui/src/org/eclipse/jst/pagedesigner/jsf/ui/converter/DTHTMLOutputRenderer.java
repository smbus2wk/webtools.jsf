/*******************************************************************************
 * Copyright (c) 2005 Oracle Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Ian Trimble - initial API and implementation
 *******************************************************************************/ 
package org.eclipse.jst.pagedesigner.jsf.ui.converter;

import org.eclipse.jst.pagedesigner.IJSFConstants;
import org.eclipse.jst.pagedesigner.dom.TagIdentifier;
import org.eclipse.jst.pagedesigner.dom.TagIdentifierFactory;
import org.eclipse.jst.pagedesigner.jsf.ui.converter.operations.ConvertAttributeToTextOperation;
import org.eclipse.jst.pagedesigner.jsf.ui.converter.operations.CopyAllAttributesOperation;
import org.eclipse.jst.pagedesigner.jsf.ui.converter.operations.CopyChildrenOperation;
import org.eclipse.jst.pagedesigner.jsf.ui.converter.operations.CreateAttributeOperation;
import org.eclipse.jst.pagedesigner.jsf.ui.converter.operations.CreateElementOperation;
import org.eclipse.jst.pagedesigner.jsf.ui.converter.operations.RenameAttributeOperation;
import org.eclipse.jst.pagedesigner.jsf.ui.converter.operations.jsf.PanelGridOperation;
import org.w3c.dom.Element;

/**
 * Design-time metadata-driven HTML IOutputRenderer implementation.
 * 
 * @author Ian Trimble - Oracle
 */
public class DTHTMLOutputRenderer implements IOutputRenderer {

	private ITagConverterContext tagConverterContext;

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jst.pagedesigner.jsf.ui.converter.IOutputRenderer#render(org.eclipse.jst.pagedesigner.jsf.ui.converter.ITagConverterContext)
	 */
	public Element render(ITagConverterContext tagConverterContext) {
		this.tagConverterContext = tagConverterContext;
		Element srcElement = tagConverterContext.getHostElement();
		Element resultElement = null;
		if (srcElement != null) {
			ITransformer transformer = getTransformer(srcElement);
			resultElement = transformer.transform(srcElement);
		}
		return resultElement;
	}

	/**
	 * Configures and returns an ITransformer instance for the specified source
	 * Element.
	 * 
	 * @param srcElement Source Element for which to configure and return an
	 * ITransformer instance.
	 * @return Configured ITransformer instance for the specified source
	 * Element.
	 */
	protected ITransformer getTransformer(Element srcElement) {
		ITransformer transformer = new DefaultTransformer();
		transformer.setTagConverterContext(tagConverterContext);
        
        TagIdentifier srcTagIdentifier = 
            TagIdentifierFactory.createDocumentTagWrapper(srcElement); 
        
		if (IJSFConstants.TAG_IDENTIFIER_VIEW.isSameTagType(srcTagIdentifier)) {
			transformer.appendTransformOperation(new CreateElementOperation("div"));
			transformer.appendTransformOperation(new CopyChildrenOperation());
		} else if (IJSFConstants.TAG_IDENTIFIER_FACET.isSameTagType(srcTagIdentifier)) {
			transformer.appendTransformOperation(new CreateElementOperation("span"));
			transformer.appendTransformOperation(new CopyChildrenOperation());
		} else if (IJSFConstants.TAG_IDENTIFIER_VERBATIM.isSameTagType(srcTagIdentifier)) {
			transformer.appendTransformOperation(new CreateElementOperation("span"));
			transformer.appendTransformOperation(new CopyChildrenOperation());
		} else if (IJSFConstants.TAG_IDENTIFIER_FORM.isSameTagType(srcTagIdentifier)) {
            transformer.appendTransformOperation(new CreateElementOperation("form"));
            transformer.appendTransformOperation(new CopyAllAttributesOperation());
            transformer.appendTransformOperation(new RenameAttributeOperation("styleClass", "class"));
            transformer.appendTransformOperation(new CopyChildrenOperation());
        } else if (IJSFConstants.TAG_IDENTIFIER_INPUTTEXT.isSameTagType(srcTagIdentifier)) {
			transformer.appendTransformOperation(new CreateElementOperation("input"));
			transformer.appendTransformOperation(new CopyAllAttributesOperation());
			transformer.appendTransformOperation(new RenameAttributeOperation("styleClass", "class"));
			transformer.appendTransformOperation(new CreateAttributeOperation("type", "text"));
        } else if (IJSFConstants.TAG_IDENTIFIER_INPUTSECRET.isSameTagType(srcTagIdentifier)) {
			transformer.appendTransformOperation(new CreateElementOperation("input"));
			transformer.appendTransformOperation(new CopyAllAttributesOperation());
			transformer.appendTransformOperation(new RenameAttributeOperation("styleClass", "class"));
			transformer.appendTransformOperation(new CreateAttributeOperation("type", "password"));
        } else if (IJSFConstants.TAG_IDENTIFIER_INPUTTEXTAREA.isSameTagType(srcTagIdentifier)) {
			transformer.appendTransformOperation(new CreateElementOperation("textarea"));
			transformer.appendTransformOperation(new CopyAllAttributesOperation());
			transformer.appendTransformOperation(new RenameAttributeOperation("styleClass", "class"));
			transformer.appendTransformOperation(new ConvertAttributeToTextOperation("value"));
        } else if (IJSFConstants.TAG_IDENTIFIER_OUTPUTTEXT.isSameTagType(srcTagIdentifier)) {
			transformer.appendTransformOperation(new CreateElementOperation("span"));
			transformer.appendTransformOperation(new CopyAllAttributesOperation());
			transformer.appendTransformOperation(new RenameAttributeOperation("styleClass", "class"));
			transformer.appendTransformOperation(new ConvertAttributeToTextOperation("value"));
        } else if (IJSFConstants.TAG_IDENTIFIER_OUTPUTLABEL.isSameTagType(srcTagIdentifier)) {
			transformer.appendTransformOperation(new CreateElementOperation("label"));
			transformer.appendTransformOperation(new CopyAllAttributesOperation());
			transformer.appendTransformOperation(new RenameAttributeOperation("styleClass", "class"));
			transformer.appendTransformOperation(new ConvertAttributeToTextOperation("value"));
			transformer.appendTransformOperation(new CopyChildrenOperation());
        } else if (IJSFConstants.TAG_IDENTIFIER_GRAPHICIMAGE.isSameTagType(srcTagIdentifier)) {
			transformer.appendTransformOperation(new CreateElementOperation("img"));
			transformer.appendTransformOperation(new CopyAllAttributesOperation());
			transformer.appendTransformOperation(new RenameAttributeOperation("styleClass", "class"));
			transformer.appendTransformOperation(new RenameAttributeOperation("url", "src"));
			//"value" overrides "url" as new "src" attribute
			transformer.appendTransformOperation(new RenameAttributeOperation("value", "src"));
		} else if (IJSFConstants.TAG_IDENTIFIER_PANEL_GRID.isSameTagType(srcTagIdentifier)) {
			transformer.appendTransformOperation(new PanelGridOperation());
		}
		return transformer;
	}

}
