/**
 * Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 * <p>
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.wso2.carbon.identity.cloud.sample;

import org.apache.http.HttpResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class DestinationManager {

    private HTTPClient httpClient;
    private String serverURL;
    private final String DESTINATION_URL = "/1.0.0/route";

    public DestinationManager() {
        httpClient = new HTTPClient();
        serverURL = PlanYourTripWebConfiguration.getInstance().getServerURL();
    }

    public int getRouteNumber(String token) {
        int routeNo = 1;
        HttpResponse httpResponse = null;
        try {
            httpResponse = httpClient.doGet(serverURL + DESTINATION_URL, "Bearer " + token);
            String response = httpClient.getResponsePayload(httpResponse);
            System.out.printf("ACCESS TOKEN #############" + response);

            JSONParser jsonParser = new JSONParser();
            JSONObject routeResponse = (JSONObject)jsonParser.parse(response);
            if (routeResponse != null) {
                routeNo = Integer.parseInt((String)routeResponse.get("routeNo"));
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return routeNo;
    }

//    private ArrayList<String> getAvailableDestinationListHelper(String destinationsInJson) throws ParseException {
//        JSONParser parser = new JSONParser();
//        Object obj = parser.parse(destinationsInJson);
//
//        JSONObject jsonObject = (JSONObject) obj;
//        String destination = (String) jsonObject.get("destination");
//    }


}
