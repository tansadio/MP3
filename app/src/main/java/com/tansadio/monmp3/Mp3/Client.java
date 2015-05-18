package com.tansadio.monmp3.Mp3;

/**
 * Created by tansadio on 01/04/15.
 */
import java.util.ArrayList;
import java.util.Map;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import android.media.MediaPlayer;
import android.media.AudioManager;

public class Client {

    Ice.Communicator ic = null;
    LecteurMp3Prx serv = null;
    MediaPlayer mediaPlayer = null;
    int length;


    public Client() {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int status = 0;
        try {
            ic = Ice.Util.initialize();
            Ice.ObjectPrx base = ic.stringToProxy("SimplePrinter:default -h 192.168.0.17 -p 10000");
            serv = LecteurMp3PrxHelper.checkedCast(base);
            boolean e = true;
            int choix = -1;
            ArrayList items = new ArrayList();
            //Item [] item = new Item[6];
            /*/items.add(serv.getFilemp3());
            System.out.println("--->>>> ");
            for(int i=0; i<items.size(); i++){
                System.out.println(items.get(i));
            }
            System.out.println("<<<<<---");*/
            //System.out.println(serv.getFilemp3());



        } catch (Ice.LocalException e) {
            e.printStackTrace();
            status = 1;
        } catch (Exception e) {
            System.out.println("--->>>> ");
            System.err.println(e.getMessage());
            status = 1;
        }
    }

    public MediaPlayer getMediaPlayer(){return this.mediaPlayer;}

    public void playClient(String name){

        try {
            serv.selectedMusic(name);
            String url = serv.jouer(); // your URL here
            this.mediaPlayer = new MediaPlayer();
            System.out.println("Playing at :"+url);
            this.mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            this.mediaPlayer.setDataSource(url);
            this.mediaPlayer.prepare();
            //this.mediaPlayer.start();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void playClient(){

        try {

            if(this.mediaPlayer != null){

                //this.mediaPlayer.seekTo(this.length);
                this.mediaPlayer.start();
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void stopClient(){

        try {

            if(this.mediaPlayer != null){

                this.mediaPlayer.stop();
                this.mediaPlayer.release();
                this.mediaPlayer = null;
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void pauseClient(){

        try {

            if(this.mediaPlayer != null){

                this.mediaPlayer.pause();
                length = this.mediaPlayer.getCurrentPosition();
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    /*public Map<String,String> getPlaylist(){

        return serv.getList();
    }*/

    public void destroyClient(){

        if (ic != null) {
            // Clean up
            //
            try {
                ic.destroy();
            } catch (Exception e) {
                System.err.println(e.getMessage());

            }
        }
        System.exit(0);
    }
}
