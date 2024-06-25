package com.devdroid.expanse_tracker.views.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.devdroid.expanse_tracker.R;
import com.devdroid.expanse_tracker.adapter.TransactionAdapter;
import com.devdroid.expanse_tracker.databinding.FragmentTransactionBinding;
import com.devdroid.expanse_tracker.models.Transaction;
import com.devdroid.expanse_tracker.utils.Constants;
import com.devdroid.expanse_tracker.utils.Helper;
import com.devdroid.expanse_tracker.viewmodels.MainViewModel;
import com.devdroid.expanse_tracker.views.activities.MainActivity;
import com.google.android.material.tabs.TabLayout;

import java.util.Calendar;

import io.realm.RealmResults;


public class TransactionFragment extends Fragment {




    public TransactionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    FragmentTransactionBinding binding;
    Calendar calendar;


    public MainViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTransactionBinding.inflate(inflater);
        viewModel = new ViewModelProvider(requireActivity() ).get(MainViewModel.class);







        calendar = Calendar.getInstance();
        updateDate();





        binding.nextDate.setOnClickListener(c -> {
            if (Constants.SELECTED_TAB==Constants.DAILY){
                calendar.add(Calendar.DATE, 1);
            }
            else if (Constants.SELECTED_TAB==Constants.MONTHLY){
                calendar.add(Calendar.MONTH, 1);

            }

            updateDate();
        });

        binding.previousDate.setOnClickListener(c -> {
            if (Constants.SELECTED_TAB==Constants.DAILY){
                calendar.add(Calendar.DATE, -1);
            }
            else if (Constants.SELECTED_TAB==Constants.MONTHLY){
                calendar.add(Calendar.MONTH, -1);

            }
            updateDate();
        });

        binding.floatingactionbutton.setOnClickListener(c -> {
            new AddTransactonFragment().show(getParentFragmentManager(), null);
        });
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getText().equals("Monthly")){
                    Constants.SELECTED_TAB=1;
                    updateDate();
                } else if (tab.getText().equals("Daily")) {
                    Constants.SELECTED_TAB=0;
                    updateDate();
                }


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        binding.transactionAmont.setLayoutManager(new LinearLayoutManager(getContext()));
        viewModel.transaction.observe(getViewLifecycleOwner(),new Observer<RealmResults<Transaction>>() {
            @Override
            public void onChanged(RealmResults<Transaction> transactions) {
                TransactionAdapter transactionAdapter = new TransactionAdapter(getActivity(), transactions);
                binding.transactionAmont.setAdapter(transactionAdapter);
                if (transactions.size()>0){
                    binding.emptyState.setVisibility(View.GONE);
                }else{
                    binding.emptyState.setVisibility(View.VISIBLE);
                }



            }
        });
        viewModel .totalIncome.observe(getViewLifecycleOwner(),new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                binding.incomeLb.setText(String.valueOf(aDouble));

            }
        });
        viewModel.totalExpense.observe(getViewLifecycleOwner(),new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                binding.expenseLb.setText(String.valueOf(aDouble));


            }
        });
        viewModel.totalAmount.observe(getViewLifecycleOwner(),new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                binding.totalLb.setText(String.valueOf(aDouble));


            }
        });
        viewModel.getTransaction(calendar);


        return binding.getRoot();
    }
//    public void getTransaction(){
//        viewModel.getTransaction(calendar);
//
//    }



    void updateDate() {
        if (Constants.SELECTED_TAB==Constants.DAILY){
            binding.currentDate.setText(Helper.formatDate(calendar.getTime()));

        }else if (Constants.SELECTED_TAB==Constants.MONTHLY){
            binding.currentDate.setText(Helper.formatDateByMonth(calendar.getTime()));

        }
//        Calendar today = Calendar.getInstance();
//
//        // Ensure calendar date doesn't exceed today's date
//        if (calendar.after(today)) {
//            calendar.setTime(today.getTime());  // Set calendar to today's date
//        }

        viewModel.getTransaction(calendar);
    }

}