About :
-------
This sample web app require a JWT Token to authenticate and once user authenticated, it calls to an API
published in API Cloud which needs an oauth token.


Prerequisites :
---------------
1. Sample MicroService "destination-manager" is provided to create the backend required for API.

2. You can use already hosted service of this (http://wso2org-destinationservice-1-0-0.wso2apps.com/destination/route), or build this from source and deploy it in Integration Cloud
(https://integration.cloud.wso2.com/appmgt)

3. Expose this "DestinationService" as an API in API Cloud (https://api.cloud.wso2.com/publisher).
You can use provided swagger.json API definition to create required API.

4. Login to the API Store and subcribed to DestinationAPI published previously and get a consumer/secret key pair.

5. Update the web.xml of this web app (plan-your-trip) with consumer/secret key details and other service endpoint
details as relevant.


Build the Web App and Configure SSO :
-------------------------------------

1. Navigate to the source/plan-your-trip folder.

2. Open a console and type "mvn clean install"

3. Once, built the inside the target folder you will find the .war file, for the web application. Drop this war inside
the webapps directory of your servlet container OR deploy it in Integration Cloud.

4. To check whether Web App has been deployed properly, enter the web app URL. You should be presented the login screen
 of the plan your trip webapp.

5. Login to the Identity Cloud https://identity.cloud.wso2.com/admin and follow steps given in blog post - 
http://dinushasblog.blogspot.com/2017/05/configure-single-sign-on-sso-for-web.html to
configure SSO for App and use JWT Bear Grant to get access token to consume API.


Try out Web App :
----------------

1. Navigate to the user-portal, by entering the URL : https://identity.cloud.wso2.com/user-porta/t/{YOUR-TENANT-DOMAIN}
 in your browser and log in.

2. Click on the plan-your-trip application listed there. You will be redirected to your application without giving
you the login page. Also check the console log of backend web app. This should have printed JWT Token received from
Identity Cloud and Access Token taken from API Cloud by providing the JWT Token.

