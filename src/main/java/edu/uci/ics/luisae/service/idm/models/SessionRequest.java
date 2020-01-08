package edu.uci.ics.luisae.service.idm.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.uci.ics.luisae.service.idm.Base.RequestModel;

public class SessionRequest extends RequestModel {
    @JsonProperty(value = "email", required = true)
    private String email;
    @JsonProperty(value = "session_id", required = true)
    private String session_id;
    public SessionRequest(){}
    @JsonCreator public SessionRequest(@JsonProperty(value="email", required = true)String email,
                                       @JsonProperty(value = "session_id", required = true)String session_id){
        this.email = email;
        this.session_id = session_id;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
