package com.devdroid.expanse_tracker.views.fragments;

import static com.devdroid.expanse_tracker.utils.Constants.SELECTED_STATS_TYPE;
import static com.devdroid.expanse_tracker.utils.Constants.SELECTED_TAB_STATS;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anychart.AnyChart;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;
import com.devdroid.expanse_tracker.R;
import com.devdroid.expanse_tracker.databinding.FragmentStatsBinding;
import com.devdroid.expanse_tracker.models.Transaction;
import com.devdroid.expanse_tracker.utils.Constants;
import com.devdroid.expanse_tracker.utils.Helper;
import com.devdroid.expanse_tracker.viewmodels.MainViewModel;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.realm.RealmResults;


public class StatsFragment extends Fragment {



    public StatsFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    FragmentStatsBinding binding;
    Calendar calendar;


    public MainViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentStatsBinding.inflate(inflater);


        viewModel = new ViewModelProvider(requireActivity() ).get(MainViewModel.class);







        calendar = Calendar.getInstance();
        updateDate();
        binding.incomebtn.setOnClickListener(view -> {
            binding.incomebtn.setBackground(getContext().getDrawable(R.drawable.income_selector));
            binding.expansebtn.setBackground(getContext().getDrawable(R.drawable.default_selector));
            binding.expansebtn.setBackground(getContext().getDrawable(R.color.textColor));
            binding.incomebtn.setBackground(getContext().getDrawable(R.color.greenbtn));
            SELECTED_STATS_TYPE=Constants.INCOME;
            updateDate();
        });

        binding.expansebtn.setOnClickListener(view -> {
            binding.incomebtn.setBackground(getContext().getDrawable(R.drawable.default_selector));
            binding.expansebtn.setBackground(getContext().getDrawable(R.drawable.expanse_selector));
            binding.incomebtn.setBackground(getContext().getDrawable(R.color.textColor));
            binding.expansebtn.setBackground(getContext().getDrawable(R.color.redbtn));
            SELECTED_STATS_TYPE=Constants.EXPENSE;
            updateDate();

        });





        binding.nextDate.setOnClickListener(c -> {
            if (SELECTED_TAB_STATS==Constants.DAILY){
                calendar.add(Calendar.DATE, 1);
            }
            else if (SELECTED_TAB_STATS==Constants.MONTHLY){
                calendar.add(Calendar.MONTH, 1);

            }

            updateDate();
        });

        binding.previousDate.setOnClickListener(c -> {
            if (SELECTED_TAB_STATS==Constants.DAILY){
                calendar.add(Calendar.DATE, -1);
            }
            else if (SELECTED_TAB_STATS==Constants.MONTHLY){
                calendar.add(Calendar.MONTH, -1);

            }
            updateDate();
        });

        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getText().equals("Monthly")){
                    Constants.SELECTED_TAB_STATS=1;
                    updateDate();
                } else if (tab.getText().equals("Daily")) {
                    Constants.SELECTED_TAB_STATS=0;
                    updateDate();
                }
//                Toast.makeText(MainActivity.this,tab.getText().toString(),Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        Pie pie = AnyChart.pie();
        viewModel.categoriesTransaction.observe(getViewLifecycleOwner(),new Observer<RealmResults<Transaction>>() {
            @Override
            public void onChanged(RealmResults<Transaction> transactions) {

                if (transactions.size()>0){

                    binding.emptyState.setVisibility(View.GONE);
                    binding.anyChart.setVisibility(View.VISIBLE);

                    List<DataEntry> data = new ArrayList<>();

                    Map<String,Double> categoryMap = new HashMap<>();

                    for (Transaction transaction :transactions){
                        String category = transaction.getCategory();
                        double amount = transaction.getAmount();
                        if (categoryMap.containsKey(category)){
                            double currentTotal = categoryMap.get(category).doubleValue();
                            currentTotal+=Math.abs(amount);
                            categoryMap.put(category,currentTotal);

                        }
                        else {
                            categoryMap.put(category,Math.abs(amount));
                        }
                    }
                    for (Map.Entry<String,Double> entry :categoryMap.entrySet()){
                        data.add(new ValueDataEntry(entry.getKey(),entry.getValue()));

                    }
                    pie.data(data);

                }else {
                    binding.emptyState.setVisibility(View.VISIBLE);
                    binding.anyChart.setVisibility(View.GONE);
                }



            }
        });
        viewModel.getTransaction(calendar,SELECTED_STATS_TYPE);









        binding.anyChart.setChart(pie);
        // Inflate the layout for this fragment
        return binding.getRoot();
    }
    void updateDate() {
        if (Constants.SELECTED_TAB_STATS==Constants.DAILY){
            binding.currentDate.setText(Helper.formatDate(calendar.getTime()));

        }else if (Constants.SELECTED_TAB_STATS ==Constants.MONTHLY){
            binding.currentDate.setText(Helper.formatDateByMonth(calendar.getTime()));

        }
//        Calendar today = Calendar.getInstance();
//
//        // Ensure calendar date doesn't exceed today's date
//        if (calendar.after(today)) {
//            calendar.setTime(today.getTime());  // Set calendar to today's date
//        }

        viewModel.getTransaction(calendar,SELECTED_STATS_TYPE);
    }

}