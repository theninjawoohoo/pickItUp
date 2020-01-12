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

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.List;

import Models.UserSingleton;
import Models.detectLabels;

public class MachineVision extends AppCompatActivity {

    private String pStream = "placeholder";
    private static final String TAG = "MachineActivity";
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
                    detectLabels.detectLabels(filePath);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                List<String> listOfLabels = detectLabels.getLabels();
                Log.d(TAG, "onClick: " + listOfLabels.get(0));
                Toast.makeText(MachineVision.this, listOfLabels.get(0), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
