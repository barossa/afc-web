package by.epam.afc.dao.entity;

public class Dialog {
    private int id;
    private int announcementId;
    private boolean visible;
    private Type type;

    public Dialog(int id, int announcementId, boolean visible, Type type) {
        this.id = id;
        this.announcementId = announcementId;
        this.visible = visible;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAnnouncementId() {
        return announcementId;
    }

    public void setAnnouncementId(int announcementId) {
        this.announcementId = announcementId;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public enum Type {
        PRIVATE,
        ANNOUNCEMENT
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Dialog dialog = (Dialog) o;

        if (id != dialog.id) return false;
        if (announcementId != dialog.announcementId) return false;
        return visible == dialog.visible;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + announcementId;
        result = 31 * result + (visible ? 1 : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Dialog{" +
                "id=" + id +
                ", announcementId=" + announcementId +
                ", visible=" + visible +
                ", type=" + type +
                '}';
    }

    public static DialogBuilder getBuilder() {
        return new DialogBuilder();
    }

    public static class DialogBuilder {
        private int nestedId;
        private int nestedAnnouncementId;
        private boolean nestedVisible;
        private Type nestedType;

        private DialogBuilder() {
        }

        public DialogBuilder id(int id) {
            nestedId = id;
            return this;
        }

        public DialogBuilder announcementId(int announcementId) {
            nestedAnnouncementId = announcementId;
            return this;
        }

        public DialogBuilder visible(boolean visible) {
            nestedVisible = visible;
            return this;
        }

        public DialogBuilder type(Type type) {
            nestedType = type;
            return this;
        }

        public Dialog build() {
            return new Dialog(nestedId, nestedAnnouncementId, nestedVisible, nestedType);
        }

    }

}
