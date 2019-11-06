package com.example.scooterRentalApp.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MsgSource {

    public final String OK001;
    public final String OK002;
    public final String OK003;
    public final String OK004;
    public final String OK005;
    public final String OK006;
    public final String OK007;
    public final String OK008;
    public final String OK009;

    public final ConstErrorMsg ERR001;
    public final ConstErrorMsg ERR002;
    public final ConstErrorMsg ERR003;
    public final ConstErrorMsg ERR004;
    public final ConstErrorMsg ERR005;
    public final ConstErrorMsg ERR006;
    public final ConstErrorMsg ERR007;
    public final ConstErrorMsg ERR008;
    public final ConstErrorMsg ERR009;
    public final ConstErrorMsg ERR010;
    public final ConstErrorMsg ERR011;
    public final ConstErrorMsg ERR012;
    public final ConstErrorMsg ERR013;
    public final ConstErrorMsg ERR014;
    public final ConstErrorMsg ERR015;
    public final ConstErrorMsg ERR016;
    public final ConstErrorMsg ERR017;
    public final ConstErrorMsg ERR018;
    public final ConstErrorMsg ERR019;

    public MsgSource(
            @Value("${common.ok.msg.ok001}") String ok001MsgValue,
            @Value("${common.ok.msg.ok002}") String ok002MsgValue,
            @Value("${common.ok.msg.ok003}") String ok003MsgValue,
            @Value("${common.ok.msg.ok004}") String ok004MsgValue,
            @Value("${common.ok.msg.ok005}") String ok005MsgValue,
            @Value("${common.ok.msg.ok006}") String ok006MsgValue,
            @Value("${common.ok.msg.ok007}") String ok007MsgValue,
            @Value("${common.ok.msg.ok008}") String ok008MsgValue,
            @Value("${common.ok.msg.ok009}") String ok009MsgValue,

            @Value("${common.const.error.msg.err001}") String err001MsgValue,
            @Value("${common.const.error.msg.err002}") String err002MsgValue,
            @Value("${common.const.error.msg.err003}") String err003MsgValue,
            @Value("${common.const.error.msg.err004}") String err004MsgValue,
            @Value("${common.const.error.msg.err005}") String err005MsgValue,
            @Value("${common.const.error.msg.err006}") String err006MsgValue,
            @Value("${common.const.error.msg.err007}") String err007MsgValue,
            @Value("${common.const.error.msg.err008}") String err008MsgValue,
            @Value("${common.const.error.msg.err009}") String err009MsgValue,
            @Value("${common.const.error.msg.err010}") String err010MsgValue,
            @Value("${common.const.error.msg.err011}") String err011MsgValue,
            @Value("${common.const.error.msg.err012}") String err012MsgValue,
            @Value("${common.const.error.msg.err013}") String err013MsgValue,
            @Value("${common.const.error.msg.err014}") String err014MsgValue,
            @Value("${common.const.error.msg.err015}") String err015MsgValue,
            @Value("${common.const.error.msg.err016}") String err016MsgValue,
            @Value("${common.const.error.msg.err017}") String err017MsgValue,
            @Value("${common.const.error.msg.err018}") String err018MsgValue,
            @Value("${common.const.error.msg.err019}") String err019MsgValue
    ) {
        OK001 = ok001MsgValue;
        OK002 = ok002MsgValue;
        OK003 = ok003MsgValue;
        OK004 = ok004MsgValue;
        OK005 = ok005MsgValue;
        OK006 = ok006MsgValue;
        OK007 = ok007MsgValue;
        OK008 = ok008MsgValue;
        OK009 = ok009MsgValue;

        ERR001 = new ConstErrorMsg("ERR001", err001MsgValue);
        ERR002 = new ConstErrorMsg("ERR002", err002MsgValue);
        ERR003 = new ConstErrorMsg("ERR003", err003MsgValue);
        ERR004 = new ConstErrorMsg("ERR004", err004MsgValue);
        ERR005 = new ConstErrorMsg("ERR005", err005MsgValue);
        ERR006 = new ConstErrorMsg("ERR006", err006MsgValue);
        ERR007 = new ConstErrorMsg("ERR007", err007MsgValue);
        ERR008 = new ConstErrorMsg("ERR008", err008MsgValue);
        ERR009 = new ConstErrorMsg("ERR009", err009MsgValue);
        ERR010 = new ConstErrorMsg("ERR010", err010MsgValue);
        ERR011 = new ConstErrorMsg("ERR011", err011MsgValue);
        ERR012 = new ConstErrorMsg("ERR012", err012MsgValue);
        ERR013 = new ConstErrorMsg("ERR013", err013MsgValue);
        ERR014 = new ConstErrorMsg("ERR014", err014MsgValue);
        ERR015 = new ConstErrorMsg("ERR015", err015MsgValue);
        ERR016 = new ConstErrorMsg("ERR016", err016MsgValue);
        ERR017 = new ConstErrorMsg("ERR017", err017MsgValue);
        ERR018 = new ConstErrorMsg("ERR018", err018MsgValue);
        ERR019 = new ConstErrorMsg("ERR019", err019MsgValue);
    }
}
