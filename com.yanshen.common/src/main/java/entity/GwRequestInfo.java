package entity;

import java.time.LocalDateTime;

/**
 * @Auther: taohaifeng
 * @Date: 2019/1/25 14:20
 * @Description:
 */
public class GwRequestInfo {
	private String id; // uuid
	private String ip; // 访问 IP
	private String URI; // 请求 uri
	private LocalDateTime startTime; // 开始时间
	private LocalDateTime recordTime; // 记录时间
	private long costTime; // 请求花费时间
	private long length;

	private String content; // 请求参数

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getURI() {
		return URI;
	}

	public void setURI(String URI) {
		this.URI = URI;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public LocalDateTime getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(LocalDateTime recordTime) {
		this.recordTime = recordTime;
	}

	public long getCostTime() {
		return costTime;
	}

	public void setCostTime(long costTime) {
		this.costTime = costTime;
	}

	public long getLength() {
		return length;
	}

	public void setLength(long length) {
		this.length = length;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "GwRequestInfo [id=" + id + ", ip=" + ip + ", URI=" + URI + ", startTime=" + startTime + ", recordTime="
				+ recordTime + ", costTime=" + costTime + ", content=" + content + ", length=" + length + "]";
	}
}