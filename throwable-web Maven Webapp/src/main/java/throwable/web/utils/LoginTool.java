package throwable.web.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import throwable.web.enums.LoginMark;
import throwable.web.enums.Right;
import throwable.web.enums.State;

public class LoginTool {
	
	public static boolean isLogin(HttpSession httpSession) {
		if(httpSession == null) {
			return false;
		}
		//session中没有相应的值  不允许登陆
		if(httpSession.getAttribute("userId") == null || httpSession.getAttribute("username") == null || httpSession.getAttribute("right") == null || httpSession.getAttribute("user_state") == null || httpSession.getAttribute("loginMark") == null)
		{
			deleteSession(httpSession);
			return false;
		}
		//session中loginMark值不对  不允许登陆
		if(!LoginMark.hasLogin.getText().equals(httpSession.getAttribute("loginMark").toString())) {
			deleteSession(httpSession);
			return false;
		}
		//session中保存的用户账号状态有问题  不允许登陆
		int state = Integer.parseInt(httpSession.getAttribute("user_state").toString());
		if(state != State.no_active.getValue() && state != State.user_nomal.getValue()) {
			deleteSession(httpSession);
			return false;
		}
		return true;
	}
	
	public static boolean isLogin(HttpSession httpSession, int userId) {
		if(!isLogin(httpSession)) {
			return false;
		}
		if(Integer.parseInt(httpSession.getAttribute("userId").toString()) != userId) {
			return false;
		}
		return true;
	}
	
	public static String getRight(HttpSession httpSession) {
		if(!isLogin(httpSession)) {
			return null;
		}
		return httpSession.getAttribute("right").toString();
	}
	
	public static String getUsername(HttpSession httpSession) {
		if(!isLogin(httpSession)) {
			return null;
		}
		return httpSession.getAttribute("username").toString();
	}
	
	public static int getUserId(HttpSession httpSession) {
		if(!isLogin(httpSession)) {
			return 0;
		}
		return Integer.parseInt(httpSession.getAttribute("userId").toString());
	}
	
	public static int getUserState(HttpSession httpSession) {
		if(!isLogin(httpSession)) {
			return 0;
		}
		return Integer.parseInt(httpSession.getAttribute("user_state").toString());
	}
	
	/**
	 * 不严格检查  只要是该权限  或者权限比该权限用户高的用户都可以访问
	 * @param httpSession
	 * @param right
	 * @return
	 */
	public static boolean isAllowed(HttpSession httpSession, Right right) {
		return isAllowed(httpSession, right);
	}
	
	/**
	 * 严格检查权限  只允许权限是被允许的权限
	 * @param httpSession
	 * @param right
	 * @return
	 */
	public static boolean isAllowedStrict(HttpSession httpSession, Right right) {
		if(!isLogin(httpSession)) {
			return false;
		}
		if(!httpSession.getAttribute("right").toString().equals(right.getValue())) {
			return false;
		}
		return true;
	}
	
	/**
	 * 给session设置值
	 * @param req
	 * @param userId
	 * @param username
	 * @param right
	 * @param user_state
	 * @param mark
	 */
	public static void addSession(HttpServletRequest req, int userId, String username, String right, String user_state, String mark) {
		HttpSession httpSession = req.getSession(); 
		httpSession.setAttribute("userId", userId);
		httpSession.setAttribute("username", username);
		httpSession.setAttribute("right", right);
		httpSession.setAttribute("user_state", user_state);
		httpSession.setAttribute("loginMark", LoginMark.hasLogin.getText());
	}
	
	/**
	 * 删除session
	 * @param httpSession
	 */
	public static void deleteSession(HttpSession httpSession) {
		httpSession.removeAttribute("userId");
		httpSession.removeAttribute("username");
		httpSession.removeAttribute("right");
		httpSession.removeAttribute("user_state");
		httpSession.removeAttribute("loginMark");
		httpSession.invalidate();
	}
}
