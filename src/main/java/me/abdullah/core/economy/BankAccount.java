package me.abdullah.core.economy;

import java.util.UUID;

public class BankAccount {

    private UUID uuid;
    private int money;

    public BankAccount(BankAccountData data){
        this.uuid = data.uuid;
        this.money = data.money;
    }

    public BankAccountData getAsSerializable(){
        return new BankAccountData(uuid, money);
    }

    public int getMoney(){
        return money;
    }

    public void setMoney(int money){
        this.money = money;
    }

    public void unsafeAddMoney(int money){
        this.money += money;
    }

    public boolean safeAddMoney(int money){
        if(this.money + money < 0) return false;

        this.money += money;
        return true;
    }

    public UUID getUUID(){
        return uuid;
    }
}
