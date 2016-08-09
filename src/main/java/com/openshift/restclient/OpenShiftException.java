/*******************************************************************************
 * Copyright (c) 2015 Red Hat, Inc. Distributed under license by Red Hat, Inc.
 * All rights reserved. This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Red Hat, Inc.
 ******************************************************************************/
package com.openshift.restclient;

import org.apache.commons.lang.StringUtils;

import com.openshift.restclient.model.IStatus;

/**
 * @author Andre Dietisheim
 */
public class OpenShiftException extends RuntimeException {

	private static final long serialVersionUID = -7076942050102006278L;
	private IStatus status;

	public OpenShiftException(Throwable cause, String message, Object... arguments) {
		super(String.format(message, arguments), cause);
	}

	public OpenShiftException(String message, Object... arguments) {
		this(null, null, message, arguments);
	}
	
	public OpenShiftException(Throwable cause, IStatus status, String message, Object... arguments ) {
		super(String.format(StringUtils.defaultIfBlank(message, ""), arguments), cause);
		this.status = status;
	}		
	
	public IStatus getStatus(){
		return this.status;
	}
	
	public boolean hasStatus() {
		return this.status != null;
	}
	
	@Override
	public String getMessage() {
		if(hasStatus()) {
			return super.getMessage() + " " + status.getMessage();
		}
		return super.getMessage();
	}
	
	
	
}
