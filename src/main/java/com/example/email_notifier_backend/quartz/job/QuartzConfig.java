package com.example.email_notifier_backend.quartz.job;

import com.example.email_notifier_backend.Service.ServiceImpli.EventReminderJob;
import com.example.email_notifier_backend.report.DailyReportJob;
import org.quartz.*;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

@Configuration
public class QuartzConfig {

    // Spring-aware JobFactory
    @Bean
    public SpringBeanJobFactory jobFactory(ApplicationContext appContext) {
        return new SpringBeanJobFactory() {
            @Override
            protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
                Object job = super.createJobInstance(bundle);
                appContext.getAutowireCapableBeanFactory().autowireBean(job);
                return job;
            }
        };
    }

    // JobDetail
    @Bean
    public JobDetail dailyReportJobDetail() {
        return JobBuilder.newJob(DailyReportJob.class)
                .withIdentity("dailyReportJob")
                .storeDurably()
                .build();
    }

    // Trigger (e.g., run every day at 9:00 AM)
    @Bean
    public Trigger dailyReportJobTrigger(JobDetail dailyReportJobDetail) {
        return TriggerBuilder.newTrigger()
                .forJob(dailyReportJobDetail)
                .withIdentity("dailyReportTrigger")
                .withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(9, 0))
                .build();
    }
    // --- Event Reminder Job ---
    @Bean
    public JobDetail eventReminderJobDetail() {
        return JobBuilder.newJob(EventReminderJob.class)
                .withIdentity("eventReminderJob")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger eventReminderJobTrigger(JobDetail eventReminderJobDetail) {
        return TriggerBuilder.newTrigger()
                .forJob(eventReminderJobDetail)
                .withIdentity("eventReminderTrigger")
                .withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(8, 0)) // daily check at 8:00 AM
                .build();
    }

    // Scheduler
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(
            SpringBeanJobFactory jobFactory,
            JobDetail dailyReportJobDetail,
            Trigger dailyReportJobTrigger,
            JobDetail eventReminderJobDetail,
            Trigger eventReminderJobTrigger) {

        SchedulerFactoryBean scheduler = new SchedulerFactoryBean();
        scheduler.setJobFactory(jobFactory);
        // Attach both jobs and triggers
        scheduler.setJobDetails(dailyReportJobDetail, eventReminderJobDetail);
        scheduler.setTriggers(dailyReportJobTrigger, eventReminderJobTrigger);
        return scheduler;
    }
}


