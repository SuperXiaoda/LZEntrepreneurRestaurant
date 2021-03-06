package cn.com.lz.entrepreneur.restaurant.config;

import com.bumptech.glide.load.Key;

import java.nio.ByteBuffer;
import java.security.MessageDigest;

public class IntegerVersionSignature implements Key {
    private Integer currentVersion;

    public IntegerVersionSignature(Integer currentVersion) {
        this.currentVersion = currentVersion;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof IntegerVersionSignature) {
            IntegerVersionSignature other = (IntegerVersionSignature) o;
            return currentVersion == other.currentVersion;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return currentVersion;
    }

    @Override
    public void updateDiskCacheKey(MessageDigest md) {
        md.update(ByteBuffer.allocate(Integer.SIZE).putInt(currentVersion).array());
    }
}