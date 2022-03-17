package com.byblos.evaluation.evaluationservice.controllers;

import static jdk.nashorn.internal.runtime.JSType.isString;
import static org.hibernate.query.criteria.internal.ValueHandlerFactory.isNumeric;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.byblos.evaluation.evaluationservice.dtos.CandidateDto;
import com.byblos.evaluation.evaluationservice.dtos.CreateTestDto;
import com.byblos.evaluation.evaluationservice.dtos.IntervalDoneDto;
import com.byblos.evaluation.evaluationservice.dtos.IntervalDurationDto;
import com.byblos.evaluation.evaluationservice.dtos.IntervalStatDto;
import com.byblos.evaluation.evaluationservice.dtos.MoyenTechnologyDto;
import com.byblos.evaluation.evaluationservice.dtos.ResultTestQuestionDto;
import com.byblos.evaluation.evaluationservice.dtos.ScoresDto;
import com.byblos.evaluation.evaluationservice.dtos.SendTestDto;
import com.byblos.evaluation.evaluationservice.dtos.TestDto;
import com.byblos.evaluation.evaluationservice.dtos.TopCandidateDto;
import com.byblos.evaluation.evaluationservice.dtos.UserDto;
import com.byblos.evaluation.evaluationservice.mappers.TestMapper;
import com.byblos.evaluation.evaluationservice.models.Test;
import com.byblos.evaluation.evaluationservice.models.TestQuestion;
import com.byblos.evaluation.evaluationservice.services.TestService;

@RestController
@RequestMapping("/test")
public class TestController {

	@Autowired
	private TestService testService;
	@Autowired
	private TestMapper testMapper;

	private static final String CD = "Content-Disposition";

	@PostMapping("/createTest")
	public ResponseEntity<TestDto> create(@RequestBody TestDto testDto) {

		Test test = testService.save(testDto);

		testDto = testMapper.toTestDto(test);
		return ResponseEntity.status(HttpStatus.CREATED).body(testDto);
	}

	@GetMapping("/all")
	public ResponseEntity<List<TestDto>> findAll() {
		return ResponseEntity.ok(testService.findAll());
	}

	@PostMapping("/create")
	public ResponseEntity<List<TestDto>> create(@RequestBody CreateTestDto createTestDto) {
		return ResponseEntity.ok(testService.createTests(createTestDto.getCandidateList(),
				createTestDto.getQuestionList(), createTestDto.getTest()));
	}

	@GetMapping("/getScoresByCategory/{testid}")
	public ResponseEntity<List<ScoresDto>> getScores(@PathVariable(name = "testid") Long testid) {
		return ResponseEntity.ok(testService.getScores(testid));
	}

	@PostMapping("/save")
	public ResponseEntity<List<TestDto>> save(@RequestBody CreateTestDto createTestDto) {
		return ResponseEntity.ok(testService.saveTest(createTestDto.getCandidateList(), createTestDto.getQuestionList(),
				createTestDto.getTest()));
	}

	@PostMapping("/send")
	public ResponseEntity<Boolean> send(@RequestBody SendTestDto sendTestDto) {
		return ResponseEntity.ok(testService.sendTest(sendTestDto.getCandidateDto(), sendTestDto.getTest()));
	}

	@PostMapping("/resend")
	public ResponseEntity<Boolean> resend(@RequestBody SendTestDto sendTestDto) {
		return ResponseEntity.ok(testService.rappelerTest(sendTestDto.getCandidateDto(), sendTestDto.getTest()));
	}

	@PostMapping("/find/{testId}")
	public ResponseEntity<List<TestQuestion>> findByTest(@PathVariable(name = "testId") Long id) {
		return ResponseEntity.ok(testService.findByTestId(id));
	}

	@GetMapping("/findtest/{id}")
	public ResponseEntity<Test> findById(@PathVariable(name = "id") Long id) {
		Optional<Test> test = testService.findById(id);
		if (test.isPresent()) {
			return ResponseEntity.ok(test.get());
		} else {
			return ResponseEntity.ok(null);
		}
	}

	@GetMapping("/findQuestion/{id}")
	public ResponseEntity<TestQuestion> findByQuestionId(@PathVariable(name = "id") Long id) {
		return ResponseEntity.ok(testService.findQuestionResponseById(id));
	}

	@PostMapping(value = "/import-excel")
	public ResponseEntity<List<CandidateDto>> importExcelFile(@RequestParam("file") MultipartFile files)
			throws IOException {
		List<CandidateDto> candidateList = new ArrayList<>();

		XSSFWorkbook workbook = new XSSFWorkbook(files.getInputStream());
		XSSFSheet worksheet = workbook.getSheetAt(0);
		System.out.println(worksheet.getPhysicalNumberOfRows());
		for (int index = 0; index < worksheet.getPhysicalNumberOfRows(); index++) {
			System.out.println(index);
			if (index > 0) {
				CandidateDto candidate = new CandidateDto();

				XSSFRow row = worksheet.getRow(index);
				if (!isNumeric(row.getCell(0)) || !isString(row.getCell(1)) || !isString(row.getCell(3))
						|| !isString(row.getCell(4))) {
					return new ResponseEntity(HttpStatus.NOT_FOUND);
				}
				Integer id = (int) row.getCell(0).getNumericCellValue();

				candidate.setId(Long.valueOf(id));
				candidate.setName((row.getCell(1).getStringCellValue()));
				DataFormatter formatter = new DataFormatter();
				Cell cell = worksheet.getRow(index).getCell(2);
				String phone = formatter.formatCellValue(cell);
				candidate.setPhoneNumber(phone);
				String email = row.getCell(3).getStringCellValue();

				UserDto user = new UserDto();
				user.setEmail(email);
				user.setPassword(phone);

				candidate.setDiploma(row.getCell(4).getStringCellValue());
				candidate.setUser(user);
				System.out.println(candidate);
				candidateList.add(candidate);

			}
		}
		return ResponseEntity.ok(candidateList);

	}

