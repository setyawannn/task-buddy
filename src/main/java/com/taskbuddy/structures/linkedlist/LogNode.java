package com.taskbuddy.structures.linkedlist;

import com.taskbuddy.structures.linkedlist.ActivityLogger.ActivityLog;

public class LogNode {
    private ActivityLog log;
    private LogNode previous;
    private LogNode next;

    public LogNode(ActivityLog log) {
        this.log = log;
    }

    public ActivityLog getLog() {
        return log;
    }

    public LogNode getPrevious() {
        return previous;
    }

    public void setPrevious(LogNode previous) {
        this.previous = previous;
    }

    public LogNode getNext() {
        return next;
    }

    public void setNext(LogNode next) {
        this.next = next;
    }
}
