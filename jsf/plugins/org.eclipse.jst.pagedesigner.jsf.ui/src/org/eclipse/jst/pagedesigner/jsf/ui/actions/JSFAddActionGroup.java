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
package org.eclipse.jst.pagedesigner.jsf.ui.actions;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jst.jsf.facesconfig.emf.ConverterIdType;
import org.eclipse.jst.jsf.facesconfig.emf.ConverterType;
import org.eclipse.jst.jsf.facesconfig.emf.FacesConfigType;
import org.eclipse.jst.jsf.facesconfig.emf.ValidatorIdType;
import org.eclipse.jst.jsf.facesconfig.emf.ValidatorType;
import org.eclipse.jst.jsf.facesconfig.util.FacesConfigArtifactEdit;
import org.eclipse.jst.pagedesigner.editors.PageDesignerActionConstants;
import org.eclipse.jst.pagedesigner.utils.StructuredModelUtil;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMElement;

/**
 * @author mengbo
 * @version 1.5
 */
public class JSFAddActionGroup {

	private final static Action EMPTY_ACTION = new Action() {
        // TODO: why?
	};

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.actions.ActionGroup#fillContextMenu(org.eclipse.jface.action.IMenuManager)
	 */
	public void fillContextMenu(IMenuManager menu, final IDOMElement element,
			final IJSFCoreSupport support) {
		final IMenuManager submenu = new MenuManager(ActionsResources
				.getString("Submenu.JSFAdd"));//$NON-NLS-1$
		submenu.add(EMPTY_ACTION);
		submenu.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				submenu.removeAll();
				addJSFAddItems(submenu, element, support);
			}
		});
		menu.appendToGroup(PageDesignerActionConstants.GROUP_SPECIAL, submenu);
	}

	/**
	 * @param submenu
	 * @param element
	 */
	private void addJSFAddItems(IMenuManager submenu, IDOMElement element,
			IJSFCoreSupport support) {
		AddActionListenerAction actionListenerAction = new AddActionListenerAction(
				element);
		actionListenerAction.setEnabled(support.isActionSource());
		submenu.add(actionListenerAction);

		AddAttributeAction attrAction = new AddAttributeAction(element);
		attrAction.setEnabled(support.isUIComponent());
		submenu.add(attrAction);

		IProject prj = null;
		IFile file = StructuredModelUtil.getFileFor(element.getModel());
		if (file != null) {
			prj = file.getProject();
		}

		IMenuManager converterMenu = new MenuManager(ActionsResources
				.getString("Submenu.JSFAdd.Converter"));//$NON-NLS-1$
		boolean supportConverter = support.isValueHolder();
		String[] converterIds = getRegisteredConverterIds(prj);
		if (converterIds != null && converterIds.length > 0) {
			for (int i = 0; i < converterIds.length; i++) {
				AddConverterAction action = new AddConverterAction(
						converterIds[i], element);
				action.setEnabled(supportConverter);
				converterMenu.add(action);
			}
			converterMenu.add(new Separator());
		}
		AddConvertDateTimeAction dateTimeAction = new AddConvertDateTimeAction(
				element);
		dateTimeAction.setEnabled(supportConverter);
		converterMenu.add(dateTimeAction);
		AddConvertNumberAction numberAction = new AddConvertNumberAction(
				element);
		numberAction.setEnabled(supportConverter);
		converterMenu.add(numberAction);
		submenu.add(converterMenu);

		AddParamAction addParamAction = new AddParamAction(element);
		addParamAction.setEnabled(support.isUIComponent());
		submenu.add(addParamAction);

		AddSelectItemAction selectItemAction = new AddSelectItemAction(element);
		selectItemAction.setEnabled(support.supportSelectItems());
		submenu.add(selectItemAction);

		AddSelectItemsAction selectItemsAction = new AddSelectItemsAction(
				element);
		selectItemsAction.setEnabled(support.supportSelectItems());
		submenu.add(selectItemsAction);

		IMenuManager validatorMenu = new MenuManager(ActionsResources
				.getString("Submenu.JSFAdd.Validators"));//$NON-NLS-1$
		boolean supportValidator = support.isEditableValueHolder();
		String[] validatorIds = getRegisteredValidatorIds(prj);
		if (validatorIds != null && validatorIds.length > 0) {
			for (int i = 0; i < validatorIds.length; i++) {
				AddValidatorAction action = new AddValidatorAction(
						validatorIds[i], element);
				action.setEnabled(supportValidator);
				validatorMenu.add(action);
			}
			validatorMenu.add(new Separator());
		}
		AddValidateDoubleRangeAction doubleRangeAction = new AddValidateDoubleRangeAction(
				element);
		doubleRangeAction.setEnabled(supportValidator);
		validatorMenu.add(doubleRangeAction);
		AddValidateLengthAction lengthAction = new AddValidateLengthAction(
				element);
		lengthAction.setEnabled(supportValidator);
		validatorMenu.add(lengthAction);
		AddValidateLongRangeAction longRangeAction = new AddValidateLongRangeAction(
				element);
		longRangeAction.setEnabled(supportValidator);
		validatorMenu.add(longRangeAction);
		submenu.add(validatorMenu);

		AddValueChangeListenerAction valueChangeAction = new AddValueChangeListenerAction(
				element);
		valueChangeAction.setEnabled(support.isEditableValueHolder());
		submenu.add(valueChangeAction);
	}

	/**
	 * @return
	 */
	private String[] getRegisteredValidatorIds(IProject project) {
		FacesConfigArtifactEdit facesConfigArtifactEdit = FacesConfigArtifactEdit
				.getFacesConfigArtifactEditForRead(project);

		if (facesConfigArtifactEdit == null)
			return null;

		FacesConfigType facesConfigType = facesConfigArtifactEdit
				.getFacesConfig();
		EList list = facesConfigType.getValidator();
		int size = list.size();
		if (size == 0)
			return null;

		String[] result = new String[size];
		for (int i = 0, n = list.size(); i < n; i++) {
			ValidatorType validator = (ValidatorType) list.get(i);
			ValidatorIdType validatorId = validator.getValidatorId();
			if (validatorId != null)
				result[i] = validatorId.getTextContent().trim();
		}
		return result;
	}

	/**
	 * @return
	 */
	private String[] getRegisteredConverterIds(IProject project) {
		FacesConfigArtifactEdit facesConfigArtifactEdit = FacesConfigArtifactEdit
				.getFacesConfigArtifactEditForRead(project);

		if (facesConfigArtifactEdit == null)
			return null;

		FacesConfigType facesConfigType = facesConfigArtifactEdit
				.getFacesConfig();
		EList list = facesConfigType.getConverter();
		int size = list.size();
		if (size == 0)
			return null;

		String[] result = new String[size];
		for (int i = 0, n = list.size(); i < n; i++) {
			ConverterType converter = (ConverterType) list.get(i);
			ConverterIdType converterId = converter.getConverterId();
			if (converterId != null)
				result[i] = converterId.getTextContent().trim();
		}
		return result;
	}
}
