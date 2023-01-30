package pk.ketanprabhu.pubsub.util;

import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutureCallback;
import com.google.api.core.ApiFutures;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.PubsubMessage;
import com.google.pubsub.v1.TopicName;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class PubSubUtil {
	@Value("${gcp.project.id}")
    private String projectId;
    @Value("${gcp.pubSub.topic.id}")
    private String topicId;

    @Value("${gcp.pubSub.sub.id")
    private String subId;

    public void sendMessage(String userSignUp) throws IOException, InterruptedException {
        Publisher publisher = null;
        try {
            publisher = Publisher.newBuilder(TopicName.of(projectId, topicId)).build();
            ByteString data = ByteString.copyFromUtf8(userSignUp.toString());
            PubsubMessage pubsubMessage = PubsubMessage.newBuilder().setData(data).build();
            ApiFuture<String> messageIdFuture = publisher.publish(pubsubMessage);
            ApiFutures.addCallback(messageIdFuture, new ApiFutureCallback<String>() {
                public void onSuccess(String messageId) {
                    System.out.println("published with message id: " + messageId);
                }

                public void onFailure(Throwable t) {
                    System.out.println("failed to publish: " + t);
                }
            }, MoreExecutors.directExecutor());
            //...
        }catch (Exception e){
            System.out.println(" Error : "+e.getMessage());
        }finally {
            if (publisher != null) {
                publisher.shutdown();
                publisher.awaitTermination(1, TimeUnit.MINUTES);
            }
        }
    }
}
