package com.redis.lettucemod.api.sync;

import java.util.List;

import com.redis.lettucemod.timeseries.Aggregation;
import com.redis.lettucemod.timeseries.CreateOptions;
import com.redis.lettucemod.timeseries.GetResult;
import com.redis.lettucemod.timeseries.KeySample;
import com.redis.lettucemod.timeseries.RangeOptions;
import com.redis.lettucemod.timeseries.RangeResult;
import com.redis.lettucemod.timeseries.Sample;

public interface RedisTimeSeriesCommands<K, V> {

	String create(K key, CreateOptions<K, V> options);

	String alter(K key, CreateOptions<K, V> options);

	Long add(K key, long timestamp, double value);

	Long addAutoTimestamp(K key, double value);

	Long add(K key, long timestamp, double value, CreateOptions<K, V> options);

	Long addAutoTimestamp(K key, double value, CreateOptions<K, V> options);

	Long add(K key, Sample sample);

	Long add(K key, Sample sample, CreateOptions<K, V> options);

	@SuppressWarnings("unchecked")
	List<Long> madd(KeySample<K>... samples);

	Long incrby(K key, double value, Long timestamp, CreateOptions<K, V> options);

	Long decrby(K key, double value, Long timestamp, CreateOptions<K, V> options);

	Long incrbyAutoTimestamp(K key, double value, CreateOptions<K, V> options);

	Long decrbyAutoTimestamp(K key, double value, CreateOptions<K, V> options);

	String createrule(K sourceKey, K destKey, Aggregation aggregation);

	String deleterule(K sourceKey, K destKey);

	List<Sample> range(K key, RangeOptions options);

	List<Sample> revrange(K key, RangeOptions options);

	@SuppressWarnings("unchecked")
	List<RangeResult<K, V>> mrange(RangeOptions options, V... filters);

	@SuppressWarnings("unchecked")
	List<RangeResult<K, V>> mrevrange(RangeOptions options, V... filters);

	@SuppressWarnings("unchecked")
	List<RangeResult<K, V>> mrangeWithLabels(RangeOptions options, V... filters);

	@SuppressWarnings("unchecked")
	List<RangeResult<K, V>> mrevrangeWithLabels(RangeOptions options, V... filters);

	Sample tsGet(K key);

	@SuppressWarnings("unchecked")
	List<GetResult<K, V>> tsMget(V... filters);

	@SuppressWarnings("unchecked")
	List<GetResult<K, V>> tsMgetWithLabels(V... filters);

	List<Object> tsInfo(K key);

	List<Object> tsInfoDebug(K key);
}
