package planet.com.helokings.Model;

public class PostUploadModel {
   String auth_token;
   String description;
   String users_json;
   String hashtags_json;

   public String getAuth_token() {
      return auth_token;
   }

   public void setAuth_token(String auth_token) {
      this.auth_token = auth_token;
   }

   public String getDescription() {
      return description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public String getUsers_json() {
      return users_json;
   }

   public void setUsers_json(String users_json) {
      this.users_json = users_json;
   }

   public String getHashtags_json() {
      return hashtags_json;
   }

   public void setHashtags_json(String hashtags_json) {
      this.hashtags_json = hashtags_json;
   }
}
