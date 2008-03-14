/**
 * Copyright (c) 2008 Oracle Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Oracle Corporation - initial API and implementation
 */
package org.eclipse.jst.jsf.apache.trinidad.tagsupport.converter.operations;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jst.pagedesigner.converter.ConvertPosition;
import org.eclipse.jst.pagedesigner.dtmanager.converter.ITransformOperation;
import org.eclipse.jst.pagedesigner.dtmanager.converter.operations.TransformOperationFactory;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

/**
 * ITransformOperation implementation specifically for the "inputText" JSF
 * Element.
 * 
 * <br><b>Note:</b> requires ITransformOperation.setTagConverterContext(...) to
 * have been called to provide a valid ITagConverterContext instance prior to
 * a call to the transform(...) method.
 * 
 * @author Ian Trimble - Oracle
 */
public class InputTextOperation extends AbstractTrinidadTransformOperation {

	private static final String STYLE_OUTERELEMENT = "font-family:Arial,Helvetica,Geneva,sans-serif;font-size:10pt;font-weight:normal;color:#000000;"; //$NON-NLS-1$
	private static final String STYLE_LABELTD = "font-family:Arial,Helvetica,Geneva,sans-serif;font-size:10pt;text-align:right;color:#000000;padding:0px 8px 0px 0px;font-weight:normal;"; //$NON-NLS-1$
	private static final String STYLE_REQUIREDSPAN = "color:#669966;font-family:Courier,sans-serif;"; //$NON-NLS-1$
	private static final String STYLE_CONTROLELEMENT = "font-family:Arial,Helvetica,Geneva,sans-serif;font-size:10pt;font-weight:normal;color:#000000;"; //$NON-NLS-1$
	private static final String STYLE_HELPSPAN = "font-family:Arial,Helvetica,Geneva,sans-serif;font-size:8pt;font-weight:normal;color:#669966;"; //$NON-NLS-1$

