//package com.example.email_notifier_backend.Service.ServiceImpli;
//
//import com.example.email_notifier_backend.Dto.NotificationDTO;
//import com.example.email_notifier_backend.Entity.NotificationLog;
//import com.example.email_notifier_backend.Repository.NotificationLogRepository;
//import com.example.email_notifier_backend.Service.NotificationService;
//import lombok.RequiredArgsConstructor;
//import org.modelmapper.ModelMapper;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//public class NotificationServivceImpli implements NotificationService {
//    private final NotificationLogRepository logRepo;
//    private final ModelMapper mapper;
//
//    @Override
//    public List<NotificationDTO> getAllLogs() {
//        List<NotificationLog> logs = logRepo.findAll();
//
//        return logs.stream()
//                .map(log -> mapper.map(log, NotificationDTO.class))
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public NotificationDTO getLogById(Long id) {
//        NotificationLog log = logRepo.findById(id)
//                .orElseThrow(() -> new RuntimeException("Log not found"));
//
//        return mapper.map(log, NotificationDTO.class);
//    }
//}
package com.example.email_notifier_backend.Service.ServiceImpli;

import com.example.email_notifier_backend.Dto.NotificationDTO;
import com.example.email_notifier_backend.Entity.NotificationLog;
import com.example.email_notifier_backend.Entity.User;
import com.example.email_notifier_backend.Repository.NotificationLogRepository;
import com.example.email_notifier_backend.Repository.UserRepository;
import com.example.email_notifier_backend.Service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationServivceImpli implements NotificationService {
    @Autowired
    private NotificationLogRepository notificationRepo;
    @Autowired private UserRepository userRepo;
    @Autowired private ModelMapper modelMapper;

    @Override
    public List<NotificationDTO> getUserNotifications(String email) {
        User user = userRepo.findByEmail(email).orElseThrow();
        return notificationRepo.findByRecipientEmail(user.getEmail()).stream()
                .map(log -> modelMapper.map(log, NotificationDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ByteArrayInputStream generateExcelReportForUser(String useremail) {
        List<NotificationLog> notifications = notificationRepo.findByRecipientEmail(useremail);
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Notifications");
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Event Title");
            header.createCell(1).setCellValue("Recipient");
            header.createCell(2).setCellValue("Status");
            header.createCell(3).setCellValue("Sent At");
            int idx = 1;
            for (NotificationLog log : notifications) {
                Row row = sheet.createRow(idx++);
                row.createCell(0).setCellValue(log.getEvent().getTitle());
                row.createCell(1).setCellValue(log.getRecipientEmail());
                row.createCell(2).setCellValue(log.getStatus());
                row.createCell(3).setCellValue(log.getSentTimestamp() != null ? log.getSentTimestamp().toString() : "");
            }
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("Report generation failed", e);
        }
    }

}
