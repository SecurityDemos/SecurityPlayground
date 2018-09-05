package org.acme.security.playground.playground;

public class LoginData {
    private long id;
    private String displayName;

    public LoginData() {

    }

    public LoginData(long id, String displayName) {
        this.id = id;
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