	/* (non-Javadoc)
	 * @see org.eclipse.jst.pagedesigner.dtmanager.converter.operations.AbstractTransformOperation#transform(org.w3c.dom.Element, org.w3c.dom.Element)
	 */
	@Override
	public Element transform(Element srcElement, Element curElement) {
		Element outerElement;

		//create outer Element
		String simple = srcElement.getAttribute("simple"); //$NON-NLS-1$
		if (Boolean.valueOf(simple)) {
			outerElement = createElement("span"); //$NON-NLS-1$
		} else {
			outerElement = createElement("table"); //$NON-NLS-1$
			//append table-specific attributes
			appendAttribute(outerElement, "cellpadding", "0"); //$NON-NLS-1$
			appendAttribute(outerElement, "cellspacing", "0"); //$NON-NLS-1$
			appendAttribute(outerElement, "border", "0"); //$NON-NLS-1$
		}

		//append common attributes
		ITransformOperation operation =
			TransformOperationFactory.getInstance().getTransformOperation(
					TransformOperationFactory.OP_CopyAttributeWithRenameOperation,
					new String[]{"styleClass", "class"}); //$NON-NLS-1$  //$NON-NLS-2$
		operation.transform(srcElement, outerElement);
		appendAttribute(outerElement, "style", //$NON-NLS-1$
				calculateStyle(STYLE_OUTERELEMENT, srcElement, "inlineStyle")); //$NON-NLS-1$

		if (Boolean.valueOf(simple)) {
			//continue building simple variant
			appendControl(srcElement, outerElement);
		} else {
			//continue building non-simple variant
			Element trElement = appendChildElement("tr", outerElement); //$NON-NLS-1$

			if (isRequired(srcElement) || getLabel(srcElement) != null) {
				//if either required or has label, build top-left table cell
				Element tdElement = appendChildElement("td", trElement); //$NON-NLS-1$
				appendAttribute(tdElement, "style", STYLE_LABELTD); //$NON-NLS-1$
				if (getRows(srcElement) > 1) {
					appendAttribute(tdElement, "valign", "top"); //$NON-NLS-1$ //$NON-NLS-2$
				}
				appendAttribute(tdElement, "nowrap", ""); //$NON-NLS-1$ //$NON-NLS-2$

				if (isRequired(srcElement)) {
					Element spanElement = appendChildElement("span", tdElement); //$NON-NLS-1$
					appendAttribute(spanElement, "title", "Required"); //$NON-NLS-1$ //$NON-NLS-2$
					appendAttribute(spanElement, "style", STYLE_REQUIREDSPAN); //$NON-NLS-1$
					appendChildText("*", spanElement); //$NON-NLS-1$
					appendChildText(" ", tdElement); //$NON-NLS-1$
				}

				if (getLabel(srcElement) != null) {
					Element labelElement = appendChildElement("label", tdElement); //$NON-NLS-1$
					appendChildText(getLabel(srcElement), labelElement);
				}
			}

			//build content table cell
			Element tdElement = appendChildElement("td", trElement); //$NON-NLS-1$
			appendAttribute(tdElement, "valign", "top"); //$NON-NLS-1$ //$NON-NLS-2$
			appendAttribute(tdElement, "nowrap", ""); //$NON-NLS-1$ //$NON-NLS-2$
			appendControl(srcElement, tdElement);

			List<Node> helpFacetChildNodes = getHelpFacetChildNodes(srcElement);
			if (helpFacetChildNodes.size() > 0) {
				//build help table row
				trElement = appendChildElement("tr", outerElement); //$NON-NLS-1$
				if (isRequired(srcElement) || getLabel(srcElement) != null) {
					tdElement = appendChildElement("td", trElement); //$NON-NLS-1$
				}
				tdElement = appendChildElement("td", trElement); //$NON-NLS-1$
				Element spanElement = appendChildElement("span", tdElement); //$NON-NLS-1$
				operation =
					TransformOperationFactory.getInstance().getTransformOperation(
							TransformOperationFactory.OP_CopyAttributeWithRenameOperation,
							new String[]{"styleClass", "class"}); //$NON-NLS-1$  //$NON-NLS-2$
				operation.transform(srcElement, spanElement);
				appendAttribute(spanElement, "style", //$NON-NLS-1$
						calculateStyle(STYLE_HELPSPAN, srcElement, "inlineStyle")); //$NON-NLS-1$
				Iterator<Node> itHelpFacetChildNodes = helpFacetChildNodes.iterator();
				int curPos = 0;
				while (itHelpFacetChildNodes.hasNext()) {
					Node curNode = itHelpFacetChildNodes.next();
					if (curNode instanceof Text) {
						if (curNode.getNodeValue() != null && curNode.getNodeValue().length() > 0) {
							tagConverterContext.addChild(
									curNode,
									new ConvertPosition(spanElement, curPos));
						}
					} else {
						tagConverterContext.addChild(
								curNode,
								new ConvertPosition(spanElement, curPos));
					}
					curPos++;
				}
			}
		}

		return outerElement;
	}

	private String calculateStyle(
			String baseStyle, Element srcElement, String styleAttributeName) {
		String style = baseStyle;
		if (srcElement != null && styleAttributeName != null) {
			String srcElementStyle = srcElement.getAttribute(styleAttributeName);
			if (srcElementStyle != null && srcElementStyle.length() > 0) {
				if (style != null) {
					if (!style.endsWith(";")) { //$NON-NLS-1$
						style += ";"; //$NON-NLS-1$
					}
					style += srcElementStyle;
				} else {
					style = srcElementStyle;
				}
			}
		}
		return style;
	}

