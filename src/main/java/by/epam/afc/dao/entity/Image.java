package by.epam.afc.dao.entity;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

public class Image {
    public static final int UNDEFINED_IMAGE_ID = -1;

    private int id;
    private LocalDateTime uploadData;
    private int uploadedByUserId;
    private BufferedImage image;

    public Image(int id, LocalDateTime uploadData, int uploadedByUserId, BufferedImage image) {
        this.id = id;
        this.uploadData = uploadData;
        this.uploadedByUserId = uploadedByUserId;
        this.image = image;
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

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public static ImageBuilder getBuilder() {
        return new ImageBuilder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Image image1 = (Image) o;

        if (id != image1.id) return false;
        if (uploadedByUserId != image1.uploadedByUserId) return false;
        if (uploadData != null ? !uploadData.equals(image1.uploadData) : image1.uploadData != null) return false;
        return image != null ? image.equals(image1.image) : image1.image == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (uploadData != null ? uploadData.hashCode() : 0);
        result = 31 * result + uploadedByUserId;
        result = 31 * result + (image != null ? image.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", uploadData=" + uploadData +
                ", uploadedByUserId=" + uploadedByUserId +
                ", image=" + image +
                '}';
    }

    public static class ImageBuilder {
        private int nestedId = UNDEFINED_IMAGE_ID;
        private LocalDateTime nestedUploadData;
        private int nestedUploadedByUserId;
        private BufferedImage nestedImage;

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

        public ImageBuilder image(BufferedImage image) {
            nestedImage = image;
            return this;
        }

        public Image build() {
            return new Image(nestedId, nestedUploadData, nestedUploadedByUserId, nestedImage);
        }
    }
}
