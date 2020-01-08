package edu.uci.ics.luisae.service.idm.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.uci.ics.luisae.service.idm.Base.RequestModel;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegisterAndLoginRequest extends RequestModel {
    @JsonProperty(value = "email", required = true)
    private String email;
    @JsonProperty(value = "password",required = true)
    private char[] password;

    public RegisterAndLoginRequest(){}
    @JsonCreator
    public RegisterAndLoginRequest(@JsonProperty(value = "email", required = true) String email,
                                   @JsonProperty(value = "password", required = true) char[] password){
        this.email = email;
        this.password = password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @JsonProperty(value = "password", required = true)
    public char[] getPassword() {
        return password;
    }

    @JsonProperty(value = "email", required = true)
    public String getEmail() {
        return email;
    }
}
