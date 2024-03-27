package cz.muni.pa165.banking.dto;

public class DtoAccount {
    private final int number;
    private final int bankCode;
    private int balance = 0;
    private final DtoPerson owner;
    public DtoAccount(int number, int bankCode, DtoPerson owner){
        this.number = number;
        this.bankCode = bankCode;
        this.owner = owner;
    }
}
