package edu.uci.ics.luisae.service.idm.models;

import edu.uci.ics.luisae.service.idm.security.Crypto;
import org.apache.commons.codec.binary.Hex;

public class SaltAndHash {
    private byte[] salt;
    private char[] pword;
    private byte[] hashedPword;
    private String encodedSalt;
    private String encodedPword;
    public SaltAndHash(char[] pword){
        salt = Crypto.genSalt();
        this.pword = pword;
        hashedPword = Crypto.hashPassword(pword, salt, Crypto.ITERATIONS, Crypto.KEY_LENGTH);
        encodedSalt = Hex.encodeHexString(salt);
        encodedPword = Hex.encodeHexString(hashedPword);
    }
    public SaltAndHash(char[] pword, byte[] salt){
        this.salt = salt;
        this.pword = pword;
        hashedPword = Crypto.hashPassword(pword, salt, Crypto.ITERATIONS, Crypto.KEY_LENGTH);
        encodedSalt = Hex.encodeHexString(salt);
        encodedPword = Hex.encodeHexString(hashedPword);
    }

    public String getEncodedPword() {
        return encodedPword;
    }

    public String getEncodedSalt() {
        return encodedSalt;
    }

    public byte[] getHashedPword() {
        return hashedPword;
    }

    public char[] getPword() {
        return pword;
    }

    public byte[] getSalt() {
        return salt;
    }
}
