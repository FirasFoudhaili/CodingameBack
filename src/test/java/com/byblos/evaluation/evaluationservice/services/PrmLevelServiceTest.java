package com.byblos.evaluation.evaluationservice.services;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.byblos.evaluation.evaluationservice.EvaluationServiceApplication;
import com.byblos.evaluation.evaluationservice.dtos.PrmLevelDto;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = EvaluationServiceApplication.class)
@TestPropertySource(locations = "classpath:application.properties")
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:scripts/prm_level.sql")
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:scripts/delete_prm_level.sql")
public class PrmLevelServiceTest {

	@Autowired
	private PrmLevelService prmLevelService;

	@Test
	public void testFindLevelById() {

		PrmLevelDto prmLevldto = prmLevelService.findById(1L);
		assertEquals("Junior", prmLevldto.getLevelName());
		assertEquals(Long.valueOf(1), prmLevldto.getId());
	}
}
