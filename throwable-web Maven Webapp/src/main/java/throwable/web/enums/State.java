package throwable.web.enums;

/**
 * @author WaterHsu@xiu8.com
 * @version 2014年11月8日
 */
public enum State {
	no_active("未激活", 1),
	user_exception("账号异常", 2),
	user_frozen("账号被冻结", 3),
	user_disabled("账号已停用", 4),
	user_nomal("账号正常", 5);
	
	private String name;
	private int value;
	
	private State(String name, int value){
		this.name = name;
		this.value = value;
	}
	
	public static String getName(int value){
		for(State s : State.values()){
			if(s.getValue() == value){
				return s.getName();
			}
		}
		return null;
	}
	
	

	@Override
	public String toString() {
		return this.name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
	public State getState(int value) {
		if(value == State.no_active.getValue()) {
			return State.no_active;
		} else if(value == State.user_exception.getValue()) {
			return State.user_exception;
		} else if(value == State.user_frozen.getValue()) {
			return State.user_frozen;
		} else if(value == State.user_disabled.getValue()) {
			return State.user_disabled;
		} else if(value == State.user_nomal.getValue()) {
			return State.user_nomal;
		} else {
			return null;
		}
	}
	
	public State getState(String name) {
		if(name.equals(State.no_active.getName())) {
			return State.no_active;
		} else if(name.equals(State.user_exception.getName())) {
			return State.user_exception;
		} else if(name.equals(State.user_frozen.getName())) {
			return State.user_frozen;
		} else if(name.equals(State.user_disabled.getName())) {
			return State.user_disabled;
		} else if(name.equals(State.user_nomal.getName())) {
			return State.user_nomal;
		} else {
			return null;
		}
	}
}
