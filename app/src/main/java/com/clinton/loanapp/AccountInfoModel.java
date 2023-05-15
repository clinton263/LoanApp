package com.clinton.loanapp;

public class AccountInfoModel
{
    String fullname;
    String resident;
    String phone_number;
    String  loan_amount;
    String  interest_percentage;
    String  direct_cost;
    String  taken_amount;
    String  loan_date;
    String  limit_date;
    String  control_number;
    String  actual_debt;
    String  status;
    String  interest_amount;
    String penalt;
    String  actual_debt_penalt;
    String totalInstallments;
    String remain_amount;

    public AccountInfoModel(String fullname, String resident, String phone_number, String loan_amount, String interest_percentage, String direct_cost, String taken_amount, String loan_date, String limit_date, String control_number, String actual_debt, String status, String interest_amount, String penalt, String actual_debt_penalt,String totalInstallments,String remain_amount) {
        this.fullname = fullname;
        this.resident = resident;
        this.phone_number = phone_number;
        this.loan_amount = loan_amount;
        this.interest_percentage = interest_percentage;
        this.direct_cost = direct_cost;
        this.taken_amount = taken_amount;
        this.loan_date = loan_date;
        this.limit_date = limit_date;
        this.control_number = control_number;
        this.actual_debt = actual_debt;
        this.status = status;
        this.interest_amount = interest_amount;
        this.penalt = penalt;
        this.actual_debt_penalt = actual_debt_penalt;
        this.totalInstallments=totalInstallments;
        this.remain_amount=remain_amount;
    }

    public String getFullname() {

        return fullname;
    }

    public void setFullname(String fullname)
    {
        this.fullname = fullname;
    }

    public String getResident() {

        return resident;
    }

    public void setResident(String resident) {
        this.resident = resident;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getLoan_amount()
    {
        return loan_amount;
    }

    public void setLoan_amount(String loan_amount) {
        this.loan_amount = loan_amount;
    }

    public String getInterest_percentage() {
        return interest_percentage;
    }

    public void setInterest_percentage(String interest_percentage) {
        this.interest_percentage = interest_percentage;
    }

    public String getDirect_cost() {
        return direct_cost;
    }

    public void setDirect_cost(String direct_cost) {
        this.direct_cost = direct_cost;
    }

    public String getTaken_amount() {
        return taken_amount;
    }

    public void setTaken_amount(String taken_amount) {
        this.taken_amount = taken_amount;
    }

    public String getLoan_date() {
        return loan_date;
    }

    public void setLoan_date(String loan_date) {
        this.loan_date = loan_date;
    }

    public String getLimit_date() {
        return limit_date;
    }

    public void setLimit_date(String limit_date) {
        this.limit_date = limit_date;
    }

    public String getControl_number() {
        return control_number;
    }

    public void setControl_number(String control_number) {
        this.control_number = control_number;
    }

    public String getActual_debt() {
        return actual_debt;
    }

    public void setActual_debt(String actual_debt) {
        this.actual_debt = actual_debt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInterest_amount() {
        return interest_amount;
    }

    public void setInterest_amount(String interest_amount) {
        this.interest_amount = interest_amount;
    }

    public String getPenalt() {
        return penalt;
    }

    public void setPenalt(String penalt) {
        this.penalt = penalt;
    }

    public String getActual_debt_penalt() {
        return actual_debt_penalt;
    }

    public void setActual_debt_penalt(String actual_debt_penalt) {
        this.actual_debt_penalt = actual_debt_penalt;
    }

    public String getTotalInstallments() {
        return totalInstallments;
    }

    public void setTotalInstallments(String totalInstallments) {
        this.totalInstallments = totalInstallments;
    }

    public String getRemain_amount() {
        return remain_amount;
    }

    public void setRemain_amount(String remain_amount) {
        this.remain_amount = remain_amount;
    }
}
