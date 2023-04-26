package finalproject.application.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomErrorController implements ErrorController {
    @GetMapping(value = "/error")
    public String HandleError(HttpServletRequest request, Model model){
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        String errorMsg = null;
        if(status != null){
            Integer statusCode = Integer.valueOf(status.toString());
            if(statusCode == HttpStatus.NOT_FOUND.value()){
                errorMsg = "404 - Page not found";
            }else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()){
                errorMsg = "500 - Internal Server Error";
            }else if(statusCode == HttpStatus.FORBIDDEN.value()){
                errorMsg = "403 - Forbidden";
            }else if(statusCode == HttpStatus.BAD_REQUEST.value()){
                errorMsg = "400 - Bad Request";
            }
            else if(statusCode == HttpStatus.METHOD_NOT_ALLOWED.value()){
                errorMsg = "405 - Method Not Allowed";
            }
            else if(statusCode == HttpStatus.UNAUTHORIZED.value()){
                errorMsg = "401 - Unauthorized";
            }
            else{
                errorMsg = "Something went wrong";
            }
        }
        model.addAttribute("errorMsg",errorMsg);
        model.addAttribute("errorCode",status);
        return "error";
    }

}
