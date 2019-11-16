package com.krisleonard.codingproject.appsheet.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.krisleonard.codingproject.appsheet.model.User;
import com.krisleonard.codingproject.appsheet.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.invoke.MethodHandles;
import java.util.List;

import static com.krisleonard.codingproject.appsheet.AppConstants.DEFAULT_MAX_NUMBER_OF_USERS;

/**
 * REST Controller that is used to return user information from the App Sheet REST API's
 * (ie it's wrapping some REST API's with another REST API)
 */
@RestController
public class UserInfoRestController {

    /** The logger */
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    /** The App Sheet User Service */
    @Autowired
    private UserService userService;

    /**
     * Get the default max number of youngest users with valid phone numbers.
     *
     * @return The youngest users with valid phone numbers in a JSON string
     * @throws JsonProcessingException When there is a problem turn the list of users in to a JSON string
     */
    @RequestMapping("/youngestUsers")
    public String getYoungestUsers() throws JsonProcessingException {
        final long startTime = System.currentTimeMillis();
        List<User> users = userService.getYoungestUsersWithValidPhonePretty(DEFAULT_MAX_NUMBER_OF_USERS);
        final long totalTime = System.currentTimeMillis() - startTime;
        System.out.println("Total time: " + totalTime + "ms");

        // Log the user list
        users.stream().forEach(user -> LOG.info(user.toString()));

        return new ObjectMapper().writeValueAsString(users);
    }
}
