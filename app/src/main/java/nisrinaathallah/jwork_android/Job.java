package nisrinaathallah.jwork_android;
/**
 * @author Nisrina Athallah - 1806148813
 * @version 27-06-2021
 */

/**
 * inisiasi class pada Job
 */
public class Job {
    private int id; //id dari Job//
    private String name; //nama dari Job//
    private Recruiter recruiter; //recruiter dari Job//
    private int fee; //upah dari Job//
    private String category; //kategori dari Job//

    /**
     * constructor pada Job
     * @param id
     * @param name
     * @param recruiter
     * @param fee
     * @param category
     */
    public Job(int id, String name, Recruiter recruiter, int fee, String category){
        this.id = id;
        this.name = name;
        this.recruiter = recruiter;
        this.fee = fee;
        this.category = category;
    }

    /**
     * akses id dari Job
     * @return id dari Job
     */
    public int getId(){
        return this.id;
    }

    /**
     * akses nama dari Job
     * @return nama dari Job
     */
    public String getName(){
        return this.name;
    }

    /**
     * akses recruiter dari Job
     * @return recruiter dari Job
     */
    public Recruiter getRecruiter(){
        return this.recruiter;
    }

    /**
     * akses upah dari Job
     * @return upah dari Job
     */
    public int getFee(){
        return this.fee;
    }

    /**
     * akses kategori dari Job
     * @return kategori dari Job
     */
    public String getCategory(){
        return this.category;
    }

    /**
     * mutasi id dari Job
     * @param id dari Job
     */
    public void setId(int id){
        this.id = id;
    }

    /**
     * mutasi nama dari Job
     * @param name dari Job
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * mutasi upah dari Job
     * @param fee dari Job
     */
    public void setFee(int fee){
        this.fee = fee;
    }

    /**
     * mutasi recruiter dari Job
     * @param recruiter dari Job
     */
    public void setRecruiter(Recruiter recruiter){
        this.recruiter = recruiter;
    }

    /**
     * mutasi kategori dari Job
     * @param category dari Job
     */
    public void setCategory(String category){
        this.category = category;
    }
}