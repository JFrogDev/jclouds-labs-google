/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jclouds.googlecomputeengine.features;

import static org.jclouds.googlecomputeengine.GoogleComputeEngineConstants.COMPUTE_READONLY_SCOPE;
import static org.jclouds.googlecomputeengine.GoogleComputeEngineConstants.COMPUTE_SCOPE;
import static org.jclouds.googlecomputeengine.GoogleComputeEngineFallbacks.EmptyListPageOnNotFoundOr404;

import java.util.Iterator;

import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jclouds.Fallbacks.NullOnNotFoundOr404;
import org.jclouds.googlecomputeengine.GoogleComputeEngineFallbacks.EmptyIteratorOnNotFoundOr404;
import org.jclouds.googlecomputeengine.binders.HttpHealthCheckCreationBinder;
import org.jclouds.googlecomputeengine.domain.HttpHealthCheck;
import org.jclouds.googlecomputeengine.domain.ListPage;
import org.jclouds.googlecomputeengine.domain.Operation;
import org.jclouds.googlecomputeengine.functions.internal.ParseHttpHealthChecks;
import org.jclouds.googlecomputeengine.options.HttpHealthCheckCreationOptions;
import org.jclouds.googlecomputeengine.options.ListOptions;
import org.jclouds.javax.annotation.Nullable;
import org.jclouds.oauth.v2.config.OAuthScopes;
import org.jclouds.oauth.v2.filters.OAuthAuthenticator;
import org.jclouds.rest.annotations.Fallback;
import org.jclouds.rest.annotations.MapBinder;
import org.jclouds.rest.annotations.PATCH;
import org.jclouds.rest.annotations.PayloadParam;
import org.jclouds.rest.annotations.RequestFilters;
import org.jclouds.rest.annotations.ResponseParser;
import org.jclouds.rest.annotations.SkipEncoding;
import org.jclouds.rest.annotations.Transform;
import org.jclouds.rest.binders.BindToJsonPayload;

/**
 * Provides access to HttpHealthChecks via their REST API.
 */
@SkipEncoding({'/', '='})
@RequestFilters(OAuthAuthenticator.class)
@Consumes(MediaType.APPLICATION_JSON)
public interface HttpHealthCheckApi {

   /**
    * Returns the specified HttpHealthCheck resource.
    *
    * @param httpHealthCheck the name of the HttpHealthCheck resource to return.
    * @return a HttpHealthCheck resource.
    */
   @Named("HttpHealthChecks:get")
   @GET
   @Path("/{httpHealthCheck}")
   @OAuthScopes(COMPUTE_READONLY_SCOPE)
   @Fallback(NullOnNotFoundOr404.class)
   @Nullable
   HttpHealthCheck get(@PathParam("httpHealthCheck") String httpHealthCheck);

   /**
    * Creates a HttpHealthCheck resource in the specified project and region using the data included in the request.
    *
    * @param httpHealthCheckName the name of the forwarding rule.
    * @return an Operation resource. To check on the status of an operation, poll the Operations resource returned to
    *         you, and look for the status field.
    */
   @Named("HttpHealthChecks:insert")
   @POST
   @Produces(MediaType.APPLICATION_JSON)
   @OAuthScopes(COMPUTE_SCOPE)
   @MapBinder(BindToJsonPayload.class)
   Operation insert(@PayloadParam("name") String httpHealthCheckName);

   /**
    * Creates a HttpHealthCheck resource in the specified project and region using the data included in the request.
    *
    * @param name the name of the forwarding rule.
    * @return an Operation resource. To check on the status of an operation, poll the Operations resource returned to
    *         you, and look for the status field.
    */
   @Named("HttpHealthChecks:insert")
   @POST
   @Produces(MediaType.APPLICATION_JSON)
   @OAuthScopes(COMPUTE_SCOPE)
   @MapBinder(HttpHealthCheckCreationBinder.class)
   Operation insert(@PayloadParam("name") String name, @PayloadParam("options") HttpHealthCheckCreationOptions options);

   /**
    * Deletes the specified TargetPool resource.
    *
    * @param httpHealthCheck name of the persistent forwarding rule resource to delete.
    * @return an Operation resource. To check on the status of an operation, poll the Operations resource returned to
    *         you, and look for the status field.
    */
   @Named("HttpHealthChecks:delete")
   @DELETE
   @Path("/{httpHealthCheck}")
   @OAuthScopes(COMPUTE_SCOPE)
   @Fallback(NullOnNotFoundOr404.class)
   @Nullable
   Operation delete(@PathParam("httpHealthCheck") String httpHealthCheck);

   /**
    * @return an Iterator that is able to fetch additional pages when required
    * @see org.jclouds.collect.PagedIterable
    */
   @Named("HttpHealthChecks:list")
   @GET
   @OAuthScopes(COMPUTE_READONLY_SCOPE)
   @ResponseParser(ParseHttpHealthChecks.class)
   @Transform(ParseHttpHealthChecks.ToIteratorOfListPage.class)
   @Fallback(EmptyIteratorOnNotFoundOr404.class)
   Iterator<ListPage<HttpHealthCheck>> list();

   /**
    * @param options @see org.jclouds.googlecomputeengine.options.ListOptions
    * @return ListPage
    */
   @Named("HttpHealthChecks:list")
   @GET
   @OAuthScopes(COMPUTE_READONLY_SCOPE)
   @ResponseParser(ParseHttpHealthChecks.class)
   @Fallback(EmptyListPageOnNotFoundOr404.class)
   ListPage<HttpHealthCheck> list(ListOptions options);

   /**
    * Updates a HttpHealthCheck resource in the specified project
    * using the data included in the request. This method supports patch semantics.
    *
    * @param name the name of the HttpHealthCheck resource to update.
    * @param options the options to set for the healthCheck
    * @return an Operation resource. To check on the status of an operation, poll the Operations resource returned to
    *         you, and look for the status field.
    */
   @Named("HttpHealthChecks:patch")
   @PATCH
   @Path("/{httpHealthCheck}")
   @OAuthScopes(COMPUTE_SCOPE)
   @MapBinder(HttpHealthCheckCreationBinder.class)
   @Nullable
   Operation patch(@PathParam("httpHealthCheck") @PayloadParam("name") String name,
         @PayloadParam("options") HttpHealthCheckCreationOptions options);

   /**
    * Updates a HttpHealthCheck resource in the specified project using the data included in the request.
    * Any options left blank will be overwritten!
    * 
    * @param name the name of the forwarding rule.
    * @param options the options to set for the healthCheck
    * @return an Operation resource. To check on the status of an operation, poll the Operations resource returned to
    *         you, and look for the status field.
    */
   @Named("HttpHealthChecks:update")
   @PUT
   @Path("/{httpHealthCheck}")
   @Produces(MediaType.APPLICATION_JSON)
   @OAuthScopes(COMPUTE_SCOPE)
   @MapBinder(HttpHealthCheckCreationBinder.class)
   Operation update(@PathParam("httpHealthCheck") @PayloadParam("name") String name,
                    @PayloadParam("options") HttpHealthCheckCreationOptions options);

}