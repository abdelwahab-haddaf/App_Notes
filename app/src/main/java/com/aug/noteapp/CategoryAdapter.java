package com.aug.noteapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.BaseViewHolder> {
    public static final int VIEW_TYPE_ADD_CATEGORY = 1;
    public static final int VIEW_TYPE_ALL_CATEGORY = 2;

    private Context mContext;
    private List<Category> mCategorySet;
    private InteractionCategoryListener mListener;

    public interface InteractionCategoryListener {
        void openCreateCategoryActivity();

        void openNotesCategory(String idCategory, int backgroundCategory);
    }

    public CategoryAdapter(Context mContext, List<Category> categories, InteractionCategoryListener mListener) {
        this.mContext = mContext;
        this.mCategorySet = categories;
        this.mListener = mListener;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_TYPE_ADD_CATEGORY;
        } else {
            return VIEW_TYPE_ALL_CATEGORY;
        }
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_ADD_CATEGORY:
                return new CreateCategoryViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_create_category_rv, parent,
                                false));
            case VIEW_TYPE_ALL_CATEGORY:
                return new CategoriesViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_category_rv, parent,
                                false));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position - 1);
    }

    public void setCategoryList(List<Category> categories) {
        this.mCategorySet.addAll(categories);
        notifyDataSetChanged();
    }

    public void clear() {
        this.mCategorySet.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mCategorySet == null ? 0 : mCategorySet.size() + 1;
    }

    class CreateCategoryViewHolder extends BaseViewHolder {
        private View mView;
        private ImageView mImgCreateCategory;

        public CreateCategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            mImgCreateCategory = itemView.findViewById(R.id.img_create_category_item_category_rv);
        }

        @Override
        public void onBind(int position) {
            super.onBind(position);
            mImgCreateCategory.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mListener.openCreateCategoryActivity();
                        }
                    }
            );
        }
    }

    class CategoriesViewHolder extends BaseViewHolder {
        private View mView;
        private View mViewCategory;
        private TextView mNameCategory;

        public CategoriesViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            mViewCategory = mView.findViewById(R.id.view_category_item_category_rv);
            mNameCategory = mView.findViewById(R.id.tv_name_category_item_category_rv);
        }

        @Override
        public void onBind(int position) {
            super.onBind(position);
            final Category category = mCategorySet.get(position);
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


    public abstract class BaseViewHolder extends RecyclerView.ViewHolder {

        private int mCurrentPosition;

        public BaseViewHolder(View itemView) {
            super(itemView);
        }

        public void onBind(int position) {
            mCurrentPosition = position;
        }

        public int getCurrentPosition() {
            return mCurrentPosition;
        }
    }
}
