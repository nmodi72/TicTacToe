package ttt.web.controller;

import java.nio.channels.SeekableByteChannel;
import java.security.Principal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.taglibs.standard.extra.spath.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.async.DeferredResult;

import ttt.model.Gamedata;
import ttt.model.gameHistory;
import ttt.model.gamePosition;
import ttt.model.gameRecord;
import ttt.model.gameUser;
import ttt.model.dao.gamePositionDao;
import ttt.model.dao.gameRecordDao;
import ttt.model.dao.gameUserDao;
import ttt.model.service.Winmove;
import ttt.service.gamePlaying;
import ttt.service.hostGameService;
import ttt.service.joinGameService;
import ttt.service.oPosition;
import ttt.service.playGameService;
import ttt.service.xPosition;

@Controller
@SessionAttributes("startgame")
public class UserController {
	String messageKey = null;
	String gameSavingStatus = null;
	Date stamp;
	Date stampwithplayer;
	String messagewithplayer = null;

	@Autowired
	hostGameService host;

	@Autowired
	joinGameService join;

	@Autowired
	gamePlaying gameplaystatus;

	@Autowired
	xPosition xp;

	@Autowired
	oPosition op;

	@Autowired
	playGameService play;

	@Autowired
	gameUserDao gameuser;

	@Autowired
	gameRecordDao gamerecord;
	gameRecord game;

	@Autowired
	gamePositionDao gamepositiondao;
	gamePosition positions;

	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	public String printWelcome(ModelMap map, HttpSession session,
			Principal principal) {
		
		String username = principal.getName();

		session.setAttribute("uname", username);
		
		session.setAttribute("username", username);
		return "redirect:gamehome.html";

	}
	
