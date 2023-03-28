package com.example.drilloxprocessco;

import java.io.*;
import java.util.*;

public class ProjectBuilder implements Cloneable {
    //private fields to use
    private String ProjectName;
    private int EstimatedAccomplishmentTime;
    private int TotalNumberOfParts;
    private int TotalNumberOfNeededReq;
    private int TotalNumberOfAvReq;
    private int TotalNumberOfCompletedParts;
    private float RatioOfAvailableRequirements_NeededRequirements;
    private float PercentageOfAvailabeRequirements;
    private ArrayList<String> NeededRequirements = new ArrayList<>();
    private ArrayList<String> AvailableRequirements = new ArrayList<>();
    private ArrayList<String> Prolangs = new ArrayList<>();
    private ArrayList<String> ProjectParts = new ArrayList<>();
    private ArrayList<String> ProjectCompletedParts = new ArrayList<>();
    private float Progress; //in terms of the ratio of completedparts:totalparts
    public static File DataNames = new File(System.getProperty("user.home"),".Data_ProName.bat");
    public static File DataProps = new File(System.getProperty("user.home"),".Data_ProProp.bat");
    private static Properties Data_Names = new Properties();
    private static Properties Data_Props = new Properties();
    private static ArrayList<ProjectBuilder> projects = new ArrayList<>();
    private static TreeMap<String, ProjectBuilder> AllProjects = new TreeMap<>();

    private static boolean calledByRetrive = false;

    //public methods
    private ProjectBuilder(){

    }
    public ProjectBuilder(String ProjectName) {
        if (ProjectName!=null)this.ProjectName = ProjectName;
        for (int i =0; i<ProjectBuilder.projects.toArray().length; i++){
            if (Objects.equals(ProjectName, ProjectBuilder.projects.get(i).getProjectName()))
                System.out.println("Project Name Exists");
            else ProjectBuilder.projects.add(this);
        }

    }

    public String getProjectName(){return this.ProjectName;}
    public int getEstimatedAccomplishmentTime() {return this.EstimatedAccomplishmentTime;}
    public float getRatioOfAvailableRequirements_NeededRequirements(){return this.RatioOfAvailableRequirements_NeededRequirements;}
    public float getProgress(){return this.Progress;}
    public float getPercentageOfAvailabeRequirements(){return this.PercentageOfAvailabeRequirements;}
    public ArrayList<String> getProlangs(){return this.Prolangs;}
    public ArrayList<String> getProjectParts(){return this.ProjectParts;}
    public ArrayList<String> getNeededRequirements(){return this.NeededRequirements;}
    public ArrayList<String> getAvailableRequirements(){return this.AvailableRequirements;}
    public ArrayList<String> getProjectCompletedParts(){return this.ProjectCompletedParts;}
    public static TreeMap<String,ProjectBuilder> getProjects(){return AllProjects;}
    public static ProjectBuilder getProject(String ProjectName){
        return AllProjects.get(ProjectName);
    }
    public void AddNeededRequirement(String Req){
        if (Req!=null) this.NeededRequirements.add(Req);
        this.UpdateProjectStatus();
    }
    public void AddNeededRequirements(String ...requirements){
        for (String rq:requirements){
            if (rq!=null) this.AddNeededRequirement(rq);
        }
        this.UpdateProjectStatus();
    }
    public void RemoveNeededRequirement(String Req){
        for (int i = 0; i<this.getNeededRequirements().toArray().length; i++){
            if (Objects.equals(this.NeededRequirements.get(i), Req)) this.NeededRequirements.remove(i);
        }
        this.UpdateProjectStatus();
    }
    public void RemoveNeededRequirements(String ...Reqs){
        for (int i = 0; i<this.getNeededRequirements().toArray().length; i++){
            for (String req : Reqs) {
                if (Objects.equals(this.getNeededRequirements().get(i), req)) this.NeededRequirements.remove(i);
            }
        }
        this.UpdateProjectStatus();
    }
    private void setRatioOfAvailableRequirements_NeededRequirements(int AvR, int NeR){
        if(NeR!=0) this.RatioOfAvailableRequirements_NeededRequirements = (float) AvR/NeR;
        this.UpdateProjectStatus();
    }

