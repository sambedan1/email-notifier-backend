//package com.example.email_notifier_backend.report;
//
//import com.example.email_notifier_backend.Service.ServiceImpli.ReportService;
//import lombok.RequiredArgsConstructor;
//import org.quartz.Job;
//import org.quartz.JobExecutionContext;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.scheduling.quartz.QuartzJobBean;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDateTime;
//
//
//@RequiredArgsConstructor
//public class DailyReportJob extends QuartzJobBean {
//    private final ReportService reportService;
//
//    public DailyReportJob() {
//        this.reportService = null;
//    }
//
//    @Override
//    protected void executeInternal(JobExecutionContext context) {
//        if (reportService == null) {
//            System.out.println("❌ reportService NOT injected!");
//            return;
//        }
//
//        reportService.generateAndSendDailyReport();
//    }
//}



//package com.example.email_notifier_backend.report;
//
//import com.example.email_notifier_backend.Service.ServiceImpli.ReportService;
//import org.quartz.DisallowConcurrentExecution;
//import org.quartz.JobExecutionContext;
//import org.springframework.scheduling.quartz.QuartzJobBean;
//import org.springframework.stereotype.Component;
//
//@Component
//@DisallowConcurrentExecution
//public class DailyReportJob extends QuartzJobBean {
//
//    private ReportService reportService; // NOT final
//
//    public DailyReportJob() {
//        // Quartz requires a no-args constructor
//    }
//
//    // ⭐ Setter used by Spring to inject ReportService
//    public void setReportService(ReportService reportService) {
//        this.reportService = reportService;
//    }
//
//    @Override
//    protected void executeInternal(JobExecutionContext context) {
//        if (reportService == null) {
//            System.out.println("❌ reportService NOT injected!");
//            return;
//        }
//
//        System.out.println("📩 ReportService injected successfully!");
//        reportService.generateAndSendDailyReport();
//    }
//}

package com.example.email_notifier_backend.report;

import com.example.email_notifier_backend.Service.ServiceImpli.ReportService;
import org.quartz.JobExecutionContext;
import org.quartz.DisallowConcurrentExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

@Component
@DisallowConcurrentExecution
public class DailyReportJob extends QuartzJobBean {

    @Autowired
    private ReportService reportService;

    @Override
    protected void executeInternal(JobExecutionContext context) {
        if (reportService == null) {
            System.out.println("❌ reportService NOT injected!");
            return;
        }
        System.out.println("📩 ReportService injected successfully!");
        reportService.generateAndSendDailyReport();
    }
}



