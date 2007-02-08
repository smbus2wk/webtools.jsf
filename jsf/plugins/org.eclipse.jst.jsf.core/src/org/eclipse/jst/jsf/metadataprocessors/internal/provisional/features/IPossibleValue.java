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

package org.eclipse.jst.jsf.metadataprocessors.internal.provisional.features;

import org.eclipse.jface.resource.ImageDescriptor;
//experimental and subject to change
public interface IPossibleValue {
	/**
	 * @return value to set.  should not return null.
	 */
	public String getValue();
	/**
	 * @return value to display in a proposal which can be different than what is set.  Must not return null.
	 */
	public String getDisplayValue();
	/**
	 * @return ImageDescriptor for image to be displayed in a proposal.   May be null.
	 */
	public ImageDescriptor getIcon();
	/**
	 * @return true if is known to be the default value.  
	 */
	public boolean isDefaultValue();
	
	/**
	 * @return additional information that could be used for descriptive help
	 * May be null
	 */
	public String getAdditionalInformation();
}