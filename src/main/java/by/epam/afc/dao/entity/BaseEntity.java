package by.epam.afc.dao.entity;

public abstract class BaseEntity {
    public static final int UNDEFINED_ID = -1;

    protected int id;

    public BaseEntity(){
        id = UNDEFINED_ID;
    }

    public BaseEntity(int id){
        this.id = id;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }
    // TODO: 9/21/21 EQUALS HASHCODE
}
