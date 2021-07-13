package com.company;

import com.encryptor.EncryptorLogic;
import com.encryptor.ReadException;
import com.encryptor.WriteException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Base64;

@Component
public class SimpleEncBase64 implements EncryptorLogic {

    public void encrypt(boolean encrypt, InputStream inputStream, OutputStream outputStream, char[] password) throws ReadException, WriteException {
        long lengthInputStream;
        try {
            lengthInputStream = inputStream.available();
        } catch (IOException e) {
            throw new ReadException("something wrong with InputStream");
        }
        encryptAlg(encrypt, inputStream, outputStream, lengthInputStream, password, 1024);
    }

    private void encryptAlg(boolean encrypt, InputStream inputStream, OutputStream outputStream, long sourceFileLength, char[] key, int bufSize) throws ReadException, WriteException {
        byte[] buf;

        if (sourceFileLength > bufSize || sourceFileLength == 0) {
            buf = new byte[bufSize];
        } else {
            buf = new byte[(int) sourceFileLength];
        }
        int keyPos = 0;

        while (true) {
            int read;
            try {
                if (!(inputStream.available() > 0)) break;
                read = inputStream.read(buf, 0, buf.length);
            } catch (IOException e) {
                throw new ReadException("Can't read input", e);
            }

            if (encrypt) {
                for (int i = 0; i < read; i++) {
                    buf[i] = ((byte) (buf[i] + key[keyPos]));
                    keyPos = (keyPos + 1) % key.length;
                }
                buf = Base64.getEncoder().encode(buf);
            } else {
                buf = Base64.getDecoder().decode(buf);
                for (int i = 0; i < buf.length; i++) {
                    buf[i] = ((byte) (buf[i] - key[keyPos]));
                    keyPos = (keyPos + 1) % key.length;
                }
            }

            try {
                outputStream.write(buf);
            } catch (IOException e) {
                throw new WriteException("Can't write to output", e);
            }
        }
        try {
            outputStream.close();
        } catch (IOException e) {
            throw new WriteException("Can't close outputStream", e);
        }
    }
}
