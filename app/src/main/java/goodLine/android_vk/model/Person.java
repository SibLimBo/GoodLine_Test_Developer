package goodLine.android_vk.model;
public class Person {
    private String firstName;
    private String lastName;
    private String photoID;
    private String photoid400;
    private int sex;
    private String screenname;
    private int relation;
    private boolean statusOnline;
    private int id;

    public Person(String firstName, String lastName,String photoID, String photoid400, int sex, String screenname, int relation, int id){
 // public Person(String firstName, String lastName,String photoID, String photoid400, int sex, int relation, int id){
        this.firstName=firstName;
        this.lastName=lastName;
        this.photoID=photoID;
        this.photoid400=photoid400;
        this.screenname=screenname;
        this.sex=sex;
        this.relation=relation;
        this.statusOnline=statusOnline;
        this.id=id;
    }
    public int getID() {
        return id;
    }

    public int getSex(){return sex;}

    public int getRelation(){return relation;}

    public String getLastName() {
        return lastName;
    }

    public String getScreenname(){return screenname;}

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPhotoID() {
        return photoID;
    }

    public String getPhotoID400() {return photoid400;}

    public void setPhotoID(String photoID) {
        this.photoID = photoID;
    }

   public boolean isOnline(){
        return statusOnline;
    }

    public void setStatusOnline(boolean status){
        this.statusOnline=status;
    }


}
