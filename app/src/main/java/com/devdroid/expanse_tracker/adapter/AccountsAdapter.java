package com.devdroid.expanse_tracker.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.devdroid.expanse_tracker.models.Account;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.devdroid.expanse_tracker.R;
import com.devdroid.expanse_tracker.databinding.RowAccountsBinding;

import java.util.ArrayList;

public class AccountsAdapter extends RecyclerView.Adapter<AccountsAdapter.AccontsViewHolder> {
    Context context;
    ArrayList<Account> accountArrayList;

    public interface AccontsClickListener {
        void onAccountSelected(Account account);
    }

    AccontsClickListener accontsClickListener;

    public AccountsAdapter(Context context, ArrayList<Account> accountArrayList, AccontsClickListener accontsClickListener) {
        this.context = context;
        this.accountArrayList = accountArrayList;
        this.accontsClickListener = accontsClickListener;
    }

    @NonNull
    @Override
    public AccontsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AccontsViewHolder(LayoutInflater.from(context).inflate(R.layout.row_accounts, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AccontsViewHolder holder, int position) {
        Account account = accountArrayList.get(position);
        holder.binding.accountName.setText(account.getAccountName());
        holder.itemView.setOnClickListener(c -> {
            accontsClickListener.onAccountSelected(account);
        });
    }

    @Override
    public int getItemCount() {
        return accountArrayList.size();
    }

    public class AccontsViewHolder extends RecyclerView.ViewHolder {
        RowAccountsBinding binding;

        public AccontsViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = RowAccountsBinding.bind(itemView);
        }
    }
}
