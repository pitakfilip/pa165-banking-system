package cz.muni.pa165.banking.domain.process;

import cz.muni.pa165.banking.domain.process.status.StatusInformation;

public class ProcessOperations {

    public static void changeState(Process source, StatusInformation information) {
        source.setCurrentStatus(information);
    }
    
}
