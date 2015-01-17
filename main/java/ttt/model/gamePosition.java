package ttt.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "gamePosition")
public class gamePosition {
	@Id
	@GeneratedValue
	private Integer id;
	@ManyToOne
	private gameRecord gameRefId;
	
	private int i;
	private int j;
	private boolean xPosition;
	private boolean oPosition;



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	

	public int getI() {
		return i;
	}

	public void setI(int i) {
		this.i = i;
	}

	public int getJ() {
		return j;
	}

	public void setJ(int j) {
		this.j = j;
	}


	public gameRecord getGameRefId() {
		return gameRefId;
	}

	public void setGameRefId(gameRecord gameRefId) {
		this.gameRefId = gameRefId;
	}

	public boolean isxPosition() {
		return xPosition;
	}

	public void setxPosition(boolean xPosition) {
		this.xPosition = xPosition;
	}

	public boolean isoPosition() {
		return oPosition;
	}

	public void setoPosition(boolean oPosition) {
		this.oPosition = oPosition;
	}




}