    public void AddAvailabeRequirements(String ...requirements){
        for (String requirement: requirements){
            if (requirement!=null) this.AvailableRequirements.add(requirement);
        }
        this.UpdateProjectStatus();
    }
    public void AddAvailableRequirement(String req){
        if(req!=null) this.AvailableRequirements.add(req);
        this.UpdateProjectStatus();
    }
    public void AddProjectCompletedPart(String partName){
        if(partName!=null) this.ProjectCompletedParts.add(partName);
        this.UpdateProjectStatus();
    }
    public void AddProjectCompletedParts(String ...parts){
        //this.ProjectCompletedParts.addAll(Arrays.asList(parts));
        for (String cpart: parts){
            if (cpart!=null) this.ProjectCompletedParts.add(cpart);
        }
        this.UpdateProjectStatus();
    }
    public void RemoveProjectCompletedPart(String part){
        if (!NotAvailable(part, this.ProjectCompletedParts.toArray(String[]::new)))
            this.ProjectCompletedParts.remove(part);
        this.UpdateProjectStatus();
    }
    public void RemoveProjectCompletedParts(String ...parts){
        //code ....
        this.UpdateProjectStatus();
    }
    public void AddProjectPart(String part){
        if (part!=null) this.ProjectParts.add(part);
        this.UpdateProjectStatus();
    }
    public void AddProjectParts(String ...parts){
        for (String part:parts){
            if (this.NotAvailable(part,this.ProjectParts.toArray(String[]::new))){
                if (part!=null)this.ProjectParts.add(part);
            }
        }
        this.UpdateProjectStatus();
    }
    public void setEstimatedAccomplishmentTime(int time){
        if (time!=0)this.EstimatedAccomplishmentTime = time;
        this.UpdateProjectStatus();
    }
    public void RemoveProjectPart(String part){
        if (!NotAvailable(part, this.getProjectParts().toArray(new String[0])))
            this.ProjectParts.remove(part);
        this.UpdateProjectStatus();
    }
    public void RemoveProjectParts(String ...parts){
        //code ...
        this.UpdateProjectStatus();
    }
    public void TerminateProject(String ProjectName){
        ProjectBuilder.projects.removeIf(project -> Objects.equals(project.getProjectName(), ProjectName));
        this.UpdateProjectStatus();
    }
    public void TerminateProjects(String ...ProjectNames){
        //code ...
        this.UpdateProjectStatus();
    }

    private boolean NotAvailable(String part, String[] toArray) {
        boolean not = true;
        for (String p: toArray){
            if (part.trim().equals(p)) {
                not = false;
                break;
            }
        }

        return not;
    }

    public void UpdateProjectStatus(){
        ArrayList<RevBook> MyRev = RevBook.getBooks();
        int RevI = MyRev.size();
        int ProI = this.getNeededRequirements().size();

        for (RevBook revBook : MyRev) {
            for (int k = 0; k < ProI; k++) {
                if (revBook.getBookTitle().trim().contains(this.getNeededRequirements().get(k).trim())) {
                    if (!this.getAvailableRequirements().contains(revBook))
                        this.AvailableRequirements.add(revBook.getBookTitle()); //adding a clone of the book in here
                    break;
                }
            }
        }
        this.PercentageOfAvailabeRequirements = this.getRatioOfAvailableRequirements_NeededRequirements()*100.0f;

        this.TotalNumberOfParts = this.ProjectParts.size();
        this.TotalNumberOfAvReq = this.AvailableRequirements.size();
        this.TotalNumberOfNeededReq = this.NeededRequirements.size();
        this.TotalNumberOfCompletedParts = this.ProjectCompletedParts.size();

         if (this.TotalNumberOfParts!=0){
             this.Progress = ((float)this.getProjectCompletedParts().size()/(float) this.TotalNumberOfParts)*100.0f;
         }
         else this.Progress = 0.0f;


        this.RatioOfAvailableRequirements_NeededRequirements = (float) this.getAvailableRequirements().size()/(float) this.getNeededRequirements().size();

        try{
            if (!ProjectBuilder.calledByRetrive) ProjectBuilder.Update(this);
        }catch (Exception e){
            System.err.println("@DRillox Works Exception (IN.UpdateProjectStatus) : "+e); e.printStackTrace();
        }
    }

