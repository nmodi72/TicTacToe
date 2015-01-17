package ttt.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "gameRecord")
public class gameRecord {
	@Id
	@GeneratedValue
	private Integer gameid;

	private boolean isAI;

	@ManyToOne
	private gameUser player1;
	@ManyToOne
	private gameUser player2;
	
	
	private Date startTime;
	private Date endTime;
	private Date savedAt;
	private boolean isTie;
	@ManyToOne
	private gameUser winnerName;
	@ManyToOne
	private gameUser loserName;
	public Integer getGameid() {
		return gameid;
	}
	public void setGameid(Integer gameid) {
		this.gameid = gameid;
	}
	public boolean isAI() {
		return isAI;
	}
	public void setAI(boolean isAI) {
		this.isAI = isAI;
	}
	
	

	public gameUser getPlayer1() {
		return player1;
	}
	public void setPlayer1(gameUser player1) {
		this.player1 = player1;
	}
	public gameUser getPlayer2() {
		return player2;
	}
	public void setPlayer2(gameUser player2) {
		this.player2 = player2;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public boolean isTie() {
		return isTie;
	}
	public void setTie(boolean isTie) {
		this.isTie = isTie;
	}
	public gameUser getWinnerName() {
		return winnerName;
	}
	public void setWinnerName(gameUser winnerName) {
		this.winnerName = winnerName;
	}
	public gameUser getLoserName() {
		return loserName;
	}
	public void setLoserName(gameUser loserName) {
		this.loserName = loserName;
	}
	public Date getSavedAt() {
		return savedAt;
	}
	public void setSavedAt(Date savedAt) {
		this.savedAt = savedAt;
	}

}
