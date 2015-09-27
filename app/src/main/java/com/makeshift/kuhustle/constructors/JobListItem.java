package com.makeshift.kuhustle.constructors;

import java.util.Calendar;

public class JobListItem {

    private int id;
    private String jobTitle;
    private String jobDescription;
    private String valueRange;
    private Calendar timeLeft;
    private String numberOfBids;
    private int icon;


    public JobListItem(int id, String jobTitle, String jobDescription, String valueRange, Calendar timeLeft, String numberOfBids, int icon) {

        this.id = id;
        this.jobTitle = jobTitle;
        this.jobDescription = jobDescription;
        this.valueRange = valueRange;
        this.numberOfBids = numberOfBids;
        this.timeLeft = timeLeft;
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public String getValueRange() {
        return valueRange;
    }

    public void setValueRange(String valueRange) {
        this.valueRange = valueRange;
    }


    public String getNumberOfBids() {
        return numberOfBids;
    }

    public void setNumberOfBids(String numberOfBids) {
        this.numberOfBids = numberOfBids;
    }

    public Calendar getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(Calendar timeLeft) {
        this.timeLeft = timeLeft;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }


}