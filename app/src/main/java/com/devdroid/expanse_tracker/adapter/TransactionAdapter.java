package com.devdroid.expanse_tracker.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.devdroid.expanse_tracker.R;
import com.devdroid.expanse_tracker.databinding.RowTransactionBinding;
import com.devdroid.expanse_tracker.models.Category;
import com.devdroid.expanse_tracker.models.Transaction;
import com.devdroid.expanse_tracker.utils.Constants;
import com.devdroid.expanse_tracker.utils.Helper;
import com.devdroid.expanse_tracker.views.activities.MainActivity;

import io.realm.RealmResults;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {
    Context context;
    RealmResults<Transaction> transactions;

    public TransactionAdapter(Context context, RealmResults<Transaction> transactions) {
        this.context = context;
        this.transactions = transactions;
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TransactionViewHolder(LayoutInflater.from(context).inflate(R.layout.row_transaction, parent, false));
    }




//    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
//        Transaction transaction = transactions.get(position);
//
//        holder.binding.transactionAmont.setText(String.valueOf(transaction.getAmount()));
//        holder.binding.accountL.setText(transaction.getAccount());
//
//        holder.binding.transactionDate.setText(Helper.formatDate(transaction.getDate()));
//        holder.binding.transactionCategory.setText(transaction.getCategory());
//
//        // Fetch the category details
//        Category transactionCategory = Constants.getCategoryDetails(transaction.getCategory());
//
//        if(transaction.getType().equals(Constants.INCOME)) {
//            holder.binding.transactionAmont.setTextColor(context.getColor(R.color.greenbtn));
//        } else if(transaction.getType().equals(Constants.EXPENSE)) {
//            holder.binding.transactionAmont.setTextColor(context.getColor(R.color.redbtn));
//        }
//
//        holder.binding.accountL.setBackgroundTintList(context.getColorStateList(Constants.getAccountsColor(transaction.getAccount())));
//
//        if (transaction.getType().equals(Constants.INCOME)) {
//            holder.binding.transactionAmont.setTextColor(context.getColor(R.color.greenbtn));
//        } else if (transaction.getType().equals(Constants.EXPENSE)) {
//            holder.binding.transactionAmont.setTextColor(context.getColor(R.color.redbtn));
//        }
//
//        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                AlertDialog deleteDialog = new AlertDialog.Builder(context).create();
//                deleteDialog.setTitle("Delete Transaction");
//                deleteDialog.setMessage("Are you Sure You Want To Delete This Transaction");
//                deleteDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Yes", (dialog, which) -> {
//                    ((MainActivity) context).viewModel.deleteTransaction(transaction);
//                });
//                deleteDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "No", (dialog, which) -> {
//                    deleteDialog.dismiss();
//                });
//                deleteDialog.show();
//
//                return false;
//            }
//        });
//    }
    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        Transaction transaction = transactions.get(position);

        holder.binding.transactionAmont.setText(String.valueOf(transaction.getAmount()));
        holder.binding.accountL.setText(transaction.getAccount());

        holder.binding.transactionDate.setText(Helper.formatDate(transaction.getDate()));
        holder.binding.transactionCategory.setText(transaction.getCategory());

        // Fetch the category details
        Category transactionCategory = Constants.getCategoryDetails(transaction.getCategory());

        if (transaction.getType() != null && transaction.getType().equals(Constants.INCOME)) {
            holder.binding.transactionAmont.setTextColor(context.getColor(R.color.greenbtn));
        } else if (transaction.getType() != null && transaction.getType().equals(Constants.EXPENSE)) {
            holder.binding.transactionAmont.setTextColor(context.getColor(R.color.redbtn));
        }

        if (transaction.getAccount() != null) {
            holder.binding.accountL.setBackgroundTintList(context.getColorStateList(Constants.getAccountsColor(transaction.getAccount())));
        }

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog deleteDialog = new AlertDialog.Builder(context).create();
                deleteDialog.setTitle("Delete Transaction");
                deleteDialog.setMessage("Are you Sure You Want To Delete This Transaction");
                deleteDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Yes", (dialog, which) -> {
                    ((MainActivity) context).viewModel.deleteTransaction(transaction);
                });
                deleteDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "No", (dialog, which) -> {
                    deleteDialog.dismiss();
                });
                deleteDialog.show();

                return false;
            }
        });
    }


    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public class TransactionViewHolder extends RecyclerView.ViewHolder {
        RowTransactionBinding binding;

        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = RowTransactionBinding.bind(itemView);
        }
    }
}
