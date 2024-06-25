package com.devdroid.expanse_tracker.views.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.devdroid.expanse_tracker.adapter.TransactionAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.devdroid.expanse_tracker.R;
import com.devdroid.expanse_tracker.databinding.ActivityMainBinding;
import com.devdroid.expanse_tracker.models.Transaction;
import com.devdroid.expanse_tracker.utils.Constants;
import com.devdroid.expanse_tracker.utils.Helper;
import com.devdroid.expanse_tracker.viewmodels.MainViewModel;
import com.devdroid.expanse_tracker.views.fragments.AddTransactonFragment;
//import com.devdroid.expanse_tracker.views.fragments.TransactionsFragment;
import com.devdroid.expanse_tracker.views.fragments.StatsFragment;
import com.devdroid.expanse_tracker.views.fragments.TransactionFragment;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    Calendar calendar;


    public MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);



        setSupportActionBar(binding.materialToolbar);
        getSupportActionBar().setTitle("Track Your Expense");

        Constants.setCategories();

        calendar = Calendar.getInstance();
        FragmentTransaction transaction= getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content,new TransactionFragment());
        transaction.commit();
        binding.bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction transaction= getSupportFragmentManager().beginTransaction();

                if (item.getItemId()==R.id.Transaction){
                    getSupportFragmentManager().popBackStack();

                }else if (item.getItemId()==R.id.Stats){
                    transaction.replace(R.id.content,new StatsFragment());
                    transaction.addToBackStack(null);


                }
                transaction.commit();

                return true;
            }
        });



//        binding.nextDate.setOnClickListener(c -> {
//            if (Constants.SELECTED_TAB==Constants.DAILY){
//                calendar.add(Calendar.DATE, 1);
//            }
//            else if (Constants.SELECTED_TAB==Constants.MONTHLY){
//                calendar.add(Calendar.MONTH, 1);
//
//            }
//
//            updateDate();
//        });
//
//        binding.previousDate.setOnClickListener(c -> {
//            if (Constants.SELECTED_TAB==Constants.DAILY){
//                calendar.add(Calendar.DATE, -1);
//            }
//            else if (Constants.SELECTED_TAB==Constants.MONTHLY){
//                calendar.add(Calendar.MONTH, -1);
//
//            }
//            updateDate();
//        });
//
//        binding.floatingactionbutton.setOnClickListener(c -> {
//            new AddTransactonFragment().show(getSupportFragmentManager(), null);
//        });
//        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                if (tab.getText().equals("Monthly")){
//                    Constants.SELECTED_TAB=1;
//                    updateDate();
//                } else if (tab.getText().equals("Daily")) {
//                   Constants.SELECTED_TAB=0;
//                    updateDate();
//                }
////                Toast.makeText(MainActivity.this,tab.getText().toString(),Toast.LENGTH_SHORT).show();
//
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });
//        binding.transactionAmont.setLayoutManager(new LinearLayoutManager(this));
//        viewModel.transaction.observe(this,new Observer<RealmResults<Transaction>>() {
//            @Override
//            public void onChanged(RealmResults<Transaction> transactions) {
//                TransactionAdapter transactionAdapter = new TransactionAdapter(MainActivity.this, transactions);
//                binding.transactionAmont.setAdapter(transactionAdapter);
//                if (transactions.size()>0){
//                    binding.emptyState.setVisibility(View.GONE);
//                 }else{
//                    binding.emptyState.setVisibility(View.VISIBLE);
//                }
//
//
//
//            }
//        });
//        viewModel .totalIncome.observe(this,new Observer<Double>() {
//            @Override
//            public void onChanged(Double aDouble) {
//                binding.incomeLb.setText(String.valueOf(aDouble));
//
//            }
//        });
//        viewModel.totalExpense.observe(this,new Observer<Double>() {
//            @Override
//            public void onChanged(Double aDouble) {
//                binding.expenseLb.setText(String.valueOf(aDouble));
//
//
//            }
//        });
//        viewModel.totalAmount.observe(this,new Observer<Double>() {
//            @Override
//            public void onChanged(Double aDouble) {
//                binding.totalLb.setText(String.valueOf(aDouble));
//
//
//            }
//        });
//        viewModel.getTransaction(calendar);








    }
    public void getTransaction(){
        viewModel.getTransaction(calendar);

    }



//    void updateDate() {
//        if (Constants.SELECTED_TAB==Constants.DAILY){
//            binding.currentDate.setText(Helper.formatDate(calendar.getTime()));
//
//        }else if (Constants.SELECTED_TAB==Constants.MONTHLY){
//            binding.currentDate.setText(Helper.formatDateByMonth(calendar.getTime()));
//
//        }
////        Calendar today = Calendar.getInstance();
////
////        // Ensure calendar date doesn't exceed today's date
////        if (calendar.after(today)) {
////            calendar.setTime(today.getTime());  // Set calendar to today's date
////        }
//
//        viewModel.getTransaction(calendar);
//    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
