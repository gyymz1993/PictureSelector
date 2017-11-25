package com.three.login;

/**
 * Created by onetouch on 2017/11/21.
 */

public class ConfigAppKey {

    private String wxId="wx5022d3c8ad81e74f";

    private String wxSecret="db846ab731f65c89fe77c49ad3af5612";

    public static ConfigAppKey instance() {
        return new ConfigAppKey();
    }

    public ConfigAppKey wxId(String id) {
        wxId = id;
        return this;
    }

    public ConfigAppKey wxSecret(String id) {
        wxSecret = id;
        return this;
    }


    public String getWxId() {
        return wxId;
    }

    public String getWxSecret() {
        return wxSecret;
    }


}
