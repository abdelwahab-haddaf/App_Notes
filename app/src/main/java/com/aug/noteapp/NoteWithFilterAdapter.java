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

public class NoteWithFilterAdapter extends RecyclerView.Adapter<NoteWithFilterAdapter.NotesViewHolder>
        implements Filterable {
    private Context mContext;
    private List<Note> mNotesSet;
    private List<Note> mNotesFiltered;

    public NoteWithFilterAdapter(Context mContext, List<Note> notes) {
        this.mContext = mContext;
        this.mNotesSet = notes;
        this.mNotesFiltered = new ArrayList<>();
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotesViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_note_rv, parent,
                        false));
    }

    public void setNoteList(List<Note> notes) {
        this.mNotesSet.addAll(notes);
        this.mNotesFiltered.addAll(notes);
        notifyDataSetChanged();
    }

    public void clear() {
        this.mNotesSet.clear();
        this.mNotesFiltered.clear();
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return mNotesSet == null ? 0 : mNotesFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new FilterData();
    }

    class NotesViewHolder extends RecyclerView.ViewHolder {
        private View mView;
        private View mViewBackgroundNote;
        private TextView mTitleNote;
        private TextView mDateNote;
        private TextView mContentNote;

        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            mViewBackgroundNote = mView.findViewById(R.id.view_item_note_rv);
            mTitleNote = mView.findViewById(R.id.tv_title_item_note_rv);
            mDateNote = mView.findViewById(R.id.tv_date_item_note_rv);
            mContentNote = mView.findViewById(R.id.tv_content_item_note_rv);
        }

        public void onBind(int position) {
            final Note note = mNotesFiltered.get(position);
            Utils.setBackgroundOval(mViewBackgroundNote, note.getBackgroundNoteSelected());
            mTitleNote.setText(note.getTitleNote());
            mContentNote.setText(note.getContentNote());
            mDateNote.setText(Utils.parseDateToMMddYYYY(note.getCreateAt()));
        }
    }

    class FilterData extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            String charString = charSequence.toString();
            if (TextUtils.isEmpty(charString)) {
                mNotesFiltered.clear();
                mNotesFiltered.addAll(mNotesSet);
            } else {
                mNotesFiltered.clear();
                for (Note row : mNotesSet) {
                    if (row.getTitleNote().toLowerCase().contains(charString.toLowerCase())
                            || row.getContentNote().toLowerCase().contains(charString.toLowerCase())) {
                        mNotesFiltered.add(row);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = mNotesFiltered;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            mNotesFiltered = (ArrayList<Note>) filterResults.values;
            notifyDataSetChanged();
        }
    }
}
