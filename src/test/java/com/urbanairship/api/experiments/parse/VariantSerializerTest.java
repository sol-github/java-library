package com.urbanairship.api.experiments.parse;

import com.urbanairship.api.experiments.model.VariantPushPayload;
import com.urbanairship.api.experiments.model.Variant;
import com.urbanairship.api.push.model.notification.Notification;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class VariantSerializerTest {

    private static final ObjectMapper MAPPER = ExperimentObjectMapper.getInstance();

    @Test
    public void testVariantSerializer() throws Exception {

        Variant variant = Variant.newBuilder()
                .setPushPayload(VariantPushPayload.newBuilder()
                        .setNotification(Notification.newBuilder()
                                .setAlert("Hello there")
                                .build()
                        )
                        .build())
                .build();

        String variantSerialized = MAPPER.writeValueAsString(variant);
        Variant variantFromJson = MAPPER.readValue(variantSerialized, Variant.class);

        String variantString =
                "{" +
                        "\"push\":{\"notification\":{\"alert\":\"Hello there\"}}" +
                        "}";

        JsonNode fromObject = MAPPER.readTree(variantSerialized);
        JsonNode fromString = MAPPER.readTree(variantString);

        assertEquals(fromObject, fromString);
        assertEquals(variantFromJson, variant);
    }
}
