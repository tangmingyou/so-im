// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: proto/net/HeartBeat.proto

package net.sopod.soim.data.msg.net;

public final class HeartBeat {
  private HeartBeat() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface PingOrBuilder extends
      // @@protoc_insertion_point(interface_extends:proto.Ping)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>int64 time = 1;</code>
     * @return The time.
     */
    long getTime();
  }
  /**
   * Protobuf type {@code proto.Ping}
   */
  public static final class Ping extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:proto.Ping)
      PingOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use Ping.newBuilder() to construct.
    private Ping(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private Ping() {
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(
        UnusedPrivateParameter unused) {
      return new Ping();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private Ping(
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

              time_ = input.readInt64();
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
      return net.sopod.soim.data.msg.net.HeartBeat.internal_static_proto_Ping_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return net.sopod.soim.data.msg.net.HeartBeat.internal_static_proto_Ping_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              net.sopod.soim.data.msg.net.HeartBeat.Ping.class, net.sopod.soim.data.msg.net.HeartBeat.Ping.Builder.class);
    }

    public static final int TIME_FIELD_NUMBER = 1;
    private long time_;
    /**
     * <code>int64 time = 1;</code>
     * @return The time.
     */
    @java.lang.Override
    public long getTime() {
      return time_;
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
      if (time_ != 0L) {
        output.writeInt64(1, time_);
      }
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (time_ != 0L) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt64Size(1, time_);
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
      if (!(obj instanceof net.sopod.soim.data.msg.net.HeartBeat.Ping)) {
        return super.equals(obj);
      }
      net.sopod.soim.data.msg.net.HeartBeat.Ping other = (net.sopod.soim.data.msg.net.HeartBeat.Ping) obj;

      if (getTime()
          != other.getTime()) return false;
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
      hash = (37 * hash) + TIME_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
          getTime());
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static net.sopod.soim.data.msg.net.HeartBeat.Ping parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static net.sopod.soim.data.msg.net.HeartBeat.Ping parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static net.sopod.soim.data.msg.net.HeartBeat.Ping parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static net.sopod.soim.data.msg.net.HeartBeat.Ping parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static net.sopod.soim.data.msg.net.HeartBeat.Ping parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static net.sopod.soim.data.msg.net.HeartBeat.Ping parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static net.sopod.soim.data.msg.net.HeartBeat.Ping parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static net.sopod.soim.data.msg.net.HeartBeat.Ping parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static net.sopod.soim.data.msg.net.HeartBeat.Ping parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static net.sopod.soim.data.msg.net.HeartBeat.Ping parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static net.sopod.soim.data.msg.net.HeartBeat.Ping parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static net.sopod.soim.data.msg.net.HeartBeat.Ping parseFrom(
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
    public static Builder newBuilder(net.sopod.soim.data.msg.net.HeartBeat.Ping prototype) {
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
     * Protobuf type {@code proto.Ping}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:proto.Ping)
        net.sopod.soim.data.msg.net.HeartBeat.PingOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return net.sopod.soim.data.msg.net.HeartBeat.internal_static_proto_Ping_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return net.sopod.soim.data.msg.net.HeartBeat.internal_static_proto_Ping_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                net.sopod.soim.data.msg.net.HeartBeat.Ping.class, net.sopod.soim.data.msg.net.HeartBeat.Ping.Builder.class);
      }

      // Construct using net.sopod.soim.data.msg.net.HeartBeat.Ping.newBuilder()
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
        time_ = 0L;

        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return net.sopod.soim.data.msg.net.HeartBeat.internal_static_proto_Ping_descriptor;
      }

      @java.lang.Override
      public net.sopod.soim.data.msg.net.HeartBeat.Ping getDefaultInstanceForType() {
        return net.sopod.soim.data.msg.net.HeartBeat.Ping.getDefaultInstance();
      }

      @java.lang.Override
      public net.sopod.soim.data.msg.net.HeartBeat.Ping build() {
        net.sopod.soim.data.msg.net.HeartBeat.Ping result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public net.sopod.soim.data.msg.net.HeartBeat.Ping buildPartial() {
        net.sopod.soim.data.msg.net.HeartBeat.Ping result = new net.sopod.soim.data.msg.net.HeartBeat.Ping(this);
        result.time_ = time_;
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
        if (other instanceof net.sopod.soim.data.msg.net.HeartBeat.Ping) {
          return mergeFrom((net.sopod.soim.data.msg.net.HeartBeat.Ping)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(net.sopod.soim.data.msg.net.HeartBeat.Ping other) {
        if (other == net.sopod.soim.data.msg.net.HeartBeat.Ping.getDefaultInstance()) return this;
        if (other.getTime() != 0L) {
          setTime(other.getTime());
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
        net.sopod.soim.data.msg.net.HeartBeat.Ping parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (net.sopod.soim.data.msg.net.HeartBeat.Ping) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private long time_ ;
      /**
       * <code>int64 time = 1;</code>
       * @return The time.
       */
      @java.lang.Override
      public long getTime() {
        return time_;
      }
      /**
       * <code>int64 time = 1;</code>
       * @param value The time to set.
       * @return This builder for chaining.
       */
      public Builder setTime(long value) {
        
        time_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int64 time = 1;</code>
       * @return This builder for chaining.
       */
      public Builder clearTime() {
        
        time_ = 0L;
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


      // @@protoc_insertion_point(builder_scope:proto.Ping)
    }

    // @@protoc_insertion_point(class_scope:proto.Ping)
    private static final net.sopod.soim.data.msg.net.HeartBeat.Ping DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new net.sopod.soim.data.msg.net.HeartBeat.Ping();
    }

    public static net.sopod.soim.data.msg.net.HeartBeat.Ping getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<Ping>
        PARSER = new com.google.protobuf.AbstractParser<Ping>() {
      @java.lang.Override
      public Ping parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new Ping(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<Ping> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<Ping> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public net.sopod.soim.data.msg.net.HeartBeat.Ping getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  public interface PongOrBuilder extends
      // @@protoc_insertion_point(interface_extends:proto.Pong)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>int64 time = 1;</code>
     * @return The time.
     */
    long getTime();

    /**
     * <pre>
     * 收到时时间戳减Ping时间戳
     * </pre>
     *
     * <code>int32 diff = 2;</code>
     * @return The diff.
     */
    int getDiff();
  }
  /**
   * Protobuf type {@code proto.Pong}
   */
  public static final class Pong extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:proto.Pong)
      PongOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use Pong.newBuilder() to construct.
    private Pong(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private Pong() {
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(
        UnusedPrivateParameter unused) {
      return new Pong();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private Pong(
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

              time_ = input.readInt64();
              break;
            }
            case 16: {

              diff_ = input.readInt32();
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
      return net.sopod.soim.data.msg.net.HeartBeat.internal_static_proto_Pong_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return net.sopod.soim.data.msg.net.HeartBeat.internal_static_proto_Pong_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              net.sopod.soim.data.msg.net.HeartBeat.Pong.class, net.sopod.soim.data.msg.net.HeartBeat.Pong.Builder.class);
    }

    public static final int TIME_FIELD_NUMBER = 1;
    private long time_;
    /**
     * <code>int64 time = 1;</code>
     * @return The time.
     */
    @java.lang.Override
    public long getTime() {
      return time_;
    }

    public static final int DIFF_FIELD_NUMBER = 2;
    private int diff_;
    /**
     * <pre>
     * 收到时时间戳减Ping时间戳
     * </pre>
     *
     * <code>int32 diff = 2;</code>
     * @return The diff.
     */
    @java.lang.Override
    public int getDiff() {
      return diff_;
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
      if (time_ != 0L) {
        output.writeInt64(1, time_);
      }
      if (diff_ != 0) {
        output.writeInt32(2, diff_);
      }
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (time_ != 0L) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt64Size(1, time_);
      }
      if (diff_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(2, diff_);
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
      if (!(obj instanceof net.sopod.soim.data.msg.net.HeartBeat.Pong)) {
        return super.equals(obj);
      }
      net.sopod.soim.data.msg.net.HeartBeat.Pong other = (net.sopod.soim.data.msg.net.HeartBeat.Pong) obj;

      if (getTime()
          != other.getTime()) return false;
      if (getDiff()
          != other.getDiff()) return false;
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
      hash = (37 * hash) + TIME_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
          getTime());
      hash = (37 * hash) + DIFF_FIELD_NUMBER;
      hash = (53 * hash) + getDiff();
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static net.sopod.soim.data.msg.net.HeartBeat.Pong parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static net.sopod.soim.data.msg.net.HeartBeat.Pong parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static net.sopod.soim.data.msg.net.HeartBeat.Pong parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static net.sopod.soim.data.msg.net.HeartBeat.Pong parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static net.sopod.soim.data.msg.net.HeartBeat.Pong parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static net.sopod.soim.data.msg.net.HeartBeat.Pong parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static net.sopod.soim.data.msg.net.HeartBeat.Pong parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static net.sopod.soim.data.msg.net.HeartBeat.Pong parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static net.sopod.soim.data.msg.net.HeartBeat.Pong parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static net.sopod.soim.data.msg.net.HeartBeat.Pong parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static net.sopod.soim.data.msg.net.HeartBeat.Pong parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static net.sopod.soim.data.msg.net.HeartBeat.Pong parseFrom(
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
    public static Builder newBuilder(net.sopod.soim.data.msg.net.HeartBeat.Pong prototype) {
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
     * Protobuf type {@code proto.Pong}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:proto.Pong)
        net.sopod.soim.data.msg.net.HeartBeat.PongOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return net.sopod.soim.data.msg.net.HeartBeat.internal_static_proto_Pong_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return net.sopod.soim.data.msg.net.HeartBeat.internal_static_proto_Pong_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                net.sopod.soim.data.msg.net.HeartBeat.Pong.class, net.sopod.soim.data.msg.net.HeartBeat.Pong.Builder.class);
      }

      // Construct using net.sopod.soim.data.msg.net.HeartBeat.Pong.newBuilder()
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
        time_ = 0L;

        diff_ = 0;

        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return net.sopod.soim.data.msg.net.HeartBeat.internal_static_proto_Pong_descriptor;
      }

      @java.lang.Override
      public net.sopod.soim.data.msg.net.HeartBeat.Pong getDefaultInstanceForType() {
        return net.sopod.soim.data.msg.net.HeartBeat.Pong.getDefaultInstance();
      }

      @java.lang.Override
      public net.sopod.soim.data.msg.net.HeartBeat.Pong build() {
        net.sopod.soim.data.msg.net.HeartBeat.Pong result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public net.sopod.soim.data.msg.net.HeartBeat.Pong buildPartial() {
        net.sopod.soim.data.msg.net.HeartBeat.Pong result = new net.sopod.soim.data.msg.net.HeartBeat.Pong(this);
        result.time_ = time_;
        result.diff_ = diff_;
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
        if (other instanceof net.sopod.soim.data.msg.net.HeartBeat.Pong) {
          return mergeFrom((net.sopod.soim.data.msg.net.HeartBeat.Pong)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(net.sopod.soim.data.msg.net.HeartBeat.Pong other) {
        if (other == net.sopod.soim.data.msg.net.HeartBeat.Pong.getDefaultInstance()) return this;
        if (other.getTime() != 0L) {
          setTime(other.getTime());
        }
        if (other.getDiff() != 0) {
          setDiff(other.getDiff());
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
        net.sopod.soim.data.msg.net.HeartBeat.Pong parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (net.sopod.soim.data.msg.net.HeartBeat.Pong) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private long time_ ;
      /**
       * <code>int64 time = 1;</code>
       * @return The time.
       */
      @java.lang.Override
      public long getTime() {
        return time_;
      }
      /**
       * <code>int64 time = 1;</code>
       * @param value The time to set.
       * @return This builder for chaining.
       */
      public Builder setTime(long value) {
        
        time_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int64 time = 1;</code>
       * @return This builder for chaining.
       */
      public Builder clearTime() {
        
        time_ = 0L;
        onChanged();
        return this;
      }

      private int diff_ ;
      /**
       * <pre>
       * 收到时时间戳减Ping时间戳
       * </pre>
       *
       * <code>int32 diff = 2;</code>
       * @return The diff.
       */
      @java.lang.Override
      public int getDiff() {
        return diff_;
      }
      /**
       * <pre>
       * 收到时时间戳减Ping时间戳
       * </pre>
       *
       * <code>int32 diff = 2;</code>
       * @param value The diff to set.
       * @return This builder for chaining.
       */
      public Builder setDiff(int value) {
        
        diff_ = value;
        onChanged();
        return this;
      }
      /**
       * <pre>
       * 收到时时间戳减Ping时间戳
       * </pre>
       *
       * <code>int32 diff = 2;</code>
       * @return This builder for chaining.
       */
      public Builder clearDiff() {
        
        diff_ = 0;
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


      // @@protoc_insertion_point(builder_scope:proto.Pong)
    }

    // @@protoc_insertion_point(class_scope:proto.Pong)
    private static final net.sopod.soim.data.msg.net.HeartBeat.Pong DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new net.sopod.soim.data.msg.net.HeartBeat.Pong();
    }

    public static net.sopod.soim.data.msg.net.HeartBeat.Pong getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<Pong>
        PARSER = new com.google.protobuf.AbstractParser<Pong>() {
      @java.lang.Override
      public Pong parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new Pong(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<Pong> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<Pong> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public net.sopod.soim.data.msg.net.HeartBeat.Pong getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_proto_Ping_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_proto_Ping_fieldAccessorTable;
  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_proto_Pong_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_proto_Pong_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\031proto/net/HeartBeat.proto\022\005proto\"\024\n\004Pi" +
      "ng\022\014\n\004time\030\001 \001(\003\"\"\n\004Pong\022\014\n\004time\030\001 \001(\003\022\014" +
      "\n\004diff\030\002 \001(\005B*\n\033net.sopod.soim.data.msg." +
      "netB\tHeartBeatP\000b\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_proto_Ping_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_proto_Ping_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_proto_Ping_descriptor,
        new java.lang.String[] { "Time", });
    internal_static_proto_Pong_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_proto_Pong_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_proto_Pong_descriptor,
        new java.lang.String[] { "Time", "Diff", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
