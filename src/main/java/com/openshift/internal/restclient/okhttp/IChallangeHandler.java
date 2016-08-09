/******************************************************************************* 
 * Copyright (c) 2016 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/
package com.openshift.internal.restclient.okhttp;

import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.Request.Builder;

/**
 * A challange handler that can retrieve a token
 * @author jeff.cantrill
 *
 */
interface IChallangeHandler{
	
	/**
	 * Is able to handle a challange given the auth mechanism
	 * provided in the header
	 * 
	 * @param headers
	 * @return true if can handle, false otherwise
	 */
	boolean canHandle(Headers headers);
	
	/**
	 * Handle the challange
	 * 
	 * @param builder
	 * @return
	 */
	Request.Builder handleChallange(Builder builder);
}