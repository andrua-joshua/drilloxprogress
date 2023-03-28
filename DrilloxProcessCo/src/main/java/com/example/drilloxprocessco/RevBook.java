package com.example.drilloxprocessco;

import java.io.*;
import java.util.*;

public class RevBook implements Cloneable {
    //private fields
    private String BookTitle;
    private int TotalNumberOfChapters;
    private int TotalnumberOfPages;
    private HardnessConstant HARDNESS_CONTANT;
    private int AverageChapterPages;
    private Category CATEGORY_CONSTANT;
    private int InitialProgressPage;
    private int InitialProgressChapter;
    private int EstimatedAccomplishmentPeriod;
    private int EstimatedRemainingPeriod;
    private float LapProgress;
    private float OverallProgress;
    private int LaplyEfficience;
    private float OverallBookavg;
    private float estimateChapterLap; //three hour laps
    private static ArrayList<RevBook> Books = new ArrayList<>();
    private static TreeMap<String,RevBook> AllBooks = new TreeMap<>();
    public static File DataTitle = new File(System.getProperty("user.home"),".DataTitle.bat");
    public static File DataProp = new File(System.getProperty("user.home"),".DataProp.bat");
    private static Properties Data_title = new Properties(); //used for getting and saving of the books titles
    private static Properties Data_Props = new Properties(); //used for getting and saving of the books Properties

    private static boolean CalledByRetrive = false;


    

    //public methods
    public RevBook(String BookTitle, int TotalChapters, int TotalPages, HardnessConstant Hardness, Category category,int lCP, int hCP, int mCP) throws CloneNotSupportedException, IOException {
        this.creatRevBook(BookTitle, TotalChapters, TotalPages, Hardness, category, lCP, hCP, mCP);
    }
    private RevBook(){
        //expected to only be used by the retrive method
    }

    private void creatRevBook(String bookTitle, int TotalChapters, int TotalPages, HardnessConstant Hardness, Category category, int lCP, int hCP, int mCP) throws CloneNotSupportedException {
        this.setBookTitle(bookTitle);
        this.setHARDNESS_CONTANT(Hardness);
        this.setCATEGORY_CONSTANT(category);
        if(TotalChapters>0){this.TotalNumberOfChapters = TotalChapters;}
        if(TotalPages>0){this.TotalnumberOfPages = TotalPages;}
        if (TotalChapters>0 && TotalPages>0){
            this.OverallBookavg = (int)((this.getTotalNumberOfChapters()+this.getTotalnumberOfPages())/2);
        }
        this.AverageChapterPages = (lCP+hCP+mCP)/3;
        if(this.getAverageChapterPages()<19) this.estimateChapterLap = 1;
        else if (this.getAverageChapterPages()<27) this.estimateChapterLap = 2;
        else if (this.getAverageChapterPages()<37) this.estimateChapterLap = 3;
        else if (this.getAverageChapterPages()<47) this.estimateChapterLap = 4;
        else if (this.getAverageChapterPages()<57) this.estimateChapterLap = 5;
        else if (this.getAverageChapterPages()<67) this.estimateChapterLap = 6;
        else this.estimateChapterLap = 7;

        int laps = 0; double hardness = 0,ctgry = 0;
        switch (this.HARDNESS_CONTANT){
            case EASY -> hardness =1;
            case HARD -> hardness =2;
            case MEDIUM -> hardness =1.5;
        }
        switch (this.CATEGORY_CONSTANT){
            case THEORY -> ctgry = 1;
            case PRACTICAL -> ctgry =2;
        }
        double v = this.estimateChapterLap * hardness * ctgry;
        this.EstimatedAccomplishmentPeriod = (int) ((int)this.getTotalNumberOfChapters()*(v));

        try{
            RevBook.Books.add(this);
            RevBook.Update(this);
        }catch(IOException e){
            System.out.println("@Drillox Exceptions(IN.creatBook) : "+e);
        }
    }

