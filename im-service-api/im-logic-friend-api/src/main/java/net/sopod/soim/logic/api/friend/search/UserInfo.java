// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: proto/UserSearch.proto

package net.sopod.soim.logic.api.friend.search;

/**
 * Protobuf type {@code net.sopod.soim.logic.api.friend.search.UserInfo}
 */
public final class UserInfo extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:net.sopod.soim.logic.api.friend.search.UserInfo)
    UserInfoOrBuilder {
private static final long serialVersionUID = 0L;
  // Use UserInfo.newBuilder() to construct.
  private UserInfo(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private UserInfo() {
    account_ = "";
    nickname_ = "";
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new UserInfo();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private UserInfo(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    if (extensionRegistry == null) {
      throw new java.lang.NullPointerException();
    }
    com.google.protobuf.UnknownFieldSet.Builder unknownFields =
        com.google.protobuf.UnknownFieldSet.newBuilder();
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          case 8: {

            uid_ = input.readInt64();
            break;
          }
          case 18: {
            java.lang.String s = input.readStringRequireUtf8();

            account_ = s;
            break;
          }
          case 26: {
            java.lang.String s = input.readStringRequireUtf8();

            nickname_ = s;
            break;
          }
          default: {
            if (!parseUnknownField(
                input, unknownFields, extensionRegistry, tag)) {
              done = true;
            }
            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          e).setUnfinishedMessage(this);
    } finally {
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return net.sopod.soim.logic.api.friend.search.UserSearch.internal_static_net_sopod_soim_logic_api_friend_search_UserInfo_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return net.sopod.soim.logic.api.friend.search.UserSearch.internal_static_net_sopod_soim_logic_api_friend_search_UserInfo_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            net.sopod.soim.logic.api.friend.search.UserInfo.class, net.sopod.soim.logic.api.friend.search.UserInfo.Builder.class);
  }

  public static final int UID_FIELD_NUMBER = 1;
  private long uid_;
  /**
   * <code>int64 uid = 1;</code>
   * @return The uid.
   */
  @java.lang.Override
  public long getUid() {
    return uid_;
  }

  public static final int ACCOUNT_FIELD_NUMBER = 2;
  private volatile java.lang.Object account_;
  /**
   * <code>string account = 2;</code>
   * @return The account.
   */
  @java.lang.Override
  public java.lang.String getAccount() {
    java.lang.Object ref = account_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      account_ = s;
      return s;
    }
  }
  /**
   * <code>string account = 2;</code>
   * @return The bytes for account.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getAccountBytes() {
    java.lang.Object ref = account_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      account_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int NICKNAME_FIELD_NUMBER = 3;
  private volatile java.lang.Object nickname_;
  /**
   * <code>string nickname = 3;</code>
   * @return The nickname.
   */
  @java.lang.Override
  public java.lang.String getNickname() {
    java.lang.Object ref = nickname_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      nickname_ = s;
      return s;
    }
  }
  /**
   * <code>string nickname = 3;</code>
   * @return The bytes for nickname.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getNicknameBytes() {
    java.lang.Object ref = nickname_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      nickname_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  private byte memoizedIsInitialized = -1;
  @java.lang.Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @java.lang.Override
  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (uid_ != 0L) {
      output.writeInt64(1, uid_);
    }
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(account_)) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 2, account_);
    }
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(nickname_)) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 3, nickname_);
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (uid_ != 0L) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt64Size(1, uid_);
    }
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(account_)) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, account_);
    }
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(nickname_)) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(3, nickname_);
    }
    size += unknownFields.getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof net.sopod.soim.logic.api.friend.search.UserInfo)) {
      return super.equals(obj);
    }
    net.sopod.soim.logic.api.friend.search.UserInfo other = (net.sopod.soim.logic.api.friend.search.UserInfo) obj;

    if (getUid()
        != other.getUid()) return false;
    if (!getAccount()
        .equals(other.getAccount())) return false;
    if (!getNickname()
        .equals(other.getNickname())) return false;
    if (!unknownFields.equals(other.unknownFields)) return false;
    return true;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    hash = (37 * hash) + UID_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
        getUid());
    hash = (37 * hash) + ACCOUNT_FIELD_NUMBER;
    hash = (53 * hash) + getAccount().hashCode();
    hash = (37 * hash) + NICKNAME_FIELD_NUMBER;
    hash = (53 * hash) + getNickname().hashCode();
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static net.sopod.soim.logic.api.friend.search.UserInfo parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static net.sopod.soim.logic.api.friend.search.UserInfo parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static net.sopod.soim.logic.api.friend.search.UserInfo parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static net.sopod.soim.logic.api.friend.search.UserInfo parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static net.sopod.soim.logic.api.friend.search.UserInfo parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static net.sopod.soim.logic.api.friend.search.UserInfo parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static net.sopod.soim.logic.api.friend.search.UserInfo parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static net.sopod.soim.logic.api.friend.search.UserInfo parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static net.sopod.soim.logic.api.friend.search.UserInfo parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static net.sopod.soim.logic.api.friend.search.UserInfo parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static net.sopod.soim.logic.api.friend.search.UserInfo parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static net.sopod.soim.logic.api.friend.search.UserInfo parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @java.lang.Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(net.sopod.soim.logic.api.friend.search.UserInfo prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @java.lang.Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code net.sopod.soim.logic.api.friend.search.UserInfo}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:net.sopod.soim.logic.api.friend.search.UserInfo)
      net.sopod.soim.logic.api.friend.search.UserInfoOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return net.sopod.soim.logic.api.friend.search.UserSearch.internal_static_net_sopod_soim_logic_api_friend_search_UserInfo_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return net.sopod.soim.logic.api.friend.search.UserSearch.internal_static_net_sopod_soim_logic_api_friend_search_UserInfo_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              net.sopod.soim.logic.api.friend.search.UserInfo.class, net.sopod.soim.logic.api.friend.search.UserInfo.Builder.class);
    }

    // Construct using net.sopod.soim.logic.api.friend.search.UserInfo.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
      }
    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      uid_ = 0L;

      account_ = "";

      nickname_ = "";

      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return net.sopod.soim.logic.api.friend.search.UserSearch.internal_static_net_sopod_soim_logic_api_friend_search_UserInfo_descriptor;
    }

    @java.lang.Override
    public net.sopod.soim.logic.api.friend.search.UserInfo getDefaultInstanceForType() {
      return net.sopod.soim.logic.api.friend.search.UserInfo.getDefaultInstance();
    }

    @java.lang.Override
    public net.sopod.soim.logic.api.friend.search.UserInfo build() {
      net.sopod.soim.logic.api.friend.search.UserInfo result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public net.sopod.soim.logic.api.friend.search.UserInfo buildPartial() {
      net.sopod.soim.logic.api.friend.search.UserInfo result = new net.sopod.soim.logic.api.friend.search.UserInfo(this);
      result.uid_ = uid_;
      result.account_ = account_;
      result.nickname_ = nickname_;
      onBuilt();
      return result;
    }

    @java.lang.Override
    public Builder clone() {
      return super.clone();
    }
    @java.lang.Override
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.setField(field, value);
    }
    @java.lang.Override
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return super.clearField(field);
    }
    @java.lang.Override
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return super.clearOneof(oneof);
    }
    @java.lang.Override
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, java.lang.Object value) {
      return super.setRepeatedField(field, index, value);
    }
    @java.lang.Override
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.addRepeatedField(field, value);
    }
    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof net.sopod.soim.logic.api.friend.search.UserInfo) {
        return mergeFrom((net.sopod.soim.logic.api.friend.search.UserInfo)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(net.sopod.soim.logic.api.friend.search.UserInfo other) {
      if (other == net.sopod.soim.logic.api.friend.search.UserInfo.getDefaultInstance()) return this;
      if (other.getUid() != 0L) {
        setUid(other.getUid());
      }
      if (!other.getAccount().isEmpty()) {
        account_ = other.account_;
        onChanged();
      }
      if (!other.getNickname().isEmpty()) {
        nickname_ = other.nickname_;
        onChanged();
      }
      this.mergeUnknownFields(other.unknownFields);
      onChanged();
      return this;
    }

    @java.lang.Override
    public final boolean isInitialized() {
      return true;
    }

    @java.lang.Override
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      net.sopod.soim.logic.api.friend.search.UserInfo parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (net.sopod.soim.logic.api.friend.search.UserInfo) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private long uid_ ;
    /**
     * <code>int64 uid = 1;</code>
     * @return The uid.
     */
    @java.lang.Override
    public long getUid() {
      return uid_;
    }
    /**
     * <code>int64 uid = 1;</code>
     * @param value The uid to set.
     * @return This builder for chaining.
     */
    public Builder setUid(long value) {
      
      uid_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int64 uid = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearUid() {
      
      uid_ = 0L;
      onChanged();
      return this;
    }

    private java.lang.Object account_ = "";
    /**
     * <code>string account = 2;</code>
     * @return The account.
     */
    public java.lang.String getAccount() {
      java.lang.Object ref = account_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        account_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string account = 2;</code>
     * @return The bytes for account.
     */
    public com.google.protobuf.ByteString
        getAccountBytes() {
      java.lang.Object ref = account_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        account_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string account = 2;</code>
     * @param value The account to set.
     * @return This builder for chaining.
     */
    public Builder setAccount(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      account_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string account = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearAccount() {
      
      account_ = getDefaultInstance().getAccount();
      onChanged();
      return this;
    }
    /**
     * <code>string account = 2;</code>
     * @param value The bytes for account to set.
     * @return This builder for chaining.
     */
    public Builder setAccountBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      account_ = value;
      onChanged();
      return this;
    }

    private java.lang.Object nickname_ = "";
    /**
     * <code>string nickname = 3;</code>
     * @return The nickname.
     */
    public java.lang.String getNickname() {
      java.lang.Object ref = nickname_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        nickname_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string nickname = 3;</code>
     * @return The bytes for nickname.
     */
    public com.google.protobuf.ByteString
        getNicknameBytes() {
      java.lang.Object ref = nickname_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        nickname_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string nickname = 3;</code>
     * @param value The nickname to set.
     * @return This builder for chaining.
     */
    public Builder setNickname(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      nickname_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string nickname = 3;</code>
     * @return This builder for chaining.
     */
    public Builder clearNickname() {
      
      nickname_ = getDefaultInstance().getNickname();
      onChanged();
      return this;
    }
    /**
     * <code>string nickname = 3;</code>
     * @param value The bytes for nickname to set.
     * @return This builder for chaining.
     */
    public Builder setNicknameBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      nickname_ = value;
      onChanged();
      return this;
    }
    @java.lang.Override
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFields(unknownFields);
    }

    @java.lang.Override
    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:net.sopod.soim.logic.api.friend.search.UserInfo)
  }

  // @@protoc_insertion_point(class_scope:net.sopod.soim.logic.api.friend.search.UserInfo)
  private static final net.sopod.soim.logic.api.friend.search.UserInfo DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new net.sopod.soim.logic.api.friend.search.UserInfo();
  }

  public static net.sopod.soim.logic.api.friend.search.UserInfo getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<UserInfo>
      PARSER = new com.google.protobuf.AbstractParser<UserInfo>() {
    @java.lang.Override
    public UserInfo parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new UserInfo(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<UserInfo> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<UserInfo> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public net.sopod.soim.logic.api.friend.search.UserInfo getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}
