package com.web.jxp.assessment;

import java.util.ArrayList;

public class AssessmentInfo
{
    private int assessmentId;
    private String name;
    private String quesType;
    private String paraName;
    private int status;
    private int userId;  
    private int ddlValue;
    private String ddlLabel;
    private String mode;
    private String parameterids;
    private ArrayList list;

    public ArrayList getList() {
        return list;
    }

    public String getParameterids() {
        return parameterids;
    }
    //for ddl
    public AssessmentInfo(int ddlValue, String ddlLabel)
    {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
    }

    public AssessmentInfo(int assessmentId, String name, int status, int userId,String mode,String parameterids,ArrayList list)
    {
        this.assessmentId = assessmentId;
        this.name = name;
        this.status = status;
        this.userId = userId;
        this.mode = mode;
        this.parameterids = parameterids;
        this.list = list;
    }
    
    public AssessmentInfo(int assessmentId, String name, String paraName, int status, int userId,String mode)
    {
        this.assessmentId = assessmentId;
        this.name = name;
        this.paraName = paraName;
        this.status = status;
        this.userId = userId;
        this.mode = mode;
    }
    
   public AssessmentInfo( String name,String parameterids,String  mode,int status)
   {
       this.name = name;
       this.parameterids = parameterids;
       this.mode = mode;
       this.status = status;
   }
   public AssessmentInfo(int assessmentId, String name,String parameterids,String  mode,int status)
   {
       this.assessmentId = assessmentId;
       this.name = name;
       this.parameterids = parameterids;
       this.mode = mode;
       this.status = status;
   }

    public String getMode() {
        return mode;
    }

    public int getAssessmentId() {
        return assessmentId;
    }

    public String getName() {
        return name;
    }

    public String getQuesType() {
        return quesType;
    }

    public String getParaName() {
        return paraName;
    }

    public int getStatus() {
        return status;
    }

    public int getUserId() {
        return userId;
    }

    public int getDdlValue() {
        return ddlValue;
    }

    public String getDdlLabel() {
        return ddlLabel;
    }
}
