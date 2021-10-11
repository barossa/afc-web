package by.epam.afc.dao.entity;

public abstract class BaseEntity implements Comparable<BaseEntity> {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaseEntity that = (BaseEntity) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public int compareTo(BaseEntity o) {
        return Integer.compare(id, o.getId());
    }
}