	private void appendControl(Element srcElement, Element parentElement) {
		int rows = getRows(srcElement);
		Element controlElement;
		if (rows < 2) {
			controlElement = appendChildElement("input", parentElement); //$NON-NLS-1$
			appendAttribute(controlElement, "size", //$NON-NLS-1$
					String.valueOf(getColumns(srcElement)));
			appendAttribute(controlElement, "type", getType(srcElement)); //$NON-NLS-1$
			String value = srcElement.getAttribute("value"); //$NON-NLS-1$
			if (value != null) {
				appendAttribute(controlElement, "value", value); //$NON-NLS-1$
			}
		} else {
			controlElement = appendChildElement("textarea", parentElement); //$NON-NLS-1$
			appendAttribute(controlElement, "cols", //$NON-NLS-1$
					String.valueOf(getColumns(srcElement)));
			appendAttribute(controlElement, "rows", //$NON-NLS-1$
					String.valueOf(getRows(srcElement)));
			appendAttribute(controlElement, "wrap", getWrap(srcElement)); //$NON-NLS-1$
			String value = srcElement.getAttribute("value"); //$NON-NLS-1$
			if (value != null) {
				appendChildText(value, controlElement);
			}
		}
		//append common attributes
		appendAttribute(controlElement, "style", //$NON-NLS-1$
				calculateStyle(STYLE_CONTROLELEMENT, srcElement, "contentStyle")); //$NON-NLS-1$
	}

	private int getRows(Element srcElement) {
		int rows = 1;
		if (srcElement != null) {
			String rowsValue = srcElement.getAttribute("rows"); //$NON-NLS-1$
			if (rowsValue != null && rowsValue.length() > 0) {
				try {
					rows = Integer.parseInt(rowsValue);
				} catch(NumberFormatException nfe) {
					//ignore; default value will be returned
				}
			}
		}
		return rows;
	}

	private int getColumns(Element srcElement) {
		int columns = 30;
		if (srcElement != null) {
			String columnsValue = srcElement.getAttribute("columns"); //$NON-NLS-1$
			if (columnsValue != null && columnsValue.length() > 0) {
				try {
					columns = Integer.parseInt(columnsValue);
				} catch(NumberFormatException nfe) {
					//ignore; default value will be returned
				}
			}
		}
		return columns;
	}

	private String getWrap(Element srcElement) {
		String wrap = "soft"; //$NON-NLS-1$
		if (srcElement != null) {
			String wrapValue = srcElement.getAttribute("wrap"); //$NON-NLS-1$
			if (wrapValue != null && wrapValue.length() > 0) {
				wrap = wrapValue;
			}
		}
		return wrap;
	}

	private String getType(Element srcElement) {
		String type = "text"; //$NON-NLS-1$
		if (srcElement != null) {
			String secret = srcElement.getAttribute("secret"); //$NON-NLS-1$
			if (Boolean.valueOf(secret)) {
				type = "password"; //$NON-NLS-1$
			}
		}
		return type;
	}

	private boolean isRequired(Element srcElement) {
		boolean required = false;
		if (srcElement != null) {
			String requiredVal = srcElement.getAttribute("required"); //$NON-NLS-1$
			String showRequiredVal = srcElement.getAttribute("showRequired"); //$NON-NLS-1$
			//if either are true, for the purposes of tag conversion, consider required to be true
			required =
				Boolean.parseBoolean(requiredVal) ||
				Boolean.parseBoolean(showRequiredVal);
		}
		return required;
	}

	private String getLabel(Element srcElement) {
		String label = null;
		if (srcElement != null) {
			String labelAndAccessKeyVal = srcElement.getAttribute("labelAndAccessKey"); //$NON-NLS-1$
			if (labelAndAccessKeyVal != null && labelAndAccessKeyVal.length() > 0) {
				label = labelAndAccessKeyVal;
			} else {
				String labelVal = srcElement.getAttribute("label"); //$NON-NLS-1$
				if (labelVal != null && labelVal.length() > 0) {
					label = labelVal;
				}
			}
		}
		return label;
	}

	private List<Node> getHelpFacetChildNodes(Element srcElement) {
		@SuppressWarnings("unchecked")
		List<Node> children = Collections.EMPTY_LIST;
		Element helpFacet = getChildFacetByName(srcElement, "help"); //$NON-NLS-1$
		if (helpFacet != null) {
			children = getCopyChildrenNodes(helpFacet);
		}
		return children;
	}

}
