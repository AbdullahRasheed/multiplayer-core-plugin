package me.abdullah.core.economy;

import java.io.Serializable;
import java.util.UUID;

public class BankAccountData implements Serializable {

    public UUID uuid;
    public int money;

    public BankAccountData(UUID uuid){
        this.uuid = uuid;
        this.money = 0;
    }

    public BankAccountData(UUID uuid, int money){
        this.uuid = uuid;
        this.money = money;
    }
}
