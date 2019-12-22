package com.aug.noteapp;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CategoryWithFilterAdapter extends RecyclerView.Adapter<CategoryWithFilterAdapter.CategoriesViewHolder>
        implements Filterable {

    private Context mContext;
    private List<Category> mCategorySet;
    private List<Category> mCategoriesFiltered;
    private InteractionCategoryListener mListener;

    public interface InteractionCategoryListener {
        void openNotesCategory(String idCategory, int backgroundCategory);
    }

    public CategoryWithFilterAdapter(Context mContext, List<Category> categories, InteractionCategoryListener mListener) {
        this.mContext = mContext;
        this.mCategorySet = categories;
        this.mCategoriesFiltered = new ArrayList<>();
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public CategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoriesViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category_rv, parent,
                        false));
    }

    public void setCategoryList(List<Category> categories) {
        this.mCategorySet.addAll(categories);
        this.mCategoriesFiltered.addAll(categories);
        notifyDataSetChanged();
    }

    public void clear() {
        this.mCategorySet.clear();
        this.mCategoriesFiltered.clear();
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return mCategorySet == null ? 0 : mCategoriesFiltered.size();
    }

    class CategoriesViewHolder extends RecyclerView.ViewHolder {
        private View mView;
        private View mViewCategory;
        private TextView mNameCategory;

        public CategoriesViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            mViewCategory = mView.findViewById(R.id.view_category_item_category_rv);
            mNameCategory = mView.findViewById(R.id.tv_name_category_item_category_rv);
        }

        public void onBind(int position) {
            final Category category = mCategoriesFiltered.get(position);
            Utils.setBackgroundCategory(mViewCategory, category.getBackgroundCategorySelected());
            mNameCategory.setText(category.getNameCategory());
            mViewCategory.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mListener.openNotesCategory(category.getIdCategory(), category.getBackgroundCategorySelected());
                        }
                    }
            );
        }
    }

    @Override
    public Filter getFilter() {
        return new FilterData();
    }

    class FilterData extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            String charString = charSequence.toString();
            if (TextUtils.isEmpty(charString)) {
                mCategoriesFiltered.clear();
                mCategoriesFiltered.addAll(mCategorySet);
            } else {
                mCategoriesFiltered.clear();
                for (Category row : mCategorySet) {
                    if (row.getNameCategory().toLowerCase().contains(charString.toLowerCase())) {
                        mCategoriesFiltered.add(row);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = mCategoriesFiltered;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            mCategoriesFiltered = (ArrayList<Category>) filterResults.values;
            notifyDataSetChanged();
        }
    }
}
