package com.rummytitans.playcashrummyonline.cardgame.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class QuizResultModel {


    @SerializedName("questionAnswList")
    public ArrayList<QuestionAnswList> questionAnswList;
    @SerializedName("TotalPoint")
    public String TotalPoint;
    @SerializedName("MissQuestion")
    public int MissQuestion;
    @SerializedName("SkipQuestion")
    public int SkipQuestion;
    @SerializedName("CorrectQuestion")
    public int CorrectQuestion;
    @SerializedName("AttamQuestion")
    public int AttamQuestion;
    @SerializedName("TotalQuestion")
    public int TotalQuestion;
    @SerializedName("CompletedTime")
    public String CompletedTime;

    public static class QuestionAnswList {
        @SerializedName("Point")
        public String Point;
        @SerializedName("SubmitAnswer")
        public String SubmitAnswer;
        @SerializedName("Answer")
        public String Answer;
        @SerializedName("Iscorrect")
        public boolean Iscorrect;
        @SerializedName("IsMiss")
        public boolean IsMiss;
        @SerializedName("IsSkip")
        public boolean IsSkip;
        @SerializedName("IsAnswer")
        public boolean IsAnswer;
        @SerializedName("Question")
        public String Question;
        @SerializedName("QuestionId")
        public int QuestionId;
    }
}
