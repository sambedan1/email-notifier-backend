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
import com.example.email_notifier_backend.Repository.NotificationLogRepository;
import com.example.email_notifier_backend.Service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationServivceImpli implements NotificationService {
    private final NotificationLogRepository logRepo;
    private final ModelMapper mapper;

    @Override
    public List<NotificationDTO> getAllLogs() {
        List<NotificationLog> logs = logRepo.findAll();
        return logs.stream()
                .map(log -> mapper.map(log, NotificationDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public NotificationDTO getLogById(Long id) {
        NotificationLog log = logRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Log not found"));

        return mapper.map(log, NotificationDTO.class);
    }
}
