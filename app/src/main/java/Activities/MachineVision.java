package Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.myapplication.R;
import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.BatchAnnotateImagesResponse;
import com.google.cloud.vision.v1.EntityAnnotation;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Image;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.protobuf.ByteString;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import Models.UserSingleton;

public class MachineVision extends AppCompatActivity {

    private String pStream = "placeholder";
    private final String TAG = "MachineActivity";
    private List<String> listOfLabels = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_machine_vision);
        setUpLeaderBoardButton();
    }

    private void setUpLeaderBoardButton() {
        Button leaderBoard = (Button) findViewById(R.id.submitai);
        String filePath = UserSingleton.getInstance(getApplicationContext()).getPhotoPath();
        Log.d(TAG, "filePath: " + filePath);
        leaderBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    detectLabels(filePath);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public void detectLabels(String filePath) throws Exception {
        List<AnnotateImageRequest> requests = new ArrayList<>();

        ByteString imgBytes = ByteString.readFrom(new FileInputStream(filePath));

        Image img = Image.newBuilder().setContent(imgBytes).build();
        Feature feat = Feature.newBuilder().setType(Feature.Type.LABEL_DETECTION).build();
        AnnotateImageRequest request =
                AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
        requests.add(request);

        try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
            BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
            List<AnnotateImageResponse> responses = response.getResponsesList();

            for (AnnotateImageResponse res : responses) {
                final float THRESHOLD = 0f;

                // For full list of available annotations, see http://g.co/cloud/vision/docs
                for (EntityAnnotation annotation : res.getLabelAnnotationsList()) {
                    // only place confidence level over threshold into labels
                    annotation.getAllFields().forEach((k, v) -> Log.d("annotation", String.format("%s : %s\n", k, v.toString())));
                    String tag = annotation.getDescription();
                    float confidence = annotation.getScore();

                    if (confidence > THRESHOLD) {
                        listOfLabels.add(tag);
                    }
                }
            }
        }
    }

}
