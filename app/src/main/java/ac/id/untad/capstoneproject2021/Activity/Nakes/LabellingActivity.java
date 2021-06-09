package ac.id.untad.capstoneproject2021.Activity.Nakes;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.Result;

import ac.id.untad.capstoneproject2021.Activity.MainActivity;
import ac.id.untad.capstoneproject2021.R;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class LabellingActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {


    private ZXingScannerView addScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_labelling);
        addScannerView = new ZXingScannerView(this);
        setContentView(addScannerView);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Labelling Person");
        }
        addScannerView.setResultHandler(this);
        addScannerView.startCamera();
    }


    @Override
    public void handleResult(Result rawResult) {
        Log.v("TAG", rawResult.getText());
        Log.v("TAG", rawResult.getBarcodeFormat().toString());
        final String setMessage = rawResult.getText();
        addScannerView.resumeCameraPreview(this);
        //atur kemana data akan pergi atau di set
        Intent intent = new Intent(LabellingActivity.this, MainActivity.class);
       // intent.putExtra(LabellingActivity.EXTRA_USERi, setMessage);
        startActivity(intent);
    }

}