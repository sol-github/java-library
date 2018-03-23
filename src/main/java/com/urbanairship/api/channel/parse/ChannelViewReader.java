/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.channel.parse;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.urbanairship.api.channel.Constants;
import com.urbanairship.api.channel.model.ChannelType;
import com.urbanairship.api.channel.model.ChannelView;
import com.urbanairship.api.channel.model.ios.IosSettings;
import com.urbanairship.api.channel.model.open.OpenChannel;
import com.urbanairship.api.channel.model.web.WebSettings;
import com.urbanairship.api.client.UrbanAirshipClient;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.BooleanFieldDeserializer;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.common.parse.ListOfStringsDeserializer;
import com.urbanairship.api.common.parse.StringFieldDeserializer;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

public final class ChannelViewReader implements JsonObjectReader<ChannelView> {
    private static final Logger log = LoggerFactory.getLogger(UrbanAirshipClient.class);

    private final ChannelView.Builder builder;

    public ChannelViewReader() {
        builder = ChannelView.newBuilder();
    }

    public void readChannelId(JsonParser jsonParser) throws IOException {
        builder.setChannelId(StringFieldDeserializer.INSTANCE.deserialize(jsonParser, Constants.CHANNEL_ID));
    }

    public void readDeviceType(JsonParser jsonParser) throws IOException {
        builder.setChannelType(StringFieldDeserializer.INSTANCE.deserialize(jsonParser, Constants.DEVICE_TYPE));
    }

    public void readInstalled(JsonParser jsonParser) throws IOException {
        builder.setInstalled(BooleanFieldDeserializer.INSTANCE.deserialize(jsonParser, Constants.INSTALLED));
    }

    public void readOptIn(JsonParser jsonParser) throws IOException {
        builder.setOptIn(BooleanFieldDeserializer.INSTANCE.deserialize(jsonParser, Constants.OPT_IN));
    }

    public void readBackground(JsonParser jsonParser) throws IOException {
        builder.setBackground(BooleanFieldDeserializer.INSTANCE.deserialize(jsonParser, Constants.BACKGROUND));
    }

    public void readPushAddress(JsonParser jsonParser) throws IOException {
        builder.setPushAddress(jsonParser.readValueAs(String.class));
    }

    public void readCreated(JsonParser jsonParser) throws IOException {
        builder.setCreated(jsonParser.readValueAs(DateTime.class));
    }

    public void readLastRegistration(JsonParser jsonParser) throws IOException {
        builder.setLastRegistration(jsonParser.readValueAs(DateTime.class));
    }

    public void readAlias(JsonParser jsonParser) throws IOException {
        builder.setAlias(jsonParser.readValueAs(String.class));
    }

    public void readTags(JsonParser jsonParser) throws IOException {
        builder.addAllTags(ListOfStringsDeserializer.INSTANCE.deserialize(jsonParser, Constants.TAGS));
    }

    public void readTagGroups(JsonParser jsonParser) throws IOException {
        Map<String, Set<String>> mutableTagGroups = jsonParser.readValueAs(new TypeReference<Map<String, Set<String>>>() {});
        ImmutableMap<String, ImmutableSet<String>> tagGroups = immutableMapConverter(mutableTagGroups);
        builder.addAllTagGroups(tagGroups);
    }

    public void readIosSettings(JsonParser jsonParser) throws IOException {
        builder.setIosSettings(jsonParser.readValueAs(IosSettings.class));
    }

    public void readWeb(JsonParser jsonParser) throws IOException {
        builder.setWebSettings(jsonParser.readValueAs(WebSettings.class));
    }

    public void readOpenChannel(JsonParser jsonParser) throws IOException {
        builder.setOpenChannel(jsonParser.readValueAs(OpenChannel.class));
    }

    public void readAddress(JsonParser jsonParser) throws IOException {
        builder.setAddress(StringFieldDeserializer.INSTANCE.deserialize(jsonParser, Constants.ADDRESS));
    }

    @Override
    public ChannelView validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception e) {
            throw new APIParsingException(e.getMessage());
        }
    }

    private static ImmutableMap<String, ImmutableSet<String>> immutableMapConverter(Map<String, Set<String>> map) {
        ImmutableMap.Builder<String, ImmutableSet<String>> builder = ImmutableMap.builder();
        for (Map.Entry<String, Set<String>> entry : map.entrySet()) {
            builder.put(entry.getKey(), ImmutableSet.copyOf(entry.getValue()));
        }
        return builder.build();
    }

}
