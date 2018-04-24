package com.rt.sm.bean;

import java.io.Serializable;

/**
 * Created by gaodesong on 17/6/26.
 */

public class ResultData<T> implements Serializable {

    //public Data data;

    public T data;

    public boolean ok;

    public String msg;

    public String result;

    public T list;

    public int code;

    public int totalCount;

    public String sys_count;

    public String user_count;

    public String status;

    public Message message;

    //

    public static class Message{
        public String info;
    }






}
