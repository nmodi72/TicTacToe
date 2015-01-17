package ttt.model;

import java.util.Date;

public class gameHistory {

	String opponent;
	String difference;
	String result;

	public gameHistory(String opponent, String difference, String result) {
		this.opponent = opponent;
		this.difference = difference;
		this.result = result;

	}

	public String getOpponent() {
		return opponent;
	}

	public void setOpponent(String opponent) {
		this.opponent = opponent;
	}

	public String getDifference() {
		return difference;
	}

	public void setDifference(String difference) {
		this.difference = difference;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}
