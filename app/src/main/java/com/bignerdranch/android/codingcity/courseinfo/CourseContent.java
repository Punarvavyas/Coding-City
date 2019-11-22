package com.bignerdranch.android.codingcity.courseinfo;

import android.os.Bundle;

import com.bignerdranch.android.codingcity.R;

import java.util.List;
import java.util.Map;

public class CourseContent{

    String topicName;

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getTopicText() {
        return topicText;
    }

    public void setTopicText(String topicText) {
        this.topicText = topicText;
    }

    String topicText;

    public CourseContent(String topicName, String topicText) {
        this.topicName = topicName;
        this.topicText = topicText;
    }
}
