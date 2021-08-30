package by.epam.afc.dao.entity;

import java.time.LocalDateTime;

public class Message {
    private int id;
    private int dialogId;
    private int senderId;
    private LocalDateTime sentTime;
    private String text;
    private boolean graphicsContent;
    private Image image;

    public Message(int id, int dialogId, int senderId, LocalDateTime sentTime, String text,
                   boolean graphicsContent, Image image) {
        this.id = id;
        this.dialogId = dialogId;
        this.senderId = senderId;
        this.sentTime = sentTime;
        this.text = text;
        this.graphicsContent = graphicsContent;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDialogId() {
        return dialogId;
    }

    public void setDialogId(int dialogId) {
        this.dialogId = dialogId;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public LocalDateTime getSentTime() {
        return sentTime;
    }

    public void setSentTime(LocalDateTime sentTime) {
        this.sentTime = sentTime;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isGraphicsContent() {
        return graphicsContent;
    }

    public void setGraphicsContent(boolean graphicsContent) {
        this.graphicsContent = graphicsContent;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public static MessageBuilder getBuilder() {
        return new MessageBuilder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;

        if (id != message.id) return false;
        if (dialogId != message.dialogId) return false;
        if (senderId != message.senderId) return false;
        if (graphicsContent != message.graphicsContent) return false;
        if (sentTime != null ? !sentTime.equals(message.sentTime) : message.sentTime != null) return false;
        if (text != null ? !text.equals(message.text) : message.text != null) return false;
        return image != null ? image.equals(message.image) : message.image == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + dialogId;
        result = 31 * result + senderId;
        result = 31 * result + (sentTime != null ? sentTime.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (graphicsContent ? 1 : 0);
        result = 31 * result + (image != null ? image.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", dialogId=" + dialogId +
                ", senderId=" + senderId +
                ", sentTime=" + sentTime +
                ", text='" + text + '\'' +
                ", graphicsContent=" + graphicsContent +
                ", image=" + image +
                '}';
    }

    public static class MessageBuilder {
        private int nestedId;
        private int nestedDialogId;
        private int nestedSenderId;
        private LocalDateTime nestedSentTime;
        private String nestedText;
        private boolean nestedGraphicsContent;
        private Image nestedImage;

        private MessageBuilder() {
        }

        public MessageBuilder id(int id) {
            nestedId = id;
            return this;
        }

        public MessageBuilder dialogId(int dialogId) {
            nestedDialogId = dialogId;
            return this;
        }

        public MessageBuilder senderId(int senderId) {
            nestedSenderId = senderId;
            return this;
        }

        public MessageBuilder sentTime(LocalDateTime sentTime) {
            nestedSentTime = sentTime;
            return this;
        }

        public MessageBuilder text(String text) {
            nestedText = text;
            return this;
        }

        public MessageBuilder graphicsContent(boolean graphicsContent) {
            nestedGraphicsContent = graphicsContent;
            return this;
        }

        public MessageBuilder image(Image image) {
            nestedImage = image;
            return this;
        }

        public Message build() {
            return new Message(nestedId, nestedDialogId, nestedSenderId, nestedSentTime,
                    nestedText, nestedGraphicsContent, nestedImage);
        }

    }

}
