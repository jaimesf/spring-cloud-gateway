/*
 * Copyright 2013-2023 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.cloud.gateway.server.mvc.filter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.gateway.server.mvc.test.HttpbinTestcontainers;
import org.springframework.cloud.gateway.server.mvc.test.HttpbinUriResolver;
import org.springframework.cloud.gateway.server.mvc.test.client.TestRestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import java.util.function.Supplier;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.cloud.gateway.server.mvc.filter.AfterFilterFunctions.addResponseHeader;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;
import static org.springframework.cloud.gateway.server.mvc.predicate.GatewayRequestPredicates.host;
import static org.springframework.cloud.gateway.server.mvc.predicate.GatewayRequestPredicates.weight;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ContextConfiguration(initializers = HttpbinTestcontainers.class)
@ActiveProfiles("tokenrelaypropertiesintegrationtests")
public class TokenRelayFilterPropertiesIntegrationTests {

	@Autowired
	TestRestClient testClient;

	@MockBean
	private TokenRelayFilterFunctions filter;


	@Test
	public void highWeight() {

		when(filter.tokenRelay("relay"));

		testClient.get()
			.uri("/get")
			.exchange()
			.expectStatus()
			.isOk();
	}



}
