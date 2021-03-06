/*******************************************************************************
 * Copyright (c) 2004, 2005 Sybase, Inc. and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Sybase, Inc. - initial API and implementation
 *******************************************************************************/

package org.eclipse.jst.jsf.facesconfig.ui.pageflow.editpolicy;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ConnectionEditPolicy;
import org.eclipse.gef.requests.GroupRequest;
import org.eclipse.jst.jsf.facesconfig.ui.pageflow.command.ConnectionCommand;
import org.eclipse.jst.jsf.facesconfig.ui.pageflow.command.DeleteConnectionCommand;
import org.eclipse.jst.jsf.facesconfig.ui.pageflow.model.PageflowLink;

/**
 * Customize the connection edit policy for pageflow links
 *
 */
public class PFLinkEditPolicy extends ConnectionEditPolicy {

	/**
	 * Default constructor
	 */
	public PFLinkEditPolicy() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ConnectionEditPolicy#getDeleteCommand
	 */
	protected Command getDeleteCommand(GroupRequest request) {
		ConnectionCommand c = new DeleteConnectionCommand();
		c.setPFLink((PageflowLink) getHost().getModel());
		return c;
	}

}
