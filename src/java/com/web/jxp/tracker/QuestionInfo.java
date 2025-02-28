package com.web.jxp.tracker;

public class QuestionInfo
{  
    private String categoryName;
    private String questionName;
    private String answer;
    private String onlinedate;
    //for ddl
    public QuestionInfo(String categoryName, String questionName, String answer, String onlinedate)
    {
        this.categoryName = categoryName;
        this.questionName = questionName;
        this.answer = answer;
        this.onlinedate = onlinedate;
    }

    /**
     * @return the categoryName
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * @return the questionName
     */
    public String getQuestionName() {
        return questionName;
    }

    /**
     * @return the answer
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * @return the onlinedate
     */
    public String getOnlinedate() {
        return onlinedate;
    }
}
