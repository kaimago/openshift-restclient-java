/*******************************************************************************
 * Copyright (c) 2015 Red Hat, Inc.
 * Distributed under license by Red Hat, Inc. All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat, Inc. - initial API and implementation
 ******************************************************************************/
package com.openshift.restclient.model.deploy;

import java.util.Collection;

import com.openshift.restclient.images.DockerImageURI;

/**
 * 
 * @author Jeff Cantrill
 *
 */
public interface IDeploymentImageChangeTrigger extends IDeploymentTrigger {

	/**
	 * Returns the name of the docker image repo to watch
	 * @return
	 */
	DockerImageURI getFrom();

	/**
	 * Automatically sets kind to "ImageStreamTag" if kind is empty
	 * @param fromImage
	 */
	void setFrom(DockerImageURI fromImage);
	
	/**
	 * The namespace of the ImageStreamTag
	 * @param namespace
	 */
	void setNamespace(String namespace);

	/**
	 * @return The namespace of the ImageStreamTag
	 * 
	 */
	String getNamespace();

	void setKind(String kind);
	
	String getKind();
	
	boolean isAutomatic();
	
	void setAutomatic(boolean auto);
	
	/**
	 * The container names for this trigger
	 * @return
	 */
	Collection<String> getContainerNames();
	
	void setContainerNames(Collection<String> names);

	/**
	 * Convenience method for setting
	 * a single container name
	 * @param names
	 */
	void setContainerName(String names);
}
