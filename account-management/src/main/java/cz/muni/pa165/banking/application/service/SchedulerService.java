package cz.muni.pa165.banking.application.service;

import cz.muni.pa165.banking.domain.scheduler.info.PaymentInfo;
import cz.muni.pa165.banking.domain.scheduler.util.TimerUtils;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class SchedulerService {
    private static final Logger LOG = LoggerFactory.getLogger(SchedulerService.class);
    private final Scheduler scheduler;
    @Autowired
    public SchedulerService(Scheduler scheduler){
        this.scheduler = scheduler;
    }
    public <T extends Job> void schedule(final Class<T> jobClass, final PaymentInfo info) {
        final JobDetail jobDetail = TimerUtils.buildJobDetail(jobClass, info);
        final Trigger trigger = TimerUtils.buildTrigger(jobClass, info);

        try {
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            LOG.error(e.getMessage(), e);
        }
    }
    public List<PaymentInfo> getAllRunningTimers() {
        try {
            return scheduler.getJobKeys(GroupMatcher.anyGroup())
                    .stream()
                    .map(jobKey -> {
                        try {
                            final JobDetail jobDetail = scheduler.getJobDetail(jobKey);
                            return (PaymentInfo) jobDetail.getJobDataMap().get(jobKey.getName());
                        } catch (final SchedulerException e) {
                            LOG.error(e.getMessage(), e);
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (final SchedulerException e) {
            LOG.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }
    public PaymentInfo getRunningTimer(final String timerId) {
        try {
            final JobDetail jobDetail = scheduler.getJobDetail(new JobKey(timerId));
            if (jobDetail == null) {
                LOG.error("Failed to find timer with ID '{}'", timerId);
                return null;
            }

            return (PaymentInfo) jobDetail.getJobDataMap().get(timerId);
        } catch (final SchedulerException e) {
            LOG.error(e.getMessage(), e);
            return null;
        }
    }

    public void updateTimer(final String timerId, final PaymentInfo info) {
        try {
            final JobDetail jobDetail = scheduler.getJobDetail(new JobKey(timerId));
            if (jobDetail == null) {
                LOG.error("Failed to find timer with ID '{}'", timerId);
                return;
            }

            jobDetail.getJobDataMap().put(timerId, info);

            scheduler.addJob(jobDetail, true, true);
        } catch (final SchedulerException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    public Boolean deleteTimer(final String timerId) {
        try {
            return scheduler.deleteJob(new JobKey(timerId));
        } catch (SchedulerException e) {
            LOG.error(e.getMessage(), e);
            return false;
        }
    }
    @PostConstruct
    public void init(){
        try{
            scheduler.start();
        } catch(SchedulerException e){
            LOG.error(e.getMessage(), e);
        }
    }
    @PreDestroy
    public void preDestroy(){
        try{
            scheduler.shutdown();
        } catch(SchedulerException e){
            LOG.error(e.getMessage(), e);
        }
    }
}
