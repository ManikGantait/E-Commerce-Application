package com.retail.ecom.cache;

import java.time.Duration;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

public class CacheStore<T> {
	
	private Cache<String, T> cache;

	public CacheStore(int maxAge) {
		this.cache= CacheBuilder.newBuilder()
				.expireAfterWrite(Duration.ofMinutes(maxAge))
				.concurrencyLevel(Runtime.getRuntime().availableProcessors())
				.build();
		
	}
	public void add(String key,T value)
	{
		cache.put(key, value);
	}
	public  T get(String key)
	{
		return cache.getIfPresent(key);
	}
	private void remove(String key)
	{
		 cache.invalidate(key);
	}

}