    public static void Update(ProjectBuilder project) throws IOException {
        Retrive();
        String name = project.getProjectName();
        //("Time","TotalNumberOfParts","TotalNumberOfCompletedParts","TotalNumberOfNeededReq","TotalNumberOfAvReq","Part","Cpart","Req","AvReq")
        Data_Names.put("project"+new Date().toString(),name);
        Data_Props.put(name+"Time",String.valueOf(project.getEstimatedAccomplishmentTime()));
        Data_Props.put(name+"TotalNumberOfParts",String.valueOf(project.TotalNumberOfParts));
        Data_Props.put(name+"TotalNumberOfCompletedParts",String.valueOf(project.TotalNumberOfCompletedParts));
        Data_Props.put(name+"TotalNumberOfNeededReq",String.valueOf(project.TotalNumberOfNeededReq));
        Data_Props.put(name+"TotalNumberOfAvReq",String.valueOf(project.TotalNumberOfAvReq));
        for (int i = 0; i<project.TotalNumberOfParts;i++){
            Data_Props.put(name+"Part"+String.valueOf(i),project.getProjectParts().get(i));
        }
        for (int i = 0; i<project.TotalNumberOfCompletedParts;i++){
            Data_Props.put(name+"Cpart"+String.valueOf(i),project.getProjectCompletedParts().get(i));
        }
        for (int i= 0; i<project.TotalNumberOfNeededReq;i++){
            Data_Props.put(name+"Req"+String.valueOf(i),project.getNeededRequirements().get(i));
        }
        for (int i=0; i<project.TotalNumberOfAvReq;i++){
            Data_Props.put(name+"AvReq"+String.valueOf(i),project.getAvailableRequirements().get(i));
        }


        //saving of the data
        if (!AllProjects.containsKey(project.getProjectName())) {
            Data_Names.store(new FileOutputStream(DataNames),"@Drillox Works Data Saving [CodTech]");
            Data_Props.store(new FileOutputStream(DataProps),"@Drillox Works Data Saving [CodTech]");
            AllProjects.put(project.getProjectName(), project);
        }else Data_Props.store(new FileOutputStream(DataProps),"@Drillox Works Data Saving [CodTech]");

        Retrive();
    }
    public static void Retrive() throws IOException {
        //responsible for retrving and assembling of the data in an object format
        ProjectBuilder project;
        Data_Names.load(new FileInputStream(DataNames));
        Data_Props.load(new FileInputStream(DataProps));
        Set<Object> ProjNameKeys = Data_Names.keySet();
        Set<Object> PropKeys = Data_Props.keySet();
        ArrayList<String> projectNames = new ArrayList<>();

        ArrayList<String> parts = new ArrayList<>();
        ArrayList<String> cparts = new ArrayList<>();
        ArrayList<String> Reqs = new ArrayList<>();
        ArrayList<String> AvReqs = new ArrayList<>();
        int TotalNumberofparts = 0;
        int TotalNumberofcparts = 0;
        int TotalNumberofneededReq = 0;
        int TotalNumberofAvReq = 0;
        int Time =0;
        //("Time","TotalNumberOfParts","TotalNumberOfCompletedParts","TotalNumberOfNeededReq","TotalNumberOfAvReq","Part","Cpart","Req","AvReq")

        for (Object name:ProjNameKeys){
            projectNames.add(Data_Names.get(name).toString());
        }

        String key1 = null;
        for (String name: projectNames){
            for (Object key:PropKeys){
                //System.err.println("!!!!!!!!!!!Keys: "+key);
                if (key.equals(name+"Time")) Time = Integer.parseInt(Data_Props.get(key).toString());
                if (key.equals(name+"TotalNumberOfParts")) {
                    //System.out.println("_____________Was called to initialize Totalparts: "+key+" -- - - -"+Data_Props.get(key));
                    TotalNumberofparts = Integer.parseInt(Data_Props.get(key).toString());
                    //System.out.println(" ####value : "+TotalNumberofparts);
                }
                if (key.equals(name+"TotalNumberOfCompletedParts")) TotalNumberofcparts = Integer.parseInt(Data_Props.get(key).toString());
                if (key.equals(name+"TotalNumberOfNeededReq")) TotalNumberofneededReq = Integer.parseInt(Data_Props.get(key).toString());
                if (key.equals(name+"TotalNumberOfAvReq")) TotalNumberofAvReq = Integer.parseInt(Data_Props.get(key).toString());
                int q= (int)TotalNumberofparts;
                for (int i=0; i<Integer.parseInt(Data_Props.get(name+"TotalNumberOfParts").toString()); i++){
                    if (key.equals(name+"Part"+String.valueOf(i)))parts.add(Data_Props.get(key).toString());;
                }
                for (int i = 0; i<TotalNumberofcparts;i++){
                    if (key.equals(name+"Cpart"+String.valueOf(i))) cparts.add(Data_Props.get(key).toString());
                }
                for (int i=0; i<TotalNumberofneededReq; i++){
                    if (key.equals(name+"Req"+String.valueOf(i))) Reqs.add(Data_Props.get(key).toString());
                }
                for (int i =0; i<Integer.parseInt(String.valueOf(Data_Props.get(name+"TotalNumberOfNeededReq").toString())); i++){
                    if (key.equals(name+"AvReq"+String.valueOf(i))) {AvReqs.add(Data_Props.get(key).toString());
                    //System.err.println(name+"_-__-____-___-_______--___-___--__ "+key+" ----- "+TotalNumberofAvReq);
                        }
                }
            }

            System.out.println("++++++++++++++++++>>>>> "+TotalNumberofparts);
            //creation of the object
            project = new ProjectBuilder();
            project.ProjectName = name;
            project.TotalNumberOfAvReq = TotalNumberofAvReq;
            project.TotalNumberOfNeededReq = TotalNumberofneededReq;
            project.TotalNumberOfParts = TotalNumberofparts;
            project.TotalNumberOfCompletedParts = TotalNumberofcparts;
            project.ProjectParts = parts;
            project.EstimatedAccomplishmentTime = Time;
            project.NeededRequirements = Reqs;
            project.AvailableRequirements = AvReqs;
            project.ProjectCompletedParts = cparts;
/*
            System.out.println("Total number of available requirements---> "+project.getAvailableRequirements().size()+" : "+TotalNumberofAvReq);
            System.out.println("Total number of requirements ---> "+project.getNeededRequirements().size());
            System.out.println("Total number of parts ---> "+TotalNumberofparts+" <> "+project.getProjectParts().size());
            System.out.println("Total number of completed parts ---> "+project.getProjectCompletedParts().size());
            System.out.println("Project name : "+project.getProjectName()+" --- - -- - - ");
            System.out.println("propkeys contains :: "+PropKeys.contains(name+"Part"+String.valueOf(0)));
*/
            cparts = new ArrayList<>();
            parts = new ArrayList<>();
            AvReqs = new ArrayList<>();
            Reqs = new ArrayList<>();

            calledByRetrive = true;
            AllProjects.put(name,project);
            project.UpdateProjectStatus();
            calledByRetrive = false;
        }


    }
}