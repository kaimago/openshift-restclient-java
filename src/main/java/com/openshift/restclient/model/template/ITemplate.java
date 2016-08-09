/*******************************************************************************
 * Copyright (c) 2015 Red Hat, Inc. Distributed under license by Red Hat, Inc.
 * All rights reserved. This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Red Hat, Inc.
 ******************************************************************************/
package com.openshift.restclient.model.template;

import java.util.Collection;
import java.util.Map;

import com.openshift.restclient.model.IResource;

/**
 * Representation of a set of pre-configured parameterized set of resources
 * 
 * @author Jeff Cantrill
 */
public interface ITemplate extends IResource {
	
	/**
	 * Retrieves the list of resources this template
	 * creates
	 * @return
	 */
	Collection<IResource> getObjects();
	
	/**
	 * Returns a map of parameter names to parameters.
	 * @return
	 */
	Map<String, IParameter> getParameters();
	
	/**
	 * Update the template's parameter values, with the values from the
	 * collection if a parameter with the same name is found
	 * 
	 * @param parameters
	 */
	void updateParameterValues(Collection<IParameter> parameters);
	
	
	/**
	 * Update a parameter if it exists
	 * @param key
	 * @param value
	 */
	void updateParameter(String key, String value);
	
	/**
	 * Retrieves the labels that are applied to the objects of the
	 * template when it is processed.
	 * 
	 * @return Unmodifiable map of the item labels
	 */
	Map<String, String> getObjectLabels();
	
	/**
	 * Add or update a label that is applied to the objects of the
	 * template when it is processed
	 * 
	 * @param key
	 * @param value
	 */
	void addObjectLabel(String key, String value);

	/**
	 * Returns <code>true</code> if the given resource contains the given text
	 * in name or tags.
	 * 
	 * @param filterText
	 * @param template
	 * @return
	 */
	public boolean isMatching(final String filterText);
}
