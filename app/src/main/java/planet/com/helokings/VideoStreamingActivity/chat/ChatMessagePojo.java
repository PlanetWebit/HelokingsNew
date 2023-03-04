package planet.com.helokings.VideoStreamingActivity.chat;

public class ChatMessagePojo {
    String model_id;
    String user_id;
    String name;
    String text;
    String message;
    String image;
    String frame;
    String type;

    public ChatMessagePojo(String model_id, String user_id,String name, String text, String message, String image, String frame, String type) {
        this.model_id = model_id;
        this.user_id = user_id;
        this.name = name;
        this.text = text;
        this.message = message;
        this.image = image;
        this.frame = frame;
        this.type = type;
    }

    public String getModel_id() {
        return model_id;
    }
    public String getFrame() {
        return frame;
    }
    public String getUser_id() {
        return user_id;
    }

    public void setModel_id(String model_id) {
        this.model_id = model_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
