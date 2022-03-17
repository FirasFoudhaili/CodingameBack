package com.byblos.evaluation.evaluationservice.controllers;

import java.nio.charset.Charset;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.byblos.evaluation.evaluationservice.dtos.PrmLevelDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(JUnit4.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application.properties")
@ActiveProfiles("test")
@WebMvcTest(value = PrmLevelController.class)
public class PrmLevelControllerTest {
	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	@Autowired
	private MockMvc mockMvc;

	@InjectMocks
	private PrmLevelController prmLevelController;;

	@Test
	public void testCreate() throws Exception {
		PrmLevelDto prmLevelDto = new PrmLevelDto();
		prmLevelDto.setLevelName("junior");

		mockMvc.perform(MockMvcRequestBuilders.post("/level/create").contentType(MediaType.APPLICATION_JSON)
				.content(mapToJson(prmLevelDto)).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void testFindAll() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/level/all")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void testDelete() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/level/delete/1"))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void findById() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/level/find/1")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(prmLevelController).build();
	}

	@Test
	public void testUpdate() throws Exception {
		PrmLevelDto prmLevelDto = new PrmLevelDto();
		prmLevelDto.setLevelName("senior");

		mockMvc.perform(MockMvcRequestBuilders.put("/level/update/1").contentType(MediaType.APPLICATION_JSON)
				.content(mapToJson(prmLevelDto)).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	private String mapToJson(Object object) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}
}