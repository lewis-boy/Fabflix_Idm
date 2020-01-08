package edu.uci.ics.luisae.service.idm.core;

import edu.uci.ics.luisae.service.idm.Base.ResponseModel;
import edu.uci.ics.luisae.service.idm.Base.Result;
import edu.uci.ics.luisae.service.idm.logger.ServiceLogger;
import edu.uci.ics.luisae.service.idm.models.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BadRequest {
    private static boolean pInvalidRange(int plevel){
        return (plevel < 1 || plevel > 5);
    }

    private static boolean tInvalidLength(String session_id){
        if(session_id == null)
            return true;
        return (session_id.length() < 64 || session_id.length() > 128);
    }

    private static boolean pInvalidLength(char[] password){
        if (password == null)
            return true;
        return (password.length <= 0 || password.length > 128);
    }

    private static boolean eInvalidFormat(String email){
        ServiceLogger.LOGGER.info(email);
        String regex = "^[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+$";
        Pattern r = Pattern.compile(regex);
        Matcher m = r.matcher(email);
        return !m.find();
    }

    private static boolean eInvalidLength(String email){
        if(email == null)
            return true;
        return(email.isEmpty() || email.length()<=0 || email.length() > 50);
    }

    public static int register(RegisterAndLoginRequest request, ResponseModel response){
        if(pInvalidLength(request.getPassword())){
            response.setResult(Result.PASSWORD_INVALID_LENGTH);
            return 1;
        }
        else if(eInvalidLength(request.getEmail())){
            response.setResult(Result.EMAIL_INVALID_LENGTH);
            return 1;
        }
        else if(eInvalidFormat(request.getEmail())){
            response.setResult(Result.EMAIL_INVALID_FORMAT);
            return 1;
        }
        else{
            return 0;
        }
    }

    public static int login(RegisterAndLoginRequest request, LoginAndSessionResponse response){
        return register(request, response);
    }

    public static int session(SessionRequest request, LoginAndSessionResponse response){
        if(tInvalidLength(request.getSession_id())){
            response.setResult(Result.TOKEN_INVALID_LENGTH);
            return 1;
        }
        else if(eInvalidFormat(request.getEmail())){
            response.setResult(Result.EMAIL_INVALID_FORMAT);
            return 1;
        }
        else if(eInvalidLength(request.getEmail())){
            response.setResult(Result.EMAIL_INVALID_LENGTH);
            return 1;
        }
        else
            return 0;
    }

    public static int privilege(PrivilegeRequest request, RegisterAndPrivilegeResponse response){
        if(pInvalidRange(request.getPlevel())){
            response.setResult(Result.PLEVEL_OUT_OF_RANGE);
            return 1;
        }
        else if(eInvalidFormat(request.getEmail())){
            ServiceLogger.LOGGER.warning("INVALID FORNMAT");
            response.setResult(Result.EMAIL_INVALID_FORMAT);
            return 1;
        }
        else if(eInvalidLength(request.getEmail())){
            response.setResult(Result.EMAIL_INVALID_LENGTH);
            return 1;
        }
        else
            return 0;
    }

    //TODO Finished bad requests portion of register endpoint, finish up endpoint and move on to OK portion.

}
