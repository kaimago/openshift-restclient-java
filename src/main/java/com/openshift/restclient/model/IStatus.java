/*******************************************************************************
 * Copyright (c) 2015 Red Hat, Inc. Distributed under license by Red Hat, Inc.
 * All rights reserved. This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Red Hat, Inc.
 ******************************************************************************/
package com.openshift.restclient.model;

/**
 * Status message resulting from trying to manipulate an
 * OpenShift resource and an error occurs
 * 
 * @author Jeff Cantrill
 */
public interface IStatus extends IResource {

	public static final String SUCCESS = "Success";
	public static final String FAILURE = "Failure";
	
	/**
	 * Returns the status message
	 * @return
	 */
	String getMessage();

	/**
	 * The HTTP status code
	 * @return an int
	 */
	int getCode();
	
	/**
	 * The status info of the status
	 * @return
	 */
	String getStatus();
	
	/**
	 * Determine if this status is indicates
	 * a failure
	 * @return
	 */
	boolean isFailure();

	/**
	 * Returns {@code true} if this status represent success}
	 * @return
	 */
	boolean isSuccess();
}
