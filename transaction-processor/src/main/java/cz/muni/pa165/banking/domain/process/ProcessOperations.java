package cz.muni.pa165.banking.domain.process;

import cz.muni.pa165.banking.domain.process.status.StatusInformation;

/**
 * Util class for modification of Process
 */
public class ProcessOperations {

    public static void changeState(Process source, StatusInformation information) {
        source.setCurrentStatus(information);
    }
    
}