	@GetMapping("/findOne")
	public ResponseEntity<Optional<Test>> findOne(@RequestParam(name = "idTest") Long id) {
		return ResponseEntity.ok(testService.findById(id));
	}

	@GetMapping("/find")
	public ResponseEntity<List<TestDto>> findDone(@RequestParam(name = "pack") Long pack) {
		return ResponseEntity.ok(testService.findDoneTests(pack));
	}

	@GetMapping(value = "/done/excel")
	public ResponseEntity<InputStreamResource> excelDoneTestReport(@RequestParam(name = "pack") Long pack)
			throws IOException {

		ByteArrayInputStream in = testService.doneTestToExcel(pack);
		HttpHeaders headers = new HttpHeaders();
		headers.add(TestController.CD, "attachment; filename=Done-Test-" + pack + ".xlsx");
		return ResponseEntity.ok().headers(headers).body(new InputStreamResource(in));
	}

	@GetMapping(value = "/tests/excel")
	public ResponseEntity<InputStreamResource> excelTestReport(@RequestParam(name = "idPack") Long idPack)
			throws IOException {

		ByteArrayInputStream in = testService.testToExcel(idPack);
		HttpHeaders headers = new HttpHeaders();
		headers.add(TestController.CD, "attachment; filename=Tests-" + idPack + ".xlsx");
		return ResponseEntity.ok().headers(headers).body(new InputStreamResource(in));
	}

	@GetMapping(value = "/topcandidate")
	public ResponseEntity<List<TopCandidateDto>> getTop(@RequestParam(name = "idPack") Long idPack,
			@RequestParam(name = "number") Long number) throws CloneNotSupportedException {
		return ResponseEntity.ok(testService.getTopCandidates(idPack, number));
	}

	@GetMapping(value = "/tests/pdf")
	public ResponseEntity<InputStreamResource> testReport(@RequestParam(name = "idPack") Long idPack) {

		ByteArrayInputStream in = testService.testPDF(idPack);
		HttpHeaders headers = new HttpHeaders();
		headers.add(TestController.CD, "inline; filename=Tests-" + idPack + ".pdf");

		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
				.body(new InputStreamResource(in));

	}

	@GetMapping(value = "/PercentageDone")
	public ResponseEntity<List<IntervalDoneDto>> getPercentageDone(@RequestParam(name = "idPack") Long idPack) {
		return ResponseEntity.ok(testService.percentageDone(idPack));
	}

	@GetMapping(value = "/done/pdf")
	public ResponseEntity<InputStreamResource> pdfDoneTestReport(@RequestParam(name = "pack") Long pack) {

		ByteArrayInputStream in = testService.doneTestPDF(pack);
		HttpHeaders headers = new HttpHeaders();
		headers.add(TestController.CD, "inline; filename=Done-Test-" + pack + ".pdf");

		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
				.body(new InputStreamResource(in));

	}

	@GetMapping(value = "/DurationInterval")

	public ResponseEntity<List<IntervalDurationDto>> getIntervalDuration(@RequestParam(name = "idPack") Long idPack) {
		return ResponseEntity.ok(testService.getDurationInterval(idPack));

	}

	@GetMapping("/statinterval")
	public ResponseEntity<List<IntervalStatDto>> getIntervalStat(@RequestParam(name = "idPackage") Long idPack) {
		return ResponseEntity.ok(testService.getIntervalStat(idPack));

	}

	@GetMapping("/rangCandidate")
	public ResponseEntity<List<TopCandidateDto>> getRang(@RequestParam(name = "idPack") Long idPack) {
		return ResponseEntity.ok(testService.getRang(idPack));

	}

	@GetMapping("/moyentechnology")
	public ResponseEntity<List<MoyenTechnologyDto>> getMoyenTechnology(@RequestParam(name = "idPackage") Long idPack) {
		return ResponseEntity.ok(testService.getAvgScoreParTechnologie(idPack));

	}

	@GetMapping("/allTests/{idPack}")
	public ResponseEntity<List<TestDto>> getAllTests(@PathVariable(name = "idPack") Long idPack) {
		return ResponseEntity.ok(testService.getAllTests(idPack));

	}

	@GetMapping("/getSendTests/{idPack}")
	public ResponseEntity<Integer> getSendNumber(@PathVariable(name = "idPack") Long idPack) {
		return ResponseEntity.ok(testService.getSendNumber(idPack));

	}

	@GetMapping("/testresult/{id}")
	public ResponseEntity<List<ResultTestQuestionDto>> findTestResult(@PathVariable(name = "id") Long test) {
		return ResponseEntity.ok(testService.getResultDetail(test));
	}

}