    public String getBookTitle(){return this.BookTitle;}
    public int getTotalNumberOfChapters(){return this.TotalNumberOfChapters;}
    public int getTotalnumberOfPages(){return this.TotalnumberOfPages;}
    public int getAverageChapterPages(){return this.AverageChapterPages;}
    public int getInitialProgressPage(){return this.InitialProgressPage;}
    public int getInitialProgressChapter(){return this.InitialProgressChapter;}
    public HardnessConstant getHARDNESS_CONTANT(){return this.HARDNESS_CONTANT;}
    public Category getCATEGORY_CONSTANT(){return this.CATEGORY_CONSTANT;}
    public float getLapProgress(){return this.LapProgress;}
    public float getOverallProgress(){return this.OverallProgress;}
    public int getEstimatedAccomplishmentPeriod(){return this.EstimatedAccomplishmentPeriod;}
    public int getEstimatedRemainingPeriod(){return this.EstimatedRemainingPeriod;}
    public static ArrayList<RevBook> getBooks(){return RevBook.Books;}
    public static RevBook getBook(String BookTitle){
        RevBook [] books = (RevBook[]) Books.toArray();
        boolean found = false;
        for (RevBook b: books){
            if(b.getBookTitle()==BookTitle){ found=true; return b;}
        }
        if(!found) System.out.println("..Book with Title: "+BookTitle+" Not found.");
        return null;
    }
    public static TreeMap<String, RevBook> getAllBooks(){return AllBooks;}
    public void setBookTitle(String bookTitle){
        if(bookTitle!=null) this.BookTitle = bookTitle;
        this.UpdateBookStatus();
    }
    public void setHARDNESS_CONTANT(HardnessConstant Hardness){
        if(Hardness!=null) this.HARDNESS_CONTANT = Hardness;
        this.UpdateBookStatus();
    }
    public void setInitialProgressPage(int newPage){
        if(newPage>this.InitialProgressPage) this.InitialProgressPage = newPage;
        this.UpdateBookStatus();
    }
    public void setInitialProgressChapter(int newChapter){
        if(newChapter>this.InitialProgressChapter) this.InitialProgressChapter = newChapter;
        this.UpdateBookStatus();
    }
    public void setCATEGORY_CONSTANT(Category category){
        this.CATEGORY_CONSTANT = category;
        this.UpdateBookStatus();
    }
    public boolean DeleteBook(){
        boolean removed = false;
        int it = RevBook.Books.toArray().length;
        for (int i=0; i<it; i++){
            if(RevBook.Books.get(i).getBookTitle() == this.getBookTitle()){
                RevBook.Books.remove(i);
                removed = true;
            }
        }
        return removed;
    }
    public void UpdateBookStatus(){
        int EstimatedTime;
        double hardness, category;
        float intiProgress = this.OverallProgress;
        float LapProgress;
        float OverallProgress;

        OverallProgress = ((this.InitialProgressChapter+this.InitialProgressPage)/(this.OverallBookavg*2))*100;
        this.OverallProgress = OverallProgress;
        LapProgress = OverallProgress-intiProgress;
        this.LapProgress = LapProgress;
        //System.out.println(this.getBookTitle()+" : OverallP : "+this.getOverallProgress()+"    : this.lapprogress-> "+this.LapProgress+" ::::: {Initial progress}-> "+intiProgress);

        EstimatedTime = (int) (this.getEstimatedAccomplishmentPeriod()-((this.getEstimatedAccomplishmentPeriod() * OverallProgress)/100));
        this.EstimatedAccomplishmentPeriod = EstimatedTime;

        try{
            if (!RevBook.CalledByRetrive)RevBook.Update(this);
        }catch (IOException e){
            System.out.println("@Drillox Exceptions(IN.BookStatusUpdate) : "+e);
        }
    }

