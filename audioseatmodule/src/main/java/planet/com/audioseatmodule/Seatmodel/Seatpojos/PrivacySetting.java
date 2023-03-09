package planet.com.audioseatmodule.Seatmodel.Seatpojos;

import com.google.gson.annotations.SerializedName;

public class PrivacySetting{

	@SerializedName("liked_videos")
	private String likedVideos;

	@SerializedName("videos_download")
	private String videosDownload;

	@SerializedName("video_comment")
	private String videoComment;

	@SerializedName("videos_repost")
	private String videosRepost;

	@SerializedName("id")
	private String id;

	@SerializedName("direct_message")
	private String directMessage;

	@SerializedName("duet")
	private String duet;

	public String getLikedVideos(){
		return likedVideos;
	}

	public String getVideosDownload(){
		return videosDownload;
	}

	public String getVideoComment(){
		return videoComment;
	}

	public String getVideosRepost(){
		return videosRepost;
	}

	public String getId(){
		return id;
	}

	public String getDirectMessage(){
		return directMessage;
	}

	public String getDuet(){
		return duet;
	}
}