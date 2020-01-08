package edu.uci.ics.luisae.service.idm.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.uci.ics.luisae.service.idm.Base.ResponseModel;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginAndSessionResponse extends ResponseModel {
    @JsonProperty(value="session_id")
    private String session_id;
    public LoginAndSessionResponse(){}
    @JsonCreator public LoginAndSessionResponse(@JsonProperty(value="session_id")String session_id){
        //test that we dont need this super();
        this.session_id = session_id;
    }

    @JsonProperty(value = "session_id")
    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }
}
