package com.beanspot.backend.oauth;

import java.util.Map;

public abstract class OauthUserInfo {
    protected Map<String, Object> attributes;

    public OauthUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }
    public abstract String getUsername();
    public abstract String getId();
    public abstract String getName();
    public abstract String getEmail();
    public abstract String getImageUrl();
}
