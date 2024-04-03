package cz.muni.pa165.banking.domain.process.handler;

import cz.muni.pa165.banking.domain.process.Process;
import cz.muni.pa165.banking.domain.process.repository.HandlerMBeanRepository;
import cz.muni.pa165.banking.domain.process.repository.ProcessRepository;

public class CrossAccountHandler extends ProcessHandler {
    
    CrossAccountHandler(ProcessRepository processRepository) {
        super(processRepository);
    }

    @Override
    void evaluate(Process process, HandlerMBeanRepository beans) {
        // TODO
    }

}
