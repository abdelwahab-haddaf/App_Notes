package com.aug.noteapp;

public class Note {
    private String idNote;
    private String idCategory;
    private String titleNote;
    private String contentNote;
    private int backgroundNoteSelected;
    private long createAt;
    private long updateAt;

    public Note() {

    }

    public Note(String idCategory, String titleNote, String contentNote, int backgroundNoteSelected, long createAt, long updateAt) {
        this.idCategory = idCategory;
        this.titleNote = titleNote;
        this.contentNote = contentNote;
        this.backgroundNoteSelected = backgroundNoteSelected;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    public String getIdNote() {
        return idNote;
    }

    public void setIdNote(String idNote) {
        this.idNote = idNote;
    }

    public String getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(String idCategory) {
        this.idCategory = idCategory;
    }

    public String getTitleNote() {
        return titleNote;
    }

    public void setTitleNote(String titleNote) {
        this.titleNote = titleNote;
    }

    public String getContentNote() {
        return contentNote;
    }

    public void setContentNote(String contentNote) {
        this.contentNote = contentNote;
    }

    public int getBackgroundNoteSelected() {
        return backgroundNoteSelected;
    }

    public void setBackgroundNoteSelected(int backgroundNoteSelected) {
        this.backgroundNoteSelected = backgroundNoteSelected;
    }

    public long getCreateAt() {
        return createAt;
    }

    public void setCreateAt(long createAt) {
        this.createAt = createAt;
    }

    public long getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(long updateAt) {
        this.updateAt = updateAt;
    }
}
