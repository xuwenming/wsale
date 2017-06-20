package jb.util.wx.bean;

/**
 * Created by wenming on 2016/9/3.
 */
public class TextMessage extends BaseMessage {
    private Text text;

    public Text getText() {
        return text;
    }

    public void setText(Text text) {
        this.text = text;
    }
}