    public static void Update(RevBook book) throws IOException {
        //String[] Prop_names = {"TotalPages","TotalChapters","PPage","PChapter","lcp","mcp","AvgPage","hcp","Hardness","Category","LastLapProgress"};
        //check for the availability of the files for data storage
        Retrive();
        if (book!=null)Data_title.put("book"+new Date().toString(),book.getBookTitle());
        Data_Props.put(book.getBookTitle()+"TotalChapters", String.valueOf(book.TotalNumberOfChapters));
        Data_Props.put(book.getBookTitle()+"TotalPages", String.valueOf(book.TotalnumberOfPages));
        Data_Props.put(book.getBookTitle()+"PPage", String.valueOf(book.InitialProgressPage));
        Data_Props.put(book.getBookTitle()+"PChapter", String.valueOf(book.InitialProgressChapter));
        Data_Props.put(book.getBookTitle()+"AvgPage", String.valueOf(book.AverageChapterPages));
        Data_Props.put(book.getBookTitle()+"LastLapProgress", String.valueOf(book.LapProgress));
        Data_Props.put(book.getBookTitle()+"Category", String.valueOf(book.getCATEGORY_CONSTANT()));
        Data_Props.put(book.getBookTitle()+"Hardness", String.valueOf(book.getHARDNESS_CONTANT()));
        Data_Props.put(book.getBookTitle()+"OverallProgress", String.valueOf(book.getOverallProgress()));

        //saving of the data
       if (!AllBooks.containsKey(book.getBookTitle())) {
           Data_title.store(new FileOutputStream(DataTitle), "@Drillox Works Data Saving [CodTech]");
           Data_Props.store(new FileOutputStream(DataProp), "@Drillox Works Data Saving [CodTech]");
           AllBooks.put(book.getBookTitle(), book);
       }else Data_Props.store(new FileOutputStream(DataProp), "@Drillox Works Data Saving [CodTech]");
       Retrive();
    }
    public static void Retrive() throws IOException {
        //String[] Prop_names = {"TotalPages","TotalChapters","PPage","PChapter","lcp","mcp","AvgPage","hcp","Hardness","Category","LastLapProgress"};
         //check for the file availability

        //get the properties from the files and construct the data structure
        Data_title.load(new FileInputStream(DataTitle));
        Data_Props.load(new FileInputStream(DataProp));
        Set<Object> BTitles_keys = Data_title.keySet();
        Set<Object> BProp_keys = Data_Props.keySet();
        ArrayList<String> BTitles = new ArrayList<>();

        //expected book properties
        Category cat = null;
        HardnessConstant hard = null;
        String BkTitle = null;
        int TotalChapters = 0;
        int TotalPages = 0;
        int lCP = 0;
        int hCP = 0;
        int mCP = 0;
        //plus the additional properties
        int AvgPage = 0;
        int PPage = 0;
        int PChapter = 0;
        float LastLapProgress = 0f;
        float OverallProgress = 0f;
        RevBook book = null;

        Iterator iterator = BTitles_keys.iterator();
        while (iterator.hasNext()){
            BTitles.add(Data_title.get(iterator.next()).toString());
        }//String[] Prop_names = {"TotalPages","TotalChapters","PPage","PChapter","lcp","mcp","AvgPage","hcp","Hardness","Category","LastLapProgress"};
        for (String val : BTitles) {
            for (int x = 0; x < BProp_keys.size(); x++) {
                String key = BProp_keys.stream().toArray()[x].toString();
                if ((val + "AvgPage").equals(key)) AvgPage = Integer.parseInt(Data_Props.get(key).toString());
                if ((val + "TotalPages").equals(key)) TotalPages = Integer.parseInt(Data_Props.get(key).toString());
                if ((val + "TotalChapters").equals(key))
                    TotalChapters = Integer.parseInt(Data_Props.get(key).toString());
                if ((val + "PPage").equals(key)) PPage = Integer.parseInt(Data_Props.get(key).toString());
                if ((val + "PChapter").equals(key)) PChapter = Integer.parseInt(Data_Props.get(key).toString());
                if ((val + "lcp").equals(key)) lCP = Integer.parseInt(Data_Props.get(key).toString());
                if ((val + "mcp").equals(key)) mCP = Integer.parseInt(Data_Props.get(key).toString());
                if ((val + "hcp").equals(key)) hCP = Integer.parseInt(Data_Props.get(key).toString());
                if ((val + "LastLapProgress").equals(key))
                    LastLapProgress = Float.parseFloat(Data_Props.get(key).toString());
                if ((val + "OverallProgress").equals(key))
                    OverallProgress = Float.parseFloat(Data_Props.get(key).toString());
                if ((val + "Hardness").equals(key)) {
                    hard = switch (Data_Props.get(key).toString()) {
                        case "HARD" -> HardnessConstant.HARD;
                        case "MEDIUM" -> HardnessConstant.MEDIUM;
                        case "EASY" -> HardnessConstant.EASY;
                        default -> HardnessConstant.MEDIUM;
                    };
                }
                if ((val + "Category").equals(key)) {
                    cat = switch (Data_Props.get(key).toString()) {
                        case "PRACTICAL" -> Category.PRACTICAL;
                        case "THEORY" -> Category.THEORY;
                        default -> Category.PRACTICAL;
                    };
                }
            }
            //creation of the RevBook object
            book = new RevBook();
            book.CATEGORY_CONSTANT = cat;
            book.OverallProgress = OverallProgress;
            book.BookTitle = val;
            book.HARDNESS_CONTANT = hard;
            book.InitialProgressChapter = PChapter;
            book.InitialProgressPage = PPage;
            book.LapProgress = LastLapProgress;
            book.TotalNumberOfChapters = TotalChapters;
            book.TotalnumberOfPages = TotalPages;
            book.OverallBookavg = (int)((book.getTotalNumberOfChapters()+book.getTotalnumberOfPages())/2);
            if (lCP != 0) book.AverageChapterPages = (lCP + hCP + mCP) / 3;
            else book.AverageChapterPages = AvgPage;
            if(book.getAverageChapterPages()<19) book.estimateChapterLap = 1;
            else if (book.getAverageChapterPages()<27) book.estimateChapterLap = 2;
            else if (book.getAverageChapterPages()<37) book.estimateChapterLap = 3;
            else if (book.getAverageChapterPages()<47) book.estimateChapterLap = 4;
            else if (book.getAverageChapterPages()<57) book.estimateChapterLap = 5;
            else if (book.getAverageChapterPages()<67) book.estimateChapterLap = 6;
            else book.estimateChapterLap = 7;

            int laps = 0; double hardness = 0,ctgry = 0;
            switch (book.HARDNESS_CONTANT){
                case EASY -> hardness =1;
                case HARD -> hardness =2;
                case MEDIUM -> hardness =1.5;
            }
            switch (book.CATEGORY_CONSTANT){
                case THEORY -> ctgry = 1;
                case PRACTICAL -> ctgry =2;
            }
            double v = book.estimateChapterLap * hardness * ctgry;
            book.EstimatedAccomplishmentPeriod = (int) ((int)book.getTotalNumberOfChapters()*(v));
            book.EstimatedRemainingPeriod = book.EstimatedAccomplishmentPeriod-((book.EstimatedAccomplishmentPeriod* (int)book.OverallProgress)/100);

            CalledByRetrive = true;
            CalledByRetrive = false;
            AllBooks.put(val, book);
        }
    }

}
