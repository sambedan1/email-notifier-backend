package com.example.email_notifier_backend.Service.ServiceImpli;

import jakarta.annotation.PostConstruct;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuartzSchedulerService {
    @Autowired
    private  Scheduler scheduler;

    @PostConstruct
    public void scheduleEventReminderJob() throws SchedulerException {
        JobKey jobKey = new JobKey("eventReminderJob");
        TriggerKey triggerKey = new TriggerKey("eventReminderTrigger");

        if (scheduler.checkExists(jobKey)) {
            scheduler.deleteJob(jobKey);
        }

        JobDetail job = JobBuilder.newJob(EventReminderJob.class)
                .withIdentity(jobKey)
                .storeDurably()
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(triggerKey)
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0 10 * * ?")) // every day at 08:00
                .build();

        scheduler.scheduleJob(job, trigger);
    }
}
