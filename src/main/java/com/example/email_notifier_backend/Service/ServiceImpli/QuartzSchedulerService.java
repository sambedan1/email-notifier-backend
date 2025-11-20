package com.example.email_notifier_backend.Service.ServiceImpli;

import com.example.email_notifier_backend.report.DailyReportJob;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuartzSchedulerService {
    @Autowired
    private  Scheduler scheduler;

    @PostConstruct
    public void scheduleDailyReportJob() throws SchedulerException {

        System.out.println("🔥 QuartzSchedulerService @PostConstruct RUNNING...");


        JobKey jobKey = new JobKey("dailyReportJob");
        TriggerKey triggerKey = new TriggerKey("dailyReportTrigger");

        // Delete existing job (important for devtools restarts)
        if (scheduler.checkExists(jobKey)) {
            scheduler.deleteJob(jobKey);
            System.out.println("🔥 Deleted old job before scheduling new one");
        }


        JobDetail job = JobBuilder.newJob(DailyReportJob.class)
                .withIdentity("dailyReportJob")
                .storeDurably()
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("dailyReportTrigger")
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0 8 * * ?"))
                .build();

        scheduler.scheduleJob(job, trigger);

        System.out.println("🔥 Scheduled dailyReportJob successfully!");
    }
}
