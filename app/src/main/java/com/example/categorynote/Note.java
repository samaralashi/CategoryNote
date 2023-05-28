package com.example.categorynote;

public class Note {
    String noteId;
    String noteName;
    String desc;
    String categoryId;
    String image;

    public Note() {
    }

//    public Note(String noteId, String noteName, String desc, String categoryId) {
//        this.noteId = noteId;
//        this.noteName = noteName;
//        this.desc = desc;
//        this.categoryId = categoryId;
//    }

//    public Note(String noteId, String noteName, String desc) {
//        this.noteId = noteId;
//        this.noteName = noteName;
//        this.desc = desc;
//    }


    public Note(String noteId, String noteName, String desc, String categoryId, String image) {
        this.noteId = noteId;
        this.noteName = noteName;
        this.desc = desc;
        this.categoryId = categoryId;
        this.image = image;
    }


    public Note(String noteId, String noteName) {
        this.noteId = noteId;
        this.noteName = noteName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }

    public String getNoteName() {
        return noteName;
    }

    public void setNoteName(String noteName) {
        this.noteName = noteName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
}
