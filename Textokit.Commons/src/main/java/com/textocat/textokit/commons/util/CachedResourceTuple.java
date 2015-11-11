/**
 *
 */
package com.textocat.textokit.commons.util;

/**
 * @author Rinat Gareev
 */
public class CachedResourceTuple<R> {

    private final CacheKey cacheKey;
    private final R resource;

    public CachedResourceTuple(CacheKey cacheKey, R resource) {
        this.cacheKey = cacheKey;
        this.resource = resource;
    }

    public CacheKey getCacheKey() {
        return cacheKey;
    }

    public R getResource() {
        return resource;
    }
}
