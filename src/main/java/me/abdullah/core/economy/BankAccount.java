package me.abdullah.core.economy;

import java.util.UUID;

public class BankAccount {

    private UUID uuid;
    private int money;

    public BankAccount(BankAccountData data){
        this.uuid = uuid;
        this.money = data.money;
    }

    public BankAccountData getRawData(){
        BankAccountData data = new BankAccountData(uuid);
        data.money = this.money;

        return data;
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
}
