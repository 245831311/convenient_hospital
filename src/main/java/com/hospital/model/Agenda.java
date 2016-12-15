package com.hospital.model;

import java.io.Serializable;

import org.springframework.format.annotation.DateTimeFormat;

public class Agenda implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 3888579080806780513L;

	private String agendaId;

    private String meetingId;

    @DateTimeFormat(pattern = "HH:mm")
    private Long time;

    private String content;

    private String staff;
    
    //extend
    private String formatTime;

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getStaff() {
        return staff;
    }

    public void setStaff(String staff) {
        this.staff = staff == null ? null : staff.trim();
    }

	public String getAgendaId() {
		return agendaId;
	}

	public void setAgendaId(String agendaId) {
		this.agendaId = agendaId;
	}

	public String getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(String meetingId) {
		this.meetingId = meetingId;
	}

	public String getFormatTime() {
		return formatTime;
	}

	public void setFormatTime(String formatTime) {
		this.formatTime = formatTime;
	}
}