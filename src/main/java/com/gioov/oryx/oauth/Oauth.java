package com.gioov.oryx.oauth;


/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2019-01-18
 */
public class Oauth {

    public class Page {
        public static final String OAUTH = "/oauth";
        public static final String CLIENT =  Page.OAUTH + "/client";
        public static final String ACCESS_TOKEN =  Page.OAUTH + "/access_token";
    }

    public class Api {
        public static final String OAUTH = com.gioov.oryx.common.Url.API + Page.OAUTH;
        public static final String CLIENT = Api.OAUTH + "/client";
        public static final String ACCESS_TOKEN = Api.OAUTH + "/access_token";
    }

}
