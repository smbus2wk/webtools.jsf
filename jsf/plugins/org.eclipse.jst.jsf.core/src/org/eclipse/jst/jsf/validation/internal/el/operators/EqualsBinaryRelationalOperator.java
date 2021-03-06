/*******************************************************************************
 * Copyright (c) 2006 Oracle Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Cameron Bateman/Oracle - initial API and implementation
 *    
 ********************************************************************************/

package org.eclipse.jst.jsf.validation.internal.el.operators;

import org.eclipse.jst.jsf.validation.internal.el.diagnostics.DiagnosticFactory;

/**
 * Represents the equals -- ==/eq EL binary operator
 * 
 * @author cbateman
 *
 */
/*package*/ class EqualsBinaryRelationalOperator extends
        EqualityRelationalBinaryOperator 
{

    EqualsBinaryRelationalOperator(final DiagnosticFactory diagnosticFactory, String jsfVersion) {
        super(diagnosticFactory, jsfVersion);
    }

    /**
     * @param firstArg
     * @param secondArg
     * @return the result of the operation
     */
    protected boolean doRealOperation(Number firstArg, Number secondArg)
    {
        return firstArg.equals(secondArg);
    }
    
    /**
     * @param firstArg
     * @param secondArg
     * @return the result of the operation
     */
    protected boolean doRealOperation(Boolean firstArg, Boolean secondArg)
    {
        return firstArg.equals(secondArg);
    }
    
    /**
     * @param firstArg
     * @param secondArg
     * @return the result  of the operation
     */
    protected boolean doRealOperation(String firstArg, String secondArg)
    {
        return firstArg.equals(secondArg);
    }

    protected String getOperationName() 
    {
        return Messages.getString("EqualsBinaryRelationalOperator.OperationName"); //$NON-NLS-1$
    }
}
