package com.urbanairship.api.channel.email.parse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanairship.api.channel.model.email.RegisterEmailChannelResponse;
import com.urbanairship.api.push.parse.PushObjectMapper;
import org.junit.Test;

import java.io.IOException;


public class EmailRegisterChannelResponseDeserializerTest {

    String responseString = "{\n" +
            "    \"ok\": true,\n" +
            "    \"channel_id\": \"251d3318-b3cb-4e9f-876a-ea3bfa6e47bd\"\n" +
            "}";

    @Test
    public void testRegisterEmailChannelResponse() throws IOException {

        ObjectMapper mapper = PushObjectMapper.getInstance();
        try {
            RegisterEmailChannelResponse response = mapper.readValue(responseString, RegisterEmailChannelResponse.class);
            org.junit.Assert.assertTrue("Error in response channelId",
                    response.getChannelId().get().equals("251d3318-b3cb-4e9f-876a-ea3bfa6e47bd"));
            org.junit.Assert.assertTrue("Error in response status",
                    response.getOk());
        } catch (IOException ex) {
            org.junit.Assert.fail("Exception in RegisterEmailResponseTest Message: " + ex.getMessage());
        }
    }
}
