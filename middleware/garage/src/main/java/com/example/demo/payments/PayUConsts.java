package com.example.demo.payments;

public class PayUConsts {
    private static final String CLIENT_ID = "388454";
    private static final String CLIENT_SECRET = "fb7a5c10b3423ec4fbca8e177609b66b";
    static final String CONTINUE_URL = "https://poland.payu.com/";
    static final String POS_ID = "388454";
    static final String CURRENCY = "PLN";
    static final String TOKEN_URL = "https://secure.snd.payu.com/pl/standard/user/oauth/authorize?grant_type=client_credentials&" + "client_id="
            + PayUConsts.CLIENT_ID + "&client_secret=" + PayUConsts.CLIENT_SECRET;
}
