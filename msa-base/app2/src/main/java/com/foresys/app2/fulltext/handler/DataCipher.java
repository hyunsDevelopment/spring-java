package com.foresys.app2.fulltext.handler;

public interface DataCipher {

    byte[] encrypt(byte[] bytes);

    byte[] decrypt(byte[] bytes);

}
