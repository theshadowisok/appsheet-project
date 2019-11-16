package com.krisleonard.codingproject.appsheet.service;

import com.krisleonard.codingproject.appsheet.model.User;
import com.krisleonard.codingproject.appsheet.model.UserIdList;
import com.krisleonard.codingproject.appsheet.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.krisleonard.codingproject.appsheet.AppConstants.DEFAULT_MAX_NUMBER_OF_USERS;
import static com.krisleonard.codingproject.appsheet.service.impl.UserServiceImpl.USER_DETAIL_URL;
import static com.krisleonard.codingproject.appsheet.service.impl.UserServiceImpl.USER_ID_LIST_URL;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * The User Service test to validate the UserService
 */
@RunWith( MockitoJUnitRunner.class )
@RestClientTest
public class UserServiceTest {

    /** Testing user id list paging token */
    private final static String TEST_TOKEN = "testToken";

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private UserService userService = new UserServiceImpl();

    @Test
    public void testGetUserIdList() {
        UserIdList mockUserIdList = new UserIdList();
        mockUserIdList.setToken(TEST_TOKEN);
        mockUserIdList.setResult(Collections.singletonList(6));

        Mockito.when(restTemplate.getForObject(USER_ID_LIST_URL, UserIdList.class, ""))
            .thenReturn(mockUserIdList);

        UserIdList userIdListData = userService.getUserIdList("");
        assertTrue("User List data not found", userIdListData != null);
        assertEquals(mockUserIdList, userIdListData);
    }

    @Test
    public void testGetAllUserIds() {
        UserIdList mockUserIdList1 = new UserIdList();
        mockUserIdList1.setToken(TEST_TOKEN);
        mockUserIdList1.setResult(new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5)));

        UserIdList mockUserIdList2 = new UserIdList();
        mockUserIdList2.setToken(null);
        mockUserIdList2.setResult(new ArrayList<>(Arrays.asList(6,7,8,9,10)));

        Mockito.when(restTemplate.getForObject(USER_ID_LIST_URL, UserIdList.class, ""))
            .thenReturn(mockUserIdList1);

        Mockito.when(restTemplate.getForObject(USER_ID_LIST_URL, UserIdList.class, TEST_TOKEN))
            .thenReturn(mockUserIdList2);

        List<Integer> userIdList = userService.getAllUserIds();
        assertTrue("userIdList is empty", userIdList != null && !userIdList.isEmpty());
        assertEquals(mockUserIdList1.getResult().size() + mockUserIdList2.getResult().size(),
            userIdList.size());
    }

    @Test
    public void testGetUser() {
        int userId = 14;
        User mockUser = new User();
        mockUser.setAge(5);
        mockUser.setBio("Bio");
        mockUser.setId(userId);
        mockUser.setLastName("Last");
        mockUser.setName("First");
        mockUser.setNumber("555-555-5555");
        mockUser.setPhoto("http://myphoto.com");

        Mockito.when(restTemplate.getForObject(USER_DETAIL_URL, User.class, userId))
            .thenReturn(mockUser);

        User user = userService.getUser(userId);
        assertEquals(mockUser, user);
    }

    @Test
    public void testGetYoungestUsersWithValidPhoneHappyPath() {
        UserIdList mockUserIdList1 = new UserIdList();
        mockUserIdList1.setToken(TEST_TOKEN);
        mockUserIdList1.setResult(new ArrayList<>(Arrays.asList(4,13,6,7,1,15,2,8)));

        Mockito.when(restTemplate.getForObject(USER_ID_LIST_URL, UserIdList.class, ""))
            .thenReturn(mockUserIdList1);

        for(int userId : mockUserIdList1.getResult() ) {
            Mockito.when(restTemplate.getForObject(USER_DETAIL_URL, User.class, userId))
                .thenReturn(createUser(userId));
        }

        // Double creation of user objects here is duplicate work
        List<User> sortedUsersList = new ArrayList<>();
        sortedUsersList.add(createUser(1));
        sortedUsersList.add(createUser(2));
        sortedUsersList.add(createUser(4));
        sortedUsersList.add(createUser(6));
        sortedUsersList.add(createUser(7));

        // Use "pretty" method that uses streams to get youngest users data
        List<User> youngestUsersWithValidPhone =
            userService.getYoungestUsersWithValidPhonePretty(DEFAULT_MAX_NUMBER_OF_USERS);
        assertEquals(DEFAULT_MAX_NUMBER_OF_USERS, youngestUsersWithValidPhone.size());
        assertEquals(sortedUsersList, youngestUsersWithValidPhone);

        // Use "not so pretty" method that uses streams to get youngest users data
        List<User> youngestUsersWithValidPhoneV2 =
            userService.getYoungestUsersWithValidPhoneNotSoPretty(DEFAULT_MAX_NUMBER_OF_USERS);
        assertEquals(DEFAULT_MAX_NUMBER_OF_USERS, youngestUsersWithValidPhoneV2.size());
        assertEquals(sortedUsersList, youngestUsersWithValidPhoneV2);
    }

    /**
     * Create a user
     *
     * @param userId The user id
     * @return A new user object
     */
    private User createUser(int userId) {
        User mockUser = new User();
        mockUser.setAge(userId);
        mockUser.setBio("Bio");
        mockUser.setId(userId);
        mockUser.setLastName("Last");
        mockUser.setName("First" + userId);
        if(userId % 2 == 0) {
            mockUser.setNumber("555 555-5555");
        }
        else {
            mockUser.setNumber("555-555-5555");
        }
        mockUser.setPhoto("http://myphoto.com");

        return mockUser;
    }
}
