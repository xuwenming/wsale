package jb.util.redis;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.SortParameters.Order;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.core.query.SortQuery;
import org.springframework.data.redis.core.query.SortQueryBuilder;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class RedisUtil {
	
	@Resource
	private RedisTemplate<Serializable, Serializable> redisTemplate;
	
	private ValueOperations<Serializable, Serializable> valueOps = null;
	
	private ListOperations<Serializable, Serializable> listOps = null;
	
	private ZSetOperations<Serializable, Serializable> zSetOps = null;
	
	private SetOperations<Serializable, Serializable> setOps = null;
	
	private HashOperations<Serializable, Object, Object> hashOps = null;
	

	//是否存在键为key的数据
	public boolean hasKey(String key) {
		return redisTemplate.hasKey(key);
	}

	//正则表达式 读取key 对数据库性能影响很大
    @Deprecated
	public Set keys(String pattern) {
		return (Set) redisTemplate.keys(pattern);
	}
    
    /**
     * 设置某个key的有效时间
     * @param key 
     * @param timeout 超时时长
     * @param unit    时间单位
     * @return        true设置成功 false设置失败
     */
    public boolean expire(String key, long timeout, TimeUnit unit) {
    	return redisTemplate.expire(key, timeout, unit);
    }

	//得到某key有效时长
	public long getExpire(String key) {
		return redisTemplate.getExpire(key);
	}

 	//取消某key过期,持久化存储
	public boolean persist(String key) {
		return redisTemplate.persist(key);
	}

	//删除键为key的键值对
	public void delete(String key) {
		redisTemplate.delete(key);
	}

	//String数据类型 存储一对键值 支持value为非String类型
	public void set(String key, Serializable value) {
		valueOps = redisTemplate.opsForValue();
		valueOps.set(key, value);
	}
	
	//String数据类型 存储一对键值并设置有效时间 支持value为非String类型
	public void set(String key, Serializable value, long timeout, TimeUnit unit) {
		valueOps = redisTemplate.opsForValue();
		valueOps.set(key, value, timeout, unit);
	}
    
	//String数据类型 存储一对键值 仅支持value为String类型
	public void setString(final String key, final String value) {
		redisTemplate.execute(new RedisCallback<Serializable>() {
			public Serializable doInRedis(RedisConnection connection)throws DataAccessException {
				connection.set(
						redisTemplate.getStringSerializer().serialize(key),
						redisTemplate.getStringSerializer().serialize(value));
				return null;
			}
		});
	}
	
	//String数据类型  将value追加到已存在的某key的value值末尾
	public void append(String key, String value) {
		valueOps = redisTemplate.opsForValue();
		valueOps.append(key, value);
	}
	
	/**
	 * /**
	 * String数据类型   如果value存储的是Integer类型值则增加
	 * @param  key
	 * @param  value
	 * @return 递增后的新增
	 * @throws org.springframework.dao.InvalidDataAccessApiUsageException value不是Integer类型时报错
	 */
	public long increment(String key, long value) throws InvalidDataAccessApiUsageException {
		valueOps = redisTemplate.opsForValue();
		return valueOps.increment(key, value);
	}
	
	/**
	 * String数据类型   读取键为key的值仅支持value为String类型
	 * @param  key
	 * @return 字符串
	 */
	public Serializable getString(final String key) {
		return redisTemplate.execute(new RedisCallback<Serializable>() {
			@Override
			public Serializable doInRedis(RedisConnection connection)throws DataAccessException {
				byte[] byteKey = redisTemplate.getStringSerializer().serialize(key);
				if (connection.exists(byteKey)) {
					byte[] value = connection.get(byteKey);
					String strValue = redisTemplate.getStringSerializer().deserialize(value);
					return strValue;
				}
				return null;
			}
		});
	}
	
	/**
	 * String数据类型   读取键为key的值 支持value非String类型
	 * @param  key
	 * @return value值
	 */
	public Serializable get(String key) {
		valueOps = redisTemplate.opsForValue();
		return valueOps.get(key);
	}
	
	
	
	/**
	 * List数据类型  新增Key对应的list头部新增元素
	 * @param key
	 * @param objValue  可序列话的对象
	 * @return          插入后List中元素的数量 
	 */
	public long setList(String key, String objValue) {
		listOps = redisTemplate.opsForList();
		return listOps.leftPush(key, objValue);
	}
	
	/**
	 * List数据类型 读取某索引范围内key对应的list
	 * @param key
	 * @param beginIndex  开始位置
	 * @param endIndex    结束位置
	 * @return            数据集合
	 */
	
	public List getList(String key, long beginIndex, long endIndex) {
		listOps = redisTemplate.opsForList();
		return (List) listOps.range(key, beginIndex, endIndex);
	}
	
	public List getReverseList(String key, long beginIndex, long endIndex) {
		listOps = redisTemplate.opsForList();
		SortQuery<Serializable> query = SortQueryBuilder.sort((Serializable)key).order(Order.ASC).limit(beginIndex, endIndex).build();
		return redisTemplate.sort(query);
	}
	
	/**
	 * List数据类型 读取key对应所有List类型的数据
	 * @param  key
	 * @return 数据集合
	 */
	public List getAllList(String key) {
		return getList(key, 0, -1);
	}
	
	/**
	 * List数据类型 移出List头部第一个元素
	 * @param  key
	 * @return 元素
	 */
	public Serializable popList(String key) {
		listOps = redisTemplate.opsForList();
		return listOps.leftPop(key);
	}
	
	/**
	 * List数据类型 移出List中的value元素
	 * @param  key
	 * @param  value
	 * @return null
	 */
	public void removeListForValue(String key, String value) {
		listOps = redisTemplate.opsForList();
		listOps.remove(key, 1l, value);
	}
	
	/**
	 * List数据类型 移出List下标从startIndex到endIndexr之外的值
	 * @param  key
	 * @return null
	 */
	public void removeListForIndex(String key, Long startIndex, Long endIndex) {
		listOps = redisTemplate.opsForList();
		listOps.trim(key, startIndex, endIndex);
	}
	
	/**
	 * List数据类型 读取key绑定的list集合大小
	 * @param  key
	 * @return 集合大小
	 */
	public long getListCount(String key) {
		listOps = redisTemplate.opsForList();
		return listOps.size(key);
	}
	
	/**
	 * Sort-Set数据类型 key绑定的set里新增value元素
	 * @param key
	 * @param objValue  可序列话的对象
	 * @param score     得分
	 * @return          true插入成功 false失败（元素已经存在）
	 */
	public boolean addZSet(String key, Serializable objValue, Double score) {
		zSetOps = redisTemplate.opsForZSet();
		return zSetOps.add(key, objValue, score);
	}
	
	/**
	 * Sort-Set数据类型 只增加key对应的分数,若key和元素不存在会先创建再增加
	 * @param key
	 * @param objValue
	 * @param score     新增分数
	 * @return          最新分数
	 */
	public Double incrementZSetScore(String key, Serializable objValue, Double score) {
		zSetOps = redisTemplate.opsForZSet();
		return zSetOps.incrementScore(key, objValue, score);
	}
	
	/**
	 * Sort-Set数据类型  读取key值的元素个数
	 * @param  key 
	 * @return 元素个数
	 */
	public long getZSetCount(String key) {
		zSetOps = redisTemplate.opsForZSet();
		return zSetOps.size(key);
	}
	
	/**
	 * Sort-Set数据类型  读取minScore - maxScore 分数范围内的元素个数 
	 * @param key 
	 * @param minScore 最小分数
	 * @param maxScore 最大分数
	 */
	public long getZSetCountByScore(String key, Double minScore, Double maxScore) {
		zSetOps = redisTemplate.opsForZSet();
		return zSetOps.count(key, minScore, maxScore);
	}
	
	/**
	 * Sort-Set数据类型   读取beginIndex - endIndex 索引范围内value集合  低到高排序
	 * @param key
	 * @param beginIndex 开始索引
	 * @param endIndex   结束索引
	 * @return           Set数据集合
	 */
	
	public Set getZSetRang(String key, long beginIndex, long endIndex) {
		zSetOps = redisTemplate.opsForZSet();
		return (Set) zSetOps.range(key, beginIndex, endIndex);
	}
	
	/**
	 * Sort-Set数据类型   读取beginIndex - endIndex 索引范围内value集合  高到底排序
	 * @param key
	 * @param beginIndex 开始索引
	 * @param endIndex   结束索引
	 * @return           Set数据集合
	 */
	
	public Set getZSetRevRang(String key, long beginIndex, long endIndex) {
		zSetOps = redisTemplate.opsForZSet();
		return (Set) zSetOps.reverseRange(key, beginIndex, endIndex);
	}
	
	/**
	 * Sort-Set数据类型   读取value存储全部value集合
	 * @param  key
	 * @return Set数据集合
	 */
	public Set getAllZSetRang(String key) {
		return getZSetRang(key, 0, -1);
	}
	
	/**
	 * Sort-Set数据类型  读取minScore - maxScore 分数范围内value集合
	 * @param key
	 * @param minScore 开始分数
	 * @param maxScore 结束分数
	 * @return         Set数据集合
	 */
	
	public Set getZSetRangByScore(String key, Double minScore, Double maxScore) {
		zSetOps = redisTemplate.opsForZSet();
		return (Set) zSetOps.rangeByScore(key, minScore, maxScore);
	}
	
	/**
	 * Sort-Set数据类型  读取value中存储某元素的分数
	 * @param key
	 * @param obj set中一个对象
	 * @return    返回对象所在集合的分数
	 */
	public Double getZSetScore(String key, Object obj) {
		zSetOps = redisTemplate.opsForZSet();
		Double score =  zSetOps.score(key, obj);
		if (score == null) return 0.0;
		else return score;
	}
	
	/**
	 * Sort-Set数据类型   读取某元素所在value集合的索引
	 * @param  key
	 * @param  obj
	 * @return 返回对象所在集合的索引
	 */
	public long getZSetRank(String key, Object obj) {
		zSetOps = redisTemplate.opsForZSet();
		return zSetOps.rank(key, obj);
	}
	
	/**
	 * Sort-Set数据类型   删除value集合中某元素
	 * @param  key 
	 * @param  obj 元素对象
	 * @return true成功 false失败 
	 */
	public boolean removeZSet(String key, Object obj) {
		zSetOps = redisTemplate.opsForZSet();
		return zSetOps.remove(key, obj) > 0;
	}
	
	/**
	 * Sort-Set数据类型    删除value集合中beginIndex - endIndex 范围的元素
	 * @param key
	 * @param beginIndex 开始索引
	 * @param endIndex   结束索引
	 * @return           被删除的元素数量
	 */
	public long removeZSetRange(String key, long beginIndex, long endIndex) {
		zSetOps = redisTemplate.opsForZSet();
		return zSetOps.removeRange(key, beginIndex, endIndex);
	}
	
	/**
	 * Sort-Set数据类型    删除value集合中minScore - maxScore 分数范围的元素
	 * @param key
	 * @param minScore 起始分数
	 * @param maxScore 结束分数
	 * @return         被删除的元素数量
	 */
	public long removeZSetRangeByScore(String key,  Double minScore, Double maxScore) {
		zSetOps = redisTemplate.opsForZSet();
		return zSetOps.removeRangeByScore(key, minScore, maxScore);
	}
	
	
	/**
	 * Set数据类型   key绑定的set集合增加元素value
	 * @param  key
	 * @param  value
	 * @return true成功 false失败
	 */
	public boolean addSet(String key, Serializable value) {
		setOps = redisTemplate.opsForSet();
		return setOps.add(key, value) > 0;
	}
	
	/**
	 * Set数据类型   key绑定的set集合是否有元素value
	 * @param  key
	 * @param  value
	 * @return true成功 false失败
	 */
	public boolean existMemberSet(String key, Serializable value) {
		setOps = redisTemplate.opsForSet();
		return setOps.isMember(key, value);
	}
	
	/**
	 * Set数据类型 读取key绑定集合的所有元素
	 * @param  key
	 * @return set集合
	 */
	
	public Set getAllSet(String key) {
		setOps = redisTemplate.opsForSet();
		return (Set) setOps.members(key);
	}
	
	/**
	 * Set数据类型 读取key不同于otherKey集合的元素
	 * @param  key
	 * @param  otherKey
	 * @return set集合
	 */
	
	public Set getDiffSet(String key, String otherKey) {
		setOps = redisTemplate.opsForSet();
		return (Set) setOps.difference(key, otherKey);
	}
	
	/**
	 * Set数据类型 读取key与otherKey集合的交集元素
	 * @param  key
	 * @param  otherKey
	 * @return set集合
	 */
	
	public Set getInterSet(String key, String otherKey) {
		setOps = redisTemplate.opsForSet();
		return (Set) setOps.intersect(key, otherKey);
	}
	
	/**
	 * Set数据类型 读取key与otherKey集合的并集元素
	 * @param  key
	 * @param  otherKey
	 * @return set集合
	 */
	
	public Set getUnionSet(String key, String otherKey) {
		setOps = redisTemplate.opsForSet();
		return (Set) setOps.union(key, otherKey);
	}
	
	
	/**
	 * Hash数据类型   key绑定的hash集合新增元素
	 * @param key
	 * @param hashKey 
	 * @param hashValue
	 */
	public void putHash(String key, Object hashKey, Object hashValue) {
		hashOps = redisTemplate.opsForHash();
		hashOps.put(key, hashKey, hashValue);
	}
	
	/**
	 * Hash数据类型  批量 key绑定的hash集合新增元素
	 * @param key
	 * @param map
	 */
	public void putHash(String key, Map<Object,Object> map) {
		hashOps = redisTemplate.opsForHash();
		hashOps.putAll(key, map);
	}
	
	/**
	 * Hash数据类型  查找 key绑定的hash集合中hashKey 的元素是否存在
	 * @param  key
	 * @param  hashKey
	 * @return true存在 false不存在
	 */
	public boolean hasHashKey(String key, Object hashKey) {
		hashOps = redisTemplate.opsForHash();
		return hashOps.hasKey(key, hashKey);
	}
	
	/**
	 * Hash数据类型  查找 key绑定的hash集合中hashKey的值
	 * @param key
	 * @param hashKey
	 * @return
	 */
	public Object getHashValue(String key, Object hashKey) {
		hashOps = redisTemplate.opsForHash();
		return hashOps.get(key, hashKey);
	}
	
	/**
	 * Hash数据类型  查找 key绑定的hash集合的大小
	 * @param key
	 * @return
	 */
	public long getHashSize(String key) {
		hashOps = redisTemplate.opsForHash();
		return hashOps.size(key);
	}
	
	/**
	 * Hash数据类型 读取 key绑定的hash集合
	 * @param  key
	 * @return map集合 key-value
	 */
	public Map<Object,Object> getHash(String key) {
		hashOps = redisTemplate.opsForHash();
		return hashOps.entries(key);
	}
	
	/**
	 * Hash数据类型 读取 key绑定的hash集合的keys
	 * @param  key
	 * @return set集合
	 */
	
	public Set getHashKeys(String key) {
		hashOps = redisTemplate.opsForHash();
		return (Set) hashOps.keys(key);
	}
	
	public RedisTemplate<Serializable, Serializable> getRedisTemplate() {
		return redisTemplate;
	}
	
	public void setRedisTemplate(RedisTemplate<Serializable, Serializable> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	public void hincreby(String key,Object field,Integer delta){
		hashOps = redisTemplate.opsForHash();
		hashOps.increment(key,field,delta);
	}

	public Map<String, Map<byte[], byte[]>> hGetAll(final List<String> keys){
		List<Object> results = redisTemplate.executePipelined(new RedisCallback<Map<byte[], byte[]>>() {
			@Override
			public Map<byte[], byte[]> doInRedis(RedisConnection redisConnection) throws DataAccessException {
				for (String key : keys) {
					redisConnection.hGetAll(key.getBytes());
				}
				return null;
			}
		});
		Map<String, Map<byte[], byte[]>> resultMap = new HashMap();
		for(int i=0;i<keys.size();i++){
			resultMap.put(keys.get(i),(Map<byte[], byte[]>) results.get(i));
		}
		return resultMap;
	}

	public void delete(List<String> keys){
		redisTemplate.delete((Collection)keys);
	}

	public boolean hexists(String key,Object field){
		hashOps = redisTemplate.opsForHash();
		return hashOps.hasKey(key,field);
	}

	public boolean hsetnx(String key,Object field,String value){
		hashOps = redisTemplate.opsForHash();
		return hashOps.putIfAbsent(key,field,value);
	}

	public Object hincrebyAndReturn(final String key, final String field, final Long delta){
		List<Object> result = redisTemplate.executePipelined(new RedisCallback<byte[]>() {
			@Override
			public byte[] doInRedis(RedisConnection connection) throws DataAccessException {
				connection.hIncrBy(key.getBytes(),field.getBytes(),delta);
				connection.hGet(key.getBytes(),field.getBytes());
				return null;
			}
		});
		return result.get(0);
	}

	public boolean setnx(final String key,final String value, Long expireTime){
		Boolean result =  redisTemplate.execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.setNX(key.getBytes(), value.getBytes());
			}
		});
		if(result)
			redisTemplate.expire(key,expireTime, TimeUnit.MILLISECONDS);
		return result;
	}
}
