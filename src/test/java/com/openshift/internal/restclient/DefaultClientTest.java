/*******************************************************************************
 * Copyright (c) 2014-2016 Red Hat, Inc.
 * Distributed under license by Red Hat, Inc. All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Red Hat, Inc. - initial API and implementation
 ******************************************************************************/
package com.openshift.internal.restclient;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jboss.dmr.ModelNode;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.openshift.internal.restclient.authorization.AuthorizationContext;
import com.openshift.internal.restclient.model.Pod;
import com.openshift.restclient.IResourceFactory;
import com.openshift.restclient.ResourceKind;
import com.openshift.restclient.model.IPod;

/**
 * @author Jeff Cantrill
 * @author Andre Dietisheim
 */
@RunWith(MockitoJUnitRunner.class)
public class DefaultClientTest extends TypeMapperFixture{

	private static final String VERSION = "v1";

	private DefaultClient client;
	private ModelNode response;
	private Pod podFrontEnd;
	private Pod podBackEnd;
	private IResourceFactory factory;
	private URL baseUrl;
	

	@Before
	public void setUp() throws Exception{
		super.setUp();
		this.baseUrl = new URL("http://myopenshift");
		givenAClient();
		givenAPodList();
		getHttpClient().whenRequestTo("http://myopenshift/api/v1/namespaces/aNamespace/pods").thenReturn(responseOf(response.toJSONString(false)));
	}

	private void givenAClient() throws MalformedURLException{
		factory = new ResourceFactory(null);
		client = new DefaultClient(baseUrl, getHttpClient(), factory, getApiTypeMapper(), new AuthorizationContext(null));
	}

	private void givenAPodList(){
		this.podFrontEnd = factory.create(VERSION, ResourceKind.POD);
		podFrontEnd.setName("frontend");
		podFrontEnd.setNamespace("aNamespace");
		podFrontEnd.addLabel("name", "frontend");
		podFrontEnd.addLabel("env", "production");

		this.podBackEnd = factory.create(VERSION, ResourceKind.POD);
		podBackEnd.setName("backend");
		podBackEnd.setNamespace("aNamespace");
		podBackEnd.addLabel("name", "backend");
		podBackEnd.addLabel("env", "production");

		Pod otherPod = factory.create(VERSION, ResourceKind.POD);
		otherPod.setName("other");
		otherPod.setNamespace("aNamespace");
		otherPod.addLabel("env", "production");

		this.response = new ModelNode();
		response.get("apiVersion").set(VERSION);
		response.get("kind").set("PodList");
		ModelNode items = response.get("items");
		items.add(podFrontEnd.getNode());
		items.add(otherPod.getNode());
		items.add(podBackEnd.getNode());
	}

	
	private DefaultClient givenClient(URL baseUrl, String token, String user) {
		DefaultClient client = new DefaultClient(baseUrl, null, null, null, new AuthorizationContext(token,user,null));
		return client;
	}
	
	@SuppressWarnings("serial")
	@Test
	public void testListResourceFilteringWithExactMatch() throws Exception {
		Map<String, String> labels = new HashMap<String, String>(){{
			put("name","backend");
			put("env","production");
		}};
		List<IPod> pods = client.list(ResourceKind.POD, "aNamespace", labels);
		assertEquals("Expected 1 pod to be returned", 1, pods.size());
		assertEquals("Expected the frontend pod", podBackEnd, pods.get(0));
	}

	@Test
	public void testListResourceFilteringNoMatch() throws Exception {
		Map<String, String> labels = new HashMap<String, String>();
		labels.put("foo", "bar");
		List<IPod> pods = client.list(ResourceKind.POD, "aNamespace", labels);
		assertEquals("Expected no pod to be returned", 0, pods.size());
	}

	@SuppressWarnings("serial")
	@Test
	public void testListResourceFilteringWithPartialMatch() throws Exception {
		Map<String, String> labels = new HashMap<String, String>(){{
			put("name","frontend");
		}};
		List<IPod> pods = client.list(ResourceKind.POD, "aNamespace", labels);
		assertEquals("Expected 1 pod to be returned", 1, pods.size());
		assertEquals("Expected the backend pod", podFrontEnd, pods.get(0));
	}

	@SuppressWarnings("serial")
	@Test
	public void testListResourceFilteringSingleLabel() throws Exception {
		Map<String, String> labels = new HashMap<String, String>(){{
			put("env","production");
		}};
		List<IPod> pods = client.list(ResourceKind.POD, "aNamespace", labels);
		assertEquals("Expected all pods to be returned", 3, pods.size());
	}

	@Test
	public void clientShouldEqualClientWithSameUrl() throws Exception {
		assertThat(givenClient(baseUrl,null,null))
			.isEqualTo(givenClient(baseUrl,null,null));
	}
		
	@Test
	public void clientShouldNotEqualClientWithDifferentUrl() throws Exception {
		assertThat(givenClient(baseUrl,null,null))
				.isNotEqualTo(givenClient(new URL("http://localhost:8443"),null,null));
	}
	
	public void client_should_equal_client_with_same_user_with_different_token() throws Exception {
		DefaultClient tokenClientOne = givenClient(baseUrl, "tokenOne", "aUser");

		DefaultClient tokenClientTwo = givenClient(baseUrl,"tokenTwo", "aUser");

		assertThat(tokenClientOne).isEqualTo(tokenClientTwo);
	}


	@Test
	public void client_should_not_equal_client_with_different_username() throws Exception {
		DefaultClient tokenClientOne = givenClient(baseUrl,"aToken", "aUser");

		DefaultClient tokenClientTwo = givenClient(baseUrl, "aToken", "differentUser");

		assertThat(tokenClientOne).isNotEqualTo(tokenClientTwo);
	}

}
