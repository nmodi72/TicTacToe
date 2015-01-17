package ttt.model.service;

import java.util.ArrayList;

public class Winmove {
	String message;

	public Winmove() {

	}

	public String Winmessage(ArrayList<Integer> Xpos, ArrayList<Integer> Opos) {

		if ((Xpos.contains(0) && Xpos.contains(1) && Xpos.contains(2))
				|| (Xpos.contains(3) && Xpos.contains(4) && Xpos.contains(5))
				|| (Xpos.contains(6) && Xpos.contains(7) && Xpos.contains(8))
				|| (Xpos.contains(0) && Xpos.contains(3) && Xpos.contains(6))
				|| (Xpos.contains(1) && Xpos.contains(4) && Xpos.contains(7))
				|| (Xpos.contains(2) && Xpos.contains(5) && Xpos.contains(8))
				|| (Xpos.contains(0) && Xpos.contains(4) && Xpos.contains(8))
				|| (Xpos.contains(2) && Xpos.contains(4) && Xpos.contains(6))) {
			/* System.out.println("Player won..!!"); */
			message = "You won..!!";

		} else if ((Opos.contains(0) && Opos.contains(1) && Opos.contains(2))
				|| (Opos.contains(3) && Opos.contains(4) && Opos.contains(5))
				|| (Opos.contains(6) && Opos.contains(7) && Opos.contains(8))
				|| (Opos.contains(0) && Opos.contains(3) && Opos.contains(6))
				|| (Opos.contains(1) && Opos.contains(4) && Opos.contains(7))
				|| (Opos.contains(2) && Opos.contains(5) && Opos.contains(8))
				|| (Opos.contains(0) && Opos.contains(4) && Opos.contains(8))
				|| (Opos.contains(2) && Opos.contains(4) && Opos.contains(6))) {
			/* System.out.println("Computer won..!!"); */
			message = "I won..!!";

		} else if ((Opos.contains(0) || Xpos.contains(0))
				&& (Opos.contains(1) || Xpos.contains(1))
				&& (Opos.contains(2) || Xpos.contains(2))
				&& (Opos.contains(3) || Xpos.contains(3))
				&& (Opos.contains(4) || Xpos.contains(4))
				&& (Opos.contains(5) || Xpos.contains(5))
				&& (Opos.contains(6) || Xpos.contains(6))
				&& (Opos.contains(7) || Xpos.contains(7))
				&& (Opos.contains(8) || Xpos.contains(8))) {
			/* System.out.println("Computer won..!!"); */
			message = "Match Tied..!";

		}
		return message;
	}

}
