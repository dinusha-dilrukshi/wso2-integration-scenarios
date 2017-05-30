/*
 * Copyright (c) 2016, WSO2 Inc. (http://wso2.com) All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.wso2.msf4j.identity.cloud.sample;

import com.google.gson.JsonObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Random;


/**
 * Destination service resource class.
 */
@Path("/destination")
public class DestinationService {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/route")
    public Response route() {

        Random r = new Random();
        int low = 1;
        int high = 5;
        int routeNo = r.nextInt(high-low) + low;
        JsonObject returnObject = new JsonObject();
        returnObject.addProperty("routeNo", routeNo);
        return Response.status(Response.Status.OK).entity(returnObject.toString()).build();
    }
}
