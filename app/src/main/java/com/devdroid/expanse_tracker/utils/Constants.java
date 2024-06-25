package com.devdroid.expanse_tracker.utils;

import com.devdroid.expanse_tracker.R;
import com.devdroid.expanse_tracker.models.Category;

import java.util.ArrayList;

public class Constants {
    public static String INCOME ="INCOME";
    public static String EXPENSE ="EXPENSE";
    public  static  ArrayList<Category> categories ;
    public static int DAILY =0;
    public static int MONTHLY =1;


    public static int SELECTED_TAB =0;
    public static int SELECTED_TAB_STATS =0;
    public static String SELECTED_STATS_TYPE =Constants.INCOME;
    public static void setCategories(){
        categories=new ArrayList<>();

        categories.add(new Category(R.drawable.ic_salary,"Salary",R.color.category3 ));
        categories.add(new Category(R.drawable.ic_savings,"Savings",R.color.category2 ));
        categories.add(new Category(R.drawable.ic_business,"Business",R.color.category5 ));
        categories.add(new Category(R.drawable.ic_loan,"Loan",R.color.category1 ));
        categories.add(new Category(R.drawable.bargraph,"Invest",R.color.black ));
        categories.add(new Category(R.drawable.ic_rent,"Rent",R.color.category3 ));
        categories.add(new Category(R.drawable.ic_othercategory,"other",R.color.category7 ));
    }
    public static  Category getCategoryDetails(String categoryName){
        for (Category cat : categories){
            if (cat.getCategoryName().equals(categoryName)){
                return cat;
            }
        }
        return null;
    }
    public static int getAccountsColor(String accountName) {
        switch (accountName) {
            case "Bank":
                return R.color.bank_color;
            case "Cash":
                return R.color.cash_color;
            case "Card":
                return R.color.card_color;
            default:
                return R.color.default_color;
        }
    }

}
