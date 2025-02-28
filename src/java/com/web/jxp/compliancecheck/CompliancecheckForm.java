package com.web.jxp.compliancecheck;

import java.util.Collection;
import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class CompliancecheckForm extends ActionForm {

    private int candidateId;
    private int ctp;
    private String search;
    private String doCancel;
    private String doView;
    private String doSave;

    private Collection positions;
    private int type;
    private Collection clients;
    private Collection countries;
    private Collection assets;
    private int clientIdIndex;
    private int countryIdIndex;
    private int assetIdIndex;
    private int expectedsalary;
    private String fname;
    private int positionIdhidden;
    private int jobpostIdIndex;
    private Collection jobposts;
    private int jobpostId;
    private int shortlistId;
    private int shortlistccId;
    private int checkpointId;
    private int status ;
    private int rbchecked ;
    private String remarks;
    private int listsize ;
    private String pgvalue;

    public String getPgvalue() {
        return pgvalue;
    }

    public void setPgvalue(String pgvalue) {
        this.pgvalue = pgvalue;
    }

    public int getListsize() {
        return listsize;
    }

    public void setListsize(int listsize) {
        this.listsize = listsize;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
    public int getRbchecked() {
        return rbchecked;
    }

    public void setRbchecked(int rbchecked) {
        this.rbchecked = rbchecked;
    }

    
    public int getCheckpointId() {
        return checkpointId;
    }

    public void setCheckpointId(int checkpointId) {
        this.checkpointId = checkpointId;
    }

    
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    
    
    

    public int getShortlistccId() {
        return shortlistccId;
    }

    public void setShortlistccId(int shortlistccId) {
        this.shortlistccId = shortlistccId;
    }
    
    
    

    public int getShortlistId() {
        return shortlistId;
    }

    public void setShortlistId(int shortlistId) {
        this.shortlistId = shortlistId;
    }
    
    

    public int getJobpostId() {
        return jobpostId;
    }

    public void setJobpostId(int jobpostId) {
        this.jobpostId = jobpostId;
    }
    


    //educatinal details
    private int educationdetailId;
    private String kindname;
    private int kindId;
    private String degree;
    private int degreeId;
    private int highestqualification;
    private String backgroundofstudy;
    private FormFile educationfile;
    private Collection degrees;
    private Collection qualificationtypes;
    private String educationhiddenfile;
    private String doaddeducationdetail;
    private String doSaveeducationdetail;
    private String doVieweducationlist;
    private String doDeleteeducationdetail;

    // work experience
    private int experiencedetailId;
    private String companyname;
    private int companyindustryId;
    private Collection companyindustries;
    private int assettypeId;
    private Collection assettypes;
    private String assetname;
    private String clientpartyname;
    private int waterdepthId;
    private Collection waterdepths;
    private int lastdrawnsalarycurrencyId;
    private int lastdrawnsalary;
    private String workstartdate;
    private String workenddate;
    private int skillsId;
    private Collection skills;
    private int rankid;
    private Collection ranks;
    private String ownerpool;
    private int crewtypeid;
    private Collection crewtypes;
    private String ocsemployed;
    private String legalrights;
    private int dayratecurrencyid;
    private int dayrate;
    private int monthlysalarycurrencyId;
    private int monthlysalary;
    private int currentworkingstatus;
    private String experiencehiddenfile;
    private Collection grades;
    private int gradeid;
    private FormFile experiencefile;
    private String workinghiddenfile;
    private FormFile workingfile;
    private String doaddexperiencedetail;
    private String doSaveexperiencedetail;
    private String doViewexperiencelist;
    private String doDeleteexperiencedetail;

    public int getJobpostIdIndex() {
        return jobpostIdIndex;
    }

    public void setJobpostIdIndex(int jobpostIdIndex) {
        this.jobpostIdIndex = jobpostIdIndex;
    }
    
    public Collection getJobposts() {
        return jobposts;
    }

    public void setJobposts(Collection jobposts) {
        this.jobposts = jobposts;
    }

    
    public int getPositionIdhidden() {
        return positionIdhidden;
    }

    public void setPositionIdhidden(int positionIdhidden) {
        this.positionIdhidden = positionIdhidden;
    }
 
    public int getCtp() {
        return ctp;
    }

    public void setCtp(int ctp) {
        this.ctp = ctp;
    }
    public Collection getGrades() {
        return grades;
    }

    public void setGrades(Collection grades) {
        this.grades = grades;
    }

    public int getGradeid() {
        return gradeid;
    }

    public void setGradeid(int gradeid) {
        this.gradeid = gradeid;
    }

    public String getWorkstartdate() {
        return workstartdate;
    }

    public void setWorkstartdate(String workstartdate) {
        this.workstartdate = workstartdate;
    }

    public String getWorkenddate() {
        return workenddate;
    }

    public void setWorkenddate(String workenddate) {
        this.workenddate = workenddate;
    }

    public int getSkillsId() {
        return skillsId;
    }

    public void setSkillsId(int skillsId) {
        this.skillsId = skillsId;
    }

    public int getRankid() {
        return rankid;
    }

    public void setRankid(int rankid) {
        this.rankid = rankid;
    }

    public String getOwnerpool() {
        return ownerpool;
    }

    public void setOwnerpool(String ownerpool) {
        this.ownerpool = ownerpool;
    }

    public int getCrewtypeid() {
        return crewtypeid;
    }

    public void setCrewtypeid(int crewtypeid) {
        this.crewtypeid = crewtypeid;
    }

    public String getOcsemployed() {
        return ocsemployed;
    }

    public void setOcsemployed(String ocsemployed) {
        this.ocsemployed = ocsemployed;
    }

    public String getLegalrights() {
        return legalrights;
    }

    public void setLegalrights(String legalrights) {
        this.legalrights = legalrights;
    }

    public int getDayratecurrencyid() {
        return dayratecurrencyid;
    }

    public void setDayratecurrencyid(int dayratecurrencyid) {
        this.dayratecurrencyid = dayratecurrencyid;
    }

    public Collection getCompanyindustries() {
        return companyindustries;
    }

    public void setCompanyindustries(Collection companyindustries) {
        this.companyindustries = companyindustries;
    }

    public Collection getAssettypes() {
        return assettypes;
    }

    public void setAssettypes(Collection assettypes) {
        this.assettypes = assettypes;
    }

    public Collection getWaterdepths() {
        return waterdepths;
    }

    public void setWaterdepths(Collection waterdepths) {
        this.waterdepths = waterdepths;
    }

    public int getLastdrawnsalarycurrencyId() {
        return lastdrawnsalarycurrencyId;
    }

    public void setLastdrawnsalarycurrencyId(int lastdrawnsalarycurrencyId) {
        this.lastdrawnsalarycurrencyId = lastdrawnsalarycurrencyId;
    }

    public Collection getSkills() {
        return skills;
    }

    public void setSkills(Collection skills) {
        this.skills = skills;
    }

    public Collection getRanks() {
        return ranks;
    }

    public void setRanks(Collection ranks) {
        this.ranks = ranks;
    }

    public Collection getCrewtypes() {
        return crewtypes;
    }

    public void setCrewtypes(Collection crewtypes) {
        this.crewtypes = crewtypes;
    }

    public int getDayrate() {
        return dayrate;
    }

    public void setDayrate(int dayrate) {
        this.dayrate = dayrate;
    }

    public int getMonthlysalarycurrencyId() {
        return monthlysalarycurrencyId;
    }

    public void setMonthlysalarycurrencyId(int monthlysalarycurrencyId) {
        this.monthlysalarycurrencyId = monthlysalarycurrencyId;
    }

    public int getMonthlysalary() {
        return monthlysalary;
    }

    public void setMonthlysalary(int monthlysalary) {
        this.monthlysalary = monthlysalary;
    }

    public int getCurrentworkingstatus() {
        return currentworkingstatus;
    }

    public void setCurrentworkingstatus(int currentworkingstatus) {
        this.currentworkingstatus = currentworkingstatus;
    }

    public String getExperiencehiddenfile() {
        return experiencehiddenfile;
    }

    public void setExperiencehiddenfile(String experiencehiddenfile) {
        this.experiencehiddenfile = experiencehiddenfile;
    }

    public FormFile getExperiencefile() {
        return experiencefile;
    }

    public void setExperiencefile(FormFile experiencefile) {
        this.experiencefile = experiencefile;
    }

    public String getWorkinghiddenfile() {
        return workinghiddenfile;
    }

    public void setWorkinghiddenfile(String workinghiddenfile) {
        this.workinghiddenfile = workinghiddenfile;
    }

    public FormFile getWorkingfile() {
        return workingfile;
    }

    public void setWorkingfile(FormFile workingfile) {
        this.workingfile = workingfile;
    }

    public int getExperiencedetailId() {
        return experiencedetailId;
    }

    public void setExperiencedetailId(int experiencedetailId) {
        this.experiencedetailId = experiencedetailId;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public int getCompanyindustryId() {
        return companyindustryId;
    }

    public void setCompanyindustryId(int companyindustryId) {
        this.companyindustryId = companyindustryId;
    }

    public int getAssettypeId() {
        return assettypeId;
    }

    public void setAssettypeId(int assettypeId) {
        this.assettypeId = assettypeId;
    }

    public String getAssetname() {
        return assetname;
    }

    public void setAssetname(String assetname) {
        this.assetname = assetname;
    }

    public String getClientpartyname() {
        return clientpartyname;
    }

    public void setClientpartyname(String clientpartyname) {
        this.clientpartyname = clientpartyname;
    }

    public int getWaterdepthId() {
        return waterdepthId;
    }

    public void setWaterdepthId(int waterdepthId) {
        this.waterdepthId = waterdepthId;
    }

    public int getLastdrawnsalary() {
        return lastdrawnsalary;
    }

    public void setLastdrawnsalary(int lastdrawnsalary) {
        this.lastdrawnsalary = lastdrawnsalary;
    }

    public String getDoaddexperiencedetail() {
        return doaddexperiencedetail;
    }

    public void setDoaddexperiencedetail(String doaddexperiencedetail) {
        this.doaddexperiencedetail = doaddexperiencedetail;
    }

    public String getDoSaveexperiencedetail() {
        return doSaveexperiencedetail;
    }

    public void setDoSaveexperiencedetail(String doSaveexperiencedetail) {
        this.doSaveexperiencedetail = doSaveexperiencedetail;
    }

    public String getDoViewexperiencelist() {
        return doViewexperiencelist;
    }

    public void setDoViewexperiencelist(String doViewexperiencelist) {
        this.doViewexperiencelist = doViewexperiencelist;
    }

    public String getDoDeleteexperiencedetail() {
        return doDeleteexperiencedetail;
    }

    public void setDoDeleteexperiencedetail(String doDeleteexperiencedetail) {
        this.doDeleteexperiencedetail = doDeleteexperiencedetail;
    }

    public int getHighestqualification() {
        return highestqualification;
    }

    public void setHighestqualification(int highestqualification) {
        this.highestqualification = highestqualification;
    }

    public String getBackgroundofstudy() {
        return backgroundofstudy;
    }

    public void setBackgroundofstudy(String backgroundofstudy) {
        this.backgroundofstudy = backgroundofstudy;
    }

    public int getEducationdetailId() {
        return educationdetailId;
    }

    public void setEducationdetailId(int educationdetailId) {
        this.educationdetailId = educationdetailId;
    }

    public String getKindname() {
        return kindname;
    }

    public void setKindname(String kindname) {
        this.kindname = kindname;
    }

    public int getKindId() {
        return kindId;
    }

    public void setKindId(int kindId) {
        this.kindId = kindId;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public int getDegreeId() {
        return degreeId;
    }

    public void setDegreeId(int degreeId) {
        this.degreeId = degreeId;
    }

    public FormFile getEducationfile() {
        return educationfile;
    }

    public void setEducationfile(FormFile educationfile) {
        this.educationfile = educationfile;
    }

    public Collection getDegrees() {
        return degrees;
    }

    public void setDegrees(Collection degrees) {
        this.degrees = degrees;
    }

    public Collection getQualificationtypes() {
        return qualificationtypes;
    }

    public void setQualificationtypes(Collection qualificationtypes) {
        this.qualificationtypes = qualificationtypes;
    }

    public String getEducationhiddenfile() {
        return educationhiddenfile;
    }

    public void setEducationhiddenfile(String educationhiddenfile) {
        this.educationhiddenfile = educationhiddenfile;
    }

    public String getDoaddeducationdetail() {
        return doaddeducationdetail;
    }

    public void setDoaddeducationdetail(String doaddeducationdetail) {
        this.doaddeducationdetail = doaddeducationdetail;
    }

    public String getDoSaveeducationdetail() {
        return doSaveeducationdetail;
    }

    public void setDoSaveeducationdetail(String doSaveeducationdetail) {
        this.doSaveeducationdetail = doSaveeducationdetail;
    }

    public String getDoVieweducationlist() {
        return doVieweducationlist;
    }

    public void setDoVieweducationlist(String doVieweducationlist) {
        this.doVieweducationlist = doVieweducationlist;
    }

    public String getDoDeleteeducationdetail() {
        return doDeleteeducationdetail;
    }

    public void setDoDeleteeducationdetail(String doDeleteeducationdetail) {
        this.doDeleteeducationdetail = doDeleteeducationdetail;
    }

    public int getExpectedsalary() {
        return expectedsalary;
    }

    public void setExpectedsalary(int expectedsalary) {
        this.expectedsalary = expectedsalary;
    }

    public String getDoSave() {
        return doSave;
    }

    public void setDoSave(String doSave) {
        this.doSave = doSave;
    }

    public Collection getClients() {
        return clients;
    }

    public void setClients(Collection clients) {
        this.clients = clients;
    }

    public Collection getCountries() {
        return countries;
    }

    public void setCountries(Collection countries) {
        this.countries = countries;
    }

    public Collection getAssets() {
        return assets;
    }

    public void setAssets(Collection assets) {
        this.assets = assets;
    }

    public int getClientIdIndex() {
        return clientIdIndex;
    }

    public void setClientIdIndex(int clientIdIndex) {
        this.clientIdIndex = clientIdIndex;
    }

    public int getCountryIdIndex() {
        return countryIdIndex;
    }

    public void setCountryIdIndex(int countryIdIndex) {
        this.countryIdIndex = countryIdIndex;
    }

    public int getAssetIdIndex() {
        return assetIdIndex;
    }

    public void setAssetIdIndex(int assetIdIndex) {
        this.assetIdIndex = assetIdIndex;
    }

    public int getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(int candidateId) {
        this.candidateId = candidateId;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getDoCancel() {
        return doCancel;
    }

    public void setDoCancel(String doCancel) {
        this.doCancel = doCancel;
    }

    public String getDoView() {
        return doView;
    }

    public void setDoView(String doView) {
        this.doView = doView;
    }
    public Collection getPositions() {
        return positions;
    }

    public void setPositions(Collection positions) {
        this.positions = positions;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }
}
