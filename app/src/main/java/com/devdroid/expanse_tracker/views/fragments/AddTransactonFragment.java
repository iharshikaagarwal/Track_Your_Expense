package com.devdroid.expanse_tracker.views.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import com.devdroid.expanse_tracker.utils.Constants;

import com.devdroid.expanse_tracker.R;
import com.devdroid.expanse_tracker.adapter.AccountsAdapter;
import com.devdroid.expanse_tracker.adapter.CategoryAdapter;
import com.devdroid.expanse_tracker.databinding.FragmentAddTransactonBinding;
import com.devdroid.expanse_tracker.databinding.ListDailogBinding;
import com.devdroid.expanse_tracker.models.Account;
import com.devdroid.expanse_tracker.models.Category;
import com.devdroid.expanse_tracker.models.Transaction;
import com.devdroid.expanse_tracker.utils.Constants;
import com.devdroid.expanse_tracker.utils.Helper;
import com.devdroid.expanse_tracker.views.activities.MainActivity;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddTransactonFragment extends BottomSheetDialogFragment {

    public AddTransactonFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    FragmentAddTransactonBinding binding;
    Transaction transaction;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddTransactonBinding.inflate(inflater);
        transaction = new Transaction();






        binding.date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext());
                datePickerDialog.setOnDateSetListener((datePicker ,i,i1,i2) -> {
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.DAY_OF_MONTH, datePickerDialog.getDatePicker().getDayOfMonth());
                    calendar.set(Calendar.MONTH, datePickerDialog.getDatePicker().getMonth());
                    calendar.set(Calendar.YEAR, datePickerDialog.getDatePicker().getYear());
                    String dateToShow = Helper.formatDate(calendar.getTime());
                    binding.date.setText(dateToShow);
                    transaction.setDate(calendar.getTime());
                    transaction.setId(calendar.getTime().getTime());
                });
                datePickerDialog.show();
            }
        });

        binding.category.setOnClickListener(c -> {
            ListDailogBinding dailogBinding = ListDailogBinding.inflate(inflater);
            AlertDialog categoryDailog = new AlertDialog.Builder(getContext()).create();
            categoryDailog.setView(dailogBinding.getRoot());

            CategoryAdapter categoryAdapter = new CategoryAdapter(getContext(), Constants.categories, new CategoryAdapter.CategoryClickListner() {
                @Override
                public void onCategoryClicked(Category category) {
                    binding.category.setText(category.getCategoryName());
                    transaction.setCategory(category.getCategoryName());
                    categoryDailog.dismiss();
                }
            });
            dailogBinding.recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
            dailogBinding.recyclerView.setAdapter(categoryAdapter);
            categoryDailog.show();
        });

        // Move the account click listener outside the category click listener
        binding.account.setOnClickListener(c -> {
            ListDailogBinding dailogBinding = ListDailogBinding.inflate(inflater);
            AlertDialog accountsDailog = new AlertDialog.Builder(getContext()).create();
            accountsDailog.setView(dailogBinding.getRoot());
            ArrayList<Account> accounts = new ArrayList<>();
            accounts.add(new Account(0.0, "Cash"));
            accounts.add(new Account(0.0, "Card"));
            accounts.add(new Account(0.0, "PAYTM"));
            accounts.add(new Account(0.0, "GooglePay"));
            accounts.add(new Account(0.0, "Other"));

            AccountsAdapter adapter = new AccountsAdapter(getContext(), accounts, new AccountsAdapter.AccontsClickListener() {
                @Override
                public void onAccountSelected(Account account) {
                    binding.account.setText(account.getAccountName());
                    transaction.setAccount(account.getAccountName());
                    accountsDailog.dismiss();
                }
            });
            dailogBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            dailogBinding.recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
            dailogBinding.recyclerView.setAdapter(adapter);
            accountsDailog.show();
        });
        binding.saveTransactionBtn.setOnClickListener(c -> {
            double amount = Double.parseDouble(binding.amount.getText().toString());
            String note = binding.note.getText().toString();

            // Ensure transaction.getType() is not null before using .equals()
            if (transaction.getType() != null && transaction.getType().equals(Constants.EXPENSE)) {
                transaction.setAmount(amount * -1);
            } else {
                transaction.setAmount(amount);
            }

            transaction.setNote(note);

            MainActivity activity = (MainActivity) getActivity();
            if (activity != null) {
                activity.viewModel.addTransaction(transaction);
                activity.getTransaction();
            }

            dismiss();
        });


        binding.incomebtn.setOnClickListener(view -> {
            binding.incomebtn.setBackground(getContext().getDrawable(R.drawable.income_selector));
            binding.expansebtn.setBackground(getContext().getDrawable(R.drawable.default_selector));
            binding.expansebtn.setBackground(getContext().getDrawable(R.color.textColor));
            binding.incomebtn.setBackground(getContext().getDrawable(R.color.greenbtn));
            transaction.setType(Constants.INCOME);
        });

        binding.expansebtn.setOnClickListener(view -> {
            binding.incomebtn.setBackground(getContext().getDrawable(R.drawable.default_selector));
            binding.expansebtn.setBackground(getContext().getDrawable(R.drawable.expanse_selector));
            binding.incomebtn.setBackground(getContext().getDrawable(R.color.textColor));
            binding.expansebtn.setBackground(getContext().getDrawable(R.color.redbtn));
            transaction.setType(Constants.EXPENSE);

        });

        // Inflate the layout for this fragment
        return binding.getRoot();
    }

}
