package com.example.android.Data;

import java.util.List;

/**
 * this class created to overcome the problem that room dose'nt supports
 * storage of list.
 */
public class Messages {
    private List<String> msgs;

    public Messages(List<String> msgs) {
        this.msgs = msgs;
    }

    public void setMsgs(List<String> msgs) {
        this.msgs = msgs;
    }

    public List<String> getMsgs() {
        return msgs;
    }
}
