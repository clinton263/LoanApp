package com.clinton.loanapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

//THIS ADAPTER IT BINDS,CREATES,PUTS DATA TO THE VIEW HOLDER OR CONTENTS LAYOUT
public class InstallmentInfoAdapter extends RecyclerView.Adapter<InstallmentInfoAdapter.Customers> {
    Context context;
    List<InstallmentInfoModel> installmentInfoModelList;

    public InstallmentInfoAdapter(Context context, List<InstallmentInfoModel> installmentInfoModelList) {
        this.context = context;
        this.installmentInfoModelList = installmentInfoModelList;
    }

    @NonNull
    @Override
    public Customers onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.my_installment_layout, parent, false);
        Customers customers = new Customers(view);
        return customers;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull Customers holder, int position) {
        InstallmentInfoModel installmentInfoModel = installmentInfoModelList.get(position);
        holder.control_number.setText("Control number: " + installmentInfoModel.getControl_number());
        holder.installement_date.setText("date paid: " + installmentInfoModel.getInstallment_date());
        holder.installed_amount.setText("Amount paid " + installmentInfoModel.getInstalled_amount() + " TZS");
    }
    @Override
    public int getItemCount() {
        return installmentInfoModelList.size();
    }

    public static class Customers extends RecyclerView.ViewHolder {
        TextView control_number, installed_amount, installement_date;

        public Customers(@NonNull View itemView) {
            super(itemView);
            installement_date = itemView.findViewById(R.id.dateInstalled);
            installed_amount = itemView.findViewById(R.id.installed_amount);
            control_number = itemView.findViewById(R.id.controlNumber);

        }
    }
}
