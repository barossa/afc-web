package by.epam.afc.dao.entity;

import java.time.LocalDateTime;

public class Image {
    public static final int UNDEFINED_IMAGE_ID = -1;

    private int id;
    private LocalDateTime uploadData;
    private int uploadedByUserId;
    private String base64;

    public Image(int id, LocalDateTime uploadData, int uploadedByUserId, String base64) {
        this.id = id;
        this.uploadData = uploadData;
        this.uploadedByUserId = uploadedByUserId;
        this.base64 = base64;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getUploadData() {
        return uploadData;
    }

    public void setUploadData(LocalDateTime uploadData) {
        this.uploadData = uploadData;
    }

    public int getUploadedByUserId() {
        return uploadedByUserId;
    }

    public void setUploadedByUserId(int uploadedByUserId) {
        this.uploadedByUserId = uploadedByUserId;
    }

    public String getBase64() {
        return base64;
    }

    public void setImage(String base64) {
        this.base64 = base64;
    }

    public static ImageBuilder getBuilder() {
        return new ImageBuilder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Image image = (Image) o;

        if (uploadedByUserId != image.uploadedByUserId) return false;
        if (uploadData != null ? !uploadData.equals(image.uploadData) : image.uploadData != null) return false;
        return base64 != null ? base64.equals(image.base64) : image.base64 == null;
    }

    @Override
    public int hashCode() {
        int result = uploadData != null ? uploadData.hashCode() : 0;
        result = 31 * result + uploadedByUserId;
        result = 31 * result + (base64 != null ? base64.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", uploadData=" + uploadData +
                ", uploadedByUserId=" + uploadedByUserId +
                ", base64=" +
                (base64.isEmpty() ? "empty" : "exists...") +
                '}';
    }

    public static class ImageBuilder {
        private int nestedId = UNDEFINED_IMAGE_ID;
        private LocalDateTime nestedUploadData;
        private int nestedUploadedByUserId;
        private String nestedBase64;

        public ImageBuilder id(int id) {
            nestedId = id;
            return this;
        }

        public ImageBuilder uploadData(LocalDateTime uploadData) {
            nestedUploadData = uploadData;
            return this;
        }

        public ImageBuilder uploadedByUser(int uploadedByUserId) {
            nestedUploadedByUserId = uploadedByUserId;
            return this;
        }

        public ImageBuilder base64(String base64) {
            nestedBase64 = base64;
            return this;
        }

        public Image build() {
            return new Image(nestedId, nestedUploadData, nestedUploadedByUserId, nestedBase64);
        }
    }
}
