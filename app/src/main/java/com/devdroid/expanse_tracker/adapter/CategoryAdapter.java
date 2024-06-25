package com.devdroid.expanse_tracker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.devdroid.expanse_tracker.R;
import com.devdroid.expanse_tracker.databinding.SampleCategoryItemBinding;
import com.devdroid.expanse_tracker.models.Category;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    Context context;
    ArrayList<Category> categories;
    public  interface  CategoryClickListner{
        void onCategoryClicked(Category category);
    }
    CategoryClickListner categoryClickListner;
    public  CategoryAdapter(Context context , ArrayList<Category> categories, CategoryClickListner categoryClickListner){
        this.context=context;
        this.categories=categories;
        this.categoryClickListner = categoryClickListner;
    }
    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryViewHolder(LayoutInflater.from(context).inflate(R.layout.sample_category_item ,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = categories.get(position);
        holder.binding.categoryText.setText(category.getCategoryName());
        holder.binding.categoryIcon.setImageResource(category.getCategoryImage());
        holder.binding.categoryIcon.setBackgroundTintList(context.getColorStateList(category.getCategoryColor()));
        holder.itemView.setOnClickListener(c->{
            categoryClickListner.onCategoryClicked(category);
        });


    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public  class  CategoryViewHolder extends RecyclerView.ViewHolder{
        SampleCategoryItemBinding binding;
        public  CategoryViewHolder (@NonNull View itemView){

            super (itemView);
            binding = SampleCategoryItemBinding.bind(itemView);
        }
    }
}
