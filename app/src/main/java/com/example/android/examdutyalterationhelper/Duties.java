package com.example.android.examdutyalterationhelper;

public class Duties {

    public String startTime,endTime,examDetail,incharge1,incharge2,remarks,date;

    public Duties() {
    }

    public Duties(String startTime, String endTime, String examDetail, String incharge1, String incharge2, String remarks, String date) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.examDetail = examDetail;
        this.incharge1 = incharge1;
        this.incharge2 = incharge2;
        this.remarks = remarks;
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getExamDetail() {
        return examDetail;
    }

    public void setExamDetail(String examDetail) {
        this.examDetail = examDetail;
    }

    public String getIncharge1() {
        return incharge1;
    }

    public void setIncharge1(String incharge1) {
        this.incharge1 = incharge1;
    }

    public String getIncharge2() {
        return incharge2;
    }

    public void setIncharge2(String incharge2) {
        this.incharge2 = incharge2;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
