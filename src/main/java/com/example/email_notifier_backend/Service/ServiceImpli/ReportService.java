package com.example.email_notifier_backend.Service.ServiceImpli;

import com.example.email_notifier_backend.Entity.NotificationLog;
import com.example.email_notifier_backend.Repository.NotificationLogRepository;
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
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor

public class ReportService {
    private final NotificationLogRepository notificationRepo; // Add this!

    public ByteArrayInputStream generateExcelReportForUser(String useremail) throws IOException {
        List<NotificationLog> notifications = notificationRepo.findByRecipientEmail(useremail);

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Notifications");
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Event Title");
        header.createCell(1).setCellValue("Status");
        header.createCell(2).setCellValue("Sent At");

        int idx = 1;
        for (NotificationLog log : notifications) {
            Row row = sheet.createRow(idx++);
            row.createCell(0).setCellValue(log.getEvent().getTitle());
            row.createCell(1).setCellValue(log.getStatus());
            row.createCell(2).setCellValue(log.getSentTimestamp() != null ? log.getSentTimestamp().toString() : "");
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();
        return new ByteArrayInputStream(out.toByteArray());
    }
}
