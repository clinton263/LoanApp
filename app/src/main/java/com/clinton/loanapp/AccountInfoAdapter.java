package com.clinton.loanapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AccountInfoAdapter extends RecyclerView.Adapter<AccountInfoAdapter.Customers> {
Context context;
List<AccountInfoModel> accountInfoModelList;

    public AccountInfoAdapter(Context context, List<AccountInfoModel> modelList) {
        this.context = context;
        this.accountInfoModelList = modelList;
    }

    @NonNull
    @Override
    public Customers onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(context).inflate(R.layout.my_account_layout,parent,false);
       Customers customers=new Customers(view);
        return customers;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull Customers holder, int position) {
    AccountInfoModel accountInfoModel=accountInfoModelList.get(position);
    holder.resident.setText("Resident: "+accountInfoModel.getResident());
    holder.phone_number.setText("Phone: "+accountInfoModel.getPhone_number());
    holder.loan_amount.setText("Requested: "+accountInfoModel.getLoan_amount()+" TZS");
    holder.taken_amount.setText("Receivable: "+accountInfoModel.getTaken_amount()+" TZS");
    holder.interest_amount.setText("Interest: "+accountInfoModel.getInterest_amount()+" TZS");
    holder.loan_date.setText("from: "+accountInfoModel.getLoan_date());
    holder.limit_date.setText("to: "+accountInfoModel.getLimit_date());
    holder.control_number.setText("Control number: "+accountInfoModel.getControl_number());
    holder.actual_debt.setText("Debt: "+accountInfoModel.getActual_debt()+" TZS");
    holder.actual_debt_penalt.setText("penalty: "+accountInfoModel.getActual_debt_penalt()+" TZS");
    holder.penalt.setText("penalty +: "+accountInfoModel.getPenalt()+" TZS");
    holder.status.setText("status: "+accountInfoModel.getStatus());
    holder.interest_percentage.setText("interest: "+accountInfoModel.getInterest_percentage()+" %");
    holder.direct_cost.setText("Direct charges: "+accountInfoModel.getDirect_cost()+" %");
    holder.fullname.setText("Names: "+accountInfoModel.getFullname());
    holder.actual_debt_penalt.setText("Penalty Debt: "+accountInfoModel.getActual_debt_penalt());

    //additional
        holder.penalt_large.setText("penalty increment: "+accountInfoModel.getPenalt()+" TZS");
        holder.actual_debt_penalty_large.setText("Actual Debt after penalty: "+accountInfoModel.getActual_debt_penalt()+" TZS");

        holder.amount_requested.setText("amount  requested: "+accountInfoModel.getLoan_amount()+" TZS");
        holder.amount_given.setText("cash given: "+accountInfoModel.getTaken_amount()+" TZS");
        holder.interest_percent.setText("interest percentage: "+accountInfoModel.getInterest_percentage()+" %");
        holder.interestamount.setText("interest amount "+accountInfoModel.getInterest_amount()+" TZS");
        holder.totalInstallment.setText("total installments "+accountInfoModel.getTotalInstallments()+" TZS");
        holder.remain_amout.setText("remain amount "+accountInfoModel.getRemain_amount()+" TZS");

     //card

        holder.debt.setText(accountInfoModel.getActual_debt()+" TZS");
        holder.remain.setText(accountInfoModel.getRemain_amount()+" TZS");

        holder.cash_given.setText(accountInfoModel.getTaken_amount()+" TZS");
        holder.totalInstallments.setText(accountInfoModel.getTotalInstallments()+" TZS");

    }

    @Override
    public int getItemCount() {
        return accountInfoModelList.size();
    }
    public static class Customers extends RecyclerView.ViewHolder{
        TextView fullname,resident,phone_number,loan_amount,interest_percentage,direct_cost,taken_amount,loan_date,limit_date,control_number,actual_debt,status,interest_amount,penalt,actual_debt_penalt,penalt_percent;
        //additional textview
        TextView penalt_large,actual_debt_large,actual_debt_penalty_large;
        TextView amount_requested,amount_given,interest_percent,interestamount,remain_amout,totalInstallment;
        //card view
        TextView remain,debt,cash_given,totalInstallments;

        public Customers(@NonNull View itemView) {
            super(itemView);
            resident=itemView.findViewById(R.id.resident_info);
            phone_number=itemView.findViewById(R.id.phonenumber_info);
            loan_amount=itemView.findViewById(R.id.loanamount_info);
            taken_amount=itemView.findViewById(R.id.takenamount_info);
            interest_amount=itemView.findViewById(R.id.interest_info);
            loan_date=itemView.findViewById(R.id.loandate_info);
            limit_date=itemView.findViewById(R.id.limitdate_info);
            control_number=itemView.findViewById(R.id.controlnumber_info_header_);
            actual_debt_penalt=itemView.findViewById(R.id.actualdebtpenalty_info);
            actual_debt=itemView.findViewById(R.id.actualdebt_info);
            penalt=itemView.findViewById(R.id.penaltamount_info);
            status=itemView.findViewById(R.id.status_info);
            interest_percentage=itemView.findViewById(R.id.interestPercentage_info);
            direct_cost=itemView.findViewById(R.id.directcost_info_);
            fullname=itemView.findViewById(R.id.fullname_info);

            //duplicate additional, we set one instance in two textviews
            penalt_large=itemView.findViewById(R.id.penalty_large);
            actual_debt_penalty_large=itemView.findViewById(R.id.actualdebtpenalty_large);
            amount_requested=itemView.findViewById(R.id.amountrequested_large);
            amount_given=itemView.findViewById(R.id.amountgiven_large);
            interest_percent=itemView.findViewById(R.id.interest_large);
            interestamount=itemView.findViewById(R.id.interestamount_large);
            totalInstallment=itemView.findViewById(R.id.totalinstallment_large);
            remain_amout=itemView.findViewById(R.id.remainAmount_large);
            remain=itemView.findViewById(R.id.remainAmount_card);
            debt=itemView.findViewById(R.id.actualdebt_info_card);
            cash_given=itemView.findViewById(R.id.cashgiven_card);
            totalInstallments=itemView.findViewById(R.id.totalinstallment_card);

            //card view

        }
    }
}
