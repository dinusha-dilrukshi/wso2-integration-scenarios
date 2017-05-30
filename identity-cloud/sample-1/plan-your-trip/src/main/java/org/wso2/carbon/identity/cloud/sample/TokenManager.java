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
import sun.misc.BASE64Encoder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class TokenManager {

    public String getToken(String jwtToken){

        String tokenEndpoint = PlanYourTripWebConfiguration.getInstance().getTokenURL();
        String consumerKey = PlanYourTripWebConfiguration.getInstance().getConsumerKey();
        String consumerSecret = PlanYourTripWebConfiguration.getInstance().getConsumerSecret();

        HTTPClient httpClient = new HTTPClient();
        try {
            String applicationToken = consumerKey + ":" + consumerSecret;
            BASE64Encoder base64Encoder = new BASE64Encoder();
            applicationToken = "Basic " + base64Encoder.encode(applicationToken.getBytes()).trim();

            String payload = "grant_type=urn:ietf:params:oauth:grant-type:jwt-bearer&assertion=" + jwtToken;
            HttpResponse httpResponse = httpClient.doPost(tokenEndpoint, applicationToken, payload,
                    "application/x-www-form-urlencoded");
            if (httpResponse.getStatusLine().getStatusCode() != 200) {
                return null;
            }
            String response = httpClient.getResponsePayload(httpResponse);
            JSONParser jsonParser = new JSONParser();
            Object obj = jsonParser.parse(response);
            JSONObject jsonObject = (JSONObject)obj;
            return jsonObject.get("access_token").toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
