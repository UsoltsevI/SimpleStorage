package org.example.sstorage.controllers;

import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;

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
    public String databaseError() {
        // Nothing to do.  Returns the logical view name of an error page, passed
        // to the view-resolver(s) in usual way.
        // Note that the exception is NOT available to this view (it is not added
        // to the model) but see "Extending ExceptionHandlerExceptionResolver"
        // below.
        return "databaseError";
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
