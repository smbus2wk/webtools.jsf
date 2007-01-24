/**
 * <copyright>
 * </copyright>
 *
 * $Id: MetadataResourceImpl.java,v 1.2 2007/01/24 17:22:47 gkessler Exp $
 */
package org.eclipse.jst.jsf.common.metadata.internal.util;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl;
import org.eclipse.jst.jsf.common.metadata.internal.provisional.Entity;
import org.eclipse.jst.jsf.common.metadata.internal.provisional.MetadataPackage;
import org.eclipse.jst.jsf.common.metadata.internal.provisional.Model;
import org.eclipse.jst.jsf.common.metadata.internal.provisional.Trait;

/**
 * <!-- begin-user-doc -->
 * The <b>Resource </b> associated with the package.
 * <!-- end-user-doc -->
 * @see org.eclipse.jst.jsf.common.metadata.internal.util.MetadataResourceFactoryImpl
 * @generated NOT
 */
public class MetadataResourceImpl extends XMLResourceImpl implements XMLResource.ResourceHandler {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Oracle inc.";

	/**
	 * Creates an instance of the resource.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param uri the URI of the new resource.
	 * @generated
	 */
	public MetadataResourceImpl(URI uri) {
		super(uri);
	}

	public MetadataResourceImpl() {
		super();
	}
	
	public void postLoad(XMLResource resource, InputStream inputStream,
			Map options) {
		Model root = (Model)resource.getContents().get(0);
		setModelKeyInTraits(root, root);
	}

	private void setModelKeyInTraits(Model root, Entity currentEntity) {
		EStructuralFeature feature = MetadataPackage.eINSTANCE.getTrait_SourceModel();
		for (int i=0;i < currentEntity.getTraits().size();i++){
			((Trait)currentEntity.getTraits().get(i)).setSourceModel(root);
		}
		for (int j=0;j < currentEntity.getChildEntities().size();j++){
			setModelKeyInTraits(root,(Entity)currentEntity.getChildEntities().get(j));
		}
	}


	public void postSave(XMLResource resource, OutputStream outputStream,
			Map options) {
		// TODO Auto-generated method stub
		
	}

	public void preLoad(XMLResource resource, InputStream inputStream,
			Map options) {
		// TODO Auto-generated method stub
		
	}

	public void preSave(XMLResource resource, OutputStream outputStream,
			Map options) {
		// TODO Auto-generated method stub
		
	}

} //MetadataResourceImpl