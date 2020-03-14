package com.example.android.examdutyalterationhelper;

public class RescheduleDuties {
    public String dutyName,dutyDate,startTime,endtime,incharge,phone;

    public RescheduleDuties() {
    }

    public RescheduleDuties(String dutyName, String dutyDate, String startTime, String endtime, String incharge, String phone) {
        this.dutyName = dutyName;
        this.dutyDate = dutyDate;
        this.startTime = startTime;
        this.endtime = endtime;
        this.incharge = incharge;
        this.phone = phone;
    }

    public String getDutyName() {
        return dutyName;
    }

    public void setDutyName(String dutyName) {
        this.dutyName = dutyName;
    }

    public String getDutyDate() {
        return dutyDate;
    }

    public void setDutyDate(String dutyDate) {
        this.dutyDate = dutyDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getIncharge() {
        return incharge;
    }

    public void setIncharge(String incharge) {
        this.incharge = incharge;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
