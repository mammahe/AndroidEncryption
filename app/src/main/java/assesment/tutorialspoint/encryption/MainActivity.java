package assesment.tutorialspoint.encryption;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FileInputStream fis = null;
        try {
            fis = this.openFileInput("abc.mp4");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        File outfile = new File("abcd.mp4");
        int read;
        if(!outfile.exists())
            try {
                outfile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        File decfile = new File("abcd.avi");
        if(!decfile.exists())
            try {
                decfile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(outfile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        FileInputStream encfis = null;
        try {
            encfis = new FileInputStream(outfile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        FileOutputStream decfos = null;
        try {
            decfos = new FileOutputStream(decfile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Cipher encipher = null;
        try {
            encipher = Cipher.getInstance("AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
        Cipher decipher = null;
        try {
            decipher = Cipher.getInstance("AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
        KeyGenerator kgen = null;
        try {
            kgen = KeyGenerator.getInstance("AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        //byte key[] = {0x00,0x32,0x22,0x11,0x00,0x00,0x00,0x00,0x00,0x23,0x00,0x00,0x00,0x00,0x00,0x00,0x00};
        SecretKey skey = kgen.generateKey();
        //Lgo
        try {
            encipher.init(Cipher.ENCRYPT_MODE, skey);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        CipherInputStream cis = new CipherInputStream(fis, encipher);
        try {
            decipher.init(Cipher.DECRYPT_MODE, skey);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        CipherOutputStream cos = new CipherOutputStream(decfos,decipher);
        try {
            while((read = cis.read())!=-1)
            {
                fos.write((char)read);
                fos.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            while((read=encfis.read())!=-1)
            {
                cos.write(read);
                cos.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            cos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
