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
package org.eclipse.jst.pagedesigner.viewer;

import org.eclipse.jface.viewers.ISelectionChangedListener;

/**
 * @author mengbo
 */
public interface IHTMLGraphicalViewerListener extends ISelectionChangedListener {
	/**
	 * Fired before selection changes
	 */
	public void selectionAboutToChange();

	/**
	 * Fired after selection changed listeners have all been processed
	 * for a change
	 */
	public void selectionChangeFinished();
}
