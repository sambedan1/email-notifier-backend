package com.example.email_notifier_backend.Service.ServiceImpli;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor

public class ReportService {
    private final JavaMailSender mailSender;

    public void generateAndSendDailyReport() {

        try (Workbook workbook = new XSSFWorkbook()) {

            Sheet sheet = workbook.createSheet("Daily Report");

            Row row = sheet.createRow(0);
            row.createCell(0).setCellValue("Report for " + LocalDate.now());

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);

            sendEmailWithAttachment(out.toByteArray());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendEmailWithAttachment(byte[] file) throws Exception {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo("s3021617@gmail.com");
        helper.setSubject("hello from notifyhub");
        helper.setText("Attached is your daily reportdxjkjkfdty.");

        helper.addAttachment("report.xlsx", () -> new ByteArrayInputStream(file));

        mailSender.send(message);
    }
}
