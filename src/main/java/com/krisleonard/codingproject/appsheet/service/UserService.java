package com.krisleonard.codingproject.appsheet.service;

import com.krisleonard.codingproject.appsheet.model.User;
import com.krisleonard.codingproject.appsheet.model.UserIdList;

import java.util.List;

public interface UserService {
    /** AppSheet REST API URL base */
    String APP_SHEET_REST_API_URL_BASE = "https://appsheettest1.azurewebsites.net/sample";

    /** The URL for user id list REST API */
    String USER_ID_LIST_URL = APP_SHEET_REST_API_URL_BASE + "/list?token={token}";

    /** The URL for the user detail REST API */
    String USER_DETAIL_URL = APP_SHEET_REST_API_URL_BASE + "/detail/{userId}";

    /**
     * Return the user id list for the input paging token
     *
     * @param pagingToken The paging token. The empty string is an acceptable value.
     * @return The user id list for the input paging token
     */
     UserIdList getUserIdList(String pagingToken);

    /**
     * Return a list of all user ids. This will page through all of the results of calling
     * the user id list REST API.
     *
     * @return A list of all user ids
     */
    List<Integer> getAllUserIds();

    /**
     * Return the User object for the input user id
     *
     * @param userId The user id of the User to find
     * @return The User object for the input user id
     */
    User getUser(int userId);

    /**
     * Returns a list of the youngest maxNumberUsers who have a valid telephone.
     * This method uses streams and will have problems scaling once the number of
     * users is very significant.
     *
     * @param maxNumberUsers The maximum number of users to return in a list
     * @return A list of the youngest maxNumberUsers who have a valid telephone
     */
    List<User> getYoungestUsersWithValidPhonePretty(final int maxNumberUsers);

    /**
     * Returns a list of the youngest maxNumberUsers who have a valid telephone.
     * This method limits the growth of the result list to the input maxNumberUsers
     * and shuffles the result list should a younger user be found. The goal is to
     * prevent usage of a large result list as the number of users grows, shrink the amount
     * of time it takes to sort the list, and shrink the amount of time to
     * replace/add younger user objects in the results.
     *
     * @param maxNumberUsers The maximum number of users to return in a list
     * @return A list of the youngest maxNumberUsers who have a valid telephone
     */
    List<User> getYoungestUsersWithValidPhoneNotSoPretty(final int maxNumberUsers);
}
