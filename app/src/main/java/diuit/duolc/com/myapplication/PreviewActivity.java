package diuit.duolc.com.myapplication;

import android.app.Activity;
import android.os.Bundle;

import com.duolc.DiuitMessage;
import com.duolc.diuitapi.messageui.message.DiuitMessageContentFactory;
import com.duolc.diuitapi.messageui.preview.DiuitMessagePreviewFactory;

/**
 * Created by duolC on 5/20/16.
 */
public class PreviewActivity extends Activity {

    private DiuitMessagePreviewFactory previewFactory;
    private DiuitMessage currentMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.initializeView();
        this.currentMessage = Utils.selectedMessage;

        DiuitMessageContentFactory.Type type = DiuitMessageContentFactory.getType(this.currentMessage);
        /*
        if(type.equals(DiuitMessageContentFactory.Type.IMAGE)) {
            this.previewFactory.bindMessage(this.currentMessage).image().setAttributes(R.style.DiuitMessageImagePreviewDefault).load();
        } else if(type.equals(DiuitMessageContentFactory.Type.WEB)) {
            this.previewFactory.bindMessage(this.currentMessage).web().setAttributes(R.style.DiuitMessageWebPreviewDefault).load();
        }
        */
        this.previewFactory.bindMessage(this.currentMessage).random();
    }

    private void initializeView() {
        this.setContentView(R.layout.activity_preview);
        this.previewFactory = (DiuitMessagePreviewFactory) this.findViewById(R.id.diuitPreview);
    }

}
