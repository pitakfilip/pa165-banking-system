package cz.muni.pa165.banking.domain.scheduler.jobs;

import cz.muni.pa165.banking.application.api.AccountApi;
import cz.muni.pa165.banking.domain.scheduler.info.PaymentInfo;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaymentJob implements Job {
    private static final Logger LOG = LoggerFactory.getLogger(PaymentJob.class);
    private final AccountApi accountApi;
    @Autowired
    public PaymentJob(AccountApi accountApi){
        this.accountApi = accountApi;
    }
    @Override
    public void execute(JobExecutionContext context) {
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        PaymentInfo info = (PaymentInfo) jobDataMap.get(PaymentJob.class.getSimpleName());

        if (accountApi.payment(info.getSenderAccountId(), info.getReceiverAccountId(), info.getMoneyAmount())){
            LOG.info("Payment successful");
        }
        else {
            LOG.info("Payment unsuccessful");
        }
        LOG.info("Sender balance: " + accountApi.getAccount(info.getSenderAccountId()).getBalance()+", receiver balance: "+accountApi.getAccount(info.getReceiverAccountId()).getBalance());
    }
}
