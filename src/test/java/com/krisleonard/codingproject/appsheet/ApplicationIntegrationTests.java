package com.krisleonard.codingproject.appsheet;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.krisleonard.codingproject.appsheet.model.User;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static com.krisleonard.codingproject.appsheet.AppConstants.DEFAULT_MAX_NUMBER_OF_USERS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Application integration tests.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ApplicationIntegrationTests {

	/** The Mock MVC */
	@Autowired
	private MockMvc mockMvc;

	/** The Object Mapper for parsing JSON */
	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void testGetYoungestUsersIT() throws Exception {
		// Make request to /youngestUser
		MvcResult mvcResult = mockMvc.perform(get("/youngestUsers"))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();

		// Convert result to list of User objects
		List<User> userList = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
				new TypeReference<List<User>>(){});

		// Validate that DEFAULT_MAX_NUMBER_OF_USERS is the size of the result User list
		assertEquals(DEFAULT_MAX_NUMBER_OF_USERS, userList.size());
	}

}
