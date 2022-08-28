package org.yasara.event.publisher.azure;

import com.azure.messaging.eventhubs.EventData;
import com.azure.messaging.eventhubs.EventDataBatch;
import com.azure.messaging.eventhubs.EventHubClientBuilder;
import com.azure.messaging.eventhubs.EventHubProducerClient;
import com.google.gson.Gson;
import org.yasara.event.publisher.azure.model.LoginInfo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
public class UserLoginController {

    static String connectionString = "<ENTER-CONNECTION-STRING>";
    static String eventHubName = "<ENTER-EVENTHUB-NAME>";

    @RequestMapping("/login")
    public String sendLoginInfo() {

        publishEvents();
        return "All login events published";
    }

    /**
     * Code sample for creating event batches.
     *
     * @throws IllegalArgumentException if the EventData is bigger than the max batch size.
     */
    public void publishEvents() {

        List<EventData> allEvents = new ArrayList<>();
        Random rand = new Random();
        for (int i = 1; i < 11; i++) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Timestamp loginTimestamp = new Timestamp(System.currentTimeMillis());
            Timestamp publishTimestamp = new Timestamp(System.currentTimeMillis());
            LoginInfo info = new LoginInfo();
            info.setLoginTimestamp(simpleDateFormat.format(loginTimestamp));
            info.setPublishTimestamp(simpleDateFormat.format(publishTimestamp));
            info.setUserstoreDomain("userstore" + (i / 2));
            info.setTenantDomain("tenant" + (i % 3));
            info.setEventType("userLogin");
            info.setuserId("userId" + rand.nextInt(1000));
            info.setEventId("event" + rand.nextInt(100000));

            String jsonString = new Gson().toJson(info);
            System.out.println("=========== creating login event : " + jsonString);
            allEvents.add(new EventData(jsonString));
        }
        publish(allEvents);
        System.out.println("=========== published all the login events ===========");
    }

    /**
     * Code sample for publishing events.
     *
     * @throws IllegalArgumentException if the EventData is bigger than the max batch size.
     */
    public static void publish(List<EventData> allEvents) {

        // Create a producer client.
        EventHubProducerClient producer = new EventHubClientBuilder()
                .connectionString(connectionString, eventHubName)
                .buildProducerClient();

        // Create a batch.
        EventDataBatch eventDataBatch = producer.createBatch();

        System.out.println("=========== publishing login events ===========");
        for (EventData eventData : allEvents) {
            // Try to add the event from the array to the batch.
            if (!eventDataBatch.tryAdd(eventData)) {
                // If the batch is full, send it and then create a new batch.
                producer.send(eventDataBatch);
                eventDataBatch = producer.createBatch();

                // Try to add that event that couldn't fit before.
                if (!eventDataBatch.tryAdd(eventData)) {
                    throw new IllegalArgumentException("Event is too large for an empty batch. Max size: "
                            + eventDataBatch.getMaxSizeInBytes());
                }
            }
        }
        // Send the last batch of remaining events.
        if (eventDataBatch.getCount() > 0) {
            producer.send(eventDataBatch);
        }
        producer.close();
    }
}
