package com.giant_monkey.dp;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    public final static String VIDEO_URL = "http://sites.google.com/site/ubiaccessmobile/sample_video.mp4";
    public final static int URL = 1;
    public final static int SDCARD = 2;
    VideoView videoView;
    String path;
    String path2;
    public int index = 0;
    public int downloadindex = 0;

    private List myList;

    private List downList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // VideoView init
        videoView = (VideoView) findViewById(R.id.videoView);
        // play();

        // db 확인
        // 파일 확인
        myList = new ArrayList();

        // 파일 추가 1
        myList.add("20170108_005018.mp4");
        myList.add("20170108_001456.mp4");

        downList = new ArrayList();

        for(int i=0; i<myList.size(); i++) {
            String filename = myList.get(i).toString();
            File file = new File("/sdcard/DCIM/" + filename);
            if(!file.exists())
                downList.add(filename);
        }


        // file download
        if(downList.size()>0) {
            String filename = downList.get(0).toString();
            download(filename);
        }

        if(downList.size()==0)
            play();



        // 주기적으로 서버에 상태 보고 저장



        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

/*        String sd = Environment.getExternalStorageDirectory().toString() + "/dp/";
        File file = new File(sd);
        File list[] = file.listFiles();
        myList = new ArrayList();
        for(int i=0; i<list.length; i++)
        {
            // Toast.makeText(MainActivity.this, sd+list[i].getName(), Toast.LENGTH_SHORT).show();
            if(list[i].isDirectory() == false && list[i].getName().indexOf(".mp4")>0)
                myList.add(sd + list[i].getName());

        }
*/

        //    File dir = new File("/digitalpost");
        //  if(!dir.exists())
        //    dir.mkdirs();
        //       File dir = new File(Environment.getExternalStorageDirectory()+"/digitalpost/");
        //     if(!dir.exists())
        //       dir.mkdir();


        // play();



        //   MediaController controller = new MediaController(MainActivity.this);
        //   videoView.setMediaController(controller);

        //   videoView.requestFocus();
/*
        int type = SDCARD;
        switch(type) {
            case URL:
                videoView.setVideoURI(Uri.parse(VIDEO_URL));
                break;
            case SDCARD:
                 path = Environment.getExternalStorageDirectory()+"/DCIM/20170107_222652.mp4";
                index = 0;
               //  path = Environment.getExternalStorageDirectory()+File.separator+"digitalpost/sample_video.mp4";
               // Toast.makeText(MainActivity.this, path, Toast.LENGTH_SHORT).show();
                videoView.setVideoPath(path);
                break;

        }
*/
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                // Toast.makeText(MainActivity.this, "동영상이 준비되었습니다.", Toast.LENGTH_SHORT).show();
                videoView.seekTo(0);
                videoView.start();



            }


        }) ;

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                //동영상 재생이 완료된 후 호출되는 메소드
                //Toast.makeText(MainActivity.this,"동영상 재생이 완료되었습니다.", Toast.LENGTH_SHORT).show();
  /*              if(index==0) {
                    path = Environment.getExternalStorageDirectory() + "/DCIM/20170107_222708.mp4";
                    index = 1;
                }

                else if(index == 1) {
                    path = Environment.getExternalStorageDirectory() + "/DCIM/20170107_222652.mp4";
                    index = 0;
                }

            //    Toast.makeText(MainActivity.this, path, Toast.LENGTH_SHORT).show();
                videoView.setVideoPath(path);
*/              play();
            }
        });
    }

    //    void play()
//    {
//        if(myList.size() == 0) {
//            videoView.setVideoURI(Uri.parse(VIDEO_URL));
//            Toast.makeText(MainActivity.this, "mp4 영상을 dp 폴더에 업로드 하세요.", Toast.LENGTH_LONG).show();
//        }
//        else {
//
//            if (index < myList.size()) {
//                String path = (String) myList.get(index);
//                videoView.setVideoPath(path);
//                ++index;
//            } else {
//                index = 0;
//                String path = (String) myList.get(index);
//                videoView.setVideoPath(path);
//                ++index;
//            }
//        }
//
//    }
    void play() {
        if(myList.size()>0) {
            if (myList.size() > index)
                videoView.setVideoPath("/sdcard/DCIM/" + myList.get(index).toString());
            else {
                index = 0;
                videoView.setVideoPath("/sdcard/DCIM/" + myList.get(index).toString());

            }
            ++index;
        }

    }

    void nextdownload()
    {
        ++downloadindex;
        if(downList.size()>downloadindex)
            download(downList.get(downloadindex).toString());
        else
            play();

    }
    void download(String filename) {
        Toast.makeText(MainActivity.this, filename+"을 다운로드 중입니다.", Toast.LENGTH_SHORT).show();
        new DownloadFileAsync(this).execute("http://hq.giant-monkey.com/"+filename,filename,"1");

    }
}
