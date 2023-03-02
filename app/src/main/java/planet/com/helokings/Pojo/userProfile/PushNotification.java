package planet.com.helokings.Pojo.userProfile;

import com.google.gson.annotations.SerializedName;

public class PushNotification {

	@SerializedName("comments")
	private String comments;

	@SerializedName("video_updates")
	private String videoUpdates;

	@SerializedName("mentions")
	private String mentions;

	@SerializedName("id")
	private String id;

	@SerializedName("new_followers")
	private String newFollowers;

	@SerializedName("direct_messages")
	private String directMessages;

	@SerializedName("likes")
	private String likes;

	public String getComments(){
		return comments;
	}

	public String getVideoUpdates(){
		return videoUpdates;
	}

	public String getMentions(){
		return mentions;
	}

	public String getId(){
		return id;
	}

	public String getNewFollowers(){
		return newFollowers;
	}

	public String getDirectMessages(){
		return directMessages;
	}

	public String getLikes(){
		return likes;
	}
}