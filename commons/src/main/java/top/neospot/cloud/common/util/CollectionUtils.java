package top.neospot.cloud.common.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * By neo.chen{neocxf@gmail.com} on 2017/11/3.
 */
public class CollectionUtils {

    private CollectionUtils() {
    }

    /**
     * split the given set with small set with size==initialRequestHotelSize
     *
     * @param original the original set
     * @param count    the size of the partitioned set
     * @param <T>      the data type
     * @return the list of the partitioned set
     */
    public static <T> List<Set<T>> split(Set<T> original, int count) {
        // Create a list of sets to return.
        ArrayList<Set<T>> result = new ArrayList<Set<T>>(count);

        // Create an iterator for the original set.
        Iterator<T> it = original.iterator();

        // Calculate the required number of elements for each set.
        int each = original.size() / count;

        // Create each new set.
        for (int i = 0; i < count; i++) {
            HashSet<T> s = new HashSet<T>(original.size() / count + 1);
            result.add(s);
            for (int j = 0; j < each && it.hasNext(); j++) {
                s.add(it.next());
            }
        }
        return result;
    }

    /**
     * 将bean list转换成map
     *
     * @param list          list
     * @param fieldName4Key map中的key
     * @param c             class
     * @param <K>
     * @param <V>
     * @return Map<K               ,                               V>
     */
    public static <K, V> Map<K, V> list2Map(List<V> list, String fieldName4Key, Class<V> c) {
        Map<K, V> map = new HashMap<K, V>();
        if (list != null) {
            try {
                PropertyDescriptor propDesc = new PropertyDescriptor(fieldName4Key, c);
                Method methodGetKey = propDesc.getReadMethod();
                for (int i = 0; i < list.size(); i++) {
                    V value = list.get(i);
                    @SuppressWarnings("unchecked")
                    K key = (K) methodGetKey.invoke(list.get(i));
                    map.put(key, value);
                }
            } catch (Exception e) {
                throw new IllegalArgumentException("field can't match the key!");
            }
        }

        return map;
    }

    public static <T> boolean isEmpty(Collection<T> collection) {
        return collection == null || collection.size() == 0;
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

    public static <K, V> void putEntriesToMap(Map<K, V> targetMap, Collection<Map.Entry<K, V>> entryList) {
        entryList.forEach(kvEntry -> targetMap.put(kvEntry.getKey(), kvEntry.getValue()));
    }

    public static <T> T[] newArray(Class<T[]> type, int size) {
        return type.cast(Array.newInstance(type.getComponentType(), size));
    }
}
