package entity;

/**
 * @Auther: taohaifeng
 * @Date: 2019/1/25 14:20
 * @Description:
 */
public class GwVoteRequestInfo extends GwRequestInfo {

	private String activityid;
	private String signuprecordid;

	public String getActivityid() {
		return activityid;
	}

	public void setActivityid(String activityid) {
		this.activityid = activityid;
	}

	public String getSignuprecordid() {
		return signuprecordid;
	}

	public void setSignuprecordid(String signuprecordid) {
		this.signuprecordid = signuprecordid;
	}
}