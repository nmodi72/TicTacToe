package ttt.model;

public class Gamedata {
	int i;
	int j;
	boolean X;
	boolean O;
	int idref;

	public Gamedata() {

	}

	public Gamedata(int i, int j, boolean X, boolean O) {
		this.i = i;
		this.j = j;
		this.X = X;
		this.O = O;
	}

	public Gamedata(int idref) {
		this.idref = idref;
	}

	public int getIdref() {
		return idref;
	}

	public void setIdref(int idref) {
		this.idref = idref;
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

	public boolean isX() {
		return X;
	}

	public void setX(boolean x) {
		X = x;
	}

	public boolean isO() {
		return O;
	}

	public void setO(boolean o) {
		O = o;
	}

}
