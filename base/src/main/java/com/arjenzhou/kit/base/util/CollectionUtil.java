package com.arjenzhou.kit.base.util;

import java.util.Collection;

/**
 * utility for collection
 *
 * @author bm-kit@arjenzhou.com
 * @see java.util.Collection
 * @since 2023/7/7
 */
public class CollectionUtil {
    /**
     * check if the collection is empty or null
     *
     * @param collection to check
     * @return true if the collection is null or empty
     */
    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * check if the collection is not empty
     *
     * @param collection to check
     * @return true if the collection is not empty
     */
    public static boolean notEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

    /**
     * check if the collection has only one item
     *
     * @param collection to check
     * @return true if the collection has only one item
     */
    public static boolean onlyHasOneItem(Collection<?> collection) {
        return notEmpty(collection) && collection.size() == 1;
    }

    /**
     * check if the collection is null or has items than one
     *
     * @param collection to check
     * @return true if the collection is null or has items than one
     */
    public static boolean nullOrHasMultipleItem(Collection<?> collection) {
        return !onlyHasOneItem(collection);
    }
}
