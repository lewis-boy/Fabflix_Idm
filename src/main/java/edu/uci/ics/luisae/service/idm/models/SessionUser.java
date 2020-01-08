package edu.uci.ics.luisae.service.idm.models;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SessionUser {
    @JsonProperty(value = "session_id")
    private String session_id;
    @JsonProperty(value = "email")
    private String email;
    @JsonProperty(value = "status")
    private int status;
    @JsonProperty(value = "time_created")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "PST")
    private Timestamp time_created;
    @JsonProperty(value = "last_used")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "PST")
    private Timestamp last_used;
    @JsonProperty(value = "expr_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "PST")
    private Timestamp expr_time;

    public SessionUser(){}

    public Timestamp getExpr_time() {
        return expr_time;
    }

    public void setExpr_time(Timestamp expr_time) {
        this.expr_time = expr_time;
    }

    public Timestamp getLast_used() {
        return last_used;
    }

    public void setLast_used(Timestamp last_used) {
        this.last_used = last_used;
    }

    public Timestamp getTime_created() {
        return time_created;
    }

    public void setTime_created(Timestamp time_created) {
        this.time_created = time_created;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }
}
