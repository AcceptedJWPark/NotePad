package com.accepted.notepad.main;


public class Listitem_Memo {

    int memoCode;
    String rTitle;
    String rContent;
    String fTitle;
    String fContent;
    String date;
    String secureType;
    String clickType;

    public Listitem_Memo(int memoCode, String rTitle,  String rContent, String fTitle,  String fContent, String date, String secureType, String clickType) {
        this.memoCode = memoCode;
        this.rTitle = rTitle;
        this.rContent = rContent;
        this.fTitle = fTitle;
        this.fContent = fContent;
        this.date = date;
        this.secureType = secureType;
        this.clickType = clickType;
    }

    public int getMemoCode() {
        return memoCode;
    }

    public void setMemoCode(int memoCode) {
        this.memoCode = memoCode;
    }

    public String getRTitle() {
        return rTitle;
    }

    public void setRTitle(String rTitle) {
        this.rTitle = rTitle;
    }

    public String getRContent() {
        return rContent;
    }

    public void setRContent(String rContent) {
        this.rContent = rContent;
    }

    public String getFTitle() {
        return fTitle;
    }

    public void setFTitle(String fTitle) {
        this.fTitle = fTitle;
    }

    public String getFContent() {
        return fContent;
    }

    public void setFContent(String fContent) {
        this.fContent = fContent;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public String getClickType() {
        return clickType;
    }

    public void setClickType(String clickType) {
        this.clickType = clickType;
    }

    public String getSecureType() {
        return secureType;
    }

    public void setSecureType(String secureType) {
        this.secureType = secureType;
    }
}
