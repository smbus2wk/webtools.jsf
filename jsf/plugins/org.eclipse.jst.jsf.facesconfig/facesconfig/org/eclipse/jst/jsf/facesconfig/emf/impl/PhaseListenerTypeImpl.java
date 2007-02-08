/***************************************************************************************************
 * Copyright (c) 2005, 2006 IBM Corporation and others. 
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: 
 *   IBM Corporation - initial API and implementation
 *   Oracle Corporation - revision
 **************************************************************************************************/
package org.eclipse.jst.jsf.facesconfig.emf.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.jst.jsf.facesconfig.emf.FacesConfigPackage;
import org.eclipse.jst.jsf.facesconfig.emf.PhaseListenerType;


/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Phase Listener Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.jst.jsf.facesconfig.emf.impl.PhaseListenerTypeImpl#getTextContent <em>Text Content</em>}</li>
 *   <li>{@link org.eclipse.jst.jsf.facesconfig.emf.impl.PhaseListenerTypeImpl#getId <em>Id</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PhaseListenerTypeImpl extends EObjectImpl implements PhaseListenerType {
    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public static final String copyright = "Copyright (c) 2005, 2006 IBM Corporation and others";

    /**
     * The default value of the '{@link #getTextContent() <em>Text Content</em>}' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see #getTextContent()
     * @generated
     * @ordered
     */
	protected static final String TEXT_CONTENT_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getTextContent() <em>Text Content</em>}' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see #getTextContent()
     * @generated
     * @ordered
     */
	protected String textContent = TEXT_CONTENT_EDEFAULT;

    /**
     * The default value of the '{@link #getId() <em>Id</em>}' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see #getId()
     * @generated
     * @ordered
     */
	protected static final String ID_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getId() <em>Id</em>}' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see #getId()
     * @generated
     * @ordered
     */
	protected String id = ID_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	protected PhaseListenerTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	protected EClass eStaticClass() {
        return FacesConfigPackage.Literals.PHASE_LISTENER_TYPE;
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public String getTextContent() {
        return textContent;
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public void setTextContent(String newTextContent) {
        String oldTextContent = textContent;
        textContent = newTextContent;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, FacesConfigPackage.PHASE_LISTENER_TYPE__TEXT_CONTENT, oldTextContent, textContent));
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public String getId() {
        return id;
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public void setId(String newId) {
        String oldId = id;
        id = newId;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, FacesConfigPackage.PHASE_LISTENER_TYPE__ID, oldId, id));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case FacesConfigPackage.PHASE_LISTENER_TYPE__TEXT_CONTENT:
                return getTextContent();
            case FacesConfigPackage.PHASE_LISTENER_TYPE__ID:
                return getId();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
            case FacesConfigPackage.PHASE_LISTENER_TYPE__TEXT_CONTENT:
                setTextContent((String)newValue);
                return;
            case FacesConfigPackage.PHASE_LISTENER_TYPE__ID:
                setId((String)newValue);
                return;
        }
        super.eSet(featureID, newValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void eUnset(int featureID) {
        switch (featureID) {
            case FacesConfigPackage.PHASE_LISTENER_TYPE__TEXT_CONTENT:
                setTextContent(TEXT_CONTENT_EDEFAULT);
                return;
            case FacesConfigPackage.PHASE_LISTENER_TYPE__ID:
                setId(ID_EDEFAULT);
                return;
        }
        super.eUnset(featureID);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean eIsSet(int featureID) {
        switch (featureID) {
            case FacesConfigPackage.PHASE_LISTENER_TYPE__TEXT_CONTENT:
                return TEXT_CONTENT_EDEFAULT == null ? textContent != null : !TEXT_CONTENT_EDEFAULT.equals(textContent);
            case FacesConfigPackage.PHASE_LISTENER_TYPE__ID:
                return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public String toString() {
        if (eIsProxy()) return super.toString();

        StringBuffer result = new StringBuffer(super.toString());
        result.append(" (textContent: ");
        result.append(textContent);
        result.append(", id: ");
        result.append(id);
        result.append(')');
        return result.toString();
    }

} //PhaseListenerTypeImpl