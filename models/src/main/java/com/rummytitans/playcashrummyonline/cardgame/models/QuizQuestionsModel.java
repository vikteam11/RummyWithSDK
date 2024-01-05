package com.rummytitans.playcashrummyonline.cardgame.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class QuizQuestionsModel implements Serializable {

    @SerializedName("questionOptionList")
    public ArrayList<QuestionOptionList> questionOptionList;
    @SerializedName("IsSkip")
    public boolean IsSkip;
    @SerializedName("IsAnswer")
    public boolean IsAnswer;
    @SerializedName("Question")
    public String Question;
    @SerializedName("QuestionId")
    public int QuestionId;

    public static class QuestionOptionList implements Serializable {
        @SerializedName("OptionValue")
        public String OptionValue;
        @SerializedName("OptionId")
        public int OptionId;
        public Boolean isSelected;

    }
}
