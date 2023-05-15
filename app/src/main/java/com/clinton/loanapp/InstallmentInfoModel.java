package com.clinton.loanapp;

//THIS MODEL CLASS INSTANTIATE CUSTOMER INSTALLATIONS

public class InstallmentInfoModel
{
    String installed_amount;
    String control_number;
    String installment_date;

    public InstallmentInfoModel(String installed_amount, String control_number, String installment_date) {
        this.installed_amount = installed_amount;
        this.control_number = control_number;
        this.installment_date = installment_date;
    }

    public String getInstalled_amount() {
        return installed_amount;
    }

    public String getControl_number() {
        return control_number;
    }

    public String getInstallment_date() {
        return installment_date;
    }
}
