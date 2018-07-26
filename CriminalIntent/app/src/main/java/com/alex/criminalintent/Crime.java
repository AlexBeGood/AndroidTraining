package com.alex.criminalintent;

import java.util.Date;
import java.util.UUID;

public class Crime {

    public Crime()
    {
        mId = UUID.randomUUID();
        mDate = new Date();
        mType = TypesCrime.Simple;
    }

    //Индетификатор преступления
    private UUID mId;
    //Название преступления
    private String mTitle;
    //Дата преступления
    private Date mDate;
    //Статус преступления
    private boolean mSolved;
    //Тип преступления
    private TypesCrime mType;

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public TypesCrime getType() {
        return mType;
    }

    public void setType(TypesCrime typeCrime) {
        mType = typeCrime;
    }
}
