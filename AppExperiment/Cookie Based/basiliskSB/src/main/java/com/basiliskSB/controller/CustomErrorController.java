package com.basiliskSB.controller;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handle(HttpServletRequest request){
        var status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            var statusCode = Integer.valueOf(status.toString());

            switch (statusCode){
                case 404:
                    return "error/404";
                case 500:
                    return "error/500";
                default:
                    return "error/undocumented";
            }
        }
        return "error/undocumented";
    }

}
