package throwable.web.enums;

public enum LoginMark {
	hasLogin("已经登陆", 1),
	notLogin("没有登陆", 2);
	
	private String text;
	private int value;
	
	private LoginMark(String text, int value) {
		this.text = text;
		this.value = value;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}
