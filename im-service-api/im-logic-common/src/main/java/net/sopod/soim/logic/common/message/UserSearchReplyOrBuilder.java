// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: proto/message.proto

package net.sopod.soim.logic.common.message;

public interface UserSearchReplyOrBuilder extends
    // @@protoc_insertion_point(interface_extends:net.sopod.soim.logic.common.message.UserSearchReply)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>repeated .net.sopod.soim.logic.common.message.UserInfo users = 1;</code>
   */
  java.util.List<net.sopod.soim.logic.common.message.UserInfo> 
      getUsersList();
  /**
   * <code>repeated .net.sopod.soim.logic.common.message.UserInfo users = 1;</code>
   */
  net.sopod.soim.logic.common.message.UserInfo getUsers(int index);
  /**
   * <code>repeated .net.sopod.soim.logic.common.message.UserInfo users = 1;</code>
   */
  int getUsersCount();
  /**
   * <code>repeated .net.sopod.soim.logic.common.message.UserInfo users = 1;</code>
   */
  java.util.List<? extends net.sopod.soim.logic.common.message.UserInfoOrBuilder> 
      getUsersOrBuilderList();
  /**
   * <code>repeated .net.sopod.soim.logic.common.message.UserInfo users = 1;</code>
   */
  net.sopod.soim.logic.common.message.UserInfoOrBuilder getUsersOrBuilder(
      int index);
}
