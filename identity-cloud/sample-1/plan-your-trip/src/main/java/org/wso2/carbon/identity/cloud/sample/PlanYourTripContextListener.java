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

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class PlanYourTripContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        String serverURL = servletContextEvent.getServletContext().getInitParameter("serverURL");
        PlanYourTripWebConfiguration.getInstance().setServerURL(serverURL);

        String tokenURL = servletContextEvent.getServletContext().getInitParameter("tokenURL");
        PlanYourTripWebConfiguration.getInstance().setTokenURL(tokenURL);

        String consumerKey = servletContextEvent.getServletContext().getInitParameter("consumerKey");
        PlanYourTripWebConfiguration.getInstance().setConsumerKey(consumerKey);

        String consumerSecret = servletContextEvent.getServletContext().getInitParameter("consumerSecret");
        PlanYourTripWebConfiguration.getInstance().setConsumerSecret(consumerSecret);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }
}
