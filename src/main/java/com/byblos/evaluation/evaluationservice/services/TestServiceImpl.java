package com.byblos.evaluation.evaluationservice.services;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.byblos.evaluation.evaluationservice.dtos.CandidateDto;
import com.byblos.evaluation.evaluationservice.dtos.IntervalDoneDto;
import com.byblos.evaluation.evaluationservice.dtos.IntervalDurationDto;
import com.byblos.evaluation.evaluationservice.dtos.IntervalStatDto;
import com.byblos.evaluation.evaluationservice.dtos.MoyenTechnologyDto;
import com.byblos.evaluation.evaluationservice.dtos.QuestionDto;
import com.byblos.evaluation.evaluationservice.dtos.ResultResponseDto;
import com.byblos.evaluation.evaluationservice.dtos.ResultTestQuestionDto;
import com.byblos.evaluation.evaluationservice.dtos.ScoreParCategoryDto;
import com.byblos.evaluation.evaluationservice.dtos.ScoreSorter;
import com.byblos.evaluation.evaluationservice.dtos.ScoresDto;
import com.byblos.evaluation.evaluationservice.dtos.TestDto;
import com.byblos.evaluation.evaluationservice.dtos.TopCandidateDto;
import com.byblos.evaluation.evaluationservice.mappers.CandidateMapper;
import com.byblos.evaluation.evaluationservice.mappers.PrmCategoryMapper;
import com.byblos.evaluation.evaluationservice.mappers.QuestionMapper;
import com.byblos.evaluation.evaluationservice.mappers.ScoreParCategoryMapper;
import com.byblos.evaluation.evaluationservice.mappers.TestMapper;
import com.byblos.evaluation.evaluationservice.models.Pack;
import com.byblos.evaluation.evaluationservice.models.PrmCategory;
import com.byblos.evaluation.evaluationservice.models.Response;
import com.byblos.evaluation.evaluationservice.models.Test;
import com.byblos.evaluation.evaluationservice.models.TestQuestion;
import com.byblos.evaluation.evaluationservice.models.TestQuestionKeys;
import com.byblos.evaluation.evaluationservice.repositories.PackRepo;
import com.byblos.evaluation.evaluationservice.repositories.PrmCategoryRepo;
import com.byblos.evaluation.evaluationservice.repositories.ResponseRepo;
import com.byblos.evaluation.evaluationservice.repositories.ResultRepo;
import com.byblos.evaluation.evaluationservice.repositories.ScoreParCategoryRepo;
import com.byblos.evaluation.evaluationservice.repositories.TestAccessRepo;
import com.byblos.evaluation.evaluationservice.repositories.TestQuestionRepo;
import com.byblos.evaluation.evaluationservice.repositories.TestRepo;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class TestServiceImpl implements TestService {

	@Autowired
	private TestRepo testRepo;
	@Autowired
	private TestQuestionRepo testQuestionRepo;
	@Autowired
	private TestMapper testMapper;
	@Autowired
	private CandidateMapper candidateMapper;
	@Autowired
	private QuestionMapper questionMapper;
	@Autowired
	private MailSending mailSending;
	@Autowired
	private PackRepo packRepo;
	@Autowired
	private ResponseRepo responseRepo;
	@Autowired
	private ResultRepo resultRepo;
	@Autowired
	private ScoreParCategoryRepo scoreParCategoryRepo;
	@Autowired
	private PrmCategoryRepo prmCategoryRepo;
	@Autowired
	PrmCategoryMapper prmCategoryMapper;
	@Autowired
	ScoreParCategoryMapper scoreParCategoryMapper;
	@Autowired
	TestAccessRepo testAccessRepo;

	@Override
	@Scheduled(cron = "0 * * * * *")
	public void updateExpiration() {
		testRepo.findBySendAndDone(true, false).forEach(test -> {
			if (new Date().after(test.getExpirationDate())) {
				test.setExpired(true);
				testRepo.save(test);
			}
		});
	}

	@Override
	public Optional<Test> findById(Long id) {
		return testRepo.findById(id);
	}

	@Override
	public List<TestQuestion> findByTestId(Long id) {
		Optional<Test> test = testRepo.findById(id);
		if (test.isPresent()) {
			return testQuestionRepo.findByTest(test.get());
		} else {
			return new ArrayList<>();
		}
	}

	@Override
	public TestQuestion findQuestionResponseById(Long id) {
		return null;
	}

	@Override
	public List<TestDto> findAll() {
		return testMapper.toTestDtos(testRepo.findAll());
	}

	public Boolean sendTest(CandidateDto candidateDto, TestDto testDto) {
		Optional<Test> test1 = testRepo.findById(testDto.getTestid());
		if (test1.isPresent()) {
			Test test = test1.get();

			test.setCandidate(candidateMapper.toCandidate(candidateDto));
			try {
				mailSending.send(candidateDto.getUser().getEmail(), "Passage de test",
						"Ceci est le lien de passage de test http://localhost:8080/#/candidat/welcome/"
								+ test.getTestid() + " this test will be exipred in 7 days");
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.DAY_OF_WEEK, 7);
				Date nextDate = cal.getTime();
				test.setExpirationDate(nextDate);
				test.setExpired(false);
				test.setSend(true);
				Test tr = testRepo.save(test);
				Long idCompany = tr.getPack().getCompany().getId();
				testAccessRepo.incrTestAccess(idCompany);
			} catch (MessagingException e) {

			}
			return true;
		}
		return false;
	}

	@Override
	public Boolean rappelerTest(CandidateDto candidateDto, TestDto test) {
		try {
			mailSending.send(candidateDto.getUser().getEmail(), "Rappel pour le test",
					"Ceci est le lien de passage de test http://localhost:8080/#/candidat/welcome/" + test.getTestid());
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DAY_OF_WEEK, 7);
		} catch (MessagingException e) {
		}
		return true;
	}

	@Override
	public List<TestDto> createTests(List<CandidateDto> candidateDtos, List<QuestionDto> questionDtos,
			TestDto testDto) {
		testDto.setCreationDate(new java.sql.Date(Calendar.getInstance().getTime().getTime()));
		List<TestDto> testDtoList = new ArrayList<>();
		candidateDtos.forEach(candidateDto -> {
			Test test = testMapper.toTest(testDto);
			test.setDone(false);
			test.setCandidate(candidateMapper.toCandidate(candidateDto));
			test.setExpirationDate(null);
			test.setSend(false);
			Test testresult = testRepo.save(test);
			try {
				mailSending.send(candidateDto.getUser().getEmail(), "Passage de test",
						"Ceci est le lien de passage de test http://localhost:8080/#/candidat/welcome/"
								+ testresult.getTestid() + " this test will be exipred in 7 days");
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.DAY_OF_WEEK, 7);
				Date nextDate = cal.getTime();
				testresult.setExpirationDate(nextDate);
				test.setExpired(false);
				testresult.setSend(true);
				Test tr = testRepo.save(testresult);
				Long idCompany = tr.getPack().getCompany().getId();
				testAccessRepo.incrTestAccess(idCompany);
			} catch (MessagingException e) {

			} finally {
				questionDtos.forEach(questionDto -> {
					TestQuestion testQuestion = new TestQuestion();
					testQuestion.setQuestion(questionMapper.toQuestion(questionDto));
					testQuestion.setTest(testresult);
					TestQuestionKeys testQuestionKeys = new TestQuestionKeys();
					testQuestionKeys.setQuestionid(questionDto.getQuestionid());
					testQuestionKeys.setTestid(testresult.getTestid());
					testQuestion.setId(testQuestionKeys);
					testQuestionRepo.save(testQuestion);
				});
				testDtoList.add(testMapper.toTestDto(testRepo.findById(testresult.getTestid()).get()));
			}
		});
		return testDtoList;
	}

	@Override
	public List<TestDto> saveTest(List<CandidateDto> candidateDtos, List<QuestionDto> questionDtos, TestDto testDto) {
		testDto.setCreationDate(new java.sql.Date(Calendar.getInstance().getTime().getTime()));
		List<TestDto> testDtoList = new ArrayList<>();
		candidateDtos.forEach(candidateDto -> {
			Test test = testMapper.toTest(testDto);
			test.setDone(false);

			test.setCandidate(candidateMapper.toCandidate(candidateDto));
			Test testresult = testRepo.save(test);
			testresult.setSend(false);
			questionDtos.forEach(questionDto -> {
				TestQuestion testQuestion = new TestQuestion();
				testQuestion.setQuestion(questionMapper.toQuestion(questionDto));
				testQuestion.setTest(testresult);
				TestQuestionKeys testQuestionKeys = new TestQuestionKeys();
				testQuestionKeys.setQuestionid(questionDto.getQuestionid());
				testQuestionKeys.setTestid(testresult.getTestid());
				testQuestion.setId(testQuestionKeys);
				testQuestionRepo.save(testQuestion);
			});
			testDtoList.add(testMapper.toTestDto(testRepo.findById(testresult.getTestid()).get()));
		});
		return testDtoList;
	}

	@Override
	public List<IntervalDoneDto> percentageDone(Long idPack) {
		List<IntervalDoneDto> liste = new ArrayList<>();
		int nbTests = this.getAllTests(idPack).size();
		int nbDone = this.findDoneTests(idPack).size();
		IntervalDoneDto intervalDurationDto = new IntervalDoneDto();
		intervalDurationDto.setName("Done");
		intervalDurationDto.setValue(((((double) nbDone) * 100) / nbTests));
		IntervalDoneDto intervalDoneDto2 = new IntervalDoneDto();
		intervalDoneDto2.setName("Not Done");
		intervalDoneDto2.setValue(100 - (((double) nbDone * 100) / nbTests));
		liste.add(intervalDurationDto);
		liste.add(intervalDoneDto2);
		return liste;
	}

	@Override
	public List<ScoresDto> getScores(Long testid) {
		List<PrmCategory> prmCategories = prmCategoryRepo.findAll();
		List<ScoresDto> listScoresDto = new ArrayList<>();
		prmCategories.forEach(prm -> {
			ScoresDto scoresDto = new ScoresDto();
			scoresDto.setCategoryName(prm.getCategoryName());
			if (scoreParCategoryRepo.findByTestAndPrmCategory(testRepo.findById(testid).get(), prm) != null) {
				scoresDto.setCategoryName(prm.getCategoryName());
				scoresDto.setScore(
						scoreParCategoryRepo.findByTestAndPrmCategory(testRepo.findById(testid).get(), prm).getScore());
				listScoresDto.add(scoresDto);
			}
		});
		return listScoresDto;
	}

	@Override
	public Test save(TestDto t) {
		t.setDone(false);
		return testRepo.save(testMapper.toTest(t));
	}

	@Override
	public List<TestDto> findDoneTests(Long pack) {
		Optional<Pack> pack1 = packRepo.findById(pack);
		if (pack1.isPresent()) {
			return testMapper.toTestDtos(testRepo.findByPackAndDone(pack1.get(), true));
		} else {
			return new ArrayList<>();
		}
	}

	@Override
	public List<TopCandidateDto> getTopCandidates(Long idPack, Long number) {

		List<TopCandidateDto> topliste = new ArrayList<>();
		List<TopCandidateDto> topfinaleliste = new ArrayList<>();
		List<TestDto> liste = this.getAllTests(idPack);
		for (int i = 0; i < liste.size(); i++) {
			boolean done = liste.get(i).getDone();
			if (done) {
				TopCandidateDto topCan = new TopCandidateDto();
				topCan.setCandidateName(liste.get(i).getCandidate().getName());
				topCan.setScore(liste.get(i).getTotalScore());
				topliste.add(topCan);

			}
		}
		topliste.sort(new ScoreSorter());
		for (int j = 0; j < number; j++) {
			topfinaleliste.add(topliste.get(j));
		}

		return topfinaleliste;

	}

	@Override
	public List<IntervalDurationDto> getDurationInterval(Long idPack) {
		List<IntervalDurationDto> liste = new ArrayList<>();
		Optional<Pack> pack1 = packRepo.findById(idPack);
		if (pack1.isPresent()) {
			int allDone = testRepo.findByPackAndDone(pack1.get(), true).size();
			List<Test> tests = testRepo.findByPackAndDone(pack1.get(), true);
			Long duration = tests.get(0).getTestDuration();
			double doubleNumber = (double) duration / 6;
			int pas = (int) doubleNumber;
			for (int i = 0; i < duration; i += pas) {
				int nb = testRepo.findByPackAndDoneAndPassageDurationBetween(pack1.get(), true, Long.valueOf(i),
						Long.valueOf(i + pas)).size();
				IntervalDurationDto intervalDurationDto = new IntervalDurationDto();
				int bi = (i / 60);
				int bs = ((i + pas) / 60);
				intervalDurationDto.setIntervalName(bi + "-" + bs);
				intervalDurationDto.setValue((double) ((((double) nb) * 100) / allDone));
				liste.add(intervalDurationDto);
			}
			return liste;
		} else {
			return new ArrayList<>();
		}

	}

	@Override
	public ByteArrayInputStream doneTestToExcel(Long pack) throws IOException {
		List<TestDto> tests = this.findDoneTests(pack);

		String[] cloumns = { "Id", "Name", "Email", "Total Score", "Passage Duration", "Test Duration", "Creation Date",
				"Connaissances", "Logique", "Intelligence" };
		try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
			CreationHelper createHelper = workbook.getCreationHelper();
			Sheet sheet = workbook.createSheet("Done Tests");
			sheet.setDefaultColumnWidth(18);
			CellStyle headerCellStyle = workbook.createCellStyle();
			XSSFFont font = (XSSFFont) workbook.createFont();
			font.setFontName("Arial");
			headerCellStyle.setFillForegroundColor(HSSFColor.BLUE.index);
			headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			font.setBold(true);
			font.setColor(HSSFColor.WHITE.index);
			headerCellStyle.setFont(font);
			// Row for Header
			Row headerRow = sheet.createRow(0);
			// Header
			for (int col = 0; col < cloumns.length; col++) {
				Cell cell = headerRow.createCell(col);
				cell.setCellValue(cloumns[col]);
				cell.setCellStyle(headerCellStyle);
			}
			// CellStyle for Age
			CellStyle ageCellStyle = workbook.createCellStyle();
			ageCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("#"));
			int rowIdx = 1;
			for (TestDto test : tests) {
				double score1;
				double score2;
				double score3;
				String score11;
				String score22;
				String score33;
				PrmCategory prmCategory = prmCategoryRepo.findByCategoryName("connaissances");
				ScoreParCategoryDto scoreParCategoryDto = scoreParCategoryMapper.toScoreParCategoryDto(
						scoreParCategoryRepo.findByTestAndPrmCategory(testMapper.toTest(test), prmCategory));
				if (scoreParCategoryDto == null) {
					score11 = "";
				} else {
					score1 = scoreParCategoryDto.getScore();
					score11 = Double.toString(score1);
				}
				PrmCategory prmCategory2 = prmCategoryRepo.findByCategoryName("logique");
				ScoreParCategoryDto scoreParCategoryDto2 = scoreParCategoryMapper.toScoreParCategoryDto(
						scoreParCategoryRepo.findByTestAndPrmCategory(testMapper.toTest(test), prmCategory2));

				if (scoreParCategoryDto2 == null) {

					score22 = "";
				} else {
					score2 = scoreParCategoryDto2.getScore();
					score22 = Double.toString(score2);
				}
				PrmCategory prmCategory3 = prmCategoryRepo.findByCategoryName("intelligence");
				ScoreParCategoryDto scoreParCategoryDto3 = scoreParCategoryMapper.toScoreParCategoryDto(
						scoreParCategoryRepo.findByTestAndPrmCategory(testMapper.toTest(test), prmCategory3));

				if (scoreParCategoryDto3 == null) {

					score33 = "";
				} else {
					score3 = scoreParCategoryDto3.getScore();
					score33 = Double.toString(score3);
				}
				Row row = sheet.createRow(rowIdx++);

				row.createCell(0).setCellValue(test.getTestid());
				row.createCell(1).setCellValue(test.getCandidate().getName());
				row.createCell(2).setCellValue(test.getCandidate().getUser().getEmail());
				row.createCell(3).setCellValue(test.getTotalScore());
				row.createCell(4).setCellValue(test.getPassageDuration());
				row.createCell(5).setCellValue(test.getTestDuration());
				row.createCell(6).setCellValue(test.getCreationDate().toString());
				row.createCell(7).setCellValue(score11);
				row.createCell(8).setCellValue(score22);
				row.createCell(9).setCellValue(score33);

			}

			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		}
	}

	@Override
	public ByteArrayInputStream testToExcel(Long idpack) throws IOException {
		List<TestDto> tests = this.getAllTests(idpack);
		String state;
		String expired;
		String[] cloumns = { "Id", "Candidate Name", "Candidate Email", "Phone", "Creation Date", "State", "Expired" };
		try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
			CreationHelper createHelper = workbook.getCreationHelper();
			Sheet sheet = workbook.createSheet(" Tests");
			CellStyle headerCellStyle = workbook.createCellStyle();
			// Row for Header
			Row headerRow = sheet.createRow(0);
			sheet.setDefaultColumnWidth(21);
			XSSFFont font = (XSSFFont) workbook.createFont();
			font.setFontName("Arial");
			headerCellStyle.setFillForegroundColor(HSSFColor.BLUE.index);
			headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			font.setBold(true);
			font.setColor(HSSFColor.WHITE.index);
			headerCellStyle.setFont(font);

			// Header
			for (int col = 0; col < cloumns.length; col++) {
				Cell cell = headerRow.createCell(col);
				cell.setCellValue(cloumns[col]);
				cell.setCellStyle(headerCellStyle);
			}

			// CellStyle for Age
			CellStyle ageCellStyle = workbook.createCellStyle();
			ageCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("#"));

			int rowIdx = 1;
			for (TestDto test : tests) {
				Row row = sheet.createRow(rowIdx++);
				boolean done = test.getDone();
				if (done) {
					state = "Done";
				} else
					state = "Not Done";

				Calendar cal = Calendar.getInstance();
				Date date = cal.getTime();
				if (test.getExpirationDate() == null) {
					expired = " ";
				} else if (test.getExpirationDate().compareTo(date) < 0) {
					expired = "Expired";

				} else
					expired = "Not Expired";

				row.createCell(0).setCellValue(test.getTestid());
				row.createCell(1).setCellValue(test.getCandidate().getName());
				row.createCell(2).setCellValue(test.getCandidate().getUser().getEmail());
				row.createCell(3).setCellValue(test.getCandidate().getPhoneNumber());
				row.createCell(4).setCellValue(test.getCreationDate().toString());

				row.createCell(5).setCellValue(state);
				row.createCell(6).setCellValue(expired);

			}

			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		}
	}

	@Override
	public ByteArrayInputStream doneTestPDF(Long pack) {
		List<TestDto> tests = this.findDoneTests(pack);
		Document document = new Document();
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		try {

			PdfPTable table = new PdfPTable(9);
			table.setWidthPercentage(100);
			table.setWidths(new int[] { 2, 6, 10, 7, 7, 9, 11, 9, 10 });

			Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

			PdfPCell hcell;
			hcell = new PdfPCell(new Phrase("Id", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Name", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Email", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);
			hcell = new PdfPCell(new Phrase("Total Score", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);
			hcell = new PdfPCell(new Phrase("Passage Duration", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Creation Date", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);
			hcell = new PdfPCell(new Phrase("connaissances", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);
			hcell = new PdfPCell(new Phrase("logique", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);
			hcell = new PdfPCell(new Phrase("intelligence", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);
			for (TestDto test : tests) {
				double score1;
				double score2;
				double score3;
				String score11;
				String score22;
				String score33;
				PrmCategory prmCategory = prmCategoryRepo.findByCategoryName("connaissances");
				ScoreParCategoryDto scoreParCategoryDto = scoreParCategoryMapper.toScoreParCategoryDto(
						scoreParCategoryRepo.findByTestAndPrmCategory(testMapper.toTest(test), prmCategory));
				if (scoreParCategoryDto == null) {
					score11 = "";
				} else {
					score1 = scoreParCategoryDto.getScore();
					score11 = Double.toString(score1);
				}
				PrmCategory prmCategory2 = prmCategoryRepo.findByCategoryName("logique");
				ScoreParCategoryDto scoreParCategoryDto2 = scoreParCategoryMapper.toScoreParCategoryDto(
						scoreParCategoryRepo.findByTestAndPrmCategory(testMapper.toTest(test), prmCategory2));

				if (scoreParCategoryDto2 == null) {

					score22 = "";
				} else {
					score2 = scoreParCategoryDto2.getScore();
					score22 = Double.toString(score2);
				}
				PrmCategory prmCategory3 = prmCategoryRepo.findByCategoryName("intelligence");
				ScoreParCategoryDto scoreParCategoryDto3 = scoreParCategoryMapper.toScoreParCategoryDto(
						scoreParCategoryRepo.findByTestAndPrmCategory(testMapper.toTest(test), prmCategory3));

				if (scoreParCategoryDto3 == null) {

					score33 = "";
				} else {
					score3 = scoreParCategoryDto3.getScore();
					score33 = Double.toString(score3);
				}

				PdfPCell cell;

				cell = new PdfPCell(new Phrase(test.getTestid().toString()));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(test.getCandidate().getName()));
				cell.setPaddingLeft(5);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(String.valueOf(test.getCandidate().getUser().getEmail())));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(5);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(String.valueOf(test.getTotalScore())));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(5);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(String.valueOf(test.getPassageDuration())));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(5);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(String.valueOf(test.getCreationDate().toString())));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(5);
				table.addCell(cell);
				cell = new PdfPCell(new Phrase(String.valueOf(score11)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(5);
				table.addCell(cell);
				cell = new PdfPCell(new Phrase(String.valueOf(score22)));

				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(5);
				table.addCell(cell);
				cell = new PdfPCell(new Phrase(String.valueOf(score33)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(5);
				table.addCell(cell);
			}
			PdfWriter.getInstance(document, out);
			document.open();
			document.add(table);
			document.close();
		} catch (DocumentException ex) {
		}
		return new ByteArrayInputStream(out.toByteArray());
	}

	@Override
	public List<IntervalStatDto> getIntervalStat(Long pack) {
		List<IntervalStatDto> intervalStatDtoList = new ArrayList<>();
		Optional<Pack> pack1 = packRepo.findById(pack);
		if (pack1.isPresent()) {
			int totalDone = testRepo.findByPackAndDone(pack1.get(), true).size();
			for (int i = 0; i < 100; i += 10) {
				int nbinterval = testRepo.findByPackAndDoneAndTotalScoreBetween(pack1.get(), true, Double.valueOf(i),
						Double.valueOf(i + 10)).size();
				IntervalStatDto intervalStatDto = new IntervalStatDto();
				intervalStatDto.setName(i + " - " + (i + 10));
				intervalStatDto.setPourcentage((double) ((((double) nbinterval) * 100) / totalDone));
				intervalStatDtoList.add(intervalStatDto);
			}
			return intervalStatDtoList;
		} else {
			return new ArrayList<>();
		}
	}

	@Override
	public List<TopCandidateDto> getRang(Long pack) {
		List<TopCandidateDto> liste = new ArrayList<>();
		Optional<Pack> pack1 = packRepo.findById(pack);
		if (pack1.isPresent()) {
			List<TestDto> tests = testMapper.toTestDtos(testRepo.findByPackAndDone(pack1.get(), true));
			for (int i = 0; i < tests.size(); i++) {
				TopCandidateDto topCandidateDto = new TopCandidateDto();
				topCandidateDto.setTestid(tests.get(i).getTestid());
				topCandidateDto.setCandidateName(tests.get(i).getCandidate().getName());
				topCandidateDto.setScore(tests.get(i).getTotalScore());
				liste.add(topCandidateDto);
			}
			liste.sort(new ScoreSorter());
			return liste;
		}
		return new ArrayList<>();
	}

	@Override
	public List<MoyenTechnologyDto> getAvgScoreParTechnologie(Long pack) {
		return testRepo.findMoyenScore(pack);
	}

	@Override
	public ByteArrayInputStream testPDF(Long idPack) {
		List<TestDto> tests = this.getAllTests(idPack);
		String state;
		String expired;
		Document document = new Document();
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		try {

			PdfPTable table = new PdfPTable(7);
			table.setWidthPercentage(100);
			table.setWidths(new int[] { 2, 6, 10, 5, 5, 5, 5 });

			Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

			PdfPCell hcell;
			hcell = new PdfPCell(new Phrase("Id", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Candidate Name", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Candidate Email", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);
			hcell = new PdfPCell(new Phrase("Phone", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);
			hcell = new PdfPCell(new Phrase("Creation Date", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);
			hcell = new PdfPCell(new Phrase("Done", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);
			hcell = new PdfPCell(new Phrase("Expired", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			for (TestDto test : tests) {

				PdfPCell cell;
				Calendar cal = Calendar.getInstance();
				Date date = cal.getTime();
				if (test.getExpirationDate() == null) {
					expired = " ";
				} else if (test.getExpirationDate().compareTo(date) < 0) {
					expired = "Expired";
				}

				else
					expired = "Not Expired";
				boolean done = test.getDone();
				if (done) {
					state = "Done";
				} else
					state = "Not Done";
				cell = new PdfPCell(new Phrase(test.getTestid().toString()));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(test.getCandidate().getName()));
				cell.setPaddingLeft(5);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(String.valueOf(test.getCandidate().getUser().getEmail())));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(5);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(test.getCandidate().getPhoneNumber()));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(5);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(String.valueOf(test.getCreationDate())));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(5);
				table.addCell(cell);
				cell = new PdfPCell(new Phrase(String.valueOf(state)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(5);
				table.addCell(cell);
				cell = new PdfPCell(new Phrase(String.valueOf(expired)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(5);
				table.addCell(cell);
			}
			PdfWriter.getInstance(document, out);
			document.open();
			document.add(table);
			document.close();
		} catch (DocumentException ex) {
		}

		return new ByteArrayInputStream(out.toByteArray());
	}

	@Override
	public List<TestDto> getAllTests(Long idPack) {
		return testMapper.toTestDtos(testRepo.getAllTests(idPack));
	}

	@Override
	public List<ResultTestQuestionDto> getResultDetail(Long idtest) {
		List<ResultTestQuestionDto> resultTestQuestionDtos = new ArrayList<>();
		Test t = testRepo.findById(idtest).get();
		List<TestQuestion> testQuestionList = testQuestionRepo.findByTest(t);
		testQuestionList.forEach(tq -> {
			ResultTestQuestionDto rtqdto = new ResultTestQuestionDto();
			rtqdto.setStatement(tq.getQuestion().getStatement());
			rtqdto.setTechnologyname(tq.getQuestion().getPrmTechnology().getTechnologyName());
			rtqdto.setResult(tq.getResult() == 1);
			rtqdto.setCode(tq.getQuestion().getCode());
			rtqdto.setScoreTotal(tq.getTest().getTotalScore());
			rtqdto.setCandidateName(tq.getTest().getCandidate().getName());
			rtqdto.setCandidateEmail(tq.getTest().getCandidate().getUser().getEmail());
			rtqdto.setCandidatePhone(tq.getTest().getCandidate().getPhoneNumber());
			List<ResultResponseDto> resultResponseDtos = new ArrayList<>();
			List<Response> responses = responseRepo.findByQuestion(tq.getQuestion());
			responses.forEach(rs -> {
				ResultResponseDto rsdto = new ResultResponseDto();
				rsdto.setCorrect(rs.getCorrect());
				rsdto.setValue(rs.getValue());
				rsdto.setChecked(resultRepo.findByTestAndResponse(t, rs).isPresent());
				resultResponseDtos.add(rsdto);
			});
			rtqdto.setResultResponseDtoList(resultResponseDtos);
			resultTestQuestionDtos.add(rtqdto);
		});

		return resultTestQuestionDtos;
	}

	@Override
	public int getSendNumber(Long idPack) {
		int totalSend = 0;
		List<TestDto> liste = this.getAllTests(idPack);
		for (int i = 0; i < liste.size(); i++) {
			boolean send = liste.get(i).getSend();
			if (send) {
				totalSend = totalSend + 1;
			}
		}
		return totalSend;
	}

}
