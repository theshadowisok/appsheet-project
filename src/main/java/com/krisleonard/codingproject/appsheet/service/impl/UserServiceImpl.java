package com.krisleonard.codingproject.appsheet.service.impl;

import com.krisleonard.codingproject.appsheet.model.User;
import com.krisleonard.codingproject.appsheet.model.UserIdList;
import com.krisleonard.codingproject.appsheet.service.UserService;
import com.krisleonard.codingproject.appsheet.util.PhoneUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.lang.invoke.MethodHandles;
import java.util.*;
import java.util.stream.Collectors;

/**
 * The user service class for invoking App Sheet REST API endpoints related to user id lists and user details.
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    /** The logger */
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    /** The Rest Template */
    @Autowired
    private RestTemplate restTemplate;

    /**
     * {@inheritDoc}
     */
    @Override
    public UserIdList getUserIdList(final String pagingToken) {
        return restTemplate.getForObject(USER_ID_LIST_URL, UserIdList.class,
            pagingToken == null ? "" : pagingToken);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Integer> getAllUserIds() {
        List<Integer> allUserIds = new ArrayList<>();
        String pagingToken = null;

        do {
            UserIdList userIdList = getUserIdList(pagingToken);

            if(userIdList == null || userIdList.getResult() == null) {
                break;
            }

            allUserIds.addAll(userIdList.getResult());
            pagingToken = userIdList.getToken();
        } while (pagingToken != null);

        return allUserIds;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User getUser(final int userId) {
        try {
            return restTemplate.getForObject(USER_DETAIL_URL, User.class, userId);
        }
        catch(RestClientException ex) {
            if(LOG.isDebugEnabled()) {
                // Hiding this from the log because it is not extremely
                // useful for this code project
                LOG.debug("Error getting user for user id: " + userId, ex);
            }
            return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<User> getYoungestUsersWithValidPhonePretty(final int maxNumberUsers) {
        List<User> users = new ArrayList<>();
        // Get all of the user ids
        List<Integer> allUserIds = getAllUserIds();
        // For each user id get the user details and then add the user to the results list if they have a valid phone
        // number
        allUserIds.stream().forEach(id -> {
            User user = getUser(id);
            if(user != null && PhoneUtil.validUSAPhoneNumber(user.getNumber())) {
                    users.add(user);
            }
        });

        // Return a list that is the maxNumberUsers youngest users sorted alphabetically by name
        return users.stream().sorted(Comparator.comparing(User::getAge))
            .limit(maxNumberUsers)
            .sorted(Comparator.comparing(User::getName))
            .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<User> getYoungestUsersWithValidPhoneNotSoPretty(final int maxNumberUsers) {
        // result youngest users array
        final User[] youngestUsers = new User[maxNumberUsers];
        // current position in the youngestUsers array that has been populated
        int lastPositionPopulated = 0;

        // Initial pagingToken
        String pagingToken = null;
        // Loop until there are no more pages of user id lists
        do {
            // Get user id list data
            UserIdList userIdList = getUserIdList(pagingToken);

            // Break if any results that have no user id list
            if(userIdList == null || userIdList.getResult() == null || userIdList.getResult().isEmpty()) {
                if(LOG.isDebugEnabled()) {
                    LOG.debug("No user id list found");
                }
                break;
            }

            // Loop over the list of user ids returned from getUserIdList
            for(Integer userId : Collections.unmodifiableList(userIdList.getResult())) {
                // Get the user details
                User user = getUser(userId);

                // Skip users that were not found or have invalid phone numbers
                if(user == null || !PhoneUtil.validUSAPhoneNumber(user.getNumber())) {
                    continue;
                }

                if(lastPositionPopulated < maxNumberUsers) {
                    // Populate the elements of the users array up to maxNumberUsers
                    youngestUsers[lastPositionPopulated] = user;
                    lastPositionPopulated++;

                    // Sort the users array once we reach the maxNumberUsers value
                    if(lastPositionPopulated == maxNumberUsers) {
                        Arrays.sort(youngestUsers, Comparator.comparing(User::getAge));
                    }
                }
                else {
                    // Update users array if the current user has a younger or same age as the current elements

                    int position  = Arrays.binarySearch(youngestUsers, user, Comparator.comparing(User::getAge));
                    if(Math.abs(position) > maxNumberUsers) {
                        continue;
                    }

                    if(position >= 0) {
                        // Find the last index of the age in the array if it already exists in the users array
                        int lastIndexOf = position;
                        for(int j = maxNumberUsers - 1; j > position; j--) {
                            if(youngestUsers[j].getAge().intValue() == user.getAge().intValue()) {
                                lastIndexOf = j;
                                break;
                            }
                        }

                        // The position for a user with an age that is already in the array
                        position = lastIndexOf + 1;
                    }
                    else {
                        // The position in the array for new younger user
                        position = Math.abs(position) - 1;
                    }

                    // Re-arrange the users array so that the new element is in the correct position and all others
                    // are moved to the right
                    shuffleUsersArray(youngestUsers, user, position);
                }
            }

            // Update the paging token to latest results from getting user id list data
            pagingToken = userIdList.getToken();
        } while(pagingToken != null);

        return Arrays.asList(youngestUsers)
                .stream()
                .sorted(Comparator.comparing(User::getName))
                .collect(Collectors.toList());
    }

    /**
     * Swap users in the users array starting at the starting position and moving right.
     *
     * @param users Users array to swap users in
     * @param newUser New user to add at the starting position
     * @param startingPosition Starting position where swapping needs to occur
     */
    private void shuffleUsersArray(final User[] users, final User newUser, final int startingPosition) {
        User swapUser = newUser;
        User previousUser = null;
        // Swap/replace items in the users array
        for(int k = startingPosition; k < users.length; k++) {
            previousUser = users[k];
            users[k] = swapUser;
            swapUser = previousUser;
        }
    }
}
