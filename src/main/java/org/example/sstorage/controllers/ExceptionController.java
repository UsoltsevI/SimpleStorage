package org.example.sstorage.controllers;

import org.springframework.dao.DataAccessException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;

/**
 * Controller that handles with exceptions.
 *
 * @author UsoltsevI
 */
@ControllerAdvice
public class ExceptionController {

    /**
     * Specify name of a specific view that will be used to display the error
     */
    @ExceptionHandler({SQLException.class, DataAccessException.class})
    public String databaseError(Model model, Exception exception) {
        // Примечание: они видны только админу
        model.addAttribute("error", exception.getMessage());

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        exception.printStackTrace(pw);

        model.addAttribute("stackTrace", sw.toString());

        return "error";
    }

    /**
     * Handle with MaxUploadSizeExceededException.
     *
     * @param exc exception
     * @return error model
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ModelAndView handleMaxSizeException(MaxUploadSizeExceededException exc) {

        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.getModel().put("error", "File size exceeds the limit!");

        return modelAndView;
    }
}
