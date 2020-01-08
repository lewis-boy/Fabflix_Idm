package edu.uci.ics.luisae.service.idm.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.uci.ics.luisae.service.idm.logger.ServiceLogger;

public class user {
    @JsonProperty(value = "user_id", required = true)
    private int user_id;
    @JsonProperty(value = "email", required = true)
    private String email;
    @JsonProperty(value = "status", required = true)
    private int status;
    @JsonProperty(value = "plevel", required = true)
    private int plevel;
    @JsonProperty(value = "salt", required = true)
    private String salt;
    @JsonProperty(value = "pword", required = true)
    private String pword;

    public user(){}

    @JsonIgnore
    public void print(){
        ServiceLogger.LOGGER.info("user_id: " + user_id);
        ServiceLogger.LOGGER.info("email: " + email);
        ServiceLogger.LOGGER.info("status: " + status);
        ServiceLogger.LOGGER.info("plevel: " + plevel);
        ServiceLogger.LOGGER.info("salt: " + salt);
        ServiceLogger.LOGGER.info("pword: " + pword);
    }

    @JsonProperty(value = "pword", required = true)
    public String getPword() {
        return pword;
    }

    public void setPword(String pword) {
        this.pword = pword;
    }

    @JsonProperty(value = "salt", required = true)
    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    @JsonProperty(value = "plevel", required = true)
    public int getPlevel() {
        return plevel;
    }

    public void setPlevel(int plevel) {
        this.plevel = plevel;
    }

    @JsonProperty(value = "status", required = true)
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @JsonProperty(value = "email", required = true)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @JsonProperty(value = "user_id", required = true)
    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
