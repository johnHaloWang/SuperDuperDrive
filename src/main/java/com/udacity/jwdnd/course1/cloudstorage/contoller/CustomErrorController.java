package com.udacity.jwdnd.course1.cloudstorage.contoller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

    private static final String PATH = "/error";
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    public final static String TAG_ = "CustomErrorController";

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        // display generic error
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        String pageTitle = "Error";
        String errorPage = "error";
        String errorMsg = "";
        if(status != null){
            Integer statusCode = Integer.valueOf(status.toString());
            if(statusCode == HttpStatus.NOT_FOUND.value()){
                pageTitle = "Page Not Found";
                errorMsg = "404 Error --- Page Can't be Found";
                errorPage = "error";
                LOGGER.error("Error 404");
            }else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                pageTitle = "Internal Server Error";
                errorMsg = "500 Error --- Internal Server Error";
                errorPage = "error";
                LOGGER.error("Error 500");
            } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
                pageTitle = "Forbidden";
                errorMsg = "406 Error --- Page is Forbidden";
                errorPage = "error";
                LOGGER.error("Error 406");
            }else{
                pageTitle = "Error";
                errorMsg = String.valueOf(statusCode) + " Error";
                errorPage = "error";
                LOGGER.error("Error other");
            }
        }
        model.addAttribute("pageTitle", pageTitle);
        model.addAttribute("errorMsg", errorMsg);

        return errorPage;
    }

    @Override
    public String getErrorPath() {
        LOGGER.debug("call... getErrorPath function");
        return "/error";
    }
}