	@RequestMapping(value = "/redirectpage.html", method = RequestMethod.GET)
	public String redirectPage(ModelMap model, HttpSession session) {
		
		
		gameplaystatus.fullclear();
		op.fullclear();
		xp.fullclear();
		play.fullclear();
		session.removeAttribute("wincondition");
		return "redirect:gamehome.html";
		
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(ModelMap model) {

		return "login";

	}

	@RequestMapping(value = "/loginfailed", method = RequestMethod.GET)
	public String loginerror(ModelMap model) {

		model.addAttribute("error", "true");
		return "login";

	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(ModelMap model) {

		return "login";

	}

	/*
	 * @RequestMapping({ "/users.html" }) public String viewUserDemo(ModelMap
	 * map) { map.put("users", gameuser.getAllUser());
	 * 
	 * return "users";
	 * 
	 * }
	 * 
	 * @RequestMapping(value = "/login.html", method = RequestMethod.GET) public
	 * String loginUser(ModelMap map) { map.put("users", new gameUser()); return
	 * "login";
	 * 
	 * }
	 * 
	 * @RequestMapping(value = "/login.html", method = RequestMethod.POST)
	 * public String loginUser(@RequestParam String username,
	 * 
	 * @RequestParam String password, @ModelAttribute gameUser user, ModelMap
	 * map, HttpSession session) { boolean switchkey =
	 * gameuser.checkUser(user.getUsername(), user.getPassword()); if (switchkey
	 * == true) { session.setAttribute("uname", user.getUsername()); return
	 * "redirect:gamehome.html"; } else { return "redirect:login.html";
	 * 
	 * } }
	 * 
	 * @RequestMapping({ "/logout.html" }) public String logout(SessionStatus
	 * sessionStatus, HttpSession session) { String sname = (String)
	 * session.getAttribute("uname");
	 * 
	 * session.setAttribute("messageKey", messageKey);
	 * 
	 * if (messageKey != null) { if (messageKey.equals("Running")) {
	 * 
	 * Date timenow = new Date();
	 * 
	 * gameRecord startgame = new gameRecord();
	 * 
	 * stamp = startgame.getStartTime(); gameUser g = gameuser.getUser(sname);
	 * startgame = gamerecord.getGamewithTime(g, stamp);
	 * 
	 * startgame.setEndTime(new Timestamp(timenow.getTime()));
	 * 
	 * startgame.setTie(false);
	 * 
	 * startgame.setLoserName(gameuser.getUser(sname));
	 * startgame.setWinnerName(null);
	 * 
	 * startgame = gamerecord.GameStart(startgame); messageKey = null; } }
	 * 
	 * session.invalidate();
	 * 
	 * return "redirect:login.html";
	 * 
	 * }
	 * 
	 * @RequestMapping(value = "/registration.html", method = RequestMethod.GET)
	 * public String addUser(ModelMap models) { models.put("userdetail", new
	 * gameUser()); return "registration"; }
	 * 
	 * @RequestMapping(value = "/registration.html", method =
	 * RequestMethod.POST) public String addUser(@ModelAttribute gameUser
	 * userdetail) {
	 * 
	 * gameuser.saveUser(userdetail); return "redirect:login.html"; }
	 */

	@RequestMapping(value = "/registration.html", method = RequestMethod.GET)
	public String addUser(ModelMap models) {
		models.put("userdetail", new gameUser());
		return "registration";
	}

	@RequestMapping(value = "/registration.html", method = RequestMethod.POST)
	public String addUser(@ModelAttribute gameUser userdetail,
			BindingResult bindingResult, HttpSession session) {

		gameuser.saveUser(userdetail);
		return "redirect:/login.html";
	}

	@RequestMapping({ "/users.html" })
	public String viewUserDemo(ModelMap map) {
		map.put("users", gameuser.getAllUser());

		return "users";

	}

	@RequestMapping(value = "/gamehome.html")
	public String GameHome(HttpServletRequest request,
			HttpServletResponse response, HttpSession session,
			SessionStatus sessionStatus, ModelMap map) {

		String sname = (String) session.getAttribute("uname");
		if (sname == null) {
			return "redirect:login.html";
		} else {

		}
		return "gamehome";
	}

	@RequestMapping(value = "/startGame.html")
	public String startGame(HttpServletRequest request,
			HttpServletResponse response, HttpSession session,
			SessionStatus sessionStatus, ModelMap map) {

		String sname = (String) session.getAttribute("uname");

		/*
		 * messageKey = (String) session.getAttribute("messageKey");
		 * session.setAttribute("messageKey", messageKey);
		 */

		if (sname == null) {
			return "redirect:login.html";
		} else {
			map.put("pageRec", 0);

			if (messageKey != null) {
				if (messageKey.equals("Running")) {

					Date timenow = new Date();

					gameRecord startgame = new gameRecord();
					/*
					 * stamp = startgame.getStartTime();
					 */gameUser g = gameuser.getUser(sname);
					startgame = gamerecord.getGamewithTime(g, stamp);

					startgame.setEndTime(new Timestamp(timenow.getTime()));

					startgame.setTie(false);

					startgame.setLoserName(gameuser.getUser(sname));
					startgame.setWinnerName(null);

					startgame = gamerecord.GameStart(startgame);
					messageKey = null;
				}
			}
			ArrayList<Gamedata> gamestatus = new ArrayList<Gamedata>();
			ArrayList<Integer> Xpos = new ArrayList<Integer>();
			ArrayList<Integer> Opos = new ArrayList<Integer>();

			String message = null;
			Gamedata g = new Gamedata();
			Winmove w = new Winmove();

			g.setIdref(0);

			for (int i = 1; i <= 3; i++) {
				for (int j = 1; j <= 3; j++) {
					gamestatus.add(new Gamedata(i, j, false, false));
				}
			}

			for (int a = 0; a < gamestatus.size(); a++) {
				if (gamestatus.get(a).isX() == true) {
					Xpos.add(a);
				}

				if (gamestatus.get(a).isO() == true) {
					Opos.add(a);
				}
			}

			message = w.Winmessage(Xpos, Opos);

			request.getSession().setAttribute("message", message);
			request.getSession().setAttribute("gamestatus", gamestatus);
			messageKey = null;

			gameSavingStatus = "newcreated";

			return "displayboard";
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/tictactoe.html", method = RequestMethod.GET)
	public String playGame(ModelMap map, HttpServletRequest request,
			HttpServletResponse response, @RequestParam int i,
			@RequestParam int j, HttpSession session) {
		/*
		 * messageKey = (String) session.getAttribute("messageKey");
		 */
		String sname = (String) session.getAttribute("uname");

		if (sname == null) {
			return "redirect:login.html";
		} else {

			map.put("pageRec", 1);

			if (messageKey == null) {
				Date timenow = new Date();

				gameRecord startgame = new gameRecord();

				startgame.setAI(true);
				startgame.setPlayer1(gameuser.getUser(sname));
				startgame.setPlayer2(null);
				startgame.setStartTime(new Timestamp(timenow.getTime()));
				stamp = startgame.getStartTime();

				game = gamerecord.GameStart(startgame);

			}

			String message = null;
			Gamedata game = new Gamedata();

			ArrayList<Gamedata> gamestatus = (ArrayList<Gamedata>) request
					.getSession().getAttribute("gamestatus");

			ArrayList<Integer> Xpos = new ArrayList<Integer>();
			ArrayList<Integer> Opos = new ArrayList<Integer>();

			// through @requestparameter get i & j
			int ivar = i;
			int jvar = j;

			// assigning values to print X
			for (int a = 0; a < gamestatus.size(); a++) {

				if ((gamestatus.get(a).getI() == ivar)
						&& (gamestatus.get(a).getJ() == jvar)) {

					/* gamestatus.set(a, new Gamedata(ivar, jvar, true, false)); */
					gamestatus.get(a).setX(true);
				}

			}

			// checking and storing values into arraylist where X's and O's
			// are..
			for (int a = 0; a < gamestatus.size(); a++) {

				if (gamestatus.get(a).isX() == true) {
					Xpos.add(a);

				}

				if (gamestatus.get(a).isO() == true) {
					Opos.add(a);

				}
			}

			// checking condition for player won
			Winmove w = new Winmove();
			message = w.Winmessage(Xpos, Opos);

			if (message == null) {

				int passkey = 0;
				int loopRef = 0;

				// checking for O's and creating O's conditions
				if ((Opos.contains(0) && Opos.contains(1))
						|| (Opos.contains(1) && Opos.contains(2))
						|| (Opos.contains(0) && Opos.contains(2))) {
					if ((Xpos.contains(0) || Xpos.contains(1) || Xpos
							.contains(2))
							|| (Opos.contains(0) && Opos.contains(1) && Opos
									.contains(2))) {

					} else if (loopRef == 0) {

						for (int a = 0; a <= 2; a++) {

							gamestatus.get(a).setO(true);
						}
						loopRef = 1;
						passkey++;
					}

				}
				if ((Opos.contains(3) && Opos.contains(4))
						|| (Opos.contains(4) && Opos.contains(5))
						|| (Opos.contains(3) && Opos.contains(5))) {
					if ((Xpos.contains(3) || Xpos.contains(4) || Xpos
							.contains(5))
							|| (Opos.contains(3) && Opos.contains(4) && Opos
									.contains(5))) {

					} else if (loopRef == 0) {
						for (int a = 3; a <= 5; a++) {

							gamestatus.get(a).setO(true);
						}
						loopRef = 1;
						passkey++;
					}

				}
				if ((Opos.contains(6) && Opos.contains(7))
						|| (Opos.contains(7) && Opos.contains(8))
						|| (Opos.contains(6) && Opos.contains(8))) {
					if ((Xpos.contains(6) || Xpos.contains(7) || Xpos
							.contains(8))
							|| (Opos.contains(6) && Opos.contains(7) && Opos
									.contains(8))) {

					} else if (loopRef == 0) {
						for (int a = 6; a <= 8; a++) {

							gamestatus.get(a).setO(true);
						}
						loopRef = 1;
						passkey++;
					}
				}
				if ((Opos.contains(0) && Opos.contains(3))
						|| (Opos.contains(3) && Opos.contains(6))
						|| (Opos.contains(0) && Opos.contains(6))) {
					if ((Xpos.contains(0) || Xpos.contains(3) || Xpos
							.contains(6))
							|| (Opos.contains(0) && Opos.contains(3) && Opos
									.contains(6))) {

					} else if (loopRef == 0) {
						int temp;
						temp = (Opos.contains(0) && Opos.contains(3)) ? 6
								: (Opos.contains(3) && Opos.contains(6)) ? 0
										: (Opos.contains(0) && Opos.contains(6)) ? 3
												: -1;

						gamestatus.get(temp).setO(true);
						loopRef = 1;
						passkey++;
					}

				}
				if ((Opos.contains(1) && Opos.contains(4))
						|| (Opos.contains(4) && Opos.contains(7))
						|| (Opos.contains(1) && Opos.contains(7))) {
					if ((Xpos.contains(1) || Xpos.contains(4) || Xpos
							.contains(7))
							|| (Opos.contains(1) && Opos.contains(4) && Opos
									.contains(7))) {

					} else if (loopRef == 0) {
						int temp;
						temp = (Opos.contains(1) && Opos.contains(4)) ? 7
								: (Opos.contains(4) && Opos.contains(7)) ? 1
										: (Opos.contains(1) && Opos.contains(7)) ? 4
												: -1;

						gamestatus.get(temp).setO(true);
						loopRef = 1;
						passkey++;
					}
				}

				if ((Opos.contains(2) && Opos.contains(5))
						|| (Opos.contains(5) && Opos.contains(8))
						|| (Opos.contains(2) && Opos.contains(8))) {
					if ((Xpos.contains(2) || Xpos.contains(5) || Xpos
							.contains(8))
							|| (Opos.contains(2) && Opos.contains(5) && Opos
									.contains(8))) {

					} else if (loopRef == 0) {
						int temp;
						temp = (Opos.contains(2) && Opos.contains(5)) ? 8
								: (Opos.contains(5) && Opos.contains(8)) ? 2
										: (Opos.contains(2) && Opos.contains(8)) ? 5
												: -1;

						gamestatus.get(temp).setO(true);
						loopRef = 1;
						passkey++;
					}

				}
				if ((Opos.contains(0) && Opos.contains(4))
						|| (Opos.contains(4) && Opos.contains(8))
						|| (Opos.contains(0) && Opos.contains(8))) {
					if ((Xpos.contains(0) || Xpos.contains(4) || Xpos
							.contains(8))) {

					} else if (Opos.contains(0) && Opos.contains(4)
							&& Opos.contains(8)) {

					} else if (loopRef == 0) {
						int temp;
						temp = (Opos.contains(0) && Opos.contains(4)) ? 8
								: (Opos.contains(4) && Opos.contains(8)) ? 0
										: (Opos.contains(0) && Opos.contains(8)) ? 4
												: -1;

						gamestatus.get(temp).setO(true);
						loopRef = 1;
						passkey++;
					}
					passkey++;

				}
				if ((Opos.contains(2) && Opos.contains(4))
						|| (Opos.contains(4) && Opos.contains(6))
						|| (Opos.contains(2) && Opos.contains(6))) {
					if ((Xpos.contains(2) || Xpos.contains(4) || Xpos
							.contains(6))
							|| (Opos.contains(2) && Opos.contains(4) && Opos
									.contains(6))) {

					} else if (loopRef == 0) {
						int temp;
						temp = (Opos.contains(2) && Opos.contains(4)) ? 6
								: (Opos.contains(4) && Opos.contains(6)) ? 2
										: (Opos.contains(2) && Opos.contains(6)) ? 4
												: -1;

						gamestatus.get(temp).setO(true);
						loopRef = 1;
						passkey++;
					}

				}
				// to check and put the O's where two X's are in same..
				if ((Xpos.contains(0) && Xpos.contains(1))
						|| (Xpos.contains(1) && Xpos.contains(2))
						|| (Xpos.contains(0) && Xpos.contains(2))) {
					if ((Opos.contains(0) || Opos.contains(1) || Opos
							.contains(2))
							|| (Xpos.contains(0) && Xpos.contains(1) && Xpos
									.contains(2))) {

					} else if (loopRef == 0) {
						int temp;
						temp = (Xpos.contains(0) && Xpos.contains(1)) ? 2
								: (Xpos.contains(1) && Xpos.contains(2)) ? 0
										: (Xpos.contains(0) && Xpos.contains(2)) ? 1
												: -1;

						gamestatus.get(temp).setO(true);
						loopRef = 1;
						passkey++;
					}
				}
				if ((Xpos.contains(3) && Xpos.contains(4))
						|| (Xpos.contains(4) && Xpos.contains(5))
						|| (Xpos.contains(3) && Xpos.contains(5))) {
					if ((Opos.contains(3) || Opos.contains(4) || Opos
							.contains(5))
							|| (Xpos.contains(3) && Xpos.contains(4) && Xpos
									.contains(5))) {

					} else if (loopRef == 0) {
						int temp;
						temp = (Xpos.contains(3) && Xpos.contains(4)) ? 5
								: (Xpos.contains(4) && Xpos.contains(5)) ? 3
										: (Xpos.contains(3) && Xpos.contains(5)) ? 4
												: -1;

						gamestatus.get(temp).setO(true);
						loopRef = 1;
						passkey++;
					}

				}
				if ((Xpos.contains(6) && Xpos.contains(7))
						|| (Xpos.contains(7) && Xpos.contains(8))
						|| (Xpos.contains(6) && Xpos.contains(8))) {

					if ((Opos.contains(6) || Opos.contains(7) || Opos
							.contains(8))
							|| (Xpos.contains(6) && Xpos.contains(7) && Xpos
									.contains(8))) {

					} else if (loopRef == 0) {
						int temp;
						temp = (Xpos.contains(6) && Xpos.contains(7)) ? 8
								: (Xpos.contains(7) && Xpos.contains(8)) ? 6
										: (Xpos.contains(6) && Xpos.contains(8)) ? 7
												: -1;

						gamestatus.get(temp).setO(true);
						loopRef = 1;
						passkey++;
					}

				}
				if ((Xpos.contains(0) && Xpos.contains(3))
						|| (Xpos.contains(3) && Xpos.contains(6))
						|| (Xpos.contains(0) && Xpos.contains(6))) {
					if ((Opos.contains(0) || Opos.contains(3) || Opos
							.contains(6))
							|| (Xpos.contains(0) && Xpos.contains(3) && Xpos
									.contains(6))) {

					} else if (loopRef == 0) {
						int temp;
						temp = (Xpos.contains(0) && Xpos.contains(3)) ? 6
								: (Xpos.contains(3) && Xpos.contains(6)) ? 0
										: (Xpos.contains(0) && Xpos.contains(6)) ? 3
												: -1;

						gamestatus.get(temp).setO(true);
						loopRef = 1;
						passkey++;
					}

				}
				if ((Xpos.contains(1) && Xpos.contains(4))
						|| (Xpos.contains(4) && Xpos.contains(7))
						|| (Xpos.contains(1) && Xpos.contains(7))) {
					if ((Opos.contains(1) || Opos.contains(4) || Opos
							.contains(7))
							|| (Xpos.contains(1) && Xpos.contains(4) && Xpos
									.contains(7))) {

					} else if (loopRef == 0) {
						int temp;
						temp = (Xpos.contains(1) && Xpos.contains(4)) ? 7
								: (Xpos.contains(4) && Xpos.contains(7)) ? 1
										: (Xpos.contains(1) && Xpos.contains(7)) ? 4
												: -1;

						gamestatus.get(temp).setO(true);
						loopRef = 1;
						passkey++;
					}

				}
				if ((Xpos.contains(2) && Xpos.contains(5))
						|| (Xpos.contains(5) && Xpos.contains(8))
						|| (Xpos.contains(2) && Xpos.contains(8))) {
					if ((Opos.contains(2) || Opos.contains(5) || Opos
							.contains(8))
							|| (Xpos.contains(2) && Xpos.contains(5) && Xpos
									.contains(8))) {

					} else if (loopRef == 0) {
						int temp;
						temp = (Xpos.contains(2) && Xpos.contains(5)) ? 8
								: (Xpos.contains(5) && Xpos.contains(8)) ? 2
										: (Xpos.contains(2) && Xpos.contains(8)) ? 5
												: -1;

						gamestatus.get(temp).setO(true);
						loopRef = 1;
						passkey++;
					}

				}
				if ((Xpos.contains(0) && Xpos.contains(4))
						|| (Xpos.contains(4) && Xpos.contains(8))
						|| (Xpos.contains(0) && Xpos.contains(8))) {
					if ((Opos.contains(0) || Opos.contains(4) || Opos
							.contains(8))
							|| (Xpos.contains(0) && Xpos.contains(4) && Xpos
									.contains(8))) {

					} else if (loopRef == 0) {
						int temp;
						temp = (Xpos.contains(0) && Xpos.contains(4)) ? 8
								: (Xpos.contains(4) && Xpos.contains(8)) ? 0
										: (Xpos.contains(0) && Xpos.contains(8)) ? 4
												: -1;

						gamestatus.get(temp).setO(true);
						loopRef = 1;
						passkey++;
					}

				}
				if ((Xpos.contains(2) && Xpos.contains(4))
						|| (Xpos.contains(4) && Xpos.contains(6))
						|| (Xpos.contains(2) && Xpos.contains(6))) {
					if ((Opos.contains(2) || Opos.contains(4) || Opos
							.contains(6))
							|| (Xpos.contains(2) && Xpos.contains(4) && Xpos
									.contains(6))) {

					} else if (loopRef == 0) {
						int temp;
						temp = (Xpos.contains(2) && Xpos.contains(4)) ? 6
								: (Xpos.contains(4) && Xpos.contains(6)) ? 2
										: (Xpos.contains(2) && Xpos.contains(6)) ? 4
												: -1;

						gamestatus.get(temp).setO(true);
						loopRef = 1;
						passkey++;
					}
					passkey++;

				}

				// else case
				if (/* (passkey != 0) && */(loopRef == 0)) {

					/*
					 * while ((gamestatus.get(randomInt).isX() == true) ||
					 * (gamestatus.get(randomInt).isO() == true)) {
					 */
					int varcheck = 0;
					while (varcheck == 0) {
						Random randomGenerator = new Random();
						int randomInt = randomGenerator.nextInt(8);

						if (!((Xpos.contains(randomInt)) || (Opos
								.contains(randomInt)))) {
							gamestatus.get(randomInt).setO(true);
							varcheck = 1;
						}

					}

				}
			}
			Xpos.clear();
			Opos.clear();
			// checking and storing values into arraylist where X's and O's
			// are..
			for (int a = 0; a < gamestatus.size(); a++) {

				if (gamestatus.get(a).isX() == true) {
					Xpos.add(a);

				}

				if (gamestatus.get(a).isO() == true) {
					Opos.add(a);

				}
			}
			message = w.Winmessage(Xpos, Opos);

			if (message == null) {
				messageKey = "Running";
			} else {

				Date timenow = new Date();

				gameRecord startgame = new gameRecord();
				/*
				 * stamp = startgame.getStartTime();
				 */gameUser g = gameuser.getUser(sname);
				startgame = gamerecord.getGamewithTime(g, stamp);

				startgame.setEndTime(new Timestamp(timenow.getTime()));

				if (message.equals("You won..!!")) {
					messageKey = "Player won";
					startgame.setTie(false);
					startgame.setLoserName(null);
					startgame.setWinnerName(gameuser.getUser(sname));

				} else if (message.equals("I won..!!")) {
					messageKey = "Computer won";
					startgame.setTie(false);
					startgame.setLoserName(gameuser.getUser(sname));
					startgame.setWinnerName(null);

				} else if (message.equals("Match Tied..!")) {
					messageKey = "Tied";
					startgame.setTie(true);
					startgame.setLoserName(null);
					startgame.setWinnerName(null);

				}

				startgame = gamerecord.GameStart(startgame);

			}

			game.setIdref(1);

			request.getSession().setAttribute("message", message);
			request.getSession().setAttribute("messageKey", messageKey);

			request.getSession().setAttribute("gamestatus", gamestatus);
			/*
			 * request. getRequestDispatcher ( "/WEB-INF/displayboard.jsp" )
			 * .forward ( request , response );
			 */
			return "displayboard";
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/gamehistory.html")
	public String getAllGames(ModelMap map, HttpServletRequest request,
			HttpServletResponse response, SessionStatus sessionStatus,
			HttpSession session) {
		String sname = (String) session.getAttribute("uname");

		if (sname == null) {
			return "redirect:login.html";
		} else {
			List<gameHistory> allHistoryData = new ArrayList<gameHistory>();

			gameUser g = gameuser.getUser(sname);

			List<gameRecord> allgames = gamerecord.getAllCompletedGames(g);
			List<gameRecord> gameplayer1 = gamerecord
					.getCompletedGames1Player(g);
			List<gameRecord> gameplayer2 = gamerecord
					.getCompletedGames2Player(g);
			List<gameRecord> winrateaginAI = gamerecord.getWinRateAgainstAI(g);
			List<gameRecord> allaginAI = gamerecord.getAllAgainstAI(g);
			List<gameRecord> allaginHUMAN = gamerecord.getAllAgainstHUMAN(g);
			List<gameRecord> winrateaginHUMAN = gamerecord
					.getWinRateAgainstHuman(g);

			List<gameRecord> alldata = gamerecord.getAllData(g);

			double allgamewithAI = allaginAI.size();
			double allgamewithHUMAN = allaginHUMAN.size();
			double winratewithAI = winrateaginAI.size();
			double winratewithHUMAN = winrateaginHUMAN.size();
			if (allgamewithAI == 0) {
				allgamewithAI = 1;
			}
			if (allgamewithHUMAN == 0) {
				allgamewithHUMAN = 1;
			}
			double winrateagainstAI = (winratewithAI * 100) / allgamewithAI;
			double winrateagainstHUMAN = (winratewithHUMAN * 100)
					/ allgamewithHUMAN;

			winrateagainstAI = Math.round(winrateagainstAI * 100.0) / 100.0;
			winrateagainstHUMAN = Math.round(winrateagainstHUMAN * 100.0) / 100.0;

			DecimalFormat df = new DecimalFormat("##.##");
			df.format(winrateagainstAI);
			df.format(winrateagainstHUMAN);

			map.put("winrateagainstAI", winrateagainstAI);
			map.put("winrateagainstHUMAN", winrateagainstHUMAN);

			map.put("allgames", allgames.size());
			map.put("playergamesby1", gameplayer1.size());
			map.put("playergamesby2", gameplayer2.size());

			map.put("winvsAI", winrateaginAI.size());
			map.put("playvsAI", allaginAI.size());
			map.put("winvsHUMAN", winrateaginHUMAN.size());
			map.put("playvsHUMAN", allaginHUMAN.size());

			map.put("alldata", alldata);

			for (int i = 0; i < alldata.size(); i++) {

				String opponent = null;
				String winner = null;
				String difference = null;

				long diff = alldata.get(i).getEndTime().getTime()
						- alldata.get(i).getStartTime().getTime();

				long diffSec = diff / 1000 % 60;
				long diffMin = diff / (60 * 1000) % 60;
				long diffHour = diff / (60 * 60 * 1000) % 24;
				long diffDay = (int) ((alldata.get(i).getEndTime().getTime() - alldata
						.get(i).getStartTime().getTime()) / (1000 * 60 * 60 * 24));

				if (diffDay == 0) {
					if (diffHour == 0) {
						if (diffMin == 0) {
							difference = diffSec + " Seconds.";
						} else {
							difference = diffMin + " Minute" + diffSec
									+ " Seconds.";
						}
					} else {

						difference = diffHour + " Hour " + diffMin + " Minute"
								+ diffSec + " Seconds.";

					}
				} else {
					difference = diffDay + " Day " + diffHour + " Hour "
							+ diffMin + " Minute" + diffSec + " Seconds.";

				}

				if (alldata.get(i).isAI() == true) {

					if (alldata.get(i).getPlayer1() != null) {
						opponent = "AI Player";

					} else if (alldata.get(i).getPlayer2() != null) {
						opponent = "AI Player";
					}

					if (alldata.get(i).isTie() == true) {
						winner = "Match Tied";
					} else if (alldata.get(i).isTie() == false) {
						if (alldata.get(i).getWinnerName() != null) {
							winner = "You Win";
						} else if (alldata.get(i).getLoserName() != null) {
							winner = "You Lose";
						}

					}

				} else if (alldata.get(i).isAI() == false) {

					if (sname.equals(alldata.get(i).getPlayer1().getUsername())) {
						opponent = alldata.get(i).getPlayer2().getUsername();

					} else if (sname.equals(alldata.get(i).getPlayer2()
							.getUsername())) {
						opponent = alldata.get(i).getPlayer1().getUsername();
					}

					if (alldata.get(i).isTie() == true) {
						winner = "Match Tied";
					} else if (alldata.get(i).isTie() == false) {

						if (sname.equals(alldata.get(i).getWinnerName()
								.getUsername())) {
							winner = "You Win";

						} else if (sname.equals(alldata.get(i).getLoserName()
								.getUsername())) {
							winner = "You Lose";
						}
					}

				}

				allHistoryData
						.add(new gameHistory(opponent, difference, winner));

			}
			map.put("allHistoryData", allHistoryData);

			/*
			 * map.put("winrateagainstAI", winrateagainstAI);
			 * map.put("winrateagainstHUMAN", winrateagainstHUMAN);
			 */

			return "gamehistory";
		}
	}

	@RequestMapping(value = "/startGuestGame.html")
	public String startguestGame(HttpServletRequest request,
			HttpServletResponse response, HttpSession session,
			SessionStatus sessionStatus, ModelMap map) {

		/*
		 * messageKey = (String) session.getAttribute("messageKey");
		 * session.setAttribute("messageKey", messageKey);
		 */

		map.put("pageRec", 0);

		if (messageKey != null) {
			if (messageKey.equals("Running")) {

				Date timenow = new Date();

				messageKey = null;
			}
		}
		ArrayList<Gamedata> gamestatus = new ArrayList<Gamedata>();
		ArrayList<Integer> Xpos = new ArrayList<Integer>();
		ArrayList<Integer> Opos = new ArrayList<Integer>();

		String message = null;
		Gamedata g = new Gamedata();
		Winmove w = new Winmove();

		for (int i = 1; i <= 3; i++) {
			for (int j = 1; j <= 3; j++) {
				gamestatus.add(new Gamedata(i, j, false, false));
			}
		}

		for (int a = 0; a < gamestatus.size(); a++) {
			if (gamestatus.get(a).isX() == true) {
				Xpos.add(a);
			}

			if (gamestatus.get(a).isO() == true) {
				Opos.add(a);
			}
		}

		message = w.Winmessage(Xpos, Opos);

		request.getSession().setAttribute("message", message);
		request.getSession().setAttribute("gamestatus", gamestatus);
		messageKey = null;

		gameSavingStatus = "newcreated";

		return "guestgame";

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/guesttictactoe.html", method = RequestMethod.GET)
	public String playguestGame(ModelMap map, HttpServletRequest request,
			HttpServletResponse response, @RequestParam int i,
			@RequestParam int j, HttpSession session) {

		map.put("pageRec", 1);

		String message = null;
		Gamedata game = new Gamedata();

		ArrayList<Gamedata> gamestatus = (ArrayList<Gamedata>) request
				.getSession().getAttribute("gamestatus");

		ArrayList<Integer> Xpos = new ArrayList<Integer>();
		ArrayList<Integer> Opos = new ArrayList<Integer>();

		// through @requestparameter get i & j
		int ivar = i;
		int jvar = j;

		// assigning values to print X
		for (int a = 0; a < gamestatus.size(); a++) {

			if ((gamestatus.get(a).getI() == ivar)
					&& (gamestatus.get(a).getJ() == jvar)) {

				/* gamestatus.set(a, new Gamedata(ivar, jvar, true, false)); */
				gamestatus.get(a).setX(true);
			}

		}

		// checking and storing values into arraylist where X's and O's
		// are..
		for (int a = 0; a < gamestatus.size(); a++) {

			if (gamestatus.get(a).isX() == true) {
				Xpos.add(a);

			}

			if (gamestatus.get(a).isO() == true) {
				Opos.add(a);

			}
		}

		// checking condition for player won
		Winmove w = new Winmove();
		message = w.Winmessage(Xpos, Opos);

		if (message == null) {

			int passkey = 0;
			int loopRef = 0;

			// checking for O's and creating O's conditions
			if ((Opos.contains(0) && Opos.contains(1))
					|| (Opos.contains(1) && Opos.contains(2))
					|| (Opos.contains(0) && Opos.contains(2))) {
				if ((Xpos.contains(0) || Xpos.contains(1) || Xpos.contains(2))
						|| (Opos.contains(0) && Opos.contains(1) && Opos
								.contains(2))) {

				} else if (loopRef == 0) {

					for (int a = 0; a <= 2; a++) {

						gamestatus.get(a).setO(true);
					}
					loopRef = 1;
					passkey++;
				}

			}
			if ((Opos.contains(3) && Opos.contains(4))
					|| (Opos.contains(4) && Opos.contains(5))
					|| (Opos.contains(3) && Opos.contains(5))) {
				if ((Xpos.contains(3) || Xpos.contains(4) || Xpos.contains(5))
						|| (Opos.contains(3) && Opos.contains(4) && Opos
								.contains(5))) {

				} else if (loopRef == 0) {
					for (int a = 3; a <= 5; a++) {

						gamestatus.get(a).setO(true);
					}
					loopRef = 1;
					passkey++;
				}

			}
			if ((Opos.contains(6) && Opos.contains(7))
					|| (Opos.contains(7) && Opos.contains(8))
					|| (Opos.contains(6) && Opos.contains(8))) {
				if ((Xpos.contains(6) || Xpos.contains(7) || Xpos.contains(8))
						|| (Opos.contains(6) && Opos.contains(7) && Opos
								.contains(8))) {

				} else if (loopRef == 0) {
					for (int a = 6; a <= 8; a++) {

						gamestatus.get(a).setO(true);
					}
					loopRef = 1;
					passkey++;
				}
			}
			if ((Opos.contains(0) && Opos.contains(3))
					|| (Opos.contains(3) && Opos.contains(6))
					|| (Opos.contains(0) && Opos.contains(6))) {
				if ((Xpos.contains(0) || Xpos.contains(3) || Xpos.contains(6))
						|| (Opos.contains(0) && Opos.contains(3) && Opos
								.contains(6))) {

				} else if (loopRef == 0) {
					int temp;
					temp = (Opos.contains(0) && Opos.contains(3)) ? 6 : (Opos
							.contains(3) && Opos.contains(6)) ? 0 : (Opos
							.contains(0) && Opos.contains(6)) ? 3 : -1;

					gamestatus.get(temp).setO(true);
					loopRef = 1;
					passkey++;
				}

			}
			if ((Opos.contains(1) && Opos.contains(4))
					|| (Opos.contains(4) && Opos.contains(7))
					|| (Opos.contains(1) && Opos.contains(7))) {
				if ((Xpos.contains(1) || Xpos.contains(4) || Xpos.contains(7))
						|| (Opos.contains(1) && Opos.contains(4) && Opos
								.contains(7))) {

				} else if (loopRef == 0) {
					int temp;
					temp = (Opos.contains(1) && Opos.contains(4)) ? 7 : (Opos
							.contains(4) && Opos.contains(7)) ? 1 : (Opos
							.contains(1) && Opos.contains(7)) ? 4 : -1;

					gamestatus.get(temp).setO(true);
					loopRef = 1;
					passkey++;
				}
			}

			if ((Opos.contains(2) && Opos.contains(5))
					|| (Opos.contains(5) && Opos.contains(8))
					|| (Opos.contains(2) && Opos.contains(8))) {
				if ((Xpos.contains(2) || Xpos.contains(5) || Xpos.contains(8))
						|| (Opos.contains(2) && Opos.contains(5) && Opos
								.contains(8))) {

				} else if (loopRef == 0) {
					int temp;
					temp = (Opos.contains(2) && Opos.contains(5)) ? 8 : (Opos
							.contains(5) && Opos.contains(8)) ? 2 : (Opos
							.contains(2) && Opos.contains(8)) ? 5 : -1;

					gamestatus.get(temp).setO(true);
					loopRef = 1;
					passkey++;
				}

			}
			if ((Opos.contains(0) && Opos.contains(4))
					|| (Opos.contains(4) && Opos.contains(8))
					|| (Opos.contains(0) && Opos.contains(8))) {
				if ((Xpos.contains(0) || Xpos.contains(4) || Xpos.contains(8))) {

				} else if (Opos.contains(0) && Opos.contains(4)
						&& Opos.contains(8)) {

				} else if (loopRef == 0) {
					int temp;
					temp = (Opos.contains(0) && Opos.contains(4)) ? 8 : (Opos
							.contains(4) && Opos.contains(8)) ? 0 : (Opos
							.contains(0) && Opos.contains(8)) ? 4 : -1;

					gamestatus.get(temp).setO(true);
					loopRef = 1;
					passkey++;
				}
				passkey++;

			}
			if ((Opos.contains(2) && Opos.contains(4))
					|| (Opos.contains(4) && Opos.contains(6))
					|| (Opos.contains(2) && Opos.contains(6))) {
				if ((Xpos.contains(2) || Xpos.contains(4) || Xpos.contains(6))
						|| (Opos.contains(2) && Opos.contains(4) && Opos
								.contains(6))) {

				} else if (loopRef == 0) {
					int temp;
					temp = (Opos.contains(2) && Opos.contains(4)) ? 6 : (Opos
							.contains(4) && Opos.contains(6)) ? 2 : (Opos
							.contains(2) && Opos.contains(6)) ? 4 : -1;

					gamestatus.get(temp).setO(true);
					loopRef = 1;
					passkey++;
				}

			}
			// to check and put the O's where two X's are in same..
			if ((Xpos.contains(0) && Xpos.contains(1))
					|| (Xpos.contains(1) && Xpos.contains(2))
					|| (Xpos.contains(0) && Xpos.contains(2))) {
				if ((Opos.contains(0) || Opos.contains(1) || Opos.contains(2))
						|| (Xpos.contains(0) && Xpos.contains(1) && Xpos
								.contains(2))) {

				} else if (loopRef == 0) {
					int temp;
					temp = (Xpos.contains(0) && Xpos.contains(1)) ? 2 : (Xpos
							.contains(1) && Xpos.contains(2)) ? 0 : (Xpos
							.contains(0) && Xpos.contains(2)) ? 1 : -1;

					gamestatus.get(temp).setO(true);
					loopRef = 1;
					passkey++;
				}
			}
			if ((Xpos.contains(3) && Xpos.contains(4))
					|| (Xpos.contains(4) && Xpos.contains(5))
					|| (Xpos.contains(3) && Xpos.contains(5))) {
				if ((Opos.contains(3) || Opos.contains(4) || Opos.contains(5))
						|| (Xpos.contains(3) && Xpos.contains(4) && Xpos
								.contains(5))) {

				} else if (loopRef == 0) {
					int temp;
					temp = (Xpos.contains(3) && Xpos.contains(4)) ? 5 : (Xpos
							.contains(4) && Xpos.contains(5)) ? 3 : (Xpos
							.contains(3) && Xpos.contains(5)) ? 4 : -1;

					gamestatus.get(temp).setO(true);
					loopRef = 1;
					passkey++;
				}

			}
			if ((Xpos.contains(6) && Xpos.contains(7))
					|| (Xpos.contains(7) && Xpos.contains(8))
					|| (Xpos.contains(6) && Xpos.contains(8))) {

				if ((Opos.contains(6) || Opos.contains(7) || Opos.contains(8))
						|| (Xpos.contains(6) && Xpos.contains(7) && Xpos
								.contains(8))) {

				} else if (loopRef == 0) {
					int temp;
					temp = (Xpos.contains(6) && Xpos.contains(7)) ? 8 : (Xpos
							.contains(7) && Xpos.contains(8)) ? 6 : (Xpos
							.contains(6) && Xpos.contains(8)) ? 7 : -1;

					gamestatus.get(temp).setO(true);
					loopRef = 1;
					passkey++;
				}

			}
			if ((Xpos.contains(0) && Xpos.contains(3))
					|| (Xpos.contains(3) && Xpos.contains(6))
					|| (Xpos.contains(0) && Xpos.contains(6))) {
				if ((Opos.contains(0) || Opos.contains(3) || Opos.contains(6))
						|| (Xpos.contains(0) && Xpos.contains(3) && Xpos
								.contains(6))) {

				} else if (loopRef == 0) {
					int temp;
					temp = (Xpos.contains(0) && Xpos.contains(3)) ? 6 : (Xpos
							.contains(3) && Xpos.contains(6)) ? 0 : (Xpos
							.contains(0) && Xpos.contains(6)) ? 3 : -1;

					gamestatus.get(temp).setO(true);
					loopRef = 1;
					passkey++;
				}

			}
			if ((Xpos.contains(1) && Xpos.contains(4))
					|| (Xpos.contains(4) && Xpos.contains(7))
					|| (Xpos.contains(1) && Xpos.contains(7))) {
				if ((Opos.contains(1) || Opos.contains(4) || Opos.contains(7))
						|| (Xpos.contains(1) && Xpos.contains(4) && Xpos
								.contains(7))) {

				} else if (loopRef == 0) {
					int temp;
					temp = (Xpos.contains(1) && Xpos.contains(4)) ? 7 : (Xpos
							.contains(4) && Xpos.contains(7)) ? 1 : (Xpos
							.contains(1) && Xpos.contains(7)) ? 4 : -1;

					gamestatus.get(temp).setO(true);
					loopRef = 1;
					passkey++;
				}

			}
			if ((Xpos.contains(2) && Xpos.contains(5))
					|| (Xpos.contains(5) && Xpos.contains(8))
					|| (Xpos.contains(2) && Xpos.contains(8))) {
				if ((Opos.contains(2) || Opos.contains(5) || Opos.contains(8))
						|| (Xpos.contains(2) && Xpos.contains(5) && Xpos
								.contains(8))) {

				} else if (loopRef == 0) {
					int temp;
					temp = (Xpos.contains(2) && Xpos.contains(5)) ? 8 : (Xpos
							.contains(5) && Xpos.contains(8)) ? 2 : (Xpos
							.contains(2) && Xpos.contains(8)) ? 5 : -1;

					gamestatus.get(temp).setO(true);
					loopRef = 1;
					passkey++;
				}

			}
			if ((Xpos.contains(0) && Xpos.contains(4))
					|| (Xpos.contains(4) && Xpos.contains(8))
					|| (Xpos.contains(0) && Xpos.contains(8))) {
				if ((Opos.contains(0) || Opos.contains(4) || Opos.contains(8))
						|| (Xpos.contains(0) && Xpos.contains(4) && Xpos
								.contains(8))) {

				} else if (loopRef == 0) {
					int temp;
					temp = (Xpos.contains(0) && Xpos.contains(4)) ? 8 : (Xpos
							.contains(4) && Xpos.contains(8)) ? 0 : (Xpos
							.contains(0) && Xpos.contains(8)) ? 4 : -1;

					gamestatus.get(temp).setO(true);
					loopRef = 1;
					passkey++;
				}

			}
			if ((Xpos.contains(2) && Xpos.contains(4))
					|| (Xpos.contains(4) && Xpos.contains(6))
					|| (Xpos.contains(2) && Xpos.contains(6))) {
				if ((Opos.contains(2) || Opos.contains(4) || Opos.contains(6))
						|| (Xpos.contains(2) && Xpos.contains(4) && Xpos
								.contains(6))) {

				} else if (loopRef == 0) {
					int temp;
					temp = (Xpos.contains(2) && Xpos.contains(4)) ? 6 : (Xpos
							.contains(4) && Xpos.contains(6)) ? 2 : (Xpos
							.contains(2) && Xpos.contains(6)) ? 4 : -1;

					gamestatus.get(temp).setO(true);
					loopRef = 1;
					passkey++;
				}
				passkey++;

			}

			// else case
			if (/* (passkey != 0) && */(loopRef == 0)) {

				/*
				 * while ((gamestatus.get(randomInt).isX() == true) ||
				 * (gamestatus.get(randomInt).isO() == true)) {
				 */
				int varcheck = 0;
				while (varcheck == 0) {
					Random randomGenerator = new Random();
					int randomInt = randomGenerator.nextInt(8);

					if (!((Xpos.contains(randomInt)) || (Opos
							.contains(randomInt)))) {
						gamestatus.get(randomInt).setO(true);
						varcheck = 1;
					}

				}

			}
		}
		Xpos.clear();
		Opos.clear();
		// checking and storing values into arraylist where X's and O's
		// are..
		for (int a = 0; a < gamestatus.size(); a++) {

			if (gamestatus.get(a).isX() == true) {
				Xpos.add(a);

			}

			if (gamestatus.get(a).isO() == true) {
				Opos.add(a);

			}
		}
		message = w.Winmessage(Xpos, Opos);

		if (message == null) {
			messageKey = "Running";
		} else {

			Date timenow = new Date();

			if (message.equals("You won..!!")) {
				messageKey = "Player won";

			} else if (message.equals("I won..!!")) {
				messageKey = "Computer won";

			} else if (message.equals("Match Tied..!")) {
				messageKey = "Tied";

			}

		}

		game.setIdref(1);

		request.getSession().setAttribute("message", message);
		request.getSession().setAttribute("messageKey", messageKey);

		request.getSession().setAttribute("gamestatus", gamestatus);
		/*
		 * request. getRequestDispatcher ( "/WEB-INF/displayboard.jsp" )
		 * .forward ( request , response );
		 */
		return "guestgame";

	}

	@SuppressWarnings("unchecked")
	@RequestMapping({ "/saveGame.html" })
	public String savegames(ModelMap map, HttpServletRequest request,
			HttpServletResponse response, SessionStatus sessionStatus,
			HttpSession session) {

		String sname = (String) session.getAttribute("uname");

		if (sname == null) {
			return "redirect:login.html";
		} else {

			ArrayList<Gamedata> gamestatus = (ArrayList<Gamedata>) request
					.getSession().getAttribute("gamestatus");

			gameUser g = gameuser.getUser(sname);
			gameRecord runningGame = gamerecord.getGamewithTime(g, stamp);
			Date timenow = new Date();
			Date time = new Timestamp(timenow.getTime());
			runningGame.setSavedAt(time);
			game = gamerecord.GameStart(runningGame);

			gamePosition storegame = new gamePosition();

			int x = 0;

			for (int a = 0; a < gamestatus.size(); a++) {

				storegame.setGameRefId(runningGame);
				storegame.setI(gamestatus.get(a).getI());
				storegame.setJ(gamestatus.get(a).getJ());
				storegame.setxPosition(gamestatus.get(a).isX());
				storegame.setoPosition(gamestatus.get(a).isO());

				positions = gamepositiondao.SaveGame(storegame);
				System.out.println(x + " " + gamestatus.size());
				x++;
			}
			gamestatus.clear();
			request.getSession().removeAttribute("gamestatus");

		}
		gameSavingStatus = "justsaved";
		messageKey = "Discard";
		return "redirect:gamehome.html";

	}

	@RequestMapping({ "/resumegame.html" })
	public String resumegame(ModelMap map, HttpServletRequest request,
			HttpServletResponse response, SessionStatus sessionStatus,
			HttpSession session) {
		map.put("pageRec", 1);

		String sname = (String) session.getAttribute("uname");

		if (sname == null) {
			return "redirect:login.html";
		} else {
			request.getSession().removeAttribute("gamestatus");

			gameUser g = gameuser.getUser(sname);

			List<gameRecord> allSavedGame = gamerecord.getAllResumeGames(g);
			for (int i = 0; i < allSavedGame.size(); i++) {

				System.out.println("Hello Saved @"
						+ allSavedGame.get(i).getSavedAt());
			}
			map.put("allSavedGame", allSavedGame);

		}
		gameSavingStatus = "justsaved";

		messageKey = "Discard";
		return "resumegame";

	}

	@RequestMapping({ "/resume.html" })
	public String resumeNow(ModelMap map, HttpServletRequest request,
			HttpServletResponse response, SessionStatus sessionStatus,
			HttpSession session, @RequestParam int id) {

		String sname = (String) session.getAttribute("uname");

		if (sname == null) {
			return "redirect:login.html";
		} else {
			gameUser g = gameuser.getUser(sname);
			String message = null;
			// to get id from gameRecord

			map.put("pageRec", 1);

			List<gameRecord> allSavedGame = gamerecord.getAllResumeGames(g);

			gameRecord gamestatusnow = gamerecord.getClickedGame(id);

			gamestatusnow.setSavedAt(null);
			game = gamerecord.GameStart(gamestatusnow);

			List<gamePosition> savedGameRecord = gamepositiondao
					.getSavedGameTime(gamestatusnow);

			// to get game steps from gamePosition
			/*
			 * List<gamePosition> savedGameRecord = gamepositiondao
			 * .getSavedGameTime(allSavedGame.get(id));
			 */
			ArrayList<Gamedata> gamestatus = new ArrayList<Gamedata>();

			for (int i = 0; i < savedGameRecord.size(); i++) {

				gamestatus.add(new Gamedata(savedGameRecord.get(i).getI(),
						savedGameRecord.get(i).getJ(), savedGameRecord.get(i)
								.isxPosition(), savedGameRecord.get(i)
								.isoPosition()));

				gamepositiondao.DeleteGame(savedGameRecord.get(i));

			}
			stamp = gamestatusnow.getStartTime();
			ArrayList<Integer> Xpos = new ArrayList<Integer>();
			ArrayList<Integer> Opos = new ArrayList<Integer>();
			for (int a = 0; a < gamestatus.size(); a++) {
				if (gamestatus.get(a).isX() == true) {
					Xpos.add(a);
				}

				if (gamestatus.get(a).isO() == true) {
					Opos.add(a);
				}
			}
			Winmove w = new Winmove();
			message = w.Winmessage(Xpos, Opos);

			request.getSession().setAttribute("message", message);
			request.getSession().setAttribute("gamestatus", gamestatus);

		}
		gameSavingStatus = "resumed";

		messageKey = "Running";
		return "displayboard";

	}

	@RequestMapping("/hostgame.json")
	public String hostJson(ModelMap models) {

		models.put("usernames", host.getUsernames());
		return "jsonView";
	}

	@RequestMapping("/joingame.json")
	public String joinJson(ModelMap models) {

		models.put("usernames", join.getUsernames());
		return "jsonView";
	}

	@RequestMapping("/hostgame.deferred.json")
	@ResponseBody
	public DeferredResult<String> hostDeferred() {

		DeferredResult<String> result = new DeferredResult<String>();
		host.addResult(result);
		return result;
	}

	@RequestMapping("/joingame.deferred.json")
	@ResponseBody
	public DeferredResult<String> joinJson() {

		DeferredResult<String> result = new DeferredResult<String>();
		join.addResult(result);
		return result;
	}

	@RequestMapping("/infogame.deferred.json")
	@ResponseBody
	public DeferredResult<String> infoDeferred() {

		DeferredResult<String> result = new DeferredResult<String>();
		host.addResult(result);
		return result;
	}

	@RequestMapping("/hostgame.html")
	public String hostGameWithHuman(HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {
		String sname = (String) session.getAttribute("uname");
		String myname = "Hoster";
		session.setAttribute("myname", myname);

		String clickstatus = "join";
		session.setAttribute("clickstatus", clickstatus);

		host.add(sname);
		List<String> joins = join.getUsernames();

		if (joins.size() != 0) {

			List<String> hosts = host.getUsernames();

			String joinplayer = joins.get(0);
			String hostplayer = hosts.get(0);

			play.addJoin(joinplayer);
			play.addHost(hostplayer);

			join.remove(joinplayer);
			host.remove(hostplayer);

			return "redirect:play2game.html";
		} else {
			return "hostgame";
		}

	}

	@RequestMapping("/joingame.html")
	public String joinGameWithHuman(HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {
		String sname = (String) session.getAttribute("uname");
		String myname = "Joiner";
		session.setAttribute("myname", myname);
		String clickstatus = "host";
		session.setAttribute("clickstatus", clickstatus);

		join.add(sname);
		List<String> hosts = host.getUsernames();

		if (hosts.size() != 0) {
			List<String> joins = join.getUsernames();

			String joinplayer = joins.get(0);
			String hostplayer = hosts.get(0);

			play.addJoin(joinplayer);
			play.addHost(hostplayer);

			join.remove(joinplayer);
			host.remove(hostplayer);

			return "redirect:play2game.html";
		} else {
			return "joingame";
		}

	}

	@RequestMapping("/leavehostedgame.html")
	public String leaveHosting(HttpSession session) {
		String sname = (String) session.getAttribute("uname");

		if (sname == null) {
			return "redirect:login.html";
		} else {
			host.remove(sname);
			return "gamehome";
		}
	}

	@RequestMapping("/leavejoinedgame.html")
	public String leaveJoining(HttpSession session) {
		String sname = (String) session.getAttribute("uname");

		if (sname == null) {
			return "redirect:login.html";
		} else {
			join.remove(sname);
			return "gamehome";
		}
	}

	@RequestMapping("/play2game.html")
	public String play2p(HttpServletRequest request,
			HttpServletResponse response, HttpSession session,
			SessionStatus sessionStatus, ModelMap map) {
		String sname = (String) session.getAttribute("uname");
		String clickstatus = (String) session.getAttribute("clickstatus");
		String myname = (String) session.getAttribute("myname");

		List<String> hosts = host.getUsernames();
		List<String> joins = join.getUsernames();

		List<String> hostplayer = play.getHostplayer();
		List<String> joinplayer = play.getJoinplayer();

		if (!hostplayer.contains(sname) && clickstatus.equals("join")) {
			host.remove(sname);
			return "redirect:hostgame.html";
		}
		if (!joinplayer.contains(sname) && clickstatus.equals("host")) {
			join.remove(sname);
			return "redirect:joingame.html";
		}

		String player = hostplayer.get(play.getHostplayer().size() - 1);
		String opponent = joinplayer.get(play.getJoinplayer().size() - 1);
		String msg = "";

		String currenthoster = player;
		String currentjoiner = opponent;

		String currentplayer = "";

		if (sname.equals(currenthoster)) {
			msg = currentjoiner
					+ " has joined the game. Please make your move.";
			currentplayer = "me";

		} else if (sname.equals(currentjoiner)) {
			msg = "Joined game hosted by " + currenthoster + ". Waiting for "
					+ currenthoster + "'s move ...";
			currentplayer = "you";

		}
		/*
		 * String currenthoster = hosts.get(0); String currentjoiner =
		 * joins.get(0);
		 * 
		 * String sname = (String) session.getAttribute("uname"); String msg =
		 * ""; if (sname.equals(currenthoster)) { msg = currentjoiner +
		 * " has joined the game. Please make your move.";
		 * 
		 * } else if (sname.equals(currentjoiner)) { msg =
		 * "Joined game hosted by " + currenthoster + ". Waiting for " +
		 * currenthoster + "'s move ...";
		 * 
		 * }
		 */

		System.out.println(currenthoster);
		System.out.println(currentjoiner);
		session.setAttribute("currentjoiner", currentjoiner);
		session.setAttribute("currenthoster", currenthoster);
		session.setAttribute("currentplayer", currentplayer);

		map.put("currentjoiner", currentjoiner);
		map.put("currenthoster", currenthoster);
		map.put("currentplayer", currentplayer);
		map.put("msg", msg);
		return "play2";

	}

	@RequestMapping("/gameagainstplayer.json")
	public String gameJson(ModelMap models) {

		models.put("usernames", gameplaystatus.getPlaystatus());
		return "jsonView";
	}

	@RequestMapping("/gameagainstplayer.deferred.json")
	@ResponseBody
	public DeferredResult<String> gameDeferred() {

		DeferredResult<String> result = new DeferredResult<String>();
		gameplaystatus.addResult(result);
		return result;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/gameagainstplayer.html", method = RequestMethod.GET)
	public String twoplayerGame(ModelMap map, HttpServletRequest request,
			HttpServletResponse response, @RequestParam int x,
			HttpSession session) {
		/*
		 * messageKey = (String) session.getAttribute("messageKey");
		 */
		String sname = (String) session.getAttribute("uname");
		String currentjoiner = (String) session.getAttribute("currentjoiner");
		String currenthoster = (String) session.getAttribute("currenthoster");
		String currentplayer = (String) session.getAttribute("currentplayer");
		String myname = (String) session.getAttribute("myname");

		/*
		 * String myname = (String) session.getAttribute("myname");
		 * 
		 * String myname = (String) session.getAttribute("myname");
		 */System.out.println(" I am" + myname);

		map.put("pageRec", 1);

		if (x == 0) {

			currentplayer = "me";

		} else {

			Gamedata game = new Gamedata();

			/*
			 * ArrayList<Gamedata> twoplayergamestatus = (ArrayList<Gamedata>)
			 * request .getSession().getAttribute("twoplayergamestatus");
			 */

			currentplayer = "you";

			gameplaystatus.add("you");

			if (myname == "Hoster") {
				xp.add(x);
			} else if (myname == "Joiner") {
				op.add(x);
			}

		}

		if (messagewithplayer == null && sname == currenthoster) {
			Date timenow = new Date();

			gameRecord startgame = new gameRecord();

			startgame.setAI(false);
			startgame.setPlayer1(gameuser.getUser(currenthoster));
			startgame.setPlayer2(gameuser.getUser(currentjoiner));
			startgame.setStartTime(new Timestamp(timenow.getTime()));

			stampwithplayer = startgame.getStartTime();

			game = gamerecord.GameStart(startgame);

		}

		String wincondition = null;

		ArrayList<Integer> xpo = (ArrayList<Integer>) xp.getUsernames();
		ArrayList<Integer> opo = (ArrayList<Integer>) op.getUsernames();

		if (!opo.isEmpty()) {

			System.out.println("OPO");
			for (Integer integer : opo) {

				System.out.println(integer);
			}
		}
		if (!xpo.isEmpty()) {

			System.out.println("XPO");
			for (Integer integer : xpo) {

				System.out.println(integer);
			}
		}
		map.put("xpo", xpo);
		map.put("opo", opo);

		ArrayList<Integer> Xpos = new ArrayList<Integer>();
		ArrayList<Integer> Opos = new ArrayList<Integer>();
		for (Integer integer : xpo) {
			Xpos.add(integer - 1);
		}
		for (Integer integer : opo) {
			Opos.add(integer - 1);
		}
		Winmove w = new Winmove();
		wincondition = w.Winmessage(Xpos, Opos);

		if (wincondition != null) {

			Date timenow = new Date();

			gameRecord startgame = new gameRecord();
			/*
			 * stamp = startgame.getStartTime();
			 */gameUser g = gameuser.getUser(currenthoster);
			startgame = gamerecord.getGamewithTime(g, stampwithplayer);

			startgame.setEndTime(new Timestamp(timenow.getTime()));

			if (wincondition == "You won..!!") {
				startgame.setTie(false);
				startgame.setWinnerName(gameuser.getUser(currenthoster));
				startgame.setLoserName(gameuser.getUser(currentjoiner));

			} else if (wincondition == "I won..!!") {
				startgame.setTie(false);
				startgame.setWinnerName(gameuser.getUser(currentjoiner));
				startgame.setLoserName(gameuser.getUser(currenthoster));

			} else if (wincondition == "Match Tied..!") {

				startgame.setTie(true);

				startgame.setLoserName(null);
				startgame.setWinnerName(null);

			}
			startgame = gamerecord.GameStart(startgame);
			messagewithplayer = null;
		}

		System.out.println(wincondition);
		if (wincondition == "You won..!!") {
			wincondition = currenthoster + " won..!!";
		}
		if (wincondition == "I won..!!") {
			wincondition = currentjoiner + " won..!!";
		}

		messagewithplayer = "Running";
		session.setAttribute("wincondition", wincondition);

		map.put("currentplayer", currentplayer);
		return "play2";

	}

}