package com.company;

import com.encryptor.ReadException;
import com.encryptor.WriteException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;

public class Base64AlgTest {
    @Test
    void sizesEq() throws ReadException, WriteException {
        SimpleEncBase64 simpleEncBase64 = new SimpleEncBase64();
        
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        simpleEncBase64.encrypt(true, new ByteArrayInputStream("qwe".getBytes()), output, new char[]{'a', 's', 'd'});
        byte[] newInp = output.toByteArray();
        ByteArrayOutputStream output2 = new ByteArrayOutputStream();

        simpleEncBase64.encrypt(false, new ByteArrayInputStream(newInp), output2, new char[]{'a', 's', 'd'});
        String res = output2.toString();

        Assertions.assertEquals("qwe", res);
    }

    @Test
    void keyIsSmallerInp() throws ReadException, WriteException {
        SimpleEncBase64 simpleEncBase64 = new SimpleEncBase64();
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        simpleEncBase64.encrypt(true, new ByteArrayInputStream("qwe".getBytes()), output, new char[]{'a', 's'});
        byte[] newInp = output.toByteArray();
        ByteArrayOutputStream output2 = new ByteArrayOutputStream();

        simpleEncBase64.encrypt(false, new ByteArrayInputStream(newInp), output2, new char[]{'a', 's'});
        String res = output2.toString();

        Assertions.assertEquals("qwe", res);
    }

    @Test
    void keyIsLongerInp() throws ReadException, WriteException {
        SimpleEncBase64 simpleEncBase64 = new SimpleEncBase64();
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        simpleEncBase64.encrypt(true, new ByteArrayInputStream("qwe".getBytes()), output, new char[]{'a', 's', 'd', 'f'});
        byte[] newInp = output.toByteArray();
        ByteArrayOutputStream output2 = new ByteArrayOutputStream();

        simpleEncBase64.encrypt(false, new ByteArrayInputStream(newInp), output2, new char[]{'a', 's', 'd', 'f'});
        String res = output2.toString();

        Assertions.assertEquals("qwe", res);
    }
}