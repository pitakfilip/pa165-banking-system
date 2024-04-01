package cz.muni.pa165.banking.application.service;

import cz.muni.pa165.banking.domain.scheduler.info.PaymentInfo;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.TriggerListener;

public class SimpleTriggerListener implements TriggerListener {
    private final SchedulerService schedulerService;

    public SimpleTriggerListener(SchedulerService schedulerService) {
        this.schedulerService = schedulerService;
    }

    @Override
    public String getName() {
        return SimpleTriggerListener.class.getSimpleName();
    }

    @Override
    public void triggerFired(Trigger trigger, JobExecutionContext context) {
        final String timerId = trigger.getKey().getName();

        final JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        final PaymentInfo info = (PaymentInfo) jobDataMap.get(timerId);

        if (!info.isRunForever()) {
            int remainingFireCount = info.getRemainingPaymentCount();
            if (remainingFireCount == 0) {
                return;
            }

            info.setRemainingPaymentCount(remainingFireCount - 1);
        }

        schedulerService.updateTimer(timerId, info);
    }

    @Override
    public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
        return false;
    }

    @Override
    public void triggerMisfired(Trigger trigger) {

    }

    @Override
    public void triggerComplete(Trigger trigger, JobExecutionContext context, Trigger.CompletedExecutionInstruction triggerInstructionCode) {

    }
}