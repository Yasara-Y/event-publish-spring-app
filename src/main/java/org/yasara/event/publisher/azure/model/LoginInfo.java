package org.yasara.event.publisher.azure.model;

public class LoginInfo {

    String userId;
    String eventId;
    String eventType;
    String userstoreDomain;
    String tenantDomain;
    String loginTimestamp;
    String publishTimestamp;

    @Override
    public String toString() {

        return "\"publishTimestamp\":" + publishTimestamp + "\"loginTimestamp\":" + loginTimestamp +
                ",\"userstoreDomain\":" + userstoreDomain + ",\"tenantDomain\":" + tenantDomain
                + ",\"eventType\":" + eventType + ",\"userId\":" + userId + ",\"eventId\":" + eventId ;
    }

    public LoginInfo() {

    }


    public String getPublishTimestamp() {
        return publishTimestamp;
    }

    public void setPublishTimestamp(String publishTimestamp) {
        this.publishTimestamp = publishTimestamp;
    }

    public String getLoginTimestamp() {
        return loginTimestamp;
    }

    public void setLoginTimestamp(String loginTimestamp) {
        this.loginTimestamp = loginTimestamp;
    }

    public String getuserId() {

        return userId;
    }

    public void setuserId(String userId) {

        this.userId = userId;
    }

    public String getEventId() {

        return eventId;
    }

    public void setEventId(String eventId) {

        this.eventId = eventId;
    }

    public String getEventType() {

        return eventType;
    }

    public void setEventType(String eventType) {

        this.eventType = eventType;
    }

    public String getUserstoreDomain() {

        return userstoreDomain;
    }

    public void setUserstoreDomain(String userstoreDomain) {

        this.userstoreDomain = userstoreDomain;
    }

    public String getTenantDomain() {

        return tenantDomain;
    }

    public void setTenantDomain(String tenantDomain) {

        this.tenantDomain = tenantDomain;
    }
}
