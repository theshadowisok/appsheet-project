package com.krisleonard.codingproject.appsheet;

import com.krisleonard.codingproject.appsheet.model.User;
import com.krisleonard.codingproject.appsheet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.krisleonard.codingproject.appsheet.AppConstants.DEFAULT_MAX_NUMBER_OF_USERS;

/**
 * The command line runner
 */
@Component
public class AppSheetCodProjectRunner implements CommandLineRunner {

    /** The user service */
    @Autowired
    UserService userService;

    /**
     * {@inheritDoc}
     */
    @Override
    public void run(String... args) throws Exception {
        List<User> users = userService.getYoungestUsersWithValidPhoneNotSoPretty(DEFAULT_MAX_NUMBER_OF_USERS);
        users.stream().forEach(user -> System.out.println(user));
    }
}
