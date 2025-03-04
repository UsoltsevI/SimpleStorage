package org.example.sstorage.controllers;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;

/**
 * Controller that handles with exceptions.
 *
 * @author UsoltsevI
 */
@Controller
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
}
