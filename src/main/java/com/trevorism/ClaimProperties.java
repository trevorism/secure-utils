package com.trevorism;

import com.trevorism.secure.Secure;

import java.util.HashSet;
import java.util.Set;

public class ClaimProperties {

    private String id;
    private String subject;
    private String issuer;
    private Set<String> audience = new HashSet<>();
    private String role;
    private String permissions;
    private String type;
    private String tenant;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public Set<String>  getAudience() {
        return audience;
    }

    public void setAudience(Set<String>  audience) {
        this.audience = audience;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }
}
