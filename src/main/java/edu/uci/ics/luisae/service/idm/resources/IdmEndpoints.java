package edu.uci.ics.luisae.service.idm.resources;

import edu.uci.ics.luisae.service.idm.core.LogicHandler;
import edu.uci.ics.luisae.service.idm.logger.ServiceLogger;
import edu.uci.ics.luisae.service.idm.models.*;
import edu.uci.ics.luisae.service.idm.utilities.Util;
import edu.uci.ics.luisae.service.idm.core.BadRequest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("idm")
public class IdmEndpoints {

    @Path("register")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response register(String jsonText){
       ServiceLogger.LOGGER.info("Entering Register Endpoint");
        //*****************first map data to request model*********
        //*****************then check request for plausible correctness**********\
        //*****************pass off to data handler **********
        RegisterAndPrivilegeResponse response = new RegisterAndPrivilegeResponse();
        RegisterAndLoginRequest request = Util.modelMapper(jsonText, RegisterAndLoginRequest.class, response);

        if(request == null)
            return response.buildResponse();

        int validateData = BadRequest.register(request,response);
        if(validateData == 1)
            return response.buildResponse();

        ServiceLogger.LOGGER.info("Exiting Register Endpoint Normally");
        return LogicHandler.RegisterHandler(request,response);

    }

    @Path("login")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(String jsonText){
        ServiceLogger.LOGGER.info("Entering Login Endpoint");
        //*****************first map data to request model*********
        //*****************then check request for plausible correctness**********\
        //*****************pass off to data handler **********
        LoginAndSessionResponse response = new LoginAndSessionResponse();
        RegisterAndLoginRequest request = Util.modelMapper(jsonText,RegisterAndLoginRequest.class,response);
        if(request == null)
            return response.buildResponse();

        int validateData = BadRequest.login(request,response);
        if(validateData == 1)
            return response.buildResponse();

        ServiceLogger.LOGGER.info("Exiting Login Endpoint Normally");
        return LogicHandler.LoginHandler(request, response);
    }

    @Path("session")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response session(String jsonText){
        ServiceLogger.LOGGER.info("Entering Session Endpoint");
        LoginAndSessionResponse response = new LoginAndSessionResponse();
        SessionRequest request = Util.modelMapper(jsonText,SessionRequest.class,response);
        if(request == null)
            return response.buildResponse();
        int validateData  = BadRequest.session(request,response);
        if(validateData == 1)
            return response.buildResponse();

        ServiceLogger.LOGGER.info("Exiting Session Endpoint Normally");
        return LogicHandler.SessionHandler(request,response);
    }

    @Path("privilege")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response privilege(String jsonText){
        ServiceLogger.LOGGER.info("Entering Privilege Endpoint");
        RegisterAndPrivilegeResponse response = new RegisterAndPrivilegeResponse();
        PrivilegeRequest request = Util.modelMapper(jsonText, PrivilegeRequest.class, response);
        if(request == null)
            return response.buildResponse();
        int validateData = BadRequest.privilege(request,response);
        if(validateData == 1 )
            return response.buildResponse();

        ServiceLogger.LOGGER.info("Exiting Privilege Endpoint Normally");
        return LogicHandler.PrivilegeHandler(request, response);
    }


}
