/*******************************************************************************
 * Copyright (c) 2007 Oracle Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Oracle Corporation - initial API and implementation
 *******************************************************************************/

package org.eclipse.jst.jsf.common.metadata.tests.updated;

import java.io.IOException;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.eclipse.jst.jsf.common.metadata.query.MetaDataException;

public class MetaDataExceptionTests extends TestCase {

	public void testMetaDataException() {
		MetaDataException e = new MetaDataException();
		Assert.assertNotNull(e);
	}

	public void testMetaDataExceptionString() {
		MetaDataException e = new MetaDataException("Foo");
		Assert.assertNotNull(e);
		Assert.assertEquals("Foo", e.getMessage());
	}

	public void testMetaDataExceptionStringThrowable() {
		IOException cause = new IOException("FooBar");
		MetaDataException e = new MetaDataException("Foo", cause );
		Assert.assertNotNull(e);
		Assert.assertEquals(cause, e.getCause());
		Assert.assertNotNull(e.getCause());
	}

}
